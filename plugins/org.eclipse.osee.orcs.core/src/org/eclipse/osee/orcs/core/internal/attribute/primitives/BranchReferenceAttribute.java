/*******************************************************************************
 * Copyright (c) 2017 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.core.internal.attribute.primitives;

import org.eclipse.osee.framework.core.data.BranchId;

/**
 * @author Ryan D. Brooks
 */
public class BranchReferenceAttribute extends IdentityReferenceAttribute {
   public static final String NAME = BranchReferenceAttribute.class.getSimpleName();

   @Override
   public BranchId convertStringToValue(String value) {
      return BranchId.valueOf(value);
   }
}