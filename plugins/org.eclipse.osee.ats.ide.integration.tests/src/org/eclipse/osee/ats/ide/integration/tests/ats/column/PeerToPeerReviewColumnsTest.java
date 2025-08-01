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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.eclipse.osee.ats.api.review.IAtsPeerReviewDefectManager;
import org.eclipse.osee.ats.api.review.IAtsPeerReviewRoleManager;
import org.eclipse.osee.ats.api.review.IAtsPeerToPeerReview;
import org.eclipse.osee.ats.api.review.ReviewDefectItem;
import org.eclipse.osee.ats.api.review.ReviewDefectItem.Disposition;
import org.eclipse.osee.ats.api.review.ReviewDefectItem.InjectionActivity;
import org.eclipse.osee.ats.api.review.ReviewDefectItem.Severity;
import org.eclipse.osee.ats.api.review.ReviewRole;
import org.eclipse.osee.ats.api.review.UserRole;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.ide.column.ReviewAuthorColumnUI;
import org.eclipse.osee.ats.ide.column.ReviewModeratorColumnUI;
import org.eclipse.osee.ats.ide.column.ReviewNumIssuesColumnUI;
import org.eclipse.osee.ats.ide.column.ReviewNumMajorDefectsColumnUI;
import org.eclipse.osee.ats.ide.column.ReviewNumMinorDefectsColumnUI;
import org.eclipse.osee.ats.ide.column.ReviewReviewerColumnUI;
import org.eclipse.osee.ats.ide.integration.tests.AtsApiService;
import org.eclipse.osee.ats.ide.integration.tests.ats.workflow.AtsTestUtil;
import org.eclipse.osee.ats.ide.integration.tests.util.DemoTestUtil;
import org.eclipse.osee.ats.ide.workflow.review.PeerToPeerReviewArtifact;
import org.eclipse.osee.ats.ide.workflow.teamwf.TeamWorkFlowArtifact;
import org.eclipse.osee.framework.core.enums.DemoUsers;
import org.eclipse.osee.framework.logging.SevereLoggingMonitor;
import org.eclipse.osee.framework.skynet.core.UserManager;
import org.eclipse.osee.support.test.util.TestUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @tests CancelledDateColumn
 * @author Donald G. Dunne
 */
public class PeerToPeerReviewColumnsTest {

   @AfterClass
   @BeforeClass
   public static void cleanup() throws Exception {
      AtsTestUtil.cleanupSimpleTest(PeerToPeerReviewColumnsTest.class.getSimpleName());
   }

   @org.junit.Test
   public void testGetColumnText() throws Exception {
      SevereLoggingMonitor loggingMonitor = TestUtil.severeLoggingStart();

      TeamWorkFlowArtifact teamArt = (TeamWorkFlowArtifact) DemoTestUtil.createSimpleAction(
         PeerToPeerReviewColumnsTest.class.getSimpleName()).getStoreObject();

      IAtsChangeSet changes = AtsApiService.get().createChangeSet(PeerToPeerReviewColumnsTest.class.getSimpleName());
      PeerToPeerReviewArtifact peerArt =
         (PeerToPeerReviewArtifact) AtsApiService.get().getReviewService().createNewPeerToPeerReview(teamArt,
            getClass().getSimpleName(), teamArt.getCurrentStateName(), changes);
      changes.add(peerArt);
      changes.execute();

      Assert.assertEquals("0", ReviewNumIssuesColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("0", ReviewNumMajorDefectsColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("0", ReviewNumMinorDefectsColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("", ReviewAuthorColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("", ReviewModeratorColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("", ReviewReviewerColumnUI.getInstance().getColumnText(peerArt, null, 0));

      changes = AtsApiService.get().createChangeSet(PeerToPeerReviewColumnsTest.class.getSimpleName());
      ReviewDefectItem item = new ReviewDefectItem(UserManager.getUser(), Severity.Issue, Disposition.None,
         InjectionActivity.Code, "description", "resolution", "location", new Date(), "notes");
      IAtsPeerReviewDefectManager defectManager = peerArt.getDefectManager();
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Issue, Disposition.None, InjectionActivity.Code,
         "description 2", "resolution", "location", new Date(), "notes 2");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Issue, Disposition.None, InjectionActivity.Code,
         "description 3", "resolution", "location", new Date(), "notes 3");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Issue, Disposition.None, InjectionActivity.Code,
         "description 34", "resolution", "location", new Date(), "notes 34");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Major, Disposition.None, InjectionActivity.Code,
         "description 4", "resolution", "location", new Date(), "notes 4");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Minor, Disposition.None, InjectionActivity.Code,
         "description 5", "resolution", "location", new Date(), "notes 5");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Minor, Disposition.None, InjectionActivity.Code,
         "description 6", "resolution", "location", new Date(), "notes 6");
      defectManager.addOrUpdateDefectItem(item);
      item = new ReviewDefectItem(UserManager.getUser(), Severity.Minor, Disposition.None, InjectionActivity.Code,
         "description 6", "resolution", "location", new Date(), "notes 6");
      defectManager.addOrUpdateDefectItem(item);
      defectManager.saveToArtifact(peerArt, changes);

      UserRole role = new UserRole(ReviewRole.Author, DemoUsers.Alex_Kay);
      IAtsPeerReviewRoleManager roleMgr = ((IAtsPeerToPeerReview) peerArt).getRoleManager();
      roleMgr.addOrUpdateUserRole(role, changes);

      role = new UserRole(ReviewRole.Moderator, DemoUsers.Jason_Michael);
      roleMgr.addOrUpdateUserRole(role, changes);

      role = new UserRole(ReviewRole.Reviewer, DemoUsers.Joe_Smith);
      roleMgr.addOrUpdateUserRole(role, changes);
      role = new UserRole(ReviewRole.Reviewer, DemoUsers.Kay_Jones);
      roleMgr.addOrUpdateUserRole(role, changes);
      roleMgr.saveToArtifact(changes);

      changes.add(peerArt);
      changes.execute();

      Assert.assertEquals("4", ReviewNumIssuesColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("1", ReviewNumMajorDefectsColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals("3", ReviewNumMinorDefectsColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals(DemoUsers.Alex_Kay.getName(),
         ReviewAuthorColumnUI.getInstance().getColumnText(peerArt, null, 0));
      Assert.assertEquals(DemoUsers.Jason_Michael.getName(),
         ReviewModeratorColumnUI.getInstance().getColumnText(peerArt, null, 0));
      List<String> results = Arrays.asList(DemoUsers.Kay_Jones_And_Joe_Smith, DemoUsers.Joe_Smith_And_Kay_Jones);
      Assert.assertTrue(results.contains(ReviewReviewerColumnUI.getInstance().getColumnText(peerArt, null, 0)));

      TestUtil.severeLoggingEnd(loggingMonitor);
   }
}
