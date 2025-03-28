/*********************************************************************
 * Copyright (c) 2021 Boeing
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
import { AsyncPipe, NgClass } from '@angular/common';
import {
	ChangeDetectionStrategy,
	Component,
	EventEmitter,
	inject,
	Input,
	OnChanges,
	Output,
	SimpleChanges,
	viewChild,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatAnchor } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatLabel } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import {
	MatMenu,
	MatMenuContent,
	MatMenuItem,
	MatMenuTrigger,
} from '@angular/material/menu';
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
import { Router, RouterLink } from '@angular/router';
import { PersistedApplicabilityDropdownComponent } from '@osee/applicability/persisted-applicability-dropdown';
import { applic, applicabilitySentinel } from '@osee/applicability/types';
import { PersistedStringAttributeInputComponent } from '@osee/attributes/persisted-string-attribute-input';
import {
	CurrentMessagesService,
	HeaderService,
} from '@osee/messaging/shared/services';
import type {
	EditViewFreeTextDialog,
	message,
	messageWithChanges,
	subMessage,
	subMessageWithChanges,
} from '@osee/messaging/shared/types';
import { UiService } from '@osee/shared/services';
import { difference } from '@osee/shared/types/change-report';
import {
	DisplayTruncatedStringWithFieldOverflowPipe,
	HighlightFilteredTextDirective,
} from '@osee/shared/utils';
import { combineLatest, iif, of } from 'rxjs';
import { filter, switchMap, take } from 'rxjs/operators';
import { AddSubMessageDialogComponent } from '../../dialogs/add-sub-message-dialog/add-sub-message-dialog.component';
import { DeleteSubmessageDialogComponent } from '../../dialogs/delete-submessage-dialog/delete-submessage-dialog.component';
import { RemoveSubmessageDialogComponent } from '../../dialogs/remove-submessage-dialog/remove-submessage-dialog.component';
import { SubMessageImpactsValidatorDirective } from '../../sub-message-impacts-validator.directive';
import { AddSubMessageDialog } from '../../types/AddSubMessageDialog';
import { EditViewFreeTextFieldDialogComponent } from '@osee/shared/components';

@Component({
	selector: 'osee-messaging-sub-message-table',
	templateUrl: './sub-message-table.component.html',
	styles: [],
	changeDetection: ChangeDetectionStrategy.OnPush,
	imports: [
		AsyncPipe,
		NgClass,
		RouterLink,
		HighlightFilteredTextDirective,
		DisplayTruncatedStringWithFieldOverflowPipe,
		MatTable,
		MatColumnDef,
		MatHeaderCell,
		MatHeaderCellDef,
		MatTooltip,
		MatCell,
		MatCellDef,
		MatAnchor,
		MatHeaderRow,
		MatHeaderRowDef,
		MatRow,
		MatRowDef,
		MatMenu,
		MatMenuContent,
		MatMenuItem,
		MatIcon,
		MatMenuTrigger,
		MatLabel,
		FormsModule,
		PersistedApplicabilityDropdownComponent,
		PersistedStringAttributeInputComponent,
		SubMessageImpactsValidatorDirective,
	],
})
export class SubMessageTableComponent implements OnChanges {
	dialog = inject(MatDialog);
	private router = inject(Router);
	private messageService = inject(CurrentMessagesService);
	private headerService = inject(HeaderService);
	private _ui = inject(UiService);

	@Input() data: subMessage[] = [];
	@Input() dataSource: MatTableDataSource<subMessage> =
		new MatTableDataSource<subMessage>();
	@Input() filter = '';

	@Input() element!: message;
	@Input() editMode = false;
	@Output() expandRow = new EventEmitter();
	headers = this.headerService.AllSubMessageHeaders.pipe(
		switchMap(([name, description, number, applicability]) =>
			of<(keyof subMessage | ' ')[]>([
				name,
				description,
				number,
				' ',
				applicability,
			])
		)
	);
	_messageRoute = combineLatest([
		this.messageService.initialRoute,
		this.messageService.endOfRoute,
	]).pipe(
		switchMap(([initial, end]) => of({ beginning: initial, end: end }))
	);
	menuPosition = {
		x: '0',
		y: '0',
	};
	matMenuTrigger = viewChild.required(MatMenuTrigger);

	/** Inserted by Angular inject() migration for backwards compatibility */
	constructor(...args: unknown[]);
	constructor() {
		this.dataSource.data = this.data;
	}

	ngOnChanges(_changes: SimpleChanges): void {
		this.dataSource.data = this.data;
		if (this.filter !== '') {
			if (this.dataSource.filteredData.length > 0) {
				this.expandRow.emit(this.element);
			}
		}
	}

	valueTracker(index: number, _item: unknown) {
		return index;
	}

	subMessageTracker(
		_index: number,
		item: subMessage | subMessageWithChanges
	) {
		return item.id;
	}

	openMenu(
		event: MouseEvent,
		message: message | messageWithChanges,
		submessage: subMessage | subMessageWithChanges,
		location: string,
		field: string | applic,
		header: keyof subMessage | ' '
	) {
		event.preventDefault();
		this.menuPosition.x = event.clientX + 'px';
		this.menuPosition.y = event.clientY + 'px';
		this.matMenuTrigger().menuData = {
			message: message,
			submessage: submessage,
			location: location,
			field: field,
			header: header,
			change:
				(submessage as subMessageWithChanges).changes !== undefined &&
				header !== ' ' &&
				(submessage as subMessageWithChanges).changes[header] !==
					undefined
					? (submessage as subMessageWithChanges).changes[header]
					: undefined,
		};
		this.matMenuTrigger().openMenu();
	}

	removeSubMessage(submessage: subMessage, message: message) {
		this.dialog
			.open(RemoveSubmessageDialogComponent, {
				data: { submessage: submessage, message: message },
			})
			.afterClosed()
			.pipe(
				take(1),
				switchMap((dialogResult: string) =>
					iif(
						() => dialogResult === 'ok',
						this.messageService.removeSubMessage(
							submessage?.id || '',
							message.id
						),
						of()
					)
				)
			)
			.subscribe();
	}
	deleteSubMessage(submessage: subMessage) {
		this.dialog
			.open(DeleteSubmessageDialogComponent, {
				data: { submessage: submessage },
			})
			.afterClosed()
			.pipe(
				take(1),
				switchMap((dialogResult: string) =>
					iif(
						() => dialogResult === 'ok',
						this.messageService.deleteSubMessage(
							submessage.id || ''
						),
						of()
					)
				)
			)
			.subscribe();
	}

	_addSubMessageDialog(message: message, submessage: subMessage) {
		if ('autogenerated' in submessage) {
			delete submessage.autogenerated;
		}
		return this.dialog.open(AddSubMessageDialogComponent, {
			minWidth: '80%',
			data: {
				name: message.name.value,
				id: message.id,
				subMessage: submessage,
			},
		});
	}
	insertSubMessage(message: message, afterSubMessage?: string) {
		this._addSubMessageDialog(message, {
			id: '-1',
			gammaId: '-1',
			name: {
				id: '-1',
				typeId: '1152921504606847088',
				gammaId: '-1',
				value: '',
			},
			description: {
				id: '-1',
				typeId: '1152921504606847090',
				gammaId: '-1',
				value: '',
			},
			interfaceSubMessageNumber: {
				id: '-1',
				typeId: '2455059983007225769',
				gammaId: '-1',
				value: '',
			},
			applicability: applicabilitySentinel,
		})
			.afterClosed()
			.pipe(
				take(1),
				filter((val) => val !== undefined),
				switchMap((z: AddSubMessageDialog) =>
					z.subMessage.id.length > 0 && z.subMessage.id !== '-1'
						? this.messageService.relateSubMessage(
								z.id,
								z?.subMessage?.id || '-1',
								afterSubMessage || 'end'
							)
						: this.messageService.createSubMessage(
								z.subMessage,
								z.id,
								afterSubMessage
							)
				)
			)
			.subscribe();
	}

	copySubMessage(
		message: message,
		submessage: subMessage,
		afterSubMessage?: string
	) {
		this._addSubMessageDialog(message, structuredClone(submessage))
			.afterClosed()
			.pipe(
				take(1),
				filter((v) => v !== undefined),
				switchMap((z: AddSubMessageDialog) =>
					this.messageService.copySubMessage(
						z.subMessage,
						z.id,
						afterSubMessage
					)
				)
			)
			.subscribe();
	}

	openDescriptionDialog(submessage: subMessage) {
		const previous = structuredClone(submessage);
		this.dialog
			.open(EditViewFreeTextFieldDialogComponent, {
				data: {
					original: submessage.description.value,
					type: 'Description',
					return: submessage.description.value,
					editable: this.editMode,
				},
				minHeight: '60%',
				minWidth: '60%',
			})
			.afterClosed()
			.pipe(
				take(1),
				switchMap((response: EditViewFreeTextDialog | string) =>
					iif(
						() =>
							response === 'ok' ||
							response === 'cancel' ||
							response === undefined,
						//do nothing
						of(),
						//change description
						this.messageService.partialUpdateSubMessage(
							{
								...submessage,
								description: {
									id: submessage.description.id,
									typeId: submessage.description.typeId,
									gammaId: submessage.description.gammaId,
									value: (response as EditViewFreeTextDialog)
										.return,
								},
							},
							previous
						)
					)
				)
			)
			.subscribe();
	}

	getHeaderByName(value: string) {
		return this.headerService.getHeaderByName(value, 'submessage');
	}

	viewDiff(open: boolean, value: difference, header: string) {
		this.messageService.sideNav = {
			opened: open,
			field: header,
			currentValue: value?.currentValue || '',
			previousValue: value?.previousValue || '',
			transaction: value.transactionToken,
		};
	}
	hasChanges(
		value: subMessage | subMessageWithChanges,
		header:
			| 'name'
			| 'description'
			| 'interfaceSubMessageNumber'
			| 'applicability'
			| ' '
	): value is subMessageWithChanges {
		return (
			(value as subMessageWithChanges).changes !== undefined &&
			header !== ' ' &&
			(value as subMessageWithChanges).changes[header] !== undefined
		);
	}
	isDifference(value: difference | undefined): value is difference {
		return (value as difference) !== undefined;
	}
}
