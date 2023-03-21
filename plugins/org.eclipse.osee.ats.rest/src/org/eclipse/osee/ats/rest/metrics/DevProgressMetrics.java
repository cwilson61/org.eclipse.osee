/*********************************************************************
 * Copyright (c) 2022 Boeing
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
package org.eclipse.osee.ats.rest.metrics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.config.WorkType;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.version.IAtsVersion;
import org.eclipse.osee.ats.api.workdef.model.StateDefinition;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsTask;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.log.IAtsLogItem;
import org.eclipse.osee.framework.core.data.ArtifactReadable;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.io.xml.ExcelXmlWriter;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.search.QueryBuilder;

/**
 * @author Stephen J. Molaro
 */
public final class DevProgressMetrics implements StreamingOutput {
   private final OrcsApi orcsApi;
   private final AtsApi atsApi;
   private String programVersion;
   private final String targetVersion;
   private final Date startDate;
   private final Date endDate;
   private final boolean allTime;

   private ExcelXmlWriter writer;
   private final QueryBuilder query;

   Pattern UI_NAME = Pattern.compile("\\{.*\\}");
   Pattern UI_DELETED = Pattern.compile("^\\(Deleted\\)$");

   private final DevProgressItemId[] actionColumns = {
      DevProgressItemId.ACT,
      DevProgressItemId.ActionName,
      DevProgressItemId.Program,
      DevProgressItemId.Build,
      DevProgressItemId.Date,
      DevProgressItemId.Created,
      DevProgressItemId.WorkType,
      DevProgressItemId.TW,
      DevProgressItemId.State,
      DevProgressItemId.Analyze,
      DevProgressItemId.Authorize,
      DevProgressItemId.Implement,
      DevProgressItemId.Complete,
      DevProgressItemId.Cancelled,
      DevProgressItemId.TotalCount,
      DevProgressItemId.CompletedCount,
      DevProgressItemId.CancelledCount,
      DevProgressItemId.TotalAddModCount,
      DevProgressItemId.CompletedAddModCount,
      DevProgressItemId.CancelledAddModCount,
      DevProgressItemId.TotalDeletedCount,
      DevProgressItemId.CompletedDeletedCount,
      DevProgressItemId.CancelledDeletedCount};

   public DevProgressMetrics(OrcsApi orcsApi, AtsApi atsApi, String targetVersion, Date startDate, Date endDate, boolean allTime) {
      this.orcsApi = orcsApi;
      this.atsApi = atsApi;
      this.programVersion = null;
      this.targetVersion = targetVersion;
      this.startDate = startDate;
      this.endDate = endDate;
      this.allTime = allTime;
      this.query = orcsApi.getQueryFactory().fromBranch(atsApi.getAtsBranch());
   }

   @Override
   public void write(OutputStream output) {
      try {
         writer = new ExcelXmlWriter(new OutputStreamWriter(output, "UTF-8"));
         writeReport();
         writer.endWorkbook();
      } catch (Exception ex) {
         try {
            writer.endWorkbook();
         } catch (IOException ex1) {
            throw new WebApplicationException(ex1);
         }
         throw new WebApplicationException(ex);
      }
   }

   private void writeReport() throws IOException {
      Collection<IAtsTeamWorkflow> workflows = getDatedWorkflows();
      if (!workflows.isEmpty()) {
         Set<IAtsAction> actionableItems = getDatedActions(workflows);
         writer.startSheet("Non-Periodic Data", actionColumns.length);
         fillActionableData(actionableItems, actionColumns.length);
      }
   }

