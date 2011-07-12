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
package org.eclipse.osee.framework.ui.skynet.commandHandlers;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osee.framework.access.AccessControlManager;
import org.eclipse.osee.framework.core.enums.PermissionEnum;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.model.TransactionRecord;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.change.Change;
import org.eclipse.osee.framework.skynet.core.change.RelationChange;
import org.eclipse.osee.framework.skynet.core.relation.RelationManager;
import org.eclipse.osee.framework.skynet.core.revision.ChangeManager;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.ui.PlatformUI;

/**
 * @author Paul K. Waldfogel
 * @author Jeff C. Phillips
 */
public class RevertArtifactHandler extends AbstractHandler {
   private List<Change> changes;

   @Override
   public Object execute(ExecutionEvent event) {
      // This is serious stuff, make sure the user understands the impact.

      List<List<Artifact>> artifacts = new LinkedList<List<Artifact>>();
      Set<Artifact> duplicateCheck = new HashSet<Artifact>();

      for (Change change : changes) {
         List<Artifact> artifactList = new LinkedList<Artifact>();

         Artifact changeArtifact = change.getChangeArtifact();
         if (!duplicateCheck.contains(changeArtifact)) {
            artifactList.add(changeArtifact);
            artifacts.add(artifactList);
            duplicateCheck.add(changeArtifact);
         }

      }

      System.out.println("We are here");

      revert(duplicateCheck);
      //      RevertWizard wizard = new RevertWizard(artifacts);
      //      NonmodalWizardDialog dialog = new NonmodalWizardDialog(Displays.getActiveShell(), wizard);
      //      dialog.create();
      //      dialog.open();
      return null;
   }

   private void revert(Collection<Artifact> artifacts) {
      if (MessageDialog.openConfirm(Displays.getActiveShell(),
         "Confirm Replace with baseline version of " + artifacts.size() + " attributes.",
         "All attribute changes selected will be replaced with thier baseline version.")) {
         for (Artifact artifact : artifacts) {
            try {
               TransactionRecord baselineTransactionRecord = artifact.getBranch().getBaseTransaction();
               for (Change change : ChangeManager.getChangesPerArtifact(artifact, new NullProgressMonitor())) {
                  if (change.getTxDelta().getEndTx().getId() == baselineTransactionRecord.getId()) {
                     //These should be the baseline items only
                     if (change.getItemKind().equals("Attribute")) {
                        artifact.getAttributeById(change.getItemId(), true).replaceWithVersion((int) change.getGamma());
                     } else if (change.getItemKind().equals("Relation")) {
                        RelationChange relationChange = (RelationChange) change;
                        RelationManager.getLoadedRelationById(relationChange.getItemId(), relationChange.getArtId(),
                           relationChange.getBArtId(), artifact.getBranch()).repplaceWithVersion(
                           (int) change.getGamma());
                     }
                  }
               }
            } catch (OseeCoreException ex) {
               OseeLog.log(getClass(), OseeLevel.SEVERE_POPUP, ex);
            }
         }
      }
   }

   @Override
   public boolean isEnabled() {
      if (PlatformUI.getWorkbench().isClosing()) {
         return false;
      }

      boolean isEnabled = false;
      try {
         ISelectionProvider selectionProvider =
            AWorkbench.getActivePage().getActivePart().getSite().getSelectionProvider();

         if (selectionProvider != null && selectionProvider.getSelection() instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selectionProvider.getSelection();
            changes = Handlers.getArtifactChangesFromStructuredSelection(structuredSelection);

            if (changes.isEmpty()) {
               return false;
            }

            for (Change change : changes) {
               isEnabled = AccessControlManager.hasPermission(change.getChangeArtifact(), PermissionEnum.WRITE);
               if (!isEnabled) {
                  break;
               }
            }
         }
      } catch (Exception ex) {
         OseeLog.log(getClass(), Level.SEVERE, ex);
         return false;
      }
      return isEnabled;
   }
}
