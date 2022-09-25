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
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HarnessLoader } from '@angular/cdk/testing';
import { MatCardHarness } from '@angular/material/card/testing';
import { MatButtonHarness } from '@angular/material/button/testing';
import {TestbedHarnessEnvironment} from '@angular/cdk/testing/testbed';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { PlatformTypeCardComponent } from './platform-type-card.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { EditTypeDialogComponent } from '../dialogs/edit-type-dialog/edit-type-dialog.component';
import { editPlatformTypeDialogDataMode } from '../../types/EditPlatformTypeDialogDataMode.enum';
import { CurrentTypesService } from '../../../types-interface/services/current-types.service';
import { of } from 'rxjs';
import { enumerationSet } from '../../types/enum';
import { editPlatformTypeDialogData } from '../../types/editPlatformTypeDialogData';
import { MatIconModule } from '@angular/material/icon';
import { PreferencesUIService } from '../../services/ui/preferences-ui.service';
import { MimPreferencesService } from '../../services/http/mim-preferences.service';
import { MimPreferencesServiceMock } from '../../mocks/MimPreferencesService.mock';
import { EnumerationUIService } from '../../services/ui/enumeration-ui.service';
import { enumerationSetServiceMock } from '../../mocks/enumeration.set.service.mock';
import { EnumerationSetService } from '../../services/http/enumeration-set.service';
import { TypesService } from '../../services/http/types.service';
import { typesServiceMock } from '../../mocks/types.service.mock';
import { TransactionBuilderService } from '../../../../../transactions/transaction-builder.service';
import { transactionBuilderMock } from '../../../../../transactions/transaction-builder.service.mock';
import { UserDataAccountService } from '../../../../../userdata/services/user-data-account.service';
import { userDataAccountServiceMock } from '../../../../plconfig/testing/mockUserDataAccountService';
import { applicabilityListServiceMock } from '../../mocks/ApplicabilityListService.mock';
import { enumsServiceMock } from '../../mocks/EnumsService.mock';
import { ApplicabilityListService } from '../../services/http/applicability-list.service';
import { EnumsService } from '../../services/http/enums.service';

let loader: HarnessLoader;

