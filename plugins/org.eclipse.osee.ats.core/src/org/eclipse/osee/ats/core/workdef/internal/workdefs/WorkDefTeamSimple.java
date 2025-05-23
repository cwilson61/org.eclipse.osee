/*********************************************************************
 * Copyright (c) 2019 Boeing
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

package org.eclipse.osee.ats.core.workdef.internal.workdefs;

import static org.eclipse.osee.ats.api.workdef.WidgetOption.FILL_VERT;
import static org.eclipse.osee.ats.api.workdef.WidgetOption.RFT;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.workdef.AtsWorkDefinitionTokens;
import org.eclipse.osee.ats.api.workdef.StateColor;
import org.eclipse.osee.ats.api.workdef.StateToken;
import org.eclipse.osee.ats.api.workdef.StateType;
import org.eclipse.osee.ats.api.workdef.model.RuleDefinitionOption;
import org.eclipse.osee.ats.api.workdef.model.WidgetDefinition;
import org.eclipse.osee.ats.api.workdef.model.WorkDefinition;
import org.eclipse.osee.ats.core.workdef.builder.WorkDefBuilder;
import org.eclipse.osee.ats.core.workdef.defaults.AbstractWorkDef;

/**
 * @author Donald G. Dunne
 */
public class WorkDefTeamSimple extends AbstractWorkDef {

   public WorkDefTeamSimple() {
      super(AtsWorkDefinitionTokens.WorkDef_Team_Simple);
   }

   @Override
   public WorkDefinition build() {
      WorkDefBuilder bld = new WorkDefBuilder(workDefToken);

      bld.andHeader() //
         .andLayout(getChangeTypeComposite(), //
            new WidgetDefinition("Work Package", "XHyperlinkWorkPackageDam" //
            ) //
         ) //
         .isShowMetricsHeader(false); //

      bld.andState(1, "Endorse", StateType.Working).isStartState() //
         .andToStates(StateToken.InWork, StateToken.Cancelled) //
         .andRules(RuleDefinitionOption.RequireStateHourSpentPrompt) //
         .andColor(StateColor.BLACK) //
         .andLayout( //
            new WidgetDefinition(AtsAttributeTypes.Description, "XTextDam", RFT, FILL_VERT), //
            new WidgetDefinition(AtsAttributeTypes.ProposedResolution, "XTextDam", FILL_VERT), //
            new WidgetDefinition(AtsAttributeTypes.ValidationRequired, "XComboBooleanDam"));

      bld.andState(2, "InWork", StateType.Working) //
         .andToStates(StateToken.Completed, StateToken.Cancelled) //
         .andRules(RuleDefinitionOption.RequireStateHourSpentPrompt) //
         .andColor(StateColor.BLACK) //
         .andLayout( //
            new WidgetDefinition(AtsAttributeTypes.Resolution, "XTextDam", FILL_VERT));

      bld.andState(3, "Completed", StateType.Completed) //
         .andRules(RuleDefinitionOption.AddDecisionValidateBlockingReview) //
         .andColor(StateColor.BLACK);

      bld.andState(4, "Cancelled", StateType.Cancelled) //
         .andColor(StateColor.BLACK);

      return bld.getWorkDefinition();
   }
}
