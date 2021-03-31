/*********************************************************************
 * Copyright (c) 2004, 2007 Boeing
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

package org.eclipse.osee.framework.ui.skynet.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osee.framework.access.AccessControlData;
import org.eclipse.osee.framework.access.AccessControlManager;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.BranchToken;
import org.eclipse.osee.framework.core.data.IUserGroup;
import org.eclipse.osee.framework.core.enums.CoreArtifactTypes;
import org.eclipse.osee.framework.core.enums.CoreBranches;
import org.eclipse.osee.framework.core.enums.PermissionEnum;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.UserManager;
import org.eclipse.osee.framework.skynet.core.access.UserGroupService;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.BranchManager;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;
import org.eclipse.osee.framework.skynet.core.event.OseeEventManager;
import org.eclipse.osee.framework.skynet.core.event.model.AccessArtifactLockTopicEvent;
import org.eclipse.osee.framework.skynet.core.event.model.AccessTopicEvent;
import org.eclipse.osee.framework.ui.skynet.internal.Activator;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * GUI that is used to maintain an <Code>Artifact</Code> access control list.
 *
 * @author Jeff C. Phillips
 */
public class PolicyDialog extends Dialog {
   private PolicyTableViewer policyTableViewer;
   private Button addButton;
   private Button chkChildrenPermission;
   private Combo userCombo;
   private Combo permissionLevelCombo;
   private final Object accessControlledObject;
   private Label accessLabel, objectLabel;
   private final Shell parentShell;
   Boolean isArtifactLockedBeforeDialog = null;

