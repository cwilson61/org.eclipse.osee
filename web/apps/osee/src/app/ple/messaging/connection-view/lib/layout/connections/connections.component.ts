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
import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MessagingControlsComponent } from '@osee/messaging/shared/main-content';
import { CurrentViewSelectorComponent } from '@osee/shared/components';
import { applic } from '@osee/applicability/types';
import { iif, of } from 'rxjs';
import { map, share, shareReplay, switchMap } from 'rxjs/operators';
import { CurrentGraphService } from '../../services/current-graph.service';
import { GraphComponent } from '../graph/graph.component';

@Component({
	selector: 'osee-connection-view-host',
	templateUrl: './connections.component.html',
	imports: [
		AsyncPipe,
		GraphComponent,
		MessagingControlsComponent,
		CurrentViewSelectorComponent,
	],
})
export class ConnectionsComponent {
	dialog = inject(MatDialog);
	private graphService = inject(CurrentGraphService);

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
