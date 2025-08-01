/*********************************************************************
 * Copyright (c) 2016 Boeing
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
package org.eclipse.osee.orcs.rest.internal;

import static org.eclipse.osee.orcs.rest.model.transaction.TransferTupleTypes.ExportedBranch;
import static org.eclipse.osee.orcs.rest.model.transaction.TransferTupleTypes.TransferFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.OseeClient;
import org.eclipse.osee.framework.core.data.TransactionId;
import org.eclipse.osee.framework.core.enums.CoreBranches;
import org.eclipse.osee.framework.jdk.core.result.XResultData;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.type.Pair;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Zip;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.data.TransactionReadable;
import org.eclipse.osee.orcs.rest.model.transaction.BranchLocation;
import org.eclipse.osee.orcs.rest.model.transaction.TransactionBuilderData;
import org.eclipse.osee.orcs.rest.model.transaction.TransactionTransferManifest;
import org.eclipse.osee.orcs.rest.model.transaction.TransferBranch;
import org.eclipse.osee.orcs.rest.model.transaction.TransferDBType;
import org.eclipse.osee.orcs.rest.model.transaction.TransferOpType;
import org.eclipse.osee.orcs.rest.model.transaction.TransferTransaction;
import org.eclipse.osee.orcs.search.Operator;
import org.eclipse.osee.orcs.search.TupleQuery;
import org.eclipse.osee.orcs.transaction.TransactionBuilder;

public class TransferDataStoreImpl {
   private final OrcsApi orcsApi;
   private final TupleQuery tupleQuery;
   private final TransactionEndpointImpl transEndpoint;
   private final TransactionTransferManifest transferManifest = new TransactionTransferManifest();
   private final TransactionTransferManifest regenTransferManifest = new TransactionTransferManifest();
   private final HashMap<TransactionId, TransactionId> regenMap = new HashMap<>();
   public static String transferDir = "transfers";
   StringBuilder strdebug = new StringBuilder("");
   public TransferDataStoreImpl(TransactionEndpointImpl transEndpoint, OrcsApi orcsApi) {
      this.transEndpoint = transEndpoint;
      this.orcsApi = orcsApi;
      this.tupleQuery = orcsApi.getQueryFactory().tupleQuery();
   };

   public final XResultData transferTransactions(TransactionId exportId, XResultData results, boolean regen,
      String mode) {
      List<TransactionReadable> txrs = new ArrayList<>();
      List<BranchLocation> branchLocations = branchLocations(exportId);
      String currentTransferDirectory = transferDirectoryHelper(exportId);

      TransactionId maxTransactionNum = TransactionId.SENTINEL;

      if (regen) {
         results = readRegenFile(exportId);
         if (results.isFailed()) {
            return results;
         }
      }

      if (!branchLocations.isEmpty()) {
         for (BranchLocation branchLoc : branchLocations) {
            BranchId branchId = branchLoc.getBranchId();
            TransactionId txId = branchLoc.getBaseTxId();
            if (regen) {
               //Reset the prev Transaction on each branch
               TransferBranch branch = regenTransferManifest.getTransferBranch(branchId);
               txId = branch.getPrevTx();

               if (txId != TransactionId.SENTINEL) {
                  branchLoc.setBaseTxId(txId);
                  branchLoc.setUniqueTxId(branch.getUniqueTx());
               } else {
                  results.error("Failed to read prev tx from manifest file. ");
                  break;
               }
            }

            int iniTxId = txId.getIdIntValue();
            txrs = orcsApi.getQueryFactory().transactionQuery().andBranch(branchId).andTxId(Operator.GREATER_THAN,
               iniTxId).getResults().getList();

            if (txrs.size() < 1) {
               results.error(
                  "Not enough transactions to generate transfer files; Nothing has been updated since the last file was generated.");
               break;
            } else {
               maxTransactionNum = txrs.get(txrs.size() - 1);
               if (maxTransactionNum == txId || maxTransactionNum == TransactionId.SENTINEL) {
                  results.error(
                     "During generating transfer files, ending (max) transaction is empty or equivalent to starting (base) transaction");
                  break;
               } else {
                  applyTransactions(txrs, branchLoc, results, branchId, currentTransferDirectory, maxTransactionNum);
               }
            }
         }
      }

      if (results.getErrorCount() == 0) {
         strdebug.append("Begin attempt to add data to tuple table.");
         try {
            // for each branch, do each transfer row
            boolean docommit = false;
            TransactionBuilder txTupleTransfer =
               orcsApi.getTransactionFactory().createTransaction(CoreBranches.COMMON, "Adding transfer tuples");
            for (TransferBranch tb : transferManifest.getTransferBranches()) {
               for (TransferTransaction transTx : tb.getTxList()) {
                  if (regenMap.get(transTx.getSourceTransId()) != null) {
                     continue;
                  }
                  txTupleTransfer.addTuple4(TransferFile, tb.getBranchId(), transTx.getTransferOp(),
                     transTx.getSourceTransId(), transTx.getSourceUniqueTrans());
                  docommit = true;
               }
            }
            // Once transaction process we apply and commit (add tuples from transferFileRows) to Database
            // Commit the Transactions in the transfer
            if (docommit) {
               txTupleTransfer.commit();
            }

            strdebug.append("Tuple Table Data Added!");
            String buildId = "10000";
            // Build Manifest File from ManifestData Container see design doc for additional info
            buildManifestFileHelper(currentTransferDirectory, buildId, exportId, results, maxTransactionNum);

            try {
               String osee = orcsApi.getSystemProperties().getValue(
                  OseeClient.OSEE_APPLICATION_SERVER_DATA) + File.separator + transferDir + File.separator;
               File directoryPath = new File(currentTransferDirectory);
               Path pathTest = Paths.get(currentTransferDirectory);
               String zipTarget = osee + currentTransferDirectory.replace(osee, "").replace("\\", "") + ".zip";
               strdebug.append(
                  "Attemtping to compress files to location: " + zipTarget + " File(s)/Directory(ies) from: " + directoryPath.toString());
               if (Files.exists(pathTest)) {
                  Zip.compressDirectory(directoryPath, zipTarget, true);
                  String filename =
                     zipTarget.split(transferDir)[zipTarget.split(transferDir).length - 1].replace("\\", "");
                  results.log(String.format("Transfer file %s is successfully generated.", filename));
               }
            } catch (Exception e) {
               results.error("Transfer file generation content could not be compressed error: " + e.getMessage());
               e.printStackTrace();
            }
         } catch (Exception ex) {
            results.errorf("%s", "Error in addding tuples exception: ", ex.getMessage());
         }
      }

      if (mode != null && mode.toLowerCase().contains("debug")) {
         System.out.println("\nFinal Results Log:\n" + results.toString());
         System.out.println(strdebug.toString());
      }

      return results;
   }

   private XResultData applyTransactions(List<TransactionReadable> txrs, BranchLocation branchLoc, XResultData results,
      BranchId branchId, String currentTransferDirectory, TransactionId endTrans) {
      Collections.sort(txrs, new TransactionIdComparator());
      if (!txrs.isEmpty() && endTrans != TransactionId.SENTINEL) {
         try {
            buildJSONExportDataHelper(txrs, branchLoc, endTrans, branchId, currentTransferDirectory, results);
         } catch (JsonProcessingException ex) {
            results.errorf("%s", String.format("Error in JSON Build: ", ex.getMessage()));
         }
      }
      return results;
   }

   // Query Branch Base Transaction based off supplied branch given
   // For each branch configured in the export ID return the maximum base transaction ID for that branch.
   private final List<BranchLocation> branchLocations(TransactionId exportId) {
      List<BranchLocation> branchLocations = new ArrayList<>();
      List<TransferDBType> transferTypes = new ArrayList<>();
      tupleQuery.getTuple4E2E3E4FromE1(ExportedBranch, CoreBranches.COMMON, exportId, (E2, E3, E4) -> {
         BranchLocation bl = new BranchLocation();
         bl.setBranchId(E2);
         bl.setBaseTxId(E3);
         // for original branch location, set the unique tx to the source tx, since both dbs have it
         bl.setUniqueTxId(E3);
         branchLocations.add(bl);
         transferTypes.add(E4);
      });
      return getMaxBranchLocations(branchLocations);
   }

   // Set base tx to max
   private final List<BranchLocation> getMaxBranchLocations(List<BranchLocation> branchLocations) {
      List<BranchLocation> bLocs = new ArrayList<>();
      Map<BranchId, Pair<TransactionId, TransactionId>> Hmap = new HashMap<>();
      for (BranchLocation branchLoc : branchLocations) {
         // Check each branch and retrieve highest tx
         if (Hmap.containsKey(branchLoc.getBranchId())) {
            TransactionId test = Hmap.get(branchLoc.getBranchId()).getFirst();
            if (test.isLessThan(branchLoc.getBaseTxId())) {
               Hmap.replace(branchLoc.getBranchId(),
                  new Pair<TransactionId, TransactionId>(branchLoc.getBaseTxId(), branchLoc.getUniqueTxId()));
            }
         } else {
            Hmap.put(branchLoc.getBranchId(),
               new Pair<TransactionId, TransactionId>(branchLoc.getBaseTxId(), branchLoc.getUniqueTxId()));
         }
         List<BranchLocation> fileBranchLocs = new ArrayList<>();
         tupleQuery.getTuple4E2E3E4FromE1(TransferFile, CoreBranches.COMMON, branchLoc.getBranchId(), (E2, E3, E4) -> {
            BranchLocation bl = new BranchLocation();
            bl.setBranchId(branchLoc.getBranchId());
            bl.setBaseTxId(E3);
            bl.setUniqueTxId(E4);
            fileBranchLocs.add(bl);
         });
         for (BranchLocation bl : fileBranchLocs) {
            if (Hmap.containsKey(bl.getBranchId())) {
               TransactionId test = Hmap.get(bl.getBranchId()).getFirst();
               if (test.isLessThan(bl.getBaseTxId())) {
                  Hmap.replace(bl.getBranchId(),
                     new Pair<TransactionId, TransactionId>(bl.getBaseTxId(), bl.getUniqueTxId()));
               }
            } else {
               Hmap.put(bl.getBranchId(), new Pair<TransactionId, TransactionId>(bl.getBaseTxId(), bl.getUniqueTxId()));
            }
         }
      }
      for (Map.Entry<BranchId, Pair<TransactionId, TransactionId>> entry : Hmap.entrySet()) {
         BranchLocation bl = new BranchLocation();
         bl.setBranchId(entry.getKey());
         bl.setBaseTxId(entry.getValue().getFirst());
         bl.setUniqueTxId(entry.getValue().getSecond());
         bLocs.add(bl);
      }
      return bLocs;
   }

   private boolean verifyValidTransactionToTransferHelper(String transactionComment) {
      String[] commentsToCheckAgainst = {
         "Adding transfer tuples",
         "User - Save Settings (IDE)",
         "Workflow Editor - Save",
         "Adding exportId to Tuple2Table",
         "Delete tuple",
         "Adding tuple for transfer init"};
      for (int i = 0; i <= commentsToCheckAgainst.length - 1; i++) {
         if (commentsToCheckAgainst[i].equalsIgnoreCase(transactionComment.strip())) {
            return false;
         }
      }
      return true;
   }

   private String transferDirectoryHelper(TransactionId exportId) {
      // Transfer File Naming Convention: OSEETransfer-YYYYMMDDhhmmss-#### where #### is a random to make the file unique.
      Random rand = new Random();
      String randomFour = String.format("%04d", rand.nextInt(10000));
      SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMddHHmmss");
      Date transferDateAndTime = new Date();
      String osee = orcsApi.getSystemProperties().getValue(
         OseeClient.OSEE_APPLICATION_SERVER_DATA) + File.separator + transferDir + File.separator;

      return osee + "OSEETransfer-" + exportId.toString() + "-" + formatter.format(
         transferDateAndTime) + "-" + randomFour + File.separator;
   }

   private XResultData buildJSONExportDataHelper(List<TransactionReadable> txrs, BranchLocation branchLoc,
      TransactionId lastTx, BranchId branchId, String transferDirectory, XResultData results)
      throws JsonProcessingException {
      TransactionBuilderData transBldData = null;
      String json = null;
      TransferBranch tb = new TransferBranch(branchId);
      tb.setPrevTx(branchLoc.getBaseTxId());
      tb.setUniqueTx(branchLoc.getUniqueTxId());
      TransactionId txInProcess = tb.getPrevTx();
      for (TransactionReadable tx : txrs) {
         boolean validTxToProcess = verifyValidTransactionToTransferHelper(tx.getComment());
         //
         TransactionId uniqueTx = regenMap.get(TransactionId.valueOf(tx.getId()));
         if (uniqueTx == null) {
            uniqueTx = TransactionId.valueOf(Lib.generateUuid());
         }

         String fileName = tx.getIdString() + ".json";
         String path = transferDirectory + branchId + File.separator + fileName;
         if (validTxToProcess) {
            TransferTransaction transTx = new TransferTransaction(branchId, tx, uniqueTx, TransferOpType.EMPTY);
            try {
               transBldData = transEndpoint.exportTxsDiff(txInProcess, tx); // This is the JSON send this mapper to return as a string
               txInProcess = tx;
            } catch (OseeCoreException ex) {
               // Store Tuple Data into transferFilRows
               results.error(ex.getLocalizedMessage());
               tb.addTransferTransaction(transTx);
               continue;
            }
            if (transBldData != null && !transBldData.isFailed()) {
               ObjectMapper mapper = new ObjectMapper();
               json = mapper.writeValueAsString(transBldData);
               // Attempt to create file / directory structure if they do not exist
               try {
                  Path directoryPath = Paths.get(transferDirectory + branchId + File.separator);
                  if (!Files.exists(directoryPath)) {
                     Files.createDirectories(directoryPath);
                  }
                  File file = new File(path);
                  if (!file.exists()) {
                     file.createNewFile();
                  }
                  // Use OSEE Lib to write to file
                  Lib.writeStringToFile(json, file);
                  transTx.setTransferOp(TransferOpType.ADD);
                  tb.addTransferTransaction(transTx);
               } catch (Exception ex) {
                  results.errorf("%s",
                     String.format("Error in writing file: ", ex.getMessage() + " location: " + path));
               }
            } else {
               // tbd is null or failed, write an empty transaction
               if (transBldData != null && transBldData.isFailed()) {
                  results.setWarningCount(results.getWarningCount() + 1);
                  results.log(transBldData.getResults());
                  results.log(transBldData.getMessage());
               }
               tb.addTransferTransaction(transTx);
               txInProcess = tx; // get the next difference from the delta from this one
            }
         }
      }
      transferManifest.addTransferBranch(tb);
      return results;
   }

   private XResultData buildManifestFileHelper(String transferDirectory, String buildId, TransactionId exportId,
      XResultData results, TransactionId maxTransaction) {
      Map<String, ArrayList<String>> Hmap = new HashMap<String, ArrayList<String>>();
      // File Output String
      StringBuilder fileOutput = new StringBuilder();
      // New Line
      String newLine = "\n";
      // MD |
      String markdownPipe = " | ";
      String markdownSeparator = "| ------------- |:-----------------:| ------------------:| :-----:|";
      String filePath = transferDirectory + "manifest.md";
      String dirName = "";
      String fileName = "";
      String opType = "";
      // Check if build id not null
      if (buildId != null) {
         fileOutput.append("BuildID: ").append(buildId).append(newLine);
         fileOutput.append("ExportID: ").append(exportId.toString()).append(newLine);
         List<TransferBranch> transBranches = transferManifest.getTransferBranches();
         if (transBranches.size() > 0) {
            fileOutput.append("| BranchId").append(markdownPipe).append("LocalTX").append(markdownPipe).append(
               "UniqueTX").append(markdownPipe).append("Op").append(markdownPipe).append(newLine).append(
                  markdownSeparator).append(newLine);
            for (TransferBranch tb : transBranches) {
               // write prev tx to manifest
               // first row is the previous transaction
               fileOutput.append(markdownPipe).append(tb.getBranchId()).append(markdownPipe).append(
                  tb.getPrevTx().getIdString()).append(markdownPipe).append(tb.getUniqueTx().getIdString()).append(
                     markdownPipe).append(TransferOpType.PREV_TX.toString()).append(markdownPipe).append(newLine);
               for (TransferTransaction transTx : tb.getTxList()) {
                  opType = transTx.getTransferOp().toString();
                  fileOutput.append(markdownPipe).append(tb.getBranchId()).append(markdownPipe).append(
                     transTx.getSourceTransId().getIdString()).append(markdownPipe).append(
                        transTx.getSourceUniqueTrans().getIdString()).append(markdownPipe).append(opType).append(
                           markdownPipe).append(newLine);
                  dirName = tb.getBranchId().toString();
                  fileName = transTx.getSourceTransId().getIdString() + ".json";
                  if (Hmap.containsKey(dirName)) {
                     Hmap.get(dirName).add(fileName);
                  } else {
                     Hmap.put(dirName, new ArrayList<String>());
                     Hmap.get(dirName).add(fileName);
                  }
               }
            }
         }
         fileOutput.append("\nList of Directories/Contents\n");
         for (Map.Entry<String, ArrayList<String>> entry : Hmap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
               fileOutput.append(newLine).append(" * ").append(entry.getKey()).append(newLine);
               String files = String.join(",", entry.getValue().toString());
               try {
                  files = "\n \t * " + files.replaceAll(",", "\n \t *");
                  files = files.replaceAll("[\\[\\](){}]", "");
               } catch (Exception ex) {
                  results.errorf("%s", "Error in string replace. Exception: ", ex.getMessage());
               }
               fileOutput.append(files).append(newLine);
            }
         }
         try {
            Path directoryPath = Paths.get(transferDirectory);
            if (!Files.exists(directoryPath)) {
               Files.createDirectories(directoryPath);
            }
            File file = new File(filePath);
            if (!file.exists()) {
               file.createNewFile();
            }
            // Use OSEE Lib to write to file
            Lib.writeStringToFile(fileOutput.toString(), file);
         } catch (Exception ex) {
            results.errorf("%s", "Error in writting manifest file. Exception: ", ex.getMessage());
         }
      }

      return results;
   }

   public List<String> getFileList(String strexportId, String path) {
      try {
         File directoryPath = new File(path);
         FilenameFilter filefilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               String lowercaseName = name.toLowerCase();
               if (!lowercaseName.endsWith(".zip")) {
                  return false;
               }

               if (strexportId == null) {
                  return true;
               }

               if (lowercaseName.contains(strexportId)) {
                  return true;
               }
               return false;
            }
         };

         return (Arrays.asList(directoryPath.list(filefilter)));
      } catch (Exception ex) {
         return null;
      }
   }

   public XResultData getTransferFileList(String strexportId) {
      XResultData results = new XResultData();
      try {
         String transferPath = orcsApi.getSystemProperties().getValue(
            OseeClient.OSEE_APPLICATION_SERVER_DATA) + File.separator + TransferDataStoreImpl.transferDir + File.separator;

         List<String> filelist = getFileList(strexportId, transferPath);
         if (filelist.size() > 0) {
            results.setInfoCount(filelist.size());
            results.setIds(filelist);
         }
      } catch (Exception ex) {
         results.error(ex.getMessage());
      }
      return results;
   }

   public String getLatestTransferFile(String strexportId) {
      String transferPath = orcsApi.getSystemProperties().getValue(
         OseeClient.OSEE_APPLICATION_SERVER_DATA) + File.separator + TransferDataStoreImpl.transferDir;

      List<String> filelist = getFileList(strexportId, transferPath);
      ArrayList<File> files = new ArrayList<File>();
      for (String str : filelist) {
         files.add(new File(transferPath + File.separator + str));
      }
      Collections.sort(files, Comparator.comparing(File::lastModified).reversed());

      if (files.size() > 0) {
         return files.get(0).getAbsolutePath().replace(".zip", "");
      }
      return null;
   }

   private XResultData readRegenFile(TransactionId exportId) {
      XResultData results = new XResultData();

      String filepath = getLatestTransferFile(exportId.getIdString());

      try {
         results = regenTransferManifest.parse(filepath);
         if (results.isFailed()) {
            return results;
         }
         regenTransferManifest.getTransactionMapping(regenMap);
      } catch (Exception ex) {
         results.errorf("%s", String.format("Error during reading latest export transfer file: ", ex.getMessage()));
      }

      return results;
   }

   private static final class TransactionIdComparator implements Comparator<TransactionId> {
      @Override
      public int compare(TransactionId arg0, TransactionId arg1) {
         return arg0.getId().compareTo(arg1.getId());
      }
   };
}