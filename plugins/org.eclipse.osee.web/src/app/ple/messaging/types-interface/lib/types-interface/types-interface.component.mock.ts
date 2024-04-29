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
import { Component } from '@angular/core';
import { TypesInterfaceComponent } from './types-interface.component';

@Component({
	selector: 'osee-types-interface',
	template: '<p>Dummy</p>',
	standalone: true,
})
export class MockTypesInterfaceComponent
	implements Partial<TypesInterfaceComponent> {}