/*********************************************************************
 * Copyright (c) 2021 Boeing
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
package org.eclipse.osee.mim;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.mim.types.InterfaceNode;

/**
 * @author Luciano T. Vaglienti
 */
@Path("nodes")
public interface InterfaceNodeEndpoint {

   @GET()
   @Produces(MediaType.APPLICATION_JSON)
   /**
    * Gets all nodes
    *
    * @return all nodes
    */
   Collection<InterfaceNode> getAllNodes();

   @GET()
   @Path("{id}")
   @Produces(MediaType.APPLICATION_JSON)
   /**
    * Fetches a specific node
    *
    * @param nodeId id of node to fetch
    * @return node
    */
   InterfaceNode getNode(@PathParam("id") ArtifactId nodeId);

}