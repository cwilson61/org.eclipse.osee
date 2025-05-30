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
import { enumerationSet } from '@osee/messaging/shared/types';
import { transactionResultMock } from '@osee/transactions/testing';
import { of } from 'rxjs';
import { EnumerationUIService } from '../services/ui/enumeration-ui.service';
import { enumerationSetMock } from './enumeration-set.response.mock';

export const enumerationUiServiceMock: Partial<EnumerationUIService> = {
	get enumSets() {
		return of(enumerationSetMock);
	},
	getEnumSet(platformTypeId: string) {
		return of(enumerationSetMock[0]);
	},
	changeEnumSet(
		currentEnumSet: enumerationSet,
		previousEnumSet: enumerationSet
	) {
		return of(transactionResultMock);
	},
};
