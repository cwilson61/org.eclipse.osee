/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.framework.skynet.core.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osee.framework.core.client.ClientSessionManager;
import org.eclipse.osee.framework.core.exception.OseeAuthenticationRequiredException;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.model.Branch;
import org.eclipse.osee.framework.jdk.core.util.OseeProperties;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactModType;
import org.eclipse.osee.framework.skynet.core.event2.BranchEvent;
import org.eclipse.osee.framework.skynet.core.event2.BroadcastEvent;
import org.eclipse.osee.framework.skynet.core.event2.PersistEvent;
import org.eclipse.osee.framework.skynet.core.event2.TransactionChange;
import org.eclipse.osee.framework.skynet.core.event2.TransactionEvent;
import org.eclipse.osee.framework.skynet.core.event2.TransactionEventType;
import org.eclipse.osee.framework.skynet.core.event2.artifact.EventBasicGuidArtifact;
import org.eclipse.osee.framework.skynet.core.event2.artifact.EventModType;
import org.eclipse.osee.framework.skynet.core.internal.Activator;
import org.eclipse.osee.framework.skynet.core.relation.RelationEventType;
import org.eclipse.osee.framework.skynet.core.relation.RelationLink;
import org.eclipse.osee.framework.skynet.core.utility.LoadedArtifacts;

/**
 * Front end to OSEE events. Provides ability to add and remove different event listeners as well as the ability to kick
 * framework events.
 * 
 * @author Donald G. Dunne
 */
public class OseeEventManager {

   private static IBranchEventListener testBranchEventListener;

   private static Sender getSender(Object sourceObject) throws OseeAuthenticationRequiredException {
      // Sender came from Remote Event Manager if source == sender
      if (sourceObject instanceof Sender && ((Sender) sourceObject).isRemote()) {
         return (Sender) sourceObject;
      }
      // Else, create new sender based on sourceObject
      return new Sender(sourceObject, ClientSessionManager.getSession());
   }

   // Kick LOCAL remote-event event
   public static void kickLocalRemEvent(Object source, RemoteEventServiceEventType remoteEventServiceEventType) throws OseeCoreException {
      if (InternalEventManager.isDisableEvents()) {
         return;
      }
      InternalEventManager.kickRemoteEventManagerEvent(getSender(source), remoteEventServiceEventType);
      InternalEventManager2.kickLocalRemEvent(getSender(source), remoteEventServiceEventType);
   }

