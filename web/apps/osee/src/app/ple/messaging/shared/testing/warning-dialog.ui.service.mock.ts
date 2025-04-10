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
import { of } from 'rxjs';
import { WarningDialogService } from '../services/ui/warning-dialog.service';
import type {
	PlatformType,
	element,
	message,
	structure,
	subMessage,
} from '@osee/messaging/shared/types';

export const warningDialogServiceMock: Partial<WarningDialogService> = {
	openMessageDialog(body: Partial<message>) {
		return of(body);
	},
	openSubMessageDialog(body: Partial<subMessage>) {
		return of(body);
	},

	openStructureDialog(body: Partial<structure>) {
		return of(body);
	},
	openElementDialogById(body: `${number}`) {
		return of(body);
	},
	openElementDialog(elements: Partial<element>[]) {
		return of(elements);
	},
	openEnumsDialogs(enums, enumSets, platformTypes) {
		return of([]);
	},
	openPlatformTypeDialog(body: Partial<PlatformType>) {
		return of(body);
	},
	openPlatformTypeDialogWithManifest(tx) {
		return of(tx);
	},
};
