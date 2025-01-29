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

package org.eclipse.osee.ats.ide.integration.tests.skynet.core;

import org.eclipse.osee.client.test.framework.NotProductionDataStoreRule;
import org.eclipse.osee.framework.skynet.core.event.OseeEventManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * event loopback will test that remote messages get processed and treated like local messages by turning off local and
 * enabling remote to be loop-ed back without another client. same tests as base-class should still pass
 *
 * @author Torin Grenda, David Miller
 */
public class ArtifactTopicEventLoopbackTest extends ArtifactTopicEventTest {

   @Rule
   public NotProductionDataStoreRule notProduction = new NotProductionDataStoreRule();

   private boolean remoteEventLoopback;

   @Before
   public void initialize() {
      remoteEventLoopback = OseeEventManager.getPreferences().isEnableRemoteEventLoopback();
      OseeEventManager.getPreferences().setEnableRemoteEventLoopback(true);
   }

   @After
   public void tearDown() {
      OseeEventManager.getPreferences().setEnableRemoteEventLoopback(remoteEventLoopback);
   }

   @Override
   protected boolean isRemoteTest() {
      return true;
   }

}
