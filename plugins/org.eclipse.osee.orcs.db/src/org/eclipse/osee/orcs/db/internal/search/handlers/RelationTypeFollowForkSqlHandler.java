/*********************************************************************
 * Copyright (c) 2023 Boeing
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

package org.eclipse.osee.orcs.db.internal.search.handlers;

import java.util.List;
import org.eclipse.osee.framework.core.data.RelationTypeSide;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.jdbc.ObjectType;
import org.eclipse.osee.orcs.OseeDb;
import org.eclipse.osee.orcs.core.ds.OptionsUtil;
import org.eclipse.osee.orcs.core.ds.criteria.CriteriaRelationTypeFollow;
import org.eclipse.osee.orcs.db.internal.sql.AbstractSqlWriter;
import org.eclipse.osee.orcs.db.internal.sql.SqlAliasManager;
import org.eclipse.osee.orcs.db.internal.sql.SqlHandler;

/**
 * @author Audrey Denk
 */
public class RelationTypeFollowForkSqlHandler extends SqlHandler<CriteriaRelationTypeFollow> {

   private CriteriaRelationTypeFollow criteria;

   private String artAlias0;
   private String txsAlias0;

   private String relAlias1;
   private String txsAlias1;

   private String artAlias2;
   private String txsAlias2;

   @Override
   public void setData(CriteriaRelationTypeFollow criteria) {
      this.criteria = criteria;
   }

   @Override
   public void addTables(AbstractSqlWriter writer) {
      List<String> artAliases = writer.getAliases(OseeDb.ARTIFACT_TABLE);

      if (artAliases.isEmpty()) {
         artAlias0 = writer.addTable(OseeDb.ARTIFACT_TABLE);
         txsAlias0 = writer.addTable(OseeDb.TXS_TABLE, ObjectType.ARTIFACT);
      } else {
         artAlias0 = artAliases.iterator().next();
      }
      relAlias1 = writer.addTable(criteria.getType());
      txsAlias1 = writer.addTable(OseeDb.TXS_TABLE, ObjectType.RELATION);

      String branchAlias = writer.getFirstAlias(OseeDb.BRANCH_TABLE);

      SqlAliasManager aliasManager = writer.getAliasManager();
      aliasManager.nextLevel();

      artAlias2 = writer.addTable(OseeDb.ARTIFACT_TABLE);
      txsAlias2 = writer.addTable(OseeDb.TXS_TABLE, ObjectType.ARTIFACT);

      if (Strings.isValid(branchAlias)) {
         aliasManager.putAlias(OseeDb.BRANCH_TABLE, ObjectType.BRANCH, branchAlias);
      }
   }

   @Override
   public void addPredicates(AbstractSqlWriter writer) {
      boolean includeDeletedRelations = OptionsUtil.areDeletedRelationsIncluded(writer.getOptions());
      RelationTypeSide typeSide = criteria.getType();

      if (txsAlias0 != null && artAlias0 != null) {

         writer.writeEqualsAnd(artAlias0, "gamma_id", txsAlias0, "gamma_id");
         writer.writeTxBranchFilter(txsAlias0, includeDeletedRelations);
         writer.writeAnd();
      }

      writer.write(relAlias1);
      writer.write(".rel_link_type_id = ?");
      writer.addParameter(typeSide.getGuid());

      ////////////////////// Source - Side   /////////////
      String aOrbArtId = typeSide.getSide().isSideA() ? "b_art_id" : "a_art_id";
      writer.writeAnd();
      writer.writeEqualsAnd(relAlias1, aOrbArtId, artAlias0, "art_id");
      writer.writeEqualsAnd(relAlias1, "gamma_id", txsAlias1, "gamma_id");
      writer.writeTxBranchFilter(txsAlias1, includeDeletedRelations);

      ////////////////////// Destination - Side   /////////////
      String oppositeAOrBartId = typeSide.getSide().isSideA() ? "a_art_id" : "b_art_id";
      writer.writeAnd();
      writer.writeEqualsAnd(relAlias1, oppositeAOrBartId, artAlias2, "art_id");
      writer.writeEqualsAnd(artAlias2, "gamma_id", txsAlias2, "gamma_id");

      writer.writeTxBranchFilter(txsAlias2, includeDeletedRelations);
   }

   @Override
   public int getPriority() {
      return SqlHandlerPriority.FOLLOW_RELATION_TYPES.ordinal();
   }

}