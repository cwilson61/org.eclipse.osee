/*******************************************************************************
 * Copyright (c) 2018 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.orcs.core.internal.applicability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.osee.framework.core.applicability.ApplicabilityBranchConfig;
import org.eclipse.osee.framework.core.applicability.FeatureDefinition;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.Branch;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.BranchViewToken;
import org.eclipse.osee.framework.core.data.UserId;
import org.eclipse.osee.framework.core.data.VariantDefinition;
import org.eclipse.osee.framework.core.enums.BranchState;
import org.eclipse.osee.framework.core.enums.BranchType;
import org.eclipse.osee.framework.core.enums.CoreArtifactTokens;
import org.eclipse.osee.framework.core.enums.CoreArtifactTypes;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.CoreTupleTypes;
import org.eclipse.osee.framework.core.enums.SystemUser;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.util.AHTML;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.NamedComparator;
import org.eclipse.osee.framework.jdk.core.util.SortOrder;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.OrcsApplicability;
import org.eclipse.osee.orcs.data.ArtifactReadable;
import org.eclipse.osee.orcs.transaction.TransactionBuilder;

/**
 * @author Donald G. Dunne
 */
public class OrcsApplicabilityOps implements OrcsApplicability {

   private final OrcsApi orcsApi;
   private ArtifactToken plFolder = ArtifactToken.getSentinal();
   private ArtifactToken featureFolder = ArtifactToken.getSentinal();
   private ArtifactToken variantsFolder = ArtifactToken.getSentinal();

   public OrcsApplicabilityOps(OrcsApi orcsApi) {
      this.orcsApi = orcsApi;
   }

   /**
    * @return config as defined in Feature artifacts
    */
   @Override
   public ApplicabilityBranchConfig getConfig(BranchId branchId) {
      ApplicabilityBranchConfig config = new ApplicabilityBranchConfig();
      Branch branch = orcsApi.getQueryFactory().branchQuery().andId(branchId).getResults().getAtMostOneOrNull();
      config.setBranch(branch);

      // Load all variants (stored as branch views)
      List<ArtifactReadable> branchViews =
         orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.BranchView).getResults().getList();
      Collections.sort(branchViews, new NamedComparator(SortOrder.ASCENDING));
      Map<ArtifactId, Map<String, List<String>>> branchViewsMap = new HashMap<>();
      for (ArtifactToken branchView : branchViews) {
         config.addVariant(branchView);
         branchViewsMap.put(branchView,
            orcsApi.getQueryFactory().applicabilityQuery().getNamedViewApplicabilityMap(branch, branchView));
      }

