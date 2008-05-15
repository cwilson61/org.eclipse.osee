/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.define.blam.operation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.TransactionArtifactModifiedEvent;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactModifiedEvent.ModType;
import org.eclipse.osee.framework.skynet.core.attribute.ArtifactSubtypeDescriptor;
import org.eclipse.osee.framework.skynet.core.attribute.Attribute;
import org.eclipse.osee.framework.skynet.core.attribute.AttributeType;
import org.eclipse.osee.framework.skynet.core.attribute.ConfigurationPersistenceManager;
import org.eclipse.osee.framework.skynet.core.event.SkynetEventManager;
import org.eclipse.osee.framework.skynet.core.relation.RelationLink;
import org.eclipse.osee.framework.skynet.core.relation.RelationPersistenceManager;
import org.eclipse.osee.framework.skynet.core.relation.RelationTypeManager;
import org.eclipse.osee.framework.ui.plugin.util.Displays;
import org.eclipse.osee.framework.ui.skynet.blam.BlamVariableMap;
import org.eclipse.osee.framework.ui.skynet.blam.operation.AbstractBlam;
import org.eclipse.swt.widgets.Display;

/**
 * Changes the descriptor type of an artifact to the provided descriptor.
 * 
 * @author Jeff C. Phillips
 */
public class ChangeArtifactType extends AbstractBlam {
   private static final ConfigurationPersistenceManager configurationPersistenceManager =
         ConfigurationPersistenceManager.getInstance();
   private static final RelationPersistenceManager relationPersistenceManager =
         RelationPersistenceManager.getInstance();
   private List<Attribute<?>> attributesToPurge;
   private List<RelationLink> linksToPurge;

   @SuppressWarnings("unchecked")
   public void runOperation(BlamVariableMap variableMap, IProgressMonitor monitor) throws Exception {
      processChange(variableMap.getArtifacts("artifact"), variableMap.getArtifactSubtypeDescriptor("New Artifact Type"));
   }

   /**
    * Changes the descriptor of the artifacts to the provided artifact descriptor
    * 
    * @param artifacts
    * @param descriptor
    * @throws SQLException
    */
   private void processChange(List<Artifact> artifacts, ArtifactSubtypeDescriptor descriptor) throws Exception {
      if (artifacts.isEmpty()) {
         throw new IllegalArgumentException("The artifact list can not be empty");
      }

      for (Artifact artifact : artifacts) {
         processAttributes(artifact, descriptor);
         processRelations(artifact, descriptor);

         if (doesUserAcceptArtifactChange(artifact, descriptor)) {
            changeArtifactType(artifact, descriptor);

            SkynetEventManager.getInstance().kick(new TransactionArtifactModifiedEvent(artifact, ModType.Changed, this));
         }
      }
   }

   /**
    * Splits the attributes of the current artifact into two groups. The attributes that are compatable for the new type
    * and the attributes that will need to be purged.
    * 
    * @param artifact
    * @param descriptor
    * @throws SQLException
    */
   private void processAttributes(Artifact artifact, ArtifactSubtypeDescriptor descriptor) throws SQLException {
      attributesToPurge = new LinkedList<Attribute<?>>();

      Collection<AttributeType> attributeTypes =
            configurationPersistenceManager.getAttributeTypesFromArtifactType(descriptor, artifact.getBranch());

      for (AttributeType attributeType : artifact.getAttributeTypes()) {
         if (!attributeTypes.contains(attributeType)) {
            attributesToPurge.addAll(artifact.getAttributes(attributeType.getName()));
         }
      }
   }

   /**
    * Splits the relationLinks of the current artifact into Two groups. The links that are compatable for the new type
    * and the links that will need to be pruged.
    * 
    * @param artifact
    * @param artifactType
    * @throws SQLException
    */
   private void processRelations(Artifact artifact, ArtifactSubtypeDescriptor artifactType) throws SQLException {
      linksToPurge = new LinkedList<RelationLink>();

      for (RelationLink link : artifact.getLinkManager().getLinks()) {
         int sideMax =
               RelationTypeManager.getRelationSideMax(link.getRelationType(), artifactType, link.getArtifactA().equals(
                     artifact));

         if (sideMax == 0) {
            linksToPurge.add(link);
         }
      }
   }

   /**
    * @param artifact
    * @param descriptor
    * @return true if the user acceptes the purging of the attributes and realtions that are not compatable for the new
    *         artifact type else false.
    */
   private boolean doesUserAcceptArtifactChange(final Artifact artifact, final ArtifactSubtypeDescriptor descriptor) {
      if (!linksToPurge.isEmpty() || !attributesToPurge.isEmpty()) {
         ArtifactChangeMessageRunnable messageRunnable = new ArtifactChangeMessageRunnable(artifact, descriptor);
         Displays.ensureInDisplayThread(messageRunnable, true);
         return messageRunnable.isAccept();
      } else {
         return true;
      }
   }

   private class ArtifactChangeMessageRunnable implements Runnable {
      private boolean accept = false;
      private Artifact artifact;
      private ArtifactSubtypeDescriptor descriptor;

      public ArtifactChangeMessageRunnable(Artifact artifact, ArtifactSubtypeDescriptor descriptor) {
         this.artifact = artifact;
         this.descriptor = descriptor;
      }

      public void run() {
         accept =
               MessageDialog.openQuestion(
                     Display.getCurrent().getActiveShell(),
                     "Confirm Artifact Type Change ",
                     "There has been a conflict in changing " + artifact.getDescriptiveName() + " to " + descriptor.getName() + " type. \n" + "The following data will need to be purged " + (linksToPurge.isEmpty() ? "" : Collections.toString(
                           linksToPurge, ":", ",", null)) + (attributesToPurge.isEmpty() ? "" : Collections.toString(
                           attributesToPurge, ":", ",", null)));
      }

      /**
       * @return Returns the accept.
       */
      public boolean isAccept() {
         return accept;
      }
   };

   /**
    * Sets the artifact descriptor.
    * 
    * @param artifact
    * @param descriptor
    * @throws SQLException
    */
   private void changeArtifactType(Artifact artifact, ArtifactSubtypeDescriptor descriptor) throws Exception {
      for (Attribute<?> attribute : attributesToPurge) {
         attribute.purge();
      }

      if (!linksToPurge.isEmpty()) {
         relationPersistenceManager.purgeRelationLinks(linksToPurge);
      }

      artifact.changeArtifactType(descriptor);
   }

   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.osee.framework.ui.skynet.blam.operation.BlamOperation#getXWidgetXml()
    */
   public String getXWidgetsXml() {
      return "<xWidgets><XWidget xwidgetType=\"XListDropViewer\" displayName=\"artifact\" /><XWidget xwidgetType=\"XArtifactTypeListViewer\" displayName=\"New Artifact Type\" /></xWidgets>";
   }
}