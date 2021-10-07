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
import { of, BehaviorSubject } from "rxjs";
import { MimPreferencesMock } from "../../shared/mocks/MimPreferences.mock";
import { OSEEWriteApiResponse } from "../../shared/types/ApiWriteResponse";
import { CurrentGraphService } from "../services/current-graph.service";
import { connection } from '../../shared/types/connection'
import { node } from '../../shared/types/node'
import { response } from "./Response.mock";
import { settingsDialogData } from "../../shared/types/settingsdialog";

export const graphServiceMock: Partial<CurrentGraphService> = {
  nodes: of({ nodes: [], edges: [] }),
  updated: new BehaviorSubject<boolean>(true),
  set update(value: boolean) {
    return;
  },
  updateConnection(connection: Partial<connection>) {
    return of(response);
  },
  unrelateConnection(nodeId: string, id: string) {
    return of(response);
  },
  updateNode(node: Partial<node>) {
    return of(response);
  },
  deleteNodeAndUnrelate(nodeId: string, edges: []) {
    return of(response)
  },
  createNewConnection(connection: connection, sourceId: string, targetId: string) {
    return of(response)
  }, 
  createNewNode(node: node) {
    return of(response)
  },
  updatePreferences(preferences: settingsDialogData) {
    return of(response);
  },
  nodeOptions: of([{id:'1',name:'First'},{id:'2',name:'Second'}]),
  applic: of([{ id: '1', name: 'Base' }, { id: '2', name: 'Second' }]),
  preferences: of(MimPreferencesMock)
  }