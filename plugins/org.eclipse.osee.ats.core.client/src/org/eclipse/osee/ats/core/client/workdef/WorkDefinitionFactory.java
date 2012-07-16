/*******************************************************************************
 * Copyright (c) 2010 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.core.client.workdef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.team.IAtsTeamDefinition;
import org.eclipse.osee.ats.api.workdef.IAtsWorkDefinition;
import org.eclipse.osee.ats.api.workdef.IAtsWorkDefinitionService;
import org.eclipse.osee.ats.core.client.internal.Activator;
import org.eclipse.osee.ats.core.client.task.TaskArtifact;
import org.eclipse.osee.ats.core.client.team.TeamWorkFlowArtifact;
import org.eclipse.osee.ats.core.client.team.TeamWorkflowProviders;
import org.eclipse.osee.ats.core.client.util.AtsUtilCore;
import org.eclipse.osee.ats.core.client.util.WorkflowManagerCore;
import org.eclipse.osee.ats.core.client.workflow.AbstractWorkflowArtifact;
import org.eclipse.osee.ats.core.client.workflow.ITeamWorkflowProvider;
import org.eclipse.osee.ats.core.workdef.AtsWorkDefinitionService;
import org.eclipse.osee.ats.core.workdef.WorkDefinitionMatch;
import org.eclipse.osee.framework.core.enums.DeletionFlag;
import org.eclipse.osee.framework.core.exception.MultipleAttributesExist;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.util.XResultData;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;
import org.eclipse.osee.framework.skynet.core.utility.ElapsedTime;

/**
 * @author Donald G. Dunne
 */
public class WorkDefinitionFactory {

   // Cache the WorkDefinition used for each AbstractWorkflowId so don't have to recompute each time
   private static final Map<Integer, WorkDefinitionMatch> awaArtIdToWorkDefinition =
      new HashMap<Integer, WorkDefinitionMatch>();
   // Cache the WorkDefinition object for each WorkDefinition id so don't have to reload
   // This grows as WorkDefinitions are requested/loaded
   private static final Map<String, WorkDefinitionMatch> workDefIdToWorkDefintion =
      new HashMap<String, WorkDefinitionMatch>();
   public static final String TaskWorkflowDefinitionId = "WorkDef_Task_Default";
   public static final String GoalWorkflowDefinitionId = "WorkDef_Goal";
   public static final String PeerToPeerWorkflowDefinitionId = "WorkDef_Review_PeerToPeer";
   public static final String DecisionWorkflowDefinitionId = "WorkDef_Review_Decision";
   public static final String TeamWorkflowDefaultDefinitionId = "WorkDef_Team_Default";

   public static void clearCaches() {
      awaArtIdToWorkDefinition.clear();
      workDefIdToWorkDefintion.clear();
   }

   public static void addWorkDefinition(IAtsWorkDefinition workDef) {
      WorkDefinitionMatch match =
         new WorkDefinitionMatch(workDef.getId(), "programatically added via WorkDefinitionFactory.addWorkDefinition");
      match.setWorkDefinition(workDef);
      workDefIdToWorkDefintion.put(workDef.getName(), match);
   }

   public static void removeWorkDefinition(IAtsWorkDefinition workDef) {
      workDefIdToWorkDefintion.remove(workDef.getName());
   }

   public static WorkDefinitionMatch getWorkDefinition(Artifact artifact) throws OseeCoreException {
      if (!awaArtIdToWorkDefinition.containsKey(artifact.getArtId())) {
         WorkDefinitionMatch match = getWorkDefinitionNew(artifact);
         awaArtIdToWorkDefinition.put(artifact.getArtId(), match);
      }
      return awaArtIdToWorkDefinition.get(artifact.getArtId());
   }

   public static Collection<IAtsWorkDefinition> getLoadedWorkDefinitions() {
      List<IAtsWorkDefinition> workDefs = new ArrayList<IAtsWorkDefinition>();
      for (WorkDefinitionMatch match : workDefIdToWorkDefintion.values()) {
         if (match.getWorkDefinition() != null) {
            workDefs.add(match.getWorkDefinition());
         }
      }
      return workDefs;
   }

