/*******************************************************************************
 * Copyright (c) 2012 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.db.internal.change;

import java.util.HashMap;
import org.eclipse.osee.framework.core.data.ApplicabilityId;
import org.eclipse.osee.framework.core.enums.ModificationType;
import org.eclipse.osee.framework.core.model.change.ChangeItem;
import org.eclipse.osee.framework.core.model.change.ChangeItemUtil;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.type.Pair;
import org.eclipse.osee.jdbc.JdbcStatement;
import org.eclipse.osee.orcs.db.internal.change.ChangeItemLoader.ChangeItemFactory;

public final class AttributeChangeItemFactory implements ChangeItemFactory {
   private static final String SELECT_ATTRIBUTES_BY_GAMMAS =
      "select art_id, attr_id, value, attr_type_id, txj.gamma_id from osee_attribute id, osee_join_transaction txj where id.gamma_id = txj.gamma_id and txj.query_id = ?";

   private final HashMap<Long, Pair<ModificationType, ApplicabilityId>> changeByGammaId;

   public AttributeChangeItemFactory(HashMap<Long, Pair<ModificationType, ApplicabilityId>> changeByGammaId) {
      super();
      this.changeByGammaId = changeByGammaId;
   }

   @Override
   public String getLoadByGammaQuery() {
      return SELECT_ATTRIBUTES_BY_GAMMAS;
   }

   @Override
   public ChangeItem createItem(JdbcStatement chStmt) throws OseeCoreException {
      int attrId = chStmt.getInt("attr_id");
      long attrTypeId = chStmt.getLong("attr_type_id");
      int artId = chStmt.getInt("art_id");

      long gammaId = chStmt.getLong("gamma_id");
      ModificationType modType = changeByGammaId.get(gammaId).getFirst();
      ApplicabilityId appId = changeByGammaId.get(gammaId).getSecond();

      String value = chStmt.getString("value");

      return ChangeItemUtil.newAttributeChange(attrId, attrTypeId, artId, gammaId, modType, value, appId);
   }

   @Override
   public String getItemIdColumnName() {
      return "attr_id";
   }

   @Override
   public String getItemTableName() {
      return "osee_attribute";
   }

   @Override
   public String getItemValueColumnName() {
      return "value";
   }
}