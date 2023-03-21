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
import { TestBed } from '@angular/core/testing';
import { TestScheduler } from 'rxjs/testing';
import {
	differenceReportMock,
	DifferenceReportServiceMock,
} from '@osee/messaging/shared/testing';
import { UiService } from 'src/app/ple-services/ui/ui.service';

import { DifferenceReportBranchInfoService } from './difference-report-branch-info.service';
import { DifferenceReportService } from '../http/difference-report.service';
import { DifferenceBranchInfoService } from 'src/app/ple-services/ui/diff/difference-branch-info.service';
import { DifferenceBranchInfoServiceMock } from 'src/app/ple-services/ui/diff/difference-branch-info.service.mock';

describe('DifferenceReportBranchInfoService', () => {
	let service: DifferenceReportBranchInfoService;
	let scheduler: TestScheduler;
	let uiService: UiService;

	beforeEach(() => {
		TestBed.configureTestingModule({
			providers: [
				{
					provide: DifferenceReportService,
					useValue: DifferenceReportServiceMock,
				},
				{
					provide: DifferenceBranchInfoService,
					useValue: DifferenceBranchInfoServiceMock,
				},
			],
		});
		service = TestBed.inject(DifferenceReportBranchInfoService);
		uiService = TestBed.inject(UiService);
		uiService.idValue = '10';
	});

	beforeEach(
		() =>
			(scheduler = new TestScheduler((actual, expected) => {
				expect(actual).toEqual(expected);
			}))
	);

	it('should be created', () => {
		expect(service).toBeTruthy();
	});

	it('should get difference report', () => {
		scheduler.run(({ expectObservable }) => {
			const expectedValues = { a: differenceReportMock };
			const expectedMarble = '(a|)';
			expectObservable(service.differenceReport('10')).toBe(
				expectedMarble,
				expectedValues
			);
		});
	});
});