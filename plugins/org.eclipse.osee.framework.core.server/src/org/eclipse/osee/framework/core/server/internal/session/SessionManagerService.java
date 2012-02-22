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
package org.eclipse.osee.framework.core.server.internal.session;

import java.util.Collection;
import org.eclipse.osee.framework.core.data.OseeCredential;
import org.eclipse.osee.framework.core.data.OseeSessionGrant;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.model.cache.IOseeDataAccessor;
import org.eclipse.osee.framework.core.server.IApplicationServerManager;
import org.eclipse.osee.framework.core.server.IAuthenticationManager;
import org.eclipse.osee.framework.core.server.ISession;
import org.eclipse.osee.framework.core.server.ISessionManager;
import org.eclipse.osee.framework.core.server.internal.BuildTypeDataProvider;
import org.eclipse.osee.framework.core.server.internal.BuildTypeIdentifier;
import org.eclipse.osee.framework.database.IOseeDatabaseService;
import org.eclipse.osee.logger.Log;

/**
 * @author Roberto E. Escobar
 */
public final class SessionManagerService implements ISessionManager {

   private Log logger;
   private IOseeDatabaseService dbService;
   private IApplicationServerManager serverManager;
   private IAuthenticationManager authenticationManager;

   private ISessionDataStoreSync dataStoreSync;
   private ISessionManager proxiedSessionManager;

   public void setLogger(Log logger) {
      this.logger = logger;
   }

   public void setDbService(IOseeDatabaseService dbService) {
      this.dbService = dbService;
   }

   public void setServerManager(IApplicationServerManager serverManager) {
      this.serverManager = serverManager;
   }

   public void setAuthenticationManager(IAuthenticationManager authenticationManager) {
      this.authenticationManager = authenticationManager;
   }

   private IOseeDatabaseService getDbService() {
      return dbService;
   }

   private IApplicationServerManager getServerManager() {
      return serverManager;
   }

   private IAuthenticationManager getAuthenticationManager() {
      return authenticationManager;
   }

   public void start() {
      String serverId = getServerManager().getId();
      BuildTypeIdentifier identifier = new BuildTypeIdentifier(new BuildTypeDataProvider());

      SessionFactory sessionFactory = new SessionFactory(logger, identifier);

      ISessionQuery sessionQuery = new DatabaseSessionQuery(serverId, getDbService());
      IOseeDataAccessor<String, Session> accessor =
         new DatabaseSessionAccessor(serverId, sessionFactory, sessionQuery, getDbService());
      SessionCache sessionCache = new SessionCache(accessor);

      proxiedSessionManager =
         new SessionManagerImpl(serverId, sessionFactory, sessionQuery, sessionCache, getAuthenticationManager());

      dataStoreSync = new SessionDataStoreSync(sessionCache);
      dataStoreSync.start();
   }

   public void stop() {
      if (dataStoreSync != null) {
         dataStoreSync.stop();
         dataStoreSync = null;
      }
   }

   @Override
   public OseeSessionGrant createSession(OseeCredential credential) throws OseeCoreException {
      return proxiedSessionManager.createSession(credential);
   }

   @Override
   public void releaseSession(String sessionId) throws OseeCoreException {
      proxiedSessionManager.releaseSession(sessionId);
   }

   @Override
   public void updateSessionActivity(String sessionId, String interactionName) throws OseeCoreException {
      proxiedSessionManager.updateSessionActivity(sessionId, interactionName);
   }

   @Override
   public ISession getSessionById(String sessionId) throws OseeCoreException {
      return proxiedSessionManager.getSessionById(sessionId);
   }

   @Override
   public Collection<ISession> getSessionByClientAddress(String clientAddress) throws OseeCoreException {
      return proxiedSessionManager.getSessionByClientAddress(clientAddress);
   }

   @Override
   public Collection<ISession> getSessionsByUserId(String userId, boolean includeNonServerManagedSessions) throws OseeCoreException {
      return proxiedSessionManager.getSessionsByUserId(userId, includeNonServerManagedSessions);
   }

   @Override
   public Collection<ISession> getAllSessions(boolean includeNonServerManagedSessions) throws OseeCoreException {
      return proxiedSessionManager.getAllSessions(includeNonServerManagedSessions);
   }

   @Override
   public void releaseSessionImmediate(String... sessionId) throws OseeCoreException {
      proxiedSessionManager.releaseSessionImmediate(sessionId);
   }

}