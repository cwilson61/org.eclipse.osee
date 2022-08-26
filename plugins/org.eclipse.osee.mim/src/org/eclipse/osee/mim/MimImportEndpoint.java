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

package org.eclipse.osee.mim;

import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.eclipse.osee.mim.types.MimImportSummary;
import org.eclipse.osee.mim.types.MimImportToken;

/**
 * @author Ryan T. Baldwin
 */
@Path("import")
public interface MimImportEndpoint {

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<MimImportToken> getImportOptions();
   
   @POST
   @Path("icd")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public MimImportSummary getImportSummary(@Multipart("file") InputStream file);

}