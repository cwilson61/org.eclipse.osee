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
package org.eclipse.osee.ats.column.ev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.xviewer.IAltLeftClickProvider;
import org.eclipse.nebula.widgets.xviewer.IMultiColumnEditProvider;
import org.eclipse.nebula.widgets.xviewer.IXViewerValueColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.osee.ats.api.IAtsObject;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.ev.IAtsWorkPackage;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.column.WorkPackageFilterTreeDialog;
import org.eclipse.osee.ats.core.client.action.ActionManager;
import org.eclipse.osee.ats.core.client.workflow.AbstractWorkflowArtifact;
import org.eclipse.osee.ats.internal.Activator;
import org.eclipse.osee.ats.internal.AtsClientService;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsColumn;
import org.eclipse.osee.ats.world.WorldXViewerFactory;
import org.eclipse.osee.framework.core.util.Result;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.utility.Artifacts;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Provides column to show selected Work Package and provides for the selection of valid Work Packages configured for
 * Team Def and AIs
 *
 * @author Donald G. Dunne
 */
public class ActivityIdColumnUI extends XViewerAtsColumn implements IMultiColumnEditProvider, IXViewerValueColumn, IAltLeftClickProvider {

   public static ActivityIdColumnUI instance = new ActivityIdColumnUI();

   public static ActivityIdColumnUI getInstance() {
      return instance;
   }

   private ActivityIdColumnUI() {
      super(WorldXViewerFactory.COLUMN_NAMESPACE + ".activityId", "Activity Id", 80, SWT.LEFT, false,
         SortDataType.String, true, "");
   }

   /**
    * XViewer uses copies of column definitions so originals that are registered are not corrupted. Classes extending
    * XViewerValueColumn MUST extend this constructor so the correct sub-class is created
    */
   @Override
   public ActivityIdColumnUI copy() {
      ActivityIdColumnUI newXCol = new ActivityIdColumnUI();
      super.copy(this, newXCol);
      return newXCol;
   }

   @Override
   public String getColumnText(Object element, XViewerColumn column, int columnIndex) {
      String result = "";
      if (element instanceof IAtsObject) {
         result =
            AtsClientService.get().getColumnUtilities().getActivityIdUtility().getColumnText((IAtsObject) element);
      }
      return result;
   }

   @Override
   public boolean handleAltLeftClick(TreeColumn treeColumn, TreeItem treeItem) {
      boolean modified = false;
      try {
         if (treeItem.getData() instanceof Artifact) {
            Artifact selectedArt = (Artifact) treeItem.getData();
            AbstractWorkflowArtifact useAwa = null;
            if ((selectedArt instanceof IAtsAction) && ActionManager.getTeams(selectedArt).size() == 1) {
               useAwa = ActionManager.getFirstTeam(selectedArt);
            } else if (selectedArt instanceof AbstractWorkflowArtifact) {
               useAwa = (AbstractWorkflowArtifact) selectedArt;
            }
            if (useAwa != null) {
               modified = promptChangeActivityId(useAwa, false);
               if (modified && isPersistViewer()) {
                  useAwa.persist("persist attribute via alt-left-click");
               }
               if (modified) {
                  ((XViewerColumn) treeColumn.getData()).getTreeViewer().update(useAwa, null);
               }
            }
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, OseeLevel.SEVERE_POPUP, ex);
      }
      return modified;
   }

   public static boolean promptChangeActivityId(AbstractWorkflowArtifact teamWf, boolean persist) throws OseeCoreException {
      return promptChangeActivityIds(Arrays.asList(teamWf), persist);
   }

   public static boolean promptChangeActivityIds(final Collection<? extends AbstractWorkflowArtifact> awas, boolean persist) throws OseeCoreException {
      boolean modified = false;
      Set<IAtsWorkPackage> commonWorkPackageOptions = new HashSet<IAtsWorkPackage>();
      Set<IAtsWorkPackage> uniqueWorkPackageOptions = new HashSet<IAtsWorkPackage>();
      Result result = getConfiguredWorkPackageOptions(awas, commonWorkPackageOptions, uniqueWorkPackageOptions);
      if (result.isFalse()) {
         AWorkbench.popup("Options Invalid", result.getText());
      } else {
         WorkPackageFilterTreeDialog dialog =
            new WorkPackageFilterTreeDialog("Select Work Package", getMessage(awas, commonWorkPackageOptions,
               uniqueWorkPackageOptions), commonWorkPackageOptions);
         dialog.setInput();
         if (dialog.open() == Window.OK) {
            IAtsWorkPackage workPackage = dialog.getSelection();
            boolean removeFromWorkPackage = dialog.isRemoveFromWorkPackage();
            for (AbstractWorkflowArtifact awa : awas) {
               if (removeFromWorkPackage) {
                  awa.deleteAttributes(AtsAttributeTypes.WorkPackageGuid);
               } else {
                  awa.setSoleAttributeValue(AtsAttributeTypes.WorkPackageGuid, workPackage.getGuid());
               }
               modified = true;
            }
            if (persist) {
               Artifacts.persistInTransaction("Assignee - Prompt Change Activity Id", awas);
            }
         }
      }
      return modified;
   }

   private static String getMessage(Collection<? extends AbstractWorkflowArtifact> awas, Set<IAtsWorkPackage> commonWorkPackageOptions, Set<IAtsWorkPackage> uniqueWorkPackageOptions) {
      String message = "Select Work Package";
      if (awas.size() > 1) {
         message =
            String.format(
               "Select Work Package Option from %d common option(s) out of %d unique options from selected Work Items",
               commonWorkPackageOptions.size(), uniqueWorkPackageOptions.size());
      }
      return message;
   }

   private static Result getConfiguredWorkPackageOptions(final Collection<? extends AbstractWorkflowArtifact> awas, Set<IAtsWorkPackage> workPackageOptions, Set<IAtsWorkPackage> uniqueWorkPackageOptions) throws OseeCoreException {
      Result result = null;
      for (AbstractWorkflowArtifact teamWf : awas) {
         Collection<IAtsWorkPackage> options =
            AtsClientService.get().getEarnedValueService().getWorkPackageOptions(teamWf);
         uniqueWorkPackageOptions.addAll(options);
         if (options.isEmpty()) {
            result = new Result(false, "One or more selected Work Items had no Work Package Options configured.");
            break;
         }
         if (workPackageOptions.isEmpty()) {
            workPackageOptions.addAll(options);
         } else {
            ArrayList<IAtsWorkPackage> setIntersection = Collections.setIntersection(options, workPackageOptions);
            workPackageOptions.clear();
            workPackageOptions.addAll(setIntersection);
         }
      }
      if (result == null) {
         if (workPackageOptions.isEmpty()) {
            result = new Result(false, "Found no common Work Package Options from selected Work Items.");
         } else {
            result = Result.TrueResult;
         }
      }
      return result;
   }

   @Override
   public void handleColumnMultiEdit(TreeColumn treeColumn, Collection<TreeItem> treeItems) {
      try {
         Set<AbstractWorkflowArtifact> awas = new HashSet<AbstractWorkflowArtifact>();
         for (TreeItem item : treeItems) {
            Artifact art = (Artifact) item.getData();
            if (art instanceof AbstractWorkflowArtifact) {
               awas.add((AbstractWorkflowArtifact) art);
            }
         }
         if (awas.isEmpty()) {
            AWorkbench.popup("No Work Items Selected");
         } else {
            promptChangeActivityIds(awas, true);
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, OseeLevel.SEVERE_POPUP, ex);
      }
   }
}
