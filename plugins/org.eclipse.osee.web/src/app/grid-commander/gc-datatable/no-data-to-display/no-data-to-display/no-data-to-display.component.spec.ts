/*********************************************************************
 * Copyright (c) 2022 Boeing
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
import { MatCardModule } from '@angular/material/card';

import { NoDataToDisplayComponent } from './no-data-to-display.component';

describe('NoDataToDisplayComponent', () => {
	let component: NoDataToDisplayComponent;
	let fixture: ComponentFixture<NoDataToDisplayComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [MatCardModule, NoDataToDisplayComponent],
		}).compileComponents();

		fixture = TestBed.createComponent(NoDataToDisplayComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
