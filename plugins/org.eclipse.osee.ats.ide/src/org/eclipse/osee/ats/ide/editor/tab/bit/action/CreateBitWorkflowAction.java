/*******************************************************************************
 * Copyright (c) 2021 Boeing.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.ide.editor.tab.bit.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.config.JaxTeamWorkflow;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.program.IAtsProgram;
import org.eclipse.osee.ats.api.team.IAtsTeamDefinition;
import org.eclipse.osee.ats.api.util.AtsImage;
import org.eclipse.osee.ats.api.util.AtsTopicEvent;
import org.eclipse.osee.ats.api.version.IAtsVersion;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.cr.bit.model.BitUtil;
import org.eclipse.osee.ats.api.workflow.cr.bit.model.BuildImpactData;
import org.eclipse.osee.ats.api.workflow.cr.bit.model.BuildImpactDatas;
import org.eclipse.osee.ats.api.workflow.cr.bit.model.BuildImpactState;
import org.eclipse.osee.ats.ide.editor.WorkflowEditor;
import org.eclipse.osee.ats.ide.editor.tab.bit.WfeBitTab;
import org.eclipse.osee.ats.ide.editor.tab.bit.XBitViewer;
import org.eclipse.osee.ats.ide.internal.AtsApiService;
import org.eclipse.osee.ats.ide.util.widgets.dialog.ActionableItemTreeWithChildrenDialog;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.enums.Active;
import org.eclipse.osee.framework.core.operation.Operations;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.osee.framework.ui.skynet.results.XResultDataUI;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.osee.framework.ui.swt.ImageManager;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author Donald G. Dunne
 */
public class CreateBitWorkflowAction extends Action {

   private final IAtsTeamWorkflow teamWf;
   private final AtsApi atsApi;
   private final WorkflowEditor editor;

   public CreateBitWorkflowAction(IAtsTeamWorkflow teamWf, WorkflowEditor editor) {
      this.teamWf = teamWf;
      this.editor = editor;
      atsApi = AtsApiService.get();
   }

   @Override
   public void run() {
      WfeBitTab bitTab = editor.getBitTab();
      XBitViewer viewer = bitTab.getxViewer();
      TreeItem[] items = viewer.getTree().getSelection();
      if (items.length != 1) {
         AWorkbench.popup("Must Select a Single Build Impact");
         return;
      }
      TreeItem item = items[0];
      Object selectedItem = item.getData();
      if (selectedItem instanceof BuildImpactData) {
         BuildImpactData bid = (BuildImpactData) selectedItem;
         if (!bid.getState().equals(BuildImpactState.InWork.name())) {
            AWorkbench.popup("Build Impact selected must be in InWork state");
            return;
         }
         ArtifactToken progArt = bid.getProgram();
         IAtsProgram program = atsApi.getProgramService().getProgramById(progArt);
         List<IAtsActionableItem> validAis = new ArrayList<>();
         for (IAtsActionableItem ai : atsApi.getProgramService().getAis(program)) {
            if (ai.getTags() != null && ai.getTags().contains(BitUtil.BIT_AI)) {
               validAis.add(ai);
            }
         }
         IAtsTeamDefinition teamDef = atsApi.getProgramService().getTeamDefHoldingVersions(program);
         // Remove top AI if configured
         Objects.requireNonNull(teamDef, "teamDef can not be null");
         if (teamDef.getActionableItems() != null && !teamDef.getActionableItems().isEmpty()) {
            IAtsActionableItem ai = teamDef.getActionableItems().iterator().next();
            validAis.remove(ai);
         }

         ActionableItemTreeWithChildrenDialog dialog =
            new ActionableItemTreeWithChildrenDialog(Active.Active, validAis);
         dialog.setAddIncludeAllCheckbox(false);
         dialog.setShowChildren(false);
         if (dialog.open() == Window.OK) {
            handleSelection(bid, dialog.getChecked(), bitTab, selectedItem);
         }
      } else {
         AWorkbench.popup("Must Select a Single Build Impact");
         return;
      }
   }

   private void handleSelection(BuildImpactData selBid, Collection<IAtsActionableItem> aias, final WfeBitTab bitTab,
      Object selectedItem) {

      Job createSiblingJob = new Job("Creating Sibling Workflows") {

         @Override
         protected IStatus run(IProgressMonitor monitor) {

            BuildImpactDatas bids = new BuildImpactDatas();
            bids.setTeamWf(teamWf.getArtifactToken());
            for (IAtsActionableItem ai : aias) {
               BuildImpactData bid = new BuildImpactData();
               bid.setBids(bids);
               bid.setBidArt(selBid.getBidArt());
               bid.setBuild(selBid.getBuild());
               bid.setProgram(selBid.getProgram());
               bid.setState(selBid.getState());
               bids.addBuildImpactData(bid);

               JaxTeamWorkflow jTeamWf = new JaxTeamWorkflow();
               jTeamWf.setName(teamWf.getName());
               jTeamWf.setNewAi(ai.getArtifactToken());
               bitTab.creatingSibling(teamWf, jTeamWf, ai);
               IAtsVersion version = atsApi.getVersionService().getVersionById(selBid.getBuild());
               if (version != null) {
                  jTeamWf.setTargetVersion(version.getArtifactToken());
               }
               jTeamWf.setPriority(
                  atsApi.getAttributeResolver().getSoleAttributeValue(teamWf, AtsAttributeTypes.Priority, ""));
               bid.addTeamWorkflow(jTeamWf);
            }
            bids = atsApi.getServerEndpoints().getActionEndpoint().updateBids(teamWf.getArtifactToken(), bids);
            if (bids.getResults().isErrors()) {
               XResultDataUI.report(bids.getResults(), "Error Creating Sibling Workflows");
            } else {
               ((Artifact) teamWf).reloadAttributesAndRelations();
               ((Artifact) teamWf.getParentAction()).reloadAttributesAndRelations();
               atsApi.getEventService().postAtsWorkItemTopicEvent(AtsTopicEvent.WORK_ITEM_MODIFIED,
                  Arrays.asList(teamWf), bids.getTransaction());
            }

            return Status.OK_STATUS;
         }
      };
      Operations.scheduleJob(createSiblingJob, true, Job.SHORT,
         new CreateSiblingChangeAdapter(selectedItem, bitTab.getxViewer()));

   }

   private final class CreateSiblingChangeAdapter extends JobChangeAdapter {
      private final Object selectedItem;
      private final XBitViewer xBitViewer;

      public CreateSiblingChangeAdapter(Object selectedItem, XBitViewer xBitViewer) {
         this.selectedItem = selectedItem;
         this.xBitViewer = xBitViewer;
      }

      @Override
      public void done(IJobChangeEvent event) {
         Displays.ensureInDisplayThread(new Runnable() {

            @Override
            public void run() {
               xBitViewer.expandToLevel(selectedItem, 2);
            }

         });
      }
   }

   @Override
   public ImageDescriptor getImageDescriptor() {
      return ImageManager.getImageDescriptor(AtsImage.WORKFLOW);
   }

   @Override
   public String getText() {
      return "Create New BIT Workflow(s)";
   }

   @Override
   public boolean isEnabled() {
      return teamWf.isInWork();
   }

}
