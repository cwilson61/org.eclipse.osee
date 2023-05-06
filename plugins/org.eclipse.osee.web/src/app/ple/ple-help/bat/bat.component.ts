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
import { MarkdownModule } from 'ngx-markdown';

@Component({
	selector: 'osee-bat',
	standalone: true,
	imports: [CommonModule, MarkdownModule],
	templateUrl: './bat.component.html',
	styleUrls: ['./bat.component.sass'],
})
export class BatComponent {
	markdown = 'assets/help/bat/BAT.md';
}
export default BatComponent;