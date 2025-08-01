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
package org.eclipse.osee.ats.ide.workflow.cr;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.team.ChangeTypes;
import org.eclipse.osee.ats.api.team.IAtsTeamDefinition;
import org.eclipse.osee.ats.api.team.Priorities;
import org.eclipse.osee.ats.api.util.AtsImage;
import org.eclipse.osee.ats.api.version.IAtsVersion;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.NewActionData;
import org.eclipse.osee.ats.api.workflow.NewActionResult;
import org.eclipse.osee.ats.core.util.AtsObjects;
import org.eclipse.osee.ats.ide.editor.WorkflowEditor;
import org.eclipse.osee.ats.ide.internal.Activator;
import org.eclipse.osee.ats.ide.internal.AtsApiService;
import org.eclipse.osee.ats.ide.util.widgets.XHyperlinkChangeTypeSelection;
import org.eclipse.osee.ats.ide.util.widgets.XHyperlinkPrioritySelection;
import org.eclipse.osee.ats.ide.util.widgets.XHyperlinkWfdForProgramAi;
import org.eclipse.osee.ats.ide.world.WorldEditor;
import org.eclipse.osee.ats.ide.world.WorldEditorSimpleProvider;
import org.eclipse.osee.framework.core.util.BooleanState;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavItemCat;
import org.eclipse.osee.framework.ui.skynet.blam.AbstractBlam;
import org.eclipse.osee.framework.ui.skynet.blam.VariableMap;
import org.eclipse.osee.framework.ui.skynet.results.XResultDataUI;
import org.eclipse.osee.framework.ui.skynet.widgets.ISelectableValueProvider;
import org.eclipse.osee.framework.ui.skynet.widgets.XHyperlinkLabelDate;
import org.eclipse.osee.framework.ui.skynet.widgets.XHyperlinkLabelEnumeratedArt;
import org.eclipse.osee.framework.ui.skynet.widgets.XHyperlinkTriStateBoolean;
import org.eclipse.osee.framework.ui.skynet.widgets.XModifiedListener;
import org.eclipse.osee.framework.ui.skynet.widgets.XText;
import org.eclipse.osee.framework.ui.skynet.widgets.XWidget;
import org.eclipse.osee.framework.ui.skynet.widgets.builder.XWidgetBuilder;
import org.eclipse.osee.framework.ui.skynet.widgets.util.SwtXWidgetRenderer;
import org.eclipse.osee.framework.ui.skynet.widgets.util.XWidgetRendererItem;
import org.eclipse.osee.framework.ui.swt.ImageManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Donald G. Dunne
 */
public abstract class CreateNewChangeRequestBlam extends AbstractBlam implements ISelectableValueProvider {

   public static final String DEBUG_DESCRIPTION = "Description...";
   private static final String BLAM_DESCRIPTION =
      "Create program top level Demo Change Request for any new feature or problem found.\n" //
         + "This will mature into all the work for all teams needed to resolve this request.";
   protected static final String TITLE = "Title";
   protected static final String PROGRAM = "Program";
   protected final static String DESCRIPTION = "Description";
   protected static final String CHANGE_TYPE = "Change Type";
   protected static final String PRIORITY = "Priority";
   protected static final String COG_PRIORITY = "COG Priority";
   protected static final String NEED_BY = AtsAttributeTypes.NeedBy.getUnqualifiedName();
   protected XText titleWidget;
   protected XText descWidget;
   protected XHyperlinkChangeTypeSelection changeTypeWidget;
   protected XHyperlinkPrioritySelection priorityWidget;
   protected XHyperlinkLabelEnumeratedArt cogPriorityWidget;
   protected final AtsApi atsApi;
   protected XWidgetBuilder wb;
   protected XHyperlinkWfdForProgramAi programWidget;
   private XHyperlinkTriStateBoolean crashWidget;
   private XHyperlinkLabelDate needByWidget;
   private String overrideTitle;
   private NewActionData data;
   private NewActionData newData;

   public CreateNewChangeRequestBlam(String name) {
      super(name, BLAM_DESCRIPTION, null);
      this.atsApi = AtsApiService.get();
   }

   @Override
   public List<XWidgetRendererItem> getXWidgetItems() {
      wb = new XWidgetBuilder();

      wb.andWidget(PROGRAM, XHyperlinkWfdForProgramAi.class.getSimpleName()).andValueProvider(
         this).andRequired().endWidget();

      wb.andXText(TITLE).andRequired().endWidget();
      wb.andXText(AtsAttributeTypes.Description).andHeight(80).andRequired().endWidget();

      // 6 columns
      wb.andChangeType().andComposite(6).andRequired().endWidget();
      if (includePriority()) {
         wb.andPriority().andComposite(6).andRequired().endWidget();
      }
      wb.andXHyperLinkDate(AtsAttributeTypes.NeedBy.getUnqualifiedName()).endComposite().endComposite().endWidget();

      wb.andXHyperlinkTriStateBoolean(
         AtsAttributeTypes.CrashOrBlankDisplay.getUnqualifiedName()).andRequired().endWidget();

      return wb.getItems();
   }

