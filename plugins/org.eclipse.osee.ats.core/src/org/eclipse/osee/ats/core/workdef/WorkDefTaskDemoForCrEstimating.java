/*********************************************************************
 * Copyright (c) 2021 Boeing
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
package org.eclipse.osee.ats.core.workdef;

import static org.eclipse.osee.ats.api.data.AtsAttributeTypes.Assumptions;
import static org.eclipse.osee.ats.api.data.AtsAttributeTypes.Description;
import static org.eclipse.osee.ats.api.data.AtsAttributeTypes.EstimatedCompletionDate;
import static org.eclipse.osee.ats.api.workdef.WidgetOption.SAVE;
import static org.eclipse.osee.ats.api.workdef.WidgetOption.FILL_VERT;
import static org.eclipse.osee.ats.api.workdef.WidgetOption.RFT;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.demo.DemoWorkDefinitions;
import org.eclipse.osee.ats.api.workdef.AtsWorkDefinitionToken;
import org.eclipse.osee.ats.api.workdef.StateColor;
import org.eclipse.osee.ats.api.workdef.StateToken;
import org.eclipse.osee.ats.api.workdef.StateType;
import org.eclipse.osee.ats.api.workdef.model.CompositeLayoutItem;
import org.eclipse.osee.ats.api.workdef.model.SignByAndDateWidgetDefinition;
import org.eclipse.osee.ats.api.workdef.model.WidgetDefinition;
import org.eclipse.osee.ats.api.workdef.model.WorkDefinition;
import org.eclipse.osee.ats.core.workdef.builder.WorkDefBuilder;
import org.eclipse.osee.ats.core.workdef.internal.workdefs.WorkDefTaskDefault;

/**
 * @author Donald G. Dunne
 */
public class WorkDefTaskDemoForCrEstimating extends WorkDefTaskDefault {

   public WorkDefTaskDemoForCrEstimating(AtsWorkDefinitionToken workDefToken) {
      super(workDefToken);
   }

   public WorkDefTaskDemoForCrEstimating() {
      this(DemoWorkDefinitions.WorkDef_Task_Demo_For_CR_Estimating);
   }

   @Override
   public WorkDefinition build() {
      WorkDefBuilder bld = new WorkDefBuilder(workDefToken);

      bld.andState(1, "InWork", StateType.Working).isStartState() //
         .andToStates(StateToken.Completed, StateToken.Cancelled) //

         .andColor(StateColor.BLACK) //
         .andLayout( //
            new WidgetDefinition(Description, "XTextDam", FILL_VERT, SAVE), //
            new WidgetDefinition(Assumptions, "XTextDam", FILL_VERT, SAVE), //
            new WidgetDefinition(AtsAttributeTypes.RiskFactor, "XHyperlinkLabelValueSelectionDam", SAVE), //
            new CompositeLayoutItem(4, //
               new WidgetDefinition("Estimated Points", "XEstimatedPointsWidget", RFT, SAVE), //
               new WidgetDefinition(EstimatedCompletionDate, "XDateDam", SAVE) //
            ), //
            new SignByAndDateWidgetDefinition("Reviewed By", AtsAttributeTypes.ReviewedBy,
               AtsAttributeTypes.ReviewedByDate));

      bld.andState(2, "Completed", StateType.Completed) //
         .andColor(StateColor.DARK_GREEN);

      bld.andState(3, "Cancelled", StateType.Cancelled) //
         .andColor(StateColor.DARK_RED);

      return bld.getWorkDefinition();
   }

}
