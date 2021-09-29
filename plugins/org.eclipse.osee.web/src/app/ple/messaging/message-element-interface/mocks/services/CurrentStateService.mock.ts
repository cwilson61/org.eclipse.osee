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
import { BehaviorSubject, of } from "rxjs";
import { MimPreferencesMock } from "../../../shared/mocks/MimPreferences.mock";
import { settingsDialogData } from "../../../shared/types/settingsdialog";
import { CurrentStateService } from "../../services/current-state.service";
import { structure } from "../../types/structure";
import { platformTypesMock } from "../ReturnObjects/PlatformTypes.mock";
import { elementResponseMock } from "../ReturnObjects/response.mock";
import { structuresMock } from "../ReturnObjects/Structures.mock";

export const CurrentStateServiceMock: Partial<CurrentStateService> = {
    createStructure(body: Partial<structure>) {
        return of(elementResponseMock)
    },
    changeElementPlatformType(structureId, elementId, typeId) {
        return of(elementResponseMock)
    },
    partialUpdateElement(body, structureId) {
        return of(elementResponseMock)
    },
    partialUpdateStructure(body) {
        return of(elementResponseMock)
    },
    relateStructure(structureId: string) {
        return of(elementResponseMock)
    },
    updatePreferences(preferences: settingsDialogData) {
        return of(elementResponseMock)
    },
    removeStructureFromSubmessage(structureId: string, submessageId: string) {
        return of(elementResponseMock);
    },
    deleteStructure(structureId: string) {
        return of(elementResponseMock);
    },
    applic: of([{ id: '1', name: 'Base' }, { id: '2', name: 'Second' }]),
    types: of(platformTypesMock),
    preferences: of(MimPreferencesMock),
    structures: of(structuresMock),
    branchId:"10",
    messageId: "10",
    subMessageId: "10",
    connection: "10",
    SubMessageId:new BehaviorSubject("10"),
    BranchId: new BehaviorSubject("10"),
    branchType: new BehaviorSubject("working")
}