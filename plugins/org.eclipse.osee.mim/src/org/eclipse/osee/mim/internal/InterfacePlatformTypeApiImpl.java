/*********************************************************************
 * Copyright (c) 2021 Boeing
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
package org.eclipse.osee.mim.internal;

import org.eclipse.osee.mim.ArtifactAccessor;
import org.eclipse.osee.mim.ArtifactInserter;
import org.eclipse.osee.mim.InterfacePlatformTypeApi;
import org.eclipse.osee.mim.types.PlatformTypeToken;
import org.eclipse.osee.orcs.OrcsApi;

/**
 * @author Luciano T. Vaglienti
 */
public class InterfacePlatformTypeApiImpl implements InterfacePlatformTypeApi {

   private ArtifactAccessor<PlatformTypeToken> accessor;
   private ArtifactInserter<PlatformTypeToken> inserter;

   InterfacePlatformTypeApiImpl(OrcsApi orcsApi) {
      this.setAccessor(new PlatformTypeAccessor(orcsApi));
      this.setInserter(new PlatformTypeInserter(orcsApi, this.getAccessor()));
   }

   @Override
   public ArtifactAccessor<PlatformTypeToken> getAccessor() {
      return accessor;
   }

   /**
    * @param accessor the accessor to set
    */
   private void setAccessor(ArtifactAccessor<PlatformTypeToken> accessor) {
      this.accessor = accessor;
   }

   @Override
   public ArtifactInserter<PlatformTypeToken> getInserter() {
      return this.inserter;
   }

   /**
    * @param inserter the inserter to set
    */
   private void setInserter(ArtifactInserter<PlatformTypeToken> inserter) {
      this.inserter = inserter;
   }

}