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

package org.eclipse.osee.testscript.internal;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.jdk.core.annotation.Swagger;
import org.eclipse.osee.testscript.ScriptApi;
import org.eclipse.osee.testscript.ScriptBatchEndpoint;
import org.eclipse.osee.testscript.ScriptDefEndpoint;
import org.eclipse.osee.testscript.ScriptDownloadEndpoint;
import org.eclipse.osee.testscript.ScriptImportEndpoint;
import org.eclipse.osee.testscript.ScriptResultEndpoint;
import org.eclipse.osee.testscript.ScriptSetDiffEndpoint;
import org.eclipse.osee.testscript.ScriptSetEndpoint;
import org.eclipse.osee.testscript.TestCaseEndpoint;
import org.eclipse.osee.testscript.TestPointEndpoint;
import org.eclipse.osee.testscript.TmoEndpoint;

/**
 * @author Stephen J. Molaro
 */

@Path("tmo")
@Swagger
public class TmoEndpointImpl implements TmoEndpoint {
   private final ScriptApi testScriptApi;

   public TmoEndpointImpl(ScriptApi testScriptApi) {
      this.testScriptApi = testScriptApi;
   }

   @Override
   @Path("{branch}/set")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptSetEndpoint getScriptSetTypes(@PathParam("branch") BranchId branch) {
      return new ScriptSetEndpointImpl(branch, testScriptApi.getScriptProgramApi());
   }

   @Override
   @Path("{branch}/batch")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptBatchEndpoint getScriptBatchEndpoint(@PathParam("branch") BranchId branch) {
      return new ScriptBatchEndpointImpl(branch, testScriptApi.getScriptBatchApi());
   }

   @Override
   @Path("{branch}/def")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptDefEndpoint getScriptDefinitionTypes(@PathParam("branch") BranchId branch) {
      return new ScriptDefEndpointImpl(branch, testScriptApi.getScriptDefApi(), testScriptApi.getDashboardApi());
   }

   @Override
   @Path("{branch}/result")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptResultEndpoint getScriptResultTypes(@PathParam("branch") BranchId branch) {
      return new ScriptResultEndpointImpl(branch, testScriptApi.getScriptResultApi());
   }

   @Override
   @Path("{branch}/case")
   @Produces(MediaType.APPLICATION_JSON)
   public TestCaseEndpoint getTestCaseTypes(@PathParam("branch") BranchId branch) {
      return new TestCaseEndpointImpl(branch, testScriptApi.getTestCaseApi());
   }

   @Override
   @Path("{branch}/point")
   @Produces(MediaType.APPLICATION_JSON)
   public TestPointEndpoint getTestPointTypes(@PathParam("branch") BranchId branch) {
      return new TestPointEndpointImpl(branch, testScriptApi.getTestPointApi());
   }

   @Override
   @Path("{branch}/import")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptImportEndpoint getTmoImportEndpoint(@PathParam("branch") BranchId branch) {
      return new ScriptImportEndpointImpl(branch, testScriptApi.getTmoImportApi());
   }

   @Override
   @Path("{branch}/download")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptDownloadEndpoint getTmoDownloadEndpoint(@PathParam("branch") BranchId branch) {
      return new ScriptDownloadEndpointImpl(branch, testScriptApi);
   }

   @Override
   @Path("{branch}/diff")
   @Produces(MediaType.APPLICATION_JSON)
   public ScriptSetDiffEndpoint getScriptSetDiffEndpoint(@PathParam("branch") BranchId branch) {
      return new ScriptSetDiffEndpointImpl(branch, testScriptApi.getScriptResultApi());
   }

}