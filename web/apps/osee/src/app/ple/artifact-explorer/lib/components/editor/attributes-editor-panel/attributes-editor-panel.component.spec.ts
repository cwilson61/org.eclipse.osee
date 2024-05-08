/*********************************************************************
 * Copyright (c) 2023 Boeing
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
import { AttributesEditorPanelComponent } from './attributes-editor-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { tab } from '../../../types/artifact-explorer.data';

describe('AttributesEditorPanelComponent', () => {
	let component: AttributesEditorPanelComponent;
	let fixture: ComponentFixture<AttributesEditorPanelComponent>;

	beforeEach(() => {
		TestBed.configureTestingModule({
			imports: [
				AttributesEditorPanelComponent,
				HttpClientTestingModule,
				BrowserAnimationsModule,
			],
		});

		// tab input
		const tabMock: tab = {
			tabType: 'Artifact',
			tabTitle: '',
			artifact: {
				name: 'Mock Artifact',
				id: '123',
				typeId: '456',
				typeName: 'Mock Type',
				icon: {
					icon: '',
					color: '',
					lightShade: '',
					darkShade: '',
					variant: '',
				},
				attributes: [
					{
						name: 'Attribute 1',
						value: 'Value 1',
						typeId: '789',
						id: '1',
						storeType: 'String',
						multiplicityId: '2',
					},
				],
				editable: true,
			},
			branchId: '789',
			viewId: '0',
		};

		fixture = TestBed.createComponent(AttributesEditorPanelComponent);
		component = fixture.componentInstance;
		component.tab = tabMock;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});