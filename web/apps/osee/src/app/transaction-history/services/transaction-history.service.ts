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
import { Injectable, inject } from '@angular/core';
import { apiURL } from '@osee/environments';
import { transactionInfo } from '@osee/transaction-history/types';

@Injectable({
	providedIn: 'root',
})
export class TransactionHistoryService {
	private http = inject(HttpClient);

	getTransaction(id: string | number) {
		return this.http.get<transactionInfo>(apiURL + '/orcs/txs/' + id);
	}

	getLatestBranchTransaction(branchId: string) {
		return this.http.get<transactionInfo>(
			apiURL + '/orcs/branches/' + branchId + '/txs/latest'
		);
	}
}