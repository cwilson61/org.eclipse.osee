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
import { RouterTestingModule } from '@angular/router/testing';

import { MessagingHelpStaticContentComponent } from './messaging-help-static-content.component';

describe('MessagingHelpStaticContentComponent', () => {
	let component: MessagingHelpStaticContentComponent;
	let fixture: ComponentFixture<MessagingHelpStaticContentComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [MessagingHelpStaticContentComponent, RouterTestingModule],
		}).compileComponents();

		fixture = TestBed.createComponent(MessagingHelpStaticContentComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});