   @Override
   public void widgetCreated(XWidget xWidget, FormToolkit toolkit, Artifact art,
      SwtXWidgetRenderer dynamicXWidgetLayout, XModifiedListener xModListener, boolean isEditable) {
      super.widgetCreated(xWidget, toolkit, art, dynamicXWidgetLayout, xModListener, isEditable);
      if (xWidget.getLabel().equals(TITLE)) {
         titleWidget = (XText) xWidget;
      } else if (xWidget.getLabel().equals(DESCRIPTION)) {
         descWidget = (XText) xWidget;
      } else if (xWidget.getLabel().equals(CHANGE_TYPE)) {
         changeTypeWidget = (XHyperlinkChangeTypeSelection) xWidget;
      } else if (xWidget.getLabel().equals(PRIORITY)) {
         priorityWidget = (XHyperlinkPrioritySelection) xWidget;
      } else if (xWidget.getLabel().equals(COG_PRIORITY)) {
         cogPriorityWidget = (XHyperlinkLabelEnumeratedArt) xWidget;
      } else if (xWidget.getLabel().equals(NEED_BY)) {
         needByWidget = (XHyperlinkLabelDate) xWidget;
      } else if (xWidget.getLabel().equals(AtsAttributeTypes.CrashOrBlankDisplay.getUnqualifiedName())) {
         crashWidget = (XHyperlinkTriStateBoolean) xWidget;
      } else if (xWidget.getLabel().equals(PROGRAM)) {

         if (xWidget instanceof XHyperlinkWfdForProgramAi) {
            programWidget = (XHyperlinkWfdForProgramAi) xWidget;
            programWidget.getLabelHyperlink().addMouseListener(new MouseAdapter() {
               @Override
               public void mouseUp(MouseEvent event) {
                  if (event.button == 3) {
                     handlePopulateWithDebugInfo();
                  }
               }
            });
            Collection<IAtsActionableItem> selectable = programWidget.getSelectable();
            if (selectable.size() == 1) {
               programWidget.setSelected(selectable.iterator().next());
               programWidget.refresh();
            }
         }
      }
   }

   abstract public Collection<IAtsActionableItem> getProgramCrAis();

   protected String getDebugTitle() {
      return "New CR " + atsApi.getRandomNum();
   }

   /**
    * Method is used to quickly create a unique title for debug purposes. Should only be used for tests.
    */
   public void handlePopulateWithDebugInfo() {
      try {
         titleWidget.set(Strings.isValid(overrideTitle) ? overrideTitle : getDebugTitle());
         descWidget.set(DEBUG_DESCRIPTION);
         if (changeTypeWidget != null) {
            changeTypeWidget.setSelected(ChangeTypes.Fix.name());
         }
         if (cogPriorityWidget != null) {
            cogPriorityWidget.setChecked(Arrays.asList("2"));
         }
         if (priorityWidget != null) {
            priorityWidget.setSelected("3");
         }
         if (crashWidget != null) {
            crashWidget.setSelected(BooleanState.Yes);
         }
         if (needByWidget != null) {
            needByWidget.setDateValue(new Date());
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, OseeLevel.SEVERE_POPUP, ex);
      }
   }

   public boolean includePriority() {
      return true;
   }

