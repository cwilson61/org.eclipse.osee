/*********************************************************************
 * Copyright (c) 2024 Boeing
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
package org.eclipse.osee.ats.core.agile.operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.agile.IAgileBacklog;
import org.eclipse.osee.ats.api.agile.IAgileItem;
import org.eclipse.osee.ats.api.agile.IAgileSprint;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.TransactionToken;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.util.ElapsedTime;
import org.eclipse.osee.framework.jdk.core.util.Lib;

/**
 * @author Donald G. Dunne
 */
public class BacklogOperations {

   private final AtsApi atsApi;

   public BacklogOperations(AtsApi atsApi) {
      this.atsApi = atsApi;
   }

   public XResultData sort(ArtifactToken backlogOrSprint) {
      XResultData rd = new XResultData();
      try {
         ElapsedTime time = new ElapsedTime("Sort Backlog", false, false);
         List<IAgileItem> sItems = new ArrayList<>();
         boolean isBacklog = false;
         if (atsApi.getAgileService().isBacklog(backlogOrSprint)) {
            IAgileBacklog bLog = atsApi.getAgileService().getAgileBacklog(backlogOrSprint);
            sItems.addAll(atsApi.getAgileService().getItems(bLog));
            isBacklog = true;
         } else {
            IAgileSprint sprint = atsApi.getAgileService().getAgileSprint(backlogOrSprint);
            sItems.addAll(atsApi.getAgileService().getItems(sprint));
         }

         time.logPoint("Start Sort");
         if (isBacklog) {
            Collections.sort(sItems, new NoSprintCompCancelledComparator(rd) //
               .thenComparing(new NoSprintCompCancelledComparator(rd) //
                  .thenComparing(new SprintComparator(rd)) //
                  .thenComparing(new CompCancelledComparator(rd)) //
               ));
         } else {
            sItems.sort(new CompCancelledComparator(rd));
         }
         time.logPoint("End Sort");

         List<ArtifactId> arts = new ArrayList<>();
         for (IAgileItem aItem : sItems) {
            arts.add(aItem.getStoreObject());
         }

         time.logPoint("Commit");
         IAtsChangeSet changes = atsApi.createChangeSet("Sort " + backlogOrSprint.getArtifactType().getName());
         if (isBacklog) {
            changes.setRelationsAndOrder(backlogOrSprint, AtsRelationTypes.Goal_Member, arts);
         } else {
            changes.setRelationsAndOrder(backlogOrSprint, AtsRelationTypes.AgileSprintToItem_AtsItem, arts);
         }
         time.logPoint("Commit End");
         TransactionToken transaction = changes.executeIfNeeded();
         rd.setTxId(transaction.getIdString());
         time.end();
      } catch (Exception ex) {
         rd.errorf("Error: Exception sorting goal %s: %s", backlogOrSprint.toStringWithId(), Lib.exceptionToString(ex));
      }
      return rd;
   }

   // Bubble completed/cancelled with no sprint to top
   private class NoSprintCompCancelledComparator implements Comparator<IAgileItem> {

      private final XResultData rd;

      public NoSprintCompCancelledComparator(XResultData rd) {
         this.rd = rd;
      }

      @Override
      public int compare(IAgileItem a1, IAgileItem a2) {
         try {
            IAgileSprint sprint1 = atsApi.getAgileService().getSprint(a1);
            IAgileSprint sprint2 = atsApi.getAgileService().getSprint(a2);

            // Must declare equal or next comparator will not be applied
            if (sprint1 == null && a1.getCurrentStateType().isCompletedOrCancelled() && //
               sprint2 == null && a2.getCurrentStateType().isCompletedOrCancelled()) {
               return 0;
            }
            if (sprint1 == null && a1.getCurrentStateType().isCompletedOrCancelled()) {
               return -1;
            } else if (sprint2 == null && a2.getCurrentStateType().isCompletedOrCancelled()) {
               return 1;
            }
         } catch (Exception ex) {
            rd.errorf("NoSprintCompCancelledComparator exception: %s\n", Lib.exceptionToString(ex));
         }
         return 0;
      }
   };

   private class SprintComparator implements Comparator<IAgileItem> {

      private final XResultData rd;

      public SprintComparator(XResultData rd) {
         this.rd = rd;
      }

      @Override
      public int compare(IAgileItem a1, IAgileItem a2) {
         try {
            IAgileSprint sprint1 = atsApi.getAgileService().getSprint(a1);
            IAgileSprint sprint2 = atsApi.getAgileService().getSprint(a2);

            if (sprint1 == null && sprint2 == null) {
               return 0;
            }
            // Sort lower if no sprint
            else if (sprint1 == null) {
               return 1;
            } else if (sprint2 == null) {
               return -1;
            }

            return sprint1.getName().compareTo(sprint2.getName());
         } catch (Exception ex) {
            rd.errorf("SprintComparator exception: %s\n", Lib.exceptionToString(ex));
         }
         return 0;
      }
   };

   private class CompCancelledComparator implements Comparator<IAgileItem> {

      private final XResultData rd;

      public CompCancelledComparator(XResultData rd) {
         this.rd = rd;
      }

      @Override
      public int compare(IAgileItem a1, IAgileItem a2) {
         try {
            IAgileSprint sprint1 = atsApi.getAgileService().getSprint(a1);
            IAgileSprint sprint2 = atsApi.getAgileService().getSprint(a2);

            if (sprint1 == null && sprint2 == null) {
               return 0;
            }

            if (!a1.getCurrentStateType().equals(a2.getCurrentStateType())) {
               if (a1.getCurrentStateType().isCancelled()) {
                  return -1;
               }
               if (a2.getCurrentStateType().isCancelled()) {
                  return 1;
               }
               if (a1.getCurrentStateType().isCompleted()) {
                  return -1;
               }
               if (a2.getCurrentStateType().isCompleted()) {
                  return 1;
               }
            }
            // Sort in reverse ordinal order so things that are further along are first.  So a2, then a1
            int comp =
               Integer.valueOf(a2.getStateDefinition().getOrdinal()).compareTo(a1.getStateDefinition().getOrdinal());
            if (comp != 0) {
               return comp;
            }
            return 0;
         } catch (Exception ex) {
            rd.errorf("CompCancelledComparator exception: %s\n", Lib.exceptionToString(ex));
         }
         return 0;
      }
   }

}
