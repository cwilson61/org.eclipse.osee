/*******************************************************************************
 * Copyright (c) 2013 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.account.admin.internal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.eclipse.osee.account.admin.Account;
import org.eclipse.osee.account.admin.AccountAdmin;
import org.eclipse.osee.account.admin.AccountField;
import org.eclipse.osee.account.admin.AccountPreferences;
import org.eclipse.osee.account.admin.internal.validator.Validator;
import org.eclipse.osee.framework.jdk.core.type.OseeArgumentException;
import org.eclipse.osee.framework.jdk.core.type.ResultSet;
import org.eclipse.osee.framework.jdk.core.type.ResultSets;
import org.eclipse.osee.framework.jdk.core.util.GUID;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

/**
 * Test Case for {@link AccountResolver}
 *
 * @author Roberto E. Escobar
 */
public class AccountResolverTest {

   private static final String TEST_VALUE = "atest";
   private static final String TEST_LOCAID_VALUE = "12334";
   private static final long LOCAID_VALUE = 12334L;

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   // @formatter:off
   @Mock private Validator validator;
   @Mock private AccountAdmin accountAdmin;
   @Mock private ResultSet<Account> accountResult;
   @Mock private Account account;
   @Mock private AccountPreferences prefs;
   // @formatter:on

   private AccountResolver resolver;

   @Before
   public void testSetup() {
      initMocks(this);

      resolver = new AccountResolver(validator, accountAdmin);

      String uuid = GUID.create();
      when(account.getGuid()).thenReturn(uuid);
      when(prefs.getGuid()).thenReturn(uuid);
      when(account.getPreferences()).thenReturn(prefs);

      when(accountResult.getExactlyOne()).thenReturn(account);
   }

   @Test
   public void testResolveAccountWithNull() {
      thrown.expect(OseeArgumentException.class);
      thrown.expectMessage("account unique field value cannot be null");
      resolver.resolveAccount(null);
   }

   @Test
   public void testResolveAccountPreferencesWithNull() {
      thrown.expect(OseeArgumentException.class);
      thrown.expectMessage("account unique field value cannot be null");
      resolver.resolveAccountPreferences(null);
   }

   @Test
   public void testResolveAccountWithEmpty() {
      thrown.expect(OseeArgumentException.class);
      thrown.expectMessage("account unique field value cannot be empty");
      resolver.resolveAccount("");
   }

   @Test
   public void testResolveAccountPreferencesWithEmpty() {
      thrown.expect(OseeArgumentException.class);
      thrown.expectMessage("account unique field value cannot be empty");
      resolver.resolveAccountPreferences("");
   }

   @Test
   public void testResolveAccountAsDisplayName() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.DISPLAY_NAME);
      when(accountAdmin.getAccountByName(TEST_VALUE)).thenReturn(accountResult);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_VALUE);
      assertEquals(accountResult, actual);

      verify(validator).guessFormatType(TEST_VALUE);
      verify(accountAdmin).getAccountByName(TEST_VALUE);
   }

   @Test
   public void testResolveAccountAsEmail() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.EMAIL);
      when(accountAdmin.getAccountByEmail(TEST_VALUE)).thenReturn(accountResult);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_VALUE);
      assertEquals(accountResult, actual);

      verify(validator).guessFormatType(TEST_VALUE);
      verify(accountAdmin).getAccountByEmail(TEST_VALUE);
   }

   @Test
   public void testResolveAccountAsLocalId() {
      when(validator.guessFormatType(TEST_LOCAID_VALUE)).thenReturn(AccountField.LOCAL_ID);
      when(accountAdmin.getAccountById(LOCAID_VALUE)).thenReturn(accountResult);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_LOCAID_VALUE);
      assertEquals(accountResult, actual);

      verify(validator).guessFormatType(TEST_LOCAID_VALUE);
      verify(accountAdmin).getAccountById(LOCAID_VALUE);
   }

   @Test
   public void testResolveAccountAsUnknown() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.UNKNOWN);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_VALUE);
      assertEquals(ResultSets.emptyResultSet(), actual);

      verify(validator).guessFormatType(TEST_VALUE);

      verify(accountAdmin, times(0)).getAccountByEmail(anyString());
      verify(accountAdmin, times(0)).getAccountById(anyLong());
      verify(accountAdmin, times(0)).getAccountByGuid(anyString());
      verify(accountAdmin, times(0)).getAccountByName(anyString());
      verify(accountAdmin, times(0)).getAccountByUserName(anyString());
   }

   @Test
   public void testResolveAccountAsUserName() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.USERNAME);
      when(accountAdmin.getAccountByUserName(TEST_VALUE)).thenReturn(accountResult);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_VALUE);
      assertEquals(accountResult, actual);

      verify(validator).guessFormatType(TEST_VALUE);
      verify(accountAdmin).getAccountByUserName(TEST_VALUE);
   }

   @Test
   public void testResolveAccountAsGuid() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.GUID);
      when(accountAdmin.getAccountByGuid(TEST_VALUE)).thenReturn(accountResult);

      ResultSet<Account> actual = resolver.resolveAccount(TEST_VALUE);
      assertEquals(accountResult, actual);

      verify(validator).guessFormatType(TEST_VALUE);
      verify(accountAdmin).getAccountByGuid(TEST_VALUE);
   }

   @Test
   public void testResolveAccountPrefsUnknown() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.UNKNOWN);

      ResultSet<AccountPreferences> actual = resolver.resolveAccountPreferences(TEST_VALUE);
      assertEquals(ResultSets.emptyResultSet(), actual);

      verify(accountAdmin, times(0)).getAccountByEmail(anyString());
      verify(accountAdmin, times(0)).getAccountById(anyLong());
      verify(accountAdmin, times(0)).getAccountByGuid(anyString());
      verify(accountAdmin, times(0)).getAccountByName(anyString());
      verify(accountAdmin, times(0)).getAccountByUserName(anyString());
   }

   @Test
   public void testResolveAccountPreferences() {
      when(validator.guessFormatType(TEST_VALUE)).thenReturn(AccountField.EMAIL);
      when(accountAdmin.getAccountByEmail(TEST_VALUE)).thenReturn(accountResult);

      ResultSet<AccountPreferences> result = resolver.resolveAccountPreferences(TEST_VALUE);
      AccountPreferences actual = result.getExactlyOne();
      assertEquals(prefs, actual);

      verify(validator).guessFormatType(TEST_VALUE);
      verify(accountAdmin).getAccountByEmail(TEST_VALUE);
   }
}
