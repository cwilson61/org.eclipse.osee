/*********************************************************************
 * Copyright (c) 2024 Boeing
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
import { Routes } from '@angular/router';
export const routes: Routes = [
	{
		path: '',
		loadComponent: () => import('@osee/toolbar/component'),
		children: [
			{
				path: '',
				loadComponent: () =>
					import(
						'./lib/components/artifact-explorer-user-menu/artifact-explorer-user-menu.component'
					),
				outlet: 'userMenu',
			},
		],
	},
	{
		path: '**',
		redirectTo: '',
	},
];

export default routes;