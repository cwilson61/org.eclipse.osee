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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.mim.types.PlatformTypeToken;

/**
 * @author Luciano T. Vaglienti
 */
@Path("types")
public interface PlatformTypesEndpoint {

   @GET()
   @Produces(MediaType.APPLICATION_JSON)
   /**
    * Gets List of all Platform Types
    *
    * @return List of platform types
    */
   Collection<PlatformTypeToken> getPlatformTypes();

   @PUT()
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON})
   /**
    * Updates a Platform Type for Branch
    *
    * @param platformTypeToken platformType to update
    * @return Api Response success/failure
    */
   XResultData updatePlatformType(PlatformTypeToken platformTypeToken);

   @PATCH()
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON})
   /**
    * Performs a partial update of a platform type for branch
    *
    * @param platformTypeToken platformType to update
    * @return Api Response success/failure
    */
   XResultData patchPlatformType(PlatformTypeToken platformTypeToken);

   @POST()
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON})
   /**
    * Creates a new Platform Type for Branch
    *
    * @param platformTypeToken platformType to create
    * @return Api Response success/failure
    */
   XResultData createPlatformType(PlatformTypeToken platformTypeToken);

   @POST()
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON})
   /**
    * Creates a new Platform Type for Branch
    *
    * @param platformTypeToken logical type to base platformType off of
    * @return Api Response success/failure
    */
   XResultData createPlatformType(Object platformTypeToken);

   @GET()
   @Path("{type_id}")
   @Produces({MediaType.APPLICATION_JSON})
   /**
    * Gets a specific platform type based on its id
    *
    * @param typeId id of platform type
    * @return platform type
    */
   PlatformTypeToken getPlatformType(@PathParam("type_id") String typeId);

   @DELETE()
   @Path("{type_id}")
   @Produces({MediaType.APPLICATION_JSON})
   /**
    * Removes a platform type
    *
    * @param typeId id of platform type
    * @return Api Response success/failure
    */
   XResultData removePlatformType(@PathParam("type_id") String typeId);

}