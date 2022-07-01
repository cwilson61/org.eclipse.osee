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
import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/router/search.service';

@Component({
  selector: 'osee-typesearch-element-table-search',
  templateUrl: './element-table-search.component.html',
  styleUrls: ['./element-table-search.component.sass']
})
export class ElementTableSearchComponent implements OnInit {
  searchTerm: string = "";
  constructor (private searchService: SearchService) {
    this.searchService.searchTerm.subscribe((val) => {
      this.searchTerm = val;
    })
   }

  ngOnInit(): void {
  }

  applyFilter(event: Event) {
    this.searchService.search=(event.target as HTMLInputElement).value;
  }

}
