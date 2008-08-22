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
package org.eclipse.osee.ats.blam;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osee.ats.AtsPlugin;
import org.eclipse.osee.ats.artifact.TeamDefinitionArtifact;
import org.eclipse.osee.ats.artifact.VersionArtifact;
import org.eclipse.osee.ats.artifact.VersionArtifact.VersionReleaseType;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.skynet.core.User;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.search.Active;
import org.eclipse.osee.framework.skynet.core.exception.OseeCoreException;
import org.eclipse.osee.framework.skynet.core.utility.Artifacts;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.osee.framework.ui.plugin.util.Displays;
import org.eclipse.osee.framework.ui.skynet.blam.BlamVariableMap;
import org.eclipse.osee.framework.ui.skynet.blam.operation.AbstractBlam;
import org.eclipse.osee.framework.ui.skynet.util.OSEELog;
import org.eclipse.osee.framework.ui.skynet.widgets.XCombo;
import org.eclipse.osee.framework.ui.skynet.widgets.XModifiedListener;
import org.eclipse.osee.framework.ui.skynet.widgets.XWidget;
import org.eclipse.osee.framework.ui.skynet.widgets.workflow.DynamicXWidgetLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Donald G. Dunne
 */
public class EditTasks extends AbstractBlam {

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.ui.skynet.blam.operation.BlamOperation#runOperation(org.eclipse.osee.framework.ui.skynet.blam.BlamVariableMap, org.eclipse.osee.framework.skynet.core.artifact.Branch, org.eclipse.core.runtime.IProgressMonitor)
    */
   public void runOperation(final BlamVariableMap variableMap, IProgressMonitor monitor) throws Exception {
      monitor.beginTask("Edit Tasks", IProgressMonitor.UNKNOWN);

      Displays.ensureInDisplayThread(new Runnable() {
         /* (non-Javadoc)
          * @see java.lang.Runnable#run()
          */
         @Override
         public void run() {
            try {
               boolean selected = false;
               StringBuffer sb = new StringBuffer("Edit Tasks for: \n\n");
               TeamDefinitionArtifact teamDef = getSelectedTeamDefinition();
               if (teamDef != null) {
                  sb.append("Team Definition: " + teamDef + "\n");
                  selected = true;
               }
               VersionArtifact verArt = getSelectedVersionArtifact();
               if (verArt != null) {
                  sb.append("Version: " + verArt + "\n");
                  selected = true;
               }
               User user = variableMap.getUser("Assignee");
               if (user != null) {
                  sb.append("Assignee: " + user + "\n");
                  selected = true;
               }
               boolean showCompletedCancelled = variableMap.getBoolean("Include Completed/Cancelled");
               if (showCompletedCancelled) {
                  sb.append("Include Completed and Cancelled\n");
               }
               sb.append("\nAre you sure?");
               if (!selected) {
                  AWorkbench.popup("ERROR", "You must select at least one option");
                  return;
               }
               if (MessageDialog.openConfirm(Display.getCurrent().getActiveShell(), "Edit Tasks", sb.toString())) {
                  AWorkbench.popup("ERROR", "Not Implemented Yet");

               }
            } catch (Exception ex) {
               OSEELog.logException(AtsPlugin.class, ex, true);
            }
         }
      });

      monitor.done();
   }

