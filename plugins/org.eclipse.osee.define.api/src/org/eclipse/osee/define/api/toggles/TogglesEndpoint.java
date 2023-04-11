/*********************************************************************
 * Copyright (c) 2023 Boeing
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

package org.eclipse.osee.define.api.toggles;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.framework.core.util.toggles.Toggles;

/**
 * This interface defines the REST API end points for getting toggle values.
 *
 * @author Loren K. Ashley
 */

@Path("toggles")
public interface TogglesEndpoint extends Toggles {

   /**
    * Makes a request to obtain the value of a toggle.
    *
    * @param name the name of the toggle.
    * @return the toggle value as a {@link Boolean}.
    */

   //@formatter:off
   @Override
   @GET
   @Path("{toggle}")
   @Consumes({MediaType.APPLICATION_JSON})
   @Produces({MediaType.APPLICATION_JSON})
   Boolean
      apply
         (
            @PathParam( "toggle" ) String name
         );
   //@formatter:on
}

/* EOF */