describe('PlatformTypeCardComponent', () => {
  let component: PlatformTypeCardComponent;
  let fixture: ComponentFixture<PlatformTypeCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[MatCardModule, MatIconModule, MatDialogModule,MatButtonModule, MatFormFieldModule,MatSlideToggleModule,FormsModule,MatInputModule, NoopAnimationsModule],
      declarations: [PlatformTypeCardComponent, EditTypeDialogComponent],
      providers: [
        { provide: TypesService, useValue: typesServiceMock },
        { provide: MimPreferencesService, useValue: MimPreferencesServiceMock },
        { provide: EnumerationSetService, useValue: enumerationSetServiceMock },
        { provide: TransactionBuilderService, useValue: transactionBuilderMock },
        { provide: UserDataAccountService, useValue: userDataAccountServiceMock },
        { provide: EnumsService, useValue: enumsServiceMock },
        { provide: ApplicabilityListService, useValue: applicabilityListServiceMock}
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlatformTypeCardComponent);
    loader = TestbedHarnessEnvironment.loader(fixture);
    component = fixture.componentInstance;
    const expectedData={
      id: "0",
      name: "Random enumeration",
      description:'',
      interfaceLogicalType: "enumeration",
      interfacePlatformTypeMinval: "0",
      interfacePlatformTypeMaxval: "1",
      interfacePlatformTypeBitSize: "8",
      interfacePlatformTypeDefaultValue: "0",
      interfacePlatformTypeMsbValue: "0",
      interfacePlatformTypeBitsResolution: "0",
      interfacePlatformTypeCompRate: "0",
      interfacePlatformTypeAnalogAccuracy: "0",
      interfacePlatform2sComplement: false,
      interfacePlatformTypeEnumLiteral: "A string",
      interfacePlatformTypeUnits: "N/A",
      interfacePlatformTypeValidRangeDescription:"N/A"
    }
    component.typeData = expectedData;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a header of class mat-card-header-text with text of Random Boolean',async () => {
    fixture.detectChanges();
    const card = await loader.getHarness(MatCardHarness);
    expect(await card.getTitleText()).toEqual("Random enumeration");
  });
  it('should create a subtitle with text of Boolean',async () => {
    fixture.detectChanges();
    const card = await loader.getHarness(MatCardHarness);
    expect(await card.getSubtitleText()).toEqual("enumeration");
  });

  it('should contain text that has minimum value, maximum value, byte size, default value, msb value, resolution, comp rate, analog accuracy, edit and Create New Type From Base',async () => {
    fixture.detectChanges();
    const card = await loader.getHarness(MatCardHarness);
    expect(await card.getText()).toContain('Random enumeration')
    expect(await card.getText()).toContain('enumeration')
    expect(await card.getText()).toContain('Minimum Value: 0')
    expect(await card.getText()).toContain('Maximum Value: 1')
    expect(await card.getText()).toContain('Bit Size: 8')
    expect(await card.getText()).toContain('Comp Rate: 0')
    expect(await card.getText()).toContain('Default Value: 0')
    expect(await card.getText()).toContain('MSB Value: 0')
    expect(await card.getText()).toContain('Resolution: 0')
    expect(await card.getText()).toContain('Analog Accuracy: 0')
    //expect(await card.getText()).toContain('Edit')
    expect(await card.getText()).toContain('Create New Type From Base')
    expect(await card.getText()).toContain('View Related Enumeration Set Attributes')
  });

  //re-enable if we re-activate platform type editing
  // it('should open dialog and create an edit of an existing type', async() => {
  //   const openDialog = spyOn(component, 'openDialog').and.callThrough();
  //   let dialogRefSpy = jasmine.createSpyObj({ afterClosed: of<editPlatformTypeDialogData>({mode:editPlatformTypeDialogDataMode.edit,type:{name:'',description:'',interfaceLogicalType:'',interfacePlatform2sComplement:false,interfacePlatformTypeAnalogAccuracy:'',interfacePlatformTypeBitSize:'0',interfacePlatformTypeBitsResolution:'',interfacePlatformTypeCompRate:'',interfacePlatformTypeDefaultValue:'0',interfacePlatformTypeEnumLiteral:'',interfacePlatformTypeMaxval:'',interfacePlatformTypeMinval:'',interfacePlatformTypeMsbValue:'',interfacePlatformTypeUnits:'',interfacePlatformTypeValidRangeDescription:''}}), close: null });
  //   let dialogSpy = spyOn(TestBed.inject(MatDialog), 'open').and.returnValue(dialogRefSpy)
  //   const button = await (await loader.getHarness(MatCardHarness)).getHarness(MatButtonHarness.with({ text: new RegExp("Edit") }));
  //   await button.click();
  //   expect(openDialog).toHaveBeenCalledWith(editPlatformTypeDialogDataMode.edit);
  // })

  it('should open dialog and create a copy of an existing type', async() => {
    const openDialog = spyOn(component, 'openDialog').and.callThrough();
    let dialogRefSpy = jasmine.createSpyObj({ afterClosed: of<editPlatformTypeDialogData>({mode:editPlatformTypeDialogDataMode.copy,type:{name:'',description:'',interfaceLogicalType:'',interfacePlatform2sComplement:false,interfacePlatformTypeAnalogAccuracy:'',interfacePlatformTypeBitSize:'0',interfacePlatformTypeBitsResolution:'',interfacePlatformTypeCompRate:'',interfacePlatformTypeDefaultValue:'0',interfacePlatformTypeMaxval:'',interfacePlatformTypeMinval:'',interfacePlatformTypeMsbValue:'',interfacePlatformTypeUnits:'',interfacePlatformTypeValidRangeDescription:''}}), close: null });
    let dialogSpy = spyOn(TestBed.inject(MatDialog), 'open').and.returnValue(dialogRefSpy)
    const button = await (await loader.getHarness(MatCardHarness)).getHarness(MatButtonHarness.with({ text: new RegExp("Create New Type From Base") }));
    await button.click();
    expect(openDialog).toHaveBeenCalledWith(editPlatformTypeDialogDataMode.copy);
  })

  it('should call openEnumDialog()',async () => {
    const openEnumDialog = spyOn(component, 'openEnumDialog').and.callThrough();
    let dialogRefSpy = jasmine.createSpyObj({ afterClosed: of<enumerationSet>({name:'',description:'',applicability:{id:'1',name:'Base'}}), close: null });
    let dialogSpy = spyOn(TestBed.inject(MatDialog),'open').and.returnValue(dialogRefSpy)
    const button = await (await loader.getHarness(MatCardHarness)).getHarness(MatButtonHarness.with({ text: new RegExp("View Related Enumeration Set Attributes") }));
    await button.click();
    expect(openEnumDialog).toHaveBeenCalled();
  })

});