   private VersionArtifact getSelectedVersionArtifact() throws OseeCoreException, SQLException {
      String versionStr = versionCombo.get();
      if (versionStr == null || versionStr.equals("")) return null;
      TeamDefinitionArtifact teamDef = getSelectedTeamDefinition();
      if (teamDef != null) {
         TeamDefinitionArtifact teamDefHoldingVersions = teamDef.getTeamDefinitionHoldingVersions();
         if (teamDefHoldingVersions == null) return null;
         for (VersionArtifact versionArtifact : teamDefHoldingVersions.getVersionsArtifacts(VersionReleaseType.Both)) {
            if (versionArtifact.getDescriptiveName().equals(versionStr)) {
               return versionArtifact;
            }
         }
      }
      return null;
   }

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.ui.skynet.blam.operation.BlamOperation#getXWidgetXml()
    */
   @Override
   public String getXWidgetsXml() {
      String widgetXml =
            "<xWidgets>" +
            //
            "<XWidget xwidgetType=\"XCombo(" + getTeams() + ")\" displayName=\"Team\" horizontalLabel=\"true\"/>" +
            //
            "<XWidget xwidgetType=\"XCombo()\" displayName=\"Version\" horizontalLabel=\"true\"/>" +
            //
            "<XWidget xwidgetType=\"XMembersCombo\" displayName=\"Assignee\" horizontalLabel=\"true\"/>" +
            //
            "<XWidget xwidgetType=\"XCheckBox\" displayName=\"Include Completed/Cancelled\" defaultValue=\"false\" labelAfter=\"true\" horizontalLabel=\"true\"/>";
      widgetXml += "</xWidgets>";
      return widgetXml;
   }

   private XCombo teamCombo = null;
   private XCombo versionCombo = null;

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.ui.skynet.blam.operation.AbstractBlam#widgetCreated(org.eclipse.osee.framework.ui.skynet.widgets.XWidget, org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.osee.framework.skynet.core.artifact.Artifact, org.eclipse.osee.framework.ui.skynet.widgets.workflow.DynamicXWidgetLayout, org.eclipse.osee.framework.ui.skynet.widgets.XModifiedListener, boolean)
    */
   @Override
   public void widgetCreated(XWidget widget, FormToolkit toolkit, Artifact art, DynamicXWidgetLayout dynamicXWidgetLayout, XModifiedListener modListener, boolean isEditable) throws OseeCoreException, SQLException {
      super.widgetCreated(widget, toolkit, art, dynamicXWidgetLayout, modListener, isEditable);
      if (widget.getLabel().equals("Team")) {
         teamCombo = (XCombo) widget;
         teamCombo.addModifyListener(new ModifyListener() {
            /* (non-Javadoc)
             * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
             */
            @Override
            public void modifyText(ModifyEvent e) {
               if (versionCombo != null) {
                  try {
                     TeamDefinitionArtifact teamDefArt = getSelectedTeamDefinition();
                     if (teamDefArt == null) {
                        versionCombo.setDataStrings(new String[] {});
                        return;
                     }
                     TeamDefinitionArtifact teamDefHoldingVersions = teamDefArt.getTeamDefinitionHoldingVersions();
                     if (teamDefHoldingVersions == null) {
                        versionCombo.setDataStrings(new String[] {});
                        return;
                     }
                     Collection<String> names =
                           Artifacts.artNames(teamDefHoldingVersions.getVersionsArtifacts(VersionReleaseType.Both));
                     if (names.size() == 0) {
                        versionCombo.setDataStrings(new String[] {});
                        return;
                     }
                     versionCombo.setDataStrings(names.toArray(new String[names.size()]));
                  } catch (Exception ex) {
                     OSEELog.logException(AtsPlugin.class, ex, true);
                  }
               }
            }
         });
      }
      if (widget.getLabel().equals("Version")) {
         versionCombo = (XCombo) widget;
         widget.getLabelWidget().setToolTipText("Select Team to populate Version list");
      }
   }

   private TeamDefinitionArtifact getSelectedTeamDefinition() throws OseeCoreException, SQLException {
      String selectedTeam = teamCombo.getComboBox().getText();
      Set<TeamDefinitionArtifact> teams = TeamDefinitionArtifact.getTeamDefinitions(Arrays.asList(selectedTeam));
      if (teams.size() > 0) {
         return teams.iterator().next();
      }
      return null;
   }

   private String getTeams() {
      try {
         Collection<String> names = Artifacts.artNames(TeamDefinitionArtifact.getTeamDefinitions(Active.Both));
         String[] namesSorted = names.toArray(new String[names.size()]);
         Arrays.sort(namesSorted);
         return Collections.toString(",", (Object[]) namesSorted);
      } catch (Exception ex) {
         OSEELog.logException(AtsPlugin.class, ex, true);
         return "unabled to acquire teams" + ex.getLocalizedMessage();
      }
   }
}