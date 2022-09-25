/*********************************************************************
 * Copyright (c) 2022 Boeing
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

package org.eclipse.osee.ats.ide;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsObject;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.core.column.ActionableItemsColumn;
import org.eclipse.osee.ats.core.column.AtsIdColumn;
import org.eclipse.osee.ats.core.column.TeamColumn;
import org.eclipse.osee.ats.ide.internal.AtsApiService;
import org.eclipse.osee.ats.ide.navigate.AtsNavigateViewItems;
import org.eclipse.osee.ats.ide.util.widgets.XActionableItemCombo;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.enums.CoreBranches;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavItemCat;
import org.eclipse.osee.framework.ui.skynet.blam.AbstractBlam;
import org.eclipse.osee.framework.ui.skynet.blam.VariableMap;
import org.eclipse.osee.framework.ui.skynet.results.XResultDataUI;
import org.eclipse.osee.framework.ui.skynet.widgets.XListDropViewer;
import org.eclipse.osee.framework.ui.skynet.widgets.XModifiedListener;
import org.eclipse.osee.framework.ui.skynet.widgets.XWidget;
import org.eclipse.osee.framework.ui.skynet.widgets.builder.XWidgetBuilder;
import org.eclipse.osee.framework.ui.skynet.widgets.util.SwtXWidgetRenderer;
import org.eclipse.osee.framework.ui.skynet.widgets.util.XWidgetRendererItem;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Vaibhav Y. Patel
 */
public class RealignActionableItemsBlam extends AbstractBlam {
   private final AtsApi atsApi = AtsApiService.get();
   private final static String ACTIONABLE_ITEMS_FROM = "Actionable Item(s) From";
   private final static String ACTIONABLE_ITEM_TO = "Actionable Item To";
   private final static String PERSIST = "Persist? (Read Description before checking the box)";
   private XActionableItemCombo xActionableItemCombo;
   private XListDropViewer xListDropViewer;

   @Override
   public String getName() {
      return "Realign Actionable Items for actions";
   }

   @Override
   public List<XWidgetRendererItem> getXWidgetItems() {
      XWidgetBuilder wb = new XWidgetBuilder();
      wb.andWidget(ACTIONABLE_ITEMS_FROM, "XListDropViewer").endWidget();
      wb.andWidget(ACTIONABLE_ITEM_TO, "XActionableItemCombo").endWidget();
      wb.andWidget(PERSIST, "XCheckBox").endWidget();
      return wb.getItems();
   }

   private IAtsActionableItem getSelectedActionableItemTo() {
      return xActionableItemCombo.getSelectedAi();
   }

   private Collection<ArtifactToken> getActionableItemsFrom() {
      Collection<ArtifactToken> actionableItemsFrom = Collections.castAll(xListDropViewer.getArtifacts());
      Conditions.checkNotNullOrEmpty(actionableItemsFrom, ACTIONABLE_ITEMS_FROM);
      return actionableItemsFrom;
   }

   @Override
   public void runOperation(VariableMap variableMap, IProgressMonitor monitor) throws Exception {
      monitor.beginTask("Realign Actionable Items for actions", IProgressMonitor.UNKNOWN);
      Collection<ArtifactToken> actionableItemsFrom = getActionableItemsFrom();
      IAtsActionableItem actionableItemTo = getSelectedActionableItemTo();
      boolean isPersistAllowed = variableMap.getBoolean(PERSIST);

      if (actionableItemTo.isValid() && actionableItemTo.getTeamDefinition().isValid()) {
         realignAiAndTdForActionsIfAllowed(actionableItemsFrom, actionableItemTo, isPersistAllowed);
      } else {
         StringBuilder err = new StringBuilder();
         err.append("Selected Actionable Item To: " + actionableItemTo.toString());
         err.append(" Or Related Team Definition: " + actionableItemTo.getTeamDefinition().toString());
         err.append(" NOT VALID");
         log(err.toString());
      }

      monitor.done();
   }

