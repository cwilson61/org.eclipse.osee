/*
 * Created on Jul 14, 2008
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.ats.util.xviewer.column;

import java.sql.SQLException;
import org.eclipse.osee.ats.world.IWorldViewArtifact;
import org.eclipse.osee.ats.world.WorldXViewerFactory;
import org.eclipse.osee.framework.skynet.core.exception.OseeCoreException;
import org.eclipse.osee.framework.ui.skynet.widgets.xviewer.XViewer;
import org.eclipse.osee.framework.ui.skynet.widgets.xviewer.XViewerColumn;
import org.eclipse.osee.framework.ui.skynet.widgets.xviewer.XViewerValueColumn;
import org.eclipse.swt.SWT;

/**
 * @author Donald G. Dunne
 */
public class XViewerSmaCreatedDateColumn extends XViewerValueColumn {

   public XViewerSmaCreatedDateColumn(XViewer viewer) {
      this("Created", viewer);
   }

   public XViewerSmaCreatedDateColumn(String name, XViewer viewer) {
      super(WorldXViewerFactory.COLUMN_NAMESPACE + "createdDate", name, 80, SWT.LEFT, true, SortDataType.Date, false,
            "Date this workflow was created.");
   }

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.ui.skynet.widgets.xviewer.XViewerValueColumn#getColumnText(java.lang.Object, org.eclipse.osee.framework.ui.skynet.widgets.xviewer.XViewerColumn)
    */
   @Override
   public String getColumnText(Object element, XViewerColumn column, int columnIndex) throws OseeCoreException, SQLException {
      if (element instanceof IWorldViewArtifact) {
         return ((IWorldViewArtifact) element).getWorldViewCreatedDateStr();
      }
      return super.getColumnText(element, column, columnIndex);
   }

}
