/*******************************************************************************
 * Copyright (c) 2013 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.core.ds.criteria;

import org.eclipse.osee.framework.core.data.IOseeBranch;
import org.eclipse.osee.framework.core.util.Conditions;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.orcs.core.ds.Criteria;
import org.eclipse.osee.orcs.core.ds.Options;

/**
 * @author Roberto E. Escobar
 */
public class CriteriaTxGetHead extends Criteria {

   private final IOseeBranch branch;
   private final int branchId;

   public CriteriaTxGetHead(IOseeBranch branch) {
      super();
      this.branch = branch;
      this.branchId = -1;
   }

   public CriteriaTxGetHead(int branchId) {
      super();
      this.branchId = branchId;
      this.branch = null;
   }

   @Override
   public void checkValid(Options options) throws OseeCoreException {
      super.checkValid(options);
      Conditions.checkExpressionFailOnTrue(branch == null && branchId < 0, "Missing valid branch");
   }

   public boolean hasBranchToken() {
      return branch != null;
   }

   public IOseeBranch getBranch() {
      return branch;
   }

   public int getBranchid() {
      return branchId;
   }

   @Override
   public String toString() {
      return "CriteriaGetHead";
   }
}
