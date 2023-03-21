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
import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CurrentGraphService } from '../../services/current-graph.service';
import { map, share, shareReplay, switchMap } from 'rxjs/operators';
import { applic } from '@osee/shared/types/applicability';
import { iif, of } from 'rxjs';
import { AsyncPipe, NgIf } from '@angular/common';
import { GraphComponent } from '../graph/graph.component';
import {
	MessagingControlsComponent,
	ViewSelectorComponent,
} from '@osee/messaging/shared';

@Component({
	selector: 'osee-connection-view-host',
	templateUrl: './host.component.html',
	styleUrls: ['./host.component.sass'],
	standalone: true,
	imports: [
		NgIf,
		AsyncPipe,
		GraphComponent,
		MessagingControlsComponent,
		ViewSelectorComponent,
	],
})
export class HostComponent {
	preferences = this.graphService.preferences;
	inEditMode = this.graphService.preferences.pipe(
		map((r) => r.inEditMode),
		share(),
		shareReplay(1)
	);
	inDiffMode = this.graphService.InDiff.pipe(
		switchMap((val) => iif(() => val, of('true'), of('false')))
	);
	sideNav = this.graphService.sideNavContent;
	sideNavOpened = this.sideNav.pipe(map((value) => value.opened));
	constructor(
		public dialog: MatDialog,
		private graphService: CurrentGraphService
	) {}

	branchId = this.graphService.branchId;
	branchType = this.graphService.branchType;

	viewDiff(open: boolean, value: string | number | applic, header: string) {
		this.graphService.sideNav = {
			opened: open,
			field: header,
			currentValue: value,
		};
	}
}