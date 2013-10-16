/*******************************************************************************
 * Copyright (c) 2013 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.db.internal.loader.executors;

import static org.eclipse.osee.orcs.db.internal.sql.RelationalConstants.MIN_FETCH_SIZE;
import org.eclipse.osee.executor.admin.HasCancellation;
import org.eclipse.osee.framework.database.IOseeDatabaseService;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.type.OseeStateException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.orcs.core.ds.LoadDataHandler;
import org.eclipse.osee.orcs.core.ds.Options;
import org.eclipse.osee.orcs.db.internal.loader.LoadUtil;
import org.eclipse.osee.orcs.db.internal.loader.SqlObjectLoader;
import org.eclipse.osee.orcs.db.internal.loader.criteria.CriteriaOrcsLoad;
import org.eclipse.osee.orcs.db.internal.search.QuerySqlContext;
import org.eclipse.osee.orcs.db.internal.sql.TableEnum;

/**
 * @author Andrew M. Finkbeiner
 */
public class QueryContextLoadExecutor extends AbstractLoadExecutor {

   private final QuerySqlContext queryContext;

   public QueryContextLoadExecutor(SqlObjectLoader loader, IOseeDatabaseService dbService, QuerySqlContext queryContext) {
      super(loader, dbService);
      this.queryContext = queryContext;
   }

   @Override
   public void load(HasCancellation cancellation, LoadDataHandler handler, CriteriaOrcsLoad criteria, Options options) throws OseeCoreException {
      int fetchSize = LoadUtil.computeFetchSize(MIN_FETCH_SIZE);
      OrcsObjectTypes typeToLoad = getObjectToLoad();
      switch (typeToLoad) {
         case TX:
            getLoader().loadTransactions(cancellation, handler, queryContext, fetchSize);
            break;
         case BRANCH:
            getLoader().loadBranches(cancellation, handler, queryContext, fetchSize);
            break;
         default:
            throw new OseeStateException("Unable to determine object to load from [%s]", queryContext);
      }
   }

   // This will be improved once we add the ability to specify what to load in the loader code.
   private OrcsObjectTypes getObjectToLoad() {
      OrcsObjectTypes type = OrcsObjectTypes.UNKNOWN;
      String sql = queryContext.getSql();
      if (Strings.isValid(sql)) {
         if (sql.contains(TableEnum.TX_DETAILS_TABLE.getName())) {
            type = OrcsObjectTypes.TX;
         } else if (sql.contains(TableEnum.BRANCH_TABLE.getName())) {
            type = OrcsObjectTypes.BRANCH;
         }
      }
      return type;
   }

   private static enum OrcsObjectTypes {
      ARTIFACT,
      BRANCH,
      TX,
      UNKNOWN;
   }
}