   public static WorkDefinitionMatch getWorkDefinition(String id) {
      if (!workDefIdToWorkDefintion.containsKey(id)) {
         WorkDefinitionMatch match = new WorkDefinitionMatch();
         // Try to get from new DSL provider if configured to use it
         if (!match.isMatched()) {
            try {
               XResultData resultData = new XResultData(false);
               IAtsWorkDefinitionService service = AtsWorkDefinitionService.getService();
               if (service == null) {
                  throw new IllegalStateException("ATS Work Definition Service is not found.");
               }
               IAtsWorkDefinition workDef = service.getWorkDef(id, resultData);
               if (workDef != null) {
                  match.setWorkDefinition(workDef);
                  if (!resultData.isEmpty()) {
                     match.addTrace((String.format("from DSL provider loaded id [%s] [%s]", id, resultData.toString())));
                  } else {
                     match.addTrace((String.format("from DSL provider loaded id [%s]", id)));
                  }
               }
            } catch (Exception ex) {
               return new WorkDefinitionMatch(null, ex.getLocalizedMessage());
            }
         }
         if (match.isMatched()) {
            OseeLog.logf(Activator.class, Level.INFO, "Loaded Work Definition [%s]", match);
            workDefIdToWorkDefintion.put(id, match);
         } else {
            OseeLog.logf(Activator.class, Level.INFO, "Unable to load Work Definition [%s]", id);
         }
      }
      WorkDefinitionMatch match = workDefIdToWorkDefintion.get(id);
      if (match == null) {
         match = new WorkDefinitionMatch();
      }
      return match;
   }

   private static WorkDefinitionMatch getWorkDefinitionFromArtifactsAttributeValue(IAtsTeamDefinition teamDef) {
      // If this artifact specifies it's own workflow definition, use it
      String workFlowDefId = teamDef.getWorkflowDefinition();
      if (Strings.isValid(workFlowDefId)) {
         WorkDefinitionMatch match = getWorkDefinition(workFlowDefId);
         if (match.isMatched()) {
            match.addTrace(String.format("from artifact [%s] for id [%s]", teamDef, workFlowDefId));
            return match;
         }
      }
      return new WorkDefinitionMatch();
   }

   private static WorkDefinitionMatch getTaskWorkDefinitionFromArtifactsAttributeValue(IAtsTeamDefinition teamDef) {
      // If this artifact specifies it's own workflow definition, use it
      String workFlowDefId = teamDef.getRelatedTaskWorkDefinition();
      if (Strings.isValid(workFlowDefId)) {
         WorkDefinitionMatch match = getWorkDefinition(workFlowDefId);
         if (match.isMatched()) {
            match.addTrace(String.format("from artifact [%s] for id [%s]", teamDef, workFlowDefId));
            return match;
         }
      }
      return new WorkDefinitionMatch();
   }

   private static WorkDefinitionMatch getWorkDefinitionFromArtifactsAttributeValue(Artifact artifact) throws OseeCoreException {
      // If this artifact specifies it's own workflow definition, use it
      String workFlowDefId = artifact.getSoleAttributeValue(AtsAttributeTypes.WorkflowDefinition, null);
      if (Strings.isValid(workFlowDefId)) {
         WorkDefinitionMatch match = getWorkDefinition(workFlowDefId);
         if (match.isMatched()) {
            match.addTrace(String.format("from artifact [%s] for id [%s]", artifact, workFlowDefId));
            return match;
         }
      }
      return new WorkDefinitionMatch();
   }

   private static WorkDefinitionMatch getTaskWorkDefinitionFromArtifactsAttributeValue(Artifact artifact) throws OseeCoreException {
      // If this artifact specifies it's own workflow definition, use it
      String workFlowDefId = artifact.getSoleAttributeValue(AtsAttributeTypes.RelatedTaskWorkDefinition, null);
      if (Strings.isValid(workFlowDefId)) {
         WorkDefinitionMatch match = getWorkDefinition(workFlowDefId);
         if (match.isMatched()) {
            match.addTrace(String.format("from artifact [%s] for id [%s]", artifact, workFlowDefId));
            return match;
         }
      }
      return new WorkDefinitionMatch();
   }

