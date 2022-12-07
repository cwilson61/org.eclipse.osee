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
import { HarnessLoader } from '@angular/cdk/testing';
import { TestbedHarnessEnvironment } from '@angular/cdk/testing/testbed';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonHarness } from '@angular/material/button/testing';
import { MatIconModule } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { UserDataAccountService } from '../../userdata/services/user-data-account.service';
import { userDataAccountServiceMock } from '../../userdata/services/user-data-account.service.mock';

import { MessagingComponent } from './messaging.component';
import {
	MessagingHelpDummy,
	MessagingMainMock,
	MessagingTypeSearchMock,
} from './mocks/components/navigation-components.mock';

describe('MessagingComponent', () => {
	let component: MessagingComponent;
	let fixture: ComponentFixture<MessagingComponent>;
	let loader: HarnessLoader;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [
				RouterTestingModule.withRoutes([
					{ path: 'connections', component: MessagingMainMock },
					{ path: 'typeSearch', component: MessagingTypeSearchMock },
					{ path: 'help', component: MessagingHelpDummy },
				]),
				MatIconModule,
			],
			providers: [
				{
					provide: UserDataAccountService,
					useValue: userDataAccountServiceMock,
				},
			],
			declarations: [
				MessagingComponent,
				MessagingMainMock,
				MessagingTypeSearchMock,
				MessagingHelpDummy,
			],
		}).compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(MessagingComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
		loader = TestbedHarnessEnvironment.loader(fixture);
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
