/*********************************************************************
 * Copyright (c) 2004, 2007 Boeing
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

package org.eclipse.osee.ats.ide.demo.navigate;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.review.IAtsPeerToPeerReview;
import org.eclipse.osee.ats.api.task.JaxAtsTask;
import org.eclipse.osee.ats.api.task.NewTaskData;
import org.eclipse.osee.ats.api.task.NewTaskSet;
import org.eclipse.osee.ats.api.team.ChangeTypes;
import org.eclipse.osee.ats.api.user.AtsUser;
import org.eclipse.osee.ats.api.util.AtsImage;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.NewActionData;
import org.eclipse.osee.ats.ide.demo.internal.AtsApiService;
import org.eclipse.osee.ats.ide.workflow.goal.GoalArtifact;
import org.eclipse.osee.ats.ide.workflow.teamwf.TeamWorkFlowArtifact;
import org.eclipse.osee.ats.ide.world.WorldEditor;
import org.eclipse.osee.ats.ide.world.WorldEditorSimpleProvider;
import org.eclipse.osee.framework.core.enums.DemoUsers;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavItemCat;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateComposite.TableLoadOption;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateItemAction;
import org.eclipse.osee.framework.ui.skynet.results.XResultDataUI;
import org.eclipse.osee.framework.ui.swt.Displays;

/**
 * @author Donald G. Dunne
 */
public class CreateGoalTestDemoArtifacts extends XNavigateItemAction {
   private Date createdDate;
   private AtsUser createdBy;
   private AtsApi atsApi;

   public CreateGoalTestDemoArtifacts(XNavItemCat xNavItemCat) {
      super("Create Test Goal Artifacts - Demo", AtsImage.GOAL, xNavItemCat);
   }

   @Override
   public void run(TableLoadOption... tableLoadOptions) {
      if (AtsApiService.get().getStoreService().isProductionDb()) {
         AWorkbench.popup("Can't be run on production");
         return;
      }
      if (!MessageDialog.openConfirm(Displays.getActiveShell(), getName(), getName())) {
         return;
      }

      atsApi = AtsApiService.get();
      createdDate = new Date();
      createdBy = atsApi.getUserService().getUserById(DemoUsers.Joe_Smith);

      IAtsChangeSet changes = AtsApiService.get().createChangeSet(getName());
      createdBy = AtsApiService.get().getUserService().getCurrentUser();
      GoalArtifact sawCodeGoal = (GoalArtifact) AtsApiService.get().getActionService().createGoal("SAW Code", changes);
      GoalArtifact sawTestGoal = (GoalArtifact) AtsApiService.get().getActionService().createGoal("SAW Test", changes);
      GoalArtifact toolsTeamGoal =
         (GoalArtifact) AtsApiService.get().getActionService().createGoal("Tools Team", changes);
      GoalArtifact facilitiesGoal =
         (GoalArtifact) AtsApiService.get().getActionService().createGoal("Facilities Team", changes);
      GoalArtifact cisReqGoal =
         (GoalArtifact) AtsApiService.get().getActionService().createGoal("CIS Requirements", changes);
      changes.execute();

      IAtsTeamWorkflow teamArt = createFixThisModelAction(sawCodeGoal);

      createCheckSignalsAction(sawCodeGoal, cisReqGoal);

      createRemoveWorkflowButtonAction(sawTestGoal, cisReqGoal);

      createAction7(facilitiesGoal);

      teamArt = createAction456(sawCodeGoal, facilitiesGoal, teamArt);

      NewTaskSet newTaskSet = NewTaskSet.createWithData(teamArt, getName(), createdBy);
      NewTaskData newTaskData = newTaskSet.getTaskData();

      for (String name : Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
         "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ",
         "KK", "LL", "MM", "NN", "OO", "PP", "QQ", "RR")) {
         JaxAtsTask.create(newTaskData, "Task " + name, createdBy, createdDate);
      }

      newTaskSet = AtsApiService.get().getTaskService().createTasks(
         NewTaskSet.create(newTaskData, "CreateGoalTestDemoArtifacts", DemoUsers.Joe_Smith));

      changes = AtsApiService.get().createChangeSet(getName());
      for (JaxAtsTask task : newTaskSet.getNewTaskDatas().iterator().next().getTasks()) {
         Artifact taskArt = AtsApiService.get().getQueryServiceIde().getArtifact(task);
         toolsTeamGoal.addMember(taskArt);
         changes.relate(toolsTeamGoal, AtsRelationTypes.Goal_Member, taskArt);
      }
      changes.execute();

