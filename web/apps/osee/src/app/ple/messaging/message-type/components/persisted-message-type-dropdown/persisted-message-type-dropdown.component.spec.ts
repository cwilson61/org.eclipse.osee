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

import { PersistedMessageTypeDropdownComponent } from './persisted-message-type-dropdown.component';
import { CurrentTransactionService } from '@osee/transactions/services';
import { currentTransactionServiceMock } from '@osee/transactions/services/testing';
import { applicabilitySentinel } from '@osee/applicability/types';
import { ATTRIBUTETYPEIDENUM } from '@osee/attributes/constants';
import { MockMessageTypeDropdownComponent } from '@osee/messaging/message-type/message-type-dropdown/testing';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

describe('PersistedMessageTypeDropdownComponent', () => {
	let component: ParentDriverComponent;
	let fixture: ComponentFixture<ParentDriverComponent>;

	@Component({
		selector: 'osee-test-standalone-form',
		standalone: true,
		imports: [FormsModule, PersistedMessageTypeDropdownComponent],
		template: `<form #testForm="ngForm">
			<osee-persisted-message-type-dropdown
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
			typeId: ATTRIBUTETYPEIDENUM.INTERFACEMESSAGETYPE,
			gammaId: '-1',
			value: '',
		});
	}

	beforeEach(async () => {
		await TestBed.overrideComponent(PersistedMessageTypeDropdownComponent, {
			set: {
				imports: [MockMessageTypeDropdownComponent],
			},
		})
			.configureTestingModule({
				imports: [PersistedMessageTypeDropdownComponent],
				providers: [
					{
						provide: CurrentTransactionService,
						useValue: currentTransactionServiceMock,
					},
				],
			})
			.compileComponents();

		fixture = TestBed.createComponent(ParentDriverComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});