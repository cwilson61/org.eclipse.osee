/*
 * Created on Oct 27, 2010
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.ats.column;

import java.util.logging.Level;
import org.eclipse.osee.ats.core.review.ReviewFormalType;
import org.eclipse.osee.ats.core.type.AtsAttributeTypes;
import org.eclipse.osee.ats.internal.AtsPlugin;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsAttributeValueColumn;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.swt.SWT;

public class ReviewFormalTypeColumn extends XViewerAtsAttributeValueColumn {

   public static ReviewFormalTypeColumn instance = new ReviewFormalTypeColumn();

   public static ReviewFormalTypeColumn getInstance() {
      return instance;
   }

   private ReviewFormalTypeColumn() {
      super(AtsAttributeTypes.ReviewFormalType, 22, SWT.CENTER, true, SortDataType.String, true, "");
   }

   /**
    * XViewer uses copies of column definitions so originals that are registered are not corrupted. Classes extending
    * XViewerValueColumn MUST extend this constructor so the correct sub-class is created
    */
   @Override
   public ReviewFormalTypeColumn copy() {
      ReviewFormalTypeColumn newXCol = new ReviewFormalTypeColumn();
      super.copy(this, newXCol);
      return newXCol;
   }

   public static ReviewFormalType getReviewFormalType(Artifact artifact) throws OseeCoreException {
      String value = artifact.getSoleAttributeValue(AtsAttributeTypes.ReviewFormalType, "");
      if (Strings.isValid(value)) {
         try {
            return ReviewFormalType.valueOf(value);
         } catch (IllegalArgumentException ex) {
            OseeLog.log(AtsPlugin.class, Level.SEVERE, ex, "Unexpected formal type [%s]", value);
         }
      }
      return null;
   }
}
