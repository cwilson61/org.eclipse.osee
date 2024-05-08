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
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
	name: 'ifIdReturnFalse',
	standalone: true,
})
export class IfIdReturnFalsePipe implements PipeTransform {
	transform(value: string, ...args: unknown[]): boolean {
		return value !== 'Id';
	}
}