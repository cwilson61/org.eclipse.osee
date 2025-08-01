/*********************************************************************
 * Copyright (c) 2017 Boeing
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

package org.eclipse.osee.ats.api.workflow;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.TransactionId;
import org.eclipse.osee.framework.jdk.core.result.XResultData;

/**
 * @author Donald G. Dunne
 */
public class ActionResult {

   private IAtsAction action;
   private final List<IAtsTeamWorkflow> teamWfs;
   private XResultData results = new XResultData();
   private TransactionId transaction = TransactionId.SENTINEL;

   public ActionResult(IAtsAction action, List<IAtsTeamWorkflow> teamWfs) {
      this.action = action;
      this.teamWfs = teamWfs;
   }

   public IAtsAction getAction() {
      return action;
   }

   public void setAction(IAtsAction action) {
      this.action = action;
   }

   public ArtifactId getActionArt() {
      return action.getStoreObject();
   }

   public List<IAtsTeamWorkflow> getTeamWfs() {
      return teamWfs;
   }

   public List<ArtifactToken> getTeamWfArts() {
      List<ArtifactToken> arts = new LinkedList<>();
      for (IAtsTeamWorkflow team : teamWfs) {
         arts.add(team.getStoreObject());
      }
      return arts;
   }

   public IAtsTeamWorkflow getFirstTeam() {
      return teamWfs.iterator().next();
   }

   public XResultData getResults() {
      return results;
   }

   public void setResultData(XResultData results) {
      this.results = results;
   }

   public TransactionId getTransaction() {
      return transaction;
   }

   public void setTransaction(TransactionId transaction) {
      this.transaction = transaction;
   }
}
