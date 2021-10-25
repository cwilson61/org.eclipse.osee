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
 import { Component, Input } from "@angular/core";
import { MatTableDataSource } from "@angular/material/table";
import { Observable, of } from "rxjs";
import { structure } from "../../types/structure";

 @Component({
     selector: 'app-structure-table',
     template:'<p>Dummy</p>'
 })
 export class StructureTableComponentMock{
    @Input() messageData: Observable<MatTableDataSource<structure>> = of(new MatTableDataSource<structure>())
     @Input() hasFilter: boolean = false;
     @Input() breadCrumb: string = '';
   }