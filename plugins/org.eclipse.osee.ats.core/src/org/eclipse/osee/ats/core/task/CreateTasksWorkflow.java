/*********************************************************************
 * Copyright (c) 2014 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/

package org.eclipse.osee.ats.core.task;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.commit.CommitConfigItem;
import org.eclipse.osee.ats.api.config.WorkType;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.program.IAtsProgram;
import org.eclipse.osee.ats.api.task.CreateTasksOption;
import org.eclipse.osee.ats.api.team.CreateOption;
import org.eclipse.osee.ats.api.team.IAtsTeamDefinition;
import org.eclipse.osee.ats.api.user.AtsCoreUsers;
import org.eclipse.osee.ats.api.user.AtsUser;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.api.version.IAtsVersion;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.NewActionData;
import org.eclipse.osee.ats.core.internal.AtsApiService;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.util.Conditions;

/**
 * @author Donald G Dunne
 */
public class CreateTasksWorkflow {

   protected final Collection<CreateTasksOption> createTasksOptions;
   protected final boolean createWorkflow;
   protected final boolean reportOnly;
   protected final XResultData resultData;
   protected final IAtsChangeSet changes;
   protected final Date createdDate;
   protected final AtsUser createdBy;
   protected final IAtsTeamWorkflow sourceTeamWf;
   protected final CommitConfigItem commitConfigItem;
   protected final WorkType workType;
   protected IAtsTeamWorkflow destTeam;
   protected final String pcrNumber;
   protected final AtsApi atsApi;
   protected IAtsActionableItem actionableItem;

   public CreateTasksWorkflow(String pcrNumber, Collection<CreateTasksOption> createTasksOptions, Collection<String> taskNamesMissingTaskArtifact, //
      boolean reportOnly, XResultData resultData, IAtsChangeSet changes, Date createdDate, AtsUser createdBy, IAtsTeamWorkflow sourceTeamWf, //
      CommitConfigItem commitConfigArt, WorkType workType, IAtsTeamWorkflow destTeam, IAtsProgram program) {
      this(pcrNumber, createTasksOptions,
         ((!taskNamesMissingTaskArtifact.isEmpty() || createTasksOptions.contains(
            CreateTasksOption.WorkflowsOnly)) && destTeam == null),
         reportOnly, resultData, changes, createdDate, createdBy, sourceTeamWf, commitConfigArt, workType, destTeam,
         program);
   }

   public CreateTasksWorkflow(String pcrNumber, Collection<CreateTasksOption> createTasksOptions, //
      boolean createWorkflow, boolean reportOnly, XResultData resultData, IAtsChangeSet changes, //
      Date createdDate, AtsUser createdBy, IAtsTeamWorkflow sourceTeamWf, //
      CommitConfigItem commitConfigItem, WorkType workType, IAtsTeamWorkflow destTeam, IAtsProgram program) {
      this.pcrNumber = pcrNumber;
      this.createTasksOptions = createTasksOptions;
      this.createWorkflow = createWorkflow;
      this.reportOnly = reportOnly;
      this.resultData = resultData;
      this.changes = changes;
      this.createdDate = createdDate;
      this.createdBy = createdBy;
      this.sourceTeamWf = sourceTeamWf;
      this.commitConfigItem = commitConfigItem;
      this.workType = workType;
      this.destTeam = destTeam;
      this.atsApi = AtsApiService.get();
   }

   protected void logWorkflowCreation() {
      resultData.logf("Create [%s] Workflow\n", workType.name());
   }

   public boolean isSourceTeamWfEqualDestination() {
      return false;
   }

   public IAtsActionableItem getActionableItem() {
      return actionableItem;
   }

   public IAtsTeamWorkflow createMissingWorkflow() {
      // Now, report all tasks of unknown origin
      if (createWorkflow) {
         logWorkflowCreation();

         /**
          * Don't need to create workflow if source equals destination.
          */
         if (isSourceTeamWfEqualDestination()) {
            destTeam = sourceTeamWf;
         } else {
            // Create missing workflow
            IAtsAction action = sourceTeamWf.getParentAction();
            Set<IAtsActionableItem> actionableItems = new HashSet<>();

            List<AtsUser> assignees = getAssignees();

            IAtsActionableItem aia = getActionableItem();
            if (aia != null) {
               actionableItems.add(aia);

               IAtsTeamDefinition teamDef =
                  AtsApiService.get().getTeamDefinitionService().getImpactedTeamDefs(actionableItems).iterator().next();

               NewActionData data = atsApi.getActionService() //
                  .createTeamWfData(changes.getComment(), action, teamDef) //
                  .andAssignees(assignees) //
                  // Set name off derived-from host workflow and not off action
                  .andTitle(sourceTeamWf.getName()) //
                  .andCreatedBy(AtsCoreUsers.SYSTEM_USER) //
                  .andCreateOption(CreateOption.Duplicate_If_Exists);
               NewActionData newData = atsApi.getActionService().createAction(data);
               Conditions.assertSuccess(newData.getRd());
               destTeam = newData.getActResult().getAtsTeamWfs().iterator().next();

            }
         }
      }
      return destTeam;
   }

   protected List<AtsUser> getAssignees() {
      List<AtsUser> assignees = new LinkedList<>();
      assignees.add(AtsCoreUsers.UNASSIGNED_USER);
      return assignees;
   }

   public void setTargetedVersion() {
      if (commitConfigItem instanceof IAtsVersion) {
         IAtsVersion targetedVersion = atsApi.getVersionService().getTargetedVersion(destTeam);
         IAtsVersion commitConfigVer = (IAtsVersion) commitConfigItem;
         if (targetedVersion == null || !targetedVersion.equals(commitConfigVer)) {
            if (targetedVersion != null) {
               changes.unrelateAll(destTeam, AtsRelationTypes.TeamWorkflowTargetedForVersion_Version);
            }
            changes.relate(destTeam, AtsRelationTypes.TeamWorkflowTargetedForVersion_Version, commitConfigVer);
         }
      }

   }

   public void setActionableItem(IAtsActionableItem ai) {
      this.actionableItem = ai;
   }

}
