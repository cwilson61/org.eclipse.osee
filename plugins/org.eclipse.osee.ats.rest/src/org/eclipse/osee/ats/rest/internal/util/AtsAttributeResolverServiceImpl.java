/*******************************************************************************
 * Copyright (c) 2013 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.rest.internal.util;

import java.rmi.activation.Activator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.api.workdef.IAtsWidgetDefinition;
import org.eclipse.osee.ats.api.workdef.IAttributeResolver;
import org.eclipse.osee.ats.api.workflow.IAttribute;
import org.eclipse.osee.ats.rest.internal.AtsServerImpl;
import org.eclipse.osee.framework.core.data.IAttributeType;
import org.eclipse.osee.framework.core.util.Conditions;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.type.OseeStateException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.data.AttributeReadable;

/**
 * @author Donald G. Dunne
 */
public class AtsAttributeResolverServiceImpl implements IAttributeResolver {

   private static OrcsApi orcsApi;

   public void setOrcsApi(OrcsApi orcsApi) {
      AtsAttributeResolverServiceImpl.orcsApi = orcsApi;
   }

   public void start() throws OseeCoreException {
      Conditions.checkNotNull(orcsApi, "OrcsApi");
      System.out.println("ATS - AtsAttributeResolverServiceImpl started");
   }

   @Override
   public boolean isAttributeNamed(String attributeName) {
      return getAttributeType(attributeName) != null;
   }

   @Override
   public String getUnqualifiedName(String attributeName) {
      return getAttributeType(attributeName).getUnqualifiedName();
   }

   @Override
   public void setXWidgetNameBasedOnAttributeName(String attributeName, IAtsWidgetDefinition widgetDef) {
      try {
         if (!Strings.isValid(widgetDef.getXWidgetName())) {
            widgetDef.setXWidgetName(AttributeTypeToXWidgetName.getXWidgetName(orcsApi, getAttributeType(attributeName)));
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(AtsAttributeResolverServiceImpl.class, Level.SEVERE, ex);
      }
   }

   @Override
   public String getDescription(String attributeName) {
      return getAttributeType(attributeName).getDescription();
   }

   @Override
   public IAttributeType getAttributeType(String attributeName) {
      IAttributeType attrType = null;
      try {
         for (IAttributeType type : orcsApi.getOrcsTypes(null).getAttributeTypes().getAll()) {
            if (type.getName().equals(attributeName)) {
               attrType = type;
            }
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, Level.SEVERE, ex);
      }
      return attrType;
   }

   @Override
   public <T> T getSoleAttributeValue(IAtsWorkItem workItem, IAttributeType attributeType, T defaultReturnValue) throws OseeCoreException {
      return AtsServerImpl.get().getArtifact(workItem).getSoleAttributeValue(attributeType, defaultReturnValue);

   }

   @Override
   public Collection<String> getAttributesToStringList(IAtsWorkItem workItem, IAttributeType attributeType) throws OseeCoreException {
      return AtsServerImpl.get().getArtifact(workItem).getAttributeValues(attributeType);
   }

   @Override
   public boolean isAttributeTypeValid(IAtsWorkItem workItem, IAttributeType attributeType) throws OseeCoreException {
      return AtsServerImpl.get().getArtifact(workItem).isAttributeTypeValid(attributeType);
   }

   @Override
   public String getSoleAttributeValueAsString(IAtsWorkItem workItem, IAttributeType attributeType, String defaultValue) throws OseeCoreException {
      return AtsServerImpl.get().getArtifact(workItem).getSoleAttributeValue(attributeType, defaultValue).toString();
   }

   @Override
   public void setSoleAttributeValue(IAtsWorkItem workItem, IAttributeType attributeType, Object value) throws OseeCoreException {
      // Sets on Server need to be through transaction
      throw new OseeStateException(
         "Invalid: Must use setSoleAttributeValue(IAtsWorkItem workItem, IAttributeType attributeType, Object value, IAtsChangeSet changes)");
   }

   @Override
   public int getAttributeCount(IAtsWorkItem workItem, IAttributeType attributeType) throws OseeCoreException {
      return AtsServerImpl.get().getArtifact(workItem).getAttributeCount(attributeType);
   }

   @Override
   public void addAttribute(IAtsWorkItem workItem, IAttributeType attributeType, Object value) throws OseeCoreException {
      // Sets on Server need to be through transaction
      throw new OseeStateException("Not Implemented");
   }

   @SuppressWarnings("unchecked")
   @Override
   public <T> Collection<IAttribute<T>> getAttributes(IAtsWorkItem workItem, IAttributeType attributeType) throws OseeCoreException {
      Collection<IAttribute<T>> attrs = new ArrayList<IAttribute<T>>();
      for (AttributeReadable<Object> attr : AtsServerImpl.get().getArtifact(workItem).getAttributes(attributeType)) {
         attrs.add(new AttributeWrapper<T>((AttributeReadable<T>) attr));
      }
      return attrs;
   }

   @Override
   public void deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType) throws OseeCoreException {
      // Sets on Server need to be through transaction
      throw new OseeStateException(
         "Invalid: Must use deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType, IAtsChangeSet changes)");
   }

   @Override
   public void deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType, IAtsChangeSet changes) throws OseeCoreException {
      changes.deleteSoleAttribute(workItem, attributeType);
   }

   @Override
   public void setSoleAttributeValue(IAtsWorkItem workItem, IAttributeType attributeType, String value, IAtsChangeSet changes) throws OseeCoreException {
      changes.setSoleAttributeValue(workItem, attributeType, value);
   }

   @Override
   public void setSoleAttributeValue(IAtsWorkItem workItem, IAttributeType attributeType, Object value, IAtsChangeSet changes) throws OseeCoreException {
      changes.setSoleAttributeValue(workItem, attributeType, value);
   }

   @Override
   public void addAttribute(IAtsWorkItem workItem, IAttributeType attributeType, Object value, IAtsChangeSet changes) throws OseeCoreException {
      changes.addAttribute(workItem, attributeType, value);
   }

   @Override
   public void deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType, Object value, IAtsChangeSet changes) throws OseeCoreException {
      changes.deleteAttribute(workItem, attributeType, value);
   }

   @Override
   public <T> void setValue(IAtsWorkItem workItem, IAttribute<String> attr, IAttributeType attributeType, T value, IAtsChangeSet changes) throws OseeCoreException {
      changes.setValue(workItem, attr, attributeType, value);
   }

   @Override
   public <T> void deleteAttribute(IAtsWorkItem workItem, IAttribute<T> attr, IAtsChangeSet changes) throws OseeCoreException {
      changes.deleteAttribute(workItem, attr);
   }

   @Override
   public <T> void deleteAttribute(IAtsWorkItem workItem, IAttribute<T> attr) throws OseeCoreException { // Sets on Server need to be through transaction
      throw new OseeStateException(
         "Invalid: Must use deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType, IAtsChangeSet changes)");
   }

   @Override
   public <T> void setValue(IAtsWorkItem workItem, IAttribute<String> attr, IAttributeType attributeType, T value) throws OseeCoreException {
      // Sets on Server need to be through transaction
      throw new OseeStateException(
         "Invalid: Must use deleteSoleAttribute(IAtsWorkItem workItem, IAttributeType attributeType, IAtsChangeSet changes)");
   }

}
