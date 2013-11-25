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
package org.eclipse.osee.ats.core.workflow;

import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.user.IAtsUser;
import org.eclipse.osee.ats.api.workdef.IAtsStateDefinition;
import org.eclipse.osee.ats.api.workdef.RuleDefinitionOption;
import org.eclipse.osee.ats.core.AtsCore;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;

/**
 * @author Donald G. Dunne
 */
public class WorkflowManagerCore {

   public static boolean isEditable(IAtsUser user, IAtsWorkItem workItem, IAtsStateDefinition stateDef, boolean privilegedEditEnabled) throws OseeCoreException {
      return isEditable(workItem, stateDef, privilegedEditEnabled, user, AtsCore.getUserService().isAtsAdmin(user));
   }

   public static boolean isEditable(IAtsWorkItem workItem, IAtsStateDefinition stateDef, boolean privilegedEditEnabled, IAtsUser currentUser, boolean isAtsAdmin) throws OseeCoreException {
      // must be current state
      return (stateDef == null || AtsCore.getWorkDefService().isInState(workItem, stateDef)) &&
      // and one of these
      //
      // page is define to allow anyone to edit
      (workItem.getStateDefinition().hasRule(RuleDefinitionOption.AllowEditToAll.name()) ||
      // team definition has allowed anyone to edit
         AtsCore.getWorkDefService().teamDefHasRule(workItem, RuleDefinitionOption.AllowEditToAll) ||
         // privileged edit mode is on
         privilegedEditEnabled ||
         // current user is assigned
         workItem.getStateMgr().getAssignees().contains(currentUser) ||
      // current user is ats admin
      isAtsAdmin);
   }

}
