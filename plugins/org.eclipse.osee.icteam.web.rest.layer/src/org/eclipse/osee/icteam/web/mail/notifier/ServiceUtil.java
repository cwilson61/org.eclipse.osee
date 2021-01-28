/*********************************************************************
 * Copyright (c) 2020 Robert Bosch Engineering and Business Solutions Ltd India
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Robert Bosch Engineering and Business Solutions Ltd India - initial API and implementation
 **********************************************************************/
package org.eclipse.osee.icteam.web.mail.notifier;

import org.eclipse.osee.framework.core.services.IOseeCachingService;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * This util class is for getting service
 * 
 * @author Ajay Chandrahasan
 */
public final class ServiceUtil {

   private ServiceUtil() {
      // Utility class
   }

   private static <T> T getService(final Class<T> clazz) throws OseeCoreException {
      Bundle bundle = FrameworkUtil.getBundle(ServiceUtil.class);
      Conditions.checkNotNull(bundle, "bundle");
      BundleContext context = bundle.getBundleContext();
      Conditions.checkNotNull(context, "bundleContext");
      ServiceReference<T> reference = context.getServiceReference(clazz);
      Conditions.checkNotNull(reference, "serviceReference");
      T service = context.getService(reference);
      Conditions.checkNotNull(service, "service");
      return service;
   }

   /**
    * get OSEE cache service
    * 
    * @return
    * @throws OseeCoreException
    */
   public static IOseeCachingService getOseeCacheService() throws OseeCoreException {
      return getService(IOseeCachingService.class);
   }

   /**
    * get package admin
    * 
    * @return
    * @throws OseeCoreException
    */
   public static PackageAdmin getPackageAdmin() throws OseeCoreException {
      return getService(PackageAdmin.class);
   }

}