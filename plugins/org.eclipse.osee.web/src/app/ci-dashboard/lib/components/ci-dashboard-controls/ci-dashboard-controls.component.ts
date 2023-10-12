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
import { NgIf } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BranchPickerComponent } from '@osee/shared/components';
import { CiDashboardUiService } from 'src/app/ci-dashboard/lib/services/ci-dashboard-ui.service';

@Component({
	selector: 'osee-ci-dashboard-controls',
	standalone: true,
	imports: [NgIf, BranchPickerComponent],
	templateUrl: './ci-dashboard-controls.component.html',
})
export class CiDashboardControlsComponent implements OnInit {
	@Input() branchPicker: boolean = true;

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
}
