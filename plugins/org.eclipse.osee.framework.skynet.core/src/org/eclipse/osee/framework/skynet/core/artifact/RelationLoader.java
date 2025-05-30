/*********************************************************************
 * Copyright (c) 2004, 2007 Boeing
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

package org.eclipse.osee.framework.skynet.core.artifact;

import static org.eclipse.osee.framework.core.enums.LoadLevel.ARTIFACT_AND_ATTRIBUTE_DATA;
import static org.eclipse.osee.framework.core.enums.LoadLevel.ARTIFACT_DATA;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.osee.framework.core.OrcsTokenService;
import org.eclipse.osee.framework.core.data.ApplicabilityId;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.GammaId;
import org.eclipse.osee.framework.core.data.RelationId;
import org.eclipse.osee.framework.core.data.RelationTypeToken;
import org.eclipse.osee.framework.core.data.TransactionToken;
import org.eclipse.osee.framework.core.enums.LoadLevel;
import org.eclipse.osee.framework.core.enums.ModificationType;
import org.eclipse.osee.framework.core.model.TransactionRecord;
import org.eclipse.osee.framework.core.sql.OseeSql;
import org.eclipse.osee.framework.skynet.core.internal.ServiceUtil;
import org.eclipse.osee.framework.skynet.core.relation.RelationManager;
import org.eclipse.osee.framework.skynet.core.transaction.TransactionManager;
import org.eclipse.osee.framework.skynet.core.utility.ConnectionHandler;
import org.eclipse.osee.jdbc.JdbcStatement;

/**
 * @author Ryan Schmitt
 */
class RelationLoader {

   public static void loadRelationData(Long joinQueryId, Collection<Artifact> artifacts, boolean historical,
      LoadLevel loadLevel, OrcsTokenService tokenservice) {
      if (loadLevel == ARTIFACT_DATA || loadLevel == ARTIFACT_AND_ATTRIBUTE_DATA) {
         return;
      }

      if (historical) {
         return; // TODO: someday we might have a use for historical relations, but not now
      }

      Map<ArtifactToken, TransactionToken> artToTx = new HashMap<>();

      JdbcStatement chStmt = ConnectionHandler.getStatement();
      try {
         String sqlQuery = ServiceUtil.getSql(OseeSql.LOAD_RELATIONS);
         chStmt.runPreparedQuery(artifacts.size() * 8, sqlQuery, joinQueryId);
         while (chStmt.next()) {
            RelationId relationId = RelationId.valueOf(chStmt.getLong("rel_link_id"));
            BranchId branch = BranchId.valueOf(chStmt.getLong("branch_id"));
            ArtifactToken aArtifactId = ArtifactToken.valueOf(chStmt.getLong("a_art_id"), branch);
            ArtifactToken bArtifactId = ArtifactToken.valueOf(chStmt.getLong("b_art_id"), branch);
            RelationTypeToken relationType = tokenservice.getRelationType(chStmt.getLong("rel_link_type_id"));

            TransactionToken txTok = TransactionToken.valueOf(chStmt.getLong("transaction_id"), branch);
            artToTx.put(aArtifactId, txTok);
            artToTx.put(bArtifactId, txTok);

            GammaId gammaId = GammaId.valueOf(chStmt.getLong("gamma_id"));
            String rationale = chStmt.getString("rationale");
            ModificationType modificationType = ModificationType.valueOf(chStmt.getInt("mod_type"));
            ApplicabilityId applicabilityId = ApplicabilityId.valueOf(chStmt.getLong("app_id"));

            RelationManager.getOrCreate(aArtifactId, bArtifactId, relationType, relationId, gammaId, rationale,
               modificationType, applicabilityId, 0, ArtifactId.SENTINEL);
         }
      } finally {
         chStmt.close();
      }
      try {
         String sqlQuery = ServiceUtil.getSql(OseeSql.LOAD_RELATIONS2);
         chStmt.runPreparedQuery(artifacts.size() * 8, sqlQuery, joinQueryId);
         while (chStmt.next()) {
            BranchId branch = BranchId.valueOf(chStmt.getLong("branch_id"));
            ArtifactToken aArtifactId = ArtifactToken.valueOf(chStmt.getLong("a_art_id"), branch);
            ArtifactToken bArtifactId = ArtifactToken.valueOf(chStmt.getLong("b_art_id"), branch);
            RelationTypeToken relationType = tokenservice.getRelationType(chStmt.getLong("rel_type"));

            TransactionToken txTok = TransactionToken.valueOf(chStmt.getLong("transaction_id"), branch);
            artToTx.put(aArtifactId, txTok);
            artToTx.put(bArtifactId, txTok);

            GammaId gammaId = GammaId.valueOf(chStmt.getLong("gamma_id"));
            int relOrder = chStmt.getInt("rel_order");
            ArtifactId relArtId = ArtifactId.valueOf(chStmt.getLong("rel_art_id"));

            ModificationType modificationType = ModificationType.valueOf(chStmt.getInt("mod_type"));
            ApplicabilityId applicabilityId = ApplicabilityId.valueOf(chStmt.getLong("app_id"));

            RelationManager.getOrCreate(aArtifactId, bArtifactId, relationType, RelationId.valueOf(0L), gammaId, "",
               modificationType, applicabilityId, relOrder, relArtId);
         }
      } finally {
         chStmt.close();
      }

      for (Artifact artifact : artifacts) {
         setTransactionRecord(artToTx, artifact);
         artifact.setLinksLoaded(true);
      }
   }

   private static void setTransactionRecord(Map<ArtifactToken, TransactionToken> artToTx, Artifact artifact) {
      TransactionToken txTok = artToTx.get(artifact);
      if (txTok != null && txTok.isValid()) {
         TransactionRecord txRec = TransactionManager.getTransactionRecord(txTok);
         if (txRec != null && txRec.isValid()) {
            if (artifact.getTransaction().getId() < txRec.getId()) {
               artifact.setTransaction(txRec);
            }
         }
      }
   }
}
