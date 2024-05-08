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
import { Component, Inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import {
	MAT_DIALOG_DATA,
	MatDialogActions,
	MatDialogClose,
	MatDialogContent,
	MatDialogRef,
	MatDialogTitle,
} from '@angular/material/dialog';
import { RemoveElementDialogData } from './remove-element-dialog';

@Component({
	selector: 'osee-messaging-remove-element-dialog',
	templateUrl: './remove-element-dialog.component.html',
	styles: [],
	standalone: true,
	imports: [
		MatDialogTitle,
		MatDialogContent,
		MatDialogActions,
		MatButton,
		MatDialogClose,
	],
})
export class RemoveElementDialogComponent {
	constructor(
		public dialogRef: MatDialogRef<RemoveElementDialogComponent>,
		@Inject(MAT_DIALOG_DATA) public data: RemoveElementDialogData
	) {}
}