   @Override
   public void runOperation(VariableMap variableMap, IProgressMonitor monitor) throws Exception {
      this.variableMap = variableMap;
      XResultData results = new XResultData();
      String title = variableMap.getString(TITLE);
      if (Strings.isInValid(title)) {
         results.error("Enter Title");
      }

      IAtsActionableItem programAi = (IAtsActionableItem) variableMap.getValue(PROGRAM);
      if (programAi == null || programAi.isInvalid()) {
         results.error("Select Program");
      }

      String desc = variableMap.getString(DESCRIPTION);
      if (Strings.isInValid(desc)) {
         results.error("Enter Description");
      }

      ChangeTypes cType = (ChangeTypes) variableMap.getValue(CHANGE_TYPE);
      if (changeTypeWidget != null) {
         if (cType == null || cType == ChangeTypes.None) {
            if (changeTypeWidget != null && changeTypeWidget.isRequiredEntry()) {
               results.error("Select Change type");
            }
         }
      }

      Priorities priority = null;
      if (priorityWidget != null) {
         priority = priorityWidget.getSelected();
         if (priorityWidget.isRequiredEntry()) {
            if (priority == null || priority == Priorities.None) {
               results.error("Select Priority");
            }
         }
      }

      if (cogPriorityWidget != null) {
         String cogPriority = cogPriorityWidget.getFirstSelected();
         if (Strings.isInvalid(cogPriority) && cogPriorityWidget.isRequiredEntry()) {
            results.error("Select COG Priority");
         }
      }

      if (crashWidget != null) {
         BooleanState crash =
            (BooleanState) variableMap.getValue(AtsAttributeTypes.CrashOrBlankDisplay.getUnqualifiedName());
         if (crash.isUnSet()) {
            if (crashWidget.isRequiredEntry()) {
               results.error("Select " + AtsAttributeTypes.CrashOrBlankDisplay.getUnqualifiedName());
            }
         }
      }
      Date needBy = null;
      if (needByWidget != null) {
         needBy = (Date) variableMap.getValue(NEED_BY);
         if (needByWidget.isRequiredEntry()) {
            results.error("Select Need By");
         }
      }

      // Allow subsclass validation of additional input
      isValidEntry(results);

      // Log all strings to output section
      log(results.toString());

      // Return if failed
      if (results.isErrors()) {
         return;
      }

      data = atsApi.getActionService().createActionData(getName(), title, desc) //
         .andChangeType(cType) //
         .andPriority(priority) //
         .andAi(programAi) //
         .andNeedBy(needBy) //
         .andRd(results);
      if (crashWidget != null) {
         data.andAttr(AtsAttributeTypes.CrashOrBlankDisplay, crashWidget.getSelected());
      }
      if (cogPriorityWidget != null) {
         data.andAttr(AtsAttributeTypes.CogPriority, cogPriorityWidget.getFirstSelected());
      }

      createActionData(data);

      newData = atsApi.getActionService().createAction(data);

      if (newData.getRd().isErrors()) {
         XResultDataUI.report(newData.getRd(), getTitle());
         return;
      }
      Collection<IAtsTeamWorkflow> teamWfs = newData.getActResult().getAtsTeamWfs();

      operationCompleted(newData);

      if (teamWfs.size() == 1) {
         WorkflowEditor.edit(teamWfs.iterator().next());
      } else {
         WorldEditor.open(new WorldEditorSimpleProvider("New CR Workflows", AtsObjects.getArtifacts(teamWfs)));
      }
   }

   public void operationCompleted(NewActionData newData) {
      // for additional operations after create
   }

   public void createActionData(NewActionData data) {
      // for additional NewActionData entries
   }

   protected Collection<IAtsActionableItem> getNewActionAis(IAtsActionableItem programAi) {
      return Collections.singleton(programAi);
   }

   public Collection<IAtsVersion> getSelectedProgramVersions() {
      IAtsTeamDefinition progTeamDef = getSelectedProgramTeamDefOrSentinel();
      if (progTeamDef.isValid()) {
         return atsApi.getVersionService().getVersionsFromTeamDefHoldingVersions(progTeamDef);
      }
      return Collections.emptyList();
   }

   public IAtsTeamDefinition getSelectedProgramTeamDefOrSentinel() {
      IAtsTeamDefinition progTeamDef = IAtsTeamDefinition.SENTINEL;
      IAtsActionableItem progAi = getSelectedProgramAiOrSentinel();
      if (progAi != null) {
         if (progAi.isValid()) {
            progTeamDef = atsApi.getActionableItemService().getTeamDefinitionInherited(progAi);
         }
      }
      return progTeamDef;
   }

   public IAtsActionableItem getSelectedProgramAiOrSentinel() {
      IAtsActionableItem selected = IAtsActionableItem.SENTINEL;
      if (selected != null) {
         IAtsActionableItem selAi = programWidget.getSelected();
         if (selAi != null) {
            selected = selAi;
         }
      }
      return selected;
   }

   @Override
   abstract public String getRunText();

   @Override
   public String getOutputMessage() {
      return "Not yet run.";
   }

   @Override
   public String getTabTitle() {
      return "Change Request";
   }

   @Override
   public String getTitle() {
      return getName();
   }

   @Override
   public Image getImage() {
      return ImageManager.getImage(AtsImage.CHANGE_REQUEST);
   }

   @Override
   public ImageDescriptor getImageDescriptor() {
      return ImageManager.getImageDescriptor(AtsImage.CHANGE_REQUEST);
   }

   @Override
   public Collection<XNavItemCat> getCategories() {
      return Arrays.asList(XNavItemCat.TOP_NEW);
   }

   @Override
   public Collection<Object> getSelectable(XWidget widget) {
      if (widget instanceof XHyperlinkWfdForProgramAi) {
         return org.eclipse.osee.framework.jdk.core.util.Collections.castAll(getProgramCrAis());
      }
      return Collections.emptyList();
   }

   public void setOverrideTitle(String overrideTitle) {
      this.overrideTitle = overrideTitle;
   }

   public NewActionResult getActionResult() {
      if (newData != null) {
         return newData.getActResult();
      }
      return null;
   }

}