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

package org.eclipse.osee.framework.ui.skynet.widgets.xmerge;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.skynet.core.artifact.Branch;
import org.eclipse.osee.framework.skynet.core.conflict.Conflict;
import org.eclipse.osee.framework.skynet.core.event.LocalTransactionEvent;
import org.eclipse.osee.framework.skynet.core.event.RemoteTransactionEvent;
import org.eclipse.osee.framework.skynet.core.event.SkynetEventManager;
import org.eclipse.osee.framework.skynet.core.event.TransactionEvent;
import org.eclipse.osee.framework.ui.plugin.event.Event;
import org.eclipse.osee.framework.ui.plugin.event.IEventReceiver;
import org.eclipse.osee.framework.ui.skynet.SkynetGuiPlugin;
import org.eclipse.osee.framework.ui.skynet.widgets.XWidget;
import org.eclipse.osee.framework.ui.swt.ALayout;
import org.eclipse.osee.framework.ui.swt.IDirtiableEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;

/**
 * @author Donald G. Dunne
 */
public class XMergeViewer extends XWidget implements IEventReceiver {

   private MergeXViewer xCommitViewer;
   private IDirtiableEditor editor;
   public final static String normalColor = "#EEEEEE";
   private Label extraInfoLabel;
   private Conflict[] conflicts;

   /**
    * @param label
    */
   public XMergeViewer() {
      super("Merge Manager");
      SkynetEventManager.getInstance().register(RemoteTransactionEvent.class, this);
      SkynetEventManager.getInstance().register(LocalTransactionEvent.class, this);
   }

   /*
    * (non-Javadoc)
    * 
    * @see osee.skynet.gui.widgets.XWidget#createWidgets(org.eclipse.swt.widgets.Composite, int)
    */
   @Override
   public void createWidgets(Composite parent, int horizontalSpan) {

      // Create Text Widgets
      if (displayLabel && !label.equals("")) {
         labelWidget = new Label(parent, SWT.NONE);
         labelWidget.setText(label + ":");
         if (toolTip != null) {
            labelWidget.setToolTipText(toolTip);
         }
      }

      Composite mainComp = new Composite(parent, SWT.BORDER);
      mainComp.setLayoutData(new GridData(GridData.FILL_BOTH));
      mainComp.setLayout(ALayout.getZeroMarginLayout());
      if (toolkit != null) toolkit.paintBordersFor(mainComp);

      createTaskActionBar(mainComp);

      xCommitViewer = new MergeXViewer(mainComp, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION, this);
      xCommitViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

      xCommitViewer.setContentProvider(new XMergeContentProvider(xCommitViewer));
      xCommitViewer.setLabelProvider(new XMergeLabelProvider(xCommitViewer));
      xCommitViewer.addSelectionChangedListener(new ISelectionChangedListener() {
         /*
          * (non-Javadoc)
          * 
          * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
          */
         public void selectionChanged(SelectionChangedEvent event) {
            refreshActionEnablement();
         }
      });

      if (toolkit != null) toolkit.adapt(xCommitViewer.getStatusLabel(), false, false);

      Tree tree = xCommitViewer.getTree();
      GridData gridData = new GridData(GridData.FILL_BOTH);
      gridData.heightHint = 100;
      tree.setLayout(ALayout.getZeroMarginLayout());
      tree.setLayoutData(gridData);
      tree.setHeaderVisible(true);
      tree.setLinesVisible(true);

   }

