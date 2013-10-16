/*******************************************************************************
 * Copyright (c) 2011 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.core.util;

import java.util.Collection;
import org.eclipse.osee.ats.api.IAtsObject;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.review.IAtsAbstractReview;
import org.eclipse.osee.ats.api.workdef.IAtsStateDefinition;
import org.eclipse.osee.ats.api.workdef.IStateToken;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsTask;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.core.AtsCore;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;

/**
 * @author Donald G. Dunne
 */
public class PercentCompleteTotalUtil {

   /**
    * Return Percent Complete on all things (including children SMAs) for this SMA<br>
    * <br>
    * percent = all state's percents / number of states (minus completed/canceled)
    */
   public static int getPercentCompleteTotal(IAtsObject atsObject) throws OseeCoreException {
      int percent = 0;
      if (atsObject instanceof IAtsWorkItem) {
         IAtsWorkItem workItem = (IAtsWorkItem) atsObject;
         if (AtsCore.getWorkDefService().isStateWeightingEnabled(workItem.getWorkDefinition())) {
            // Calculate total percent using configured weighting
            for (IAtsStateDefinition stateDef : workItem.getWorkDefinition().getStates()) {
               if (!stateDef.getStateType().isCompletedState() && !stateDef.getStateType().isCancelledState()) {
                  double stateWeightInt = stateDef.getStateWeight();
                  double weight = stateWeightInt / 100;
                  int percentCompleteForState = getPercentCompleteSMAStateTotal(workItem, stateDef);
                  percent += weight * percentCompleteForState;
               }
            }
         } else {
            percent = getPercentCompleteSMASinglePercent(workItem);
            if (percent == 0) {
               if (AtsCore.getWorkItemService().getWorkData(workItem).isCompletedOrCancelled()) {
                  percent = 100;
               } else if (isAnyStateHavePercentEntered(workItem)) {
                  int numStates = 0;
                  for (IAtsStateDefinition state : workItem.getWorkDefinition().getStates()) {
                     if (!state.getStateType().isCompletedState() && !state.getStateType().isCancelledState()) {
                        percent += getPercentCompleteSMAStateTotal(workItem, state);
                        numStates++;
                     }
                  }
                  if (numStates > 0) {
                     percent = percent / numStates;
                  }
               }
            }
         }
      }
      return percent;
   }

   private static boolean isAnyStateHavePercentEntered(IAtsWorkItem workItem) throws OseeCoreException {
      for (String stateName : workItem.getStateMgr().getVisitedStateNames()) {
         if (workItem.getStateMgr().getPercentComplete(stateName) != 0) {
            return true;
         }
      }
      return false;
   }

   /**
    * Add percent represented by percent attribute, percent for reviews and tasks divided by number of objects.
    */
   private static int getPercentCompleteSMASinglePercent(IAtsObject atsObject) throws OseeCoreException {
      int percent = 0;
      if (atsObject instanceof IAtsWorkItem) {
         IAtsWorkItem workItem = (IAtsWorkItem) atsObject;
         int numObjects = 1;
         percent = AtsCore.getAttrResolver().getSoleAttributeValue(workItem, AtsAttributeTypes.PercentComplete, 0);
         if (workItem instanceof IAtsTeamWorkflow) {
            for (IAtsAbstractReview revArt : AtsCore.getWorkItemService().getReviews((IAtsTeamWorkflow) workItem)) {
               percent += getPercentCompleteTotal(revArt);
               numObjects++;
            }
         }
         if (workItem instanceof IAtsTeamWorkflow) {
            for (IAtsTask taskArt : AtsCore.getWorkItemService().getTasks(((IAtsTeamWorkflow) workItem))) {
               percent += getPercentCompleteTotal(taskArt);
               numObjects++;
            }
         }
         if (percent > 0) {
            if (numObjects > 0) {
               percent = percent / numObjects;
            }
         }
      }
      return percent;
   }

   /**
    * Return Percent Complete on all things (including children SMAs) related to stateName. Total Percent for state,
    * tasks and reviews / 1 + # Tasks + # Reviews
    */
   public static int getPercentCompleteSMAStateTotal(IAtsObject atsObject, IStateToken state) throws OseeCoreException {
      return getStateMetricsData(atsObject, state).getResultingPercent();
   }

   private static StateMetricsData getStateMetricsData(IAtsObject atsObject, IStateToken teamState) throws OseeCoreException {
      if (!(atsObject instanceof IAtsWorkItem)) {
         return null;
      }
      IAtsWorkItem workItem = (IAtsWorkItem) atsObject;
      // Add percent and bump objects 1 for state percent
      int percent = getPercentCompleteSMAState(workItem, teamState);
      int numObjects = 1; // the state itself is one object

      // Add percent for each task and bump objects for each task
      if (workItem instanceof IAtsTeamWorkflow) {
         Collection<IAtsTask> tasks =
            AtsCore.getWorkItemService().getTasks((IAtsTeamWorkflow) workItem, teamState);
         for (IAtsTask taskArt : tasks) {
            percent += getPercentCompleteTotal(taskArt);
         }
         numObjects += tasks.size();
      }

      // Add percent for each review and bump objects for each review
      if (workItem instanceof IAtsTeamWorkflow) {
         Collection<IAtsAbstractReview> reviews =
            AtsCore.getWorkItemService().getReviews((IAtsTeamWorkflow) workItem, teamState);
         for (IAtsAbstractReview reviewArt : reviews) {
            percent += getPercentCompleteTotal(reviewArt);
         }
         numObjects += reviews.size();
      }

      return new StateMetricsData(percent, numObjects);
   }
   private static class StateMetricsData {
      public int numObjects = 0;
      public int percent = 0;

      public StateMetricsData(int percent, int numObjects) {
         this.numObjects = numObjects;
         this.percent = percent;
      }

      public int getResultingPercent() {
         return percent / numObjects;
      }

      @Override
      public String toString() {
         return "Percent: " + getResultingPercent() + "  NumObjs: " + numObjects + "  Total Percent: " + percent;
      }
   }

   /**
    * Return Percent Complete working ONLY the current state (not children SMAs)
    */
   public static int getPercentCompleteSMAState(IAtsObject atsObject) throws OseeCoreException {
      int percent = 0;
      if (atsObject instanceof IAtsAction) {
         IAtsAction action = (IAtsAction) atsObject;
         if (AtsCore.getWorkItemService().getTeams(action).size() == 1) {
            return getPercentCompleteSMAState(AtsCore.getWorkItemService().getFirstTeam(action));
         } else {
            int items = 0;
            for (IAtsTeamWorkflow team : AtsCore.getWorkItemService().getTeams(action)) {
               if (!AtsCore.getWorkItemService().getWorkData(team).isCancelled()) {
                  percent += getPercentCompleteSMAState(team);
                  items++;
               }
            }
            if (items > 0) {
               Double rollPercent = new Double(percent) / items;
               percent = rollPercent.intValue();
            }
         }
      } else if (atsObject instanceof IAtsWorkItem) {
         return getPercentCompleteSMAState(atsObject,
            AtsCore.getWorkItemService().getCurrentState((IAtsWorkItem) atsObject));
      }
      return percent;
   }

   /**
    * Return Percent Complete working ONLY the SMA stateName (not children SMAs)
    */
   public static int getPercentCompleteSMAState(IAtsObject atsObject, IStateToken state) throws OseeCoreException {
      if (atsObject instanceof IAtsWorkItem) {
         IAtsWorkItem workItem = (IAtsWorkItem) atsObject;
         return workItem.getStateMgr().getPercentComplete(state.getName());
      }
      return 0;
   }
}
