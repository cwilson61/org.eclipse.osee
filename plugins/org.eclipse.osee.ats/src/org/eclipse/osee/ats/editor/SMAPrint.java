/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/

package org.eclipse.osee.ats.editor;

import java.util.Arrays;
import java.util.logging.Level;
import org.eclipse.jface.action.Action;
import org.eclipse.osee.ats.artifact.AbstractTaskableArtifact;
import org.eclipse.osee.ats.artifact.AbstractWorkflowArtifact;
import org.eclipse.osee.ats.artifact.AtsAttributeTypes;
import org.eclipse.osee.ats.artifact.TaskArtifact;
import org.eclipse.osee.ats.artifact.TeamWorkFlowArtifact;
import org.eclipse.osee.ats.artifact.note.NoteItem;
import org.eclipse.osee.ats.column.ChangeTypeColumn;
import org.eclipse.osee.ats.column.DeadlineColumn;
import org.eclipse.osee.ats.column.PriorityColumn;
import org.eclipse.osee.ats.column.TeamColumn;
import org.eclipse.osee.ats.editor.widget.ReviewInfoXWidget;
import org.eclipse.osee.ats.internal.AtsPlugin;
import org.eclipse.osee.ats.util.AtsUtil;
import org.eclipse.osee.ats.util.Overview;
import org.eclipse.osee.ats.workdef.StateXWidgetPage;
import org.eclipse.osee.ats.world.IWorldViewArtifact;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.jdk.core.util.DateUtil;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.ui.skynet.results.XResultData;
import org.eclipse.osee.framework.ui.skynet.results.html.XResultPage.Manipulations;

/**
 * @author Donald G. Dunne
 */
public class SMAPrint extends Action {

   private final AbstractWorkflowArtifact sma;
   boolean includeTaskList = true;

   public SMAPrint(AbstractWorkflowArtifact sma) {
      super();
      this.sma = sma;
   }

   @Override
   public void run() {
      try {
         XResultData xResultData = getResultData();
         xResultData.report("Print Preview of " + sma.getName(), Manipulations.RAW_HTML);
      } catch (OseeCoreException ex) {
         OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
      }

   }

