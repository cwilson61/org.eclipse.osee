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

package org.eclipse.osee.ats.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.nebula.widgets.xviewer.IMultiColumnEditProvider;
import org.eclipse.nebula.widgets.xviewer.IXViewerFactory;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.action.ColumnMultiEditAction;
import org.eclipse.nebula.widgets.xviewer.customize.XViewerCustomMenu;
import org.eclipse.osee.ats.AtsImage;
import org.eclipse.osee.ats.AtsOpenOption;
import org.eclipse.osee.ats.actions.ConvertActionableItemsAction;
import org.eclipse.osee.ats.actions.DeletePurgeAtsArtifactsAction;
import org.eclipse.osee.ats.actions.EmailActionAction;
import org.eclipse.osee.ats.actions.FavoriteAction;
import org.eclipse.osee.ats.actions.ISelectedAtsArtifacts;
import org.eclipse.osee.ats.actions.ISelectedTeamWorkflowArtifacts;
import org.eclipse.osee.ats.actions.OpenInArtifactEditorAction;
import org.eclipse.osee.ats.actions.OpenInAtsWorkflowEditor;
import org.eclipse.osee.ats.actions.OpenInMassEditorAction;
import org.eclipse.osee.ats.actions.SubscribedAction;
import org.eclipse.osee.ats.artifact.AbstractWorkflowArtifact;
import org.eclipse.osee.ats.artifact.ActionArtifact;
import org.eclipse.osee.ats.artifact.GoalArtifact;
import org.eclipse.osee.ats.artifact.TaskArtifact;
import org.eclipse.osee.ats.artifact.TeamWorkFlowArtifact;
import org.eclipse.osee.ats.editor.SMAPromptChangeStatus;
import org.eclipse.osee.ats.field.GoalOrderColumn;
import org.eclipse.osee.ats.field.IPersistAltLeftClickProvider;
import org.eclipse.osee.ats.internal.AtsPlugin;
import org.eclipse.osee.ats.task.TaskEditor;
import org.eclipse.osee.ats.task.TaskEditorSimpleProvider;
import org.eclipse.osee.ats.task.TaskXViewer;
import org.eclipse.osee.ats.util.ArtifactEmailWizard;
import org.eclipse.osee.ats.util.AtsUtil;
import org.eclipse.osee.ats.util.DefaultTeamState;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsAttributeColumn;
import org.eclipse.osee.framework.core.data.IAttributeType;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ISelectedArtifacts;
import org.eclipse.osee.framework.skynet.core.transaction.SkynetTransaction;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateComposite.TableLoadOption;
import org.eclipse.osee.framework.ui.skynet.artifact.ArtifactPromptChange;
import org.eclipse.osee.framework.ui.skynet.results.XResultData;
import org.eclipse.osee.framework.ui.skynet.widgets.xviewer.skynet.column.XViewerAttributeColumn;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.osee.framework.ui.swt.ImageManager;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * @author Donald G. Dunne
 */
public class WorldXViewer extends XViewer implements ISelectedAtsArtifacts, IPersistAltLeftClickProvider, ISelectedTeamWorkflowArtifacts, ISelectedArtifacts {
   private String title;
   private String extendedStatusString = "";
   public static final String MENU_GROUP_ATS_WORLD_EDIT = "ATS WORLD EDIT";
   public static final String MENU_GROUP_ATS_WORLD_OPEN = "ATS WORLD OPEN";
   public static final String MENU_GROUP_ATS_WORLD_OTHER = "ATS WORLD OTHER";
   public static final String ADD_AS_FAVORITE = "Add as Favorite";
   public static final String REMOVE_FAVORITE = "Remove Favorite";
   public static final String SUBSCRIBE = "Subscribe for Notifications";
   public static final String UN_SUBSCRIBE = "Un-Subscribe for Notifications";
   public final WorldXViewer thisXViewer = this;
   public List<IMenuActionProvider> menuActionProviders = new ArrayList<IMenuActionProvider>();

   public WorldXViewer(Composite parent, int style, IXViewerFactory xViewerFactory) {
      super(parent, style, xViewerFactory);
   }

   @Override
   protected void createSupportWidgets(Composite parent) {
      super.createSupportWidgets(parent);
      parent.addDisposeListener(new DisposeListener() {
         @Override
         public void widgetDisposed(DisposeEvent e) {
            ((WorldContentProvider) getContentProvider()).clear(false);
         }
      });
      createMenuActions();
   }

