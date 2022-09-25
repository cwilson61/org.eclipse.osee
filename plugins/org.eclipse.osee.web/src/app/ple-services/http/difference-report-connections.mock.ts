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
import { transportType } from "src/app/ple/messaging/shared/types/connection";
import { connectionDiffItem } from "src/app/ple/messaging/shared/types/DifferenceReport";

export const connectionDiffsMock: connectionDiffItem[] = [
    {
        "id": "200431",
        "name": "Connection A",
        "primaryNode": 200393,
        "secondaryNode": 200394,
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "Added this connection",
        "transportType": transportType.HSDN,
        "diffInfo": {
            "added": true,
            "deleted": false,
            "fieldsChanged": {},
            "url": {
                "label": "",
                "url": ""
            }
        }
    },
    {
        "id": "200392",
        "name": "Connection1",
        "primaryNode": 200390,
        "secondaryNode": 200391,
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "Added a description",
        "transportType": transportType.HSDN,
        "diffInfo": {
            "added": false,
            "deleted": false,
            "fieldsChanged": {
                "transportType": "ETHERNET",
                "description": null
            },
            "url": {
                "label": "",
                "url": ""
            }
        }
    }
]