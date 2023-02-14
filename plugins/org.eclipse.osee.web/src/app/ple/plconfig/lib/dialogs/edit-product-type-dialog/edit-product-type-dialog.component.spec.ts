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
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
	MatDialogModule,
	MatDialogRef,
	MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ProductType } from '../../types/pl-config-product-types';

import { EditProductTypeDialogComponent } from './edit-product-type-dialog.component';

describe('EditProductTypeDialogComponent', () => {
	let component: EditProductTypeDialogComponent;
	let fixture: ComponentFixture<EditProductTypeDialogComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			imports: [
				MatDialogModule,
				MatButtonModule,
				MatFormFieldModule,
				MatInputModule,
				FormsModule,
				EditProductTypeDialogComponent,
				NoopAnimationsModule,
			],
			providers: [
				{ provide: MatDialogRef, useValue: {} },
				{
					provide: MAT_DIALOG_DATA,
					useValue: new ProductType('Code', 'Description', '1'),
				},
			],
		}).compileComponents();

		fixture = TestBed.createComponent(EditProductTypeDialogComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
