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
import {
	animate,
	state,
	style,
	transition,
	trigger,
} from '@angular/animations';
import { ChangeDetectionStrategy, Component, ViewChild } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule, MatMenuTrigger } from '@angular/material/menu';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { combineLatest, iif, of } from 'rxjs';
import {
	debounceTime,
	filter,
	first,
	map,
	pairwise,
	scan,
	share,
	shareReplay,
	startWith,
	switchMap,
	take,
	takeUntil,
} from 'rxjs/operators';
import { difference } from '@osee/shared/types/change-report';

import { applic } from '@osee/shared/types/applicability';
import { AddMessageDialog } from '../../types/AddMessageDialog';
import { AddSubMessageDialog } from '../../types/AddSubMessageDialog';
import { DeleteMessageDialogComponent } from '../../dialogs/delete-message-dialog/delete-message-dialog.component';
import { RemoveMessageDialogComponent } from '../../dialogs/remove-message-dialog/remove-message-dialog.component';
import { AddSubMessageDialogComponent } from '../../dialogs/add-sub-message-dialog/add-sub-message-dialog.component';
import { AddMessageDialogComponent } from '../../dialogs/add-message-dialog/add-message-dialog.component';
import { AsyncPipe, NgClass, NgFor, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material/tooltip';
import { EditMessageFieldComponent } from '../../fields/edit-message-field/edit-message-field.component';
import { EditMessageNodesFieldComponent } from '../../fields/edit-message-nodes-field/edit-message-nodes-field.component';
import { SubMessageTableComponent } from '../sub-message-table/sub-message-table.component';
import { MatInputModule } from '@angular/material/input';
import {
	CurrentMessagesService,
	HeaderService,
} from '@osee/messaging/shared/services';
import type {
	message,
	messageWithChanges,
	messageChanges,
	EditViewFreeTextDialog,
	ConnectionNode,
} from '@osee/messaging/shared/types';
import { HighlightFilteredTextDirective } from '@osee/shared/utils';
import {
	TwoLayerAddButtonComponent,
	ViewSelectorComponent,
} from '@osee/shared/components';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { EditViewFreeTextFieldDialogComponent } from '@osee/messaging/shared/dialogs/free-text';
import { MessagingControlsComponent } from '@osee/messaging/shared/main-content';

@Component({
	selector: 'osee-messaging-message-table',
	templateUrl: './message-table.component.html',
	styles: [],
	changeDetection: ChangeDetectionStrategy.OnPush,
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ maxHeight: '0vh' })),
			state('expanded', style({ maxHeight: '60vh' })),
			transition(
				'expanded <=> collapsed',
				animate('225ms cubic-bezier(0.42, 0.0, 0.58, 1)')
			),
		]),
		trigger('expandButton', [
			state('closed', style({ transform: 'rotate(0)' })),
			state('open', style({ transform: 'rotate(-180deg)' })),
			transition(
				'open => closed',
				animate('250ms cubic-bezier(0.4, 0.0, 0.2, 1)')
			),
			transition(
				'closed => open',
				animate('250ms cubic-bezier(0.4, 0.0, 0.2, 1)')
			),
		]),
	],
	standalone: true,
	imports: [
		NgIf,
		AsyncPipe,
		RouterLink,
		FormsModule,
		NgFor,
		NgClass,
		MatButtonModule,
		MatIconModule,
		MatFormFieldModule,
		MatTableModule,
		MatInputModule,
		MatTooltipModule,
		MatMenuModule,
		MatDialogModule,
		MatPaginatorModule,
		AddMessageDialogComponent,
		AddSubMessageDialogComponent,
		RemoveMessageDialogComponent,
		DeleteMessageDialogComponent,
		EditMessageFieldComponent,
		EditMessageNodesFieldComponent,
		HighlightFilteredTextDirective,
		SubMessageTableComponent,
		TwoLayerAddButtonComponent,
		ViewSelectorComponent,
		MessagingControlsComponent,
	],
})
export class MessageTableComponent {
	messageData = this.messageService.messages.pipe(
		switchMap((data) =>
			of(new MatTableDataSource<message | messageWithChanges>(data))
		),
		takeUntil(this.messageService.done)
	);
	headers = this.headerService.AllMessageHeaders;
	nonEditableHeaders: (keyof message)[] = [];
	expandedElement = this.messageService.expandedRows;
	filter: string = '';
	searchTerms: string = '';
	preferences = this.messageService.preferences.pipe(
		takeUntil(this.messageService.done)
	);
	inEditMode = this.preferences.pipe(
		map((r) => r.inEditMode),
		share(),
		shareReplay(1),
		takeUntil(this.messageService.done)
	);
	menuPosition = {
		x: '0',
		y: '0',
	};
	@ViewChild(MatMenuTrigger, { static: true })
	matMenuTrigger!: MatMenuTrigger;
	sideNav = this.messageService.sideNavContent;
	sideNavOpened = this.sideNav.pipe(map((value) => value.opened));
	inDiffMode = this.messageService.isInDiff.pipe(
		switchMap((val) => iif(() => val, of('true'), of('false')))
	);
	_connectionsRoute = this.messageService.connectionsRoute;
	messages = this.messageService.messages;
	messagesCount = this.messageService.messagesCount;
	currentPage = this.messageService.currentPage;
	currentPageSize = this.messageService.currentPageSize;

