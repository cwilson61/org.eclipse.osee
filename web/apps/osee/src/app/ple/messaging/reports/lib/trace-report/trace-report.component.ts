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
import { AsyncPipe } from '@angular/common';
import { Component, Signal, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import {
	MatButtonToggle,
	MatButtonToggleGroup,
} from '@angular/material/button-toggle';
import {
	MatExpansionPanel,
	MatExpansionPanelContent,
	MatExpansionPanelHeader,
	MatExpansionPanelTitle,
} from '@angular/material/expansion';
import { ActivatedRoute } from '@angular/router';
import { ReportsService } from '@osee/messaging/shared/services';
import { CurrentBranchInfoService, UiService } from '@osee/shared/services';
import { Observable, map } from 'rxjs';
import { TraceReportTableComponent } from './trace-report-table/trace-report-table.component';

@Component({
	selector: 'osee-trace-report',
	imports: [
		AsyncPipe,
		TraceReportTableComponent,
		MatButtonToggleGroup,
		MatButtonToggle,
		MatExpansionPanel,
		MatExpansionPanelHeader,
		MatExpansionPanelContent,
		MatExpansionPanelTitle,
	],
	templateUrl: './trace-report.component.html',
})
export class NodeTraceReportRequirementsComponent {
	private route = inject(ActivatedRoute);
	private ui = inject(UiService);
	private reportsService = inject(ReportsService);
	private branchService = inject(CurrentBranchInfoService);

	/** Inserted by Angular inject() migration for backwards compatibility */
	constructor(...args: unknown[]);

	constructor() {
		this.route.paramMap.subscribe((params) => {
			this.ui.idValue = params.get('branchId') || '';
			this.ui.typeValue =
				(params.get('branchType') as 'working' | 'baseline' | '') || '';
		});
	}

	branchName = this.branchService.currentBranch.pipe(map((br) => br.name));
	requirementsReport = this.reportsService.nodeTraceReportRequirements;

	requirementsReportCount =
		this.reportsService.nodeTraceReportRequirementsCount;

	_currentPage = toSignal(this.reportsService.currentPage, {
		initialValue: 0,
	});

	_missingPage = toSignal(this.reportsService.missingPage, {
		initialValue: 0,
	});

	_currentPageSize = this.reportsService.currentPageSize;

	_missingPageSize = this.reportsService.missingPageSize;

	missingRequirementsReport =
		this.reportsService.nodeTraceReportNoMatchingArtifacts;

	missingRequirementsReportCount =
		this.reportsService.nodeTraceReportNoMatchingArtifactsCount;
	artifactsReport = this.reportsService.nodeTraceReportInterfaceArtifacts;

	artifactsReportCount =
		this.reportsService.nodeTraceReportInterfaceArtifactsCount;

	missingInterfaceArtifactsReport =
		this.reportsService.nodeTraceReportNoMatchingInterfaceArtifacts;

	missingInterfaceArtifactsReportCount =
		this.reportsService.nodeTraceReportNoMatchingInterfaceArtifactsCount;

	get currentPageSize(): Observable<number> {
		return this._currentPageSize;
	}

	set currentPageSize(value: number) {
		this.reportsService.currentPageSize = value;
	}

	get currentPage(): Signal<number> {
		return this._currentPage;
	}

	set currentPage(value: number) {
		this.reportsService.currentPage = value;
	}

	get missingPageSize(): Observable<number> {
		return this._missingPageSize;
	}

	set missingPageSize(value: number) {
		this.reportsService.missingPageSize = value;
	}

	get missingPage(): Signal<number> {
		return this._missingPage;
	}

	set missingPage(value: number) {
		this.reportsService.missingPage = value;
	}
}

export default NodeTraceReportRequirementsComponent;