   public void createTaskActionBar(Composite parent) {

      // Button composite for state transitions, etc
      Composite bComp = new Composite(parent, SWT.NONE);
      // bComp.setBackground(mainSComp.getDisplay().getSystemColor(SWT.COLOR_CYAN));
      bComp.setLayout(new GridLayout(2, false));
      bComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

      Composite leftComp = new Composite(bComp, SWT.NONE);
      leftComp.setLayout(new GridLayout());
      leftComp.setLayoutData(new GridData(GridData.BEGINNING | GridData.FILL_HORIZONTAL));

      extraInfoLabel = new Label(leftComp, SWT.NONE);
      extraInfoLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      extraInfoLabel.setText("");

      Composite rightComp = new Composite(bComp, SWT.NONE);
      rightComp.setLayout(new GridLayout());
      rightComp.setLayoutData(new GridData(GridData.END));

      ToolBar toolBar = new ToolBar(rightComp, SWT.FLAT | SWT.RIGHT);
      GridData gd = new GridData(GridData.FILL_HORIZONTAL);
      toolBar.setLayoutData(gd);
      ToolItem item = null;

      item = new ToolItem(toolBar, SWT.PUSH);
      item.setImage(SkynetGuiPlugin.getInstance().getImage("branch_change.gif"));
      item.setToolTipText("Show Change Report");
      item.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
//            if (xCommitViewer.getTransactionArtifactChanges() != null)
//               ChangeReportView.openViewUpon(xCommitViewer.getWorkingBranch());
//            else
//               AWorkbench.popup("ERROR", "Not implemented yet.");
         }
      });

      item = new ToolItem(toolBar, SWT.PUSH);
      item.setImage(SkynetGuiPlugin.getInstance().getImage("refresh.gif"));
      item.setToolTipText("Refresh");
      item.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            loadTable();
         }
      });

      item = new ToolItem(toolBar, SWT.PUSH);
      item.setImage(SkynetGuiPlugin.getInstance().getImage("customize.gif"));
      item.setToolTipText("Customize Table");
      item.addSelectionListener(new SelectionAdapter() {
         public void widgetSelected(SelectionEvent e) {
            xCommitViewer.getCustomize().handleTableCustomization();
         }
      });

      refreshActionEnablement();
   }

   public void refreshActionEnablement() {
   }

   public void loadTable() {
//      try {
//         if (artifact != null && (artifact instanceof IBranchArtifact)) xCommitViewer.setWorkingBranch(((IBranchArtifact) artifact).getWorkingBranch());
//         extraInfoLabel.setText("Commit Status for branch: \"" + ((IBranchArtifact) artifact).getWorkingBranch() + "\" - \"" + ((IBranchArtifact) artifact).getWorkingBranch().getBranchShortName() + "\"");
//      } catch (Exception ex) {
//         OSEELog.logException(SkynetGuiPlugin.class, ex, true);
//      }
      refresh();
   }

   @SuppressWarnings("unchecked")
   public ArrayList<Branch> getSelectedBranches() {
      ArrayList<Branch> items = new ArrayList<Branch>();
      if (xCommitViewer == null) return items;
      if (xCommitViewer.getSelection().isEmpty()) return items;
      Iterator i = ((IStructuredSelection) xCommitViewer.getSelection()).iterator();
      while (i.hasNext()) {
         Object obj = i.next();
         items.add((Branch) obj);
      }
      return items;
   }

   @Override
   public Control getControl() {
      return xCommitViewer.getTree();
   }

   @Override
   public void dispose() {
      xCommitViewer.dispose();
   }

   @Override
   public void setFocus() {
      xCommitViewer.getTree().setFocus();
   }

   public void refresh() {
      xCommitViewer.refresh();
      setLabelError();
      refreshActionEnablement();
   }

   @Override
   public boolean isValid() {
      if (isRequiredEntry() && xCommitViewer.getTree().getItemCount() == 0) {
         extraInfoLabel.setText("At least one role entry is required");
         return false;
      }
      extraInfoLabel.setText("");
      return true;
   }

   @Override
   public void setXmlData(String str) {
   }

   @Override
   public String getXmlData() {
      return null;
   }

   @Override
   public String toHTML(String labelFont) {
      return AHTML.simplePage("Unhandled");
   }

   @Override
   public String getReportData() {
      return null;
   }

   /**
    * @return Returns the xViewer.
    */
   public MergeXViewer getXViewer() {
      return xCommitViewer;
   }

   public void onEvent(final Event event) {
      if (xCommitViewer == null || xCommitViewer.getTree() == null || xCommitViewer.getTree().isDisposed()) return;

      if (event instanceof TransactionEvent) {
         refresh();
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see osee.jdk.core.event.IEventReceiver#runOnEventInDisplayThread()
    */
   public boolean runOnEventInDisplayThread() {
      return true;
   }

   /*
    * (non-Javadoc)
    * 
    * @see osee.skynet.gui.widgets.XWidget#getData()
    */
   @Override
   public Object getData() {
      return xCommitViewer.getInput();
   }

   public IDirtiableEditor getEditor() {
      return editor;
   }

   public void setEditor(IDirtiableEditor editor) {
      this.editor = editor;
   }

   public boolean isEditable() {
      return editable;
   }

   public void setEditable(boolean editable) {
      this.editable = editable;
   }

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.ui.skynet.widgets.IDamWidget#setArtifact(org.eclipse.osee.framework.skynet.core.artifact.Artifact, java.lang.String)
    */
   public void setConflicts(Conflict[] conflicts) throws IllegalStateException, SQLException {
      this.conflicts = conflicts;
      loadTable();
      xCommitViewer.setWorkingBranch(conflicts);

   }
}
