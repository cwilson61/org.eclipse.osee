/*********************************************************************
 * Copyright (c) 2013 Boeing
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

package org.eclipse.osee.disposition.rest.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.data.AtsArtifactToken;
import org.eclipse.osee.disposition.model.CiItemData;
import org.eclipse.osee.disposition.model.CiSetData;
import org.eclipse.osee.disposition.model.CopySetParams;
import org.eclipse.osee.disposition.model.Discrepancy;
import org.eclipse.osee.disposition.model.DispoAnnotationData;
import org.eclipse.osee.disposition.model.DispoConfig;
import org.eclipse.osee.disposition.model.DispoItem;
import org.eclipse.osee.disposition.model.DispoItemData;
import org.eclipse.osee.disposition.model.DispoSet;
import org.eclipse.osee.disposition.model.DispoSetData;
import org.eclipse.osee.disposition.model.DispoSetDescriptorData;
import org.eclipse.osee.disposition.model.DispoStrings;
import org.eclipse.osee.disposition.model.DispoSummarySeverity;
import org.eclipse.osee.disposition.model.Note;
import org.eclipse.osee.disposition.model.OperationReport;
import org.eclipse.osee.disposition.rest.DispoApi;
import org.eclipse.osee.disposition.rest.DispoApiConfiguration;
import org.eclipse.osee.disposition.rest.DispoImporterApi;
import org.eclipse.osee.disposition.rest.internal.importer.DispoImporterFactory;
import org.eclipse.osee.disposition.rest.internal.importer.DispoImporterFactory.ImportFormat;
import org.eclipse.osee.disposition.rest.internal.importer.DispoSetCopier;
import org.eclipse.osee.disposition.rest.internal.importer.coverage.CoverageAdapter;
import org.eclipse.osee.disposition.rest.util.DispoUtil;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactReadable;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.AttributeTypeToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.BranchToken;
import org.eclipse.osee.framework.core.data.OseeClient;
import org.eclipse.osee.framework.core.data.UserId;
import org.eclipse.osee.framework.core.enums.CoreArtifactTypes;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.DispoOseeTypes;
import org.eclipse.osee.framework.core.executor.ExecutorAdmin;
import org.eclipse.osee.framework.core.util.JsonUtil;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.type.OseeStateException;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.jdk.core.util.Conditions;
import org.eclipse.osee.framework.jdk.core.util.Zip;
import org.eclipse.osee.logger.Log;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.transaction.TransactionBuilder;

/**
 * @author Angel Avila
 */
public class DispoApiImpl implements DispoApi {

   private final Date newDate;

   private Storage storage;
   private ExecutorAdmin executor;
   private Log logger;
   private DispoDataFactory dataFactory;
   private DispoConnector dispoConnector;
   private DispoResolutionValidator resolutionValidator;
   private DispoImporterFactory importerFactory;
   private OrcsApi orcsApi;
   private AtsApi atsApi;

   private static final Pattern gitlabApiPattern = Pattern.compile("^.*projects.*?jobs.*?artifacts/?$");

   private volatile DispoApiConfiguration config;

   public DispoApiImpl() {
      newDate = new Date();
   }

   public void setOrcsApi(OrcsApi orcsApi) {
      this.orcsApi = orcsApi;
   }

   public void setAtsApi(AtsApi atsApi) {
      this.atsApi = atsApi;
   }

   @Override
   public DispoApiConfiguration getConfig() {
      return config;
   }

   public void setConfig(DispoApiConfiguration config) {
      this.config = config;
   }

   public void setExecutor(ExecutorAdmin executor) {
      this.executor = executor;
   }

   public void setLogger(Log logger) {
      this.logger = logger;
   }

   public void setDataFactory(DispoDataFactory dataFactory) {
      this.dataFactory = dataFactory;
   }

   public void setDispoConnector(DispoConnector dispoConnector) {
      this.dispoConnector = dispoConnector;
   }

   public void setResolutionValidator(DispoResolutionValidator resolutionValidator) {
      this.resolutionValidator = resolutionValidator;
   }

   /**
    * Only for use my unit test (remove once this is integration tested)
    */
   public void setStorageForTest(Storage storage) {
      this.storage = storage;
   }

   public void start(Map<String, Object> props) {
      logger.trace("Starting DispoApiImpl...");
      update(props);
      importerFactory = new DispoImporterFactory(dataFactory, executor, config, logger, orcsApi.jaxRsApi());
      storage = new OrcsStorageImpl(orcsApi);
   }

   public void update(Map<String, Object> props) {
      logger.trace("Configuring [%s]...", getClass().getSimpleName());
      setConfig(DispoApiConfiguration.newConfig(props));
   }

   public void stop() {
      logger.trace("Stopping DispoApiImpl...");
   }

   private DispoQuery getQuery() {
      return storage;
   }

   private DispoWriter getWriter() {
      return storage;
   }

   @Override
   public Long createDispoProgram(String name) {
      return getWriter().createDispoProgram(name).getId();
   }

   @Override
   public ArtifactId createSet(BranchId branch, String importPath, String setName, String partitionName) {
      return getWriter().createSet(branch, importPath, setName, partitionName);
   }

   @Override
   public ArtifactId createDispoSet(BranchId branch, DispoSetDescriptorData descriptor) {
      DispoSetData newSet = dataFactory.creteSetDataFromDescriptor(descriptor);
      return getWriter().createDispoSet(branch, newSet);
   }

   private void createDispoItems(BranchId branch, String setId, List<DispoItem> dispoItems) {
      DispoSet parentSet = getQuery().findDispoSetsById(branch, setId);
      if (parentSet != null) {
         getWriter().createDispoItems(branch, parentSet, dispoItems);
      }
   }

