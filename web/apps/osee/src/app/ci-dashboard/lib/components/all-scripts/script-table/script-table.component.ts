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
import {
	ChangeDetectionStrategy,
	Component,
	effect,
	inject,
	signal,
	viewChild,
} from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { scriptDefHeaderDetails } from '../../../table-headers/script-headers';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {
	MatCell,
	MatCellDef,
	MatColumnDef,
	MatHeaderCell,
	MatHeaderCellDef,
	MatHeaderRow,
	MatHeaderRowDef,
	MatRow,
	MatRowDef,
	MatTable,
	MatTableDataSource,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { HeaderService } from '@osee/shared/services';
import { CiDetailsService } from '../../../services/ci-details.service';
import type { DefReference } from '../../../types/tmo';
import { CiDashboardUiService } from '../../../services/ci-dashboard-ui.service';
import { Router } from '@angular/router';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { SubsystemSelectorComponent } from '../../subsystem-selector/subsystem-selector.component';
import { TeamSelectorComponent } from '../../team-selector/team-selector.component';
import {
	MatFormField,
	MatLabel,
	MatPrefix,
} from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatIcon } from '@angular/material/icon';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';

@Component({
	selector: 'osee-script-table',
	imports: [
		AsyncPipe,
		SubsystemSelectorComponent,
		TeamSelectorComponent,
		FormsModule,
		MatTable,
		MatColumnDef,
		MatHeaderCell,
		MatHeaderCellDef,
		MatTooltip,
		MatCell,
		MatCellDef,
		MatHeaderRow,
		MatHeaderRowDef,
		MatRow,
		MatRowDef,
		MatFormField,
		MatLabel,
		MatInput,
		MatIcon,
		MatPrefix,
		MatPaginator,
		MatSort,
		MatSortHeader,
	],
	changeDetection: ChangeDetectionStrategy.OnPush,
	template: `<div class="tw-flex tw-h-full tw-w-screen tw-flex-col">
		@if (scriptDefs()) {
			<mat-form-field
				class="tw-w-full"
				subscriptSizing="dynamic">
				<mat-label>Filter Scripts</mat-label>
				<input
					matInput
					[(ngModel)]="filterText" />
				<mat-icon matPrefix>filter_list</mat-icon>
			</mat-form-field>
			<mat-table
				[dataSource]="datasource"
				class="tw-overflow-auto"
				matSort>
				@for (header of headers; track $index) {
					<ng-container [matColumnDef]="header">
						<th
							mat-header-cell
							*matHeaderCellDef
							mat-sort-header
							class="tw-text-center tw-align-middle tw-font-medium tw-text-primary-600"
							[matTooltip]="
								(getTableHeaderByName(header) | async)
									?.description || ''
							">
							{{
								(getTableHeaderByName(header) | async)
									?.humanReadable || ''
							}}
						</th>
						<td
							mat-cell
							*matCellDef="let def"
							class="tw-align-middle">
							@if (header === 'name') {
								<button
									mat-list-item
									(click)="resultList(def.id)"
									class="tw-text-primary">
									{{ def[header] }}
								</button>
							} @else if (header === 'subsystem') {
								<osee-subsystem-selector [script]="def" />
							} @else if (header === 'team') {
								<osee-team-selector [script]="def" />
							} @else {
								{{ def[header] }}
							}
						</td>
					</ng-container>
				}
				<tr
					mat-header-row
					*matHeaderRowDef="headers; sticky: true"></tr>
				<tr
					mat-row
					*matRowDef="let row; columns: headers; let i = index"
					class="odd:tw-bg-selected-button even:tw-bg-background-background hover:tw-bg-background-app-bar"
					[attr.data-cy]="'script-def-table-row-' + row.name"></tr>
			</mat-table>
			<mat-paginator
				[pageSizeOptions]="[10, 25, 50, 100, 200, 500]"
				[pageSize]="100"
				[length]="datasource.data.length"
				[disabled]="false"></mat-paginator>
		}
	</div>`,
})
export class ScriptTableComponent {
	ciDetailsService = inject(CiDetailsService);
	ciDashboardService = inject(CiDashboardUiService);
	headerService = inject(HeaderService);
	dialog = inject(MatDialog);
	router = inject(Router);

	private paginator = viewChild(MatPaginator);
	private matSort = viewChild(MatSort);

	scriptDefs = toSignal(
		this.ciDetailsService.scriptDefs.pipe(takeUntilDestroyed())
	);
	ciSetId = this.ciDashboardService.ciSetId;

	datasource = new MatTableDataSource<DefReference>();

	filterText = signal('');

	resultList(defId: string) {
		let url = this.router.url;
		url = url.replace('allScripts', 'details');
		this.ciDetailsService.CiDefId = defId;
		this.router.navigateByUrl(url);
	}

	private _filterEffect = effect(
		() => (this.datasource.filter = this.filterText())
	);

	private _paginatorEffect = effect(() => {
		const paginator = this.paginator();
		if (paginator) {
			this.datasource.paginator = paginator;
		}
	});

	private _sortEffect = effect(() => {
		const sort = this.matSort();
		if (sort) {
			this.datasource.sort = sort;
		}
	});

	private _scriptsEffect = effect(() => {
		const scripts = this.scriptDefs();
		if (scripts) {
			this.datasource.data = scripts;
		} else {
			this.datasource.data = [];
		}
	});

	menuPosition = {
		x: '0',
		y: '0',
	};

	getTableHeaderByName(header: keyof DefReference) {
		return this.headerService.getHeaderByName(
			scriptDefHeaderDetails,
			header
		);
	}

	headers: (keyof DefReference)[] = [
		'name',
		'team',
		'subsystem',
		'safety',
		'statusBy',
		'statusDate',
		'latestResult',
		'latestPassedCount',
		'latestFailedCount',
		'latestScriptAborted',
		'machineName',
		'latestElapsedTime',
		'fullScriptName',
		'notes',
	];
}
