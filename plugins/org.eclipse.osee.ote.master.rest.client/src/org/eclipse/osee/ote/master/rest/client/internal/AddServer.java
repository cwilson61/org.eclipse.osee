package org.eclipse.osee.ote.master.rest.client.internal;

import java.net.URI;
import java.util.concurrent.Callable;
import javax.ws.rs.core.MediaType;
import org.eclipse.osee.jaxrs.client.JaxRsClient;
import org.eclipse.osee.ote.master.rest.client.OTEMasterServerResult;
import org.eclipse.osee.ote.master.rest.model.OTEServer;
import com.sun.jersey.api.client.WebResource;

public class AddServer implements Callable<OTEMasterServerResult> {

   private final JaxRsClient webClientProvider;
   private final OTEServer server;
   private final URI uri;

   public AddServer(JaxRsClient webClientProvider, URI uri, OTEServer server) {
      this.webClientProvider = webClientProvider;
      this.uri = uri;
      this.server = server;
   }

   @Override
   public OTEMasterServerResult call() throws Exception {
      OTEMasterServerResult result = new OTEMasterServerResult();
      try {
         WebResource resource = webClientProvider.createResource(uri);
         resource.path(OTEMasterServerImpl.CONTEXT_NAME).path(OTEMasterServerImpl.CONTEXT_SERVERS).accept(
            MediaType.APPLICATION_XML).post(server);

      } catch (Throwable th) {
         result.setSuccess(false);
         result.setThrowable(th);
      }
      return result;
   }

}
