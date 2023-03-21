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
import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { from, of } from 'rxjs';
import { take, filter, mergeMap, reduce, switchMap } from 'rxjs/operators';
import { CurrentGraphService } from '../../services/current-graph.service';
import { RemovalDialog } from '../../types/ConfirmRemovalDialog';
import { ConfirmRemovalDialogComponent } from '../../dialogs/confirm-removal-dialog/confirm-removal-dialog.component';
import { EditConnectionDialogComponent } from '../../dialogs/edit-connection-dialog/edit-connection-dialog.component';
import { applic } from '@osee/shared/types/applicability';
import { difference } from '@osee/shared/types/change-report';
import type {
	connection,
	connectionWithChanges,
	node,
	nodeData,
	nodeDataWithChanges,
	OseeNode,
	transportType,
} from '@osee/messaging/shared';
import { AsyncPipe, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';

@Component({
	selector: 'osee-messaging-graph-link-menu',
	templateUrl: './graph-link-menu.component.html',
	styleUrls: ['./graph-link-menu.component.sass'],
	standalone: true,
	imports: [
		NgIf,
		AsyncPipe,
		RouterLink,
		MatIconModule,
		MatMenuModule,
		MatButtonModule,
	],
})
export class GraphLinkMenuComponent {
	@Input() editMode: boolean = false;
	@Input() data: connection | connectionWithChanges = {
		name: '',
		dashed: false,
		description: '',
		transportType: {} as transportType,
	};

	@Input()
	source!: OseeNode<node | nodeData | nodeDataWithChanges>;
	@Input()
	target!: OseeNode<node | nodeData | nodeDataWithChanges>;
	_messageRoute = this.graphService.messageRoute;

	constructor(
		private graphService: CurrentGraphService,
		public dialog: MatDialog
	) {}

	openConnectionEditDialog(value: connection) {
		let dialogRef = this.dialog.open(EditConnectionDialogComponent, {
			data: Object.assign({}, value),
		});
		dialogRef
			.afterClosed()
			.pipe(
				//only take first response
				take(1),
				//filter out non-valid responses
				filter(
					(dialogResponse) =>
						dialogResponse !== undefined && dialogResponse !== null
				),
				//convert object to key-value pair emissions emitted sequentially instead of all at once
				mergeMap((arrayDialogResponse: connection) =>
					from(Object.entries(arrayDialogResponse)).pipe(
						//filter out key-value pairs that are unchanged on value, and maintain id property
						filter(
							(filteredProperties) =>
								value[
									filteredProperties[0] as keyof connection
								] !== filteredProperties[1] ||
								filteredProperties[0] === 'id'
						),
						//accumulate into an array of properties that are changed
						reduce(
							(acc, curr) => [...acc, curr],
							[] as [string, any][]
						)
					)
				),
				//transform array of properties into Partial<connection> using Object.fromEntries()(ES2019)
				switchMap((arrayOfProperties: [string, any][]) =>
					of(
						Object.fromEntries(
							arrayOfProperties
						) as Partial<connection>
					).pipe(
						//HTTP PATCH call to update value
						switchMap((changes) =>
							this.graphService.updateConnection(
								changes,
								value.transportType.id || ''
							)
						)
					)
				)
			)
			.subscribe();
	}

	openRemoveConnectionDialog(
		value: connection,
		source: OseeNode<node | nodeData | nodeDataWithChanges>,
		target: OseeNode<node | nodeData | nodeDataWithChanges>
	) {
		let dialogRef = this.dialog.open(ConfirmRemovalDialogComponent, {
			data: {
				id: value.id,
				name: value.name,
				extraNames: [source.label, target.label],
				type: 'connection',
			},
		});
		dialogRef
			.afterClosed()
			.pipe(
				//only take first response
				take(1),
				//filter out non-valid responses
				filter(
					(dialogResponse): dialogResponse is RemovalDialog =>
						dialogResponse !== undefined && dialogResponse !== null
				),
				//make sure there is a name and id, and extra names
				filter(
					(result: RemovalDialog) =>
						result.name.length > 0 &&
						result.id.length > 0 &&
						result.extraNames.length > 0
				),
				mergeMap((dialogResults: RemovalDialog) =>
					from([
						{ node: source.id, connection: dialogResults.id },
						{ node: target.id, connection: dialogResults.id },
					]).pipe(
						mergeMap((request) =>
							this.graphService.unrelateConnection(
								request.node,
								request.connection
							)
						)
					)
				)
			)
			.subscribe();
	}

	viewDiff(open: boolean, value: difference, header: string) {
		let current = value.currentValue as string | number | applic;
		let prev = value.previousValue as string | number | applic;
		if (prev === null) {
			prev = '';
		}
		if (current === null) {
			current = '';
		}
		this.graphService.sideNav = {
			opened: open,
			field: header,
			currentValue: current,
			previousValue: prev,
			transaction: value.transactionToken,
		};
	}

	hasChanges(
		value: connection | connectionWithChanges
	): value is connectionWithChanges {
		return (value as connectionWithChanges).changes !== undefined;
	}
}