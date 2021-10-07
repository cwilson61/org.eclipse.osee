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
import { Observable, of } from 'rxjs';
import { relation, transaction } from '../../../../transactions/transaction';
import { TransactionBuilderService } from '../../../../transactions/transaction-builder.service';
import { apiURL } from 'src/environments/environment';
import { OSEEWriteApiResponse } from '../../shared/types/ApiWriteResponse';
import { TypesApiResponse } from '../types/ApiResponse';
import { logicalType, logicalTypeFormDetail } from '../types/logicaltype';
import { PlatformType } from '../types/platformType';
import { ARTIFACTTYPEID } from '../../shared/constants/ArtifactTypeId.enum';

@Injectable({
  providedIn: 'root'
})
export class TypesService {
  
  constructor(private http: HttpClient, private builder: TransactionBuilderService) { }

  /**
   * Gets a list of Platform Types based on a filter condition using the platform types filter GET API
   * @param filter @type {string} filter conditions for finding the correct platform types
   * @param branchId @type {string} branch to fetch from
   * @returns @type {Observable<PlatformType[]>} Observable of array of platform types matching filter conditions (see @type {PlatformType} and @type {Observable})
   */
  getFilteredTypes(filter: string, branchId: string): Observable<PlatformType[]> {
    return this.http.get<PlatformType[]>(apiURL + "/mim/branch/" + branchId + "/types/filter/" + filter);
  }

  createPlatformType(branchId:string,type:PlatformType|Partial<PlatformType>,relations:relation[]) {
    return of<transaction>(this.builder.createArtifact(type, ARTIFACTTYPEID.PLATFORMTYPE, relations, undefined, branchId, "Create Platform Type"));
  }

  changePlatformType(branchId: string, type: Partial<PlatformType>) {
    return of<transaction>(this.builder.modifyArtifact(type,undefined,branchId,"Change platform type attributes"));
  }
  performMutation(body: transaction,branchId:string) {
    return this.http.post<OSEEWriteApiResponse>(apiURL + "/orcs/txs",body)
  }

  get logicalTypes() {
    return this.http.get<logicalType[]>(apiURL + "/mim/logicalType");
  }
  getLogicalTypeFormDetail(id:string) {
    return this.http.get<logicalTypeFormDetail>(apiURL + "/mim/logicalType/" + id);
  }
}