/*********************************************************************
 * Copyright (c) 2004, 2007 Boeing
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

package org.eclipse.osee.ats.ide.navigate;

import java.util.Arrays;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.ide.internal.Activator;
import org.eclipse.osee.ats.ide.internal.AtsApiService;
import org.eclipse.osee.framework.core.util.OseeEmail;
import org.eclipse.osee.framework.core.util.OseeEmail.BodyType;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.jdk.core.util.EmailUtil;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.logging.OseeLevel;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.User;
import org.eclipse.osee.framework.skynet.core.UserManager;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateComposite.TableLoadOption;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateItem;
import org.eclipse.osee.framework.ui.plugin.xnavigate.XNavigateItemAction;
import org.eclipse.osee.framework.ui.skynet.FrameworkImage;
import org.eclipse.osee.framework.ui.skynet.notify.OseeEmailIde;
import org.eclipse.osee.framework.ui.skynet.results.XResultDataUI;

/**
 * @author Donald G. Dunne
 */
public class TestEmailSend extends XNavigateItemAction {

   AtsApi atsApi;

   public TestEmailSend() {
      super("Test Email Send", FrameworkImage.EMAIL, XNavigateItem.EMAIL_NOTIFICATIONS);
      atsApi = AtsApiService.get();
   }

   @Override
   public void run(TableLoadOption... tableLoadOptions) {
      try {
         XResultData rd = new XResultData();
         rd.log("Send Test Email");
         User user = UserManager.getUser();
         if (user.isInvalid()) {
            rd.errorf("User [%s] is invalid", user);
         } else {
            String email = user.getEmail();
            if (!EmailUtil.isEmailValid(email)) {
               rd.errorf("User email [%s] is invalid", user);
               XResultDataUI.report(rd, "Test Email");
               return;
            } else {
               rd = new XResultData();
               rd.log("Send Test Email - Client");
               try {
                  OseeEmail emailMessage =
                     OseeEmailIde.create(Arrays.asList(email), email, email, "Test Email - Client",
                        AHTML.simplePage(AHTML.bold("Hello World - this should be bold")), BodyType.Html);
                  emailMessage.send();
                  rd.log("Completed");
               } catch (Exception ex) {
                  rd.error(Lib.exceptionToString(ex));
               }
               XResultDataUI.report(rd, "Send Test Email - Client");

               rd = atsApi.getServerEndpoints().getNotifyEndpoint().sendEmail(email);
               rd.log("Completed");
               XResultDataUI.report(rd, "Send Test Email - Server");
            }
         }

      } catch (Exception ex) {
         OseeLog.log(Activator.class, OseeLevel.SEVERE_POPUP, ex);
      }
   }
}