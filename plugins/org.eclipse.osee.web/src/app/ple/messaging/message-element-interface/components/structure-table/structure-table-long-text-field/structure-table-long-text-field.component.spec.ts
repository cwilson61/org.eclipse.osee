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
import { HighlightFilteredTextDirective } from '../../../../../../osee-utils/osee-string-utils/osee-string-utils-directives/highlight-filtered-text.directive';

import { StructureTableLongTextFieldComponent } from './structure-table-long-text-field.component';

describe('StructureTableLongTextFieldComponent', () => {
	let component: StructureTableLongTextFieldComponent;
	let fixture: ComponentFixture<StructureTableLongTextFieldComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [
				StructureTableLongTextFieldComponent,
				HighlightFilteredTextDirective,
			],
		}).compileComponents();

		fixture = TestBed.createComponent(StructureTableLongTextFieldComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});