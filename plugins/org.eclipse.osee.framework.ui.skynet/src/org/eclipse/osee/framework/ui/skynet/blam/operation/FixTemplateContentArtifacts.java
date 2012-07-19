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
package org.eclipse.osee.framework.ui.skynet.blam.operation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osee.framework.core.client.ClientSessionManager;
import org.eclipse.osee.framework.core.client.server.HttpUrlBuilderClient;
import org.eclipse.osee.framework.core.data.OseeServerContext;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.exception.OseeExceptions;
import org.eclipse.osee.framework.core.util.HttpProcessor;
import org.eclipse.osee.framework.core.util.HttpProcessor.AcquireResult;
import org.eclipse.osee.framework.database.core.IOseeStatement;
import org.eclipse.osee.framework.jdk.core.util.Lib;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.ui.skynet.blam.AbstractBlam;
import org.eclipse.osee.framework.ui.skynet.blam.VariableMap;
import org.eclipse.osee.framework.ui.skynet.internal.Activator;
import org.eclipse.osee.framework.ui.skynet.internal.ServiceUtil;
import org.w3c.dom.Element;

/**
 *
 * Looking for bad sequence:
 * <pre>
 * 0xEF 0xBF 0xBD
 * </pre>
 *
 * @author Jeff C. Phillips
 * @author Theron Virgin
 *
 */
public class FixTemplateContentArtifacts extends AbstractBlam {

	//@formatter:off
	private static final String GET_TRANS_DETAILS =
			"SELECT attr.gamma_id, attr.uri, tx_d.* " +
			"FROM osee_txs txs, osee_tx_details tx_d, osee_attribute attr " +
			"WHERE txs.gamma_id = attr.gamma_id " +
			"AND txs.branch_id = tx_d.branch_id " +
			"AND tx_d.transaction_id = txs.transaction_id " +
			"AND attr.attr_type_id = ? " +
			"AND attr.uri IS NOT NULL";
	//@formatter:on

	private final Collection<String> badData = new LinkedList<String>();

	@Override
	public void runOperation(VariableMap variableMap, IProgressMonitor monitor) throws Exception {
		ArrayList<AttrData> attrDatas = loadAttrData();
		int totalAttrs = attrDatas.size();

		monitor.beginTask("Fix word template content", totalAttrs);

		for (int i = 0; !monitor.isCanceled() && i < attrDatas.size(); i++) {
			AttrData attrData = attrDatas.get(i);

			Resource resource = getResource(attrData.getUri());

			try {
				boolean foundOffenderByteSeq = false;
				for (int byteIndex = 0; byteIndex < resource.backingBytes.length; byteIndex++) {

					//problem - dont be short or at the end
					foundOffenderByteSeq = //
							(resource.backingBytes[byteIndex] == (byte) 0xEF) && //
							(resource.backingBytes[byteIndex+1] == (byte) 0xBF) && //
							(resource.backingBytes[byteIndex+2] == (byte) 0xBD);

					if (foundOffenderByteSeq) {
						String offender = new String( new byte[] { resource.backingBytes[byteIndex], resource.backingBytes[byteIndex+1], resource.backingBytes[byteIndex+2] }, "UTF-8");
						log("Offender: " + offender);
						log("Found a hit: " + resource.resourceName);

						int beginIndex = resource.data.indexOf(offender);
						int maxIndex = beginIndex + 10 < resource.data.length() ? beginIndex + 10 : resource.data.length()-1;
						beginIndex = beginIndex < 10 ? 0 : beginIndex-10;

						log("Reduced Chunk:\n " + resource.data.substring(beginIndex, maxIndex));

						log("\nIntroducing transaction:");
						log(attrData.comment);
						break;
					}
				}

			} catch (Exception ex) {
				badData.add(attrData.gammaId);
				OseeLog.logf(Activator.class, Level.SEVERE, "Skiping File %s, %s because of exception %s", attrData.getGammaId(), attrData.getUri(),
						ex);
			}

		}
	}

	private ArrayList<AttrData> loadAttrData() throws OseeCoreException {
		ArrayList<AttrData> attrData = new ArrayList<AttrData>();

		IOseeStatement chStmt = ServiceUtil.getOseeDatabaseService().getStatement();
		try {
			chStmt.runPreparedQuery(GET_TRANS_DETAILS, ServiceUtil.getIdentityService().getLocalId(CoreAttributeTypes.WordTemplateContent));
			while (chStmt.next()) {
				String comment = String.format("author: %s, time: %s, osee_comment: %s, branch_id: %s, transaction_id %s",
						chStmt.getString("author"), chStmt.getString("time"), chStmt.getString("osee_comment"), chStmt.getString("branch_id"), chStmt.getString("transaction_id"));
				attrData.add(new AttrData(chStmt.getString("gamma_id"), chStmt.getString("uri"), comment));
			}
		} finally {
			chStmt.close();
		}
		return attrData;
	}

