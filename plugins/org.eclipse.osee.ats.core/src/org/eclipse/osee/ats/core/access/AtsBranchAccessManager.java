package org.eclipse.osee.ats.core.access;

import java.util.Date;
import java.util.logging.Level;
import org.eclipse.osee.ats.core.internal.AtsApiService;
import org.eclipse.osee.framework.logging.OseeLog;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

/**
 * This class will clear access caches upon branch modified event
 *
 * @author Donald G. Dunne
 */
public class AtsBranchAccessManager implements EventHandler {

   private volatile long cacheUpdated = 0;

   public AtsBranchAccessManager() {
      // jax-rs
   }

   /**
    * Since multiple events of same artifact type can come through, only clear cache every one second
    */
   public synchronized void clearCache() {
      long now = new Date().getTime();
      if (now - cacheUpdated > 1000) {
         AtsApiService.get().getAccessControlService().clearCaches();
      }
   }

   @Override
   public void handleEvent(Event event) {
      try {
         clearCache();
      } catch (Exception ex) {
         OseeLog.log(AtsBranchAccessManager.class, Level.SEVERE, ex);
      }
   }

}