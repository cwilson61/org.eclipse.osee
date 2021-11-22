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
import { difference } from "src/app/types/change-report/change-report";
import { applic } from "../../../../types/applicability/applic";

export interface subMessage {
    id?: string,
    name: string,
    description: string,
    interfaceSubMessageNumber: string,
    applicability?:applic
}

export interface subMessageWithChanges extends subMessage{
    added: boolean,
    deleted: boolean,
    changes: {
        name?: difference,
        description?: difference,
        interfaceSubMessageNumber?: difference,
        applicability?:difference
    }
}