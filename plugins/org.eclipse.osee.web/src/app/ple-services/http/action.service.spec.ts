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
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { TestScheduler } from 'rxjs/testing';
import { apiURL } from 'src/environments/environment';
import { testARB, testBranchActions, testDataResponse, testDataTransitionResponse, testDataUser, testDataVersion, testNewActionData, testnewActionResponse, testUsers, testWorkFlow } from '../../ple/plconfig/testing/mockTypes';
import { NameValuePair } from '../../ple/plconfig/types/base-types/NameValuePair';
import { action, newActionResponse, transitionAction } from '../../ple/plconfig/types/pl-config-actions';
import { PlConfigBranchListingBranch } from '../../ple/plconfig/types/pl-config-branch';
import { response } from '../../types/responses';
import { ActionService } from './action.service';

describe('PlConfigActionService', () => {
  let service: ActionService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let scheduler: TestScheduler;
  
  
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ActionService);
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  beforeEach(() => scheduler = new TestScheduler((actual, expected) => {
    expect(actual).toEqual(expected);
  }));

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get users', () => {
    
    service.users.subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/user?active=Active');
    expect(req.request.method).toEqual('GET');
    req.flush(testUsers);
    httpTestingController.verify();
  })

  it('should get ARB actionable item', () => {
    
    service.getActionableItems('ARB').subscribe();
    const req = httpTestingController.expectOne(apiURL + "/ats/ai/worktype/ARB");
    expect(req.request.method).toEqual('GET');
    req.flush(testARB);
    httpTestingController.verify();
  })

  it('should get workflow', () => {

    service.getWorkFlow(0).subscribe();
    const req = httpTestingController.expectOne(apiURL + "/ats/teamwf/" + 0);
    expect(req.request.method).toEqual('GET');
    req.flush(testWorkFlow);
    httpTestingController.verify();
  })

  it('should get action', () => {
    service.getAction('0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/action/' + 0);
    expect(req.request.method).toEqual('GET');
    req.flush(testBranchActions);
    httpTestingController.verify();
  })

  it('should get validateTransitionAction response', () => {
    
    let transitionData = new transitionAction('Review', 'Transition To Review', testBranchActions, testDataUser);
    service.validateTransitionAction(transitionData).subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/action/transitionValidate');
    expect(req.request.method).toEqual('POST');
    req.flush(testDataTransitionResponse);
    httpTestingController.verify();
  })

  it('should transitionAction', () => {
    let transitionData = new transitionAction('Review', 'Transition To Review', testBranchActions, testDataUser);
    service.transitionAction(transitionData).subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/action/transition');
    expect(req.request.method).toEqual('POST');
    req.flush(testDataTransitionResponse);
    httpTestingController.verify();
  })

  it('should get versions', () => {
    service.getVersions('0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/teamwf/' + 0 + '/version?sort=true');
    expect(req.request.method).toEqual('GET');
    req.flush(testDataVersion);
    httpTestingController.verify();
  })

  it('should create branch', () => {

    service.createBranch(testNewActionData).subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/action/branch');
    expect(req.request.method).toEqual('POST');
    req.flush(testnewActionResponse);
    httpTestingController.verify();
  })

  it('should commit branch', () => {
    service.commitBranch('0', '0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/action/branch/commit?teamWfId='+0+'&branchId='+0);
    expect(req.request.method).toEqual('PUT');
    req.flush(testDataResponse);
    httpTestingController.verify();
  })

  it('should approve branch', () => {
    service.approveBranch('0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/ple/action/' + 0 + '/approval');
    expect(req.request.method).toEqual('POST');
    req.flush(testDataResponse);
    httpTestingController.verify();
  })

  it('should get team leads', () => {
    let testData: NameValuePair[] = [{
      id: '0',
      name: 'name'
    }]
    service.getTeamLeads('0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/config/teamdef/'+0+'/leads');
    expect(req.request.method).toEqual('GET');
    req.flush(testData);
    httpTestingController.verify();
  })

  it('should get branch approval', () => {
    service.getBranchApproved('0').subscribe();
    const req = httpTestingController.expectOne(apiURL + '/ats/ple/action/' + 0 + '/approval');
    expect(req.request.method).toEqual('GET');
    req.flush(testDataResponse);
    httpTestingController.verify();
  })
});