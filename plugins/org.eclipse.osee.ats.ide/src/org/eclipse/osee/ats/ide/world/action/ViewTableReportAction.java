package org.eclipse.osee.ats.ide.world.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerTreeReport;
import org.eclipse.osee.framework.ui.swt.ImageManager;

/**
 * @author Donald G. Dunne
 */
public class ViewTableReportAction extends Action {

   private final XViewer xViewer;

   public ViewTableReportAction(XViewer xViewer) {
      super("View in HTML");
      this.xViewer = xViewer;
   }

   @Override
   public ImageDescriptor getImageDescriptor() {
      return ImageManager.getProgramImageDescriptor("html");
   }

   @Override
   public void run() {
      if (xViewer.getXViewerFactory().getXViewerTreeReport(xViewer) != null) {
         xViewer.getXViewerFactory().getXViewerTreeReport(xViewer).open();
      } else {
         new XViewerTreeReport(xViewer).open();
      }
   }

}