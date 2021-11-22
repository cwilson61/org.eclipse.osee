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
import { subMessage, subMessageWithChanges } from "../../types/sub-messages";

export const subMessagesMock: Required<subMessage|subMessageWithChanges>[] = [
    {
        name: 'submessage0',
        description: '',
        interfaceSubMessageNumber: '0',
        id: '1',
        applicability: {
            id: '1',
            name:'Base'
        },
        changes: {
            name: {
                previousValue: '',
                currentValue: 'submessage0',
                transactionToken: {
                    id: '-1',
                    branchId:'-1'
                }
            }
        }
    }
]