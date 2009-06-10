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
package org.eclipse.osee.framework.manager.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.osee.framework.core.server.OseeHttpServlet;
import org.eclipse.osee.framework.db.connection.ConnectionHandlerStatement;
import org.eclipse.osee.framework.db.connection.exception.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;

/**
 * @author Donald G. Dunne
 */
public class ManagerServlet extends OseeHttpServlet {

   private static final long serialVersionUID = 3334123351267606890L;

   private static enum OperationType {
      USER, INVALID;

      public static OperationType fromString(String value) {
         OperationType toReturn = OperationType.INVALID;
         for (OperationType operType : OperationType.values()) {
            if (operType.name().equalsIgnoreCase(value)) {
               toReturn = operType;
               break;
            }
         }
         return toReturn;
      }
   }

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.core.server.OseeHttpServlet#checkAccessControl(javax.servlet.http.HttpServletRequest)
    */
   @Override
   protected void checkAccessControl(HttpServletRequest request) throws OseeCoreException {
      // Allow access to all
   }

   /* (non-Javadoc)
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String operation = request.getParameter("operation");
      try {
         OperationType operationType = OperationType.fromString(operation);
         switch (operationType) {
            case USER:
               displayUser(request, response);
               break;
            default:
               displayOverview(request, response);
               break;
         }
      } catch (Exception ex) {
         OseeLog.log(InternalManagerServletActivator.class, Level.SEVERE, String.format(
               "Error processing request for protocols [%s]", request.toString()), ex);
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         response.setContentType("text/plain");
         response.getWriter().write(Lib.exceptionToString(ex));
      } finally {
         response.getWriter().flush();
         response.getWriter().close();
      }
   }

   private void displayOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer sb = new StringBuffer(1000);
      try {
         sb.append(AHTML.heading(2, "OSEE System Manager"));
         sb.append(getHeader());
         sb.append(AHTML.newline() + getSessionByUserIdEntry(request, response));
         sb.append(getSessions());
      } catch (OseeCoreException ex) {
         sb.append("Exception: " + ex.getLocalizedMessage());
      }
      displayResults(sb.toString(), request, response);
   }

   private String getHeader() {
      return "<a href=\"http://localhost:8089/osee/manager\">Home</a><br>";
   }

   private String getSessionByUserIdEntry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer sb = new StringBuffer(1000);
      try {
         sb.append("<form METHOD=GET ACTION=\"http://localhost:8089/osee/manager\">");
         sb.append("By UserId: <input TYPE=\"text\" NAME=\"userId\" SIZE=\"10\" MAXLENGTH=\"10\">");
         sb.append("<input TYPE=\"hidden\" NAME=\"operation\" VALUE=\"user\">");
         sb.append("<INPUT TYPE=SUBMIT></form>");
      } catch (Exception ex) {
         sb.append("Exception: " + ex.getLocalizedMessage());
      }
      return sb.toString();
   }

   private void displayUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuffer sb = new StringBuffer(1000);
      try {
         HttpManagerCreationInfo info = new HttpManagerCreationInfo(request);
         String userId = info.getUserId();
         if (!Strings.isValid(userId)) {
            sb.append("Invalid userId [" + userId + "]");
         } else {
            sb.append(AHTML.heading(2, "OSEE System Manager"));
            sb.append(getHeader());
            sb.append(getSessionsByUserId(userId));
         }
      } catch (OseeCoreException ex) {
         sb.append("Exception: " + ex.getLocalizedMessage());
      }
      displayResults(sb.toString(), request, response);
   }

   private void displayResults(String results, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         response.setStatus(HttpServletResponse.SC_OK);
         response.setContentType("text/html");
         response.setCharacterEncoding("UTF-8");
         response.getWriter().write(results + AHTML.newline() + "As of: " + new Date());
      } catch (Exception ex) {
         OseeLog.log(InternalManagerServletActivator.class, Level.SEVERE, String.format(
               "Error processing request for protocols [%s]", request.toString()), ex);
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         response.setContentType("text/plain");
         response.getWriter().write(Lib.exceptionToString(ex));
      } finally {
         response.getWriter().flush();
         response.getWriter().close();
      }
   }

   private static final String SESSION_QUERY_ALL = "Select * from osee_session";
   private static final String SESSION_QUERY_USER = "Select * from osee_session where user_id = ?";

   private String getSessions() throws OseeCoreException {
      ConnectionHandlerStatement chStmt = new ConnectionHandlerStatement();
      try {
         chStmt.runPreparedQuery(SESSION_QUERY_ALL);
         return getSessionResults(chStmt, "Sessions");
      } finally {
         chStmt.close();
      }

   }

   private String getSessionsByUserId(String userId) throws OseeCoreException {
      ConnectionHandlerStatement chStmt = new ConnectionHandlerStatement();
      try {
         chStmt.runPreparedQuery(SESSION_QUERY_USER, userId);
         return getSessionResults(chStmt, "Sessions for [" + userId + "]");
      } finally {
         chStmt.close();
      }

   }

   private String getSessionResults(ConnectionHandlerStatement chStmt, String title) throws OseeCoreException {
      StringBuffer sb = new StringBuffer(1000);
      sb.append(AHTML.heading(3, title));
      sb.append(AHTML.beginMultiColumnTable(100, 1));
      sb.append(AHTML.addHeaderRowMultiColumnTable(new String[] {"User", "Version", "Machine", "Exceptions", "Info",
            "Created", "Last Interaction", "IP", "Port"}));
      int insertLoc = sb.toString().length();
      while (chStmt.next()) {
         String clientIp = chStmt.getString("client_address");
         String clientPort = chStmt.getString("client_port");
         sb.insert(insertLoc, AHTML.addRowMultiColumnTable(new String[] {chStmt.getString("user_id"),
               chStmt.getString("client_version"), chStmt.getString("client_machine_name"),
               "<a href=\"http://" + clientIp + ":" + clientPort + "/osee/request?cmd=exceptions\">exceptions</a>",
               "<a href=\"http://" + clientIp + ":" + clientPort + "/osee/request?cmd=info\">info</a>",
               chStmt.getString("created_on"), chStmt.getString("last_interaction_date"), clientIp, clientPort}));
      }
      sb.append(AHTML.endMultiColumnTable());
      return sb.toString();
   }
}
