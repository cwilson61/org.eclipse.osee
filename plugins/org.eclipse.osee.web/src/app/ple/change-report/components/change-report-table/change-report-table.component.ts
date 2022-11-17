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
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import {
	BehaviorSubject,
	combineLatest,
	of,
	shareReplay,
	switchMap,
	tap,
} from 'rxjs';
import { UiService } from 'src/app/ple-services/ui/ui.service';
import {
	HeaderKeys,
	HeaderService,
} from 'src/app/ple/messaging/shared/services/ui/header.service';
import { changeReportRow } from 'src/app/types/change-report/change-report';
import { ChangeReportService } from '../../services/change-report.service';

@Component({
	selector: 'osee-change-report-table',
	templateUrl: './change-report-table.component.html',
	styleUrls: ['./change-report-table.component.sass'],
})
export class ChangeReportTableComponent implements OnChanges {
	@Input() branchId: string = '';

	constructor(
		private headerService: HeaderService,
		private crService: ChangeReportService,
		private uiService: UiService
	) {}

	ngOnChanges(changes: SimpleChanges): void {
		if (changes.branchId) {
			this.branchId$.next(this.branchId);
		}
	}

	headers: (keyof changeReportRow)[] = [
		'ids',
		'names',
		'itemType',
		'itemKind',
		'changeType',
		'isValue',
		'wasValue',
	];

	getHeaderByName(value: keyof changeReportRow) {
		return this.headerService.getTableHeaderByName(
			value,
			HeaderKeys.changeReportRow
		);
	}

	branchId$ = new BehaviorSubject<string>('');

	branch = combineLatest([this.branchId$, this.uiService.id]).pipe(
		switchMap(([localBranchId, uiBranchId]) =>
			this.crService.getBranchInfo(
				localBranchId !== '' ? localBranchId : uiBranchId
			)
		),
		shareReplay(1)
	);

	parentBranch = this.branch.pipe(
		switchMap((branch) =>
			this.crService.getBranchInfo(branch.parentBranch.id)
		),
		shareReplay(1)
	);

	action = this.branch.pipe(
		switchMap((branch) =>
			this.crService.getRelatedAction(branch.associatedArtifact)
		),
		shareReplay(1)
	);

	latestTxInfo = this.branch.pipe(
		switchMap((branch) => this.crService.getLatestTxInfo(branch.id)),
		shareReplay(1)
	);

	changes = combineLatest([this.branch, this.parentBranch]).pipe(
		switchMap(([branch, parentBranch]) =>
			this.crService.getBranchChanges(branch.id, parentBranch.id)
		),
		shareReplay(1)
	);

	viewData = combineLatest([
		this.branch,
		this.parentBranch,
		this.action,
		this.latestTxInfo,
	]).pipe(
		switchMap(([branch, parentBranch, action, txInfo]) =>
			of({ branch, parentBranch, action, txInfo })
		)
	);
}