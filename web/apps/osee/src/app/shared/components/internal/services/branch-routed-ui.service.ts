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
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UiService } from '@osee/shared/services';

@Injectable({
	providedIn: 'root',
})
export class BranchRoutedUIService {
	constructor(
		private branchService: UiService,
		private router: Router
	) {}

	set branchType(value: 'working' | 'baseline' | '') {
		const tree = this.router.parseUrl(this.router.url);
		let baseUrl =
			tree.root.children?.primary?.segments
				.map((s) => s.path)
				.join('/') || this.router.url;
		if (this.branchService.type.getValue() != '') {
			baseUrl = baseUrl.split(
				this.branchService.type.getValue().replace(/ /g, '%20')
			)[0];
		}

		this.branchService.typeValue = value;
		this.branchService.idValue = '';
		this.router.navigate([baseUrl, value], {
			queryParams: tree.queryParams,
		});
	}
	get type() {
		return this.branchService.type;
	}

	get id() {
		return this.branchService.id;
	}

	set branchId(value: string) {
		const tree = this.router.parseUrl(this.router.url);
		let baseUrl =
			tree.root.children?.primary?.segments
				.map((s) => s.path)
				.join('/') || this.router.url;
		if (this.branchService.type.getValue() != '') {
			baseUrl = baseUrl.split(
				this.branchService.type.getValue().replace(/ /g, '%20')
			)[0];
		}
		this.branchService.idValue = value;
		this.router.navigate(
			[baseUrl, this.branchService.type.getValue(), value],
			{ queryParams: tree.queryParams }
		);
	}

	/**
	 *  this function is used to change position from baseline/working & the branch id, however it has the catch of doing in-line position replacement instead of replacing the whole URL
	 */
	set position(value: { type: 'working' | 'baseline' | ''; id: string }) {
		let baseUrl;
		if (this.branchService.type.getValue() != '') {
			baseUrl = this.router.url.split(
				this.branchService.type.getValue().replace(/ /g, '%20')
			);
		} else {
			baseUrl = this.router.url;
		}
		const [initialURL, idURL] = baseUrl;
		let remainingURL = idURL.includes(
			'/' + this.branchService.id.getValue() + '/'
		)
			? idURL
					.split('/' + this.branchService.id.getValue() + '/')[1]
					.replace(/ /g, '%20')
					.split('/')
			: [];
		remainingURL = remainingURL.map((u) => u.replace('%2D', '-'));
		this.branchService.typeValue = value.type;
		this.branchService.idValue = value.id;
		this.router.navigate([
			initialURL,
			value.type,
			value.id,
			...remainingURL,
		]);
	}
}