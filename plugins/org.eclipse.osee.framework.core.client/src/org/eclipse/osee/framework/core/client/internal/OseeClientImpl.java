/*********************************************************************
 * Copyright (c) 2012, 2022 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *     Boeing - add SynchronizationEndpoint
 **********************************************************************/

package org.eclipse.osee.framework.core.client.internal;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.activity.api.ActivityLogEndpoint;
import org.eclipse.osee.define.rest.api.DefineBranchEndpointApi;
import org.eclipse.osee.define.rest.api.git.GitEndpoint;
import org.eclipse.osee.define.rest.api.importing.ImportEndpoint;
import org.eclipse.osee.define.rest.api.publisher.datarights.DataRightsEndpoint;
import org.eclipse.osee.define.rest.api.publisher.publishing.PublishingEndpoint;
import org.eclipse.osee.define.rest.api.publisher.templatemanager.TemplateManagerEndpoint;
import org.eclipse.osee.define.rest.api.synchronization.SynchronizationEndpoint;
import org.eclipse.osee.define.rest.api.toggles.TogglesEndpoint;
import org.eclipse.osee.framework.core.OseeApiBase;
import org.eclipse.osee.framework.core.access.IAccessControlService;
import org.eclipse.osee.framework.core.client.OseeClient;
import org.eclipse.osee.framework.core.client.QueryBuilder;
import org.eclipse.osee.framework.core.client.TogglesClientImpl;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.AttributeId;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.TransactionId;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.server.ide.api.SessionEndpoint;
import org.eclipse.osee.framework.server.ide.api.client.ClientEndpoint;
import org.eclipse.osee.orcs.rest.model.ApiKeyEndpoint;
import org.eclipse.osee.orcs.rest.model.ApplicabilityEndpoint;
import org.eclipse.osee.orcs.rest.model.ApplicabilityUiEndpoint;
import org.eclipse.osee.orcs.rest.model.ArtifactEndpoint;
import org.eclipse.osee.orcs.rest.model.BranchEndpoint;
import org.eclipse.osee.orcs.rest.model.DatastoreEndpoint;
import org.eclipse.osee.orcs.rest.model.ExceptionRegistryEndpoint;
import org.eclipse.osee.orcs.rest.model.GridCommanderEndpoint;
import org.eclipse.osee.orcs.rest.model.IndexerEndpoint;
import org.eclipse.osee.orcs.rest.model.OrcsWriterEndpoint;
import org.eclipse.osee.orcs.rest.model.RelationEndpoint;
import org.eclipse.osee.orcs.rest.model.ResourcesEndpoint;
import org.eclipse.osee.orcs.rest.model.TransactionEndpoint;
import org.eclipse.osee.orcs.rest.model.TypesEndpoint;
import org.eclipse.osee.orcs.rest.model.search.artifact.Predicate;
import org.eclipse.osee.orcs.rest.model.search.artifact.RequestType;
import org.eclipse.osee.orcs.rest.model.search.artifact.SearchRequest;
import org.eclipse.osee.orcs.rest.model.search.artifact.SearchResponse;
import org.eclipse.osee.orcs.rest.model.search.artifact.SearchResult;

/**
 * @author John Misinco
 * @author Roberto E. Escobar
 */
public class OseeClientImpl extends OseeApiBase implements OseeClient, QueryExecutor {

   // for ReviewOsgiXml public void setJaxRsApi(JaxRsApi jaxRsApi)
   // for ReviewOsgiXml public void setOrcsTokenService(OrcsTokenService tokenService)
   // for ReviewOsgiXml public void bindUserService(UserService userService) {

   private PredicateFactory predicateFactory;
   private IAccessControlService accessControlService;
   private TogglesClientImpl togglesClientImpl;
   private Supplier<DataRightsEndpoint> dataRightsCache;

   public void bindAccessControlService(IAccessControlService accessControlService) {
      this.accessControlService = accessControlService;
   }

   public void start(Map<String, Object> properties) {
      this.predicateFactory = new PredicateFactoryImpl();
      jaxRsApi().createClientFactory(userService());
      this.togglesClientImpl = TogglesClientImpl.create(this.getTogglesEndpoint());
   }

