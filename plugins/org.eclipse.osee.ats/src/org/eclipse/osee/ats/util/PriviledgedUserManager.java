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
package org.eclipse.osee.ats.util;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.osee.ats.core.config.ActionableItemArtifact;
import org.eclipse.osee.ats.core.config.TeamDefinitionArtifact;
import org.eclipse.osee.ats.core.team.TeamWorkFlowArtifact;
import org.eclipse.osee.ats.core.util.AtsUtilCore;
import org.eclipse.osee.ats.core.workdef.RuleDefinitionOption;
import org.eclipse.osee.ats.core.workdef.StateDefinition;
import org.eclipse.osee.ats.core.workflow.AbstractWorkflowArtifact;
import org.eclipse.osee.ats.internal.AtsPlugin;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.User;
import org.eclipse.osee.framework.skynet.core.UserManager;

public class PriviledgedUserManager {

   public static Set<User> getPrivilegedUsers(AbstractWorkflowArtifact workflow) throws OseeCoreException {
      Set<User> users = new HashSet<User>();
      if (workflow.getParentTeamWorkflow() != null) {
         users.addAll(getPrivilegedUsers(workflow.getParentTeamWorkflow()));
      } else {
         for (ActionableItemArtifact aia : workflow.getParentTeamWorkflow().getActionableItemsDam().getActionableItems()) {
            for (TeamDefinitionArtifact teamDef : aia.getImpactedTeamDefs()) {
               addPriviledgedUsersUpTeamDefinitionTree(teamDef, users);
            }
         }
      }
      AbstractWorkflowArtifact parentSma = workflow.getParentAWA();
      if (parentSma != null) {
         users.addAll(parentSma.getStateMgr().getAssignees());
      }
      if (AtsUtilCore.isAtsAdmin()) {
         users.add(UserManager.getUser());
      }
      return users;
   }

   public static Set<User> getPrivilegedUsers(TeamWorkFlowArtifact teamArt) {
      Set<User> users = new HashSet<User>();
      try {
         addPriviledgedUsersUpTeamDefinitionTree(teamArt.getTeamDefinition(), users);

         StateDefinition stateDefinition = teamArt.getStateDefinition();

         // Add user if allowing privileged edit to all users
         if (!users.contains(UserManager.getUser()) && (stateDefinition.hasRule(RuleDefinitionOption.AllowPriviledgedEditToAll) || teamArt.getTeamDefinition().hasRule(
            RuleDefinitionOption.AllowPriviledgedEditToAll))) {
            users.add(UserManager.getUser());
         }

         // Add user if user is team member and rule exists
         boolean workPageToTeamMember = stateDefinition.hasRule(RuleDefinitionOption.AllowPriviledgedEditToTeamMember);
         boolean teamDefToTeamMember =
            teamArt.getTeamDefinition().hasRule(RuleDefinitionOption.AllowPriviledgedEditToTeamMember);
         if (!users.contains(UserManager.getUser()) && (workPageToTeamMember || teamDefToTeamMember) && //
         teamArt.getTeamDefinition().getMembers().contains(UserManager.getUser())) {
            users.add(UserManager.getUser());
         }

         // Add user if team member is originator and rule exists
         boolean workPageToMemberAndOriginator =
            stateDefinition.hasRule(RuleDefinitionOption.AllowPriviledgedEditToTeamMemberAndOriginator);
         boolean teamDefToMemberAndOriginator =
            teamArt.getTeamDefinition().hasRule(RuleDefinitionOption.AllowPriviledgedEditToTeamMemberAndOriginator);
         if (!users.contains(UserManager.getUser()) && (workPageToMemberAndOriginator || teamDefToMemberAndOriginator) && //
         teamArt.getCreatedBy().equals(UserManager.getUser()) && teamArt.getTeamDefinition().getMembers().contains(
            UserManager.getUser())) {
            users.add(UserManager.getUser());
         }

      } catch (Exception ex) {
         OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
      }
      return users;
   }

   protected static void addPriviledgedUsersUpTeamDefinitionTree(TeamDefinitionArtifact tda, Set<User> users) throws OseeCoreException {
      users.addAll(tda.getLeads());
      users.addAll(tda.getPrivilegedMembers());

      // Walk up tree to get other editors
      if (tda.getParent() instanceof TeamDefinitionArtifact) {
         addPriviledgedUsersUpTeamDefinitionTree((TeamDefinitionArtifact) tda.getParent(), users);
      }
   }

}
