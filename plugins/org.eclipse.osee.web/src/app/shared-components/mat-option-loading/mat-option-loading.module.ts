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
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatOptionLoadingComponent } from './mat-option-loading/mat-option-loading.component';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@NgModule({
	declarations: [MatOptionLoadingComponent],
	imports: [
		CommonModule,
		FormsModule,
		MatFormFieldModule,
		MatProgressSpinnerModule,
		MatSelectModule,
	],
	exports: [MatOptionLoadingComponent],
})
export class MatOptionLoadingModule {}