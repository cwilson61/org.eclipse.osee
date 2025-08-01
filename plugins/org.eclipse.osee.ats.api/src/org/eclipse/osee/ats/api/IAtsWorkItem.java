/*********************************************************************
 * Copyright (c) 2013 Boeing
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

package org.eclipse.osee.ats.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.osee.ats.api.config.WorkType;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.user.AtsUser;
import org.eclipse.osee.ats.api.workdef.IStateToken;
import org.eclipse.osee.ats.api.workdef.StateType;
import org.eclipse.osee.ats.api.workdef.model.StateDefinition;
import org.eclipse.osee.ats.api.workdef.model.WorkDefinition;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsGoal;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.log.IAtsLog;
import org.eclipse.osee.ats.api.workflow.log.IAtsLogItem;
import org.eclipse.osee.ats.api.workflow.log.LogType;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.SystemUser;
import org.eclipse.osee.framework.jdk.core.type.NamedIdBase;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.jdk.core.util.Strings;

/**
 * @author Donald G. Dunne
 */
public interface IAtsWorkItem extends IAtsObject {

   String getAtsId();

   IAtsTeamWorkflow getParentTeamWorkflow();

   IAtsLog getLog();

   WorkDefinition getWorkDefinition();

   StateDefinition getStateDefinition();

   AtsUser getCreatedBy();

   Date getCreatedDate();

   AtsUser getCompletedBy();

   AtsUser getCancelledBy();

   String getCompletedFromState();

   String getCancelledFromState();

   String getArtifactTypeName();

   Date getCompletedDate();

   Date getCancelledDate();

   String getCancelledReason();

   IAtsAction getParentAction();

   IAtsWorkItem SENTINEL = createSentinel();

   default boolean isTeamWorkflow() {
      return isOfType(AtsArtifactTypes.TeamWorkflow);
   }

   default boolean isDecisionReview() {
      return isOfType(AtsArtifactTypes.DecisionReview);
   }

   default boolean isPeerReview() {
      return isOfType(AtsArtifactTypes.PeerToPeerReview);
   }

   default boolean isTask() {
      return isOfType(AtsArtifactTypes.Task);
   }

   default boolean isReview() {
      return isOfType(AtsArtifactTypes.AbstractReview);
   }

   default boolean isSprint() {
      return isOfType(AtsArtifactTypes.AgileSprint);
   }

   default boolean isBacklog() {
      return isOfType(AtsArtifactTypes.AgileBacklog);
   }

   default boolean isGoal() {
      return this instanceof IAtsGoal;
   }

   default boolean isInWork() {
      return getStateDefinition().isWorking();
   }

   default boolean isCompleted() {
      return getStateDefinition().isCompleted();
   }

   default boolean isCancelled() {
      return getStateDefinition().isCancelled();
   }

   default boolean isCompletedOrCancelled() {
      return isCompleted() || isCancelled();
   }

   default boolean hasAction() {
      return true;
   }

   void clearCaches();

   default boolean isInState(IStateToken... states) {
      for (IStateToken state : states) {
         if (getCurrentStateName().equals(state.getName())) {
            return true;
         }
      }
      return false;
   }

   default boolean isNotInState(IStateToken... states) {
      return !isInState(states);
   }

   @Override
   default public Collection<String> getTags() {
      return getAtsApi().getAttributeResolver().getAttributesToStringList(getStoreObject(),
         CoreAttributeTypes.StaticId);
   }

   default public void setTags(List<String> tags) {
      throw new UnsupportedOperationException("Invalid method for IAtsWorkItem; use IAtsChangeSet");
   }

   @Override
   default public boolean hasTag(String tag) {
      return getTags().contains(tag);
   }

   @Override
   default String toStringWithId() {
      String atsId = "";
      try {
         atsId = getAtsId();
      } catch (Exception ex) {
         atsId = "Exception: " + ex.getLocalizedMessage();
      }
      return String.format("[%s]-[%s]-[%s]", getName(), atsId, getIdString());
   }

   @Override
   default String toStringWithId(int nameTruncateLength) {
      return String.format("[%s]-[%s]-[%s]", Strings.truncate(getName(), nameTruncateLength, true), getAtsId(),
         getIdString());
   }

   default String toStringWithAtsId() {
      return String.format("[%s]-[%s]", getName(), getAtsId());
   }

   default boolean isChangeRequest() {
      return getArtifactType().inheritsFrom(
         AtsArtifactTypes.AbstractChangeRequestWorkflow) || getWorkDefinition().isChangeRequest();
   }

   default boolean isProblemReport() {
      return getArtifactType().inheritsFrom(
         AtsArtifactTypes.ProblemReportTeamWorkflow) || getWorkDefinition().isProblemReport();
   }

