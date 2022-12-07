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
import { HarnessLoader } from '@angular/cdk/testing';
import { TestbedHarnessEnvironment } from '@angular/cdk/testing/testbed';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MatButtonHarness } from '@angular/material/button/testing';
import {
	MatDialogModule,
	MatDialogRef,
	MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatIconHarness } from '@angular/material/icon/testing';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSelectHarness } from '@angular/material/select/testing';
import { MatTableModule } from '@angular/material/table';
import { MatTableHarness } from '@angular/material/table/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { UserDataAccountService } from '../../../../../../userdata/services/user-data-account.service';
import { userDataAccountServiceMock } from '../../../../../../userdata/services/user-data-account.service.mock';
import { applicabilityListServiceMock } from '../../../mocks/ApplicabilityListService.mock';
import { EditEnumSetFieldMock } from '../../../mocks/EditEnumSetField.component.mock';
import { enumerationSetServiceMock } from '../../../mocks/enumeration.set.service.mock';
import { MimPreferencesServiceMock } from '../../../mocks/MimPreferencesService.mock';
import { typesServiceMock } from '../../../mocks/types.service.mock';
import { ApplicabilityListService } from '../../../services/http/applicability-list.service';
import { EnumerationSetService } from '../../../services/http/enumeration-set.service';
import { MimPreferencesService } from '../../../services/http/mim-preferences.service';
import { TypesService } from '../../../services/http/types.service';
import { enumsetDialogData } from '../../../types/EnumSetDialogData';
import { EditEnumSetFieldComponent } from '../../edit-enum-set-field/edit-enum-set-field.component';

import { EditEnumSetDialogComponent } from './edit-enum-set-dialog.component';

describe('EditEnumSetDialogComponent', () => {
	let component: EditEnumSetDialogComponent;
	let fixture: ComponentFixture<EditEnumSetDialogComponent>;
	let loader: HarnessLoader;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [EditEnumSetDialogComponent, EditEnumSetFieldMock],
			imports: [
				MatDialogModule,
				MatIconModule,
				MatSelectModule,
				MatInputModule,
				MatFormFieldModule,
				FormsModule,
				MatTableModule,
				NoopAnimationsModule,
			],
			providers: [
				{
					provide: MatDialogRef,
					useValue: {
						close() {
							return of();
						},
					},
				},
				{
					provide: MAT_DIALOG_DATA,
					useValue: of<enumsetDialogData>({
						id: '1234567890',
						isOnEditablePage: true,
					}),
				},
				{
					provide: EnumerationSetService,
					useValue: enumerationSetServiceMock,
				},
				{
					provide: ApplicabilityListService,
					useValue: applicabilityListServiceMock,
				},
				{
					provide: MimPreferencesService,
					useValue: MimPreferencesServiceMock,
				},
				{
					provide: UserDataAccountService,
					useValue: userDataAccountServiceMock,
				},
				{ provide: TypesService, useValue: typesServiceMock },
			],
		}).compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(EditEnumSetDialogComponent);
		component = fixture.componentInstance;
		loader = TestbedHarnessEnvironment.loader(fixture);
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});

	it('should close the dialog', async () => {
		const dialogRefClosure = spyOn(component.dialogRef, 'close').and.stub();
		const button = await loader.getHarness(
			MatButtonHarness.with({ text: 'Cancel' })
		);
		await button.click();
		expect(dialogRefClosure).toHaveBeenCalled();
	});
});
