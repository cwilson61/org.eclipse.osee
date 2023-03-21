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
import type { subMessage, subMessageWithChanges } from './sub-messages';
import type { ConnectionNode } from './connection-nodes';
import type { nodeToken } from './node';

export interface message {
	[index: string]:
		| string
		| subMessage
		| subMessageWithChanges
		| boolean
		| applic
		| Array<subMessage | subMessageWithChanges>
		| undefined
		| messageChanges
		| nodeToken;
	id: string;
	name: string;
	description: string;
	subMessages: Array<subMessage | subMessageWithChanges>;
	interfaceMessageRate: string;
	interfaceMessagePeriodicity: string;
	interfaceMessageWriteAccess: boolean;
	interfaceMessageType: string;
	interfaceMessageNumber: string;
	applicability?: applic;
	initiatingNode: ConnectionNode;
}

export interface messageWithChanges extends message {
	added: boolean;
	deleted: boolean;
	hasSubMessageChanges: boolean;
	changes: messageChanges;
}

export interface messageChanges {
	name?: difference;
	description?: difference;
	interfaceMessageRate?: difference;
	interfaceMessagePeriodicity?: difference;
	interfaceMessageWriteAccess?: difference;
	interfaceMessageType?: difference;
	interfaceMessageNumber?: difference;
	applicability?: difference;
	initiatingNode?: difference;
}

export interface messageToken
	extends Pick<
		message,
		| 'id'
		| 'name'
		| 'description'
		| 'subMessages'
		| 'interfaceMessageRate'
		| 'interfaceMessagePeriodicity'
		| 'interfaceMessageWriteAccess'
		| 'interfaceMessageType'
		| 'interfaceMessageNumber'
		| 'applicability'
	> {
	initiatingNode: nodeToken;
}