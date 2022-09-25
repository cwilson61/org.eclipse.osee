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
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TransactionBuilderService } from '../../../transactions/transaction-builder.service';
import { transactionBuilderMock } from '../../../transactions/transaction-builder.service.mock';
import { UserDataAccountService } from '../../../userdata/services/user-data-account.service';
import { userDataAccountServiceMock } from '../../plconfig/testing/mockUserDataAccountService';
import { applicabilityListServiceMock } from '../shared/mocks/ApplicabilityListService.mock';
import { enumerationSetServiceMock } from '../shared/mocks/enumeration.set.service.mock';
import { enumsServiceMock } from '../shared/mocks/EnumsService.mock';
import { MimPreferencesServiceMock } from '../shared/mocks/MimPreferencesService.mock';
import { typesServiceMock } from '../shared/mocks/types.service.mock';
import { ApplicabilityListService } from '../shared/services/http/applicability-list.service';
import { EnumerationSetService } from '../shared/services/http/enumeration-set.service';
import { EnumsService } from '../shared/services/http/enums.service';
import { MimPreferencesService } from '../shared/services/http/mim-preferences.service';
import { TypesService } from '../shared/services/http/types.service';

import { TypeDetailComponent } from './type-detail.component';

describe('TypeDetailComponent', () => {
  let component: TypeDetailComponent;
  let fixture: ComponentFixture<TypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      providers: [
        { provide: TypesService, useValue: typesServiceMock },
        { provide: MimPreferencesService, useValue: MimPreferencesServiceMock },
        { provide: EnumerationSetService, useValue: enumerationSetServiceMock },
        { provide: TransactionBuilderService, useValue: transactionBuilderMock },
        { provide: UserDataAccountService, useValue: userDataAccountServiceMock },
        { provide: EnumsService, useValue: enumsServiceMock },
        { provide: ApplicabilityListService, useValue: applicabilityListServiceMock}],
      declarations: [ TypeDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});