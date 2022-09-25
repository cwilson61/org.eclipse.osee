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
Cypress.Commands.add('toggleBaseAddMenu', () => {
  return cy.get('#addButton').click();
});
Cypress.Commands.add('openNestedAddMenu', () => {
  cy.toggleBaseAddMenu().get(`[data-cy="nested-add-button"]`).click();
});