   Action editAction, editStatusAction, editActionableItemsAction;
   ConvertActionableItemsAction convertActionableItemsAction;
   Action openInAtsWorldEditorAction, openInAtsTaskEditorAction;
   OpenInAtsWorkflowEditor openInAtsWorkflowEditorAction;
   OpenInArtifactEditorAction openInArtifactEditorAction;
   OpenInMassEditorAction openInMassEditorAction;
   FavoriteAction favoritesAction;
   SubscribedAction subscribedAction;
   DeletePurgeAtsArtifactsAction deletePurgeAtsObjectAction;
   EmailActionAction emailAction;
   Action resetActionArtifactAction;

   public void createMenuActions() {

      convertActionableItemsAction = new ConvertActionableItemsAction(this);
      openInMassEditorAction = new OpenInMassEditorAction(this);
      openInAtsWorkflowEditorAction = new OpenInAtsWorkflowEditor("Open", this);
      favoritesAction = new FavoriteAction(this);
      subscribedAction = new SubscribedAction(this);
      openInArtifactEditorAction = new OpenInArtifactEditorAction(this);
      deletePurgeAtsObjectAction = new DeletePurgeAtsArtifactsAction(this);
      emailAction = new EmailActionAction(this);

      editStatusAction = new Action("Edit Status", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            try {
               if (SMAPromptChangeStatus.promptChangeStatus(getSelectedSMAArtifacts(), true)) {
                  update(getSelectedSMAArtifacts().toArray(), null);
               }
            } catch (Exception ex) {
               OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
            }
         }
      };

