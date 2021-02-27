/*********************************************************************
 * Copyright (c) 2011 Boeing
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

package org.eclipse.osee.framework.access.internal;

import java.util.List;
import org.eclipse.osee.framework.access.AccessControlData;
import org.eclipse.osee.framework.access.AccessControlServiceImpl;
import org.eclipse.osee.framework.access.AccessObject;
import org.eclipse.osee.framework.access.internal.data.BranchAccessObject;
import org.eclipse.osee.framework.core.data.BranchId;

/**
 * @author Jeff C. Phillips
 */
public class AccessControlCacheHandler {

   public void updateAccessListForBranchObject(AccessControlServiceImpl service, BranchId branch) {
      BranchAccessObject branchAccessObject = BranchAccessObject.getBranchAccessObject(branch);
      if (branchAccessObject != null) {
         updateAccessList(service, branchAccessObject);
      }
   }

   public void updateAccessList(AccessControlServiceImpl service, AccessObject accessObject) {
      List<AccessControlData> acl = service.generateAccessControlList(accessObject);
      for (AccessControlData accessControlData : acl) {
         service.removeAccessControlDataIf(false, accessControlData);
      }
   }

   public void reloadCache(AccessControlServiceImpl service) {
      service.reloadCache();
   }
}