   public XResultData getResultData() throws OseeCoreException {
      XResultData resultData = new XResultData();
      resultData.addRaw(AHTML.beginMultiColumnTable(100));
      resultData.addRaw(AHTML.addRowMultiColumnTable(new String[] {AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Title: ",
         sma.getName())}));
      resultData.addRaw(AHTML.endMultiColumnTable());
      resultData.addRaw(AHTML.beginMultiColumnTable(100));
      resultData.addRaw(AHTML.addRowMultiColumnTable(new String[] {
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Current State: ", sma.getCurrentStateName()),
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Team: ", TeamColumn.getName(sma)),
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Assignees: ", ((IWorldViewArtifact) sma).getAssigneeStr()),
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Originator: ", sma.getCreatedBy().getName()),
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Created: ", DateUtil.getMMDDYYHHMM(sma.getCreatedDate()))

      }));
      resultData.addRaw(AHTML.endMultiColumnTable());
      resultData.addRaw(AHTML.beginMultiColumnTable(100));
      resultData.addRaw(AHTML.addRowMultiColumnTable(new String[] {
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Change Type: ", ChangeTypeColumn.getChangeTypeStr(sma)),
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Priority: ", PriorityColumn.getPriorityStr(sma)),
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Need By: ", DeadlineColumn.getDateStr(sma))}));

      resultData.addRaw(AHTML.addRowMultiColumnTable(new String[] {
         //
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Workflow: ", sma.getArtifactTypeName()),
         AHTML.getLabelValueStr(AHTML.LABEL_FONT, "HRID: ", sma.getHumanReadableId()),
         (sma.getPcrId() == null ? "" : AHTML.getLabelValueStr(AHTML.LABEL_FONT, "Id: ", sma.getPcrId()))}));
      resultData.addRaw(AHTML.endMultiColumnTable());
      for (NoteItem note : sma.getNotes().getNoteItems()) {
         if (note.getState().equals("")) {
            resultData.addRaw(note.toHTML() + AHTML.newline());
         }
      }
      getWorkFlowHtml(resultData);
      if (includeTaskList) {
         getTaskHtml(resultData);
      }
      resultData.addRaw(AHTML.newline());
      resultData.addRaw(sma.getLog().getHtml());

      XResultData rd = new XResultData();
      rd.addRaw(AHTML.beginMultiColumnTable(100, 1));
      rd.addRaw(AHTML.addRowMultiColumnTable(new String[] {resultData.getReport("").getManipulatedHtml(
         Arrays.asList(Manipulations.NONE))}));
      rd.addRaw(AHTML.endMultiColumnTable());

      return rd;
   }

   private void getTaskHtml(XResultData rd) {
      if (!(sma instanceof AbstractTaskableArtifact)) {
         return;
      }
      try {
         rd.addRaw(AHTML.addSpace(1) + AHTML.getLabelStr(AHTML.LABEL_FONT, "Tasks"));
         rd.addRaw(AHTML.startBorderTable(100, Overview.normalColor, ""));
         rd.addRaw(AHTML.addHeaderRowMultiColumnTable(new String[] {
            "Title",
            "State",
            "POC",
            "%",
            "Hrs",
            "Resolution",
            "ID"}));
         for (TaskArtifact art : ((AbstractTaskableArtifact) sma).getTaskArtifacts()) {
            rd.addRaw(AHTML.addRowMultiColumnTable(new String[] {
               art.getName(),
               art.getStateMgr().getCurrentStateName().replaceAll("(Task|State)", ""),
               art.getAssigneeStr(),
               art.getPercentCompleteSMATotal() + "",
               art.getHoursSpentSMATotal() + "",
               art.getSoleAttributeValue(AtsAttributeTypes.Resolution, ""),
               art.getHumanReadableId()}));
         }
         rd.addRaw(AHTML.endBorderTable());
      } catch (Exception ex) {
         OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
         rd.addRaw("Task Exception - " + ex.getLocalizedMessage());
      }
   }

   private void getWorkFlowHtml(XResultData rd) throws OseeCoreException {
      // Only display current or past states
      for (StateXWidgetPage statePage : sma.getStatePages()) {
         if (sma.isInState(statePage) || sma.getStateMgr().isStateVisited(statePage)) {
            // Don't show completed or cancelled state if not currently those state
            if (statePage.isCompletedPage() && !sma.isCompleted()) {
               continue;
            }
            if (statePage.isCancelledPage() && !sma.isCancelled()) {
               continue;
            }
            StringBuffer notesSb = new StringBuffer();
            for (NoteItem note : sma.getNotes().getNoteItems()) {
               if (note.getState().equals(statePage.getPageName())) {
                  notesSb.append(note.toHTML());
                  notesSb.append(AHTML.newline());
               }
            }
            if (sma.isInState(statePage) || sma.getStateMgr().isStateVisited(statePage) && sma.isTeamWorkflow()) {
               statePage.generateLayoutDatas(sma);
               rd.addRaw(statePage.getHtml(sma.isInState(statePage) ? AtsUtil.activeColor : AtsUtil.normalColor,
                  notesSb.toString(), getStateHoursSpentHtml(statePage) + getReviewData(sma, statePage)));
               rd.addRaw(AHTML.newline());
            }
         }
      }
   }

   private String getReviewData(AbstractWorkflowArtifact sma, StateXWidgetPage page) throws OseeCoreException {
      if (sma instanceof TeamWorkFlowArtifact) {
         return ReviewInfoXWidget.toHTML((TeamWorkFlowArtifact) sma, page);
      }
      return "";
   }

   private String getStateHoursSpentHtml(StateXWidgetPage statePage) throws OseeCoreException {
      return AHTML.getLabelValueStr("State Hours Spent",
         AtsUtil.doubleToI18nString(sma.getStateMgr().getHoursSpent(statePage)) + "<br>");
   }

   public boolean isIncludeTaskList() {
      return includeTaskList;
   }

   public void setIncludeTaskList(boolean includeTaskList) {
      this.includeTaskList = includeTaskList;
   }

}
