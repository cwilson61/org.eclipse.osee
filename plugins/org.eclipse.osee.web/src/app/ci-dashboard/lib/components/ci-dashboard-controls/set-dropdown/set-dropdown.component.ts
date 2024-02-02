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
import { Component, OnChanges, SimpleChanges, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {
	BehaviorSubject,
	filter,
	from,
	of,
	switchMap,
	combineLatest,
	reduce,
} from 'rxjs';
import type { SetReference } from '../../../types/tmo';
import {
	MatCheckboxChange,
	MatCheckboxModule,
} from '@angular/material/checkbox';
import { CiDashboardUiService } from '../../../services/ci-dashboard-ui.service';
import { CiSetsService } from '../../../services/ci-sets.service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
	selector: 'osee-set-dropdown',
	standalone: true,
	templateUrl: './set-dropdown.component.html',
	imports: [
		CommonModule,
		FormsModule,
		MatAutocompleteModule,
		MatCheckboxModule,
		MatFormFieldModule,
		MatInputModule,
	],
})
export class SetDropdownComponent implements OnChanges {
	@Input() setId: string = '';

	constructor(
		private ciSetsService: CiSetsService,
		private ui: CiDashboardUiService
	) {}

	filterText = new BehaviorSubject<string>('');

	sets = combineLatest([this.ciSetsService.ciSets, this.filterText]).pipe(
		switchMap(([setRefs, filterText]) =>
			from(setRefs).pipe(
				filter((a) =>
					a.name.toLowerCase().includes(filterText.toLowerCase())
				),
				reduce((acc, curr) => [...acc, curr], [] as SetReference[])
			)
		)
	);

	activeOnly = toSignal(this.ciSetsService.activeOnly);

	selectedSet = combineLatest([this.sets, this.ui.ciSetId]).pipe(
		switchMap(([sets, setId]) => {
			if (setId === undefined || setId === '' || setId === '-1') {
				if (sets.length > 0) {
					this.selectSet(sets[0]);
					return of(sets[0]);
				}
				return of(undefined);
			}
			const set = sets.find((v) => v.id === setId);
			return set ? of(set) : of(undefined);
		})
	);

	setActiveOnly(event: MatCheckboxChange) {
		this.ciSetsService.ActiveOnly = event.checked;
	}

	selectSet(set: SetReference) {
		this.ui.routeToSet(set.id);
	}

	applyFilter(text: Event) {
		const value = (text.target as HTMLInputElement).value;
		this.filterText.next(value);
	}

	ngOnChanges(changes: SimpleChanges): void {
		this.ui.CiSetId = this.setId;
	}
}