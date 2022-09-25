/*********************************************************************
 * Copyright (c) 2022 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/
package org.eclipse.osee.mim.internal;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.osee.framework.core.data.ApplicabilityToken;
import org.eclipse.osee.framework.jdk.core.util.io.excel.ExcelWorkbookReader;
import org.eclipse.osee.framework.jdk.core.util.io.excel.ExcelWorkbookWriter.WorkbookFormat;
import org.eclipse.osee.mim.MimImportApi;
import org.eclipse.osee.mim.types.InterfaceEnumeration;
import org.eclipse.osee.mim.types.InterfaceEnumerationSet;
import org.eclipse.osee.mim.types.MimImportSummary;
import org.eclipse.osee.mim.types.PlatformTypeImportToken;

/**
 * @author Ryan T. Baldwin
 */
public class PlatformTypeImportApiImpl implements MimImportApi {

   private final ExcelWorkbookReader reader;
   private MimImportSummary summary;

   private Long id = 1L;

   public PlatformTypeImportApiImpl(InputStream inputStream) {
      this.reader = new ExcelWorkbookReader(inputStream, WorkbookFormat.XLSX);
   }

   @Override
   public MimImportSummary getSummary() {
      summary = new MimImportSummary();

      reader.setActiveSheet(0);

      List<PlatformTypeImportToken> platformTypes = new LinkedList<>();

      int rowIndex = 1;
      while (reader.rowExists(rowIndex)) {
         String name = reader.getCellStringValue(rowIndex, 1);
         int bitSize = (int) reader.getCellNumericValue(rowIndex, 2);
         String logicalType = reader.getCellStringValue(rowIndex, 3);
         String minVal = reader.getCellStringValue(rowIndex, 4);
         String maxVal = reader.getCellStringValue(rowIndex, 5);
         String defaultVal = reader.getCellStringValue(rowIndex, 6);
         String units = reader.getCellStringValue(rowIndex, 7);
         String validRange = reader.getCellStringValue(rowIndex, 14);
         String description = reader.getCellStringValue(rowIndex, 15);

         PlatformTypeImportToken pType = new PlatformTypeImportToken(id, name, logicalType, bitSize + "", minVal,
            maxVal, units, description, defaultVal, validRange);
         incrementId();
         summary.getPlatformTypes().add(pType);

         if (logicalType.equals("enumeration")) {
            InterfaceEnumerationSet enumSet = new InterfaceEnumerationSet(id, name);
            enumSet.setDescription(description);
            enumSet.setApplicability(ApplicabilityToken.BASE);
            incrementId();
            summary.getEnumSets().add(enumSet);
            summary.getPlatformTypeEnumSetRelations().put(pType.getIdString(),
               new LinkedList<>(Arrays.asList(enumSet.getIdString())));

            String[] enumerations = description.split("\n");
            for (String enumeration : enumerations) {
               String[] split = enumeration.split(" = ");
               if (enumeration.isEmpty() || split.length < 2) {
                  continue;
               }
               InterfaceEnumeration enumToken = new InterfaceEnumeration(id, split[1]);
               enumToken.setOrdinal(Integer.parseInt(split[0]));
               enumToken.setApplicability(ApplicabilityToken.BASE);
               incrementId();
               summary.getEnums().add(enumToken);
               List<String> rels =
                  summary.getEnumSetEnumRelations().getOrDefault(enumSet.getIdString(), new LinkedList<>());
               rels.add(enumToken.getIdString());
               summary.getEnumSetEnumRelations().put(enumSet.getIdString(), rels);
            }

         }

         rowIndex++;
      }

      summary.getPlatformTypes().addAll(platformTypes);

      return summary;
   }

   private void incrementId() {
      id++;
   }

}