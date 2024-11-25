/*********************************************************************
 * Copyright (c) 2024 Boeing
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

import { PersistedRateDropdownComponent } from './persisted-rate-dropdown.component';
import { CurrentTransactionService } from '@osee/transactions/services';
import { currentTransactionServiceMock } from '@osee/transactions/services/testing';
import { MockRateDropdownComponent } from '@osee/messaging/rate/rate-dropdown/testing';
import { ATTRIBUTETYPEIDENUM } from '@osee/attributes/constants';
import { applicabilitySentinel } from '@osee/applicability/types';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

describe('PersistedRateDropdownComponent', () => {
	let component: ParentDriverComponent;
	let fixture: ComponentFixture<ParentDriverComponent>;

	@Component({
		selector: 'osee-test-standalone-form',
		imports: [FormsModule, PersistedRateDropdownComponent],
		template: `<form #testForm="ngForm">
			<osee-persisted-rate-dropdown
				[artifactId]="artifactId()"
				[artifactApplicability]="artifactApplicability()"
				[(value)]="value" />
		</form>`,
	})
	class ParentDriverComponent {
		artifactId = signal(`1` as const);
		artifactApplicability = signal(applicabilitySentinel);
		value = signal({
			id: '-1',
			typeId: ATTRIBUTETYPEIDENUM.INTERFACEMESSAGERATE,
			gammaId: '-1',
			value: '',
		});
	}

	beforeEach(async () => {
		await TestBed.overrideComponent(PersistedRateDropdownComponent, {
			set: {
				imports: [MockRateDropdownComponent],
			},
		})
			.configureTestingModule({
				imports: [PersistedRateDropdownComponent],
				providers: [
					{
						provide: CurrentTransactionService,
						useValue: currentTransactionServiceMock,
					},
				],
			})
			.compileComponents();

		fixture = TestBed.createComponent(ParentDriverComponent);
		// fixture.componentRef.setInput('artifactId', '1234');
		// fixture.componentRef.setInput(
		// 	'artifactApplicability',
		// 	applicabilitySentinel
		// );
		// fixture.componentRef.setInput('value', {
		// 	id: '-1',
		// 	typeId: ATTRIBUTETYPEIDENUM.INTERFACEMESSAGERATE,
		// 	gammaId: '-1',
		// 	value: '',
		// });
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