	currentOffset = combineLatest([
		this.messageService.currentPage.pipe(startWith(0), pairwise()),
		this.messageService.currentPageSize,
	]).pipe(
		debounceTime(100),
		scan((acc, [[previousPageNum, currentPageNum], currentSize]) => {
			if (previousPageNum < currentPageNum) {
				return (acc += currentSize);
			} else {
				return acc;
			}
		}, 10)
	);

	minPageSize = combineLatest([this.currentOffset, this.messagesCount]).pipe(
		debounceTime(100),
		switchMap(([offset, messages]) => of([offset, messages])),
		map(([offset, length]) => Math.max(offset + 1, length + 1))
	);

	constructor(
		private messageService: CurrentMessagesService,
		public dialog: MatDialog,
		private headerService: HeaderService
	) {}
	rowIsExpanded(value: string) {
		return this.messageService.expandedRows.pipe(
			map((rows) => rows.map((s) => s.id).includes(value))
		);
	}
	expandRow(value: message | messageWithChanges) {
		this.messageService.addExpandedRow = value;
	}
	hideRow(value: message | messageWithChanges) {
		this.messageService.removeExpandedRow = value;
	}

	rowChange(value: message | messageWithChanges, type: boolean) {
		if (type) {
			this.expandRow(value);
		} else {
			this.hideRow(value);
		}
	}

	applyFilter(event: Event) {
		const filterValue = (event.target as HTMLInputElement).value;
		this.searchTerms = filterValue;
		this.messageService.filter = filterValue.trim().toLowerCase();
		this.filter = filterValue.trim().toLowerCase();
	}
	valueTracker(index: any, item: keyof message) {
		return index;
	}

	messageTracker(index: any, item: message | messageWithChanges) {
		return item.id;
	}

	openNewMessageDialog() {
		let dialogData: Partial<AddMessageDialog> = {
			name: '',
			description: '',
			interfaceMessageNumber: '',
			interfaceMessagePeriodicity: '',
			interfaceMessageRate: '',
			interfaceMessageType: '',
			applicability: {
				id: '1',
				name: 'Base',
			},
			interfaceMessageWriteAccess: false,
			publisherNodes: [
				{
					id: '',
					name: '',
				},
			],
			subscriberNodes: [
				{
					id: '',
					name: '',
				},
			],
			subMessages: [],
		};
		const dialogRef = this.dialog.open(AddMessageDialogComponent, {
			data: dialogData,
		});
		dialogRef
			.afterClosed()
			.pipe(
				first(),
				filter((val) => val !== undefined),
				switchMap(
					({
						publisherNodes,
						subscriberNodes,
						subMessages,
						...val
					}) =>
						this.messageService.createMessage(
							publisherNodes,
							subscriberNodes,
							val
						)
				)
			)
			.subscribe();
	}
	copyMessageDialog(message: message) {
		let dialogData: Partial<AddMessageDialog> = {
			name: message.name,
			description: message.description,
			interfaceMessageNumber: message.interfaceMessageNumber,
			interfaceMessagePeriodicity: message.interfaceMessagePeriodicity,
			interfaceMessageRate: message.interfaceMessageRate,
			interfaceMessageType: message.interfaceMessageType,
			applicability: message.applicability,
			interfaceMessageWriteAccess: message.interfaceMessageWriteAccess,
			publisherNodes: message.publisherNodes,
			subscriberNodes: message.subscriberNodes,
			subMessages: message.subMessages,
		};
		const dialogRef = this.dialog.open(AddMessageDialogComponent, {
			data: dialogData,
		});
		dialogRef
			.afterClosed()
			.pipe(
				first(),
				filter((val) => val !== undefined),
				switchMap(
					({
						publisherNodes,
						subscriberNodes,
						subMessages,
						...val
					}) =>
						this.messageService.createMessage(
							publisherNodes,
							subscriberNodes,
							val,
							subMessages
						)
				)
			)
			.subscribe();
	}

