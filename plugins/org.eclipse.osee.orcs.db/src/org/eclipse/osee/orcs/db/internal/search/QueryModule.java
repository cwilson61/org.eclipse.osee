/*******************************************************************************
 * Copyright (c) 2012 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.db.internal.search;

import static org.eclipse.osee.orcs.db.internal.search.Engines.newArtifactQueryEngine;
import static org.eclipse.osee.orcs.db.internal.search.Engines.newBranchQueryEngine;
import static org.eclipse.osee.orcs.db.internal.search.Engines.newIndexingEngine;
import static org.eclipse.osee.orcs.db.internal.search.Engines.newTaggingEngine;
import org.eclipse.osee.executor.admin.ExecutorAdmin;
import org.eclipse.osee.framework.core.model.cache.BranchCache;
import org.eclipse.osee.framework.core.services.IdentityService;
import org.eclipse.osee.framework.database.IOseeDatabaseService;
import org.eclipse.osee.framework.resource.management.IResourceManager;
import org.eclipse.osee.logger.Log;
import org.eclipse.osee.orcs.core.ds.DataLoaderFactory;
import org.eclipse.osee.orcs.core.ds.QueryEngine;
import org.eclipse.osee.orcs.core.ds.QueryEngineIndexer;
import org.eclipse.osee.orcs.data.AttributeTypes;
import org.eclipse.osee.orcs.db.internal.SqlProvider;
import org.eclipse.osee.orcs.db.internal.search.engines.QueryEngineImpl;
import org.eclipse.osee.orcs.db.internal.search.indexer.IndexerConstants;
import org.eclipse.osee.orcs.db.internal.search.tagger.TaggingEngine;

/**
 * @author Roberto E. Escobar
 */
public class QueryModule {

   private final Log logger;
   private final ExecutorAdmin executorAdmin;
   private final IOseeDatabaseService dbService;
   private final IdentityService idService;
   private final SqlProvider sqlProvider;

   private TaggingEngine taggingEngine;
   private QueryEngineIndexer queryIndexer;

   public QueryModule(Log logger, ExecutorAdmin executorAdmin, IOseeDatabaseService dbService, IdentityService idService, SqlProvider sqlProvider) {
      super();
      this.logger = logger;
      this.executorAdmin = executorAdmin;
      this.dbService = dbService;
      this.idService = idService;
      this.sqlProvider = sqlProvider;
   }

   public void startIndexer(IResourceManager resourceManager) throws Exception {
      taggingEngine = newTaggingEngine(logger);
      queryIndexer = newIndexingEngine(logger, dbService, idService, taggingEngine, executorAdmin, resourceManager);

      executorAdmin.createFixedPoolExecutor(IndexerConstants.INDEXING_CONSUMER_EXECUTOR_ID, 4);
   }

   public void stopIndexer() throws Exception {
      queryIndexer = null;
      taggingEngine = null;
      executorAdmin.shutdown(IndexerConstants.INDEXING_CONSUMER_EXECUTOR_ID);
   }

   public QueryEngineIndexer getQueryIndexer() {
      return queryIndexer;
   }

   public QueryEngine createQueryEngine(DataLoaderFactory objectLoader, BranchCache branchCache, AttributeTypes attrTypes) {
      QueryCallableFactory factory1 =
         newArtifactQueryEngine(logger, dbService, idService, sqlProvider, taggingEngine, executorAdmin, objectLoader,
            branchCache, attrTypes);
      QueryCallableFactory factory2 = newBranchQueryEngine(logger, dbService, idService, sqlProvider, objectLoader);
      return new QueryEngineImpl(factory1, factory2);
   }

}
