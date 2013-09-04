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
package org.eclipse.osee.ats.util.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.osee.ats.core.client.branch.AtsBranchManagerCore;
import org.eclipse.osee.ats.internal.Activator;
import org.eclipse.osee.ats.util.widgets.dialog.MultipleBranchSelectionDialog;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.model.Branch;
import org.eclipse.osee.framework.core.model.MergeBranch;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.BranchManager;
import org.eclipse.osee.framework.ui.skynet.FrameworkImage;
import org.eclipse.osee.framework.ui.skynet.util.MergeInProgressHandler;
import org.eclipse.osee.framework.ui.swt.ImageManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * @author Angel Avila
 */
public class XWorkingBranchDeleteMerge extends XWorkingBranchButtonAbstract {

   public final static String WIDGET_NAME = "XWorkingBranchDeleteMerge";

   @Override
   protected void initButton(final Button button) {
      button.setToolTipText("Delete Merge Branch(es)");
      button.setImage(ImageManager.getImage(FrameworkImage.DELETE));
      button.addListener(SWT.Selection, new Listener() {
         @Override
         public void handleEvent(Event e) {
            try {
               Branch workingBranch = getTeamArt().getWorkingBranch();
               if (isWorkingBranchCommitWithMergeInProgress()) {
                  List<Branch> selectedBranches = new ArrayList<Branch>();
                  Collection<Branch> branchesAlreadyCommitted =
                     AtsBranchManagerCore.getBranchesCommittedTo(getTeamArt());
                  List<MergeBranch> mergeBranches = BranchManager.getMergeBranches(workingBranch);

                  Set<Branch> destinationMinusAlreadyCommitted = new HashSet<Branch>();
                  // Remove all the Merge branches having to do with a Destination branch that's already been committed, can't delete these merge branches
                  for (MergeBranch branch : mergeBranches) {
                     if (!branchesAlreadyCommitted.contains(branch.getDestinationBranch())) {
                        destinationMinusAlreadyCommitted.add(branch.getDestinationBranch());
                     }
                  }

                  if (destinationMinusAlreadyCommitted.size() > 1) {
                     MultipleBranchSelectionDialog dialog =
                        new MultipleBranchSelectionDialog(destinationMinusAlreadyCommitted,
                           "Select Destination Branch(es)",
                           "Select the Destination branch(es) for which you want to Delete the Merge Branch");
                     if (dialog.open() == 0) {
                        selectedBranches.addAll(dialog.getSelectedBranches());
                     }
                  } else if (destinationMinusAlreadyCommitted.size() == 1) {
                     MergeBranch mergeBranch = BranchManager.getFirstMergeBranch(workingBranch);
                     selectedBranches.add(mergeBranch.getDestinationBranch());
                  }

                  if (!selectedBranches.isEmpty()) {
                     MergeInProgressHandler.deleteMultipleMergeBranches(workingBranch, selectedBranches, false);
                  }
               }
            } catch (OseeCoreException ex) {
               OseeLog.log(Activator.class, Level.SEVERE, ex);
            }
         }
      });
   }

   @Override
   protected void refreshEnablement(Button button) {
      button.setEnabled(destinationBranchNotCommitted());
   }

   private boolean destinationBranchNotCommitted() {
      boolean toReturn = false;
      try {
         if (isWorkingBranchCommitWithMergeInProgress()) {
            List<MergeBranch> mergeBranches = BranchManager.getMergeBranches(getWorkingBranch());
            Collection<Branch> committedBranches = AtsBranchManagerCore.getBranchesCommittedTo(getTeamArt());
            List<MergeBranch> remainingMergeBranches = new ArrayList<MergeBranch>();

            for (MergeBranch mergeBranch : mergeBranches) {
               if (!committedBranches.contains(mergeBranch.getDestinationBranch())) {
                  remainingMergeBranches.add(mergeBranch);
               }
            }
            if (!remainingMergeBranches.isEmpty()) {
               toReturn = true;
            }
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, Level.SEVERE, ex);
      }

      return toReturn;

   }
}
