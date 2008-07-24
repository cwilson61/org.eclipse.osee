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
package org.eclipse.osee.framework.ui.skynet.widgets.xviewer;

import org.eclipse.osee.framework.jdk.core.util.AXml;
import org.eclipse.swt.SWT;

/**
 * @author Donald G. Dunne
 */
public class XViewerColumn {

   private XViewer xViewer;
   private String id;
   private String name = "";
   private String description;
   private boolean multiColumnEditable = false;
   private int width;
   private int align;
   private boolean sortForward = true; // if true, sort alphabetically; else reverse
   private boolean show = true;
   private SortDataType sortDataType = SortDataType.String;
   private String toolTip = "";
   public enum SortDataType {
      Date, Float, Percent, String, String_MultiLine, Boolean, Integer
   };

   public XViewerColumn(String id, String name, int width, int align, boolean show, SortDataType sortDataType, boolean multiColumnEditable, String description) {
      super();
      this.id = id;
      this.name = name;
      this.width = width;
      this.align = align;
      this.show = show;
      this.sortDataType = sortDataType;
      this.multiColumnEditable = multiColumnEditable;
      this.description = description;
      this.toolTip = this.name;
   }

   /**
    * XViewer uses copies of column definitions so originals that are registered are not corrupted. Classes extending
    * XViewerColumn need to extend this method to copy extra stored fields
    * 
    * @param col
    */
   public XViewerColumn copy() {
      return new XViewerColumn(id, name, width, align, show, sortDataType, multiColumnEditable, description);
   }

   public XViewerColumn(XViewer xViewer, String xml) {
      this.xViewer = xViewer;
      setFromXml(xml);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof XViewerColumn) {
         return ((XViewerColumn) obj).getId().equals(id);
      }
      return super.equals(obj);
   }

   @Override
   public int hashCode() {
      return getId().hashCode();
   }

   public static String ID = "id";
   public static String NAME = "name";
   public static String WIDTH = "wdth";
   public static String ALIGN = "algn";
   public static String SORT_FORWARD = "srtFwd";
   public static String SHOW = "show";
   public static String XTREECOLUMN_TAG = "xCol";

   public String toXml() {
      StringBuffer sb = new StringBuffer("<" + XTREECOLUMN_TAG + ">");
      sb.append(AXml.addTagData(ID, id));
      sb.append(AXml.addTagData(NAME, name));
      sb.append(AXml.addTagData(WIDTH, width + ""));
      sb.append(AXml.addTagData(ALIGN, getAlignStoreName(align)));
      sb.append(AXml.addTagData(SORT_FORWARD, sortForward + ""));
      sb.append(AXml.addTagData(SHOW, show + ""));
      sb.append("</" + XTREECOLUMN_TAG + ">");
      return sb.toString();
   }

   public void setFromXml(String xml) {
      id = AXml.getTagData(xml, ID);
      name = AXml.getTagData(xml, NAME);
      width = AXml.getTagIntData(xml, WIDTH);
      align = getAlignStoreValue(AXml.getTagData(xml, ALIGN));
      sortForward = AXml.getTagBooleanData(xml, SORT_FORWARD);
      show = AXml.getTagBooleanData(xml, SHOW);
   }

   public static String getColumnId(String xml) {
      return AXml.getTagData(xml, ID);
   }

   public String getAlignStoreName(int align) {
      if (align == SWT.CENTER)
         return "center";
      else if (align == SWT.RIGHT)
         return "right";
      else
         return "left";
   }

   public int getAlignStoreValue(String str) {
      if (str.equals("center"))
         return SWT.CENTER;
      else if (str.equals("right"))
         return SWT.RIGHT;
      else
         return SWT.LEFT;
   }

   public String toString() {
      return name + " - " + id + "";
   }

   public int getAlign() {
      return align;
   }

   public void setAlign(int align) {
      this.align = align;
   }

   public String getId() {
      return id;
   }

   public int getWidth() {
      return width;
   }

   public XViewer getTreeViewer() {
      return xViewer;
   }

   public boolean isSortForward() {
      return sortForward;
   }

   public void setSortForward(boolean sortForward) {
      this.sortForward = sortForward;
   }

   public void reverseSort() {
      setSortForward(!sortForward);
   }

   public boolean isShow() {
      return show;
   }

   public void setShow(boolean show) {
      this.show = show;
   }

   /**
    * @return alternateName if exists, otherwise systemName
    */
   public String getName() {
      return name;
   }

   /**
    * @return the sortDataType
    */
   public SortDataType getSortDataType() {
      return sortDataType;
   }

   /**
    * @param sortDataType the sortDataType to set
    */
   public void setSortDataType(SortDataType sortDataType) {
      this.sortDataType = sortDataType;
   }

   /**
    * @param treeViewer the treeViewer to set
    */
   public void setXViewer(XViewer treeViewer) {
      this.xViewer = treeViewer;
   }

   /**
    * @return the toolTip
    */
   public String getToolTip() {
      return toolTip;
   }

   /**
    * @param toolTip the toolTip to set
    */
   public void setToolTip(String toolTip) {
      if (toolTip != null) this.toolTip = toolTip;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
      this.toolTip = getName() + ":\n" + getDescription();
   }

   public boolean isMultiColumnEditable() {
      return multiColumnEditable;
   }

   public void setMultiColumnEditable(boolean multiColumnEditable) {
      this.multiColumnEditable = multiColumnEditable;
   }

   public void setWidth(int width) {
      this.width = width;
   }

}
