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
import type { difference } from '@osee/shared/types/change-report';
import type { applic } from '@osee/shared/types/applicability';

export interface subMessage {
	id?: string;
	name: string;
	description: string;
	interfaceSubMessageNumber: string;
	autogenerated?: boolean;
	applicability?: applic;
}

export interface subMessageWithChanges extends subMessage {
	added: boolean;
	deleted: boolean;
	changes: submessageChanges;
}

export interface submessageChanges {
	[index: string]: difference | undefined;
	name?: difference;
	description?: difference;
	interfaceSubMessageNumber?: difference;
	applicability?: difference;
}