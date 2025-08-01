/*********************************************************************
 * Copyright (c) 2015 Boeing
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

package org.eclipse.osee.ats.rest.internal.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.eclipse.core.runtime.Assert;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.agile.AgileEndpointApi;
import org.eclipse.osee.ats.api.agile.AgileWriterResult;
import org.eclipse.osee.ats.api.agile.IAgileProgram;
import org.eclipse.osee.ats.api.agile.IAgileProgramBacklog;
import org.eclipse.osee.ats.api.agile.IAgileProgramBacklogItem;
import org.eclipse.osee.ats.api.agile.IAgileProgramFeature;
import org.eclipse.osee.ats.api.agile.IAgileSprint;
import org.eclipse.osee.ats.api.agile.IAgileStory;
import org.eclipse.osee.ats.api.agile.JaxAgileItem;
import org.eclipse.osee.ats.api.agile.JaxAgileProgram;
import org.eclipse.osee.ats.api.agile.JaxAgileProgramBacklog;
import org.eclipse.osee.ats.api.agile.JaxAgileProgramBacklogItem;
import org.eclipse.osee.ats.api.agile.JaxAgileProgramFeature;
import org.eclipse.osee.ats.api.agile.JaxAgileStory;
import org.eclipse.osee.ats.api.agile.JaxNewAgileBacklog;
import org.eclipse.osee.ats.api.agile.JaxNewAgileFeatureGroup;
import org.eclipse.osee.ats.api.agile.JaxNewAgileSprint;
import org.eclipse.osee.ats.api.agile.JaxNewAgileTeam;
import org.eclipse.osee.ats.api.config.JaxAtsObject;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.demo.DemoArtifactToken;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.WorkItemType;
import org.eclipse.osee.ats.api.workflow.transition.TransitionData;
import org.eclipse.osee.ats.api.workflow.transition.TransitionOption;
import org.eclipse.osee.ats.api.workflow.transition.TransitionResults;
import org.eclipse.osee.ats.core.workflow.state.TeamState;
import org.eclipse.osee.ats.rest.internal.agile.AgileEndpointImpl;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.enums.CoreRelationTypes;
import org.eclipse.osee.framework.core.enums.DemoUsers;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.type.OseeStateException;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.jdk.core.util.DateUtil;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.orcs.OrcsApi;

/**
 * @author Donald G. Dunne
 */
public class Pdd93CreateDemoAgile extends AbstractPopulateDemoDatabase {

   private AgileEndpointApi agileEp;
   private final OrcsApi orcsApi;

   public Pdd93CreateDemoAgile(XResultData rd, AtsApi atsApi, OrcsApi orcsApi) {
      super(rd, atsApi);
      this.orcsApi = orcsApi;
   }

   @Override
   public void run() {
      rd.logf("Running [%s]...\n", getClass().getSimpleName());

      agileEp = new AgileEndpointImpl(atsApi, null, orcsApi.getJdbcService(), orcsApi);

      // create agile program
      IAgileProgram aProgram = createAgileProgram();

      // create two agile teams and add to program
      createSawAgileTeam(aProgram);
      createCisAgileTeam(aProgram);
      createProgramBacklogAndFeaturesAndStories(aProgram);
      createAgileStandAloneTeam();
   }

