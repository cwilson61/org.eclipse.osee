/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/

package org.eclipse.osee.framework.skynet.core.artifact;

import static org.eclipse.osee.framework.db.connection.core.schema.SkynetDatabase.ARTIFACT_VERSION_TABLE;
import static org.eclipse.osee.framework.db.connection.core.schema.SkynetDatabase.ATTRIBUTE_VERSION_TABLE;
import static org.eclipse.osee.framework.db.connection.core.schema.SkynetDatabase.RELATION_LINK_VERSION_TABLE;
import static org.eclipse.osee.framework.db.connection.core.schema.SkynetDatabase.TRANSACTIONS_TABLE;
import static org.eclipse.osee.framework.db.connection.core.schema.SkynetDatabase.TRANSACTION_DETAIL_TABLE;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osee.framework.db.connection.ConnectionHandlerStatement;
import org.eclipse.osee.framework.db.connection.exception.OseeCoreException;
import org.eclipse.osee.framework.db.connection.exception.OseeDataStoreException;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;

/**
 * @author Theron Virgin
 */
public class MergeBranchManager {
   private static ArtifactPersistenceManager artifactManager = ArtifactPersistenceManager.getInstance();

   private static final String GET_ART_IDS_FOR_ART_VER_TABLE =
         "SELECT t1.art_id FROM " + ARTIFACT_VERSION_TABLE + " t1, " + TRANSACTION_DETAIL_TABLE + " t2, " + TRANSACTIONS_TABLE + " t3 where t2.transaction_id = t3.transaction_id and t3.gamma_id = t1.gamma_id and t2.branch_id = ?";
   private static final String GET_ART_IDS_FOR_ATR_VER_TABLE =
         "SELECT t1.art_id FROM " + ATTRIBUTE_VERSION_TABLE + " t1, " + TRANSACTION_DETAIL_TABLE + " t2, " + TRANSACTIONS_TABLE + " t3 where t2.transaction_id = t3.transaction_id and t3.gamma_id = t1.gamma_id and t2.branch_id = ?";
   private static final String GET_ART_IDS_FOR_REL_VER_TABLE =
         "SELECT t1.a_art_id, t1.b_art_id FROM " + RELATION_LINK_VERSION_TABLE + " t1, " + TRANSACTION_DETAIL_TABLE + " t2, " + TRANSACTIONS_TABLE + " t3 where t2.transaction_id = t3.transaction_id and t3.gamma_id = t1.gamma_id and t2.branch_id = ?";

   private static final boolean DEBUG =
         "TRUE".equalsIgnoreCase(Platform.getDebugOption("org.eclipse.osee.framework.skynet.core/debug/Merge"));