   public void stop() {
      this.predicateFactory = null;
      this.togglesClientImpl = null;
   }

   @Override
   public QueryBuilder createQueryBuilder(BranchId branch) {
      QueryOptions options = new QueryOptions();
      List<Predicate> predicates = new ArrayList<>();
      return new QueryBuilderImpl(branch, predicates, options, predicateFactory, this);
   }

   @Override
   public int getCount(BranchId branch, List<Predicate> predicates, QueryOptions options) {
      SearchResponse result = performSearch(RequestType.COUNT, branch, predicates, options);
      return result.getTotal();
   }

   @Override
   public SearchResult getResults(RequestType request, BranchId branch, List<Predicate> predicates,
      QueryOptions options) {
      SearchResponse result = performSearch(request, branch, predicates, options);
      return result;
   }

   private SearchResponse performSearch(RequestType requestType, BranchId branch, List<Predicate> predicates,
      QueryOptions options) {
      Conditions.checkNotNull(requestType, "RequestType");
      TransactionId fromTx = TransactionId.valueOf(0);
      if (options.isHistorical()) {
         fromTx = options.getFromTransaction();
      }

      boolean includeDeleted = false;
      if (options.areDeletedIncluded()) {
         includeDeleted = true;
      }

      SearchRequest params = new SearchRequest(branch, predicates, requestType, fromTx, includeDeleted);
      WebTarget target = jaxRsApi().newTarget("orcs/branch/" + branch.getIdString() + "/artifact/search/v1");
      return target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(params), SearchResponse.class);

   }

   private <T> T getOrcsBranchEndpoint(Class<T> clazz, BranchId branch) {
      return jaxRsApi().newProxy("orcs/branch/" + branch.getIdString(), clazz);
   }

   /**
    * Creates a new endpoint proxy with the first URL path segment of &quot;define&quot;.
    *
    * @param <T> the interface class of the endpoint to create a proxy for.
    * @param clazz the {@link Class} of the interface to create a proxy for.
    * @return a proxy for the specified interface.
    */

   private <T> T getDefineEndpoint(Class<T> clazz) {
      return jaxRsApi().newProxy("define", clazz);
   }

   /**
    * Creates a new endpoint proxy with the first URL path segment of &quot;orcs&quot;.
    *
    * @param <T> the interface class of the endpoint to create a proxy for.
    * @param clazz the {@link Class} of the interface to create a proxy for.
    * @return a proxy for the specified interface.
    */

   private <T> T getOrcsEndpoint(Class<T> clazz) {
      return jaxRsApi().newProxy("orcs", clazz);
   }

   @Override
   public BranchEndpoint getBranchEndpoint() {
      return getOrcsEndpoint(BranchEndpoint.class);
   }

   @Override
   public ApiKeyEndpoint getApiKeyEndpoint() {
      return getOrcsEndpoint(ApiKeyEndpoint.class);
   }

   /**
    * {@inheritDoc}
    */

   @Override
   public ExceptionRegistryEndpoint getExceptionRegistryEndpoint() {
      return this.getOrcsEndpoint(ExceptionRegistryEndpoint.class);
   }

   @Override
   public TransactionEndpoint getTransactionEndpoint() {
      return getOrcsEndpoint(TransactionEndpoint.class);
   }

   @Override
   public RelationEndpoint getRelationEndpoint(BranchId branch) {
      return getOrcsBranchEndpoint(RelationEndpoint.class, branch);
   }

   @Override
   public TypesEndpoint getTypesEndpoint() {
      return getOrcsEndpoint(TypesEndpoint.class);
   }

   @Override
   public IndexerEndpoint getIndexerEndpoint() {
      return getOrcsEndpoint(IndexerEndpoint.class);
   }

   @Override
   public ClientEndpoint getClientEndpoint() {
      return jaxRsApi().newProxy("ide", ClientEndpoint.class);
   }

   @Override
   public ResourcesEndpoint getResourcesEndpoint() {
      return getOrcsEndpoint(ResourcesEndpoint.class);
   }

   @Override
   public DatastoreEndpoint getDatastoreEndpoint() {
      return getOrcsEndpoint(DatastoreEndpoint.class);
   }

   @Override
   public OrcsWriterEndpoint getOrcsWriterEndpoint() {
      return getOrcsEndpoint(OrcsWriterEndpoint.class);
   }

   @Override
   public ApplicabilityEndpoint getApplicabilityEndpoint(BranchId branch) {
      return getOrcsBranchEndpoint(ApplicabilityEndpoint.class, branch);
   }

   @Override
   public ApplicabilityUiEndpoint getApplicabilityUiEndpoint() {
      return getOrcsEndpoint(ApplicabilityUiEndpoint.class);
   }

   @Override
   public ArtifactEndpoint getArtifactEndpoint(BranchId branch) {
      return getOrcsBranchEndpoint(ArtifactEndpoint.class, branch);
   }

   @Override
   public ActivityLogEndpoint getActivityLogEndpoint() {
      return jaxRsApi().newProxy("", ActivityLogEndpoint.class);
   }

   @Override
   public SessionEndpoint getSessionEndpoint() {
      return jaxRsApi().newProxy("ide", SessionEndpoint.class);
   }

   /*
    * Setup Define Endpoints
    */
   @Override
   public DefineBranchEndpointApi getDefineBranchEndpoint() {
      return this.getDefineEndpoint(DefineBranchEndpointApi.class);
   }

   /**
    * DataRightsEndpoint is used in all WordTemplateProcessorClient which can cause context menus to instantiate a new
    * endpoint for every artifact selected. Cache until all publishing is on server.
    */
   @Override
   public DataRightsEndpoint getDataRightsEndpoint() {
      if (dataRightsCache == null) {
         dataRightsCache = Suppliers.memoizeWithExpiration(getDataRightsEndpointSupplier(), 10, TimeUnit.MINUTES);
      }
      return dataRightsCache.get();
   }

   private Supplier<DataRightsEndpoint> getDataRightsEndpointSupplier() {
      return new Supplier<DataRightsEndpoint>() {
         @Override
         public DataRightsEndpoint get() {
            return getDefineEndpoint(DataRightsEndpoint.class);
         }
      };
   }

   @Override
   public GitEndpoint getGitEndpoint() {
      return this.getDefineEndpoint(GitEndpoint.class);
   }

   @Override
   public GridCommanderEndpoint getGridCommanderEndpoint(BranchId branch) {
      return getOrcsBranchEndpoint(GridCommanderEndpoint.class, branch);
   }

   @Override
   public ImportEndpoint getImportEndpoint() {
      return this.getDefineEndpoint(ImportEndpoint.class);
   }

   @Override
   public PublishingEndpoint getPublishingEndpoint() {
      return this.getDefineEndpoint(PublishingEndpoint.class);
   }

   @Override
   public SynchronizationEndpoint getSynchronizationEndpoint() {
      return this.getDefineEndpoint(SynchronizationEndpoint.class);
   }

   @Override
   public TemplateManagerEndpoint getTemplateManagerEndpoint() {
      return this.getDefineEndpoint(TemplateManagerEndpoint.class);
   }

   @Override
   public TogglesEndpoint getTogglesEndpoint() {
      return this.getDefineEndpoint(TogglesEndpoint.class);
   }

   @Override
   public TogglesClientImpl getTogglesClient() {
      return this.togglesClientImpl;
   }

   @Override
   public String loadAttributeValue(AttributeId attrId, TransactionId transactionId, ArtifactToken artifact) {
      String url = String.format("orcs/branch/%s/artifact/%s/attribute/%s/version/%s/text",
         artifact.getBranchIdString(), artifact.getIdString(), attrId.getIdString(), transactionId.getIdString());
      WebTarget target = jaxRsApi().newTarget(url);
      return target.request(MediaType.TEXT_PLAIN).get(String.class);
   }

   @Override
   public IAccessControlService getAccessControlService() {
      return accessControlService;
   }
}