   private Collection<IAtsTeamWorkflow> getDatedWorkflows() {
      ArtifactReadable versionId = query.andIsOfType(AtsArtifactTypes.Version).andAttributeIs(CoreAttributeTypes.Name,
         targetVersion).asArtifact();
      IAtsVersion version = atsApi.getVersionService().getVersionById(versionId);
      Collection<IAtsTeamWorkflow> workflowArts = atsApi.getVersionService().getTargetedForTeamWorkflows(version);

      Collection<IAtsTeamWorkflow> datedWorkflowArts = new ArrayList<IAtsTeamWorkflow>();
      for (IAtsTeamWorkflow workflow : workflowArts) {
         try {
            if ((workflow.isWorkType(WorkType.Requirements) || workflow.isWorkType(
               WorkType.Code)) || workflow.isWorkType(WorkType.Test)) {
               if (allTime) {
                  datedWorkflowArts.add(workflow);
               } else if ((workflow.getCreatedDate().before(endDate))) {
                  if (allTime) {
                     if ((workflow.isCompleted() && workflow.getCompletedDate() != null && workflow.getCompletedDate().before(
                        startDate)) || (workflow.isCancelled() && workflow.getCancelledDate() != null && workflow.getCancelledDate().before(
                           startDate))) {
                        continue;
                     }
                  }
                  datedWorkflowArts.add(workflow);
               }

            }
         } catch (Exception ex) {
            continue;
         }

      }
      programVersion = atsApi.getRelationResolver().getRelatedOrSentinel(version,
         AtsRelationTypes.TeamDefinitionToVersion_TeamDefinition).getName();
      return datedWorkflowArts;
   }

   private Set<IAtsAction> getDatedActions(Collection<IAtsTeamWorkflow> workflows) {
      Set<IAtsAction> actionableItems = new HashSet<>();
      for (IAtsTeamWorkflow workflow : workflows) {
         actionableItems.add(workflow.getParentAction());
      }
      return actionableItems;
   }

   private void fillActionableData(Set<IAtsAction> actionableItems, int numColumns) throws IOException {
      Object[] buffer = new Object[numColumns];
      for (int i = 0; i < numColumns; ++i) {
         buffer[i] = actionColumns[i].getDisplayName();
      }
      writer.writeRow(buffer);

      for (IAtsAction actionItem : actionableItems) {
         buffer[0] = actionItem.getAtsId();
         buffer[1] = actionItem.getName();
         buffer[2] = programVersion;
         buffer[3] = targetVersion;
         Date createdDate = new Date();
         for (IAtsTeamWorkflow teamWorkflow : actionItem.getTeamWorkflows()) {
            if (teamWorkflow.getCreatedDate().before(createdDate)) {
               createdDate = teamWorkflow.getCreatedDate();
            }
         }
         buffer[5] = createdDate;
         fillTeamWfData(buffer, endDate, actionItem);
      }
      writer.endSheet();
   }

