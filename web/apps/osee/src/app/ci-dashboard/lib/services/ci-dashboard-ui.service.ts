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
import { Injectable, inject } from '@angular/core';
import { UiService } from '@osee/shared/services';
import { BehaviorSubject } from 'rxjs';

@Injectable({
	providedIn: 'root',
})
export class CiDashboardUiService {
	private uiService = inject(UiService);

	private _ciSetId = new BehaviorSubject<`${number}`>('-1');

	get branchType() {
		return this.uiService.type;
	}

	set BranchType(type: '' | 'working' | 'baseline') {
		this.uiService.typeValue = type;
	}

	get branchId() {
		return this.uiService.id;
	}

	set BranchId(branchId: string) {
		this.uiService.idValue = branchId;
	}

	get ciSetId() {
		return this._ciSetId;
	}

	set CiSetId(id: `${number}`) {
		if (this.ciSetId.value === id) {
			return;
		}
		this._ciSetId.next(id);
	}

	get updateRequired() {
		return this.uiService.update;
	}

	set update(value: boolean) {
		this.uiService.updated = value;
	}
}