   public static void updateMergeBranch(Branch mergeBranch, ArrayList<Integer> expectedArtIds, Branch destBranch, Branch sourceBranch) throws OseeCoreException {
      Collection<Integer> allMergeBranchArtifacts = getAllMergeArtifacts(mergeBranch);
      long time = System.currentTimeMillis();
      Collection<Integer> allMergeBranchArtifactsCopy = new HashSet<Integer>(allMergeBranchArtifacts);
      Collection<Artifact> goodMergeBranchArtifacts = ArtifactQuery.getArtifactsFromBranch(mergeBranch, true);

      if (DEBUG) {
         System.out.println(String.format("        Get artifacts on branch took %s", Lib.getElapseString(time)));
         System.out.println("            Need the following Artifacts on the Merge Branch");
         System.out.print("            ");
         for (Integer integer : expectedArtIds) {
            System.out.print(integer + ", ");
         }
         System.out.print("\n");
      }
      time = System.currentTimeMillis();
      int count = 0;
      //Delete any damaged artifacts (from a source revert) on the merge branch
      for (Artifact artifact : goodMergeBranchArtifacts) {
         allMergeBranchArtifactsCopy.remove(new Integer(artifact.getArtId()));
      }
      if (!allMergeBranchArtifactsCopy.isEmpty()) {
         for (Integer artifact : allMergeBranchArtifactsCopy) {
            artifactManager.purgeArtifactFromBranch(mergeBranch.getBranchId(), artifact.intValue());
            count++;
         }
      }
      if (DEBUG) {
         System.out.println(String.format("          Deleting %d Damaged Artifacts took %s", count,
               Lib.getElapseString(time)));
         time = System.currentTimeMillis();
         count = 0;
      }

      //Delete any artifacts that shouldn't be on the merge branch but are
      for (Integer artid : expectedArtIds) {
         allMergeBranchArtifacts.remove(artid);
      }
      if (!allMergeBranchArtifacts.isEmpty()) {
         for (Integer artifact : allMergeBranchArtifacts) {
            count++;
            artifactManager.purgeArtifactFromBranch(mergeBranch.getBranchId(), artifact.intValue());
         }
      }
      if (DEBUG) {
         System.out.println(String.format("          Deleting %d unused Artifacts took %s", count,
               Lib.getElapseString(time)));
         time = System.currentTimeMillis();
         count = 0;
      }

      //Add any artifacts that should be on the merge branch but aren't
      for (Artifact artifact : goodMergeBranchArtifacts) {
         expectedArtIds.remove(new Integer(artifact.getArtId()));
      }
      if (!expectedArtIds.isEmpty()) {
         BranchCreator.getInstance().addArtifactsToBranch(sourceBranch, destBranch, mergeBranch, expectedArtIds);
      }

      if (DEBUG) {
         System.out.println(String.format("          Adding %d new Artifacts took %s", expectedArtIds.size(),
               Lib.getElapseString(time)));
         time = System.currentTimeMillis();
      }

   }

   private static Collection<Integer> getAllMergeArtifacts(Branch branch) throws OseeDataStoreException {
      Collection<Integer> artSet = new HashSet<Integer>();
      long time = System.currentTimeMillis();

      ConnectionHandlerStatement chStmt1 = new ConnectionHandlerStatement();
      ConnectionHandlerStatement chStmt2 = new ConnectionHandlerStatement();
      ConnectionHandlerStatement chStmt3 = new ConnectionHandlerStatement();
      try {
         chStmt1.runPreparedQuery(GET_ART_IDS_FOR_ART_VER_TABLE, branch.getBranchId());
         while (chStmt1.next()) {
            artSet.add(chStmt1.getInt("art_id"));
         }
         if (DEBUG) {
            System.out.println(String.format(
                  "          Getting Artifacts that are on the Merge Branch Completed in %s", Lib.getElapseString(time)));
            time = System.currentTimeMillis();
         }

         chStmt2.runPreparedQuery(GET_ART_IDS_FOR_ATR_VER_TABLE, branch.getBranchId());
         while (chStmt2.next()) {
            artSet.add(chStmt2.getInt("art_id"));
         }
         if (DEBUG) {
            System.out.println(String.format(
                  "          Getting Attributes that are on the Merge Branch Completed in %s",
                  Lib.getElapseString(time)));
            time = System.currentTimeMillis();
         }

         chStmt3.runPreparedQuery(GET_ART_IDS_FOR_REL_VER_TABLE, branch.getBranchId());
         while (chStmt3.next()) {
            artSet.add(chStmt3.getInt("a_art_id"));
            artSet.add(chStmt3.getInt("b_art_id"));
         }
      } finally {
         chStmt1.close();
         chStmt2.close();
         chStmt3.close();
      }
      if (DEBUG) {
         System.out.println(String.format("          Getting Relations that are on the Merge Branch Completed in %s",
               Lib.getElapseString(time)));
         System.out.println("            Found the following Artifacts on the Merge Branch");
         System.out.print("            ");
         for (Integer integer : artSet) {
            System.out.print(integer + ", ");
         }
         System.out.print("\n");
      }
      return artSet;
   }

}
