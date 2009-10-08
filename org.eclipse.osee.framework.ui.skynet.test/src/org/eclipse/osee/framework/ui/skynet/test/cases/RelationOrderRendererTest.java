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
package org.eclipse.osee.framework.ui.skynet.test.cases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.eclipse.osee.framework.core.enums.RelationSide;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.GUID;
import org.eclipse.osee.framework.skynet.core.artifact.Branch;
import org.eclipse.osee.framework.skynet.core.linking.OseeLinkBuilder;
import org.eclipse.osee.framework.skynet.core.relation.order.IRelationSorter;
import org.eclipse.osee.framework.skynet.core.relation.order.RelationOrderBaseTypes;
import org.eclipse.osee.framework.skynet.core.relation.order.RelationOrderData;
import org.eclipse.osee.framework.skynet.core.relation.order.RelationSorterProvider;
import org.eclipse.osee.framework.ui.skynet.render.ArtifactGuidToWordML;
import org.eclipse.osee.framework.ui.skynet.render.RelationOrderRenderer;
import org.eclipse.osee.framework.ui.skynet.render.word.WordMLProducer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Roberto E. Escobar
 */
public class RelationOrderRendererTest {

   private static RelationOrderRenderer renderer;
   private static RelationSorterProvider sorterProvider;

   @BeforeClass
   public static void prepareTest() {
      MockArtifactGuidResolver resolver = new MockArtifactGuidResolver(null);

      sorterProvider = new RelationSorterProvider();
      renderer = new RelationOrderRenderer(resolver, sorterProvider);
   }

   @AfterClass
   public static void cleanupTest() {
      sorterProvider = null;
      renderer = null;
   }

   @Test
   public void testRenderingAllValid() throws OseeCoreException {
      RelationOrderData orderData = new MockRelationOrderData();
      List<Object[]> expectedData = new ArrayList<Object[]>();

      addData(orderData, expectedData, "Relation 1", RelationSide.SIDE_A,
            RelationOrderBaseTypes.LEXICOGRAPHICAL_ASC.getGuid(), "1", "2", "3");

      addData(orderData, expectedData, "Relation 2", RelationSide.SIDE_B, //
            RelationOrderBaseTypes.UNORDERED.getGuid(), "4", "5", "6");

      addData(orderData, expectedData, "Relation 3", RelationSide.OPPOSITE, //
            RelationOrderBaseTypes.USER_DEFINED.getGuid(), "7", "8", "9");

      checkRelationOrderRenderer(getExpected(expectedData), orderData);
   }

   @Test
   public void testRenderingOrderTypeNotFoud() throws OseeCoreException {
      RelationOrderData orderData = new MockRelationOrderData();
      List<Object[]> expectedData = new ArrayList<Object[]>();
      addData(orderData, expectedData, "Relation 1", RelationSide.SIDE_A, GUID.create(), "0", "1", "2");
      checkRelationOrderRenderer(getExpected(expectedData), orderData);
   }

   @Test
   public void testRenderingEmptyGuids() throws OseeCoreException {
      RelationOrderData orderData = new MockRelationOrderData();
      List<Object[]> expectedData = new ArrayList<Object[]>();
      addData(orderData, expectedData, "Relation 1", RelationSide.SIDE_A, RelationOrderBaseTypes.USER_DEFINED.getGuid());
      checkRelationOrderRenderer(getExpected(expectedData), orderData);
   }

   @Test
   public void testEmptyData() throws OseeCoreException {
      RelationOrderData orderData = new MockRelationOrderData();
      List<Object[]> expectedData = new ArrayList<Object[]>();
      checkRelationOrderRenderer(getExpected(expectedData), orderData);
   }

   private void addData(RelationOrderData orderData, List<Object[]> expectedData, String relationType, RelationSide side, String relationOrderIdGuid, String... guids) {
      List<String> artGuids = new ArrayList<String>();
      if (guids != null && guids.length > 0) {
         artGuids.addAll(Arrays.asList(guids));
      }
      orderData.addOrderList(relationType, side.name(), relationOrderIdGuid, artGuids);

      String expectedOrderId = relationOrderIdGuid;
      try {
         IRelationSorter sorter = sorterProvider.getRelationOrder(relationOrderIdGuid);
         expectedOrderId = sorter.getSorterId().prettyName();
      } catch (Exception ex) {
         // Do Nothing
      }
      expectedData.add(new Object[] {relationType, side.name(), expectedOrderId, artGuids});
   }

   private String getExpected(List<Object[]> data) {
      StringBuilder builder = new StringBuilder();
      builder.append("<wx:sub-section><w:tbl><w:tblPr><w:tblW w:w=\"7200\" w:type=\"dxa\"/><w:jc w:val=\"center\"/></w:tblPr>");
      if (data.isEmpty()) {
         builder.append("<w:tr>");
         builder.append("<w:tc>");
         builder.append(getCellData("None"));
         builder.append("</w:tc>");
         builder.append("</w:tr>");
      } else {
         for (Object[] dataArray : data) {
            builder.append("<w:tr>");
            for (int index = 0; index < dataArray.length; index++) {
               builder.append("<w:tc>");
               builder.append(getCellData(dataArray[index]));
               builder.append("</w:tc>");
            }
            builder.append("</w:tr>");
         }
      }
      builder.append("</w:tbl></wx:sub-section>");
      return builder.toString();
   }

   private String getCellData(Object object) {
      if (object instanceof Collection<?>) {
         Collection<?> values = (Collection<?>) object;
         if (!values.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (Object data : values) {
               builder.append(getCellData(data));
            }
            return builder.toString();
         } else {
            return getCellData("None");
         }
      } else {
         return "<w:p><w:r><w:t>" + object + "</w:t></w:r></w:p>";
      }
   }

   private void checkRelationOrderRenderer(String expected, RelationOrderData orderData) throws OseeCoreException {
      StringBuilder builder = new StringBuilder();
      WordMLProducer producer = new WordMLProducer(builder);
      renderer.toWordML(producer, null, orderData);
      Assert.assertEquals(expected, builder.toString());
   }

   private final static class MockRelationOrderData extends RelationOrderData {
      public MockRelationOrderData() {
         super(null, null);
      }
   }

   private static final class MockArtifactGuidResolver extends ArtifactGuidToWordML {

      public MockArtifactGuidResolver(OseeLinkBuilder linkBuilder) {
         super(linkBuilder);
      }

      @Override
      public List<String> resolveAsOseeLinks(Branch branch, List<String> artifactGuids) throws OseeCoreException {
         List<String> values = new ArrayList<String>();
         for (String guid : artifactGuids) {
            values.add(guid);
         }
         return values;
      }

   }
}
