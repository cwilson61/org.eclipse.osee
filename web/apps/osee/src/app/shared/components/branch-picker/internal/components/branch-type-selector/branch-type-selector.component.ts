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
import { LowerCasePipe, NgClass } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
	MatRadioButton,
	MatRadioChange,
	MatRadioGroup,
} from '@angular/material/radio';
import { BranchRoutedUIService } from '@osee/shared/services';

@Component({
	selector: 'osee-branch-type-selector',
	templateUrl: './branch-type-selector.component.html',
	styles: [],
	imports: [
		MatRadioGroup,
		MatRadioButton,
		FormsModule,
		NgClass,
		LowerCasePipe,
	],
})
export class BranchTypeSelectorComponent implements OnInit {
	private routerState = inject(BranchRoutedUIService);

	branchTypes: string[] = ['Product Line', 'Working'];
	branchType = '';

	ngOnInit(): void {
		this.routerState.type.subscribe((value) => {
			this.branchType = value;
		});
	}

	changeBranchType(value: 'working' | 'baseline' | '') {
		this.routerState.branchType = value;
	}

	selectType(event: MatRadioChange) {
		this.changeBranchType(event.value as 'working' | 'baseline' | '');
	}
	normalizeType(type: string) {
		if (type === 'product line') {
			return 'baseline';
		}
		return type;
	}
}
