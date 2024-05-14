/*********************************************************************
 * Copyright (c) 2022 Boeing
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
import { Component, Input, OnInit, computed } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import {
	CurrentActionDropDownComponent,
	BranchPickerComponent,
} from '@osee/shared/components';
import { CiDashboardUiService } from '../../services/ci-dashboard-ui.service';
import { SetDropdownComponent } from './set-dropdown/set-dropdown.component';

@Component({
	selector: 'osee-ci-dashboard-controls',
	standalone: true,
	imports: [
		BranchPickerComponent,
		SetDropdownComponent,
		CurrentActionDropDownComponent,
	],
	templateUrl: './ci-dashboard-controls.component.html',
})
export class CiDashboardControlsComponent implements OnInit {
	@Input() branchPicker: boolean = true;
	@Input() ciSetSelector: boolean = true;
	@Input() actionButton: boolean = false;

	constructor(
		private route: ActivatedRoute,
		private uiService: CiDashboardUiService
	) {}

	ngOnInit(): void {
		this.route.paramMap.subscribe((params) => {
			this.uiService.BranchId = params.get('branchId') || '';
			this.uiService.BranchType =
				(params.get('branchType') as 'working' | 'baseline' | '') || '';
			this.uiService.CiSetId = params.get('ciSet') || '-1';
		});
	}

	protected _branchType = toSignal(this.uiService.branchType);
	protected _branchId = toSignal(this.uiService.branchId);

	changedBranchId = computed(
		() =>
			this._branchId() !== '' &&
			this._branchId() !== '-1' &&
			this._branchId() !== '0'
	);
}
