/*
 * Created on Sep 28, 2008
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.ats.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.osee.ats.AtsPlugin;
import org.eclipse.osee.framework.db.connection.exception.OseeCoreException;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.UserManager;
import org.eclipse.osee.framework.skynet.core.User;

/**
 * @author Donald G. Dunne
 */
public class UsersByIds {

   public static Pattern userPattern = Pattern.compile("<(.*?)>");

   public static String getStorageString(Collection<User> users) throws OseeCoreException {
      StringBuffer sb = new StringBuffer();
      for (User u : users)
         sb.append("<" + u.getUserId() + ">");
      return sb.toString();
   }

   public static Collection<User> getUsers(String sorageString) throws OseeCoreException {
      Set<User> users = new HashSet<User>();
      Matcher m = userPattern.matcher(sorageString);
      while (m.find()) {
         String userId = m.group(1);
         if (userId == null || userId.equals("")) throw new IllegalArgumentException("Blank userId specified.");
         try {
            User u = UserManager.getUserByUserId(m.group(1));
            users.add(u);
         } catch (Exception ex) {
            OseeLog.log(AtsPlugin.class, Level.SEVERE, ex);
         }
      }
      return users;
   }

}
