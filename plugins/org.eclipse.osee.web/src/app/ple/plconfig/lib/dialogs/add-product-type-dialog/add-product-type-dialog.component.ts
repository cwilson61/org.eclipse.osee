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
import { TextFieldModule } from '@angular/cdk/text-field';
import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
	MatDialogModule,
	MatDialogRef,
	MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ParentErrorStateMatcher } from '@osee/shared/matchers';
import { productType } from '../../types/pl-config-product-types';

@Component({
	selector: 'osee-add-product-type-dialog',
	templateUrl: './add-product-type-dialog.component.html',
	styleUrls: ['./add-product-type-dialog.component.sass'],
	standalone: true,
	imports: [
		FormsModule,
		MatDialogModule,
		MatFormFieldModule,
		MatInputModule,
		TextFieldModule,
		MatButtonModule,
	],
})
export class AddProductTypeDialogComponent {
	parentMatcher = new ParentErrorStateMatcher();
	constructor(
		public dialogRef: MatDialogRef<AddProductTypeDialogComponent>,
		@Inject(MAT_DIALOG_DATA) public data: productType
	) {}

	onNoClick(): void {
		this.dialogRef.close();
	}
}
