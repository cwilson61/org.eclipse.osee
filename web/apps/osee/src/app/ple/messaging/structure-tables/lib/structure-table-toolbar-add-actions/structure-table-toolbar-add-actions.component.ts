/*********************************************************************
 * Copyright (c) 2024 Boeing
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
	computed,
	inject,
	input,
} from '@angular/core';
import {
	takeUntilDestroyed,
	toObservable,
	toSignal,
} from '@angular/core/rxjs-interop';
import { MatBadge } from '@angular/material/badge';
import { MatButton, MatMiniFabButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import { MatMenu, MatMenuItem, MatMenuTrigger } from '@angular/material/menu';
import { MatTooltip } from '@angular/material/tooltip';
import { applicabilitySentinel } from '@osee/applicability/types';
import { STRUCTURE_SERVICE_TOKEN } from '@osee/messaging/shared/tokens';
import { ElementDialog, structure } from '@osee/messaging/shared/types';
import { filter, map, share, shareReplay, switchMap, take } from 'rxjs';
import { AddElementDialogComponent } from '../dialogs/add-element-dialog/add-element-dialog.component';
import { DefaultAddElementDialog } from '../dialogs/add-element-dialog/add-element-dialog.default';
import { AddStructureDialog } from '../dialogs/add-structure-dialog/add-structure-dialog';
import { AddStructureDialogComponent } from '../dialogs/add-structure-dialog/add-structure-dialog.component';

@Component({
	selector: 'osee-structure-table-toolbar-add-actions',
	standalone: true,
	imports: [
		MatMiniFabButton,
		MatIcon,
		MatTooltip,
		MatButton,
		MatMenu,
		MatMenuItem,
		MatMenuTrigger,
		MatBadge,
	],
	template: ` <div
			class="tw-flex tw-items-center tw-justify-between tw-gap-4 tw-pr-4">
			<div
				[matTooltip]="
					isEditing() === false
						? 'Switch to edit mode to add elements'
						: structures().length === 0
							? 'Expand a structure to add elements'
							: 'Add an element to an existing structure'
				">
				<button
					mat-stroked-button
					class="tertiary-badge"
					[matBadge]="structures().length"
					[matBadgeHidden]="
						isEditing() === false || structures().length === 0
					"
					[matMenuTriggerFor]="addElementMenu"
					data-testid="add-element"
					[disabled]="
						isEditing() === false || structures().length === 0
					">
					{{
						isEditing() === false || structures().length === 0
							? 'Element Creation Disabled'
							: 'Add Element to:'
					}}
				</button>
			</div>
			<div
				[matTooltip]="
					isEditing() === false
						? 'Switch to edit mode to add structures'
						: 'Add a structure'
				">
				<button
					mat-mini-fab
					class="tertiary-fab"
					data-testid="add-structure"
					[disabled]="isEditing() === false"
					(click)="openAddStructureDialog()">
					<mat-icon>add</mat-icon>
				</button>
			</div>
		</div>
		<mat-menu #addElementMenu>
			@for (structure of structures(); track structure.id) {
				<button
					mat-menu-item
					(click)="openAddElementDialog(structure)">
					<mat-icon class="tw-text-success">add_circle</mat-icon>
					{{ structure.name.value }}
				</button>
			}
		</mat-menu>`,
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StructureTableToolbarAddActionsComponent {
	private structureService = inject(STRUCTURE_SERVICE_TOKEN);
	protected structures = computed(() =>
		this.structureService.expandedRows().filter((s) => !s.autogenerated)
	);
	private dialog = inject(MatDialog);
	breadCrumb = input.required<string>();
	protected preferences = this.structureService.preferences;
	private _isEditing$ = this.preferences.pipe(
		map((x) => x.inEditMode),
		share(),
		shareReplay(1),
		takeUntilDestroyed()
	);
	protected isEditing = toSignal(this._isEditing$, { initialValue: false });
	protected openAddElementDialog(structure: structure) {
		const dialogData = new DefaultAddElementDialog(
			structure?.id || '',
			structure.name.value || ''
		);
		const dialogRef = this.dialog.open(AddElementDialogComponent, {
			data: dialogData,
			minWidth: '80vw',
			minHeight: '80vh',
		});
		const createElement = dialogRef.afterClosed().pipe(
			filter(
				(val) =>
					(val !== undefined || val !== null) &&
					val?.element !== undefined
			),
			switchMap((value: ElementDialog) =>
				value.element.id !== undefined &&
				value.element.id !== '-1' &&
				value.element.id.length > 0
					? this.structureService.relateElement(
							structure.id,
							value.element.id !== undefined
								? value.element.id
								: '-1'
						)
					: this.structureService.createNewElement(
							value.element,
							structure.id
						)
			),
			take(1)
		);
		createElement.subscribe();
	}
	private _submessageId = toObservable(this.structureService.SubMessageId);
	protected structureDialog = this._submessageId.pipe(
		take(1),
		switchMap((submessage) =>
			this.dialog
				.open(AddStructureDialogComponent, {
					minWidth: '80vw',
					minHeight: '80vh',
					data: {
						id: submessage,
						name: this.breadCrumb(),
						structure: {
							id: '-1',
							gammaId: '-1',
							name: {
								id: '-1',
								typeId: '1152921504606847088',
								gammaId: '-1',
								value: '',
							},
							nameAbbrev: {
								id: '-1',
								typeId: '8355308043647703563',
								gammaId: '-1',
								value: '',
							},
							description: {
								id: '-1',
								typeId: '1152921504606847090',
								gammaId: '-1',
								value: '',
							},
							interfaceMaxSimultaneity: {
								id: '-1',
								typeId: '2455059983007225756',
								gammaId: '-1',
								value: '',
							},
							interfaceMinSimultaneity: {
								id: '-1',
								typeId: '2455059983007225755',
								gammaId: '-1',
								value: '',
							},
							interfaceTaskFileType: {
								id: '-1',
								typeId: '2455059983007225760',
								gammaId: '-1',
								value: 0,
							},
							interfaceStructureCategory: {
								id: '-1',
								typeId: '2455059983007225764',
								gammaId: '-1',
								value: '',
							},
							applicability: applicabilitySentinel,
							elements: [],
						},
					},
				})
				.afterClosed()
				.pipe(
					take(1),
					filter((val) => val !== undefined),
					switchMap((value: AddStructureDialog) =>
						value.structure.id !== '-1' &&
						value.structure.id.length > 0
							? this.structureService.relateStructure(
									value.structure.id
								)
							: this.structureService.createStructure(
									value.structure
								)
					)
				)
		)
	);

	protected openAddStructureDialog() {
		this.structureDialog.subscribe();
	}
}