   /**
    * Look at team def's attribute for Work Definition setting, otherwise, walk up team tree for setting
    */
   protected static WorkDefinitionMatch getWorkDefinitionFromTeamDefinitionAttributeInherited(IAtsTeamDefinition teamDef) throws OseeCoreException {
      WorkDefinitionMatch match = getWorkDefinitionFromArtifactsAttributeValue(teamDef);
      if (match.isMatched()) {
         return match;
      }
      IAtsTeamDefinition parentArt = teamDef.getParentTeamDef();
      if (parentArt != null) {
         return getWorkDefinitionFromTeamDefinitionAttributeInherited(parentArt);
      }
      return new WorkDefinitionMatch();
   }

   public static WorkDefinitionMatch getWorkDefinitionForTask(TaskArtifact taskArt) throws OseeCoreException {
      TeamWorkFlowArtifact teamWf = (TeamWorkFlowArtifact) WorkflowManagerCore.getParentTeamWorkflow(taskArt);
      return getWorkDefinitionForTask(teamWf, taskArt);
   }

   /**
    * Return the WorkDefinition that would be assigned to a new Task. This is not necessarily the actual WorkDefinition
    * used because it can be overridden once the Task artifact is created.
    */
   public static WorkDefinitionMatch getWorkDefinitionForTaskNotYetCreated(TeamWorkFlowArtifact teamWf) throws OseeCoreException {
      return getWorkDefinitionForTask(teamWf, null);
   }

   /**
    * @param teamWf
    * @param taskArt - if null, returned WorkDefinition will be proposed; else returned will be actual
    */
   private static WorkDefinitionMatch getWorkDefinitionForTask(TeamWorkFlowArtifact teamWf, TaskArtifact taskArt) throws OseeCoreException {
      WorkDefinitionMatch match = new WorkDefinitionMatch();
      for (ITeamWorkflowProvider provider : TeamWorkflowProviders.getAtsTeamWorkflowProviders()) {
         String workFlowDefId = provider.getRelatedTaskWorkflowDefinitionId(teamWf);
         if (Strings.isValid(workFlowDefId)) {
            match = WorkDefinitionFactory.getWorkDefinition(workFlowDefId);
            match.addTrace((String.format("from provider [%s] for id [%s]", provider.getClass().getSimpleName(),
               workFlowDefId)));
            break;
         }
      }
      if (!match.isMatched() && taskArt != null) {
         // If task specifies it's own workflow id, use it
         match = getWorkDefinitionFromArtifactsAttributeValue(taskArt);
      }
      if (!match.isMatched()) {
         // Else If parent SMA has a related task definition workflow id specified, use it
         WorkDefinitionMatch match2 = getTaskWorkDefinitionFromArtifactsAttributeValue(teamWf);
         if (match2.isMatched()) {
            match2.addTrace(String.format("from task parent SMA [%s]", teamWf));
            match = match2;
         }
      }
      if (!match.isMatched()) {
         // Else If parent TeamWorkflow's IAtsTeamDefinition has a related task definition workflow id, use it
         match = getTaskWorkDefinitionFromArtifactsAttributeValue(teamWf.getTeamDefinition());
      }
      if (!match.isMatched()) {
         match = getWorkDefinition(TaskWorkflowDefinitionId);
         match.addTrace("WorkDefinitionFactory - Default Task");
      }
      return match;
   }

