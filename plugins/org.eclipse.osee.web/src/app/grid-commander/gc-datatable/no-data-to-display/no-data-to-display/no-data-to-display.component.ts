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
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';

@Component({
	selector: 'osee-no-data-to-display',
	templateUrl: './no-data-to-display.component.html',
	styleUrls: ['./no-data-to-display.component.sass'],
	standalone: true,
	imports: [MatCardModule],
})
export class NoDataToDisplayComponent {}
