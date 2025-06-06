/*********************************************************************
 * Copyright (c) 2023 Boeing
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

package org.eclipse.osee.testscript;

import java.util.Collection;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.AttributeTypeId;
import org.eclipse.osee.framework.core.data.BranchId;

/**
 * @author Stephen J. Molaro
 */
public interface ScriptSetApi {

   ScriptSetToken get(BranchId branch, ArtifactId setId);

   Collection<ScriptSetToken> get(BranchId branch, Collection<ArtifactId> setIds);

   Collection<ScriptSetToken> getAll(BranchId branch);

   Collection<ScriptSetToken> getAll(BranchId branch, ArtifactId viewId);

   Collection<ScriptSetToken> getAll(BranchId branch, AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAll(BranchId branch, ArtifactId viewId, AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAll(BranchId branch, long pageNum, long pageSize);

   Collection<ScriptSetToken> getAll(BranchId branch, ArtifactId viewId, long pageNum, long pageSize);

   Collection<ScriptSetToken> getAll(BranchId branch, long pageNum, long pageSize, AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAll(BranchId branch, ArtifactId viewId, long pageNum, long pageSize,
      AttributeTypeId orderByAttribute, boolean activeOnly);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, String filter);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, ArtifactId viewId, String filter);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, String filter, AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, ArtifactId viewId, String filter,
      AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, String filter, long pageNum, long pageSize);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, ArtifactId viewId, String filter, long pageNum,
      long pageSize);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, String filter, long pageNum, long pageSize,
      AttributeTypeId orderByAttribute);

   Collection<ScriptSetToken> getAllByFilter(BranchId branch, ArtifactId viewId, String filter, long pageNum,
      long pageSize, AttributeTypeId orderByAttribute, boolean activeOnly);

   int getCountWithFilter(BranchId branch, ArtifactId viewId, String filter);
}
