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
package org.eclipse.osee.framework.branch.management.exchange.handler;

import java.util.Map;
import org.eclipse.osee.framework.db.connection.ConnectionHandler;
import org.eclipse.osee.framework.db.connection.ConnectionHandlerStatement;
import org.eclipse.osee.framework.db.connection.DbUtil;
import org.eclipse.osee.framework.db.connection.info.SQL3DataType;
import org.eclipse.osee.framework.jdk.core.util.Strings;

/**
 * @author Roberto E. Escobar
 */
public class RelationalTypeCheckSaxHandler extends RelationalSaxHandler {

   public static RelationalTypeCheckSaxHandler newCacheAllDataRelationalTypeCheckSaxHandler() {
      return new RelationalTypeCheckSaxHandler(true, 0);
   }

   public static RelationalTypeCheckSaxHandler newLimitedCacheRelationalTypeCheckSaxHandler(int cacheLimit) {
      return new RelationalTypeCheckSaxHandler(false, cacheLimit);
   }

   private RelationalTypeCheckSaxHandler(boolean isCacheAll, int cacheLimit) {
      super(isCacheAll, cacheLimit);
   }

   @Override
   protected void processData(Map<String, String> fieldMap) throws Exception {
      //      System.out.println(String.format("Table: [%s] Data: %s ", getMetaData(), fieldMap));
      String typeField = "art_type_id";
      String nameField = "name";
      String name = fieldMap.get(nameField);
      String typeId = fieldMap.get(typeField);
      if (Strings.isValid(name)) {
         if (!Strings.isValid(typeId)) {
            typeField = "attr_type_id";
         }
      } else {
         typeField = "rel_link_type_id";
         nameField = "type_name";
      }
      name = fieldMap.get(nameField);
      typeId = fieldMap.get(typeField);
      Long original = Strings.isValid(typeId) ? new Long(typeId) : -1;

      ConnectionHandlerStatement chStmt = null;
      try {
         chStmt =
               ConnectionHandler.runPreparedQuery(getConnection(), String.format("select %s from %s where %s =?",
                     typeField, getMetaData().getTableName(), nameField), SQL3DataType.VARCHAR, name);
         if (chStmt.next()) {
            getTranslator().addMappingTo(typeField, original, chStmt.getRset().getLong(1));
         }
      } finally {
         DbUtil.close(chStmt);
      }
   }
}
