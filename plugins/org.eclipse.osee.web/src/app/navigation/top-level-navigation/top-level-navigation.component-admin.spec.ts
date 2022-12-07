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
import { userDataAccountAdminServiceMock } from '../../userdata/services/user-data-account.service.mock';
import { tests } from './top-level-navigation.component.spec';

describe('TopLevelNavigationComponent', () => {
	tests(userDataAccountAdminServiceMock, true);
});