   // Kick LOCAL and REMOTE broadcast event
   public static void kickBroadcastEvent(Object source, BroadcastEvent broadcastEvent) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickBroadcastEvent(getSender(source), broadcastEvent.getBroadcastEventType(),
            broadcastEvent.getUsers().toArray(new String[broadcastEvent.getUsers().size()]),
            broadcastEvent.getMessage());
      InternalEventManager2.kickBroadcastEvent(getSender(source), broadcastEvent);
   }

   //Kick LOCAL and REMOTE branch events
   public static void kickBranchEvent(Object source, BranchEvent branchEvent, int branchId) throws OseeCoreException {
      eventLog("OEM: kickBranchEvent: type: " + branchEvent.getEventType() + " guid: " + branchEvent.getBranchGuid() + " - " + source);
      if (testBranchEventListener != null) {
         testBranchEventListener.handleBranchEvent(getSender(source), branchEvent.getEventType(), branchId);
      }
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickBranchEvent(getSender(source), branchEvent.getEventType(), branchId);
      branchEvent.setNetworkSender(getSender(source).getNetworkSender2());
      InternalEventManager2.kickBranchEvent(getSender(source), branchEvent);
   }

   // Kick LOCAL and REMOTE branch events
   public static void kickMergeBranchEvent(Object source, MergeBranchEventType branchEventType, int branchId) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickMergeBranchEvent(getSender(source), branchEventType, branchId);
      // Handled by kickMergeBranchEvent for new Events
   }

   // Kick LOCAL and REMOTE access control events
   public static void kickAccessControlArtifactsEvent(Object source, final AccessControlEventType accessControlModType, final LoadedArtifacts loadedArtifacts) throws OseeAuthenticationRequiredException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickAccessControlArtifactsEvent(getSender(source), accessControlModType, loadedArtifacts);
   }

   // Kick LOCAL artifact modified event; This event does NOT go external
   public static void kickArtifactModifiedEvent(Object source, ArtifactModType artifactModType, Artifact artifact) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickArtifactModifiedEvent(getSender(source), artifactModType, artifact);
   }

   // Kick LOCAL relation modified event; This event does NOT go external
   public static void kickRelationModifiedEvent(Object source, RelationEventType relationEventType, RelationLink link, Branch branch, String relationType) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickRelationModifiedEvent(getSender(source), relationEventType, link, branch, relationType);
   }

   // Kick LOCAL and REMOTE purged event depending on sender
   public static void kickArtifactsPurgedEvent(Object source, LoadedArtifacts loadedArtifacts, Set<EventBasicGuidArtifact> artifactChanges) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickArtifactsPurgedEvent(getSender(source), loadedArtifacts);
      // Handled by kickTransactionEvent for new Events
   }

   // Kick LOCAL and REMOTE artifact change type depending on sender
   public static void kickArtifactsChangeTypeEvent(Object source, int toArtifactTypeId, String toArtifactTypeGuid, LoadedArtifacts loadedArtifacts, Set<EventBasicGuidArtifact> artifactChanges) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickArtifactsChangeTypeEvent(getSender(source), toArtifactTypeId, loadedArtifacts);
      // Handled by kickTransactionEvent for new Events
   }

   // Kick LOCAL and REMOTE transaction deleted event
   public static void kickTransactionEvent(Object source, final TransactionEvent transactionEvent) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      Set<Integer> transactionIds = new HashSet<Integer>();
      for (TransactionChange transChange : transactionEvent.getTransactions()) {
         transactionIds.add(transChange.getTransactionId());
      }
      int[] transIds = new int[transactionIds.size()];
      int x = 0;
      for (Integer value : transactionIds) {
         transIds[x++] = value.intValue();
      }
      //TODO This needs to be converted into the individual artifacts and relations that were deleted/modified
      if (transactionEvent.getEventType() == TransactionEventType.Purged) {
         InternalEventManager.kickTransactionsPurgedEvent(getSender(source), transIds);
      }
      transactionEvent.setNetworkSender(getSender(source).getNetworkSender2());
      InternalEventManager2.kickTransactionEvent(getSender(source), transactionEvent);
   }

   // Kick LOCAL and REMOTE transaction event
   public static void kickPersistEvent(Object source, Collection<ArtifactTransactionModifiedEvent> xModifiedEvents, PersistEvent persistEvent) throws OseeAuthenticationRequiredException {
      if (isDisableEvents()) {
         return;
      }
      if (xModifiedEvents != null) {
         InternalEventManager.kickPersistEvent(getSender(source), xModifiedEvents);
      }
      persistEvent.setNetworkSender(getSender(source).getNetworkSender2());
      InternalEventManager2.kickPersistEvent(getSender(source), persistEvent);
   }

   // Kick LOCAL transaction event
   public static void kickLocalArtifactReloadEvent(Object source, Collection<? extends Artifact> artifacts) throws OseeCoreException {
      if (isDisableEvents()) {
         return;
      }
      InternalEventManager.kickArtifactReloadEvent(getSender(source), artifacts);
      InternalEventManager2.kickLocalArtifactReloadEvent(getSender(source), EventBasicGuidArtifact.get(
            EventModType.Reloaded, artifacts));
   }

   /**
    * Add a priority listener. This should only be done for caches where they need to be updated before all other
    * listeners are called.
    */
   public static void addPriorityListener(IEventListener listener) {
      InternalEventManager.addPriorityListener(listener);
   }

   public static void addListener(IEventListener listener) {
      InternalEventManager.addListener(listener);
   }

   public static void removeListener(IEventListener listener) {
      InternalEventManager.removeListeners(listener);
   }

   public static boolean isDisableEvents() {
      return InternalEventManager.isDisableEvents();
   }

   // Turn off all event processing including LOCAL and REMOTE
   public static void setDisableEvents(boolean disableEvents) {
      InternalEventManager.setDisableEvents(disableEvents);
      InternalEventManager2.setDisableEvents(disableEvents);
   }

   // Return report showing all listeners registered
   public static String getListenerReport() {
      return InternalEventManager.getListenerReport();
   }

   // Registration for branch events; for test only
   public static void registerBranchEventListenerForTest(IBranchEventListener branchEventListener) {
      if (!OseeProperties.isInTest()) {
         throw new IllegalStateException("Invalid registration for production");
      }
      testBranchEventListener = branchEventListener;
   }

   public static boolean isEventDebugConsole() {
      if (!Strings.isValid(System.getProperty("eventDebug"))) return false;
      return System.getProperty("eventDebug").equals("console");
   }

   public static boolean isEventDebugErrorLog() {
      if (!Strings.isValid(System.getProperty("eventDebug"))) return false;
      return System.getProperty("eventDebug").equals("log") || "TRUE".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.osee.framework.skynet.core/debug/Events"));
   }

   public static void eventLog(String output) {
      eventLog(output, null);
   }

   public static void eventLog(String output, Exception ex) {
      try {
         if (isEventDebugConsole()) {
            System.err.println(output + (ex != null ? " <<ERROR>> " + ex.getLocalizedMessage() : ""));
         } else if (isEventDebugErrorLog()) {
            if (ex != null) {
               OseeLog.log(Activator.class, Level.SEVERE, output, ex);
            } else {
               OseeLog.log(Activator.class, Level.INFO, output);
            }
         }
      } catch (Exception ex1) {
         OseeLog.log(Activator.class, Level.SEVERE, ex1);
      }
   }

}
