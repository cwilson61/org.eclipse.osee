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
package org.eclipse.osee.ats.core.client.action;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.core.client.team.TeamWorkFlowArtifact;
import org.eclipse.osee.framework.core.data.IArtifactType;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.model.Branch;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactFactory;

/**
 * @author Donald G. Dunne
 */
public class ActionArtifact extends Artifact {

   public ActionArtifact(ArtifactFactory parentFactory, String guid, String humanReadableId, Branch branch, IArtifactType artifactType) throws OseeCoreException {
      super(parentFactory, guid, humanReadableId, branch, artifactType);
   }

   public Set<IAtsActionableItem> getActionableItems() throws OseeCoreException {
      Set<IAtsActionableItem> aias = new HashSet<IAtsActionableItem>();
      for (TeamWorkFlowArtifact team : getTeams()) {
         aias.addAll(team.getActionableItemsDam().getActionableItems());
      }
      return aias;
   }

   public Collection<TeamWorkFlowArtifact> getTeams() throws OseeCoreException {
      return getRelatedArtifactsUnSorted(AtsRelationTypes.ActionToWorkflow_WorkFlow, TeamWorkFlowArtifact.class);
   }

   public TeamWorkFlowArtifact getFirstTeam() throws OseeCoreException {
      if (getRelatedArtifactsCount(AtsRelationTypes.ActionToWorkflow_WorkFlow) > 0) {
         return getTeams().iterator().next();
      }
      return null;
   }

}
