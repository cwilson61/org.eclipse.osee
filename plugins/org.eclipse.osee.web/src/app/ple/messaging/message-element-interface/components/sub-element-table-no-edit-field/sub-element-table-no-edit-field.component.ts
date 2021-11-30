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
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { applic } from '../../../../../types/applicability/applic';
import { element } from '../../types/element';

@Component({
  selector: 'app-sub-element-table-no-edit-field',
  templateUrl: './sub-element-table-no-edit-field.component.html',
  styleUrls: ['./sub-element-table-no-edit-field.component.sass']
})
export class SubElementTableNoEditFieldComponent implements OnInit {

  @Input() filter: string = "";
  @Input() element: element= {
    id: '',
    name: '',
    description: '',
    notes: '',
    interfaceElementIndexEnd: 0,
    interfaceElementIndexStart: 0,
    applicability: {
      id: '1',
      name:'Base'
    },
    interfaceElementAlterable: false
  };
  @Input() header: string = "";
  @Input() width: string = "";

  @Output() platformNavigation = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }
  navigateTo(location: string) {
    this.platformNavigation.emit(location);
  }

}