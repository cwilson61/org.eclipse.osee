/*********************************************************************
 * Copyright (c) 2010 Boeing
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

package org.eclipse.osee.ats.ide.integration.tests.ats.column;

import java.util.Date;
import org.eclipse.osee.ats.api.demo.DemoWorkType;
import org.eclipse.osee.ats.core.column.CreatedDateColumn;
import org.eclipse.osee.ats.ide.integration.tests.util.DemoTestUtil;
import org.eclipse.osee.ats.ide.workflow.teamwf.TeamWorkFlowArtifact;
import org.eclipse.osee.framework.jdk.core.util.DateUtil;
import org.eclipse.osee.framework.logging.SevereLoggingMonitor;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.support.test.util.TestUtil;
import org.junit.Assert;

/**
 * @tests CreatedDateColumn
 * @author Donald G. Dunne
 */
public class CreatedDateColumnTest {

   @org.junit.Test
   public void testGetColumnText() throws Exception {
      SevereLoggingMonitor loggingMonitor = TestUtil.severeLoggingStart();

      TeamWorkFlowArtifact codeArt =
         (TeamWorkFlowArtifact) DemoTestUtil.getUncommittedActionWorkflow(DemoWorkType.Code);
      Assert.assertNotNull(CreatedDateColumn.getDateStr(codeArt));
      Date date = CreatedDateColumn.getDate(codeArt);
      Assert.assertNotNull(date);
      Assert.assertEquals(DateUtil.getMMDDYYHHMM(date), CreatedDateColumn.getDateStr(codeArt));

      Artifact actionArt = (Artifact) codeArt.getParentAction().getStoreObject();
      Assert.assertEquals(DateUtil.getMMDDYYHHMM(date), CreatedDateColumn.getDateStr(actionArt));

      TestUtil.severeLoggingEnd(loggingMonitor);
   }

}