   private void fillTeamWfData(Object[] buffer, Date rowDate, IAtsAction actionItem) {
      buffer[4] = rowDate;
      IAtsTeamWorkflow requirementsWorkflow = IAtsTeamWorkflow.SENTINEL;
      IAtsTeamWorkflow codeWorkflow = IAtsTeamWorkflow.SENTINEL;
      IAtsTeamWorkflow testWorkflow = IAtsTeamWorkflow.SENTINEL;

      for (IAtsTeamWorkflow teamWorkflow : actionItem.getTeamWorkflows()) {
         if (teamWorkflow.isWorkType(WorkType.Requirements)) {
            if (requirementsWorkflow.equals(IAtsTeamWorkflow.SENTINEL)) {
               requirementsWorkflow = teamWorkflow;
            } else if (requirementsWorkflow.isCancelled() && !teamWorkflow.isCancelled()) {
               requirementsWorkflow = teamWorkflow;
            }
         } else if (teamWorkflow.isWorkType(WorkType.Code)) {
            if (codeWorkflow.equals(IAtsTeamWorkflow.SENTINEL)) {
               codeWorkflow = teamWorkflow;
            } else if (codeWorkflow.isCancelled() && !teamWorkflow.isCancelled()) {
               codeWorkflow = teamWorkflow;
            }
         } else if (teamWorkflow.isWorkType(WorkType.Test)) {
            if (testWorkflow.equals(IAtsTeamWorkflow.SENTINEL)) {
               testWorkflow = teamWorkflow;
            } else if (testWorkflow.isCancelled() && !teamWorkflow.isCancelled()) {
               testWorkflow = teamWorkflow;
            }
         }
      }
      int reqTasks = 0;
      float reqAddModTasks = 0;
      float reqDeletedTasks = 0;

      //Code Workflow Parsing
      if (!codeWorkflow.equals(IAtsTeamWorkflow.SENTINEL) && !getStateAtDate(codeWorkflow, rowDate).isEmpty()) {
         Collection<IAtsTask> tasks = getTaskList(codeWorkflow, rowDate);
         float[] deletedCounts = getDeletedTaskCount(codeWorkflow, rowDate);
         String stateAtDate = getStateAtDate(codeWorkflow, rowDate);
         buffer[6] = "Code";
         buffer[7] = codeWorkflow.getAtsId();
         buffer[8] = stateAtDate;
         if (stateAtDate.equals("None")) {
            buffer[9] = null;
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Analyze")) {
            buffer[9] = getStateStartDate(codeWorkflow, rowDate, "Analyze");
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Authorize")) {
            buffer[9] = getStateStartDate(codeWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(codeWorkflow, rowDate, "Authorize");
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Implement") || stateAtDate.equals("Test")) {
            buffer[9] = getStateStartDate(codeWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(codeWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(codeWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Completed")) {
            buffer[9] = getStateStartDate(codeWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(codeWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(codeWorkflow, rowDate, "Implement");
            buffer[12] = getStateStartDate(codeWorkflow, rowDate, "Completed");
            buffer[13] = null;
         } else if (stateAtDate.equals("Cancelled")) {
            buffer[9] = getStateStartDate(codeWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(codeWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(codeWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = getStateStartDate(codeWorkflow, rowDate, "Cancelled");
         }

         float tasksCompleted = getTaskCompleted(codeWorkflow, rowDate, tasks);
         int tasksCancelled = getTaskCancelled(codeWorkflow, rowDate, tasks);

         buffer[14] = tasks.size();
         buffer[15] = tasksCompleted;
         buffer[16] = tasksCancelled;

         buffer[17] = tasks.size() - deletedCounts[0];
         buffer[18] = tasksCompleted - deletedCounts[1];
         buffer[19] = tasksCancelled - deletedCounts[2];

         buffer[20] = deletedCounts[0];
         buffer[21] = deletedCounts[1];
         buffer[22] = deletedCounts[2];

         try {
            writer.writeRow(buffer);
         } catch (IOException ex) {
            OseeCoreException.wrapAndThrow(ex);
         }
      }

      //Test Workflow Parsing
      if (!testWorkflow.equals(IAtsTeamWorkflow.SENTINEL) && !getStateAtDate(testWorkflow, rowDate).isEmpty()) {
         Collection<IAtsTask> tasks = getTaskList(testWorkflow, rowDate);
         float[] deletedCounts = getDeletedTaskCount(testWorkflow, rowDate);
         String stateAtDate = getStateAtDate(testWorkflow, rowDate);
         buffer[6] = "Test";
         buffer[7] = testWorkflow.getAtsId();
         buffer[8] = stateAtDate;
         if (stateAtDate.equals("None")) {
            buffer[9] = null;
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Analyze")) {
            buffer[9] = getStateStartDate(testWorkflow, rowDate, "Analyze");
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Authorize")) {
            buffer[9] = getStateStartDate(testWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(testWorkflow, rowDate, "Authorize");
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Implement") || stateAtDate.equals("Test")) {
            buffer[9] = getStateStartDate(testWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(testWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(testWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Completed")) {
            buffer[9] = getStateStartDate(testWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(testWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(testWorkflow, rowDate, "Implement");
            buffer[12] = getStateStartDate(testWorkflow, rowDate, "Completed");
            buffer[13] = null;
         } else if (stateAtDate.equals("Cancelled")) {
            buffer[9] = getStateStartDate(testWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(testWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(testWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = getStateStartDate(testWorkflow, rowDate, "Cancelled");
         }

         reqTasks = tasks.size();
         reqAddModTasks = tasks.size() - deletedCounts[0];
         reqDeletedTasks = deletedCounts[0];

         float tasksCompleted = getTaskCompleted(testWorkflow, rowDate, tasks);
         int tasksCancelled = getTaskCancelled(testWorkflow, rowDate, tasks);

         buffer[14] = tasks.size();
         buffer[15] = tasksCompleted;
         buffer[16] = tasksCancelled;

         buffer[17] = tasks.size() - deletedCounts[0];
         buffer[18] = tasksCompleted - deletedCounts[1];
         buffer[19] = tasksCancelled - deletedCounts[2];

         buffer[20] = deletedCounts[0];
         buffer[21] = deletedCounts[1];
         buffer[22] = deletedCounts[2];

         try {
            writer.writeRow(buffer);
         } catch (IOException ex) {
            OseeCoreException.wrapAndThrow(ex);
         }
      }

      //Requirements Workflow Parsing
      if (!requirementsWorkflow.equals(
         IAtsTeamWorkflow.SENTINEL) && !getStateAtDate(requirementsWorkflow, rowDate).isEmpty()) {
         String stateAtDate = getStateAtDate(requirementsWorkflow, rowDate);

         buffer[6] = "Requirements";
         buffer[7] = requirementsWorkflow.getAtsId();
         buffer[8] = stateAtDate;

         if (stateAtDate.equals("None")) {
            buffer[9] = null;
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Analyze")) {
            buffer[9] = getStateStartDate(requirementsWorkflow, rowDate, "Analyze");
            buffer[10] = null;
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Authorize")) {
            buffer[9] = getStateStartDate(requirementsWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(requirementsWorkflow, rowDate, "Authorize");
            buffer[11] = null;
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Implement") || stateAtDate.equals("Test")) {
            buffer[9] = getStateStartDate(requirementsWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(requirementsWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(requirementsWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = null;
         } else if (stateAtDate.equals("Completed")) {
            buffer[9] = getStateStartDate(requirementsWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(requirementsWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(requirementsWorkflow, rowDate, "Implement");
            buffer[12] = getStateStartDate(requirementsWorkflow, rowDate, "Completed");
            buffer[13] = null;
         } else if (stateAtDate.equals("Cancelled")) {
            buffer[9] = getStateStartDate(requirementsWorkflow, rowDate, "Analyze");
            buffer[10] = getStateStartDate(requirementsWorkflow, rowDate, "Authorize");
            buffer[11] = getStateStartDate(requirementsWorkflow, rowDate, "Implement");
            buffer[12] = null;
            buffer[13] = getStateStartDate(requirementsWorkflow, rowDate, "Cancelled");
         }

         buffer[14] = reqTasks;
         buffer[17] = reqAddModTasks;
         buffer[20] = reqDeletedTasks;
         if (stateAtDate.contains("Complete")) {
            buffer[15] = reqTasks;
            buffer[16] = 0;
            buffer[18] = reqAddModTasks;
            buffer[19] = 0;
            buffer[21] = reqDeletedTasks;
            buffer[22] = 0;
         } else if (stateAtDate.equals("Cancelled")) {
            buffer[15] = 0;
            buffer[16] = reqTasks;
            buffer[18] = 0;
            buffer[19] = reqAddModTasks;
            buffer[21] = 0;
            buffer[22] = reqDeletedTasks;
         } else {
            buffer[15] = 0;
            buffer[16] = 0;
            buffer[18] = 0;
            buffer[19] = 0;
            buffer[21] = 0;
            buffer[22] = 0;
         }

         try {
            writer.writeRow(buffer);
         } catch (IOException ex) {
            OseeCoreException.wrapAndThrow(ex);
         }
      }

   }

   private String getStateAtDate(IAtsWorkItem teamWorkflow, Date iterationDate) {
      List<String> stateList = new ArrayList<String>();
      try {
         for (StateDefinition state : teamWorkflow.getWorkDefinition().getStates()) {
            stateList.add(state.getName());
         }
      } catch (Exception ex) {
         //DO NOTHING
      }

      String stateName = "";
      Date stateStartDate = new GregorianCalendar(1916, 7, 15).getTime();

      try {
         for (String visitedState : teamWorkflow.getStateMgr().getVisitedStateNames()) {
            if (stateList.contains(visitedState)) {
               Date newStateStartDate = teamWorkflow.getStateMgr().getStateStartedData(visitedState).getDate();
               if (newStateStartDate.before(iterationDate) && newStateStartDate.after(stateStartDate)) {
                  stateName = visitedState;
                  stateStartDate = newStateStartDate;
               }
            }
         }
      } catch (Exception ex) {
         //Do Nothing
      }
      return stateName;
   }

   private Date getStateStartDate(IAtsWorkItem teamWorkflow, Date iterationDate, String stateName) {
      try {
         IAtsLogItem stateStartedData = teamWorkflow.getStateMgr().getStateStartedData(stateName);
         if (stateStartedData.getDate().before(iterationDate)) {
            return stateStartedData.getDate();
         }
      } catch (Exception ex) {
         //Do Nothing
      }
      return null;
   }

   private Collection<IAtsTask> getTaskList(IAtsTeamWorkflow teamWorkflow, Date iterationDate) {
      Collection<IAtsTask> tasks = new ArrayList<IAtsTask>();
      Collection<String> taskUINames = new ArrayList<String>();
      for (IAtsTask task : atsApi.getTaskService().getTasks(teamWorkflow)) {
         Matcher m = UI_NAME.matcher(task.getName());
         if (m.find()) {
            String taskUIName = m.group();
            if (task.getCreatedDate().before(iterationDate) && !taskUINames.contains(taskUIName)) {
               taskUINames.add(taskUIName);
               tasks.add(task);
            }
         }
      }
      return tasks;
   }

   private float[] getDeletedTaskCount(IAtsTeamWorkflow teamWorkflow, Date iterationDate) {
      float[] deletedCounts = new float[3];
      float deletedCount = 0;
      float deletedCompleteCount = 0;
      float deletedCancelledCount = 0;
      for (IAtsTask task : atsApi.getTaskService().getTasks(teamWorkflow)) {
         Matcher m = UI_DELETED.matcher(task.getName());
         if (m.find()) {
            try {
               StateDefinition state = stateNameToDefinition(task, iterationDate);

               if (task.getCreatedDate().before(iterationDate)) {
                  deletedCount++;
                  if (task.isCompleted() && task.getCompletedDate().before(iterationDate)) {
                     deletedCompleteCount++;
                  } else if (task.isCancelled() && task.getCancelledDate().before(iterationDate)) {
                     deletedCancelledCount++;
                  } else if (state.getRecommendedPercentComplete() != null && state.getRecommendedPercentComplete() > 0) {
                     deletedCompleteCount += (state.getRecommendedPercentComplete() / 100);
                  }
               }
            } catch (Exception Ex) {
               //Do Nothing
            }
         }
      }
      deletedCounts[0] = deletedCount;
      deletedCounts[1] = deletedCompleteCount;
      deletedCounts[2] = deletedCancelledCount;
      return deletedCounts;
   }

   private float getTaskCompleted(IAtsTeamWorkflow teamWorkflow, Date iterationDate, Collection<IAtsTask> tasks) {
      float completedTasks = 0;
      for (IAtsTask task : tasks) {
         try {
            StateDefinition state = stateNameToDefinition(task, iterationDate);
            if ((task.isCompleted() && task.getCompletedDate().before(iterationDate)) || state.getName().equals(
               "No_Change")) {
               completedTasks++;
            } else if (state.getRecommendedPercentComplete() != null && state.getRecommendedPercentComplete() > 0 && !state.getName().equals(
               "Cancelled")) {
               completedTasks += (state.getRecommendedPercentComplete() / 100.0);
            }
         } catch (Exception Ex) {
            //Do Nothing
         }
      }
      return completedTasks;
   }

   private StateDefinition stateNameToDefinition(IAtsWorkItem item, Date iterationDate) {
      for (StateDefinition state : item.getWorkDefinition().getStates()) {
         if (state.getName().equals(getStateAtDate(item, iterationDate))) {
            return state;
         }
      }
      return null;
   }

   private int getTaskCancelled(IAtsTeamWorkflow teamWorkflow, Date iterationDate, Collection<IAtsTask> tasks) {
      Collection<IAtsTask> iterationTasks = new ArrayList<IAtsTask>();
      for (IAtsTask task : tasks) {
         try {
            if (task.isCancelled() && task.getCancelledDate().before(iterationDate)) {
               iterationTasks.add(task);
            }
         } catch (Exception Ex) {
            //Do Nothing
         }
      }
      return iterationTasks.size();
   }
}