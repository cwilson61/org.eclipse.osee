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
package org.eclipse.osee.orcs.rest.client.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.osee.framework.core.data.IOseeBranch;
import org.eclipse.osee.framework.core.data.OseeCodeVersion;
import org.eclipse.osee.jaxrs.client.JaxRsClient;
import org.eclipse.osee.jaxrs.client.OseeClientProperties;
import org.eclipse.osee.orcs.rest.client.OseeClient;
import org.eclipse.osee.orcs.rest.client.QueryBuilder;
import org.eclipse.osee.orcs.rest.client.internal.search.PredicateFactory;
import org.eclipse.osee.orcs.rest.client.internal.search.PredicateFactoryImpl;
import org.eclipse.osee.orcs.rest.client.internal.search.QueryBuilderImpl;
import org.eclipse.osee.orcs.rest.client.internal.search.QueryExecutorV1;
import org.eclipse.osee.orcs.rest.client.internal.search.QueryExecutorV1.BaseUriBuilder;
import org.eclipse.osee.orcs.rest.client.internal.search.QueryOptions;
import org.eclipse.osee.orcs.rest.model.Client;
import org.eclipse.osee.orcs.rest.model.search.artifact.Predicate;
import com.google.inject.Inject;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * @author John Misinco
 * @author Roberto E. Escobar
 */
public class OseeClientImpl implements OseeClient, BaseUriBuilder {

   private PredicateFactory predicateFactory;
   private QueryExecutorV1 executor;

   private JaxRsClient client;

   @Inject
   public void setJaxRsClient(JaxRsClient client) {
      this.client = client;
   }

   public void start() {
      predicateFactory = new PredicateFactoryImpl();
      executor = new QueryExecutorV1(client, this);
   }

   public void stop() {
      predicateFactory = null;
      executor = null;
   }

   @Override
   public QueryBuilder createQueryBuilder(IOseeBranch branch) {
      QueryOptions options = new QueryOptions();
      List<Predicate> predicates = new ArrayList<Predicate>();
      return new QueryBuilderImpl(branch, predicates, options, predicateFactory, executor);
   }

   @Override
   public UriBuilder newBuilder() {
      String serverUri = OseeClientProperties.getApplicationServerAddress();
      return UriBuilder.fromUri(serverUri).path("orcs");
   }

   @Override
   public boolean isClientVersionSupportedByApplicationServer() {
      boolean result = false;
      URI uri = newBuilder().path("client").build();
      WebResource resource = client.createResource(uri);
      Client clientResult = null;
      try {
         clientResult = resource.accept(MediaType.APPLICATION_XML).get(Client.class);
         if (clientResult != null) {
            result = clientResult.getSupportedVersions().contains(OseeCodeVersion.getVersion());
         }
      } catch (UniformInterfaceException ex) {
         throw client.handleException(ex);
      }
      return result;
   }

   @Override
   public boolean isApplicationServerAlive() {
      boolean alive = false;
      try {
         isClientVersionSupportedByApplicationServer();
         alive = true;
      } catch (Exception ex) {
         alive = false;
      }
      return alive;
   }
}
