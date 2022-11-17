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
import { Observable, of } from 'rxjs';
import { TransportTypeService } from '../services/http/transport-type.service';
import { transportType } from '../types/transportType';

export const transportTypes: Required<transportType>[] = [
	{
		id: '12345',
		name: 'ETHERNET',
		byteAlignValidation: false,
		byteAlignValidationSize: 0,
		messageGeneration: false,
		messageGenerationPosition: '',
		messageGenerationType: '',
	},
];
export const transportTypeServiceMock: Partial<TransportTypeService> = {
	getAll: function (branchId: string): Observable<Required<transportType>[]> {
		return of(transportTypes);
	},
	get: function (
		branchId: string,
		artId: string
	): Observable<Required<transportType>> {
		return of(transportTypes[0]);
	},
};