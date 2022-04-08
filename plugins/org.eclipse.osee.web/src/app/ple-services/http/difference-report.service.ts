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
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DifferenceReport } from 'src/app/ple/messaging/shared/types/DifferenceReport';
import { changeInstance } from 'src/app/types/change-report/change-report';
import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DifferenceReportService {

  constructor (private http: HttpClient) { }
  
  getDifferences(fromBranchId: string | number, toBranchId:string|number) {
    return this.http.get<changeInstance[]>(apiURL + '/orcs/branches/' + toBranchId + '/diff/' + fromBranchId);
  }

  getDifferenceReport(fromBranchId: string | number, toBranchId: string | number) {
    return this.http.get<DifferenceReport>(apiURL + '/mim/branch/' + toBranchId + '/diff/' + fromBranchId);
  }
}
