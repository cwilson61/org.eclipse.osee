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
import { DifferenceReport } from "src/app/ple/messaging/shared/types/DifferenceReport";
import { changeTypeEnum, changeTypeNumber, ignoreType, ModificationType } from "src/app/types/change-report/change-report";
import { RelationTypeId } from "src/app/types/constants/RelationTypeId.enum";
export const differenceReportMock: DifferenceReport = {
  "changeItems": {
      "200390": {
          "item": {
              "id": "200390",
              "name": "Node1",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "address": "1111",
              "color": "#893e3e",
              "description": "Edited this node"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200390",
                  "itemId": "38930",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71707",
                      "modType": ModificationType.NEW,
                      "value": "Node 1 description",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "351",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72027",
                      "modType": ModificationType.MODIFIED,
                      "value": "Edited this node",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "293",
                          "branchId": "9"
                      },
                      "gammaId": "71707",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72027",
                      "modType": ModificationType.MODIFIED,
                      "value": "Edited this node",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200390",
                  "itemId": "38931",
                  "itemTypeId": "5726596359647826656",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71705",
                      "modType": ModificationType.NEW,
                      "value": "111",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "351",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72026",
                      "modType": ModificationType.MODIFIED,
                      "value": "1111",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "293",
                          "branchId": "9"
                      },
                      "gammaId": "71705",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72026",
                      "modType": ModificationType.MODIFIED,
                      "value": "1111",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200390",
                  "itemId": "38932",
                  "itemTypeId": "5221290120300474048",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71704",
                      "modType": ModificationType.NEW,
                      "value": "#854c4c",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "351",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72025",
                      "modType": ModificationType.MODIFIED,
                      "value": "#893e3e",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "293",
                          "branchId": "9"
                      },
                      "gammaId": "71704",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72025",
                      "modType": ModificationType.MODIFIED,
                      "value": "#893e3e",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200390",
                  "itemId": "200390",
                  "itemTypeId": "6039606571486514295",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71704",
                      "modType": ModificationType.NEW,
                      "value": "#854c4c",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72025",
                      "modType": ModificationType.MODIFIED,
                      "value": "#893e3e",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71704",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72025",
                      "modType": ModificationType.MODIFIED,
                      "value": "#893e3e",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200391": {
          "item": {
              "id": "200391",
              "name": "Node2(Edit)",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "address": "",
              "color": "#7993b4",
              "description": "Node 2 description"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200391",
                  "itemId": "38933",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71711",
                      "modType": ModificationType.NEW,
                      "value": "Node2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "352",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72028",
                      "modType": ModificationType.MODIFIED,
                      "value": "Node2(Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "294",
                          "branchId": "9"
                      },
                      "gammaId": "71711",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72028",
                      "modType": ModificationType.MODIFIED,
                      "value": "Node2(Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200391",
                  "itemId": "200391",
                  "itemTypeId": "6039606571486514295",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71711",
                      "modType": ModificationType.NEW,
                      "value": "Node2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72028",
                      "modType": ModificationType.MODIFIED,
                      "value": "Node2(Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71711",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72028",
                      "modType": ModificationType.MODIFIED,
                      "value": "Node2(Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200392": {
          "item": {
              "id": "200392",
              "name": "Connection1",
              "primaryNode": 200390,
              "secondaryNode": 200391,
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Added a description",
              "transportType": transportType.HSDN
          },
          "changes": [
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200392",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACECONNECTIONCONTENT,
                      "name": "Interface Connection Content",
                      "order": "LEXICOGRAPHICAL_ASC",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 1955695738,
                      "idString": "6039606571486514298"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71801",
                      "modType": ModificationType.DELETED,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71801",
                      "modType": ModificationType.NEW,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200402",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200392",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACECONNECTIONCONTENT,
                      "name": "Interface Connection Content",
                      "order": "LEXICOGRAPHICAL_ASC",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 1955695738,
                      "idString": "6039606571486514298"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72046",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72046",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200432",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200392",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACECONNECTIONCONTENT,
                      "name": "Interface Connection Content",
                      "order": "LEXICOGRAPHICAL_ASC",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 1955695738,
                      "idString": "6039606571486514298"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "358",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71791",
                      "modType": ModificationType.DELETED,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71791",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200401",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200392",
                  "itemId": "38937",
                  "itemTypeId": "4522496963078776538",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71714",
                      "modType": ModificationType.NEW,
                      "value": "ETHERNET",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "354",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72029",
                      "modType": ModificationType.MODIFIED,
                      "value": "HSDN",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "295",
                          "branchId": "9"
                      },
                      "gammaId": "71714",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72029",
                      "modType": ModificationType.MODIFIED,
                      "value": "HSDN",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200392",
                  "itemId": "39148",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "354",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72030",
                      "modType": ModificationType.NEW,
                      "value": "Added a description",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72030",
                      "modType": ModificationType.NEW,
                      "value": "Added a description",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200392",
                  "itemId": "200392",
                  "itemTypeId": "126164394421696910",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71714",
                      "modType": ModificationType.NEW,
                      "value": "ETHERNET",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72030",
                      "modType": ModificationType.NEW,
                      "value": "Added a description",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71714",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72030",
                      "modType": ModificationType.MODIFIED,
                      "value": "Added a description",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200394": {
          "item": {
              "id": "200394",
              "name": "Node4",
              "applicability": {
                  "id": "1009971623404681232",
                  "name": "Config = Product C"
              },
              "address": "444",
              "color": "",
              "description": "Node      4"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200394",
                  "itemId": "200394",
                  "itemTypeId": "6039606571486514295",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71724",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "353",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71724",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1009971623404681232",
                          "name": "Config = Product C"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "297",
                          "branchId": "9"
                      },
                      "gammaId": "71724",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71724",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1009971623404681232",
                          "name": "Config = Product C"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200396": {
          "item": {
              "id": "200396",
              "name": "Node D",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "address": "000",
              "color": "",
              "description": "Delete this"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200396",
                  "itemId": "38950",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71739",
                      "modType": ModificationType.NEW,
                      "value": "Node D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "346",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71739",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Node D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "299",
                          "branchId": "9"
                      },
                      "gammaId": "71739",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71739",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Node D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200396",
                  "itemId": "38951",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71740",
                      "modType": ModificationType.NEW,
                      "value": "Delete this",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "346",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71740",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "299",
                          "branchId": "9"
                      },
                      "gammaId": "71740",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71740",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200396",
                  "itemId": "38952",
                  "itemTypeId": "5726596359647826656",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71738",
                      "modType": ModificationType.NEW,
                      "value": "000",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "346",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71738",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "000",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "299",
                          "branchId": "9"
                      },
                      "gammaId": "71738",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71738",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "000",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200396",
                  "itemId": "38953",
                  "itemTypeId": "5221290120300474048",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71737",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "346",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71737",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "299",
                          "branchId": "9"
                      },
                      "gammaId": "71737",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71737",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200396",
                  "itemId": "200396",
                  "itemTypeId": "6039606571486514295",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71736",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "346",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71736",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "299",
                          "branchId": "9"
                      },
                      "gammaId": "71736",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71736",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200399": {
          "item": {
              "id": "200399",
              "name": "Message1",
              "subMessages": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "initiatingNode": null,
              "description": "This is message 1",
              "interfaceMessageNumber": "1",
              "interfaceMessagePeriodicity": "Aperiodic",
              "interfaceMessageType": "Connection",
              "interfaceMessageRate": "5",
              "interfaceMessageWriteAccess": false
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200399",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEMESSAGECONTENT,
                      "name": "Interface Message SubMessage Content",
                      "order": "USER_DEFINED",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 225187764,
                      "idString": "2455059983007225780"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "371",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72074",
                      "modType": ModificationType.NEW,
                      "value": "-1,1310720",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72074",
                      "modType": ModificationType.NEW,
                      "value": "-1,1310720",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200436",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200399",
                  "itemId": "38974",
                  "itemTypeId": "3899709087455064789",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71768",
                      "modType": ModificationType.NEW,
                      "value": "OnDemand",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "355",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72031",
                      "modType": ModificationType.MODIFIED,
                      "value": "Aperiodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71768",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72031",
                      "modType": ModificationType.MODIFIED,
                      "value": "Aperiodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200399",
                  "itemId": "200399",
                  "itemTypeId": "2455059983007225775",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71768",
                      "modType": ModificationType.NEW,
                      "value": "OnDemand",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72031",
                      "modType": ModificationType.MODIFIED,
                      "value": "Aperiodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71768",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72031",
                      "modType": ModificationType.MODIFIED,
                      "value": "Aperiodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200392"
          ]
      },
      "200400": {
          "item": {
              "id": "200400",
              "name": "Message2",
              "subMessages": [
                  {
                      "id": "200406",
                      "name": "Submessage D",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "description": "Delete this submessage",
                      "interfaceSubMessageNumber": "4"
                  }
              ],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "initiatingNode": null,
              "description": "This is message 2",
              "interfaceMessageNumber": "2",
              "interfaceMessagePeriodicity": "Aperiodic",
              "interfaceMessageType": "Operational",
              "interfaceMessageRate": "10",
              "interfaceMessageWriteAccess": true
          },
          "changes": [],
          "parents": [
              "200392"
          ]
      },
      "200402": {
          "item": {
              "id": "200402",
              "name": "Message D",
              "subMessages": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "initiatingNode": null,
              "description": "Delete this message",
              "interfaceMessageNumber": "4",
              "interfaceMessagePeriodicity": "Periodic",
              "interfaceMessageType": "Operational",
              "interfaceMessageRate": "10",
              "interfaceMessageWriteAccess": false
          },
          "changes": [
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200402",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEMESSAGESENDINGNODE,
                      "name": "Interface Message Sending Node",
                      "order": "LEXICOGRAPHICAL_ASC",
                      "ordered": true,
                      "multiplicity": "MANY_TO_ONE",
                      "idIntValue": 1955695739,
                      "idString": "6039606571486514299"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71802",
                      "modType": ModificationType.DELETED,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71802",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200391",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38993",
                  "itemTypeId": "2455059983007225754",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71794",
                      "modType": ModificationType.NEW,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71794",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71794",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71794",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38994",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71795",
                      "modType": ModificationType.NEW,
                      "value": "Message D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71795",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Message D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71795",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71795",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Message D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38995",
                  "itemTypeId": "3899709087455064789",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71798",
                      "modType": ModificationType.NEW,
                      "value": "Periodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71798",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Periodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71798",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71798",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Periodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38996",
                  "itemTypeId": "2455059983007225770",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71799",
                      "modType": ModificationType.NEW,
                      "value": "Operational",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71799",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Operational",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71799",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71799",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Operational",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38997",
                  "itemTypeId": "2455059983007225768",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71800",
                      "modType": ModificationType.NEW,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71800",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71800",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71800",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38998",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71797",
                      "modType": ModificationType.NEW,
                      "value": "Delete this message",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71797",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this message",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71797",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71797",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this message",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200402",
                  "itemId": "38999",
                  "itemTypeId": "2455059983007225763",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71796",
                      "modType": ModificationType.NEW,
                      "value": "10",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71796",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "10",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71796",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71796",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "10",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200402",
                  "itemId": "200402",
                  "itemTypeId": "2455059983007225775",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71793",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "357",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71793",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71793",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71793",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200392"
          ]
      },
      "200403": {
          "item": {
              "id": "200403",
              "name": "Submessage1",
              "applicability": {
                  "id": "1009971623404681232",
                  "name": "Config = Product C"
              },
              "description": "This is submessage 1",
              "interfaceSubMessageNumber": "0"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200403",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESUBMESSAGECONTENT,
                      "name": "Interface SubMessage Content",
                      "order": "USER_DEFINED",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 684636562,
                      "idString": "126164394421696914"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72055",
                      "modType": ModificationType.NEW,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72055",
                      "modType": ModificationType.NEW,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200433",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200403",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESUBMESSAGECONTENT,
                      "name": "Interface SubMessage Content",
                      "order": "USER_DEFINED",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 684636562,
                      "idString": "126164394421696914"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71856",
                      "modType": ModificationType.DELETED,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71856",
                      "modType": ModificationType.NEW,
                      "value": "-1,1048576",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200410",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200403",
                  "itemId": "39001",
                  "itemTypeId": "2455059983007225769",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71806",
                      "modType": ModificationType.NEW,
                      "value": "1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "356",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72032",
                      "modType": ModificationType.MODIFIED,
                      "value": "0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71806",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72032",
                      "modType": ModificationType.MODIFIED,
                      "value": "0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200403",
                  "itemId": "200403",
                  "itemTypeId": "126164394421696908",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71803",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "379",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71803",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1009971623404681232",
                          "name": "Config = Product C"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71803",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71803",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1009971623404681232",
                          "name": "Config = Product C"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200399"
          ]
      },
      "200404": {
          "item": {
              "id": "200404",
              "name": "Submessage2 (Edit)",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "This is submessage 2",
              "interfaceSubMessageNumber": "2"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200404",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESUBMESSAGECONTENT,
                      "name": "Interface SubMessage Content",
                      "order": "USER_DEFINED",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 684636562,
                      "idString": "126164394421696914"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "366",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72037",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72037",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200409",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200404",
                  "itemId": "39003",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71809",
                      "modType": ModificationType.NEW,
                      "value": "Submessage2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "372",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72075",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage2 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71809",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72075",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage2 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200404",
                  "itemId": "200404",
                  "itemTypeId": "126164394421696908",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71809",
                      "modType": ModificationType.NEW,
                      "value": "Submessage2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72075",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage2 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71809",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72075",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage2 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200399"
          ]
      },
      "200405": {
          "item": {
              "id": "200405",
              "name": "Submessage UR Edited",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Unrelate this submessage",
              "interfaceSubMessageNumber": "3"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200405",
                  "itemId": "39006",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71814",
                      "modType": ModificationType.NEW,
                      "value": "Submessage UR",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "360",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72033",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage UR Edited",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71814",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72033",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage UR Edited",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200405",
                  "itemId": "200405",
                  "itemTypeId": "126164394421696908",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71814",
                      "modType": ModificationType.NEW,
                      "value": "Submessage UR",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72033",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage UR Edited",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71814",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72033",
                      "modType": ModificationType.MODIFIED,
                      "value": "Submessage UR Edited",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200400",
              "200399"
          ]
      },
      "200406": {
          "item": {
              "id": "200406",
              "name": "Submessage D",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Delete this submessage",
              "interfaceSubMessageNumber": "4"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200406",
                  "itemId": "39009",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71819",
                      "modType": ModificationType.NEW,
                      "value": "Submessage D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "359",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71819",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Submessage D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71819",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71819",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Submessage D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200406",
                  "itemId": "39010",
                  "itemTypeId": "2455059983007225769",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71821",
                      "modType": ModificationType.NEW,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "359",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71821",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71821",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71821",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200406",
                  "itemId": "39011",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71820",
                      "modType": ModificationType.NEW,
                      "value": "Delete this submessage",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "359",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71820",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this submessage",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71820",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71820",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this submessage",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200406",
                  "itemId": "200406",
                  "itemTypeId": "126164394421696908",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71818",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "359",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71818",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71818",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71818",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200400",
              "200399"
          ]
      },
      "200407": {
          "item": {
              "id": "200407",
              "name": "Structure1 (Edit)",
              "elements": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "This is structure 1",
              "interfaceMinSimultaneity": "1",
              "interfaceTaskFileType": 1,
              "interfaceMaxSimultaneity": "2",
              "bytesPerSecondMinimum": 0,
              "incorrectlySized": false,
              "interfaceStructureCategory": "Flight Test",
              "bytesPerSecondMaximum": 0,
              "numElements": 0,
              "sizeInBytes": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200407",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESTRUCTURECONTENT,
                      "name": "Interface Structure Content",
                      "order": "USER_DEFINED",
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 225187765,
                      "idString": "2455059983007225781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72068",
                      "modType": ModificationType.NEW,
                      "value": "-1,2359296",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72068",
                      "modType": ModificationType.NEW,
                      "value": "-1,2359296",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200435",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200407",
                  "itemId": "39012",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71828",
                      "modType": ModificationType.NEW,
                      "value": "Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "364",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72036",
                      "modType": ModificationType.MODIFIED,
                      "value": "Structure1 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71828",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72036",
                      "modType": ModificationType.MODIFIED,
                      "value": "Structure1 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200407",
                  "itemId": "200407",
                  "itemTypeId": "2455059983007225776",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71828",
                      "modType": ModificationType.NEW,
                      "value": "Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72036",
                      "modType": ModificationType.MODIFIED,
                      "value": "Structure1 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71828",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72036",
                      "modType": ModificationType.MODIFIED,
                      "value": "Structure1 (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200403"
          ]
      },
      "200408": {
          "item": {
              "id": "200408",
              "name": "Structure2",
              "elements": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "This is structure 2",
              "interfaceMinSimultaneity": "0",
              "interfaceTaskFileType": 2,
              "interfaceMaxSimultaneity": "0",
              "bytesPerSecondMinimum": 0,
              "incorrectlySized": false,
              "interfaceStructureCategory": "Miscellaneous",
              "bytesPerSecondMaximum": 0,
              "numElements": 0,
              "sizeInBytes": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200408",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESTRUCTURECONTENT,
                      "name": "Interface Structure Content",
                      "order": "USER_DEFINED",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 225187765,
                      "idString": "2455059983007225781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "423",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72275",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72275",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200413",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200408",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESTRUCTURECONTENT,
                      "name": "Interface Structure Content",
                      "order": "USER_DEFINED",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 225187765,
                      "idString": "2455059983007225781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71942",
                      "modType": ModificationType.DELETED,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71942",
                      "modType": ModificationType.NEW,
                      "value": "-1,786432",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200415",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200403"
          ]
      },
      "200410": {
          "item": {
              "id": "200410",
              "name": "Structure D",
              "elements": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Delete this structure",
              "interfaceMinSimultaneity": "0",
              "interfaceTaskFileType": 5,
              "interfaceMaxSimultaneity": "2",
              "bytesPerSecondMinimum": 0,
              "incorrectlySized": false,
              "interfaceStructureCategory": "BIT Status",
              "bytesPerSecondMaximum": 0,
              "numElements": 0,
              "sizeInBytes": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39030",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71852",
                      "modType": ModificationType.NEW,
                      "value": "Structure D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71852",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Structure D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71852",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71852",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Structure D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39031",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71853",
                      "modType": ModificationType.NEW,
                      "value": "Delete this structure",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71853",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this structure",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71853",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71853",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this structure",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39032",
                  "itemTypeId": "2455059983007225756",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71851",
                      "modType": ModificationType.NEW,
                      "value": "2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71851",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71851",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71851",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "2",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39033",
                  "itemTypeId": "2455059983007225755",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71850",
                      "modType": ModificationType.NEW,
                      "value": "0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71850",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71850",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71850",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39034",
                  "itemTypeId": "2455059983007225764",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71855",
                      "modType": ModificationType.NEW,
                      "value": "BIT Status",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71855",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "BIT Status",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71855",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71855",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "BIT Status",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200410",
                  "itemId": "39035",
                  "itemTypeId": "2455059983007225760",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71854",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71854",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71854",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71854",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200410",
                  "itemId": "200410",
                  "itemTypeId": "2455059983007225776",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71849",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "365",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71849",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71849",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71849",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200403"
          ]
      },
      "200411": {
          "item": {
              "id": "200411",
              "name": "Integer1",
              "description": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformType2sComplement": "true",
              "interfacePlatformTypeAnalogAccuracy": "",
              "interfacePlatformTypeEnumLiteral": "",
              "interfacePlatformTypeBitsResolution": "",
              "interfacePlatformTypeValidRangeDescription": "",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeBitSize": "32",
              "interfacePlatformTypeCompRate": "",
              "interfaceLogicalType": "integer",
              "interfacePlatformTypeMsbValue": "",
              "interfacePlatformTypeUnits": "Nm",
              "interfacePlatformTypeMinval": "0"
          },
          "changes": [],
          "parents": []
      },
      "200412": {
          "item": {
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
              "interfacePlatformTypeDescription": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": false,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "Integer1",
              "notes": "This is a note",
              "platformTypeId": 200411,
              "endByte": 3,
              "endWord": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200412",
                  "itemId": "39046",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "361",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200412",
                  "itemId": "200412",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200412",
                  "itemId": "39046",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "361",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200412",
                  "itemId": "200412",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200412",
                  "itemId": "39046",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "361",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200412",
                  "itemId": "200412",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200412",
                  "itemId": "39046",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "361",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200412",
                  "itemId": "200412",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200412",
                  "itemId": "39046",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "361",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200412",
                  "itemId": "200412",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": "This is element 1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71871",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72034",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is element 1 (Edited)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200407",
              "200408"
          ]
      },
      "200413": {
          "item": {
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
              "interfacePlatformTypeDescription": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "1000",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 8,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 64,
              "platformTypeName2": "Double1",
              "notes": "Changed from int to double",
              "platformTypeId": 200424,
              "endByte": 3,
              "endWord": 1
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.DELETED,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200413",
                  "itemId": "39051",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72035",
                      "modType": ModificationType.MODIFIED,
                      "value": "Added a note",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "377",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200413",
                  "itemId": "200413",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.DELETED,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200413",
                  "itemId": "39051",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72035",
                      "modType": ModificationType.MODIFIED,
                      "value": "Added a note",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "377",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200413",
                  "itemId": "200413",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72084",
                      "modType": ModificationType.NEW,
                      "value": "-1,524288",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200413",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "378",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.DELETED,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71880",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200413",
                  "itemId": "39051",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72035",
                      "modType": ModificationType.MODIFIED,
                      "value": "Added a note",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "377",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200413",
                  "itemId": "200413",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71875",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72083",
                      "modType": ModificationType.MODIFIED,
                      "value": "Changed from int to double",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200407",
              "200408"
          ]
      },
      "200414": {
          "item": {
              "id": "200414",
              "name": "Element UR",
              "beginByte": 0,
              "beginWord": 1,
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "logicalType": "integer",
              "autogenerated": false,
              "units": "Nm",
              "description": "Unrelate this element",
              "interfacePlatformTypeDescription": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "Integer1",
              "notes": "",
              "platformTypeId": 200411,
              "endByte": 3,
              "endWord": 1
          },
          "changes": [],
          "parents": [
              "200408",
              "200407"
          ]
      },
      "200415": {
          "item": {
              "id": "200415",
              "name": "Element D",
              "beginByte": 0,
              "beginWord": 0,
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "logicalType": "integer",
              "autogenerated": false,
              "units": "Nm",
              "description": "Delete this element",
              "interfacePlatformTypeDescription": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": false,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "Integer1",
              "notes": "To be deleted",
              "platformTypeId": 200411,
              "endByte": 3,
              "endWord": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.DELETED_AND_DNE_ON_DESTINATION,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200415",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71894",
                      "modType": ModificationType.DELETED,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71894",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200415",
                  "itemId": "39057",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71891",
                      "modType": ModificationType.NEW,
                      "value": "Element D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71891",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Element D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71891",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71891",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Element D",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200415",
                  "itemId": "39058",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71892",
                      "modType": ModificationType.NEW,
                      "value": "Delete this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71892",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71892",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71892",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "Delete this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200415",
                  "itemId": "39059",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71889",
                      "modType": ModificationType.NEW,
                      "value": "To be deleted",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71889",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "To be deleted",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71889",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71889",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "To be deleted",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200415",
                  "itemId": "39060",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71890",
                      "modType": ModificationType.NEW,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71890",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71890",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71890",
                      "modType": ModificationType.ARTIFACT_DELETED,
                      "value": "false",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200415",
                  "itemId": "200415",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71888",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "363",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "71888",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71888",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71888",
                      "modType": ModificationType.DELETED,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": true,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200416": {
          "item": {
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
              "interfacePlatformTypeDescription": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 28,
              "interfaceElementAlterable": false,
              "interfaceElementIndexEnd": 6,
              "elementSizeInBits": 224,
              "platformTypeName2": "Integer1",
              "notes": "",
              "platformTypeId": 200411,
              "endByte": 3,
              "endWord": 6
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39062",
                  "itemTypeId": "2455059983007225802",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "375",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39064",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "374",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200416",
                  "itemId": "200416",
                  "itemTypeId": "6360154518785980502",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39062",
                  "itemTypeId": "2455059983007225802",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "375",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39064",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "374",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200416",
                  "itemId": "200416",
                  "itemTypeId": "6360154518785980502",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39062",
                  "itemTypeId": "2455059983007225802",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "375",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39064",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "374",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200416",
                  "itemId": "200416",
                  "itemTypeId": "6360154518785980502",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39062",
                  "itemTypeId": "2455059983007225802",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "375",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39064",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "374",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200416",
                  "itemId": "200416",
                  "itemTypeId": "6360154518785980502",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39062",
                  "itemTypeId": "2455059983007225802",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": "5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "375",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71899",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72081",
                      "modType": ModificationType.MODIFIED,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200416",
                  "itemId": "39064",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "374",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200416",
                  "itemId": "200416",
                  "itemTypeId": "6360154518785980502",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": "This is an element array",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71898",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72080",
                      "modType": ModificationType.MODIFIED,
                      "value": "This is an element array (Edit)",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200407"
          ]
      },
      "200418": {
          "item": {
              "id": "200418",
              "name": "EnumSet1",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "enumerations": [
                  {
                      "id": "200419",
                      "name": "OPTION 1",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "ordinal": 0
                  },
                  {
                      "id": "200420",
                      "name": "OPTION 2",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "ordinal": 1
                  },
                  {
                      "id": "200421",
                      "name": "OPTION 3",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "ordinal": 2
                  },
                  {
                      "id": "200422",
                      "name": "OPTION 4",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "ordinal": 3
                  },
                  {
                      "id": "200437",
                      "name": "OPTION 5",
                      "applicability": {
                          "id": "1",
                          "name": "Base"
                      },
                      "ordinal": 4
                  }
              ],
              "description": " OPTION 1=0 , OPTION 2=1 , OPTION 3=2 , OPTION 4=3"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200437",
                  "itemId": "39173",
                  "itemTypeId": "2455059983007225790",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "373",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72077",
                      "modType": ModificationType.NEW,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72077",
                      "modType": ModificationType.NEW,
                      "value": "4",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200437",
                  "itemId": "39174",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "373",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72078",
                      "modType": ModificationType.NEW,
                      "value": "OPTION 5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72078",
                      "modType": ModificationType.NEW,
                      "value": "OPTION 5",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200437",
                  "itemId": "200437",
                  "itemTypeId": "2455059983007225793",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "373",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72076",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72076",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200417"
          ]
      },
      "200423": {
          "item": {
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
              "interfacePlatformTypeDescription": "",
              "interfacePlatformTypeDefaultValue": "",
              "interfacePlatformTypeMaxval": "",
              "interfacePlatformTypeMinval": "",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "Enum1",
              "notes": "",
              "platformTypeId": 200417,
              "endByte": 3,
              "endWord": 0
          },
          "changes": [],
          "parents": [
              "200407"
          ]
      },
      "200424": {
          "item": {
              "id": "200424",
              "name": "Double1",
              "description": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformType2sComplement": "true",
              "interfacePlatformTypeAnalogAccuracy": "",
              "interfacePlatformTypeEnumLiteral": "",
              "interfacePlatformTypeBitsResolution": "",
              "interfacePlatformTypeValidRangeDescription": "",
              "interfacePlatformTypeMaxval": "1000",
              "interfacePlatformTypeBitSize": "64",
              "interfacePlatformTypeCompRate": "",
              "interfaceLogicalType": "double",
              "interfacePlatformTypeMsbValue": "",
              "interfacePlatformTypeUnits": "Feet^2",
              "interfacePlatformTypeMinval": "0"
          },
          "changes": [],
          "parents": []
      },
      "200425": {
          "item": {
              "id": "200425",
              "name": "Element Double",
              "beginByte": 0,
              "beginWord": 2,
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "logicalType": "double",
              "autogenerated": false,
              "units": "Feet^2",
              "description": "This element has a type of double",
              "interfacePlatformTypeDescription": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "1000",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 8,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 64,
              "platformTypeName2": "Double1",
              "notes": "Change to Integer later",
              "platformTypeId": 200424,
              "endByte": 3,
              "endWord": 3
          },
          "changes": [],
          "parents": [
              "200407"
          ]
      },
      "200426": {
          "item": {
              "id": "200426",
              "name": "FLOAT1",
              "description": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformType2sComplement": "true",
              "interfacePlatformTypeAnalogAccuracy": "",
              "interfacePlatformTypeEnumLiteral": "",
              "interfacePlatformTypeBitsResolution": "",
              "interfacePlatformTypeValidRangeDescription": "",
              "interfacePlatformTypeMaxval": "150",
              "interfacePlatformTypeBitSize": "32",
              "interfacePlatformTypeCompRate": "",
              "interfaceLogicalType": "float",
              "interfacePlatformTypeMsbValue": "",
              "interfacePlatformTypeUnits": "dB",
              "interfacePlatformTypeMinval": "0"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200426",
                  "itemId": "39108",
                  "itemTypeId": "3899709087455064783",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": "100",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "376",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200426",
                  "itemId": "200426",
                  "itemTypeId": "6360154518785980503",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": "100",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200427": {
          "item": {
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
              "interfacePlatformTypeDescription": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "150",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "FLOAT1",
              "notes": "",
              "platformTypeId": 200426,
              "endByte": 3,
              "endWord": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200426",
                  "itemId": "39108",
                  "itemTypeId": "3899709087455064783",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": "100",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "376",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "337",
                          "branchId": "9"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200426",
                  "itemId": "200426",
                  "itemTypeId": "6360154518785980503",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": "100",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "71962",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72082",
                      "modType": ModificationType.MODIFIED,
                      "value": "150",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": true,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200407"
          ]
      },
      "200430": {
          "item": {
              "id": "200430",
              "name": "Node A",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "address": "555",
              "color": "#2c5926",
              "description": "Added this node"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200430",
                  "itemId": "39141",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "349",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72016",
                      "modType": ModificationType.NEW,
                      "value": "Node A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72016",
                      "modType": ModificationType.NEW,
                      "value": "Node A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200430",
                  "itemId": "39142",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "349",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72017",
                      "modType": ModificationType.NEW,
                      "value": "Added this node",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72017",
                      "modType": ModificationType.NEW,
                      "value": "Added this node",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200430",
                  "itemId": "39143",
                  "itemTypeId": "5726596359647826656",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "349",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72015",
                      "modType": ModificationType.NEW,
                      "value": "555",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72015",
                      "modType": ModificationType.NEW,
                      "value": "555",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200430",
                  "itemId": "39144",
                  "itemTypeId": "5221290120300474048",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "349",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72014",
                      "modType": ModificationType.NEW,
                      "value": "#2c5926",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72014",
                      "modType": ModificationType.NEW,
                      "value": "#2c5926",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200430",
                  "itemId": "200430",
                  "itemTypeId": "6039606571486514295",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "349",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72013",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72013",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200431": {
          "item": {
              "id": "200431",
              "name": "Connection A",
              "primaryNode": 200393,
              "secondaryNode": 200394,
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Added this connection",
              "transportType": transportType.HSDN
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200431",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACECONNECTIONPRIMARYNODE,
                      "name": "Interface Connection Primary Node",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_ONE",
                      "idIntValue": 1955695736,
                      "idString": "6039606571486514296"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72023",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72023",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200393",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200431",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACECONNECTIONSECONDARYNODE,
                      "name": "Interface Connection Secondary Node",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_ONE",
                      "idIntValue": 1955695737,
                      "idString": "6039606571486514297"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72022",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72022",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200394",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200431",
                  "itemId": "39145",
                  "itemTypeId": "4522496963078776538",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72019",
                      "modType": ModificationType.NEW,
                      "value": "HSDN",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72019",
                      "modType": ModificationType.NEW,
                      "value": "HSDN",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200431",
                  "itemId": "39146",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72020",
                      "modType": ModificationType.NEW,
                      "value": "Connection A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72020",
                      "modType": ModificationType.NEW,
                      "value": "Connection A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200431",
                  "itemId": "39147",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72021",
                      "modType": ModificationType.NEW,
                      "value": "Added this connection",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72021",
                      "modType": ModificationType.NEW,
                      "value": "Added this connection",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200431",
                  "itemId": "200431",
                  "itemTypeId": "126164394421696910",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "350",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72018",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72018",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": []
      },
      "200432": {
          "item": {
              "id": "200432",
              "name": "Message A",
              "subMessages": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "initiatingNode": null,
              "description": "Added this message",
              "interfaceMessageNumber": "3",
              "interfaceMessagePeriodicity": "Periodic",
              "interfaceMessageType": "Connection",
              "interfaceMessageRate": "20",
              "interfaceMessageWriteAccess": true
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200432",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEMESSAGESENDINGNODE,
                      "name": "Interface Message Sending Node",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_ONE",
                      "idIntValue": 1955695739,
                      "idString": "6039606571486514299"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72047",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72047",
                      "modType": ModificationType.NEW,
                      "value": "-1,0",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200390",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39149",
                  "itemTypeId": "2455059983007225754",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72039",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72039",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39150",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72040",
                      "modType": ModificationType.NEW,
                      "value": "Message A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72040",
                      "modType": ModificationType.NEW,
                      "value": "Message A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39151",
                  "itemTypeId": "3899709087455064789",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72043",
                      "modType": ModificationType.NEW,
                      "value": "Periodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72043",
                      "modType": ModificationType.NEW,
                      "value": "Periodic",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39152",
                  "itemTypeId": "2455059983007225770",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72044",
                      "modType": ModificationType.NEW,
                      "value": "Connection",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72044",
                      "modType": ModificationType.NEW,
                      "value": "Connection",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39153",
                  "itemTypeId": "2455059983007225768",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72045",
                      "modType": ModificationType.NEW,
                      "value": "3",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72045",
                      "modType": ModificationType.NEW,
                      "value": "3",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39154",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72042",
                      "modType": ModificationType.NEW,
                      "value": "Added this message",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72042",
                      "modType": ModificationType.NEW,
                      "value": "Added this message",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200432",
                  "itemId": "39155",
                  "itemTypeId": "2455059983007225763",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72041",
                      "modType": ModificationType.NEW,
                      "value": "20",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72041",
                      "modType": ModificationType.NEW,
                      "value": "20",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200432",
                  "itemId": "200432",
                  "itemTypeId": "2455059983007225775",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "367",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72038",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72038",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200392"
          ]
      },
      "200433": {
          "item": {
              "id": "200433",
              "name": "Structure A",
              "elements": [],
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Added this structure",
              "interfaceMinSimultaneity": "1",
              "interfaceTaskFileType": 6,
              "interfaceMaxSimultaneity": "1",
              "bytesPerSecondMinimum": 0,
              "incorrectlySized": false,
              "interfaceStructureCategory": "Tactical Status",
              "bytesPerSecondMaximum": 0,
              "numElements": 0,
              "sizeInBytes": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200433",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACESTRUCTURECONTENT,
                      "name": "Interface Structure Content",
                      "order": "USER_DEFINED",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": 225187765,
                      "idString": "2455059983007225781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72061",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72061",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200434",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200433",
                  "itemId": "200433",
                  "itemTypeId": "2455059983007225776",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72048",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72048",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39156",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72051",
                      "modType": ModificationType.NEW,
                      "value": "Structure A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72051",
                      "modType": ModificationType.NEW,
                      "value": "Structure A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39157",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72052",
                      "modType": ModificationType.NEW,
                      "value": "Added this structure",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72052",
                      "modType": ModificationType.NEW,
                      "value": "Added this structure",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39158",
                  "itemTypeId": "2455059983007225756",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72050",
                      "modType": ModificationType.NEW,
                      "value": "1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72050",
                      "modType": ModificationType.NEW,
                      "value": "1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39159",
                  "itemTypeId": "2455059983007225755",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72049",
                      "modType": ModificationType.NEW,
                      "value": "1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72049",
                      "modType": ModificationType.NEW,
                      "value": "1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39160",
                  "itemTypeId": "2455059983007225764",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72054",
                      "modType": ModificationType.NEW,
                      "value": "Tactical Status",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72054",
                      "modType": ModificationType.NEW,
                      "value": "Tactical Status",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200433",
                  "itemId": "39161",
                  "itemTypeId": "2455059983007225760",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "368",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72053",
                      "modType": ModificationType.NEW,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72053",
                      "modType": ModificationType.NEW,
                      "value": "6",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200403"
          ]
      },
      "200434": {
          "item": {
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
              "interfacePlatformTypeDescription": "A 32 bit integer",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "199",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 4,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 32,
              "platformTypeName2": "Integer1",
              "notes": "",
              "platformTypeId": 200411,
              "endByte": 3,
              "endWord": 0
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200434",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200434",
                  "itemId": "200434",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39162",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39163",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39164",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39165",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200434",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200434",
                  "itemId": "200434",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39162",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39163",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39164",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39165",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200434",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200434",
                  "itemId": "200434",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39162",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39163",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39164",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39165",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200434",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200434",
                  "itemId": "200434",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39162",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39163",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39164",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39165",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200434",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72062",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200411",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200434",
                  "itemId": "200434",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72056",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39162",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72059",
                      "modType": ModificationType.NEW,
                      "value": "Element A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39163",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72060",
                      "modType": ModificationType.NEW,
                      "value": "Added this element",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39164",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72057",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200434",
                  "itemId": "39165",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "369",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72058",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200433"
          ]
      },
      "200435": {
          "item": {
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
              "interfacePlatformTypeDescription": "",
              "interfacePlatformTypeDefaultValue": "0",
              "interfacePlatformTypeMaxval": "1000",
              "interfacePlatformTypeMinval": "0",
              "interfaceElementIndexStart": 0,
              "elementSizeInBytes": 8,
              "interfaceElementAlterable": true,
              "interfaceElementIndexEnd": 0,
              "elementSizeInBits": 64,
              "platformTypeName2": "Double1",
              "notes": "",
              "platformTypeId": 200424,
              "endByte": 3,
              "endWord": 1
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39168",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200435",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39169",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200435",
                  "itemId": "200435",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39166",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39167",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39168",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200435",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39169",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200435",
                  "itemId": "200435",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39166",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39167",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39168",
                  "itemTypeId": "1152921504606847085",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72064",
                      "modType": ModificationType.NEW,
                      "value": "",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.RELATION_CHANGE,
                      "name": changeTypeEnum.RELATION_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": false,
                      "notAttributeChange": true,
                      "idIntValue": 333,
                      "idString": "333"
                  },
                  "artId": "200435",
                  "itemId": "-1",
                  "itemTypeId": {
                      "id": RelationTypeId.INTERFACEELEMENTPLATFORMTYPE,
                      "name": "Interface Element Platform Type",
                      "order": "LEXICOGRAPHICAL_ASC",
                      
                      "ordered": true,
                      "multiplicity": "MANY_TO_MANY",
                      "idIntValue": -450940211,
                      "idString": "3899709087455064781"
                  },
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72069",
                      "modType": ModificationType.NEW,
                      "value": "-1,262144",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "200424",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39169",
                  "itemTypeId": "2455059983007225788",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72065",
                      "modType": ModificationType.NEW,
                      "value": "true",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200435",
                  "itemId": "200435",
                  "itemTypeId": "2455059983007225765",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72063",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39166",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72066",
                      "modType": ModificationType.NEW,
                      "value": "Element Added",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200435",
                  "itemId": "39167",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "370",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72067",
                      "modType": ModificationType.NEW,
                      "value": "Added this element to Structure1",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200407"
          ]
      },
      "200436": {
          "item": {
              "id": "200436",
              "name": "Submessage A",
              "applicability": {
                  "id": "1",
                  "name": "Base"
              },
              "description": "Added this submessage",
              "interfaceSubMessageNumber": "3"
          },
          "changes": [
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200436",
                  "itemId": "39170",
                  "itemTypeId": "1152921504606847088",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "371",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72071",
                      "modType": ModificationType.NEW,
                      "value": "Submessage A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72071",
                      "modType": ModificationType.NEW,
                      "value": "Submessage A",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200436",
                  "itemId": "39171",
                  "itemTypeId": "2455059983007225769",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "371",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72073",
                      "modType": ModificationType.NEW,
                      "value": "3",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72073",
                      "modType": ModificationType.NEW,
                      "value": "3",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ATTRIBUTE_CHANGE,
                      "name": changeTypeEnum.ATTRIBUTE_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": false,
                      "idIntValue": 222,
                      "idString": "222"
                  },
                  "artId": "200436",
                  "itemId": "39172",
                  "itemTypeId": "1152921504606847090",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "371",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72072",
                      "modType": ModificationType.NEW,
                      "value": "Added this submessage",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72072",
                      "modType": ModificationType.NEW,
                      "value": "Added this submessage",
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              },
              {
                  "ignoreType": ignoreType.NONE,
                  "changeType": {
                      "id": changeTypeNumber.ARTIFACT_CHANGE,
                      "name": changeTypeEnum.ARTIFACT_CHANGE,
                      "typeId": 2834799904,
                      "notRelationChange": true,
                      "notAttributeChange": true,
                      "idIntValue": 111,
                      "idString": "111"
                  },
                  "artId": "200436",
                  "itemId": "200436",
                  "itemTypeId": "126164394421696908",
                  "baselineVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "firstNonCurrentChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "currentVersion": {
                      "transactionToken": {
                          "id": "371",
                          "branchId": "1628313979987671715"
                      },
                      "gammaId": "72070",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "destinationVersion": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": null,
                      "modType": ModificationType.NONE,
                      "value": null,
                      "uri": "",
                      "valid": false,
                      "applicabilityToken": null
                  },
                  "netChange": {
                      "transactionToken": {
                          "id": "-1",
                          "branchId": "-1"
                      },
                      "gammaId": "72070",
                      "modType": ModificationType.NEW,
                      "value": null,
                      "uri": "",
                      "valid": true,
                      "applicabilityToken": {
                          "id": "1",
                          "name": "Base"
                      }
                  },
                  "synthetic": false,
                  "artIdB": "-1",
                  "deleted": false,
                  "applicabilityCopy": false
              }
          ],
          "parents": [
              "200399"
          ]
      }
  },
  "nodes": [
      "200394",
      "200396",
      "200430",
      "200391",
      "200390"
  ],
  "connections": [
      "200431",
      "200392"
  ],
  "messages": [
      "200402",
      "200432",
      "200399"
  ],
  "subMessages": [
      "200403",
      "200406",
      "200436",
      "200405",
      "200404"
  ],
  "structures": [
      "200408",
      "200433",
      "200407",
      "200410"
  ],
  "elements": [
      "200435",
      "200413",
      "200415",
      "200434",
      "200416",
      "200412",
      "200427",
      "200425",
      "200414",
      "200423"
  ],
  "enumSets": [
      "200418"
  ]
}