   private static WorkDefinitionMatch getWorkDefinitionNew(Artifact artifact) throws OseeCoreException {
      WorkDefinitionMatch match = new WorkDefinitionMatch();
      if (artifact instanceof TaskArtifact) {
         match = getWorkDefinitionForTask((TaskArtifact) artifact);
      }
      if (!match.isMatched() && artifact instanceof AbstractWorkflowArtifact) {
         AbstractWorkflowArtifact aba = (AbstractWorkflowArtifact) artifact;
         // Check extensions for definition handling
         for (ITeamWorkflowProvider provider : TeamWorkflowProviders.getAtsTeamWorkflowProviders()) {
            String workFlowDefId = provider.getWorkflowDefinitionId(aba);
            if (Strings.isValid(workFlowDefId)) {
               match = WorkDefinitionFactory.getWorkDefinition(workFlowDefId);
            }
         }
         if (!match.isMatched()) {
            // If this artifact specifies it's own workflow definition, use it
            match = getWorkDefinitionFromArtifactsAttributeValue(artifact);
            if (!match.isMatched()) {
               // Otherwise, use workflow defined by attribute of WorkflowDefinition
               // Note: This is new.  Old TeamDefs got workflow off relation
               if (artifact.isOfType(AtsArtifactTypes.TeamWorkflow)) {
                  IAtsTeamDefinition teamDef = ((TeamWorkFlowArtifact) artifact).getTeamDefinition();
                  match = getWorkDefinitionFromTeamDefinitionAttributeInherited(teamDef);
               } else if (artifact.isOfType(AtsArtifactTypes.Goal)) {
                  match = getWorkDefinition(GoalWorkflowDefinitionId);
                  match.addTrace("WorkDefinitionFactory - GoalWorkflowDefinitionId");
               } else if (artifact.isOfType(AtsArtifactTypes.PeerToPeerReview)) {
                  match = getWorkDefinition(PeerToPeerWorkflowDefinitionId);
                  match.addTrace("WorkDefinitionFactory - PeerToPeerWorkflowDefinitionId");
               } else if (artifact.isOfType(AtsArtifactTypes.DecisionReview)) {
                  match = getWorkDefinition(DecisionWorkflowDefinitionId);
                  match.addTrace("WorkDefinitionFactory - DecisionWorkflowDefinitionId");
               }
            }
         }
      }
      return match;
   }

   public static Set<String> errorDisplayed = new HashSet<String>();

   public static Set<IAtsWorkDefinition> loadAllDefinitions() throws OseeCoreException {
      ElapsedTime time = new ElapsedTime("  - Load All Work Definitions");

      Set<IAtsWorkDefinition> workDefs = new HashSet<IAtsWorkDefinition>();
      // This load is faster than loading each by artifact type
      for (Artifact art : ArtifactQuery.getArtifactListFromType(AtsArtifactTypes.WorkDefinition,
         AtsUtilCore.getAtsBranch(), DeletionFlag.EXCLUDE_DELETED)) {
         try {
            XResultData resultData = new XResultData(false);
            IAtsWorkDefinition workDef = AtsWorkDefinitionService.getService().getWorkDef(art.getName(), resultData);
            if (!resultData.isEmpty()) {
               OseeLog.log(Activator.class, Level.SEVERE, resultData.toString());
            }
            if (workDef == null) {
               OseeLog.logf(Activator.class, Level.SEVERE, "Null WorkDef loaded for Artifact [%s]",
                  art.toStringWithId());
            } else {
               workDefs.add(workDef);
            }
         } catch (Exception ex) {
            OseeLog.log(Activator.class, Level.SEVERE,
               "Error loading WorkDefinition from artifact " + art.toStringWithId(), ex);
         }
      }
      time.end();
      return workDefs;
   }

   public static boolean isTaskOverridingItsWorkDefinition(TaskArtifact taskArt) throws MultipleAttributesExist, OseeCoreException {
      return taskArt.getSoleAttributeValueAsString(AtsAttributeTypes.WorkflowDefinition, null) != null;
   }

   public static IAtsWorkDefinition copyWorkDefinition(String newName, IAtsWorkDefinition workDef, XResultData resultData) {
      return org.eclipse.osee.ats.core.workdef.AtsWorkDefinitionService.getService().copyWorkDefinition(newName,
         workDef, resultData, AtsWorkDefinitionStore.getInstance().getAttributeResolver(),
         AtsWorkDefinitionStore.getInstance().getUserResolver());
   }
}
