/*******************************************************************************
 * Copyright (c) 2020 Boeing.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.core.notify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.notify.AtsNotificationCollector;
import org.eclipse.osee.ats.api.notify.AtsNotificationEvent;
import org.eclipse.osee.ats.api.notify.AtsWorkItemNotificationEvent;
import org.eclipse.osee.ats.api.notify.IAtsNotificationService;
import org.eclipse.osee.ats.api.user.AtsUser;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.util.OseeEmail;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.type.OseeArgumentException;
import org.eclipse.osee.framework.jdk.core.util.EmailUtil;
import org.eclipse.osee.framework.jdk.core.util.Lib;

/**
 * @author Donald G. Dunne
 */
public abstract class AbstractAtsNotificationService implements IAtsNotificationService, OseeEmailCreator {

   private volatile boolean emailEnabled = true;
   protected AtsApi atsApi;
   WorkItemNotificationProcessor workItemNotificationProcessor;
   boolean loggedNotificationDisabled = false;

   public AbstractAtsNotificationService(AtsApi atsApi) {
      this.atsApi = atsApi;
   }

   @Override
   public boolean isNotificationsEnabled() {
      return emailEnabled;
   }

   @Override
   public void setNotificationsEnabled(boolean enabled) {
      this.emailEnabled = enabled;
   }

   @Override
   public Collection<AtsUser> getJournalSubscribedUsers(IAtsWorkItem workItem) {
      Set<AtsUser> users = new HashSet<>();
      Collection<ArtifactId> userArts =
         atsApi.getAttributeResolver().getAttributeValues(workItem, AtsAttributeTypes.JournalSubscriber);
      for (ArtifactId userArt : userArts) {
         AtsUser user = atsApi.getConfigService().getUser(userArt);
         if (user != null) {
            users.add(user);
         }
      }
      return users;
   }

   @Override
   public void setJournalSubscribedUsers(IAtsWorkItem workItem, Collection<AtsUser> users) {
      List<Object> userIds = new ArrayList<>();
      for (AtsUser user : users) {
         userIds.add(user.getArtifactId());
      }
      IAtsChangeSet changes = atsApi.createChangeSet("Set Journal Subscribers");
      changes.setAttributeValues(workItem, AtsAttributeTypes.JournalSubscriber, userIds);
      changes.executeIfNeeded();
   }

   @Override
   public synchronized XResultData sendNotifications(final AtsNotificationCollector notifications, XResultData rd) {

      workItemNotificationProcessor = new WorkItemNotificationProcessor(rd);

      // convert all WorkItem notifications to AtsNotificationEvent
      for (AtsWorkItemNotificationEvent workItemEvent : notifications.getWorkItemNotificationEvents()) {
         workItemNotificationProcessor.run(notifications, workItemEvent);
      }

      if (isNotificationsEnabled()) {
         Thread send = new Thread("Send Notifications") {

            @Override
            public void run() {
               if (isNotificationsEnabled()) {
                  if (!atsApi.getStoreService().isProductionDb()) {
                     if (!loggedNotificationDisabled) {
                        atsApi.getLogger().info("Osee Notification Disabled");
                        loggedNotificationDisabled = true;
                     }
                  } else {
                     if (notifications.isIncludeCancelHyperlink() && !atsApi.getWorkItemService().isCancelHyperlinkConfigured()) {
                        throw new OseeArgumentException("Cancel Hyperlink URl not configured");
                     }
                     Thread thread = new Thread("ATS Notification Sender") {

                        @Override
                        public void run() {
                           super.run();

                           // change to email address for testing purposes; all emails will go there
                           String testingUserEmail = "";
                           String fromUserEmail = getFromUserEmail(notifications);

                           sendNotifications(fromUserEmail, testingUserEmail, notifications.getSubject(),
                              notifications.getBody(), notifications.getNotificationEvents(), new XResultData());
                        }

                     };
                     thread.start();
                  }
               }
            }

         };
         send.start();
      }
      return rd;
   }

   @Override
   public void sendNotifications(String fromUserEmail, String testingUserEmail, String subject, String body,
      Collection<? extends AtsNotificationEvent> notificationEvents, XResultData rd) {
      SendNotificationEvents job = new SendNotificationEvents(this, atsApi, fromUserEmail, testingUserEmail, subject,
         body, notificationEvents, atsApi.getUserService(), rd);
      job.run();
   }

   @Override
   public void sendNotifications(String fromUserEmail, Collection<String> toUserEmails, String subject,
      String htmlBody) {
      if (isNotificationsEnabled()) {
         Thread thread = new Thread("ATS Emailer") {

            @Override
            public void run() {
               try {
                  OseeEmail msg = createOseeEmail();
                  msg.setFrom(fromUserEmail);
                  msg.setRecipients(toUserEmails.toArray(new String[toUserEmails.size()]));
                  msg.setSubject(subject);
                  msg.setHTMLBody(htmlBody);
                  msg.send();
               } catch (Exception ex) {
                  System.err.println(Lib.exceptionToString(ex));
               }
            }

         };
         thread.start();
      }
   }

   @Override
   public abstract OseeEmail createOseeEmail();

   private String getFromUserEmail(AtsNotificationCollector notifications) {
      String email = atsApi.getConfigValue("NoReplyEmail");
      for (AtsNotificationEvent event : notifications.getNotificationEvents()) {
         if (EmailUtil.isEmailValid(event.getFromEmailAddress())) {
            email = event.getFromEmailAddress();
            break;
         }
      }
      return email;
   }

}