      WorldEditor.open(new WorldEditorSimpleProvider("Goals",
         Arrays.asList(sawCodeGoal, sawTestGoal, toolsTeamGoal, facilitiesGoal, cisReqGoal)));

   }

   private void createAction7(GoalArtifact facilitiesGoal) {

      Set<IAtsActionableItem> aias =
         AtsApiService.get().getActionableItemService().getActionableItems(Arrays.asList("Network"));

      NewActionData data = atsApi.getActionService() //
         .createActionData(getClass().getSimpleName(), "Add the Improvement", "description") //
         .andAis(aias).andChangeType(ChangeTypes.Improvement).andPriority("4");
      NewActionData newActionData = atsApi.getActionService().createAction(data);
      if (newActionData.getRd().isErrors()) {
         XResultDataUI.report(newActionData.getRd(), getName());
      }
      IAtsAction action = newActionData.getActResult().getAtsAction();

      IAtsChangeSet changes = atsApi.createChangeSet(getClass().getSimpleName());
      facilitiesGoal.addMember(action.getStoreObject());
      changes.add(facilitiesGoal);
      changes.execute();
   }

   private IAtsTeamWorkflow createAction456(GoalArtifact sawCodeGoal, GoalArtifact facilitiesGoal,
      IAtsTeamWorkflow teamArt) {
      IAtsChangeSet changes = AtsApiService.get().createChangeSet(getName());
      NewTaskSet newTaskSet = null;
      for (String msaTool : Arrays.asList("Backups", "Computers", "Network")) {

         Set<IAtsActionableItem> aias =
            AtsApiService.get().getActionableItemService().getActionableItems(Arrays.asList(msaTool));

         NewActionData data = atsApi.getActionService() //
            .createActionData(getClass().getSimpleName(), "Fix " + msaTool + " button", "description") //
            .andAis(aias).andChangeType(ChangeTypes.Problem).andPriority("4");
         NewActionData newActionData = atsApi.getActionService().createAction(data);
         if (newActionData.getRd().isErrors()) {
            XResultDataUI.report(newActionData.getRd(), getName());
         }
         IAtsAction action = newActionData.getActResult().getAtsAction();

         facilitiesGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
         teamArt = AtsApiService.get().getWorkItemService().getFirstTeam(action);
         newTaskSet = NewTaskSet.createWithData(teamArt, "createAction456", createdBy);
         NewTaskData newTaskData = newTaskSet.getTaskData();
         JaxAtsTask.create(newTaskData, "Task 1", createdBy, createdDate);
         JaxAtsTask.create(newTaskData, "Task 2", createdBy, createdDate);
      }
      changes.add(facilitiesGoal);
      changes.add(sawCodeGoal);
      changes.execute();

      newTaskSet = AtsApiService.get().getTaskService().createTasks(newTaskSet);
      Conditions.assertTrue(newTaskSet.getResults().isSuccess(), newTaskSet.getResults().toString());

      changes = AtsApiService.get().createChangeSet(getName());
      for (NewTaskData ntd : newTaskSet.getNewTaskDatas()) {
         for (JaxAtsTask task : ntd.getTasks()) {
            facilitiesGoal.addMember(task.getStoreObject());
            sawCodeGoal.addMember(task.getStoreObject());
            changes.add(task.getStoreObject());
         }
      }
      changes.execute();

      return teamArt;
   }

   private void createRemoveWorkflowButtonAction(GoalArtifact sawCodeGoal, GoalArtifact cisReqGoal) {

      Set<IAtsActionableItem> aias = AtsApiService.get().getActionableItemService().getActionableItems(
         Arrays.asList("SAW Code", "CIS Requirements"));

      NewActionData data = atsApi.getActionService() //
         .createActionData(getClass().getSimpleName(), "Remove Workflow button", "description") //
         .andAis(aias).andChangeType(ChangeTypes.Problem).andPriority("4");
      NewActionData newActionData = atsApi.getActionService().createAction(data);
      if (newActionData.getRd().isErrors()) {
         XResultDataUI.report(newActionData.getRd(), getName());
      }
      IAtsAction action = newActionData.getActResult().getAtsAction();

      sawCodeGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
      cisReqGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
   }

   private void createCheckSignalsAction(GoalArtifact sawCodeGoal, GoalArtifact cisReqGoal) {

      Set<IAtsActionableItem> aias = AtsApiService.get().getActionableItemService().getActionableItems(
         Arrays.asList("SAW Code", "CIS Requirements"));

      NewActionData data = atsApi.getActionService() //
         .createActionData(getClass().getSimpleName(), "Add Check Signals", "description") //
         .andAis(aias).andChangeType(ChangeTypes.Problem).andPriority("4");
      NewActionData newActionData = atsApi.getActionService().createAction(data);
      if (newActionData.getRd().isErrors()) {
         XResultDataUI.report(newActionData.getRd(), getName());
      }
      IAtsAction action = newActionData.getActResult().getAtsAction();

      sawCodeGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
      cisReqGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
   }

   private IAtsTeamWorkflow createFixThisModelAction(GoalArtifact sawCodeGoal) {

      Set<IAtsActionableItem> aias = atsApi.getActionableItemService().getActionableItems(Arrays.asList("SAW Code"));

      NewActionData data = atsApi.getActionService() //
         .createActionData(getClass().getSimpleName(), "Fix this model", "description") //
         .andAis(aias).andChangeType(ChangeTypes.Problem).andPriority("2");
      NewActionData newActionData = atsApi.getActionService().createAction(data);
      if (newActionData.getRd().isErrors()) {
         XResultDataUI.report(newActionData.getRd(), getName());
      }
      IAtsAction action = newActionData.getActResult().getAtsAction();

      IAtsChangeSet changes = atsApi.createChangeSet(getClass().getSimpleName());
      sawCodeGoal.addMember(AtsApiService.get().getWorkItemService().getFirstTeam(action).getStoreObject());
      IAtsTeamWorkflow teamWf = AtsApiService.get().getWorkItemService().getFirstTeam(action);
      IAtsPeerToPeerReview peerReviewArt = AtsApiService.get().getReviewService().createNewPeerToPeerReview(
         (TeamWorkFlowArtifact) teamWf.getStoreObject(), "New Review", "Implement", changes);
      sawCodeGoal.addMember(peerReviewArt.getStoreObject());
      return teamWf;
   }
}
