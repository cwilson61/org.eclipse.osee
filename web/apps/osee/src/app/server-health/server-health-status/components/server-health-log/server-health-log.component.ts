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
	CdkFixedSizeVirtualScroll,
	CdkVirtualForOf,
	CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';

import { Component, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { scan, shareReplay, switchMap } from 'rxjs';
import { ServerHealthHttpService } from '../../../shared/services/server-health-http.service';
import { AsyncPipe } from '@angular/common';

@Component({
	selector: 'osee-server-health-log',
	imports: [
		AsyncPipe,
		CdkVirtualScrollViewport,
		CdkFixedSizeVirtualScroll,
		CdkVirtualForOf,
	],
	templateUrl: './server-health-log.component.html',
})
export class ServerHealthLogComponent {
	private serverHealthHttpService = inject(ServerHealthHttpService);

	remoteHealthLog = this.serverHealthHttpService.RemoteLog.pipe(
		switchMap((data) => split(data.healthLog.log)),
		scan((acc, curr) => [...acc, curr], [] as string[]),
		shareReplay({ bufferSize: 1, refCount: true }),
		takeUntilDestroyed()
	);

	trackByLine(_index: number, item: string) {
		return item;
	}

	getHeightPx(itemSize: number, optLength: number) {
		return itemSize * Math.min(5, Math.max(optLength, 1));
	}
}
function split(log: string) {
	return log.split('\n');
}
