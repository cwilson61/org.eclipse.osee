/*
 * Created on Oct 27, 2010
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.ats.field;

import org.eclipse.nebula.widgets.xviewer.IXViewerValueColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerCells;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.osee.ats.artifact.DecisionReviewArtifact;
import org.eclipse.osee.ats.artifact.DecisionReviewArtifact.DecisionReviewState;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsColumn;
import org.eclipse.osee.ats.world.WorldXViewerFactory;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.skynet.core.utility.Artifacts;
import org.eclipse.swt.SWT;

public class ReviewDeciderColumn extends XViewerAtsColumn implements IXViewerValueColumn {

   public static ReviewDeciderColumn instance = new ReviewDeciderColumn();

   public static ReviewDeciderColumn getInstance() {
      return instance;
   }

   private ReviewDeciderColumn() {
      super(WorldXViewerFactory.COLUMN_NAMESPACE + ".reviewDecider", "Review Decider", 100, SWT.LEFT, false,
         SortDataType.String, false, "Review Decider");
   }

   public ReviewDeciderColumn(String id, String name, int width, int align, boolean show, SortDataType sortDataType, boolean multiColumnEditable, String description) {
      super(id, name, width, align, show, sortDataType, multiColumnEditable, description);
   }

   /**
    * XViewer uses copies of column definitions so originals that are registered are not corrupted. Classes extending
    * XViewerValueColumn MUST extend this constructor so the correct sub-class is created
    */
   @Override
   public ReviewDeciderColumn copy() {
      return new ReviewDeciderColumn(getId(), getName(), getWidth(), getAlign(), isShow(), getSortDataType(),
         isMultiColumnEditable(), getDescription());
   }

   @Override
   public String getColumnText(Object element, XViewerColumn column, int columnIndex) {
      try {
         if (element instanceof DecisionReviewArtifact) {
            return Artifacts.toString("; ",
               ((DecisionReviewArtifact) element).getStateMgr().getAssignees(DecisionReviewState.Decision.name()));
         }
      } catch (OseeCoreException ex) {
         XViewerCells.getCellExceptionString(ex);
      }
      return "";
   }
}