	getNodeNames(nodes: ConnectionNode[]) {
		return nodes.map((n) => n.name).join(', ');
	}

	openMenu(
		event: MouseEvent,
		message: message,
		field: string | boolean | applic,
		header: string
	) {
		event.preventDefault();
		this.menuPosition.x = event.clientX + 'px';
		this.menuPosition.y = event.clientY + 'px';
		this.matMenuTrigger.menuData = {
			message: message,
			field: field,
			header: header,
		};
		this.matMenuTrigger.openMenu();
	}
	removeMessage(message: message) {
		//open dialog, iif result ==='ok' messageservice. removemessage
		this.dialog
			.open(RemoveMessageDialogComponent, {
				data: { message: message },
			})
			.afterClosed()
			.pipe(
				take(1),
				switchMap((dialogResult: string) =>
					iif(
						() => dialogResult === 'ok',
						this.messageService.removeMessage(message.id),
						of()
					)
				)
			)
			.subscribe();
	}

	deleteMessage(message: message) {
		this.dialog
			.open(DeleteMessageDialogComponent, {
				data: { message: message },
			})
			.afterClosed()
			.pipe(
				take(1),
				switchMap((dialogResult: string) =>
					iif(
						() => dialogResult === 'ok',
						this.messageService.deleteMessage(message.id),
						of()
					)
				)
			)
			.subscribe();
	}

	openDescriptionDialog(description: string, messageId: string) {
		this.dialog
			.open(EditViewFreeTextFieldDialogComponent, {
				data: {
					original: JSON.parse(JSON.stringify(description)) as string,
					type: 'Description',
					return: description,
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
						this.messageService.partialUpdateMessage({
							id: messageId,
							description: (response as EditViewFreeTextDialog)
								.return,
						})
					)
				)
			)
			.subscribe();
	}

	getHeaderByName(value: string) {
		return this.headerService.getHeaderByName(value, 'message');
	}

	viewDiff(open: boolean, value: difference, header: string) {
		this.messageService.sideNav = {
			opened: open,
			field: header,
			currentValue: value.currentValue as string | number | applic,
			previousValue: value.previousValue as
				| string
				| number
				| applic
				| undefined,
			transaction: value.transactionToken,
		};
	}

	hasChanges(
		value: message | messageWithChanges
	): value is messageWithChanges {
		return (value as messageWithChanges).changes !== undefined;
	}
	changeExists(
		value: messageWithChanges,
		header: keyof messageChanges
	): header is keyof messageChanges {
		return (value as messageWithChanges).changes[header] !== undefined;
	}

	createNewSubMessage(message: message | messageWithChanges) {
		this.dialog
			.open(AddSubMessageDialogComponent, {
				minWidth: '80%',
				data: {
					name: message.name,
					id: message.id,
					subMessage: {
						name: '',
						description: '',
						interfaceSubMessageNumber: '',
						applicability: {
							id: '1',
							name: 'Base',
						},
					},
				},
			})
			.afterClosed()
			.pipe(
				take(1),
				filter((val) => val !== undefined),
				switchMap((z: AddSubMessageDialog) =>
					iif(
						() =>
							z != undefined &&
							z.subMessage != undefined &&
							z.subMessage.id != undefined &&
							z?.subMessage?.id.length > 0 &&
							z.subMessage.id !== '-1',
						this.messageService.relateSubMessage(
							z.id,
							z?.subMessage?.id || '-1'
						),
						this.messageService.createSubMessage(z.subMessage, z.id)
					)
				)
			)
			.subscribe();
	}
	setPage(event: PageEvent) {
		this.messageService.pageSize = event.pageSize;
		this.messageService.page = event.pageIndex;
	}
}
