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
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchCriteriaComponent } from './search-criteria/search-criteria.component';
import { DiffTableComponent } from './diff-table/diff-table.component';
import { SearchOptions } from './model/types';
import { Subject } from 'rxjs';

@Component({
	selector: 'osee-diff-report',
	standalone: true,
	imports: [CommonModule, SearchCriteriaComponent, DiffTableComponent],
	templateUrl: './diff-report.component.html',
})
export class DiffReportComponent {
	displayTable = new Subject<Boolean>();

	startGeneratingReport(searchOptions: SearchOptions) {
		this.displayTable.next(searchOptions.displaySearch);
	}
}

export default DiffReportComponent;