   public String getCurrentStateName();

   StateType getCurrentStateType();

   IStateToken getCurrentState();

   public List<AtsUser> getAssignees();

   public Collection<AtsUser> getImplementers();

   default Collection<AtsUser> getAssignees(IStateToken state) {
      Set<AtsUser> assignees = new HashSet<>();
      for (IAtsLogItem item : getLog().getLogItems(LogType.Assign)) {
         AtsUser user = getUserByUserId(item.getUserId());
         assignees.add(user);
      }
      return assignees;
   }

   AtsUser getUserByUserId(String userId);

   default String getAssigneesStr() {
      return Collections.toString("; ", getAssignees());
   }

   default public String getAssigneesStr(int length) {
      return Strings.truncate(Collections.toString("; ", getAssigneesStr()), length);
   }

   @SuppressWarnings("unlikely-arg-type")
   default public boolean isUnAssigned() {
      return getAssignees().contains(SystemUser.UnAssigned);
   }

   default String getLegacyId() {
      return getAtsApi().getAttributeResolver().getSoleAttributeValue(this, AtsAttributeTypes.LegacyPcrId, "");
   }

   default List<String> getPcrIds() {
      return getAtsApi().getAttributeResolver().getAttributesToStringList(this, AtsAttributeTypes.PcrId);
   }

   /**
    * @return ATS Id, Legacy PCR Id and all PCR Ids
    */
   default Collection<String> getIds() {
      List<String> ids = new ArrayList<>();
      ids.add(getAtsId());
      String legacyPcrId = getLegacyId();
      if (Strings.isValid(legacyPcrId)) {
         ids.add(legacyPcrId);
      }
      ids.addAll(getPcrIds());
      return ids;
   }

   default String getIdsStr() {
      return Collections.toString(", ", getIds());
   }

   /**
    * @return Legacy PCR Id and all PCR Ids
    */
   default Collection<String> getPcrIdsAll() {
      List<String> ids = new ArrayList<>();
      String legacyPcrId = getLegacyId();
      if (Strings.isValid(legacyPcrId)) {
         ids.add(legacyPcrId);
      }
      ids.addAll(getPcrIds());
      return ids;

   }

   public static IAtsWorkItem createSentinel() {
      final class IAtsWorkItemSentinel extends NamedIdBase implements IAtsWorkItem {

         @Override
         public ArtifactTypeToken getArtifactType() {
            return null;
         }

         @Override
         public List<AtsUser> getAssignees() {
            return null;
         }

         @Override
         public Collection<AtsUser> getImplementers() {
            return null;
         }

         @Override
         public String getAtsId() {
            return null;
         }

         @Override
         public IAtsTeamWorkflow getParentTeamWorkflow() {
            return null;
         }

         @Override
         public IAtsLog getLog() {
            return null;
         }

         @Override
         public WorkDefinition getWorkDefinition() {
            return null;
         }

         @Override
         public StateDefinition getStateDefinition() {
            return null;
         }

         @Override
         public AtsUser getCreatedBy() {
            return null;
         }

         @Override
         public Date getCreatedDate() {
            return null;
         }

         @Override
         public AtsUser getCompletedBy() {
            return null;
         }

         @Override
         public AtsUser getCancelledBy() {
            return null;
         }

         @Override
         public String getCompletedFromState() {
            return null;
         }

         @Override
         public String getCancelledFromState() {
            return null;
         }

         @Override
         public String getArtifactTypeName() {
            return null;
         }

         @Override
         public Date getCompletedDate() {
            return null;
         }

         @Override
         public Date getCancelledDate() {
            return null;
         }

         @Override
         public String getCancelledReason() {
            return null;
         }

         @Override
         public IAtsAction getParentAction() {
            return null;
         }

         @Override
         public void clearCaches() {
            // do nothing
         }

         @Override
         public AtsApi getAtsApi() {
            return null;
         }

         @Override
         public Collection<WorkType> getWorkTypes() {
            return null;
         }

         @Override
         public boolean isWorkType(WorkType workType) {
            return false;
         }

         @Override
         public Collection<String> getTags() {
            return null;
         }

         @Override
         public boolean hasTag(String tag) {
            return false;
         }

         @Override
         public boolean isSprint() {
            return false;
         }

         @Override
         public String getCurrentStateName() {
            return "";
         }

         @Override
         public StateType getCurrentStateType() {
            return null;
         }

         @Override
         public IStateToken getCurrentState() {
            return null;
         }

         @Override
         public AtsUser getUserByUserId(String userId) {
            return null;
         }

      }
      return new IAtsWorkItemSentinel();
   }

}