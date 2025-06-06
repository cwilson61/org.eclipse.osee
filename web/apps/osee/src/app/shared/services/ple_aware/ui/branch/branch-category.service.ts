/*********************************************************************
 * Copyright (c) 2025 Boeing
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
import { Injectable, signal, WritableSignal } from '@angular/core';

@Injectable({
	providedIn: 'root',
})
export class BranchCategoryService {
	private _branchCategory: WritableSignal<string> = signal('');

	get branchCategory() {
		return this._branchCategory;
	}

	set BranchCategory(branchCategory: string) {
		this._branchCategory.set(branchCategory);
	}
}