      editAction = new Action("Edit", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            try {
               new ColumnMultiEditAction(thisXViewer).run();
            } catch (Exception ex) {
               OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
            }
         }
      };

      editActionableItemsAction = new Action("Edit Actionable Item(s)", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            try {
               if (getSelectedActionArtifacts().size() == 1) {
                  ActionArtifact actionArt = getSelectedActionArtifacts().iterator().next();
                  AtsUtil.editActionableItems(actionArt);
                  refresh(getSelectedArtifactItems().iterator().next());
               } else {
                  TeamWorkFlowArtifact teamArt = getSelectedTeamWorkflowArtifacts().iterator().next();
                  AtsUtil.editActionableItems(teamArt);
                  refresh(getSelectedArtifactItems().toArray()[0]);
               }
            } catch (Exception ex) {
               OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
            }
         }
      };

      openInAtsWorldEditorAction = new Action("Open in ATS World Editor", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            if (getSelectedArtifacts().isEmpty()) {
               AWorkbench.popup("Error", "No items selected");
               return;
            }
            WorldEditorInput worldEditorInput = null;
            if (thisXViewer instanceof TaskXViewer) {
               worldEditorInput =
                  new WorldEditorInput(new WorldEditorSimpleProvider("ATS World", getSelectedArtifacts(), null,
                     TableLoadOption.None));
            } else {
               worldEditorInput =
                  new WorldEditorInput(new WorldEditorSimpleProvider("ATS World", getSelectedArtifacts(),
                     getCustomizeMgr().generateCustDataFromTable(), TableLoadOption.None));
            }
            IWorkbenchPage page = AWorkbench.getActivePage();
            try {
               page.openEditor(worldEditorInput, WorldEditor.EDITOR_ID);
            } catch (PartInitException ex) {
               OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
            }
         }

         @Override
         public ImageDescriptor getImageDescriptor() {
            return ImageManager.getImageDescriptor(AtsImage.GLOBE);
         }

      };

      openInAtsTaskEditorAction = new Action("Open in ATS Task Editor", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            if (getSelectedTaskArtifacts().isEmpty()) {
               AWorkbench.popup("Error", "No Tasks selected");
               return;
            }
            try {
               TaskEditor.open(new TaskEditorSimpleProvider("ATS Tasks", getSelectedTaskArtifacts()));
            } catch (OseeCoreException ex) {
               OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
            }
         }

         @Override
         public ImageDescriptor getImageDescriptor() {
            return ImageManager.getImageDescriptor(AtsImage.TASK);
         }

      };

      resetActionArtifactAction = new Action("Reset Action off Children", IAction.AS_PUSH_BUTTON) {
         @Override
         public void run() {
            SkynetTransaction transaction;
            try {
               transaction = new SkynetTransaction(AtsUtil.getAtsBranch(), "Reset Action off Children");
               for (ActionArtifact actionArt : getSelectedActionArtifacts()) {
                  actionArt.resetAttributesOffChildren(transaction);
               }
               transaction.execute();
            } catch (OseeCoreException ex) {
               OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
            }

         }
      };
   }

   @Override
   public void handleColumnMultiEdit(TreeColumn treeColumn, Collection<TreeItem> treeItems) {
      super.handleColumnMultiEdit(treeColumn, treeItems);
      handleColumnMultiEdit(treeColumn, treeItems, true);
   }

   public void handleColumnMultiEdit(TreeColumn treeColumn, Collection<TreeItem> treeItems, final boolean persist) {
      if (treeColumn.getData() instanceof IMultiColumnEditProvider) {
         return;
      }
      if (!(treeColumn.getData() instanceof XViewerAttributeColumn) && !(treeColumn.getData() instanceof XViewerAtsAttributeColumn)) {
         AWorkbench.popup("ERROR", "Column is not attribute and thus not multi-editable " + treeColumn.getText());
         return;
      }

      XResultData rData = new XResultData();
      IAttributeType attributeType = null;
      if (treeColumn.getData() instanceof XViewerAttributeColumn) {
         final XViewerAttributeColumn xCol = (XViewerAttributeColumn) treeColumn.getData();
         attributeType = xCol.getAttributeType();
      }
      if (treeColumn.getData() instanceof XViewerAtsAttributeColumn) {
         final XViewerAtsAttributeColumn xCol = (XViewerAtsAttributeColumn) treeColumn.getData();
         attributeType = xCol.getAttributeType();
      }
      if (attributeType == null) {
         AWorkbench.popup("ERROR", "Can't retrieve attribute name from attribute column " + treeColumn.getText());
         return;
      }
      final Set<Artifact> useArts = new HashSet<Artifact>();
      for (TreeItem item : treeItems) {
         Artifact art = (Artifact) item.getData();
         try {
            if (art.isAttributeTypeValid(attributeType)) {
               useArts.add(art);
            } else {
               rData.logError(attributeType + " not valid for artifact " + art.getGuid() + " - " + art.getName());
            }
         } catch (OseeCoreException ex) {
            OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
            rData.logError(ex.getLocalizedMessage());
         }
      }

      try {
         if (!rData.isEmpty()) {
            rData.report("Column Multi Edit Errors");
            return;
         }
         if (useArts.size() > 0) {
            ArtifactPromptChange.promptChangeAttribute(attributeType, useArts, persist);
         }
      } catch (Exception ex) {
         OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
      }
   }

   @Override
   public boolean isColumnMultiEditable(TreeColumn treeColumn, Collection<TreeItem> treeItems) {
      if (!(treeColumn.getData() instanceof XViewerColumn)) {
         return false;
      }
      if (!((XViewerColumn) treeColumn.getData()).isMultiColumnEditable()) {
         return false;
      }
      if (((XViewerColumn) treeColumn.getData()) instanceof IMultiColumnEditProvider) {
         return true;
      }
      IAttributeType attributeType = null;
      // Currently don't know how to multi-edit anything but attribute
      if (treeColumn.getData() instanceof XViewerAttributeColumn) {
         XViewerAttributeColumn xCol = (XViewerAttributeColumn) treeColumn.getData();
         attributeType = xCol.getAttributeType();
      } else if (treeColumn.getData() instanceof XViewerAtsAttributeColumn) {
         XViewerAtsAttributeColumn xCol = (XViewerAtsAttributeColumn) treeColumn.getData();
         attributeType = xCol.getAttributeType();
      } else {
         return false;
      }

      if (attributeType == null) {
         AWorkbench.popup("ERROR", "Can't retrieve attribute name from attribute column " + treeColumn.getText());
         return false;
      }
      for (TreeItem item : treeItems) {
         if (item.getData() instanceof ActionArtifact) {
            return false;
         }
         try {
            Artifact artifact = (Artifact) item.getData();
            if (!artifact.isAttributeTypeValid(attributeType)) {
               return false;
            }
         } catch (OseeCoreException ex) {
            OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
            return false;
         }
      }
      return true;
   }

   @Override
   public boolean isColumnMultiEditEnabled() {
      return true;
   }

   public void handleEmailSelectedAtsObject() throws OseeCoreException {
      Artifact art = getSelectedArtifacts().iterator().next();
      if (art instanceof ActionArtifact) {
         if (((ActionArtifact) art).getTeamWorkFlowArtifacts().size() > 1) {
            art = AtsUtil.promptSelectTeamWorkflow((ActionArtifact) art);
            if (art == null) {
               return;
            }
         } else {
            art = ((ActionArtifact) art).getTeamWorkFlowArtifacts().iterator().next();
         }
      }
      if (art != null) {
         ArtifactEmailWizard ew = new ArtifactEmailWizard((AbstractWorkflowArtifact) art);
         WizardDialog dialog = new WizardDialog(Displays.getActiveShell(), ew);
         dialog.create();
         dialog.open();
      }
   }

   public AbstractWorkflowArtifact getSelectedSMA() {
      Object obj = null;
      if (getSelectedArtifactItems().isEmpty()) {
         return null;
      }
      obj = getTree().getSelection()[0].getData();
      return obj != null && obj instanceof AbstractWorkflowArtifact ? (AbstractWorkflowArtifact) obj : null;
   }

   /**
    * Create Edit menu at top to make easier for users to see and eventually enable menu to get rid of all separate edit
    * items
    */
   public MenuManager updateEditMenu(MenuManager mm) {
      final Collection<TreeItem> selectedTreeItems = Arrays.asList(thisXViewer.getTree().getSelection());
      Set<TreeColumn> editableColumns = ColumnMultiEditAction.getEditableTreeColumns(thisXViewer, selectedTreeItems);

      return XViewerCustomMenu.createEditMenuManager(thisXViewer, "Edit", selectedTreeItems, editableColumns);
   }

   public void updateEditMenuActions() {
      MenuManager mm = getMenuManager();

      // EDIT MENU BLOCK
      MenuManager editMenuManager = updateEditMenu(mm);
      mm.insertBefore(MENU_GROUP_PRE, editMenuManager);

      mm.insertBefore(MENU_GROUP_PRE, editStatusAction);
      editStatusAction.setEnabled(getSelectedSMAArtifacts().size() > 0);

      mm.insertBefore(MENU_GROUP_PRE, editActionableItemsAction);
      editActionableItemsAction.setEnabled(getSelectedActionArtifacts().size() == 1 || getSelectedTeamWorkflowArtifacts().size() == 1);

      mm.insertBefore(MENU_GROUP_PRE, convertActionableItemsAction);
      convertActionableItemsAction.updateEnablement();

   }

   @Override
   public void updateMenuActionsForTable() {
      MenuManager mm = getMenuManager();

      mm.insertBefore(XViewer.MENU_GROUP_PRE, new GroupMarker(MENU_GROUP_ATS_WORLD_EDIT));

      updateEditMenuActions();

      mm.insertBefore(MENU_GROUP_PRE, new Separator());

      // OPEN MENU BLOCK
      mm.insertBefore(MENU_GROUP_PRE, new Separator());
      mm.insertBefore(MENU_GROUP_PRE, openInAtsWorkflowEditorAction);
      openInAtsWorkflowEditorAction.updateEnablement();

      MenuManager openWithMenuManager = new MenuManager("Open With", "openwith");

      openWithMenuManager.add(openInMassEditorAction);
      openInMassEditorAction.updateEnablement();

      openWithMenuManager.add(openInAtsWorldEditorAction);
      openInAtsWorldEditorAction.setEnabled(getSelectedArtifacts() != null);
      openWithMenuManager.add(openInAtsTaskEditorAction);
      openInAtsTaskEditorAction.setEnabled(getSelectedTaskArtifacts() != null);
      if (AtsUtil.isAtsAdmin()) {
         openWithMenuManager.add(openInArtifactEditorAction);
         openInArtifactEditorAction.updateEnablement();
      }
      mm.insertBefore(MENU_GROUP_PRE, openWithMenuManager);

      if (AtsUtil.isAtsAdmin()) {
         mm.insertBefore(MENU_GROUP_PRE, new Separator());
         mm.insertBefore(MENU_GROUP_PRE, deletePurgeAtsObjectAction);
         deletePurgeAtsObjectAction.updateEnablement();
      }

      mm.insertBefore(XViewer.MENU_GROUP_PRE, new GroupMarker(MENU_GROUP_ATS_WORLD_OPEN));
      mm.insertBefore(MENU_GROUP_PRE, new Separator());

      // OTHER MENU BLOCK
      mm.insertBefore(MENU_GROUP_PRE, favoritesAction);
      favoritesAction.updateEnablement();

      mm.insertBefore(MENU_GROUP_PRE, subscribedAction);
      subscribedAction.updateEnablement();

      mm.insertBefore(MENU_GROUP_PRE, emailAction);
      emailAction.updateEnablement();

      mm.insertBefore(MENU_GROUP_PRE, resetActionArtifactAction);
      resetActionArtifactAction.setEnabled(getSelectedActionArtifacts().size() > 0);

      mm.insertAfter(XViewer.MENU_GROUP_PRE, new GroupMarker(MENU_GROUP_ATS_WORLD_OTHER));
      mm.insertAfter(MENU_GROUP_PRE, new Separator());

      for (IMenuActionProvider provider : menuActionProviders) {
         provider.updateMenuActionsForTable();
      }
   }

   @Override
   public void handleDoubleClick() {
      if (getSelectedArtifactItems().isEmpty()) {
         return;
      }
      Artifact art = getSelectedArtifactItems().iterator().next();
      AtsUtil.openATSAction(art, AtsOpenOption.OpenOneOrPopupSelect);
   }

   public List<Artifact> getLoadedArtifacts() {
      List<Artifact> arts = new ArrayList<Artifact>();
      if (getRoot() != null) {
         for (Object artifact : (Collection<?>) getRoot()) {
            if (artifact instanceof Artifact) {
               arts.add((Artifact) artifact);
            }
         }
      }
      return arts;
   }

   public void clear(boolean forcePend) {
      ((WorldContentProvider) getContentProvider()).clear(forcePend);
   }

   /**
    * Release resources
    */
   @Override
   public void dispose() {
      // Dispose of the table objects is done through separate dispose listener off tree
      // Tell the label provider to release its resources
      getLabelProvider().dispose();
      super.dispose();
   }

   @Override
   public List<Artifact> getSelectedArtifacts() {
      List<Artifact> arts = new ArrayList<Artifact>();
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            arts.add((Artifact) item.getData());
         }
      }
      return arts;
   }

   public List<TaskArtifact> getSelectedTaskArtifacts() {
      List<TaskArtifact> arts = new ArrayList<TaskArtifact>();
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            if (item.getData() instanceof TaskArtifact) {
               arts.add((TaskArtifact) item.getData());
            }
         }
      }
      return arts;
   }

   /**
    * @return true if all selected are Workflow OR are Actions with single workflow
    */
   public boolean isSelectedTeamWorkflowArtifacts() {
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            if (item.getData() instanceof ActionArtifact) {
               try {
                  if (((ActionArtifact) item.getData()).getTeamWorkFlowArtifacts().size() != 1) {
                     return false;
                  }
               } catch (OseeCoreException ex) {
                  // Do Nothing
               }
            } else if (!(item.getData() instanceof TeamWorkFlowArtifact)) {
               return false;
            }
         }
      }
      return true;
   }

   /**
    * @return all selected Workflow and any workflow that have Actions with single workflow
    */
   @Override
   public Set<TeamWorkFlowArtifact> getSelectedTeamWorkflowArtifacts() {
      Set<TeamWorkFlowArtifact> teamArts = new HashSet<TeamWorkFlowArtifact>();
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            if (item.getData() instanceof TeamWorkFlowArtifact) {
               teamArts.add((TeamWorkFlowArtifact) item.getData());
            }
            if (item.getData() instanceof ActionArtifact) {
               try {
                  if (((ActionArtifact) item.getData()).getTeamWorkFlowArtifacts().size() == 1) {
                     teamArts.addAll(((ActionArtifact) item.getData()).getTeamWorkFlowArtifacts());
                  }
               } catch (OseeCoreException ex) {
                  // Do Nothing
               }
            }
         }
      }
      return teamArts;
   }

   /**
    * @return all selected Workflow and any workflow that have Actions with single workflow
    */
   @Override
   public Set<AbstractWorkflowArtifact> getSelectedSMAArtifacts() {
      Set<AbstractWorkflowArtifact> smaArts = new HashSet<AbstractWorkflowArtifact>();
      try {
         Iterator<?> i = ((IStructuredSelection) getSelection()).iterator();
         while (i.hasNext()) {
            Object obj = i.next();
            if (obj instanceof AbstractWorkflowArtifact) {
               smaArts.add((AbstractWorkflowArtifact) obj);
            } else if (obj instanceof ActionArtifact) {
               smaArts.addAll(((ActionArtifact) obj).getTeamWorkFlowArtifacts());
            }
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
      }
      return smaArts;
   }

   public Set<ActionArtifact> getSelectedActionArtifacts() {
      Set<ActionArtifact> actionArts = new HashSet<ActionArtifact>();
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            if (item.getData() instanceof ActionArtifact) {
               actionArts.add((ActionArtifact) item.getData());
            }
         }
      }
      return actionArts;
   }

   public void setCancelledNotification() {
      TreeItem item = getTree().getItem(0);
      if (item.getData() instanceof String) {
         item.setData(DefaultTeamState.Cancelled.name());
      }
      refresh(item.getData());
   }

   /**
    * @param title string to be used in reporting
    */
   public void setReportingTitle(String title) {
      this.title = title;
   }

   /**
    * @return Returns the title.
    */
   public String getTitle() {
      return title;
   }

   @Override
   public void load(Collection<Object> objects) {
      Set<Artifact> arts = new HashSet<Artifact>();
      for (Object obj : objects) {
         if (obj instanceof IWorldViewArtifact) {
            arts.add((Artifact) obj);
         }
      }

      setInput(arts);
   }

   public List<Artifact> getSelectedArtifactItems() {
      List<Artifact> arts = new ArrayList<Artifact>();
      TreeItem items[] = getTree().getSelection();
      if (items.length > 0) {
         for (TreeItem item : items) {
            arts.add((Artifact) item.getData());
         }
      }
      return arts;
   }

   @Override
   public String getStatusString() {
      return extendedStatusString;
   }

   @Override
   public boolean handleLeftClickInIconArea(TreeColumn treeColumn, TreeItem treeItem) {
      try {
         Artifact useArt = (Artifact) treeItem.getData();
         boolean modified = false;
         if (useArt instanceof ActionArtifact) {
            if (((ActionArtifact) useArt).getTeamWorkFlowArtifacts().size() == 1) {
               useArt = ((ActionArtifact) useArt).getTeamWorkFlowArtifacts().iterator().next();
            } else {
               return false;
            }
         }
         if (modified) {
            update(useArt, null);
            return true;
         }
      } catch (OseeCoreException ex) {
         OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
      }
      return false;
   }

   @Override
   public boolean handleAltLeftClick(TreeColumn treeColumn, TreeItem treeItem) {
      try {
         Artifact useArt = (Artifact) treeItem.getData();
         boolean handled = super.handleAltLeftClick(treeColumn, treeItem);
         if (handled) {
            return true;
         }
         if (!(treeColumn.getData() instanceof XViewerColumn)) {
            return false;
         }
         XViewerColumn xCol = (XViewerColumn) treeColumn.getData();
         if (useArt instanceof ActionArtifact) {
            if (((ActionArtifact) useArt).getTeamWorkFlowArtifacts().size() == 1) {
               useArt = ((ActionArtifact) useArt).getTeamWorkFlowArtifacts().iterator().next();
            } else {
               return false;
            }
         }
         AbstractWorkflowArtifact sma = (AbstractWorkflowArtifact) useArt;
         boolean modified = false;
         if (modified && isAltLeftClickPersist()) {
            sma.persist("persist attribute change");
         }
         if (modified) {
            update(useArt, null);
            return true;
         }
      } catch (Exception ex) {
         OseeLog.log(AtsPlugin.class, OseeLevel.SEVERE_POPUP, ex);
      }
      return false;
   }

   public String getExtendedStatusString() {
      return extendedStatusString;
   }

   public void setExtendedStatusString(String extendedStatusString) {
      this.extendedStatusString = extendedStatusString;
      updateStatusLabel();
   }

   /**
    * store off parent goalItem in label provider so it can determine parent when providing order of goal member
    */
   @Override
   protected void doUpdateItem(Item item, Object element) {
      if (item instanceof TreeItem) {
         GoalArtifact parentGoalArtifact = GoalOrderColumn.getParentGoalArtifact((TreeItem) item);
         if (parentGoalArtifact != null) {
            ((WorldLabelProvider) getLabelProvider()).setParentGoal(parentGoalArtifact);
         }
      }
      super.doUpdateItem(item, element);
   }

   public void addMenuActionProvider(IMenuActionProvider provider) {
      menuActionProviders.add(provider);
   }

   @Override
   public List<Artifact> getSelectedAtsArtifacts() {
      List<Artifact> artifacts = new ArrayList<Artifact>();
      Iterator<?> i = ((IStructuredSelection) getSelection()).iterator();
      while (i.hasNext()) {
         Object obj = i.next();
         if (obj instanceof AbstractWorkflowArtifact) {
            artifacts.add((AbstractWorkflowArtifact) obj);
         }
      }
      return artifacts;
   }

   @Override
   public boolean isAltLeftClickPersist() {
      return true;
   }
}
