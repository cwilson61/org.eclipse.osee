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
package org.eclipse.osee.framework.ui.skynet;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.osee.framework.db.connection.ConnectionHandler;
import org.eclipse.osee.framework.db.connection.IDbConnectionListener;
import org.eclipse.osee.framework.db.connection.OseeDbConnection;
import org.eclipse.osee.framework.db.connection.info.DbDetailData;
import org.eclipse.osee.framework.skynet.core.dbinit.ApplicationServer;
import org.eclipse.osee.framework.ui.plugin.util.OverlayImage;
import org.eclipse.osee.framework.ui.skynet.widgets.dialog.HtmlDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Jeff C. Phillips
 */
public class SkynetConnectionContributionItem extends SkynetContributionItem implements IDbConnectionListener {
   private static final DbDetailData dbData = OseeDbConnection.getDefaultDatabaseService().getDatabaseDetails();
   private static final String dbName = dbData.getFieldValue(DbDetailData.ConfigField.DatabaseName);
   private static final String userName = dbData.getFieldValue(DbDetailData.ConfigField.UserName);
   private static final String ID = "skynet.connection";
   private static final SkynetGuiPlugin skynetGuiPlugin = SkynetGuiPlugin.getInstance();
   private static final Image ENABLED = skynetGuiPlugin.getImage("repository.gif");
   private static final Image DISABLED =
         new OverlayImage(ENABLED, skynetGuiPlugin.getImageDescriptor("red_slash.gif")).createImage();
   private static final String ENABLED_TOOLTIP = "Database is connected to " + dbName + " as " + userName + ".";
   private static final String DISABLED_TOOLTIP = "Database is disconnected.";

   public SkynetConnectionContributionItem() {
      super(ID, ENABLED, DISABLED, ENABLED_TOOLTIP, DISABLED_TOOLTIP);
      init();
   }

   private void init() {
      updateStatus(OseeDbConnection.hasOpenConnection());
      ConnectionHandler.addListener(this);
      setActionHandler(new Action() {
         /*
          * (non-Javadoc)
          * 
          * @see org.eclipse.jface.action.Action#run()
          */
         @Override
         public void run() {
            (new HtmlDialog("OSEE Session", "", ApplicationServer.getOseeSession().toString())).open();
         }
      });
   }

   public static void addTo(IStatusLineManager manager) {
      for (IContributionItem item : manager.getItems())
         if (item instanceof SkynetConnectionContributionItem) return;
      manager.add(new SkynetConnectionContributionItem());
   }

   public static void addTo(ViewPart view, boolean update) {
      addTo(view.getViewSite().getActionBars().getStatusLineManager());

      if (update) view.getViewSite().getActionBars().updateActionBars();
   }

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.database.IDbConnectionListener#onConnectionStatusUpdate(boolean)
    */
   @Override
   public void onConnectionStatusUpdate(final boolean open) {
      Display.getDefault().asyncExec(new Runnable() {
         @Override
         public void run() {
            updateStatus(open);
         }
      });
   }
}
