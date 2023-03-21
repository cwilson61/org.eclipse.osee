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
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NewTypeDialogComponent } from '@osee/messaging/shared/dialogs';
import type {
	newPlatformTypeDialogReturnData,
	PlatformType,
} from '@osee/messaging/shared/types';

@Component({
	selector: 'osee-new-type-dialog',
	template: '<p>Dummy</p>',
})
export class MockNewTypeDialogComponent
	implements Partial<NewTypeDialogComponent>
{
	@Output() dialogClosed =
		new EventEmitter<newPlatformTypeDialogReturnData>();
	@Input() preFillData?: PlatformType[];
	public closeDialog() {
		this.dialogClosed.emit({
			platformType: {},
			createEnum: false,
			enumSetId: '-1',
			enumSetName: '',
			enumSetDescription: '',
			enumSetApplicability: { id: '1', name: 'Base' },
			enums: [],
		});
		return {
			platformType: {},
			createEnum: false,
			enumSetId: '-1',
			enumSetName: '',
			enumSetDescription: '',
			enumSetApplicability: { id: '1', name: 'Base' },
			enums: [],
		};
	}
}