      List<ArtifactReadable> featureArts =
         orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.Feature).getResults().getList();
      Collections.sort(featureArts, new NamedComparator(SortOrder.ASCENDING));
      for (ArtifactToken featureArt : featureArts) {
         FeatureDefinition fDef = getFeatureDefinition(featureArt);
         config.addFeature(fDef);
      }

      // Setup Features column and add maps in order
      for (FeatureDefinition fDef : config.getFeatureIdToFeature().values()) {
         Map<String, String> variantToValue = new HashMap<>(config.getFeatureIdToFeature().values().size() + 1);
         /**
          * Note: Confusing, but Features is the header of the first column, so add it here. The rest of the columns
          * headers will be the variant names added below.
          */
         variantToValue.put("feature", fDef.getName());
         variantToValue.put("id", fDef.getIdString());
         variantToValue.put("description", fDef.getDescription());
         config.addFeatureToValueMap(variantToValue);

      }

      // Add variants and values
      int count = 0;
      for (FeatureDefinition fDef : config.getFeatureIdToFeature().values()) {
         for (ArtifactToken variant : config.getVariants()) {
            Map<String, String> variantToValue = config.getFeatureToValues(count);
            String variantToFeatureValue = getVariantToFeatureValue(variant, fDef, branchViewsMap);
            variantToValue.put(variant.getName().toLowerCase(), variantToFeatureValue);
         }
         count++;
      }

      return config;
   }

   private String getVariantToFeatureValue(ArtifactToken variant, FeatureDefinition fDef, Map<ArtifactId, Map<String, List<String>>> branchViewsMap) {
      Map<String, List<String>> map = branchViewsMap.get(variant);
      List<String> list = map.get(fDef.getName());
      if (list == null) {
         return "";
      }
      return org.eclipse.osee.framework.jdk.core.util.Collections.toString(",", list);
   }

   @Override
   public VariantDefinition getVariantDefinition(ArtifactToken featureArt) {
      ArtifactReadable art = (ArtifactReadable) featureArt;
      VariantDefinition variant = new VariantDefinition();
      variant.setId(art.getId());
      variant.setName(art.getName());
      variant.setData(art);
      return variant;
   }

   @Override
   public FeatureDefinition getFeatureDefinition(ArtifactToken featureArt) {
      ArtifactReadable art = (ArtifactReadable) featureArt;
      FeatureDefinition feature = new FeatureDefinition();
      feature.setId(art.getId());
      feature.setName(art.getName());
      feature.setDefaultValue(art.getSoleAttributeValue(CoreAttributeTypes.DefaultValue, ""));
      feature.setValues(art.getAttributeValues(CoreAttributeTypes.Value));
      feature.setValueType(art.getSoleAttributeAsString(CoreAttributeTypes.FeatureValueType, ""));
      feature.setMultiValued(art.getSoleAttributeValue(CoreAttributeTypes.FeatureMultivalued, false));
      feature.setDescription(art.getSoleAttributeAsString(CoreAttributeTypes.Description, ""));
      feature.setData(featureArt);
      return feature;
   }

   @Override
   public ArtifactToken createUpdateFeatureDefinition(FeatureDefinition featureDef, TransactionBuilder tx, XResultData results) {
      ArtifactToken fDefArt = null;
      if (Strings.isInValid(featureDef.getName())) {
         results.error("Feature must have a name.");
         return null;
      }

      fDefArt = orcsApi.getQueryFactory().fromBranch(tx.getBranch()).andId(
         ArtifactId.valueOf(featureDef.getId())).getResults().getAtMostOneOrNull();
      if (fDefArt == null) {
         ArtifactToken featuresFolder = tx.getWriteable(CoreArtifactTokens.FeaturesFolder);
         // Check current transaction first
         if (featuresFolder.isInvalid()) {
            featuresFolder = getFeaturesFolder(tx.getBranch());
         }
         if (featuresFolder.isInvalid()) {
            results.error("Features Folder cannot be null");
            return null;
         }
         Long artId = featureDef.getId();
         if (artId == null) {
            artId = Lib.generateArtifactIdAsInt();
         }
         fDefArt = tx.createArtifact(featuresFolder, CoreArtifactTypes.Feature, featureDef.getName(), artId);
      }
      updateFeatureDefinition(fDefArt, featureDef, tx);
      return fDefArt;
   }

   private void updateFeatureDefinition(ArtifactToken fDefArt, FeatureDefinition featureDef, TransactionBuilder tx) {
      tx.setName(fDefArt, featureDef.getName());
      tx.setSoleAttributeValue(fDefArt, CoreAttributeTypes.DefaultValue, featureDef.getDefaultValue());
      tx.setAttributesFromValues(fDefArt, CoreAttributeTypes.Value, featureDef.getValues());
      tx.setSoleAttributeValue(fDefArt, CoreAttributeTypes.FeatureValueType, featureDef.getValueType());
      tx.setSoleAttributeValue(fDefArt, CoreAttributeTypes.FeatureMultivalued, featureDef.isMultiValued());
      tx.setSoleAttributeValue(fDefArt, CoreAttributeTypes.Description, featureDef.getDescription());
   }

   @Override
   public List<BranchViewToken> getApplicabilityBranches() {
      List<BranchViewToken> tokens = new ArrayList<BranchViewToken>();
      for (Branch branch : orcsApi.getQueryFactory().branchQuery().includeArchived(false).includeDeleted(
         false).andIsOfType(BranchType.BASELINE, BranchType.WORKING).andStateIs(BranchState.CREATED,
            BranchState.MODIFIED).getResults().getList()) {
         if (orcsApi.getQueryFactory().fromBranch(branch).andId(CoreArtifactTokens.ProductLineFolder).exists()) {
            tokens.add(new BranchViewToken(branch, branch.getName(), branch.getViewId()));
         }
      }
      return tokens;
   }

   @Override
   public ArtifactToken getFeaturesFolder(BranchId branch) {
      if (featureFolder.isInvalid()) {
         featureFolder = orcsApi.getQueryFactory().fromBranch(branch).andId(
            CoreArtifactTokens.FeaturesFolder).getArtifactOrSentinal();
      }
      if (featureFolder.isInvalid()) {
         featureFolder = orcsApi.getQueryFactory().fromBranch(branch).andNameEquals(
            CoreArtifactTokens.FeaturesFolder.getName()).getAtMostOneOrSentinal();
      }
      return featureFolder;
   }

   @Override
   public ArtifactToken getVariantsFolder(BranchId branch) {
      if (variantsFolder.isInvalid()) {
         variantsFolder = orcsApi.getQueryFactory().fromBranch(branch).andId(
            CoreArtifactTokens.VariantsFolder).getArtifactOrSentinal();
      }
      if (variantsFolder.isInvalid()) {
         variantsFolder = orcsApi.getQueryFactory().fromBranch(branch).andNameEquals(
            CoreArtifactTokens.VariantsFolder.getName()).getAtMostOneOrSentinal();
      }
      if (variantsFolder.isInvalid()) {
         variantsFolder =
            orcsApi.getQueryFactory().fromBranch(branch).andNameEquals("Products").getAtMostOneOrSentinal();
      }
      return variantsFolder;
   }

   @Override
   public ArtifactToken getProductLineFolder(BranchId branch) {
      if (plFolder.isInvalid()) {
         plFolder = orcsApi.getQueryFactory().fromBranch(branch).andId(
            CoreArtifactTokens.ProductLineFolder).getArtifactOrSentinal();
      }
      if (plFolder.isInvalid()) {
         plFolder = orcsApi.getQueryFactory().fromBranch(branch).andNameEquals("Product Line").getAtMostOneOrSentinal();
      }
      return plFolder;
   }

   @Override
   public String convertConfigToArtifact(BranchId branch) {
      TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, SystemUser.OseeSystem,
         "Convert Feature Defs to Artifact");

      for (FeatureDefinition feature : orcsApi.getQueryFactory().applicabilityQuery().getFeatureDefinitionData(
         branch)) {
         ArtifactToken featureArt = getFeaturesFolder(branch);

         // type goes to multivalued
         if (feature.getValueType().equals("single")) {
            // do nothing
         } else if (feature.getValueType().equals("multiple")) {
            feature.setMultiValued(true);
         } else {
            throw new IllegalArgumentException(String.format("Unexpected value type [%s]", feature.getValueType()));
         }

         // set feature value type
         feature.setValueType("String");

         updateFeatureDefinition(featureArt, feature, tx);
      }
      tx.commit();
      return AHTML.simplePage("Completed");
   }

   @Override
   public XResultData createUpdateFeature(FeatureDefinition feature, BranchId branch, UserId account) {
      XResultData results = new XResultData();
      try {
         UserId user = account;
         if (user == null) {
            user = SystemUser.OseeSystem;
         }
         TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, user,
            "Update Feature " + feature.toStringWithId());
         createUpdateFeatureDefinition(feature, tx, results);
         tx.commit();
      } catch (Exception ex) {
         results.error(Lib.exceptionToString(ex));
      }
      return results;
   }

   @Override
   public FeatureDefinition getFeature(String feature, BranchId branch) {
      if (Strings.isNumeric(feature)) {
         ArtifactToken featureArt =
            orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.Feature).andId(
               Long.valueOf(feature)).getResults().getAtMostOneOrNull();
         if (featureArt != null) {
            return getFeatureDefinition(featureArt);
         }
      } else {
         ArtifactToken featureArt =
            orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.Feature).andNameEquals(
               feature).getResults().getAtMostOneOrNull();
         if (featureArt != null) {
            return getFeatureDefinition(featureArt);
         }
      }
      return null;
   }

   @Override
   public XResultData deleteFeature(String feature, BranchId branch, UserId account) {
      XResultData results = new XResultData();
      try {
         UserId user = account;
         if (user == null) {
            user = SystemUser.OseeSystem;
         }
         ArtifactToken featureArt = (ArtifactToken) getFeature(feature, branch).getData();
         TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, user,
            "Update Feature " + featureArt.toStringWithId());
         tx.deleteArtifact(featureArt);
         tx.commit();
      } catch (Exception ex) {
         results.error(Lib.exceptionToString(ex));
      }
      return results;
   }

   @Override
   public VariantDefinition getVariant(String variant, BranchId branch) {
      if (Strings.isNumeric(variant)) {
         ArtifactToken variantArt =
            orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.BranchView).andId(
               Long.valueOf(variant)).getResults().getAtMostOneOrNull();
         if (variantArt != null) {
            return getVariantDefinition(variantArt);
         }
      } else {
         ArtifactToken variantArt =
            orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(CoreArtifactTypes.BranchView).andNameEquals(
               variant).getResults().getAtMostOneOrNull();
         if (variantArt != null) {
            return getVariantDefinition(variantArt);
         }
      }
      return null;
   }

   @Override
   public XResultData createUpdateVariant(VariantDefinition variant, BranchId branch, UserId account) {
      XResultData results = new XResultData();
      if (!Strings.isValid(variant.getName())) {
         results.errorf("Name can not be empty for variant %s", variant.getId());
      }
      try {
         UserId user = account;
         if (user == null) {
            user = SystemUser.OseeSystem;
         }
         TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, user,
            "Update Variant " + variant.toStringWithId());
         createUpdateVariantDefinition(variant, tx);
         tx.commit();
      } catch (Exception ex) {
         results.error(Lib.exceptionToString(ex));
      }
      return results;
   }

   private ArtifactToken createUpdateVariantDefinition(VariantDefinition variant, TransactionBuilder tx) {
      ArtifactToken vDefArt = null;
      if (variant.getId() != null && variant.getId() > 0) {
         vDefArt = orcsApi.getQueryFactory().fromBranch(tx.getBranch()).andId(
            ArtifactId.valueOf(variant.getId())).getResults().getAtMostOneOrNull();
      }
      if (vDefArt == null) {
         Long artId = variant.getId();
         if (artId == null || artId <= 0) {
            artId = Lib.generateArtifactIdAsInt();
         }
         vDefArt = tx.createArtifact(getVariantsFolder(tx.getBranch()), CoreArtifactTypes.BranchView, variant.getName(),
            artId);
      }
      tx.setName(vDefArt, variant.getName());
      // reload artifact to return
      return orcsApi.getQueryFactory().fromBranch(vDefArt.getBranch()).andId(vDefArt.getId()).getArtifactOrSentinal();
   }

   @Override
   public XResultData deleteVariant(String variant, BranchId branch, UserId account) {
      XResultData results = new XResultData();
      try {
         UserId user = account;
         if (user == null) {
            user = SystemUser.OseeSystem;
         }
         ArtifactToken variantArt = (ArtifactToken) getVariant(variant, branch).getData();
         if (variantArt.isValid()) {
            TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, user,
               "Delete Variant " + variantArt.toStringWithId());
            tx.deleteArtifact(variantArt);
            tx.commit();
         }
      } catch (Exception ex) {
         results.error(Lib.exceptionToString(ex));
      }
      return results;
   }

   @Override
   public XResultData setApplicability(BranchId branch, ArtifactId variant, ArtifactId feature, String applicability, UserId account) {
      XResultData results = new XResultData();
      try {
         UserId user = account;
         if (user == null) {
            user = SystemUser.OseeSystem;
         }
         ArtifactToken featureArt = orcsApi.getQueryFactory().fromBranch(branch).andId(feature).getArtifactOrNull();
         FeatureDefinition fDef = orcsApi.getApplicabilityOps().getFeatureDefinition(featureArt);
         TransactionBuilder tx =
            orcsApi.getTransactionFactory().createTransaction(branch, user, "Set Variant Feature Applicability");
         List<String> existingValues = new LinkedList<>();
         for (String appl : orcsApi.getQueryFactory().tupleQuery().getTuple2(CoreTupleTypes.ViewApplicability, branch,
            variant)) {
            if (appl.startsWith(fDef.getName() + " = ")) {
               existingValues.add(appl);
            }
         }
         List<String> newValues = new LinkedList<>();
         for (String value : applicability.split(";")) {
            value = value.replace("^ +", "");
            value = value.replace(" +$", "");
            value = featureArt.getName() + " = " + value;
            newValues.add(value);
         }
         System.err.println("existing " + existingValues);
         System.err.println("new " + newValues);
         boolean change = false;
         // delete existing if not match value
         for (String existingValue : existingValues) {
            if (!newValues.contains(existingValue)) {
               change = true;
               System.err.println("deleting " + existingValue);
               tx.deleteTuple2(CoreTupleTypes.ViewApplicability, variant, existingValue);
            }
         }
         // add new
         for (String newValue : newValues) {
            if (!existingValues.contains(newValue)) {
               change = true;
               System.err.println("add " + newValue);
               tx.addTuple2(CoreTupleTypes.ViewApplicability, variant, newValue);
            }
         }
         if (change) {
            tx.commit();
         }
      } catch (Exception ex) {
         results.error(Lib.exceptionToString(ex));
      }
      return results;
   }

}