	private static void uploadResource(String gammaId, Resource resource) throws Exception {
		String fileName = resource.resourceName;
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("sessionId", ClientSessionManager.getSessionId());
		parameterMap.put("is.overwrite.allowed", String.valueOf(true));
		parameterMap.put("protocol", "attr");
		parameterMap.put("seed", gammaId);

		String extension = Lib.getExtension(fileName);
		if (Strings.isValid(extension)) {
			parameterMap.put("extension", extension);
			int charToRemove = extension.length() + 1;
			fileName = fileName.substring(0, fileName.length() - charToRemove);
		}
		parameterMap.put("name", fileName);

		byte[] toUpload = resource.data.getBytes(resource.encoding);
		if (resource.wasZipped) {
			toUpload = Lib.compressStream(new ByteArrayInputStream(toUpload), resource.entryName);
		}

		String urlString =
				HttpUrlBuilderClient.getInstance().getOsgiServletServiceUrl(OseeServerContext.RESOURCE_CONTEXT, parameterMap);
		HttpProcessor.put(new URL(urlString), new ByteArrayInputStream(toUpload), resource.result.getContentType(),
				resource.result.getEncoding());
	}

	private Resource getResource(String resourcePath) throws OseeCoreException {
		Resource toReturn = null;
		ByteArrayOutputStream sourceOutputStream = new ByteArrayOutputStream();
		try {
			Map<String, String> parameterMap = new HashMap<String, String>();
			parameterMap.put("sessionId", ClientSessionManager.getSessionId());
			parameterMap.put("uri", resourcePath);
			String urlString =
					HttpUrlBuilderClient.getInstance().getOsgiServletServiceUrl(OseeServerContext.RESOURCE_CONTEXT,
							parameterMap);

			AcquireResult result = HttpProcessor.acquire(new URL(urlString), sourceOutputStream);
			if (result.getCode() == HttpURLConnection.HTTP_OK) {
				toReturn = new Resource(resourcePath, result, sourceOutputStream.toByteArray());
			}
		} catch (Exception ex) {
			OseeExceptions.wrapAndThrow(ex);
		} finally {
			try {
				sourceOutputStream.close();
			} catch (IOException ex) {
				OseeExceptions.wrapAndThrow(ex);
			}
		}
		return toReturn;
	}

	private final class Resource {
		private final String entryName;
		private final String resourceName;
		private final AcquireResult result;
		private final byte[] rawBytes;
		private final boolean wasZipped;
		private final String sourcePath;

		public byte[] backingBytes;
		private String data;
		private String encoding;

		private Resource(String sourcePath, AcquireResult result, byte[] rawBytes) throws IOException {
			this.rawBytes = rawBytes;
			this.result = result;
			int index = sourcePath.lastIndexOf('/');
			this.sourcePath = sourcePath;
			this.resourceName = sourcePath.substring(index + 1, sourcePath.length());
			this.wasZipped = result.getContentType().contains("zip");
			if (wasZipped) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				this.entryName = decompressStream(new ByteArrayInputStream(rawBytes), outputStream);
				this.encoding = "UTF-8";
				this.backingBytes = outputStream.toByteArray();
				this.data = new String(backingBytes, encoding);
			} else {
				this.backingBytes = rawBytes;
				this.data = new String(rawBytes, result.getEncoding());
				this.entryName = null;
				this.encoding = result.getEncoding();
			}
		}
	}

	private static String decompressStream(InputStream inputStream, OutputStream outputStream) throws IOException {
		String zipEntryName = null;
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(inputStream);
			ZipEntry entry = zipInputStream.getNextEntry();
			zipEntryName = entry.getName();
			// Transfer bytes from the ZIP file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = zipInputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, len);
			}
		} finally {
			if (zipInputStream != null) {
				zipInputStream.close();
			}
		}
		return zipEntryName;
	}

	//To handle the case of sub-sections
	private boolean isBadParagraph(Element paragraph) {
		boolean badParagraph = false;
		String content = paragraph.getTextContent();
		if (content != null && content.contains("LISTNUM \"listreset\"")) {
			badParagraph = true;
		}

		return badParagraph;
	}

	@Override
	public String getXWidgetsXml() {
		return AbstractBlam.emptyXWidgetsXml;
	}

	class AttrData {
		private final String gammaId;
		private final String uri;
		private final String comment;

		public AttrData(String gammaId, String uri, String comment) {
			this.gammaId = gammaId;
			this.uri = uri;
			this.comment = comment;
		}

		public String getGammaId() {
			return gammaId;
		}

		public String getUri() {
			return uri;
		}
	}

	@Override
	public Collection<String> getCategories() {
		return Arrays.asList("Admin.Health");
	}
}