   private void createProgramBacklogAndFeaturesAndStories(IAgileProgram aProgram) {

      JaxAgileProgramBacklog jaxProgramBacklog =
         JaxAgileProgramBacklog.construct(aProgram, DemoArtifactToken.RD_Program_Backlog);
      IAgileProgramBacklog programBacklog =
         atsApi.getAgileService().createAgileProgramBacklog(aProgram, jaxProgramBacklog);

      if (programBacklog != null) {
         JaxAgileProgramBacklogItem backlogItem1 =
            JaxAgileProgramBacklogItem.construct(programBacklog, DemoArtifactToken.RD_Program_Backlog_Item_1);
         IAgileProgramBacklogItem item =
            atsApi.getAgileService().createAgileProgramBacklogItem(programBacklog, backlogItem1);

         JaxAgileProgramBacklogItem item2 =
            JaxAgileProgramBacklogItem.construct(programBacklog, DemoArtifactToken.RD_Program_Backlog_Item_2);
         atsApi.getAgileService().createAgileProgramBacklogItem(programBacklog, item2);
         JaxAgileProgramBacklogItem item3 =
            JaxAgileProgramBacklogItem.construct(programBacklog, DemoArtifactToken.RD_Program_Backlog_Item_3);
         atsApi.getAgileService().createAgileProgramBacklogItem(programBacklog, item3);

         JaxAgileProgramFeature jaxFeature =
            JaxAgileProgramFeature.construct(backlogItem1, DemoArtifactToken.RD_Program_Feature_Robot_Nav);
         IAgileProgramFeature feature = atsApi.getAgileService().createAgileProgramFeature(item, jaxFeature);

         //    if (feature != null) {
         JaxAgileStory jaxStory1 = JaxAgileStory.construct(feature, DemoArtifactToken.RD_Robot_Nav_Story_1);
         IAgileStory story1 = atsApi.getAgileService().createAgileStory(feature, jaxStory1);

         JaxAgileStory jaxStory2 = JaxAgileStory.construct(feature, DemoArtifactToken.RD_Robot_Nav_Story_2);
         IAgileStory story2 = atsApi.getAgileService().createAgileStory(feature, jaxStory2);

         JaxAgileStory jaxStory3 = JaxAgileStory.construct(feature, DemoArtifactToken.RD_Robot_Nav_Story_3);
         IAgileStory story3 = atsApi.getAgileService().createAgileStory(feature, jaxStory3);

         IAtsChangeSet changes = atsApi.createChangeSet("Add Agile Items to Stories");

         IAtsTeamWorkflow codeWf = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_Commited_Code_TeamWf);
         atsApi.getAgileService().setAgileStory(codeWf, story1, changes);
         IAtsTeamWorkflow testWf = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_Commited_Test_TeamWf);
         atsApi.getAgileService().setAgileStory(testWf, story1, changes);
         IAtsTeamWorkflow reqWf = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_Commited_Req_TeamWf);

         // relate story to agile team and sprint
         ArtifactToken story1Art = atsApi.getQueryService().getArtifact(story1);
         ArtifactToken agileTeamArt = atsApi.getQueryService().getArtifact(DemoArtifactToken.SAW_Agile_Team);
         changes.relate(story1Art, AtsRelationTypes.AgileStoryToAgileTeam_AgileTeam, agileTeamArt);
         ArtifactToken sprint2Art = atsApi.getQueryService().getArtifact(DemoArtifactToken.SAW_Sprint_2);
         changes.relate(story1Art, AtsRelationTypes.AgileStoryToSprint_AgileSprint, sprint2Art);

         ArtifactToken story2Art = atsApi.getQueryService().getArtifact(story2);
         changes.relate(story2Art, AtsRelationTypes.AgileStoryToAgileTeam_AgileTeam, agileTeamArt);
         changes.relate(story2Art, AtsRelationTypes.AgileStoryToSprint_AgileSprint, sprint2Art);

         ArtifactToken story3Art = atsApi.getQueryService().getArtifact(story3);
         changes.relate(story3Art, AtsRelationTypes.AgileStoryToAgileTeam_AgileTeam, agileTeamArt);
         changes.relate(story3Art, AtsRelationTypes.AgileStoryToSprint_AgileSprint, sprint2Art);

         atsApi.getAgileService().setAgileStory(reqWf, story1, changes);
         changes.relate(story1Art, AtsRelationTypes.AgileStoryToItem_TeamWorkflow, reqWf);

         IAtsTeamWorkflow codeWf2 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_UnCommited_Code_TeamWf);
         atsApi.getAgileService().setAgileStory(codeWf2, story2, changes);
         IAtsTeamWorkflow testWf2 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_UnCommited_Test_TeamWf);
         atsApi.getAgileService().setAgileStory(testWf2, story2, changes);
         IAtsTeamWorkflow reqWf2 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_UnCommited_Req_TeamWf);
         atsApi.getAgileService().setAgileStory(reqWf2, story2, changes);

         IAtsTeamWorkflow codeWf3 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_NoBranch_Code_TeamWf);
         atsApi.getAgileService().setAgileStory(codeWf3, story3, changes);
         IAtsTeamWorkflow testWf3 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_NoBranch_Test_TeamWf);
         atsApi.getAgileService().setAgileStory(testWf3, story3, changes);
         IAtsTeamWorkflow reqWf3 = atsApi.getQueryService().getTeamWf(DemoArtifactToken.SAW_NoBranch_Req_TeamWf);
         atsApi.getAgileService().setAgileStory(reqWf3, story3, changes);
         changes.execute();

         jaxFeature = JaxAgileProgramFeature.construct(backlogItem1, DemoArtifactToken.RD_Program_Feature_Robot_Voice);
         atsApi.getAgileService().createAgileProgramFeature(item, jaxFeature);
      }
   }

   private void createAgileStandAloneTeam() {
      long teamId = 999L;

      // Create Facilities Team
      JaxNewAgileTeam newTeam = new JaxNewAgileTeam();
      newTeam.setName("Facilities Team");
      newTeam.setId(teamId);
      try (Response response = agileEp.createTeam(newTeam)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response.getStatus());
      }

      // Create Backlog
      JaxNewAgileBacklog backlog = new JaxNewAgileBacklog();
      backlog.setName("Facilities Backlog");
      backlog.setId(9991L);
      backlog.setTeamId(newTeam.getId());
      try (Response response = agileEp.createBacklog(teamId, backlog)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response.getStatus());
      }
   }

   private void createCisAgileTeam(IAgileProgram aProgram) {
      // Create CIS Team
      JaxNewAgileTeam newTeam = new JaxNewAgileTeam();
      newTeam.setName(DemoArtifactToken.CIS_Agile_Team.getName());
      newTeam.setId(DemoArtifactToken.CIS_Agile_Team.getId());
      newTeam.setProgramId(aProgram.getIdString());

      Response response = null;
      try {
         response = agileEp.createTeam(newTeam);
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response.getStatus());

         IAtsChangeSet changes = atsApi.createChangeSet("Config Agile Team with points attr type");
         ArtifactToken sawAgileTeam = atsApi.getQueryService().getArtifact(DemoArtifactToken.CIS_Agile_Team);
         changes.setSoleAttributeValue(sawAgileTeam, AtsAttributeTypes.PointsAttributeType,
            AtsAttributeTypes.Points.getName());
         changes.execute();

         // Create Backlog
         JaxNewAgileBacklog backlog = new JaxNewAgileBacklog();
         backlog.setName(DemoArtifactToken.CIS_Backlog.getName());
         backlog.setId(DemoArtifactToken.CIS_Backlog.getId());
         backlog.setTeamId(newTeam.getId());
         agileEp.createBacklog(DemoArtifactToken.CIS_Agile_Team.getId(), backlog);
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response.getStatus());
      } finally {

         if (response != null) {
            response.close();
         }
      }
   }

   private IAgileProgram createAgileProgram() {
      JaxAgileProgram jProgram = new JaxAgileProgram();
      jProgram.setName(DemoArtifactToken.RD_Agile_Program.getName());
      jProgram.setId(DemoArtifactToken.RD_Agile_Program.getId());
      IAgileProgram aProgram = atsApi.getAgileService().createAgileProgram(jProgram);
      return aProgram;
   }

   private void createSawAgileTeam(IAgileProgram aProgram) {

      // Create Team
      JaxNewAgileTeam newTeam = getJaxAgileTeam();
      newTeam.setProgramId(aProgram.getIdString());

      try (Response response = agileEp.createTeam(newTeam)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response.getStatus());
      }

      IAtsChangeSet changes = atsApi.createChangeSet("Config Agile Team with points attr type");
      ArtifactToken sawAgileTeam = atsApi.getQueryService().getArtifact(DemoArtifactToken.SAW_Agile_Team);
      changes.setSoleAttributeValue(sawAgileTeam, AtsAttributeTypes.PointsAttributeType,
         AtsAttributeTypes.Points.getName());
      changes.execute();

      // Assign ATS Team to Agile Team
      changes = atsApi.createChangeSet("Config Agile Team with points attr type");
      ArtifactToken agileTeam = atsApi.getQueryService().getArtifact(newTeam.getId());
      for (ArtifactToken tok : Arrays.asList(DemoArtifactToken.SAW_SW, DemoArtifactToken.SAW_HW,
         DemoArtifactToken.SAW_Code, DemoArtifactToken.SAW_Test, DemoArtifactToken.SAW_SW_Design,
         DemoArtifactToken.SAW_Requirements)) {
         ArtifactToken sawTeamDef = atsApi.getQueryService().getArtifact(tok);
         Conditions.assertNotNull(sawTeamDef, "sawCodeArt");
         changes.relate(agileTeam, AtsRelationTypes.AgileTeamToAtsTeam_AtsTeam, sawTeamDef);
      }

      // Add team members to agile team
      ArtifactToken joeUser = atsApi.getQueryService().getArtifact(DemoUsers.Joe_Smith);
      changes.relate(agileTeam, CoreRelationTypes.Users_User, joeUser);
      ArtifactToken kayUser = atsApi.getQueryService().getArtifact(DemoUsers.Kay_Jones);
      changes.relate(agileTeam, CoreRelationTypes.Users_User, kayUser);
      changes.execute();

      // Create Backlog
      JaxNewAgileBacklog backlog = getBacklog();
      try (Response response2 = agileEp.createBacklog(DemoArtifactToken.SAW_Agile_Team.getId(), backlog)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == response2.getStatus());
      }

      // Add items to backlog
      Collection<IAtsWorkItem> items = new ArrayList<>();
      for (IAtsWorkItem workItem : atsApi.getQueryService().createQuery(WorkItemType.TeamWorkflow).isOfType(
         AtsArtifactTypes.DemoCodeTeamWorkflow, AtsArtifactTypes.DemoReqTeamWorkflow,
         AtsArtifactTypes.DemoTestTeamWorkflow).getItems()) {
         if (!workItem.getName().equals(DemoArtifactToken.SAW_NotesAnnotations_Code_TeamWf.getName())) {
            items.add(workItem);
         }
      }
      Assert.isTrue(items.size() > 0);

      JaxAgileItem item = new JaxAgileItem();
      item.setBacklogId(backlog.getId());
      item.setSetBacklog(true);
      for (IAtsWorkItem workItem : items) {
         item.getIds().add(workItem.getId());
      }
      AgileWriterResult result = agileEp.updateItems(item);
      Conditions.assertFalse(result.getResults().isErrors(), result.getResults().toString());

      // Set backlog as user_defined member order
      changes = atsApi.createChangeSet("Set Backlog Order");
      ArtifactToken backlogArt = atsApi.getQueryService().getArtifact(backlog.getId());
      changes.setRelationsAndOrder(backlogArt, AtsRelationTypes.Goal_Member,
         atsApi.getRelationResolver().getRelated(backlogArt, AtsRelationTypes.Goal_Member));
      changes.executeIfNeeded();

      // Create Sprints
      JaxNewAgileSprint sprint1 = newSprint(DemoArtifactToken.SAW_Sprint_1);
      try (Response sprint1Response = agileEp.createSprint(sprint1.getTeamId(), sprint1)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == sprint1Response.getStatus());
      }
      JaxNewAgileSprint sprint2 = newSprint(DemoArtifactToken.SAW_Sprint_2);
      try (Response sprint2Response = agileEp.createSprint(sprint2.getTeamId(), sprint2)) {
         Assert.isTrue(Response.Status.CREATED.getStatusCode() == sprint2Response.getStatus());
      }

      // Add items to Sprint
      JaxAgileItem completedItems = new JaxAgileItem();
      completedItems.setSprintId(DemoArtifactToken.SAW_Sprint_1.getId());
      completedItems.setSetSprint(true);

      JaxAgileItem inworkItems = new JaxAgileItem();
      inworkItems.setSprintId(DemoArtifactToken.SAW_Sprint_2.getId());
      inworkItems.setSetSprint(true);

      for (IAtsWorkItem workItem : items) {
         if (workItem.getCurrentStateType().isCompleted()) {
            completedItems.getIds().add(workItem.getId());
         } else {
            inworkItems.getIds().add(workItem.getId());
         }
      }
      result = agileEp.updateItems(inworkItems);
      Conditions.assertFalse(result.getResults().isErrors(), result.getResults().toString());
      result = agileEp.updateItems(completedItems);
      Conditions.assertFalse(result.getResults().isErrors(), result.getResults().toString());

      changes = atsApi.createChangeSet("Set relation order for Sprint 1");
      ArtifactToken sprint1Art = atsApi.getQueryService().getArtifact(sprint1.getId());
      changes.setRelationsAndOrder(sprint1Art, AtsRelationTypes.AgileSprintToItem_AtsItem,
         atsApi.getRelationResolver().getRelated(sprint1Art, AtsRelationTypes.AgileSprintToItem_AtsItem));
      changes.executeIfNeeded();

      changes = atsApi.createChangeSet("Set relation order for Sprint 2");
      ArtifactToken sprint2Art = atsApi.getQueryService().getArtifact(sprint2.getId());
      changes.setRelationsAndOrder(sprint2Art, AtsRelationTypes.AgileSprintToItem_AtsItem,
         atsApi.getRelationResolver().getRelated(sprint2Art, AtsRelationTypes.AgileSprintToItem_AtsItem));
      changes.executeIfNeeded();

      // Transition First Sprint to completed
      IAtsWorkItem sprint = atsApi.getQueryService().createQuery(WorkItemType.WorkItem).andIds(
         DemoArtifactToken.SAW_Sprint_1.getId()).getItems().iterator().next();
      TransitionData transData = new TransitionData("Transition Agile Stprint", Arrays.asList(sprint),
         TeamState.Completed.getName(), null, null, null, TransitionOption.OverrideAssigneeCheck);
      TransitionResults results = atsApi.getWorkItemService().transition(transData);
      if (results.isErrors()) {
         throw new OseeStateException("Exception transitioning sprint: %s", results.toString());
      }

      // Create Feature Groups
      for (String name : Arrays.asList("Communications", "UI", "Documentation", "Framework")) {
         JaxNewAgileFeatureGroup featureGroup = newFeatureGroup(name);
         try (Response featGroup = agileEp.createFeatureGroup(DemoArtifactToken.SAW_Program.getId(), featureGroup)) {
            Assert.isTrue(Response.Status.CREATED.getStatusCode() == featGroup.getStatus());
         }
      }
      setupSprint2ForBurndown(DemoArtifactToken.SAW_Sprint_2.getId());
   }

   private void setupSprint2ForBurndown(long secondSprintId) {

      // Transition First Sprint to completed
      IAtsWorkItem sprint = atsApi.getQueryService().createQuery(WorkItemType.WorkItem).andIds(
         secondSprintId).getItems().iterator().next();
      IAtsChangeSet changes = atsApi.createChangeSet("Setup Sprint 2 for Burndown");

      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, -5);
      Date holiday1 = null, holiday2 = null;
      // backup start date till hit weekday
      while (!DateUtil.isWeekDay(cal)) {
         cal.add(Calendar.DAY_OF_YEAR, -1);
      }
      changes.setSoleAttributeValue(sprint, AtsAttributeTypes.StartDate, cal.getTime());
      int x = 1;
      int holidayDayNum = 5;
      // count up 20 weekdays and set 2 weekday holidays
      for (x = 1; x <= 21; x++) {
         cal.add(Calendar.DAY_OF_YEAR, 1);
         while (!DateUtil.isWeekDay(cal)) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
         }
         if (x == holidayDayNum) {
            holiday1 = cal.getTime();
         } else if (x == holidayDayNum + 1) {
            holiday2 = cal.getTime();
         }
      }
      changes.setSoleAttributeValue(sprint, AtsAttributeTypes.EndDate, cal.getTime());
      changes.setSoleAttributeValue(sprint, AtsAttributeTypes.UnplannedPoints, 45);
      changes.setSoleAttributeValue(sprint, AtsAttributeTypes.PlannedPoints, 200);
      changes.addAttribute(sprint, AtsAttributeTypes.Holiday, holiday1);
      changes.addAttribute(sprint, AtsAttributeTypes.Holiday, holiday2);
      changes.execute();

      // set sprint data on sprint items
      ArtifactToken agileTeamArt = atsApi.getRelationResolver().getRelated(sprint,
         AtsRelationTypes.AgileTeamToSprint_AgileTeam).iterator().next();

      setSprintItemData(agileTeamArt.getId(), (IAgileSprint) sprint);
   }

   private void setSprintItemData(Long teamId, IAgileSprint sprint) {
      List<SprintItemData> datas = new LinkedList<>();
      datas.add(
         new SprintItemData("Sprint Order", "Title", "Points", "Unplanned Work", "Feature Group", "CreatedDate"));
      datas.add(
         new SprintItemData("1", "Button W doesn't work on Situation Page", "8", " ", "Communications", "10/03/2016"));
      datas.add(new SprintItemData("2", "Can't load Diagram Tree", "4", "Unplanned Work", "Framework", "10/03/2016"));
      datas.add(new SprintItemData("3", "Can't see the Graph View", "8", "Unplanned Work", "Framework", "10/03/2016"));
      datas.add(new SprintItemData("4", "Problem in Diagram Tree", "40", " ", "Framework", "10/03/2016"));
      datas.add(new SprintItemData("5", "Problem with the Graph View", "8", " ", "Communications", "10/03/2016"));
      datas.add(new SprintItemData("6", DemoArtifactToken.SAW_COMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "2",
         "Unplanned Work", "Framework", "10/03/2016"));
      datas.add(new SprintItemData("7", DemoArtifactToken.SAW_COMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "8", " ",
         "Framework", "10/03/2016"));
      datas.add(new SprintItemData("8", DemoArtifactToken.SAW_COMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "5", " ", "UI",
         "10/03/2016"));
      datas.add(new SprintItemData("9", DemoArtifactToken.SAW_NO_BRANCH_REQT_CHANGES_FOR_DIAGRAM_VIEW, "8", " ",
         "Communications", "10/03/2016"));
      datas.add(new SprintItemData("10", DemoArtifactToken.SAW_NO_BRANCH_REQT_CHANGES_FOR_DIAGRAM_VIEW, "40", " ",
         "Documentation", "10/03/2016"));
      datas.add(new SprintItemData("11", DemoArtifactToken.SAW_NO_BRANCH_REQT_CHANGES_FOR_DIAGRAM_VIEW, "8", " ",
         "Documentation", "10/03/2016"));
      datas.add(new SprintItemData("12", DemoArtifactToken.SAW_UNCOMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "1", " ",
         "Communications", "10/03/2016"));
      datas.add(new SprintItemData("13", DemoArtifactToken.SAW_UNCOMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "6", " ",
         "Documentation", "10/03/2016"));
      datas.add(new SprintItemData("14", DemoArtifactToken.SAW_UNCOMMITTED_REQT_CHANGES_FOR_DIAGRAM_VIEW, "2", " ",
         "Communications", "10/03/2016"));
      datas.add(new SprintItemData("15", DemoArtifactToken.SAW_UnCommitedConflicted_Req_TeamWf.getName(), "1", " ",
         "Communications", "10/03/2016"));
      datas.add(new SprintItemData("16", "Workaround for Graph View for SAW_Bld_2", "1", "Unplanned Work",
         "Communications", "10/03/2016"));
      datas.add(new SprintItemData("17", "Workaround for Graph View for SAW_Bld_3", "2", "Unplanned Work",
         "Communications", "10/03/2016"));

      int x = 1;
      List<JaxAtsObject> jaxObjects = agileEp.getSprintItemsAsJax(teamId, sprint.getId()).getAtsObjects();
      for (JaxAtsObject jaxWorkItem : jaxObjects) {
         SprintItemData data = getSprintData(datas, x++, jaxWorkItem);
         if (data == null) {
            throw new OseeStateException("data is null for " + jaxWorkItem);
         }
         String featureGroupName = data.getFeature();
         if (Strings.isValid(featureGroupName)) {
            Response res = agileEp.addFeatureGroup(jaxWorkItem.getId(), featureGroupName);
            res.close();
         }
         String unPlannedStr = data.getUnPlanned();
         boolean unPlanned = false;
         if (Strings.isValid(unPlannedStr)) {
            if (unPlannedStr.toLowerCase().contains("un")) {
               unPlanned = true;
            }
         }

         try (Response res2 = agileEp.setUnPlanned(jaxWorkItem.getId(), unPlanned)) {
            String points = data.getPoints();
            if (Strings.isValid(points)) {
               Response res3 = agileEp.setPoints(jaxWorkItem.getId(), points);
               res3.close();
            }
         }
      }
   }

   private SprintItemData getSprintData(List<SprintItemData> datas, int i, JaxAtsObject workItem) {
      for (SprintItemData data : datas) {
         if (data.getOrder().equals(String.valueOf(i)) && data.getTitle().equals(workItem.getName())) {
            return data;
         }
      }
      return null;
   }

   private JaxNewAgileBacklog getBacklog() {
      JaxNewAgileBacklog backlog = new JaxNewAgileBacklog();
      backlog.setName(DemoArtifactToken.SAW_Backlog.getName());
      backlog.setId(DemoArtifactToken.SAW_Backlog.getId());
      backlog.setTeamId(DemoArtifactToken.SAW_Agile_Team.getId());
      return backlog;
   }

   private JaxNewAgileFeatureGroup newFeatureGroup(String name) {
      JaxNewAgileFeatureGroup group = new JaxNewAgileFeatureGroup();
      group.setName(name);
      group.setTeamId(DemoArtifactToken.SAW_Agile_Team.getId());
      group.setId(Lib.generateArtifactIdAsInt());
      return group;
   }

   private JaxNewAgileSprint newSprint(ArtifactToken token) {
      JaxNewAgileSprint newSprint = new JaxNewAgileSprint();
      newSprint.setName(token.getName());
      newSprint.setId(token.getId());
      newSprint.setTeamId(DemoArtifactToken.SAW_Agile_Team.getId());
      return newSprint;
   }

   private JaxNewAgileTeam getJaxAgileTeam() {
      JaxNewAgileTeam newTeam = new JaxNewAgileTeam();
      newTeam.setName(DemoArtifactToken.SAW_Agile_Team.getName());
      newTeam.setId(DemoArtifactToken.SAW_Agile_Team.getId());
      return newTeam;
   }

}