   @Override
   public String createDispoAnnotation(BranchId branch, String itemId, DispoAnnotationData annotationToCreate,
      String userName, boolean isCi) {
      String idOfNewAnnotation = "";
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null && (isCi || dispoItem.getAssignee().equalsIgnoreCase(userName))) {
         List<DispoAnnotationData> annotationsList = dispoItem.getAnnotationsList();
         dataFactory.initAnnotation(annotationToCreate);
         idOfNewAnnotation = dataFactory.getNewId();
         annotationToCreate.setId(idOfNewAnnotation);
         int indexOfAnnotation = annotationsList.size();
         annotationToCreate.setIndex(indexOfAnnotation);

         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         dispoConnector.connectAnnotation(annotationToCreate, discrepanciesList);
         annotationsList.add(indexOfAnnotation, annotationToCreate);

         DispoItem updatedItem;
         updatedItem = dataFactory.createUpdatedItem(annotationsList, discrepanciesList);
         getWriter().updateDispoItem(branch, dispoItem.getGuid(), updatedItem);
      }
      return idOfNewAnnotation;
   }

   @Override
   public void editDispoSet(BranchId branch, String setId, DispoSetData newSet) {
      DispoSet dispSetToEdit = getQuery().findDispoSetsById(branch, setId);
      if (dispSetToEdit != null) {
         if (newSet.getOperation() != null) {
            runOperation(branch, dispSetToEdit, newSet, false);
         } else {
            getWriter().updateDispoSet(branch, dispSetToEdit.getGuid(), newSet);
         }
      }
   }

   @Override
   public void importDispoSet(BranchId branch, String dispoSetId) {
      DispoSet dispSetToEdit = getQuery().findDispoSetsById(branch, dispoSetId);
      if (dispSetToEdit != null) {
         String operation = DispoStrings.Operation_Import;
         DispoSetData newSet = new DispoSetData();
         newSet.setOperation(operation);
         runOperation(branch, dispSetToEdit, newSet, false);
      }
   }

   @Override
   public void importDispoSet(BranchId branch, String dispoSetId, String coverageImportApi, String partition) {
      DispoSet dispSetToEdit = getQuery().findDispoSetsById(branch, dispoSetId);

      if (dispSetToEdit != null) {
         String operation = DispoStrings.Operation_Import;
         DispoSetData newSet = new DispoSetData();
         boolean editImportInfo = false;
         String currPartition = dispSetToEdit.getCoveragePartition();
         String currImportApi = dispSetToEdit.getCoverageImportApi();

         newSet.setOperation(operation);

         //Ensure older sets have these attributes when they are re-imported
         if (currPartition == null || !partition.equals(currPartition)) {
            editImportInfo = true;
            newSet.setCoveragePartition(partition);
         }
         if (currImportApi == null || !coverageImportApi.equals(currImportApi)) {
            editImportInfo = true;
            newSet.setCoverageImportApi(coverageImportApi);
         }

         if (editImportInfo) {
            getWriter().updateDispoSet(branch, dispSetToEdit.getGuid(), newSet);
         }
         runOperation(branch, dispSetToEdit, newSet, false);
      }
   }

   @Override
   public void importAllDispoSets(BranchId branch, String filterState) {
      List<DispoSet> dispoSets = new ArrayList<>();
      DispoSetData newSet;

      dispoSets = getDispoSets(branch, DispoStrings.CODE_COVERAGE);

      if (filterState.isEmpty()) {
         filterState = DispoStrings.STATE_NONE;
      }

      for (DispoSet set : dispoSets) {
         newSet = new DispoSetData();
         newSet.setOperation(DispoStrings.Operation_Import);
         if (filterState.equalsIgnoreCase(DispoStrings.STATE_ALL) || set.getImportState().equalsIgnoreCase(
            filterState)) {
            runOperation(branch, set, newSet, true);
         } else {
            getWriter().updateDispoSet(branch, set.getGuid(), newSet);
         }
      }

   }

   @Override
   public void importAllDispoPrograms(String filterState) {
      List<BranchToken> dispoBranches = new ArrayList<>();

      dispoBranches = getDispoPrograms();

      for (BranchToken branch : dispoBranches) {
         importAllDispoSets(branch, filterState);
      }
   }

   @Override
   public Set<String> getTestScripts(BranchId branch, String dispoSetId) {
      Set<String> testScripts = new HashSet<String>();

      for (DispoItem dispoItem : getDispoItems(branch, dispoSetId, true)) {
         if (dispoItem != null) {
            for (DispoAnnotationData annotation : dispoItem.getAnnotationsList()) {
               if (annotation.getResolutionType().equals(DispoStrings.Test_Unit_Resolution)) {
                  testScripts.add(annotation.getResolution());
               }
            }
         }
      }
      return testScripts;
   }

   @Override
   public Set<String> getTestScripts(BranchId branch) {
      Set<String> testScripts = new HashSet<String>();

      List<DispoSet> dispoSets = getDispoSets(branch, DispoStrings.CODE_COVERAGE);
      for (DispoSet dispoSet : dispoSets) {
         for (DispoItem dispoItem : getDispoItems(branch, dispoSet.getIdString(), true)) {
            if (dispoItem != null) {
               for (DispoAnnotationData annotation : dispoItem.getAnnotationsList()) {
                  if (annotation.getResolutionType().equals(DispoStrings.Test_Unit_Resolution)) {
                     testScripts.add(annotation.getResolution());
                  }
               }
            }
         }
      }
      return testScripts;
   }

   @Override
   public boolean deleteDispoSet(BranchId branch, String setId) {
      return getWriter().deleteDispoSet(branch, setId);
   }

   @Override
   public boolean editMassDispositions(BranchId branch, String setId, List<String> ids, String resolutionType,
      String resolution) {
      boolean wasUpdated = false;
      List<DispoItem> itemsToEdit = massDisposition(branch, setId, ids, resolutionType, resolution);
      if (itemsToEdit.size() > 0) {
         editDispoItems(branch, setId, itemsToEdit, true, "Import");
         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public boolean editDispoItem(BranchId branch, String itemId, DispoItemData newDispoItem, String userName,
      boolean assignUser) {
      boolean wasUpdated = false;
      DispoItem dispoItemToEdit = getQuery().findDispoItemById(branch, itemId);

      // We will not allow the user to do mass edit of Annotations or discrepancies
      if (assignUser || dispoItemToEdit != null && newDispoItem.getAnnotationsList() == null) {
         getWriter().updateDispoItem(branch, dispoItemToEdit.getGuid(), newDispoItem);
         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public boolean updateAllDispoItems(BranchId branch, String dispoSetId) {
      boolean wasUpdated = false;

      TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch, "Update All Dispo Item Status");

      for (DispoItem dispoItem : getDispoItems(branch, dispoSetId, true)) {
         if (dispoItem != null) {
            boolean isPass = true;
            boolean isAnalysis = false;
            boolean needsModify = false;
            boolean isIncomplete = false;

            ArtifactReadable dispoItemArt = orcsApi.getQueryFactory().fromBranch(branch).andUuid(
               Long.valueOf(dispoItem.getGuid())).getResults().getExactlyOne();

            String itemStatus = dispoItem.getStatus();
            boolean needsEdit = false;
            String newStatus;
            for (DispoAnnotationData annotation : dispoItem.getAnnotationsList()) {
               if (annotation.getIsPairAnnotation()) {
                  continue;
               }
               if (!annotation.getIsResolutionValid()) {
                  isIncomplete = true;
                  isPass = false;
               }
               if (annotation.setAndGetIsAnalyze()) {
                  isAnalysis = true;
                  isPass = false;
               }
               if (annotation.setAndGetNeedsModify()) {
                  needsModify = true;
                  isPass = false;
               }
               if (!annotation.getIsDefault()) {
                  isPass = false;
               }
            }

            if (isPass) {
               newStatus = DispoStrings.Item_Pass;
            } else if (isIncomplete) {
               newStatus = DispoStrings.Item_Incomplete;
            } else if (needsModify) {
               newStatus = DispoStrings.Item_Modify;
            } else if (isAnalysis) {
               newStatus = DispoStrings.Item_Analysis;
            } else {
               newStatus = DispoStrings.Item_Complete;
            }

            if (!newStatus.equals(itemStatus)) {
               needsEdit = true;
            }

            if (needsEdit) {
               wasUpdated = true;
               tx.setSoleAttributeFromString(dispoItemArt, CoreAttributeTypes.CoverageStatus, newStatus);
            }
         }
      }
      if (wasUpdated) {
         tx.commit();
      }
      return wasUpdated;
   }

   private String getFullFilePathFromDispoItemId(BranchId branch, String itemId, DispoItem dispoItemToEdit) {
      Conditions.checkNotNull(dispoItemToEdit, "Dispo Item to Edit");
      Conditions.checkNotNull(branch, "Branch");
      Conditions.checkNotNull(itemId, "Item Id");

      ArtifactId set = getQuery().getDispoItemParentSet(branch, itemId);
      if (set != null) {
         DispoSet dispoSet = getQuery().findDispoSetsById(branch, set.getIdString());
         if (dispoSet != null) {
            String importPath = dispoSet.getCoverageImportApi();
            String name = dispoItemToEdit.getName().replaceAll(config.getFileExtRegex(), ".LIS");
            return importPath + File.separator + "vcast" + File.separator + name;
         }
      }
      return "";
   }

   @Override
   public boolean massEditTeam(BranchId branch, String setId, List<String> itemNames, String team, String operation) {
      boolean wasUpdated = false;
      Set<DispoItem> dispoItems = new HashSet<>();
      List<DispoItem> itemsFromSet = getDispoItems(branch, setId);
      Map<String, String> nameToId = new HashMap<>();
      OperationReport report = new OperationReport();

      for (DispoItem item : itemsFromSet) {
         nameToId.put(item.getName(), item.getGuid());
      }

      Set<String> itemsUpdated = new HashSet<>();
      for (String name : itemNames) {
         name = name.trim();
         String matchingItemId = nameToId.get(name);
         if (matchingItemId == null) {
            report.addEntry(name, "No existing item with this name for the selected set", DispoSummarySeverity.WARNING);
         } else {
            itemsUpdated.add(name);
            DispoItemData newItem = new DispoItemData();
            newItem.setGuid(matchingItemId);
            newItem.setTeam(team);
            dispoItems.add(newItem);
         }

      }

      if (!itemsUpdated.isEmpty()) {
         report.addEntry(team, String.format("Team Applied to %s of %s items", itemsUpdated.size(), itemNames.size()),
            DispoSummarySeverity.UPDATE);
         if (itemsUpdated.size() != itemNames.size()) {
            Set<String> uniqueNames = new HashSet<>(itemNames);
            itemNames.removeAll(uniqueNames);
            if (!itemNames.isEmpty()) {
               String duplicatesAsString = Collections.toString(", ", itemNames);
               report.addEntry(team,
                  String.format("There were %s duplciates: %s", itemNames.size(), duplicatesAsString),
                  DispoSummarySeverity.WARNING);
            }
         }
         editDispoItems(branch, setId, dispoItems, false, operation);
      } else {
         report.addEntry("Womp womp womp",
            "No items were updated. Please check your 'Items' list and make sure it's a comma seperated list of item names",
            DispoSummarySeverity.ERROR);
      }

      // Generate report
      getWriter().updateOperationSummary(branch, setId, report);
      return wasUpdated;
   }

   private void editDispoItems(BranchId branch, String setId, Collection<DispoItem> dispoItems, boolean resetRerunFlag,
      String operation) {
      getWriter().updateDispoItems(branch, dispoItems, resetRerunFlag, operation);
   }

   @Override
   public boolean deleteDispoItem(BranchId branch, String itemId) {
      return getWriter().deleteDispoItem(branch, itemId);
   }

   @Override
   public boolean editDispoAnnotation(BranchId branch, String itemId, String annotationId,
      DispoAnnotationData newAnnotation, String userName, boolean isCi) {
      boolean wasUpdated = false;
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null && (isCi || dispoItem.getAssignee().equalsIgnoreCase(userName))) {

         List<DispoAnnotationData> annotationsList = dispoItem.getAnnotationsList();
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         DispoAnnotationData origAnnotation = DispoUtil.getById(annotationsList, annotationId);
         if (!origAnnotation.isValid()) {
            return false;
         }
         int indexOfAnnotation = origAnnotation.getIndex();
         boolean needToReconnect = false;

         // now if the new Annotation modified the location Reference or resolution then disconnect the annotation and try to match it to discrepancies again
         String newLocationRefs = newAnnotation.getLocationRefs();
         String newResolution = newAnnotation.getResolution();
         String newResolutionType = newAnnotation.getResolutionType();

         boolean isTypeChange = !origAnnotation.getResolutionType().equals(newResolutionType);
         boolean isResolutionChange = !origAnnotation.getResolution().equals(newResolution);

         if (isTypeChange || isResolutionChange) {
            needToReconnect = true;
            resolutionValidator.validate(newAnnotation);
         }
         if (!origAnnotation.getLocationRefs().equals(newLocationRefs)) {
            needToReconnect = true;
         }

         if (needToReconnect == true) {
            newAnnotation.disconnect();
            dispoConnector.connectAnnotation(newAnnotation, discrepanciesList);
         }

         annotationsList.set(indexOfAnnotation, newAnnotation);

         DispoItemData modifiedDispoItem = DispoUtil.itemArtToItemData(getDispoItemById(branch, itemId), true);

         modifiedDispoItem.setAnnotationsList(annotationsList);

         modifiedDispoItem.setStatus(dispoConnector.getItemStatus(modifiedDispoItem));

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), modifiedDispoItem);

         wasUpdated = true;

         //For MCDC, if it is a pair, check to see if the parent should be set to complete, if so set it.
         if (newAnnotation.getIsPairAnnotation() && newAnnotation.getIsResolutionValid()) {
            String[] splitPairLoc = newAnnotation.getLocationRefs().split("\\.", 3);
            if (splitPairLoc.length >= 2) {
               String parentLoc = String.format("%s.%s", splitPairLoc[0], splitPairLoc[1]);
               DispoAnnotationData parentAnnotation = DispoUtil.getByLocation(annotationsList, parentLoc);
               if (!parentAnnotation.getIsResolutionValid()) {
                  List<String> satisfiedPairs = new ArrayList<>();
                  String newParentResolutionType = newAnnotation.getResolutionType();
                  String newParentResolution = newAnnotation.getResolution();
                  for (int pairNumber : newAnnotation.getPairedWith()) {
                     String pairLoc = String.format("%s.%d", parentLoc, pairNumber);
                     DispoAnnotationData pairAnnotation = DispoUtil.getByLocation(annotationsList, pairLoc);
                     if (pairAnnotation.getIsResolutionValid()) {
                        if (newAnnotation.getRow() < pairNumber) {
                           satisfiedPairs.add(String.format("%s/%s", newAnnotation.getRow(), pairNumber));
                        } else {
                           satisfiedPairs.add(String.format("%s/%s", pairNumber, newAnnotation.getRow()));
                        }
                        if (!pairAnnotation.getResolutionType().equals(newParentResolutionType)) {
                           newParentResolutionType = newParentResolutionType + "/" + pairAnnotation.getResolutionType();
                        }
                        if (!pairAnnotation.getResolution().equals(newParentResolution)) {
                           newParentResolution = newParentResolution + "/" + pairAnnotation.getResolution();
                        }
                     }
                  }
                  if (!satisfiedPairs.isEmpty()) {
                     DispoAnnotationData newParentAnnotation = new DispoAnnotationData();
                     newParentAnnotation = parentAnnotation;
                     newParentAnnotation.setResolutionType(newParentResolutionType);
                     newParentAnnotation.setResolution(newParentResolution);
                     newParentAnnotation.setSatisfiedPairs(
                        String.format("Pairs Satisfied By: %s", satisfiedPairs.toString()));
                     editDispoAnnotation(branch, itemId, parentAnnotation.getGuid(), newParentAnnotation, userName,
                        isCi);
                  }
               }
            }
         }
      }
      return wasUpdated;
   }

   @Override
   public boolean deleteDispoAnnotation(BranchId branch, String itemId, String annotationId, String userName,
      boolean isCi) {
      boolean wasUpdated = false;
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null && (isCi || dispoItem.getAssignee().equalsIgnoreCase(userName))) {
         List<DispoAnnotationData> annotationsList = dispoItem.getAnnotationsList();
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         DispoAnnotationData annotationToRemove = DispoUtil.getById(annotationsList, annotationId);
         if (!annotationToRemove.isValid()) {
            return wasUpdated;
         }
         annotationToRemove.disconnect();
         // collapse list so there are no gaps
         List<DispoAnnotationData> newAnnotationsList =
            removeAnnotationFromList(annotationsList, annotationToRemove.getIndex());

         DispoItem updatedItem = dataFactory.createUpdatedItem(newAnnotationsList, discrepanciesList);

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), updatedItem);
         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public boolean deleteAllDispoAnnotation(BranchId branch, String itemId, String userName, boolean isCi) {
      boolean wasUpdated = false;
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null && (isCi || dispoItem.getAssignee().equalsIgnoreCase(userName))) {
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         for (DispoAnnotationData annotation : dispoItem.getAnnotationsList()) {
            annotation.disconnect();
         }

         DispoItem updatedItem = dataFactory.createUpdatedItem(new ArrayList<>(), discrepanciesList);

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), updatedItem);
         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public List<BranchToken> getDispoPrograms() {
      return getQuery().getDispoBranches();
   }

   @Override
   public List<String> getDispoProgramNames() {
      return getQuery().getDispoBranchNames();
   }

   @Override
   public BranchToken getDispoProgramIdByName(String branchName) {
      return getQuery().findDispoProgramIdByName(branchName);
   }

   @Override
   public List<DispoSet> getDispoSets(BranchId branch, String type) {
      return getQuery().findDispoSets(branch, type);
   }

   @Override
   public List<String> getDispoSetNames(BranchId branch, String type) {
      return getQuery().getDispoSets(branch, type);
   }

   @Override
   public DispoSet getDispoSetById(BranchId branch, String setId) {
      return getQuery().findDispoSetsById(branch, setId);
   }

   @Override
   public String getDispoSetIdByName(BranchId branchId, String setName) {
      return getQuery().findDispoSetIdByName(branchId, setName);
   }

   @Override
   public List<DispoItem> getDispoItems(BranchId branch, String setArtId, boolean isDetailed) {
      return getQuery().findDispoItems(branch, setArtId, isDetailed);
   }

   private List<DispoItem> getDispoItems(BranchId branch, String setArtId) {
      return getDispoItems(branch, setArtId, true);
   }

   @Override
   public DispoItem getDispoItemById(BranchId branch, String itemId) {
      return getQuery().findDispoItemById(branch, itemId);
   }

   @Override
   public String getDispoItemIdByName(BranchId branchId, String setId, String itemName) {
      return getQuery().findDispoItemIdByName(branchId, setId, itemName);
   }

   @Override
   public Collection<DispoItem> getDispoItemByAnnotationText(BranchId branch, String setId, String keyword,
      boolean isDetailed) {
      return getQuery().findDispoItemByAnnoationText(branch, setId, keyword, isDetailed);
   }

   @Override
   public List<DispoAnnotationData> getDispoAnnotations(BranchId branch, String itemId) {
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      return dispoItem.getAnnotationsList();
   }

   @Override
   public List<DispoAnnotationData> getDispoAnnotationsByType(Iterable<DispoAnnotationData> annotationData,
      String resolutionType) {
      List<DispoAnnotationData> resolutionTypeAnnotations = new ArrayList<>();
      for (DispoAnnotationData dad : annotationData) {
         if (dad.getResolutionType().equals(resolutionType)) {
            resolutionTypeAnnotations.add(dad);
         }
      }
      return resolutionTypeAnnotations;
   }

   @Override
   public List<String> getCheckedReruns(BranchId branch, String setId) {
      DispoSet set = getDispoSetById(branch, setId);
      HashMap<String, DispoItem> nameToItemMap = getItemsMap(branch, set);
      return getQuery().getCheckedReruns(nameToItemMap, setId);
   }

   @Override
   public DispoAnnotationData getDispoAnnotationById(BranchId branch, String itemId, String annotationId) {
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      List<DispoAnnotationData> annotationsList = dispoItem.getAnnotationsList();
      return DispoUtil.getById(annotationsList, annotationId);
   }

   @Override
   public boolean isUniqueProgramName(String name) {
      return getQuery().isUniqueProgramName(name);
   }

   @Override
   public boolean isUniqueItemName(BranchId branch, String setId, String name) {
      return getQuery().isUniqueItemName(branch, setId, name);
   }

   @Override
   public boolean isUniqueSetName(BranchId branch, String name) {
      return getQuery().isUniqueSetName(branch, name);
   }

   private void runOperation(BranchId branch, DispoSet setToEdit, DispoSetData newSet, boolean isIterative) {
      OperationReport report = new OperationReport();
      String operation = newSet.getOperation();
      if (operation.equals(DispoStrings.Operation_Import)) {
         try {
            HashMap<String, DispoItem> nameToItemMap = getItemsMap(branch, setToEdit);

            DispoImporterApi importer;

            if (setToEdit.getDispoType().equalsIgnoreCase(DispoStrings.CODE_COVERAGE)) {
               importer = importerFactory.createImporter(ImportFormat.LIS, dispoConnector);
            } else {
               importer = importerFactory.createImporter(ImportFormat.TMO, dispoConnector);
            }

            File importDirectory;

            if (gitlabApiPattern.matcher(setToEdit.getCoverageImportApi()).matches()) {
               importDirectory = importDataFromGit(branch, setToEdit, setToEdit.getCoverageImportApi(),
                  setToEdit.getCoveragePartition());
            } else if (gitlabApiPattern.matcher(setToEdit.getImportPath()).matches()) {
               importDirectory =
                  importDataFromGit(branch, setToEdit, setToEdit.getImportPath(), setToEdit.getCoveragePartition());
            } else if (setToEdit.getImportPath().contains("artifactory")) { //TODO: Remove after Artifactory is no longer in use.
               importDirectory = importDataArtifactory(branch, setToEdit, setToEdit.getImportPath());
            } else {
               importDirectory = new File(setToEdit.getImportPath());
            }
            boolean pathExists = checkIfPathExists(importDirectory, report);

            ArtifactReadable programConfig = orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(
               CoreArtifactTypes.CoverageProgram).andNameEquals("Coverage Config").asArtifactOrSentinel();

            List<DispoItem> itemsFromParse =
               importer.importDirectory(nameToItemMap, importDirectory, report, logger, programConfig);

            if (pathExists && itemsFromParse.isEmpty()) {
               report.addEntry(setToEdit.getImportPath(), "No file(s) found", DispoSummarySeverity.IGNORE);
            } else {
               report.addEntry("INFO", "CI Set: " + setToEdit.getCiSet(), DispoSummarySeverity.IGNORE);
               report.addEntry("INFO", "Dispo Type: " + setToEdit.getDispoType(), DispoSummarySeverity.IGNORE);
               report.addEntry("INFO", "Import State: " + setToEdit.getImportState(), DispoSummarySeverity.IGNORE);
            }

            List<DispoItem> itemsToCreate = new ArrayList<>();
            List<DispoItem> itemsToEdit = new ArrayList<>();

            for (DispoItem item : itemsFromParse) {
               // if the ID is non-empty then we are updating an item instead of creating a new one
               if (item.getGuid() == null) {
                  itemsToCreate.add(item);
                  report.addEntry(item.getName(), "", DispoSummarySeverity.NEW);
               } else {
                  itemsToEdit.add(item);
               }
            }

            if (!report.getStatus().isFailed()) {
               if (itemsToCreate.size() > 0) {
                  createDispoItems(branch, setToEdit.getGuid(), itemsToCreate);
               }
               if (itemsToEdit.size() > 0) {
                  editDispoItems(branch, setToEdit.getGuid(), itemsToEdit, true, "Import");
               }
            }

            updateAllDispoItems(branch, setToEdit.getGuid());

         } catch (Exception ex) {
            if (isIterative) {
               return;
            }
            throw new OseeCoreException(ex);
         }
      }

      // Create the Note to document the Operation
      List<Note> notesList = setToEdit.getNotesList();
      Note genOpNotes = generateOperationNotes(operation);
      notesList.add(generateOperationNotes("Import"));
      notesList.add(genOpNotes);
      newSet.setNotesList(notesList);
      newDate.setTime(System.currentTimeMillis());
      newSet.setTime(newDate);

      // Generate report
      getWriter().updateOperationSummary(branch, setToEdit.getGuid(), report);

      //Update Disposition Set
      getWriter().updateDispoSet(branch, setToEdit.getGuid(), newSet);

   }

   //TODO: Remove after Artifactory is no longer in use.
   private File importDataArtifactory(BranchId branch, DispoSet setId, String dataSource) {

      String folderPath = getLocalFolderPath(branch.getIdString(), setId.getIdString());
      File setFolder = new File(folderPath);
      File parent = setFolder.getParentFile();
      if (parent != null && !parent.exists() && !parent.mkdirs()) {
         throw new OseeStateException("Couldn't create dir: " + parent);
      }
      if (!setFolder.exists()) {
         setFolder.mkdirs();
      }

      String fullPath = "";

      fullPath = folderPath + getZipFileName(dataSource);
      downloadArtifactoryFile(dataSource, fullPath);

      getWriter().updateOrCreateServerImportPath(branch, setId, fullPath);

      return new File(fullPath);
   }

   private File importDataFromGit(BranchId branch, DispoSet setId, String apiCall, String partitionName) {

      String folderPath = getLocalFolderPath(branch.getIdString(), setId.getIdString());
      File setFolder = new File(folderPath);
      File parent = setFolder.getParentFile();
      if (parent != null && !parent.exists() && !parent.mkdirs()) {
         throw new OseeStateException("Couldn't create dir: " + parent);
      }
      if (!setFolder.exists()) {
         setFolder.mkdirs();
      }

      String fullPath = "";

      fullPath = folderPath + partitionName;
      downloadGitLabFile(apiCall, fullPath, partitionName);

      getWriter().updateOrCreateServerImportPath(branch, setId, fullPath);

      return new File(fullPath);
   }

   private String getLocalFolderPath(String branchId, String setId) {
      String basePath = orcsApi.getSystemProperties().getValue(OseeClient.OSEE_APPLICATION_SERVER_DATA);
      return basePath + File.separator + "coverage" + File.separator + branchId + File.separator + setId + File.separator;
   }

   private String getZipFileName(String artifactoryUrl) {
      int lastSlashIndex = artifactoryUrl.lastIndexOf('/');
      int dotZipIndex = artifactoryUrl.lastIndexOf(".zip");

      if (lastSlashIndex != -1 && dotZipIndex != -1 && lastSlashIndex < dotZipIndex) {
         return artifactoryUrl.substring(lastSlashIndex + 1, dotZipIndex);
      } else {
         return "";
      }
   }

   //TODO: Remove after Artifactory is no longer in use.
   private StringBuilder downloadArtifactoryFile(String artifactoryUrl, String downloadLocation) {
      StringBuilder response = new StringBuilder();
      String personalAccessToken = getArtifactoryPersonalAccessToken();

      try {
         if (!artifactoryUrl.endsWith(".zip")) {
            throw new OseeCoreException("Artifactory Item is not a .zip");
         }
         URL repositoryUrl = new URL(artifactoryUrl);

         HttpURLConnection connection = (HttpURLConnection) repositoryUrl.openConnection();
         connection.addRequestProperty("X-JFrog-Art-Api", personalAccessToken);
         connection.setRequestProperty("Connection", "keep-alive");
         connection.setRequestMethod("GET");
         connection.connect();
         String fileZip = String.format("%s.zip", downloadLocation);
         File uploadedZip = new File(fileZip);

         try (InputStream stream = connection.getInputStream();
            OutputStream outStream = new FileOutputStream(uploadedZip);) {

            byte[] buffer = stream.readAllBytes();
            stream.close();

            outStream.write(buffer);
            outStream.close();

            connection.disconnect();

            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            File unzipLocation = new File(downloadLocation);

            Zip.decompressStream(zis, buffer, unzipLocation);
         }
      } catch (Exception ex) {
         throw new OseeCoreException("Artifactory Operation Failed", ex);
      }
      return response;
   }

   private StringBuilder downloadGitLabFile(String gitlabUrl, String downloadLocation, String partitionName) {
      StringBuilder response = new StringBuilder();
      String personalAccessToken = String.format("Bearer %s", getGitlabPersonalAccessToken());
      String fullGitlabUrl = gitlabUrl;

      if (!gitlabUrl.contains("https")) {
         fullGitlabUrl = String.format("%s%s", getGitlabBaseApiUrl(), gitlabUrl);
      }

      try {
         URL repositoryUrl = new URL(fullGitlabUrl);

         HttpURLConnection connection = (HttpURLConnection) repositoryUrl.openConnection();
         connection.addRequestProperty("Authorization", personalAccessToken);
         connection.setRequestProperty("Connection", "keep-alive");
         connection.setRequestMethod("GET");
         connection.connect();
         String fileZip = String.format("%s%svcast.zip", downloadLocation, File.separator);

         try (InputStream stream = connection.getInputStream();) {
            File unzipLocation = new File(downloadLocation);
            Zip.decompressStream(stream, unzipLocation.getParentFile(), partitionName + "/vcast");

            connection.disconnect();
            stream.close();

            File vcastDir = new File(String.format("%s%svcast", downloadLocation, File.separator));
            if (vcastDir.exists()) {
               Zip.compressDirectory(vcastDir, fileZip, true);
            }
         }
      } catch (Exception ex) {
         throw new OseeCoreException("Gitlab Operation Failed", ex);
      }
      return response;
   }

   private String getGitlabPersonalAccessToken() {
      ArtifactReadable gitlabConfig =
         orcsApi.getQueryFactory().fromBranch(atsApi.getAtsBranch()).andId(AtsArtifactToken.GitlabConfig).asArtifact();
      return gitlabConfig.getSoleAttributeAsString(CoreAttributeTypes.GeneralStringData);
   }

   private String getGitlabBaseApiUrl() {
      ArtifactReadable gitlabConfig =
         orcsApi.getQueryFactory().fromBranch(atsApi.getAtsBranch()).andId(AtsArtifactToken.GitlabConfig).asArtifact();
      return gitlabConfig.getSoleAttributeAsString(CoreAttributeTypes.ContentUrl);
   }

   //TODO: Remove after Artifactory is no longer in use.
   private String getArtifactoryPersonalAccessToken() {
      ArtifactReadable artifactoryConfig = orcsApi.getQueryFactory().fromBranch(atsApi.getAtsBranch()).andId(
         AtsArtifactToken.ArtifactoryConfig).asArtifact();
      return artifactoryConfig.getSoleAttributeAsString(CoreAttributeTypes.GeneralStringData);
   }

   private boolean checkIfPathExists(File file, OperationReport report) {
      boolean ret = true;

      if (!file.canRead()) {
         report.addEntry(file.getAbsolutePath(), "No Read Access", DispoSummarySeverity.IGNORE);
      }
      if (!file.canWrite()) {
         report.addEntry(file.getAbsolutePath(), "No Write Access", DispoSummarySeverity.IGNORE);
      }

      if (!file.exists() && !file.isDirectory()) {
         ret = false;
         boolean isRealPath = false;
         String filePath = file.getAbsolutePath();

         while (!isRealPath && filePath.contains(File.separator)) {
            int end = filePath.lastIndexOf(File.separator);
            StringBuilder pathName = new StringBuilder();
            pathName.append(filePath.substring(0, end));
            filePath = pathName.toString();
            if (new File(filePath).isDirectory()) {
               isRealPath = true;
            }
         }
         if (isRealPath) {
            report.addEntry(file.getAbsolutePath(),
               "No file or directory was found. Closest path found at: " + filePath, DispoSummarySeverity.IGNORE);
         } else {
            report.addEntry(file.getAbsolutePath(), "No file or directory was found.", DispoSummarySeverity.IGNORE);
         }
      } else {
         report.addEntry(file.getAbsolutePath(), "The Import Path/File Does Exist", DispoSummarySeverity.IGNORE);
      }
      return ret;
   }

   private List<DispoItem> massDisposition(BranchId branch, String setId, List<String> itemIds, String resolutionType,
      String resolution) {
      List<DispoItem> toEdit = new ArrayList<>();

      List<DispoItem> allItemsInSet = getDispoItems(branch, setId);
      for (DispoItem item : allItemsInSet) {
         if (itemIds.contains(item.getGuid())) {
            DispoItemData newItem = new DispoItemData();
            newItem.setGuid(item.getGuid());
            newItem.setName(item.getName());

            List<DispoAnnotationData> newAnnotations = new ArrayList<>();
            newAnnotations = item.getAnnotationsList();
            for (DispoAnnotationData annotation : item.getAnnotationsList()) {
               if (DispoUtil.isAnnotationValueBlank(annotation)) {
                  if (annotation.getIsParentPair()) {
                     continue;
                  }

                  DispoAnnotationData newAnnotation =
                     new DispoAnnotationData(annotation, resolutionType, resolution, true, false, true);

                  dispoConnector.connectAnnotation(newAnnotation, item.getDiscrepanciesList());
                  newAnnotations.set(newAnnotation.getIndex(), newAnnotation);

                  //If it is an MCDC Pair Row and a pair is satisfied, the parent of that pair is satisfied.
                  if (newAnnotation.getIsPairAnnotation()) {
                     DispoAnnotationData parentAnnotation = getParentAnnotation(branch, item.getGuid(), newAnnotation);
                     if (!parentAnnotation.getIsResolutionValid()) {
                        String newParentResolutionType = resolutionType;
                        String newParentResolution = resolution;

                        List<String> satisfiedPairs = new ArrayList<>();
                        for (int pairNumber : newAnnotation.getPairedWith()) {
                           String pairLoc = String.format("%s.%d", parentAnnotation.getLocationRefs(), pairNumber);
                           DispoAnnotationData pairAnnotation = DispoUtil.getByLocation(newAnnotations, pairLoc);
                           if (pairAnnotation.getIsResolutionValid()) {
                              if (newAnnotation.getRow() < pairNumber) {
                                 satisfiedPairs.add(String.format("%s/%s", newAnnotation.getRow(), pairNumber));
                              } else {
                                 satisfiedPairs.add(String.format("%s/%s", pairNumber, newAnnotation.getRow()));
                              }
                              if (!pairAnnotation.getResolutionType().equals(newParentResolutionType)) {
                                 newParentResolutionType =
                                    newParentResolutionType + "/" + pairAnnotation.getResolutionType();
                              }
                              if (!pairAnnotation.getResolution().equals(newParentResolution)) {
                                 newParentResolution = newParentResolution + "/" + pairAnnotation.getResolution();
                              }
                           }
                        }
                        if (!satisfiedPairs.isEmpty()) {
                           DispoAnnotationData newParentAnnotation =
                              new DispoAnnotationData(parentAnnotation, newParentResolutionType, newParentResolution,
                                 String.format("Pairs Satisfied By: %s", satisfiedPairs.toString()), true, false, true);
                           dispoConnector.connectAnnotation(newParentAnnotation, item.getDiscrepanciesList());
                           newAnnotations.set(newParentAnnotation.getIndex(), newParentAnnotation);
                        }
                     }
                  }
               }
            }
            newItem.setAnnotationsList(newAnnotations);
            newItem.setDiscrepanciesList(item.getDiscrepanciesList());
            newItem.setStatus(dispoConnector.getItemStatus(newItem));
            toEdit.add(newItem);
         }
      }
      return toEdit;
   }

   private DispoAnnotationData getParentAnnotation(BranchId branch, String itemId, DispoAnnotationData pairAnnotation) {
      DispoAnnotationData parentAnnotation = DispoAnnotationData.SENTINEL;

      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      List<DispoAnnotationData> annotationsList = dispoItem.getAnnotationsList();

      if (pairAnnotation.getIsPairAnnotation() && pairAnnotation.getIsResolutionValid()) {
         String[] splitPairLoc = pairAnnotation.getLocationRefs().split("\\.", 3);
         if (splitPairLoc.length >= 2) {
            String parentLoc = String.format("%s.%s", splitPairLoc[0], splitPairLoc[1]);
            parentAnnotation = DispoUtil.getByLocation(annotationsList, parentLoc);
         }
      }

      return parentAnnotation;
   }

   private HashMap<String, DispoItem> getItemsMap(BranchId branch, DispoSet set) {
      HashMap<String, DispoItem> toReturn = new HashMap<>();
      List<DispoItem> dispoItems = getDispoItems(branch, set.getGuid());
      for (DispoItem item : dispoItems) {
         toReturn.put(item.getName(), item);
      }
      return toReturn;
   }

   private Note generateOperationNotes(String operation) {
      Note operationNote = new Note();
      newDate.setTime(System.currentTimeMillis());
      operationNote.setDateString(newDate.toString());
      operationNote.setType("SYSTEM");
      operationNote.setContent(operation);
      return operationNote;
   }

   private List<DispoAnnotationData> removeAnnotationFromList(List<DispoAnnotationData> oldList, int indexRemoved) {
      List<DispoAnnotationData> newList = new ArrayList<>();
      oldList.remove(indexRemoved);

      // Re assign index to Annotations still left in list
      int newIndex = 0;
      for (DispoAnnotationData annotation : oldList) {
         annotation.setIndex(newIndex);
         newList.add(newIndex, annotation);
         newIndex++;
      }
      return newList;
   }

   @Override
   public void copyDispoSetCoverage(BranchId sourceBranch, Long sourceCoverageUuid, BranchId destBranch,
      String destSetId, CopySetParams params) {
      Map<String, ArtifactReadable> coverageUnits = getQuery().getCoverageUnits(sourceBranch, sourceCoverageUuid);
      List<DispoItem> destItems = getDispoItems(destBranch, destSetId);

      OperationReport report = new OperationReport();

      CoverageAdapter coverageAdapter = new CoverageAdapter(dispoConnector);
      List<DispoItem> copyData = coverageAdapter.copyData(coverageUnits, destItems, report);

      String operation =
         String.format("Copy From Legacy Coverage - Branch [%s] and Source Set [%s]", sourceBranch, sourceCoverageUuid);
      if (!copyData.isEmpty()) {
         editDispoItems(destBranch, destSetId, copyData, false, operation);
         storage.updateOperationSummary(destBranch, destSetId, report);
      }
   }

   @Override
   public void copyDispoSet(BranchId branch, String destSetId, BranchId sourceBranch, String sourceSetId,
      CopySetParams params) {

      if (branch == sourceBranch && destSetId == sourceSetId) {
         return;
      }

      boolean wasUpdated = false;

      DispoSet destDispoSet = getQuery().findDispoSetsById(branch, destSetId);

      if (destDispoSet.getDispoType().equalsIgnoreCase(DispoStrings.CODE_COVERAGE)) {
         DispoConfig dispoConfig = getDispoConfig(branch);
         // If the the validResolutions is empty the copyAllDispositions will
         Set<String> validResolutions = new HashSet<>();
         dispoConfig.getValidResolutions().forEach(res -> {
            validResolutions.add(res.getValue());
         });

         List<DispoItem> sourceItems = getDispoItems(sourceBranch, sourceSetId);
         Map<String, Set<DispoItemData>> namesToDestItems = new HashMap<>();
         for (DispoItem itemArt : getDispoItems(branch, destSetId)) {
            DispoItemData itemData = DispoUtil.itemArtToItemData(itemArt, true, true);

            String name = itemData.getName();
            Set<DispoItemData> itemsWithSameName = namesToDestItems.get(name);
            if (itemsWithSameName == null) {
               Set<DispoItemData> set = new HashSet<>();
               set.add(itemData);
               namesToDestItems.put(name, set);
            } else {
               itemsWithSameName.add(itemData);
               namesToDestItems.put(name, itemsWithSameName);
            }
         }
         HashMap<String, String> reruns = new HashMap<>();
         Map<String, DispoItem> namesToToEditItems = new HashMap<>();
         OperationReport report = new OperationReport();

         DispoSetCopier copier = new DispoSetCopier(dispoConnector);
         if (!params.getAnnotationParam().isNone()) {
            List<DispoItem> copyResults = copier.copyAllDispositions(namesToDestItems, sourceItems, true, reruns,
               params.getAllowOnlyValidResolutionTypes(), validResolutions, false, report);
            for (DispoItem item : copyResults) {
               namesToToEditItems.put(item.getName(), item);
            }
         }

         copier.copyCategories(namesToDestItems, sourceItems, namesToToEditItems, params.getCategoryParam());
         copier.copyAssignee(namesToDestItems, sourceItems, namesToToEditItems, params.getAssigneeParam());
         copier.copyNotes(namesToDestItems, sourceItems, namesToToEditItems, params.getNoteParam());

         String operation = String.format("Copy Set from Program [%s] and Set [%s] to branch [%s] set [%s]",
            sourceBranch, sourceSetId, branch, destSetId);
         if (!namesToToEditItems.isEmpty() && !report.getStatus().isFailed()) {
            editDispoItems(branch, destSetId, namesToToEditItems.values(), false, operation);
            storage.updateOperationSummary(branch, destSetId, report);
         }
         storeRerunData(branch, destSetId, reruns);
         updateAllDispoItems(branch, destSetId);
      } else {
         TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(branch,
            String.format("Merge CI Dispositions from branch [%s] set [%s] to branch [%s] set [%s]", sourceBranch,
               sourceSetId, branch, destSetId));

         List<String> destItemNames = new ArrayList<>();
         List<ArtifactReadable> sourceItemArtifacts = getQuery().findItemArtifacts(sourceBranch, sourceSetId);
         List<ArtifactReadable> destItemArtifacts = getQuery().findItemArtifacts(branch, destSetId);
         ArtifactReadable setArtifact = getQuery().findSetArtifact(branch, destSetId);

         for (ArtifactReadable destItemArt : destItemArtifacts) {
            destItemNames.add(destItemArt.getName());
         }

         for (ArtifactReadable sourceItemArt : sourceItemArtifacts) {
            if (destItemNames.contains(sourceItemArt.getName())) {
               continue;
            }
            wasUpdated = true;

            ArtifactToken copiedItem = tx.createArtifact(setArtifact, CoreArtifactTypes.DispositionableItem,
               sourceItemArt.getSoleAttributeValue(CoreAttributeTypes.Name));

            for (AttributeTypeToken attributeTypeToken : sourceItemArt.getExistingAttributeTypes()) {
               if (copiedItem.isAttributeTypeValid(attributeTypeToken)) {
                  if (attributeTypeToken.isEnumerated() && sourceItemArt.getSoleAttributeValue(
                     attributeTypeToken) == null) {
                     tx.setSoleAttributeValue(copiedItem, attributeTypeToken, "");
                  } else {
                     tx.setSoleAttributeValue(copiedItem, attributeTypeToken,
                        sourceItemArt.getSoleAttributeValue(attributeTypeToken, orcsApi.tokenService().getAttributeType(
                           attributeTypeToken).getBaseAttributeTypeDefaultValue()));
                  }
               }
            }
         }

         if (wasUpdated) {
            tx.commit();
         }

      }
   }

   private void storeRerunData(BranchId branch, String destSetId, HashMap<String, String> reruns) {
      StringBuilder sb = new StringBuilder();
      for (Entry<String, String> entry : reruns.entrySet()) {
         sb = sb.append(DispoStrings.SCRIPT_ENTRY);
         sb = sb.append(String.format(DispoStrings.SCRIPT_NAME, entry.getKey()));
         sb = sb.append(String.format(DispoStrings.SCRIPT_PATH, entry.getValue()));
         sb = sb.append(DispoStrings.IS_RUNNABLE);
         sb = sb.append(DispoStrings.SCRIPT_ENTRY_END);
      }

      DispoSetData dispoSetData = new DispoSetData();
      newDate.setTime(System.currentTimeMillis());
      dispoSetData.setTime(newDate);
      dispoSetData.setRerunList(DispoStrings.BATCH_RERUN_LIST + sb.toString() + DispoStrings.BATCH_RERUN_LIST_END);
      UserId author = getQuery().findUser();
      storage.updateDispoSet(branch, destSetId, dispoSetData);
   }

   @Override
   public DispoConfig getDispoConfig(BranchId branch) {
      return getQuery().findDispoConfig(branch);
   }

   @Override
   public DispoSet getDispoItemParentSet(BranchId branch, String itemId) {
      ArtifactId id = getQuery().getDispoItemParentSet(branch, itemId);
      return getDispoSetById(branch, id.getIdString());
   }

   @Override
   public HashMap<ArtifactReadable, BranchId> getCiSet(CiSetData setData) {
      return getQuery().getCiSet(setData);
   }

   @Override
   public String getDispoItemId(BranchId branch, String setId, String item) {
      return getQuery().getDispoItemId(branch, setId, item);
   }

   @Override
   public List<CiSetData> getAllCiSets() {
      return getQuery().getAllCiSets();
   }

   @Override
   public String createDispoDiscrepancy(BranchId branch, String itemId, Discrepancy discrepancy, String userName) {
      String idOfNewDiscrepancy = "";
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null) {
         Map<String, Discrepancy> discrepancyList = dispoItem.getDiscrepanciesList();

         idOfNewDiscrepancy = dataFactory.getNewId();
         discrepancy.setId(idOfNewDiscrepancy);

         if (discrepancy.getLocation() == null) {
            discrepancy.setLocation("");
         }
         if (discrepancy.getText() == null) {
            discrepancy.setText("");
         }
         discrepancyList.put(idOfNewDiscrepancy, discrepancy);

         DispoItemData newItem = new DispoItemData();
         newItem.setDiscrepanciesList(discrepancyList);
         newItem.setStatus(dispoConnector.getItemStatus(newItem));

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), newItem);
      }
      return idOfNewDiscrepancy;
   }

   @Override
   public void createDispoDiscrepancies(BranchId branch, String itemId, List<Discrepancy> discrepancies,
      String userName) {
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null) {
         Map<String, Discrepancy> discrepancyList = dispoItem.getDiscrepanciesList();
         Collection<DispoItem> dispoItems = new ArrayList<>();

         for (Discrepancy discrepancy : discrepancies) {
            String idOfNewDiscrepancy = "";
            idOfNewDiscrepancy = dataFactory.getNewId();
            discrepancy.setId(idOfNewDiscrepancy);

            if (discrepancy.getLocation() == null) {
               discrepancy.setLocation("");
            }
            if (discrepancy.getText() == null) {
               discrepancy.setText("");
            }
            discrepancyList.put(idOfNewDiscrepancy, discrepancy);

            DispoItemData newItem = new DispoItemData();
            newItem.setDiscrepanciesList(discrepancyList);
            newItem.setStatus(dispoConnector.getItemStatus(newItem));

            dispoItems.add(newItem);
         }

         UserId author = getQuery().findUser();

         String operation = String.format("Create Dispo Discrepancies in Program [%s], Item [%s]", branch, itemId);

         getWriter().updateDispoItems(branch, dispoItems, false, operation);
      }
   }

   @Override
   public boolean editDispoDiscrepancy(BranchId branch, String itemId, String discrepancyId, Discrepancy newDiscrepancy,
      String userName) {
      boolean wasUpdated = false;
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null) {
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         discrepanciesList.put(discrepancyId, newDiscrepancy);

         DispoItemData modifiedDispoItem = DispoUtil.itemArtToItemData(getDispoItemById(branch, itemId), true);
         modifiedDispoItem.setDiscrepanciesList(discrepanciesList);
         modifiedDispoItem.setStatus(dispoConnector.getItemStatus(modifiedDispoItem));

         try {
            Date date = DispoUtil.getTimestampOfFile(getFullFilePathFromDispoItemId(branch, itemId, dispoItem));
            modifiedDispoItem.setLastUpdate(date);
         } catch (Throwable ex) {
            throw new OseeCoreException(ex);
         }

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), modifiedDispoItem);

         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public void editDispoDiscrepancies(BranchId branch, String itemId, List<Discrepancy> discrepancies,
      String userName) {
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null) {
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();

         for (Discrepancy discrepancy : discrepancies) {
            discrepanciesList.put(discrepancy.getId(), discrepancy);
         }

         DispoItemData modifiedDispoItem = DispoUtil.itemArtToItemData(getDispoItemById(branch, itemId), true);
         modifiedDispoItem.setDiscrepanciesList(discrepanciesList);
         modifiedDispoItem.setStatus(dispoConnector.getItemStatus(modifiedDispoItem));

         try {
            Date date = DispoUtil.getTimestampOfFile(getFullFilePathFromDispoItemId(branch, itemId, dispoItem));
            modifiedDispoItem.setLastUpdate(date);
         } catch (Throwable ex) {
            throw new OseeCoreException(ex);
         }

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), modifiedDispoItem);
      }
   }

   @Override
   public boolean deleteDispoDiscrepancy(BranchId branch, String itemId, String discrepancyId, String userName) {
      boolean wasUpdated = false;
      DispoItem dispoItem = getQuery().findDispoItemById(branch, itemId);
      if (dispoItem != null) {
         Map<String, Discrepancy> discrepanciesList = dispoItem.getDiscrepanciesList();
         discrepanciesList.remove(discrepancyId);

         DispoItemData newItem = new DispoItemData();
         newItem.setDiscrepanciesList(discrepanciesList);
         newItem.setStatus(dispoConnector.getItemStatus(newItem));

         getWriter().updateDispoItem(branch, dispoItem.getGuid(), newItem);
         wasUpdated = true;
      }
      return wasUpdated;
   }

   @Override
   public String createDispoItem(BranchId branch, CiItemData data, String userName) {
      DispoItemData dispoItemData = new DispoItemData();
      dispoItemData.setName(data.getScriptName());
      dispoItemData.setGuid(dataFactory.getNewId());
      dispoItemData.setDiscrepanciesAsRanges(data.getTestPoints().getFail());
      dispoItemData.setDiscrepanciesList(new HashMap<String, Discrepancy>());
      dispoItemData.setAnnotationsList(data.getAnnotations());

      DispoSet parentSet = getQuery().findDispoSetsById(branch, data.getSetData().getDispoSetId());
      if (parentSet != null) {
         getWriter().createDispoItem(branch, parentSet, dispoItemData);
      }
      return dispoItemData.getGuid();
   }

   @Override
   public BranchId demoDbInit() {
      BranchId baselineBranch = getWriter().createDispoBaseline();

      if (baselineBranch.isValid()) {
         String Resolution_Methods =
            "RESOLUTION_METHODS=[\r\n    {\r\n" + "        \"text\": \"\",\r\n" + "        \"value\": \"\",\r\n" + "        \"isDefault\": false\r\n" + "    },\r\n" + "   {\r\n" + "        \"text\": \"Test Script\",\r\n" + "        \"value\": \"Test_Script\",\r\n" + "        \"isDefault\": false\r\n" + "    },\r\n" + "    {\r\n" + "        \"text\": \"Exception_Handling\",\r\n" + "        \"value\": \"Exception_Handling\",\r\n" + "        \"isDefault\": true\r\n" + "    },\r\n" + "    {\r\n" + "        \"text\": \"Analysis\",\r\n" + "        \"value\": \"Analysis\",\r\n" + "        \"isDefault\": false\r\n" + "    },\r\n" + "    {\r\n" + "        \"text\": \"Defensive_Programming\",\r\n" + "        \"value\": \"Defensive_Programming\",\r\n" + "        \"isDefault\": false\r\n" + "    },\r\n" + "    {\r\n" + "        \"text\": \"Modify_Tooling\",\r\n" + "        \"value\": \"Modify_Tooling\",\r\n" + "        \"isDefault\": false\r\n" + "    }\r\n" + "]";
         TransactionBuilder tx =
            orcsApi.getTransactionFactory().createTransaction(baselineBranch, "Create Config File");
         ArtifactToken progConfig = tx.createArtifact(CoreArtifactTypes.GeneralData, "Program Config");
         tx.createAttribute(progConfig, CoreAttributeTypes.GeneralStringData, Resolution_Methods);
         tx.commit();
      }

      BranchId demoBranch = orcsApi.getQueryFactory().branchQuery().andNameEquals("Dispo Demo").getOneOrSentinel();

      if (demoBranch.isValid()) {
         orcsApi.getBranchOps().deleteBranch(demoBranch);
      }

      demoBranch = getWriter().createDispoProgram("Dispo Demo");

      List<DispoAnnotationData> annotations = new ArrayList<>();
      DispoAnnotationData annotation1 = new DispoAnnotationData();
      annotation1.setLocationRefs("1");
      annotation1.setResolutionType("Defensive_Programming");
      annotation1.setResolution("Resolution_1");
      annotation1.setIsResolutionValid(true);
      annotation1.setIndex(0);
      annotation1.setCustomerNotes("int i = 1");
      annotations.add(annotation1);

      DispoAnnotationData annotation2 = new DispoAnnotationData();
      annotation2.setLocationRefs("2");
      annotation2.setResolutionType("Defensive_Programming");
      annotation2.setResolution("Resolution_2");
      annotation2.setIsResolutionValid(true);
      annotation2.setIndex(1);
      annotation2.setCustomerNotes("int i = 2");
      annotations.add(annotation2);

      DispoAnnotationData annotation3 = new DispoAnnotationData();
      annotation3.setLocationRefs("3");
      annotation3.setResolutionType("");
      annotation3.setResolution("");
      annotation3.setIsResolutionValid(false);
      annotation3.setIndex(2);
      annotation3.setCustomerNotes("int i = 3");
      annotations.add(annotation3);

      TransactionBuilder tx = orcsApi.getTransactionFactory().createTransaction(demoBranch, "Create Set and Items");

      ArtifactToken dispoSet = tx.createArtifact(CoreArtifactTypes.DispositionSet, "Dispo_Demo_Set");
      tx.createAttribute(dispoSet, DispoOseeTypes.CoverageConfig, "codeCoverage");
      tx.setSoleAttributeFromString(dispoSet, CoreAttributeTypes.CoverageImportPath, "//path/to/vcast");

      ArtifactToken dispoItem = tx.createArtifact(CoreArtifactTypes.DispositionableItem, "Dispo_Demo_Item");
      tx.createAttribute(dispoItem, CoreAttributeTypes.CoverageAssignee, "Sentinel");
      tx.setSoleAttributeFromString(dispoItem, DispoOseeTypes.DispoItemVersion, "VER1");
      tx.setSoleAttributeFromString(dispoItem, CoreAttributeTypes.CoverageStatus, "INCOMPLETE");
      tx.setSoleAttributeFromString(dispoItem, CoreAttributeTypes.CoverageAnnotationsJson,
         JsonUtil.toJson(annotations));

      tx.addChild(dispoSet, dispoItem);

      tx.commit();

      return demoBranch;
   }

}
