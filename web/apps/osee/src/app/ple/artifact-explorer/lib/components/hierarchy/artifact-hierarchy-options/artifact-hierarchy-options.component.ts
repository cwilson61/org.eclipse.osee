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
import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatMenu, MatMenuItem, MatMenuTrigger } from '@angular/material/menu';
import { MatTooltip } from '@angular/material/tooltip';
import { map, take } from 'rxjs';
import { ArtifactHierarchyOptionsService } from '../../../services/artifact-hierarchy-options.service';
import { MatButton } from '@angular/material/button';

@Component({
	selector: 'osee-artifact-hierarchy-options',
	imports: [
		AsyncPipe,
		MatMenuTrigger,
		MatTooltip,
		MatIcon,
		MatMenu,
		MatMenuItem,
		MatButton,
	],
	templateUrl: './artifact-hierarchy-options.component.html',
})
export class ArtifactHierarchyOptionsComponent {
	private optionsService = inject(ArtifactHierarchyOptionsService);

	option$ = this.optionsService.options$;

	toggleShowRelations() {
		this.option$
			.pipe(
				take(1),
				map((currentOptions) => {
					const updatedOptions = {
						...currentOptions,
						showRelations: !currentOptions.showRelations,
					};
					this.optionsService.updateOptions(updatedOptions);
				})
			)
			.subscribe();
	}
}
