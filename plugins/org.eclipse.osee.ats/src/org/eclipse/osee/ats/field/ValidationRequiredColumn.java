/*
 * Created on Oct 27, 2010
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.ats.field;

import org.eclipse.osee.ats.artifact.AtsAttributeTypes;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsAttributeValueColumn;
import org.eclipse.osee.ats.world.WorldXViewerFactory;
import org.eclipse.osee.framework.core.data.IAttributeType;
import org.eclipse.swt.SWT;

public class ValidationRequiredColumn extends XViewerAtsAttributeValueColumn {

   public static ValidationRequiredColumn instance = new ValidationRequiredColumn();

   public static ValidationRequiredColumn getInstance() {
      return instance;
   }

   private ValidationRequiredColumn() {
      super(WorldXViewerFactory.COLUMN_NAMESPACE + ".validationRequired", AtsAttributeTypes.ValidationRequired, 80,
         SWT.LEFT, false, SortDataType.String, false,
         "If set, Originator will be asked to perform a review to\nensure changes are as expected.");
   }

   public ValidationRequiredColumn(IAttributeType attributeType, int width, int align, boolean show, SortDataType sortDataType, boolean multiColumnEditable) {
      super(attributeType, width, align, show, sortDataType, multiColumnEditable);
   }

   /**
    * XViewer uses copies of column definitions so originals that are registered are not corrupted. Classes extending
    * XViewerValueColumn MUST extend this constructor so the correct sub-class is created
    */
   @Override
   public ValidationRequiredColumn copy() {
      return new ValidationRequiredColumn(getAttributeType(), getWidth(), getAlign(), isShow(), getSortDataType(),
         isMultiColumnEditable());
   }

}
