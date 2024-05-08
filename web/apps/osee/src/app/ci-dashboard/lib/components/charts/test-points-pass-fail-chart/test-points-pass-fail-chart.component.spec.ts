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
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TestPointsPassFailChartComponent } from './test-points-pass-fail-chart.component';

describe('TestPointsPassFailChartComponent', () => {
	let component: TestPointsPassFailChartComponent;
	let fixture: ComponentFixture<TestPointsPassFailChartComponent>;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [TestPointsPassFailChartComponent],
		});
		fixture = TestBed.createComponent(TestPointsPassFailChartComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});