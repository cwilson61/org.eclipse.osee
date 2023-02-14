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
import { Route } from '@angular/router';
import {
	MULTI_STRUCTURE_SERVICE,
	SINGLE_STRUCTURE_SERVICE,
} from '@osee/messaging/shared';
import { diffReportResolverFn } from '@osee/shared/resolvers';

const routes: Route[] = [
	{
		path: '',
		loadChildren: () => import('./toolbar.routes'),
		outlet: 'toolbar',
	},
	{
		path: '',
		providers: [MULTI_STRUCTURE_SERVICE],
		children: [
			{
				path: '',
				loadComponent: () =>
					import(
						'./multi-structure-table/multi-structure-table.component'
					),
				children: [],
			},
			{
				path: 'diff',
				loadComponent: () =>
					import(
						'./multi-structure-table/multi-structure-table.component'
					),
				resolve: { diff: diffReportResolverFn },
			},
		],
	},
	{
		path: ':structureId',
		providers: [SINGLE_STRUCTURE_SERVICE],
		children: [
			{
				path: '',
				loadComponent: () =>
					import(
						'./single-structure-table/single-structure-table.component'
					),
			},
			{
				path: 'diff',
				loadComponent: () =>
					import(
						'./single-structure-table/single-structure-table.component'
					),
				resolve: { diff: diffReportResolverFn },
			},
		],
	},
	{
		path: '',
		loadComponent: () =>
			import('../../diff-views/single-diff/single-diff.component'),
		outlet: 'rightSideNav',
	},
	{
		path: '',
		loadComponent: () => import('./lib/menus/usermenu/usermenu.component'),
		outlet: 'userMenu',
	},
	// {
	// 	path: '',
	// 	loadComponent: () => import('@osee/messaging/shared/headers'),
	// 	outlet: 'navigationHeader',
	// },
];

export default routes;