   private void realignAiAndTdForActionsIfAllowed(Collection<ArtifactToken> actionableItemsFrom, IAtsActionableItem actionableItemTo, boolean isPersistAllowed) {
      IAtsChangeSet changes = atsApi.createChangeSet("Realign AI/TD References for actions");
      List<ArtifactToken> teamWfs = atsApi.getQueryService().getArtifactListFromAttributeValues(
         AtsAttributeTypes.ActionableItemReference, actionableItemsFrom, CoreBranches.COMMON);

      final XResultData resultData = new XResultData();
      resultData.addRaw(getName());
      resultData.addRaw("\n Related actions counts: " + teamWfs.size());
      resultData.addRaw(AHTML.beginMultiColumnTable(0, 1));
      String[] titleRow = {"Action Title", "Id", "AI From", "AI To", "TD From", "TD To"};
      resultData.addRaw(AHTML.addHeaderRowMultiColumnTable(titleRow));

      for (ArtifactToken teamWf : teamWfs) {
         String currentlyAssignedAI = new ActionableItemsColumn(atsApi).getColumnText((IAtsObject) teamWf);
         String currentlyAssignedTD = new TeamColumn(atsApi).getColumnText((IAtsObject) teamWf);
         String currentlyAssignedAtsId = new AtsIdColumn(atsApi).getColumnText((IAtsObject) teamWf);

         resultData.addRaw(AHTML.addRowMultiColumnTable(teamWf.getName(), currentlyAssignedAtsId, currentlyAssignedAI,
            actionableItemTo.getName(), currentlyAssignedTD, actionableItemTo.getTeamDefinition().getName()));
         if (isPersistAllowed) {
            changes.setSoleAttributeValue(teamWf, AtsAttributeTypes.ActionableItemReference, actionableItemTo);
            changes.setSoleAttributeValue(teamWf, AtsAttributeTypes.TeamDefinitionReference,
               actionableItemTo.getTeamDefinition());
         }
      }
      Displays.ensureInDisplayThread(new Runnable() {
         @Override
         public void run() {
            XResultDataUI.report(resultData, "Results for " + getName());
         }
      });
      if (isPersistAllowed) {
         changes.executeIfNeeded();
      }
   }

   @Override
   public String getDescriptionUsage() {
      StringBuilder description = new StringBuilder();
      description.append("This BLAM will realign items that are associated with the dragged/dropped ");
      description.append("\"Actionable Item(s) From\" to the selected \"Actionable Item To\". ");
      description.append(
         "Team Definition will get realign to the related Team Definition of the selected \"Actionable Item To\". ");
      description.append(
         "It will re-assign team workflows to the selected \"Actionable Item To\" and related Team Definition. ");
      description.append("Note: Run BLAM without checking the persist checkbox first. It will generate report. ");
      description.append(
         "If report data looks correct, then check the persist checkbox and run the BLAM to execute changes. ");
      return description.toString();
   }

   @Override
   public void widgetCreated(XWidget xWidget, FormToolkit toolkit, Artifact art, SwtXWidgetRenderer dynamicXWidgetLayout, XModifiedListener modListener, boolean isEditable) {
      super.widgetCreated(xWidget, toolkit, art, dynamicXWidgetLayout, modListener, isEditable);
      if (xWidget.getLabel().equals(ACTIONABLE_ITEM_TO)) {
         xActionableItemCombo = (XActionableItemCombo) xWidget;
      } else if (xWidget.getLabel().equals(ACTIONABLE_ITEMS_FROM)) {
         xListDropViewer = (XListDropViewer) xWidget;
      }
   }

   @Override
   public Collection<XNavItemCat> getCategories() {
      return Arrays.asList(AtsNavigateViewItems.ATS_ADMIN, XNavItemCat.OSEE_ADMIN);
   }

}