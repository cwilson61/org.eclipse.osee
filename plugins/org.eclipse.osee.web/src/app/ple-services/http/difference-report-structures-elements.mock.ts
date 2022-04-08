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

// @ts-nocheck
import { structureDiffItem } from "src/app/ple/messaging/shared/types/DifferenceReport";

export const structureElementDiffsMock: structureDiffItem[] = [
    {
        "id": "200433",
        "name": "Structure A",
        "elements": [],
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "Added this structure",
        "interfaceMaxSimultaneity": "1",
        "bytesPerSecondMinimum": 0,
        "interfaceStructureCategory": "Tactical Status",
        "interfaceTaskFileType": 6,
        "interfaceMinSimultaneity": "1",
        "incorrectlySized": false,
        "bytesPerSecondMaximum": 0,
        "sizeInBytes": 0,
        "numElements": 0,
        "diffInfo": {
            "added": true,
            "deleted": false,
            "fieldsChanged": {},
            "url": {
                "label": "",
                "url": ""
            }
        },
        "elementChanges": [
            {
                "id": "200434",
                "name": "Element A",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "integer",
                "autogenerated": false,
                "units": "Nm",
                "description": "Added this element",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "A 32 bit integer",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "199",
                "interfaceElementAlterable": true,
                "elementSizeInBytes": 4,
                "elementSizeInBits": 32,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Integer1",
                "interfaceElementIndexEnd": 0,
                "notes": "",
                "platformTypeId": 200411,
                "endByte": 3,
                "endWord": 0,
                "diffInfo": {
                    "added": true,
                    "deleted": false,
                    "fieldsChanged": {},
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            }
        ]
    },
    {
        "id": "200407",
        "name": "Structure1 (Edit)",
        "elements": [],
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "This is structure 1",
        "interfaceMaxSimultaneity": "2",
        "bytesPerSecondMinimum": 0,
        "interfaceStructureCategory": "Flight Test",
        "interfaceTaskFileType": 1,
        "interfaceMinSimultaneity": "1",
        "incorrectlySized": false,
        "bytesPerSecondMaximum": 0,
        "sizeInBytes": 0,
        "numElements": 0,
        "diffInfo": {
            "added": false,
            "deleted": false,
            "fieldsChanged": {
                "name": "Structure1"
            },
            "url": {
                "label": "",
                "url": ""
            }
        },
        "elementChanges": [
            {
                "id": "200435",
                "name": "Element Added",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "double",
                "autogenerated": false,
                "units": "Feet^2",
                "description": "Added this element to Structure1",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "1000",
                "interfaceElementAlterable": true,
                "elementSizeInBytes": 8,
                "elementSizeInBits": 64,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Double1",
                "interfaceElementIndexEnd": 0,
                "notes": "",
                "platformTypeId": 200424,
                "endByte": 3,
                "endWord": 1,
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
                "id": "200416",
                "name": "Element Array",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "integer",
                "autogenerated": false,
                "units": "Nm",
                "description": "This is an element array (Edit)",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "A 32 bit integer",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "199",
                "interfaceElementAlterable": false,
                "elementSizeInBytes": 28,
                "elementSizeInBits": 224,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Integer1",
                "interfaceElementIndexEnd": 6,
                "notes": "",
                "platformTypeId": 200411,
                "endByte": 3,
                "endWord": 6,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "interfaceElementIndexEnd": "5",
                        "description": "This is an element array"
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            },
            {
                "id": "200413",
                "name": "Element2",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "double",
                "autogenerated": false,
                "units": "Feet^2",
                "description": "This is element 2",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "1000",
                "interfaceElementAlterable": true,
                "elementSizeInBytes": 8,
                "elementSizeInBits": 64,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Double1",
                "interfaceElementIndexEnd": 0,
                "notes": "Changed from int to double",
                "platformTypeId": 200424,
                "endByte": 3,
                "endWord": 1,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "notes": ""
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            },
            {
                "id": "200412",
                "name": "Element1",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "integer",
                "autogenerated": false,
                "units": "Nm",
                "description": "This is element 1 (Edited)",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "A 32 bit integer",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "199",
                "interfaceElementAlterable": false,
                "elementSizeInBytes": 4,
                "elementSizeInBits": 32,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Integer1",
                "interfaceElementIndexEnd": 0,
                "notes": "This is a note",
                "platformTypeId": 200411,
                "endByte": 3,
                "endWord": 0,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "description": "This is element 1"
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            },
            {
                "id": "200427",
                "name": "Element Edit PT",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "float",
                "autogenerated": false,
                "units": "dB",
                "description": "Edit the platform type's values",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "150",
                "interfaceElementAlterable": true,
                "elementSizeInBytes": 4,
                "elementSizeInBits": 32,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "FLOAT1",
                "interfaceElementIndexEnd": 0,
                "notes": "",
                "platformTypeId": 200426,
                "endByte": 3,
                "endWord": 0,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "interfacePlatformTypeMaxval": "100"
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            },
            {
                "id": "200423",
                "name": "Element Enum",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "enumeration",
                "autogenerated": false,
                "units": "",
                "description": "Testing enums",
                "interfacePlatformTypeDefaultValue": "",
                "interfacePlatformTypeDescription": "",
                "interfacePlatformTypeMinval": "",
                "interfacePlatformTypeMaxval": "",
                "interfaceElementAlterable": true,
                "elementSizeInBytes": 4,
                "elementSizeInBits": 32,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Enum1",
                "interfaceElementIndexEnd": 0,
                "notes": "",
                "platformTypeId": 200417,
                "endByte": 3,
                "endWord": 0,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "enumeration": ""
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                },
                "enumeration": "0=OPTION 1 [Base]; 1=OPTION 2 [Base]; 2=OPTION 3 [Base]; 3=OPTION 4 [Base]; 4=OPTION 5 [Base]; "
            }
        ]
    },
    {
        "id": "200410",
        "name": "Structure D",
        "elements": [],
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "Delete this structure",
        "interfaceMaxSimultaneity": "2",
        "bytesPerSecondMinimum": 0,
        "interfaceStructureCategory": "BIT Status",
        "interfaceTaskFileType": 5,
        "interfaceMinSimultaneity": "0",
        "incorrectlySized": false,
        "bytesPerSecondMaximum": 0,
        "sizeInBytes": 0,
        "numElements": 0,
        "diffInfo": {
            "added": false,
            "deleted": true,
            "fieldsChanged": {},
            "url": {
                "label": "",
                "url": ""
            }
        },
        "elementChanges": []
    },
    {
        "id": "200408",
        "name": "Structure2",
        "elements": [
            {
                "id": "200412",
                "name": "Element1",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": null,
                "logicalType": "",
                "autogenerated": false,
                "units": "",
                "description": "This is element 1 (Edited)",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "0",
                "interfaceElementAlterable": false,
                "elementSizeInBytes": 0,
                "elementSizeInBits": 0,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "",
                "interfaceElementIndexEnd": 0,
                "notes": "This is a note",
                "platformTypeId": -1,
                "endByte": -1,
                "endWord": -1
            }
        ],
        "applicability": {
            "id": "1",
            "name": "Base"
        },
        "description": "This is structure 2",
        "interfaceMaxSimultaneity": "0",
        "bytesPerSecondMinimum": 0,
        "interfaceStructureCategory": "Miscellaneous",
        "interfaceTaskFileType": 2,
        "interfaceMinSimultaneity": "0",
        "incorrectlySized": false,
        "bytesPerSecondMaximum": 0,
        "sizeInBytes": 0,
        "numElements": 1,
        "elementChanges": [
            {
                "id": "200412",
                "name": "Element1",
                "beginByte": 0,
                "beginWord": 0,
                "applicability": {
                    "id": "1",
                    "name": "Base"
                },
                "logicalType": "integer",
                "autogenerated": false,
                "units": "Nm",
                "description": "This is element 1 (Edited)",
                "interfacePlatformTypeDefaultValue": "0",
                "interfacePlatformTypeDescription": "A 32 bit integer",
                "interfacePlatformTypeMinval": "0",
                "interfacePlatformTypeMaxval": "199",
                "interfaceElementAlterable": false,
                "elementSizeInBytes": 4,
                "elementSizeInBits": 32,
                "interfaceElementIndexStart": 0,
                "platformTypeName2": "Integer1",
                "interfaceElementIndexEnd": 0,
                "notes": "This is a note",
                "platformTypeId": 200411,
                "endByte": 3,
                "endWord": 0,
                "diffInfo": {
                    "added": false,
                    "deleted": false,
                    "fieldsChanged": {
                        "description": "This is element 1"
                    },
                    "url": {
                        "label": "",
                        "url": ""
                    }
                }
            }
        ],
        "diffInfo": {
            "added": false,
            "deleted": false,
            "fieldsChanged": {},
            "url": {}
        }
    }
]