   public PolicyDialog(Shell parentShell, Object accessControlledObject) {
      super(parentShell);
      this.parentShell = parentShell;
      this.accessControlledObject = accessControlledObject;
      setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | getDefaultOrientation() | SWT.RESIZE);
   }

   @Override
   protected Control createDialogArea(Composite parent) {
      getShell().setText("Access Control List: " + getHeaderName(accessControlledObject));

      Composite mainComposite = new Composite(parent, SWT.NONE);
      mainComposite.setFont(parent.getFont());
      mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      mainComposite.setLayout(new GridLayout(1, false));

      accessLabel = new Label(mainComposite, SWT.NONE);
      accessLabel.setForeground(Displays.getSystemColor(SWT.COLOR_RED));

      Group group = new Group(mainComposite, SWT.NULL);
      group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      group.setLayout(new GridLayout(1, false));

      // Setup policy table
      objectLabel = new Label(group, SWT.NONE);
      objectLabel.setText(
         String.format("Access Control for [%s]", Strings.truncate(accessControlledObject.toString(), 70)));

      policyTableViewer = new PolicyTableViewer(group, accessControlledObject);

      // Create Input Widgets
      Composite composite = new Composite(group, SWT.NONE);
      composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
      composite.setLayout(new GridLayout(3, false));

      permissionLevelCombo = new Combo(composite, SWT.NONE);
      userCombo = new Combo(composite, SWT.NONE);
      addButton = new Button(composite, SWT.PUSH);
      addButton.setText("Add");

      boolean accessEnabled = isAccessEnabled();
      addButton.setEnabled(accessEnabled);
      policyTableViewer.setReadonly(!accessEnabled);

      chkChildrenPermission = new Button(composite, SWT.CHECK);
      chkChildrenPermission.setText("Set permission for artifact's default hierarchy descendents.");

      new Label(mainComposite, SWT.NONE).setText("  NOTE: Higher permission rank overrides lower rank.");
      Label baseNote = new Label(mainComposite, SWT.NONE);
      baseNote.setText("  NOTE: Baseline Branches are Read-Only by default.");
      if (accessControlledObject instanceof BranchToken) {
         BranchToken branch = (BranchToken) accessControlledObject;
         if (BranchManager.getType(branch).isBaselineBranch()) {
            baseNote.setForeground(Displays.getSystemColor(SWT.COLOR_RED));
         }
      }

      try {
         populateInputWidgets();
      } catch (Exception ex) {
         OseeLog.log(Activator.class, Level.SEVERE, ex);
      }
      addListeners();
      checkEnabled();
      setMaxModificationLevel();

      if (accessControlledObject instanceof Artifact) {
         isArtifactLockedBeforeDialog = AccessControlManager.hasLock((Artifact) accessControlledObject);
      }

      return mainComposite;
   }

   private void populateInputWidgets() {

      // Setup permissions combo
      permissionLevelCombo.setText("-Select Permission-");
      List<PermissionEnum> permissions = new ArrayList<>();
      for (PermissionEnum permission : PermissionEnum.values()) {
         if (permission == PermissionEnum.USER_LOCK) {
            if (accessControlledObject instanceof ArtifactId) {
               permissions.add(permission);
            }
         } else {
            permissions.add(permission);
         }
      }
      Collections.sort(permissions, new Comparator<PermissionEnum>() {

         @Override
         public int compare(PermissionEnum o1, PermissionEnum o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
         }
      });
      for (PermissionEnum permission : permissions) {
         permissionLevelCombo.add(
            permission.getName() + " - Rank = " + permission.getRank() + " - " + permission.getDescription());
         permissionLevelCombo.setData(permission.getName(), permission);
      }

      // Setup user combo
      userCombo.setText("-Select Person-");
      ArrayList<Artifact> subjectList = new ArrayList<>();
      subjectList.addAll(UserManager.getUsersSortedByName());
      subjectList.addAll(ArtifactQuery.getArtifactListFromType(CoreArtifactTypes.UserGroup, CoreBranches.COMMON));
      Collections.sort(subjectList, new UserComparator<Artifact>());
      for (Artifact subject : subjectList) {
         String name = subject.getName();
         userCombo.add(name);
         userCombo.setData(name, subject);
      }

   }

   private void addListeners() {
      addButton.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            Artifact subject = (Artifact) userCombo.getData(userCombo.getText().replaceAll(" - Rank.*", ""));
            PermissionEnum permission = (PermissionEnum) permissionLevelCombo.getData(
               permissionLevelCombo.getText().replaceAll(" - Rank.*", ""));

            if (subject != null && permission != null) {
               try {
                  policyTableViewer.addOrModifyItem(subject, accessControlledObject, permission);
               } catch (OseeCoreException ex) {
                  OseeLog.log(Activator.class, Level.SEVERE, ex);
               }
            } else {
               MessageDialog.openError(parentShell, "Add Error",
                  "Please select a Person and Permission Level to add to the Access Control List");
            }
         }
      });
   }

   private void setMaxModificationLevel() {
      PermissionEnum permission = AccessControlManager.getPermission(accessControlledObject);
      policyTableViewer.setMaxModificationLevel(permission);
   }

   private void checkEnabled() {
      boolean isAccessEnabled = isAddAccessEnabled();
      boolean isModifyEnabled = isModifyAccessEnabled();

      String displayText = "";
      if (!isAccessEnabled) {
         displayText = "You do not have permissions to add/delete users";
         if (!isModifyEnabled) {
            displayText += " or modify access";
         }
      } else if (!isModifyEnabled) {
         displayText = "You do not have permissions to modify access";
      }

      accessLabel.setText(displayText);
      boolean isArtifact = accessControlledObject instanceof Artifact;

      userCombo.setEnabled(isAccessEnabled);
      permissionLevelCombo.setEnabled(isAccessEnabled);
      addButton.setEnabled(isAccessEnabled);
      policyTableViewer.allowTableModification(isAccessEnabled);
      chkChildrenPermission.setEnabled(isArtifact);
   }

   private boolean isAccessEnabled() {
      return isModifyAccessEnabled() || isAddAccessEnabled();
   }

   private boolean isModifyAccessEnabled() {
      return isAccessEnabled(PermissionEnum.USER_LOCK);
   }

   private boolean isAddAccessEnabled() {
      return isAccessEnabled(PermissionEnum.FULLACCESS);
   }

   private boolean isAccessEnabled(PermissionEnum permission) {
      boolean enabled;
      try {
         IUserGroup oseeAccessGroup = UserGroupService.getOseeAccessAdmin();
         boolean isOseeAccessAdmin = oseeAccessGroup.isCurrentUserMember();
         boolean objectHasAccessSet = !policyTableViewer.getAccessControlList().isEmpty();
         if (isOseeAccessAdmin) {
            enabled = true;
         } else if (objectHasAccessSet) {
            enabled = AccessControlManager.hasPermission(accessControlledObject, permission);
         } else {
            enabled = false;
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(Activator.class, Level.SEVERE, ex);
         enabled = false;
      }
      return enabled;
   }

   @Override
   protected void okPressed() {
      for (AccessControlData data : policyTableViewer.getAccessControlList().values()) {
         if (data.isDirty()) {
            boolean isRecursionAllowed = chkChildrenPermission.getSelection();
            AccessControlManager.persistPermission(data, isRecursionAllowed);
         }
      }
      policyTableViewer.removeDataFromDB();
      AccessControlManager.clearCaches();

      // Send artifact locked event if changed in dialog
      if (isArtifactLockedBeforeDialog != null) {
         try {
            Artifact artifact = (Artifact) accessControlledObject;
            boolean isArtifactLockedAfterDialog = AccessControlManager.hasLock(artifact);
            if (isArtifactLockedAfterDialog != isArtifactLockedBeforeDialog) {
               AccessArtifactLockTopicEvent event = new AccessArtifactLockTopicEvent();
               event.setBranch(artifact.getBranch());
               event.addArtifact(artifact.getUuid());
               OseeEventManager.kickAccessTopicEvent(this, event, AccessTopicEvent.ACCESS_ARTIFACT_LOCK_MODIFIED);
            }
         } catch (Exception ex) {
            OseeLog.log(PolicyDialog.class, Level.SEVERE, ex);
         }

      }
      super.okPressed();
   }

   private String getHeaderName(Object object) {
      String name = "";
      if (object instanceof Artifact) {
         name = ((Artifact) object).getName();
      } else if (object instanceof BranchToken) {
         name = ((BranchToken) object).getName();
      }
      return name;
   }

   private static final class UserComparator<T> implements Comparator<T> {
      @Override
      public int compare(T o1, T o2) {
         if (o1 instanceof Artifact && o2 instanceof Artifact) {
            return ((Artifact) o1).getName().compareToIgnoreCase(((Artifact) o2).getName());
         }
         return 0;
      }
   }

}
