/*********************************************************************
 * Copyright (c) 2017 Boeing
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

package org.eclipse.osee.framework.ui.skynet.render.word;

import static org.eclipse.osee.framework.core.enums.CoreAttributeTypes.Partition;
import static org.eclipse.osee.framework.core.enums.CoreAttributeTypes.SeverityCategory;
import static org.eclipse.osee.framework.core.enums.CoreAttributeTypes.WordTemplateContent;
import static org.eclipse.osee.framework.core.enums.CoreBranches.COMMON;
import static org.eclipse.osee.framework.core.enums.DeletionFlag.EXCLUDE_DELETED;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.osee.define.api.publishing.datarights.DataRightContentBuilder;
import org.eclipse.osee.define.api.publishing.datarights.DataRightResult;
import org.eclipse.osee.framework.core.data.ApplicabilityId;
import org.eclipse.osee.framework.core.data.ApplicabilityToken;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.data.AttributeTypeId;
import org.eclipse.osee.framework.core.data.AttributeTypeToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.enums.BranchType;
import org.eclipse.osee.framework.core.enums.CoreArtifactTypes;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.CoreRelationTypes;
import org.eclipse.osee.framework.core.enums.DataRightsClassification;
import org.eclipse.osee.framework.core.enums.PresentationType;
import org.eclipse.osee.framework.core.model.Branch;
import org.eclipse.osee.framework.core.publishing.AttributeOptions;
import org.eclipse.osee.framework.core.publishing.MetadataOptions;
import org.eclipse.osee.framework.core.publishing.PublishingTemplateInsertTokenType;
import org.eclipse.osee.framework.core.publishing.RendererOption;
import org.eclipse.osee.framework.core.publishing.WordCoreUtil;
import org.eclipse.osee.framework.core.publishing.WordMLProducer;
import org.eclipse.osee.framework.jdk.core.type.OseeArgumentException;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Message;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.jdk.core.util.io.CharBackedInputStream;
import org.eclipse.osee.framework.jdk.core.util.xml.XmlEncoderDecoder;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.plugin.core.util.AIFile;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactHierarchyComparator;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactLoader;
import org.eclipse.osee.framework.skynet.core.artifact.Attribute;
import org.eclipse.osee.framework.skynet.core.artifact.BranchManager;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;
import org.eclipse.osee.framework.skynet.core.attribute.AttributeTypeManager;
import org.eclipse.osee.framework.skynet.core.transaction.SkynetTransaction;
import org.eclipse.osee.framework.skynet.core.word.WordCoreUtilClient;
import org.eclipse.osee.framework.ui.skynet.internal.ServiceUtil;
import org.eclipse.osee.framework.ui.skynet.render.MSWordTemplateClientRenderer;
import org.eclipse.osee.framework.ui.skynet.render.RendererManager;
import org.eclipse.osee.framework.ui.skynet.render.RenderingUtil;
import org.eclipse.osee.framework.ui.skynet.util.WordUiUtil;
import org.eclipse.osee.framework.ui.swt.Displays;
import org.eclipse.osee.orcs.rest.model.ApplicabilityEndpoint;
import org.eclipse.swt.program.Program;

/**
 * @author Robert A. Fisher
 * @author Jeff C. Phillips
 * @author Ryan D. Brooks
 * @author Andrew M. Finkbeiner
 * @author Branden W. Phillips
 * @author Loren K. Ashley
 * @link WordTemplateProcessorTest
 */
public class WordTemplateProcessor {

   private static final String LOAD_EXCLUDED_ARTIFACTIDS =
      "select art_id from osee_artifact art, osee_txs txs where art.gamma_id = txs.gamma_id and txs.branch_id = ? and txs.tx_current = 1 and not exists (select null from osee_tuple2 t2, osee_txs txsP where tuple_type = 2 and e1 = ? and t2.gamma_id = txsP.gamma_id and txsP.branch_id = ? and txsP.tx_current = 1 and e2 = txs.app_id)";
   private static final String ARTIFACT = "Artifact";
   private static final String ARTIFACT_TYPE = "Artifact Type";
   private static final Object ARTIFACT_ID = "Artifact Id";
   private static final String APPLICABILITY = "Applicability";
   private static final String NESTED_TEMPLATE = "NestedTemplate";
   public static final String STYLES = "<w:lists>.*?</w:lists><w:styles>.*?</w:styles>";

   private static final Program wordApp = Program.findProgram("doc");

   private String slaveTemplate;
   private String slaveTemplateOptions;
   private String slaveTemplateStyles;

   private String elementType;
   private String overrideClassification;
   private BranchId branch;

   //Outlining Options
   private AttributeTypeId headingAttributeType;
   private boolean outlining;
   private boolean outlineOnlyHeadersFolders = false;
   private boolean overrideOutlineNumber = false;
   private boolean recurseChildren;
   private String outlineNumber = null;
   private boolean templateFooter = false;
   private Boolean includeEmptyHeaders;

   //Attribute Options
   private String attributeLabel;
   private String attributeType;
   private String format;
   private String formatPre;
   private String formatPost;

   //Metadata Options
   private String metadataType;
   private String metadataLabel;
   private String metadataFormat;

   //Nested Template Options
   private String outlineType;
   private String sectionNumber;
   private String subDocName;
   private String key;
   private String value;
   private int nestedCount;

   private final List<AttributeOptions> attributeOptionsList = new LinkedList<>();
   private final List<MetadataOptions> metadataOptionsList = new LinkedList<>();
   final List<Artifact> nonTemplateArtifacts = new LinkedList<>();
   private final Set<Artifact> processedArtifacts = new HashSet<>();
   private final MSWordTemplateClientRenderer renderer;
   private boolean isDiff;
   private boolean excludeFolders;
   private CharSequence paragraphNumber = null;
   private final List<ArtifactTypeToken> excludeArtifactTypes = new LinkedList<>();
   private HashMap<ApplicabilityId, ApplicabilityToken> applicabilityTokens;
   private HashMap<ArtifactId, ArtifactId> artifactsToExclude;
   private final Set<ArtifactId> emptyFolders = new HashSet<>();

   public WordTemplateProcessor(MSWordTemplateClientRenderer renderer) {
      this.renderer = renderer;
      artifactsToExclude = new HashMap<>();
   }

   /**
    * Parse through template to find xml defining artifact sets and replace it with the result of publishing those
    * artifacts Only used by Publish SRS
    */
   public void publishWithNestedTemplates(Artifact masterTemplateArtifact, Artifact slaveTemplateArtifact, List<Artifact> artifacts) {
      nestedCount = 0;
      String masterTemplate = masterTemplateArtifact.getSoleAttributeValue(CoreAttributeTypes.WholeWordContent, "");
      String masterTemplateOptions =
         masterTemplateArtifact.getSoleAttributeValue(CoreAttributeTypes.RendererOptions, "");
      slaveTemplate = "";
      slaveTemplateOptions = "";
      isDiff = (boolean) renderer.getRendererOptionValue(RendererOption.PUBLISH_DIFF);
      renderer.setRendererOption(RendererOption.TEMPLATE_ARTIFACT, masterTemplateArtifact);

      slaveTemplateStyles = "";
      if (slaveTemplateArtifact != null) {
         renderer.setRendererOption(RendererOption.TEMPLATE_ARTIFACT, slaveTemplateArtifact);
         slaveTemplate = slaveTemplateArtifact.getSoleAttributeValue(CoreAttributeTypes.WholeWordContent, "");
         slaveTemplateOptions =
            slaveTemplateArtifact.getSoleAttributeValueAsString(CoreAttributeTypes.RendererOptions, "");

         List<Artifact> slaveTemplateRelatedArtifacts =
            slaveTemplateArtifact.getRelatedArtifacts(CoreRelationTypes.SupportingInfo_SupportingInfo);

         if (slaveTemplateRelatedArtifacts.size() == 1) {
            slaveTemplateStyles += slaveTemplateRelatedArtifacts.get(0).getSoleAttributeValueAsString(
               CoreAttributeTypes.WholeWordContent, "");
         } else {
            OseeLog.log(this.getClass(), Level.INFO,
               "More than one style relation currently not supported. Defaulting to styles defined in the template.");
         }
      }

      try {
         attributeOptionsList.clear();
         metadataOptionsList.clear();
         //JSONObject jsonObject = new JSONObject(masterTemplateOptions);
         ObjectMapper objMap = new ObjectMapper();
         JsonNode node = objMap.readTree(masterTemplateOptions);
         elementType = node.get("ElementType").asText();
         if (elementType.equals(ARTIFACT)) {
            parseAttributeOptions(masterTemplateOptions);
            parseMetadataOptions(masterTemplateOptions);
            parseOutliningOptions(masterTemplateOptions);
         }
      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }
      // Need to check if all attributes will be published.  If so set the AllAttributes option.
      // Assumes that all (*) will not be used when other attributes are specified
      renderer.setRendererOption(RendererOption.ALL_ATTRIBUTES, false);
      if (attributeOptionsList.size() == 1) {
         String attributeName = attributeOptionsList.get(0).getAttributeName();
         if (attributeName.equals("*")) {
            renderer.setRendererOption(RendererOption.ALL_ATTRIBUTES, true);
         }
      }
      List<Artifact> masterTemplateRelatedArtifacts =
         masterTemplateArtifact.getRelatedArtifacts(CoreRelationTypes.SupportingInfo_SupportingInfo);
      String masterTemplateStyles = "";

      int size = masterTemplateRelatedArtifacts.size();
      if (size == 0) {
         OseeLog.log(this.getClass(), Level.INFO, "No Artifacts supporting master template styles were found");
      } else if (size == 1) {
         masterTemplateStyles += masterTemplateRelatedArtifacts.get(0).getSoleAttributeValueAsString(
            CoreAttributeTypes.WholeWordContent, "");
      } else {
         OseeLog.log(this.getClass(), Level.INFO,
            "More than one style relation currently not supported. Defaulting to styles defined in the template.");
      }

      getExcludeArtifactTypes();

      var theMasterTemplateStyles = masterTemplateStyles;

      //@formatter:off
      RenderingUtil
         .getRenderFile
            (
               renderer,
               PresentationType.PREVIEW,
               null,
               "xml",
               COMMON.getShortName(),
               masterTemplateArtifact.getSafeName()
            )
         .map
            (
               ( iFile ) ->
               {
                  var inputStream =
                     this.applyTemplate
                        (
                          artifacts,
                          masterTemplate,
                          masterTemplateOptions,
                          theMasterTemplateStyles,
                          iFile.getParent(),
                          outlineNumber,
                          null,
                          PresentationType.PREVIEW,
                          null
                        );

                  AIFile.writeToFile( iFile, inputStream );

                  return iFile.getLocation();
               }
            )
         .flatMap( RenderingUtil::getOsString )
         .ifPresentOrElse
            (
               ( renderingFileAbsoultePath ) ->
               {
                  if( !( (boolean) renderer.getRendererOptionValue( RendererOption.NO_DISPLAY ) ) && !isDiff ) {

                     RenderingUtil.ensureFilenameLimit( renderingFileAbsoultePath );

                     wordApp.execute( renderingFileAbsoultePath );
                  }
               },
               () -> new OseeCoreException
                            (
                               new Message()
                                      .title( "WordTemplateProcessor::publishWithNestedTemplates, failed to write content file.")
                                      .indentInc()
                                      .segment( "Template", masterTemplateArtifact.getSafeName() )
                                      .toString()
                            )
            );
         ;
      //@formatter:on

   }

   /**
    * Takes a list of artifacts and loops through finding which headers should not be published. Takes an artifact and
    * loops through its' children. On each set of grandchildren, the method is called recursively.<br/>
    * <br/>
    * case1: Any MS Word Header that has only excluded children is not published<br/>
    * case2: Any MS Word Header that has only excluded children, but has included grandchildren, is published<br/>
    * case3: Non MS Word Header artifacts are still published even if all children/grandchildren are excluded<br/>
    * case4: Any MS Word Header that has no children is not published<br/>
    * case5: Any MS Word Header with only excluded header children, should not be published<br/>
    */
   public boolean isEmptyHeaders(List<Artifact> artifacts) {
      boolean hasIncludedChildren = false;
      boolean includeParent = false;
      List<Artifact> children = new LinkedList<>();
      for (Artifact artifact : artifacts) {
         if (!artifact.isHistorical()) {
            children = artifact.getChildren();
         }
         if (!children.isEmpty()) {
            hasIncludedChildren = isEmptyHeaders(children);
            if (!hasIncludedChildren) {
               if (artifact.isOfType(CoreArtifactTypes.HeadingMsWord)) {
                  emptyFolders.add(artifact);
               }
            }
         } else if (children.isEmpty() && artifact.isOfType(CoreArtifactTypes.HeadingMsWord)) {
            emptyFolders.add(artifact);
         }
         if (!isOfType(artifact, excludeArtifactTypes) && !artifact.isOfType(CoreArtifactTypes.HeadingMsWord)) {
            includeParent = true;
         }
         if (hasIncludedChildren) {
            includeParent = true;
         }
      }
      return includeParent;
   }

   private List<ArtifactTypeToken> getExcludeArtifactTypes() {
      excludeArtifactTypes.clear();

      Object o = renderer.getRendererOptionValue(RendererOption.EXCLUDE_ARTIFACT_TYPES);
      if (o instanceof Collection<?>) {
         Collection<?> coll = (Collection<?>) o;
         Iterator<?> iterator = coll.iterator();
         while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof ArtifactTypeToken) {
               excludeArtifactTypes.add((ArtifactTypeToken) next);
            }
         }

      }

      return excludeArtifactTypes;
   }

   /**
    * Parse through template to find xml defining artifact sets and replace it with the result of publishing those
    * artifacts. Only used by Publish SRS
    *
    * @param artifacts null if the template defines the artifacts to be used in the publishing
    * @param templateContent the publishing template Word Ml
    * @param templateOptions the publishing template JSON rendering options
    * @param templateStyles when non-<code>null</code> the publishing template styles will be replaced
    * @param folder null when not using an extension template
    * @param outlineNumber if null will find based on first artifact
    * @param presentationType
    * @param outputStream when non-<code>null</code> generated WordMl is appended to this {@link OutputStream}. When
    * null the Word ML is written to a buffer.
    * @return when <code>outputStream</code> is non-<code>null</code>, <code>null</code>; otherwise, an
    * {@link InputStream} that reads from the buffer the WordML was written to.
    */

   public InputStream applyTemplate(List<Artifact> artifacts, String templateContent, String templateOptions, String templateStyles, IContainer folder, String outlineNumber, String outlineType, PresentationType presentationType, OutputStream outputStream) {

      String overrideDataRights = (String) renderer.getRendererOptionValue(RendererOption.OVERRIDE_DATA_RIGHTS);
      overrideClassification = "invalid";
      if (DataRightsClassification.isValid(overrideDataRights)) {
         overrideClassification = overrideDataRights;
      }

      excludeFolders = (boolean) renderer.getRendererOptionValue(RendererOption.EXCLUDE_FOLDERS);

      if (artifacts.isEmpty()) {
         branch = BranchId.SENTINEL;
      } else {
         branch = artifacts.get(0).getBranch();
         Branch fullBranch = BranchManager.getBranch(branch);
         if (fullBranch.getBranchType().equals(BranchType.MERGE)) {
            fullBranch = fullBranch.getParentBranch();
         }
         ApplicabilityEndpoint applEndpoint = ServiceUtil.getOseeClient().getApplicabilityEndpoint(fullBranch);

         applicabilityTokens = new HashMap<>();
         Collection<ApplicabilityToken> appTokens = applEndpoint.getApplicabilityTokens();
         for (ApplicabilityToken token : appTokens) {
            applicabilityTokens.put(token, token);
         }
      }

      ArtifactId view = (ArtifactId) renderer.getRendererOptionValue(RendererOption.VIEW);
      artifactsToExclude = getNonApplicableArtifacts(artifacts, view == null ? ArtifactId.SENTINEL : view);

      WordMLProducer wordMlUnfinal = null;
      InputStream inputStream = null;
      BufferedWriter bufferedWriter = null;

      if (Objects.nonNull(outputStream)) {
         /*
          * An output stream was provided, write data to it.
          */
         var outputStreamWriter = new OutputStreamWriter(outputStream);
         bufferedWriter = new BufferedWriter(outputStreamWriter);
         wordMlUnfinal = new WordMLProducer(bufferedWriter);
      } else {
         /*
          * An output stream was not provided, write data to a buffer.
          */
         try {
            var charBak = new CharBackedInputStream();
            wordMlUnfinal = new WordMLProducer(charBak);
            inputStream = charBak;
         } catch (CharacterCodingException ex) {
            OseeCoreException.wrapAndThrow(ex);
         }
      }

      var wordMl = wordMlUnfinal;

      try {

         //TODO: change templateContent from Sting to CharSequence
         templateContent = WordCoreUtil.cleanupPageNumberTypeStart1(templateContent).toString();

         if (!templateStyles.isEmpty()) {
            templateContent = templateContent.replaceAll(STYLES, templateStyles);
         }

         //@formatter:off
         this.outlineNumber =
            Strings.isInValid( outlineNumber )
               ? peekAtFirstArtifactToGetParagraphNumber( templateContent, artifacts )
               : outlineNumber;
         //@formatter:on

         var cleanTemplateContent =
            WordCoreUtil.initializePublishingTemplateOutliningNumbers(this.outlineNumber, templateContent, outlineType);

         wordMl.setNextParagraphNumberTo(this.outlineNumber);

         var objectMapper = new ObjectMapper();
         var jsonNode = objectMapper.readTree(templateOptions);
         this.elementType = jsonNode.get("ElementType").asText();

         Consumer<CharSequence> segmentProcessor;

         //@formatter:off
         switch( this.elementType )
         {
            case WordTemplateProcessor.ARTIFACT:
            {
               this.parseOutliningOptions( templateOptions );

               segmentProcessor =
                  new Consumer<CharSequence> () {
                     @Override
                     public void accept( CharSequence segment ) {

                        wordMl.addWordMl( segment );

                        if(    ( presentationType == PresentationType.SPECIALIZED_EDIT )
                            && ( artifacts.size() == 1 ) ) {
                           // for single edit override outlining options
                           WordTemplateProcessor.this.outlining = false;
                        }

                        WordTemplateProcessor.this.processArtifactSet
                           (
                              templateOptions,
                              artifacts,
                              wordMl,
                              outlineType,
                              presentationType,
                              (ArtifactId) WordTemplateProcessor.this.renderer.getRendererOptionValue( RendererOption.VIEW )
                           );
                     }
                  };
            }
            break;

            case WordTemplateProcessor.NESTED_TEMPLATE:
            {
               segmentProcessor =
                  new Consumer<CharSequence> () {
                     @Override
                     public void accept( CharSequence segment ) {

                        wordMl.addWordMl( segment );

                        WordTemplateProcessor.this.parseNestedTemplateOptions
                           (
                              templateOptions,
                              folder,
                              wordMl,
                              presentationType
                           );

                     }
                  };
            }
            break;

            default:
            {
               throw new OseeArgumentException("Invalid ElementType [%s]", this.elementType );
            }
         }

         WordCoreUtil.processPublishingTemplate
            (
               cleanTemplateContent,
               segmentProcessor,
               ( tail ) ->
               {
                  var cleanFooterText = WordCoreUtil.cleanupFooter( tail );
                  wordMl.addWordMl( cleanFooterText );
               }
           );
         //@formatter:on

         displayNonTemplateArtifacts(nonTemplateArtifacts,
            "Only artifacts of type Word Template Content are supported in this case.");

      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      } finally {
         if (Objects.nonNull(outputStream) && Objects.nonNull(bufferedWriter)) {

            try {
               bufferedWriter.close();
               outputStream.close();
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
         }
      }

      return inputStream;
   }

   private void parseNestedTemplateOptions(String templateOptions, IContainer folder, WordMLProducer wordMl, PresentationType presentationType) {
      try {
         ObjectMapper objMap = new ObjectMapper();
         JsonNode jsonObject = objMap.readTree(templateOptions);
         JsonNode nestedTemplateOptions = jsonObject.findValue("NestedTemplates");
         JsonNode options = null;

         if (nestedCount < nestedTemplateOptions.size()) {
            options = nestedTemplateOptions.get(nestedCount);

            nestedCount++;
            outlineType = options.findValue("OutlineType").asText();
            if (outlineType.isEmpty()) {
               outlineType = null;
            }
            sectionNumber = options.findValue("SectionNumber").asText();
            subDocName = options.findValue("SubDocName").asText();
            key = options.findValue("Key").asText();
            // rendererOption is either ID or NAME
            RendererOption rendererOption = RendererOption.valueOf(key.toUpperCase());
            value = options.findValue("Value").asText();

            renderer.setRendererOption(rendererOption, value);

            String artifactName = (String) renderer.getRendererOptionValue(RendererOption.NAME);
            String artifactId = (String) renderer.getRendererOptionValue(RendererOption.ID);
            BranchId branch = (BranchId) renderer.getRendererOptionValue(RendererOption.BRANCH);
            List<Artifact> artifacts = null;

            if (Strings.isValid(artifactId)) {
               List<ArtifactId> artIds = Arrays.asList(ArtifactId.valueOf(artifactId));
               artifacts = ArtifactQuery.getArtifactListFrom(artIds, branch, EXCLUDE_DELETED);
            } else if (Strings.isValid(artifactName)) {
               artifacts = ArtifactQuery.getArtifactListFromName(artifactName, branch);
            }

            String subDocFileName = subDocName + ".xml";

            if (isDiff) {
               WordTemplateFileDiffer templateFileDiffer = new WordTemplateFileDiffer(renderer);
               if (artifacts != null) {
                  templateFileDiffer.generateFileDifferences(artifacts, "/results/" + subDocFileName, sectionNumber,
                     outlineType, recurseChildren);
               }
            } else {
               //@formatter:off
               var inputStream =
                  applyTemplate
                     (
                       artifacts,
                       slaveTemplate,
                       slaveTemplateOptions,
                       slaveTemplateStyles,
                       folder,
                       sectionNumber,
                       outlineType,
                       presentationType,
                       null
                     );


               IFile file = folder.getFile( new Path( subDocFileName ) );

               AIFile.writeToFile( file, inputStream );
               //@formatter:on
            }
            wordMl.createHyperLinkDoc(subDocFileName);
         }

      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }
   }

   private List<Artifact> parseOrcsQueryResult(String result, BranchId branch) {
      ArrayList<Artifact> artifacts = new ArrayList<>();
      try {
         ObjectMapper objMap = new ObjectMapper();

         JsonNode jsonObject = objMap.readTree(result);
         JsonNode results = jsonObject.findValue("results");
         if (results.size() >= 1) {
            JsonNode artifactIds = results.get(0).findValue("artifacts");
            JsonNode id = null;
            for (int i = 0; i < artifactIds.size(); i++) {
               id = artifactIds.get(i);
               ArtifactId artifactId = ArtifactId.valueOf(id.findValue("id").asLong());
               Artifact artifact = ArtifactQuery.getArtifactFromId(artifactId, branch, EXCLUDE_DELETED);
               artifacts.add(artifact);
            }
         }
      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }

      return artifacts;
   }

   private String findValue(JsonNode options, String key) {
      if (Objects.isNull(options)) {
         return "";
      }
      var textNode = options.findValue(key);
      if (Objects.isNull(textNode)) {
         return "";
      }
      var text = textNode.asText();
      return (Objects.nonNull(text)) ? text : "";
   }

   private void parseAttributeOptions(String templateOptions) {
      try {
         this.attributeOptionsList.clear();

         ObjectMapper objMapper = new ObjectMapper();
         JsonNode jsonObject = objMapper.readTree(templateOptions);
         JsonNode attributeOptions = jsonObject.findValue("AttributeOptions");
         JsonNode options = null;

         for (int i = 0; i < attributeOptions.size(); i++) {
            options = attributeOptions.get(i);

            attributeType = this.findValue(options, "AttrType");
            attributeLabel = this.findValue(options, "Label");
            format = this.findValue(options, "Format");
            formatPre = this.findValue(options, "FormatPre");
            formatPost = this.findValue(options, "FormatPost");

            if (attributeType.equals("*") || AttributeTypeManager.typeExists(attributeType)) {
               this.attributeOptionsList.add(
                  new AttributeOptions(attributeType, attributeLabel, format, formatPre, formatPost));
            }
         }
      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }
   }

   /**
    * <b>Outlining:</b> Whether or not to include various outlining elements on all artifacts, includes
    * headers/sectioning<br/>
    * <b>OutlineOnlyHeadersFolders:</b> Only outline the header and footer artifacts, this then excludes Requirements
    * from being outlined and treats them as content<br/>
    * <b>OverrideOutlineNumber:</b> The outline number/level will be manually computed at time of publish and set.
    * Useful for diffs when artifacts are processed separately<br/>
    * <b>RecurseChildren:</b> Recurse through the children of the artifacts being processed<br/>
    * <b>IncludeEmptyHeaders:</b> Headers without published children will not be included<br/>
    * <b>OutlineNumber:</b> The starting outline number for the document if included<br/>
    * <b>TemplateFooter:</b> Whether or not to process the footer of that artifact, or just use whatever is in the Word
    * Template Content or on the RendererTemplate<br/>
    * <b>HeadingAttributeType:</b> Which attribute type to use as the outlining header<br/>
    */
   private void parseOutliningOptions(String templateOptions) {
      try {
         ObjectMapper objMap = new ObjectMapper();
         JsonNode jsonObject = objMap.readTree(templateOptions);
         JsonNode attributeOptions = jsonObject.findValue("OutliningOptions");

         outlining = attributeOptions.findValue("Outlining").asBoolean();
         JsonNode outlineOnlyHeadersFolders = attributeOptions.findValue("OulineOnlyHeadersFolders");
         if (outlineOnlyHeadersFolders != null) {
            this.outlineOnlyHeadersFolders = outlineOnlyHeadersFolders.asBoolean();
         }
         JsonNode overrideOutlineNumber = attributeOptions.findValue("OverrideOutlineNumber");
         if (overrideOutlineNumber != null) {
            this.overrideOutlineNumber = overrideOutlineNumber.asBoolean();
         }
         recurseChildren = attributeOptions.findValue("RecurseChildren").asBoolean();
         JsonNode includeEmptyHeaders = attributeOptions.findValue("IncludeEmptyHeaders");
         if (includeEmptyHeaders != null) {
            this.includeEmptyHeaders = includeEmptyHeaders.asBoolean();
         }
         outlineNumber = attributeOptions.findValue("OutlineNumber").asText();
         JsonNode templateFooter = attributeOptions.findValue("TemplateFooter");
         if (templateFooter != null) {
            this.templateFooter = templateFooter.asBoolean();
         }
         String headingAttrType = attributeOptions.findValue("HeadingAttributeType").asText();
         headingAttributeType = AttributeTypeManager.getType(headingAttrType);
      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }
   }

   private void parseMetadataOptions(String metadataOptions) {
      try {
         ObjectMapper objMap = new ObjectMapper();
         JsonNode jsonObject = objMap.readTree(metadataOptions);

         if (!jsonObject.has("MetadataOptions")) {
            return;
         }

         JsonNode optionsArray = jsonObject.findValue("MetadataOptions");
         JsonNode options = null;
         for (int i = 0; i < optionsArray.size(); i++) {
            options = optionsArray.get(i);
            metadataType = options.findValue("Type").asText();
            metadataFormat = options.findValue("Format").asText();
            metadataLabel = options.findValue("Label").asText();

            this.metadataOptionsList.add(new MetadataOptions(metadataType, metadataFormat, metadataLabel));
         }
      } catch (IOException ex) {
         OseeCoreException.wrapAndThrow(ex);
      }
   }

   private String peekAtFirstArtifactToGetParagraphNumber(String templateContent, List<Artifact> artifacts) {

      var startParagraphNumber = "1";

      //@formatter:off
      if(    Objects.isNull(artifacts)
          || artifacts.isEmpty()
          || Objects.isNull( templateContent )
          || templateContent.isEmpty() ) {
         return startParagraphNumber;
      }
      //@formatter:on

      var firstArtifact = artifacts.get(0);

      if (!firstArtifact.isAttributeTypeValid(CoreAttributeTypes.ParagraphNumber)) {
         return startParagraphNumber;
      }

      if (!PublishingTemplateInsertTokenType.ARTIFACT.equals(WordCoreUtil.getInsertHereTokenType(templateContent))) {
         return startParagraphNumber;
      }

      var firstArtifactParagraphNumber = firstArtifact.getSoleAttributeValue(CoreAttributeTypes.ParagraphNumber, "");

      if (Strings.isValid(firstArtifactParagraphNumber)) {
         startParagraphNumber = firstArtifactParagraphNumber;
      }

      return startParagraphNumber;
   }

   private void processArtifactSet(String templateOptions, List<Artifact> artifacts, WordMLProducer wordMl, String outlineType, PresentationType presentationType, ArtifactId viewId) {
      nonTemplateArtifacts.clear();
      renderer.setRendererOption(RendererOption.VIEW, viewId == null ? ArtifactId.SENTINEL : viewId);

      if (Strings.isValid(outlineNumber)) {
         wordMl.setNextParagraphNumberTo(outlineNumber);
      }

      // Don't extract the settings from the template if already done.
      if (attributeOptionsList.isEmpty()) {
         parseAttributeOptions(templateOptions);
      }

      if (metadataOptionsList.isEmpty()) {
         parseMetadataOptions(templateOptions);
      }

      /**
       * EmptyHeaders can be set in the template RendererOptions, or via the rendererOptions (such as the Publish with
       * Specified Template Blam). Via Template's RendererOptions takes priority, if set, this will check to see if the
       * option to exclude empty headers was set to false. If true, won't run at all regardless of rendererOptions. If
       * template does not set the option, will check to see if set via rendererOptions, again, if set to false it will
       * run, if true it will not run.
       */
      if (includeEmptyHeaders != null) {
         if (!includeEmptyHeaders) {
            isEmptyHeaders(artifacts);
         }
      } else if (renderer.isRendererOptionSet(RendererOption.PUBLISH_EMPTY_HEADERS)) {
         if (!(boolean) renderer.getRendererOptionValue(RendererOption.PUBLISH_EMPTY_HEADERS)) {
            isEmptyHeaders(artifacts);
         }
      }

      if ((boolean) renderer.getRendererOptionValue(RendererOption.PUBLISH_DIFF)) {
         WordTemplateFileDiffer templateFileDiffer = new WordTemplateFileDiffer(renderer);
         templateFileDiffer.generateFileDifferences(artifacts, "/results/", outlineNumber, outlineType,
            recurseChildren);
      } else {

         List<ArtifactId> allArtifacts = new ArrayList<>();
         if (recurseChildren || (boolean) renderer.getRendererOptionValue(
            RendererOption.RECURSE_ON_LOAD) && !((boolean) renderer.getRendererOptionValue(
               RendererOption.ORIG_PUBLISH_AS_DIFF))) {
            for (Artifact art : artifacts) {
               if (!allArtifacts.contains(art)) {
                  allArtifacts.add(art);
               }
               if (!art.isHistorical()) {
                  for (Artifact descendant : art.getDescendants()) {
                     if (!allArtifacts.contains(descendant) && !descendant.isHistorical() && isWordTemplateContentValid(
                        descendant) && isArtifactIncluded(descendant)) {
                        allArtifacts.add(descendant);
                     }
                  }
               }
            }
         } else {
            allArtifacts.addAll(artifacts);
         }

         if (!allArtifacts.isEmpty()) {

            DataRightResult dataRightResult = ServiceUtil.getOseeClient().getDataRightsEndpoint().getDataRights(branch,
               overrideClassification, allArtifacts);

            var dataRightContentBuilder = new DataRightContentBuilder(dataRightResult);

            for (Artifact artifact : artifacts) {
               processObjectArtifact(artifact, wordMl, outlineType, presentationType, dataRightContentBuilder);
            }
         }

         WordUiUtil.getStoredResultData();
      }
      // maintain a list of artifacts that have been processed so we do not
      // have duplicates.
      processedArtifacts.clear();
   }

   private HashMap<ArtifactId, ArtifactId> getNonApplicableArtifacts(List<Artifact> artifacts, ArtifactId view) {
      HashMap<ArtifactId, ArtifactId> toReturn = new HashMap<>();

      if (artifacts != null && !artifacts.isEmpty()) {
         Object[] objs = {branch, view, branch};

         if (view.isValid()) {
            List<ArtifactId> excludedArtifacts = ArtifactLoader.selectArtifactIds(LOAD_EXCLUDED_ARTIFACTIDS, objs, 300);
            for (ArtifactId artId : excludedArtifacts) {
               toReturn.put(artId, artId);
            }
         }
      }

      return toReturn;
   }

   private void processObjectArtifact(Artifact artifact, WordMLProducer wordMl, String outlineType, PresentationType presentationType, DataRightContentBuilder dataRightContentBuilder) {
      if (isWordTemplateContentValid(artifact)) {
         // If the artifact has not been processed
         if (!processedArtifacts.contains(artifact)) {
            boolean publishInline = artifact.getSoleAttributeValue(CoreAttributeTypes.PublishInline, false);
            boolean startedSection = false;
            boolean templateOnly = (boolean) renderer.getRendererOptionValue(RendererOption.TEMPLATE_ONLY);
            boolean headerOrFolder =
               artifact.isOfType(CoreArtifactTypes.HeadingMsWord) || artifact.isOfType(CoreArtifactTypes.Folder);
            boolean includeOutline = templateOnly ? false : (outlineOnlyHeadersFolders ? headerOrFolder : true);

            boolean includeUUIDs = (boolean) renderer.getRendererOptionValue(RendererOption.INCLUDE_UUIDS);

            if (isArtifactIncluded(artifact)) {
               if (outlining && includeOutline) {
                  String headingText = artifact.getSoleAttributeValue(headingAttributeType, "");

                  if (includeUUIDs) {
                     String UUIDtext = String.format(" <UUID = %s>", artifact.getIdString());
                     headingText = headingText.concat(UUIDtext);
                  }

                  Boolean mergeTag = (Boolean) renderer.getRendererOptionValue(RendererOption.ADD_MERGE_TAG);
                  if (mergeTag != null && mergeTag) {
                     headingText = headingText.concat(" [MERGED]");
                  }

                  if (!publishInline) {
                     if (overrideOutlineNumber) {
                        paragraphNumber = startOutlineSubSectionOverride(wordMl, artifact, headingText);
                     } else {
                        paragraphNumber = wordMl.startOutlineSubSection("Times New Roman", headingText, outlineType);
                     }
                     startedSection = true;
                  }

                  if (paragraphNumber == null) {
                     paragraphNumber = wordMl.startOutlineSubSection();
                     startedSection = true;
                  }

                  if ((boolean) renderer.getRendererOptionValue(
                     RendererOption.UPDATE_PARAGRAPH_NUMBERS) && !publishInline) {
                     if (artifact.isAttributeTypeValid(CoreAttributeTypes.ParagraphNumber)) {
                        artifact.setSoleAttributeValue(CoreAttributeTypes.ParagraphNumber, paragraphNumber.toString());

                        SkynetTransaction transaction =
                           (SkynetTransaction) renderer.getRendererOptionValue(RendererOption.TRANSACTION_OPTION);
                        if (transaction != null) {
                           artifact.persist(transaction);
                        } else {
                           artifact.persist(getClass().getSimpleName());
                        }
                     }
                  }
               }

               String footer = "";
               if (!templateFooter) {
                  WordCoreUtil.pageType orientation = WordCoreUtilClient.getPageOrientation(artifact);
                  footer = dataRightContentBuilder.getContent(artifact, orientation);
               }

               processMetadata(artifact, wordMl);
               processAttributes(artifact, wordMl, presentationType, publishInline, footer);
            }

            // Check for option that may have been set from Publish with Diff BLAM to recurse
            boolean recurse = (boolean) renderer.getRendererOptionValue(RendererOption.RECURSE_ON_LOAD);
            boolean origDiff = (boolean) renderer.getRendererOptionValue(RendererOption.ORIG_PUBLISH_AS_DIFF);

            if (recurseChildren && !recurse || recurse && !origDiff) {
               for (Artifact childArtifact : artifact.getChildren()) {
                  processObjectArtifact(childArtifact, wordMl, outlineType, presentationType, dataRightContentBuilder);
               }
            }

            if (startedSection) {
               wordMl.endOutlineSubSection();
            }
            processedArtifacts.add(artifact);
         }
      } else {
         nonTemplateArtifacts.add(artifact);
      }
   }

   private boolean isOfType(Artifact artifact, List<ArtifactTypeToken> excludeArtifactTypes) {
      for (ArtifactTypeToken artType : excludeArtifactTypes) {
         if (artifact.isOfType(artType)) {
            return true;
         }
      }
      return false;
   }

   private void processMetadata(Artifact artifact, WordMLProducer wordMl) {
      for (MetadataOptions metadataOptions : metadataOptionsList) {
         processMetadata(artifact, wordMl, metadataOptions);
      }
   }

   private void processAttributes(Artifact artifact, WordMLProducer wordMl, PresentationType presentationType, boolean publishInLine, String footer) {
      for (AttributeOptions attributeOptions : attributeOptionsList) {
         String attributeName = attributeOptions.getAttributeName();

         if ((boolean) renderer.getRendererOptionValue(RendererOption.ALL_ATTRIBUTES) || attributeName.equals("*")) {
            for (AttributeTypeToken attributeType : RendererManager.getAttributeTypeOrderList(artifact)) {
               if (!outlining || attributeType.notEqual(headingAttributeType)) {
                  processAttribute(artifact, wordMl, attributeOptions, attributeType, true, presentationType,
                     publishInLine, footer);
               }
            }
         } else {
            AttributeTypeToken attributeType = AttributeTypeManager.getType(attributeName);
            if (artifact.isAttributeTypeValid(attributeType)) {
               processAttribute(artifact, wordMl, attributeOptions, attributeType, false, presentationType,
                  publishInLine, footer);
            }
         }
      }
   }

   private void processMetadata(Artifact artifact, WordMLProducer wordMl, MetadataOptions element) {
      wordMl.startParagraph();
      String name = element.getType();
      String format = element.getFormat();
      String label = element.getLabel();
      String value = "";
      if (name.equals(APPLICABILITY)) {
         value = "unknown";
         if (artifact.getApplicablityId().isValid()) {
            ApplicabilityToken applicabilityToken = applicabilityTokens.get(artifact.getApplicablityId());
            if (applicabilityToken != null && applicabilityToken.isValid()) {
               value = applicabilityToken.getName();
            } else {
               value = artifact.getApplicablityId().getIdString();
            }
         }
      } else if (name.equals(ARTIFACT_TYPE)) {
         value = artifact.getArtifactTypeName();
      } else if (name.equals(ARTIFACT_ID)) {
         value = artifact.getIdString();
      }
      if (!format.isEmpty() || !label.isEmpty()) {
         if (label.contains(">x<")) {
            wordMl.addWordMl(label.replace(">x<", ">" + XmlEncoderDecoder.textToXml(name + ": ").toString() + "<"));
         }
         if (format.contains(">x<")) {
            wordMl.addWordMl(format.replace(">x<", ">" + XmlEncoderDecoder.textToXml(value).toString() + "<"));
         }
      } else {
         wordMl.addTextInsideParagraph(name + ": " + value);
      }
      wordMl.endParagraph();
   }

   private void processAttribute(Artifact artifact, WordMLProducer wordMl, AttributeOptions attributeOptions, AttributeTypeToken attributeType, boolean allAttrs, PresentationType presentationType, boolean publishInLine, String footer) {
      renderer.setRendererOption(RendererOption.ALL_ATTRIBUTES, allAttrs);
      // This is for SRS Publishing. Do not publish unspecified attributes
      if (!allAttrs && attributeType.matches(Partition, SeverityCategory)) {
         if (artifact.isAttributeTypeValid(Partition)) {
            for (Attribute<?> partition : artifact.getAttributes(Partition)) {
               if (partition == null || partition.getValue() == null || partition.getValue().equals("Unspecified")) {
                  return;
               }
            }
         }
      }
      boolean templateOnly = (boolean) renderer.getRendererOptionValue(RendererOption.TEMPLATE_ONLY);
      if (templateOnly && attributeType.notEqual(WordTemplateContent)) {
         return;
      }

      /**
       * In some cases this returns no attributes at all, including no wordTemplateContent, even though it exists This
       * happens when wordTemplateContent is blank, so the else if condition takes this into account.
       */
      Collection<Attribute<Object>> attributes = artifact.getAttributes(attributeType);

      if (!attributes.isEmpty()) {
         //If WordOleData, the attribute is skipped
         if (attributeType.equals(CoreAttributeTypes.WordOleData)) {
            return;
         }

         // Do not publish relation order during publishing
         if ((boolean) renderer.getRendererOptionValue(
            RendererOption.IN_PUBLISH_MODE) && CoreAttributeTypes.RelationOrder.equals(attributeType)) {
            return;
         }

         if (!(publishInLine && artifact.isAttributeTypeValid(WordTemplateContent)) || attributeType.equals(
            WordTemplateContent)) {
            RendererManager.renderAttribute(attributeType, presentationType, artifact, wordMl,
               attributeOptions.getFormat(), attributeOptions.getLabel(), footer, renderer.getRendererOptionsView());
         }
      } else if (attributeType.equals(WordTemplateContent)) {
         RendererManager.renderAttribute(attributeType, presentationType, artifact, wordMl,
            attributeOptions.getFormat(), attributeOptions.getLabel(), footer, renderer.getRendererOptionsView());
      }
   }

   private void displayNonTemplateArtifacts(final Collection<Artifact> artifacts, final String warningString) {
      if (!artifacts.isEmpty()) {
         Displays.ensureInDisplayThread(new Runnable() {

            @Override
            public void run() {
               ArrayList<Artifact> nonTempArtifacts = new ArrayList<>(artifacts.size());
               nonTempArtifacts.addAll(artifacts);
               WordUiUtil.displayUnhandledArtifacts(artifacts, warningString);
            }
         });
      }
   }

   public Set<ArtifactId> getEmptyFolders() {
      return emptyFolders;
   }

   public void setExcludedArtifactTypeForTest(List<ArtifactTypeToken> excludeTokens) {
      excludeArtifactTypes.clear();
      for (ArtifactTypeToken token : excludeTokens) {
         excludeArtifactTypes.add(token);
      }
   }

   private CharSequence startOutlineSubSectionOverride(WordMLProducer wordMl, Artifact artifact, String headingText) {
      String paragraphNumber = artifact.getSoleAttributeValue(CoreAttributeTypes.ParagraphNumber, "");
      if (paragraphNumber.isEmpty()) {
         ArtifactHierarchyComparator comparator = new ArtifactHierarchyComparator();
         paragraphNumber = comparator.getHierarchyPosition(artifact);
      }
      int outlineLevel = 1;
      for (int i = 0; i < paragraphNumber.length(); i++) {
         char charAt = paragraphNumber.charAt(i);
         if (charAt == '.') {
            outlineLevel++;
         }
      }
      wordMl.startOutlineSubSection("Heading", outlineLevel, paragraphNumber, "Times New Roman", headingText);

      return paragraphNumber;
   }

   private boolean isWordTemplateContentValid(Artifact artifact) {
      return !artifact.isAttributeTypeValid(CoreAttributeTypes.WholeWordContent) && !artifact.isAttributeTypeValid(
         CoreAttributeTypes.NativeContent);
   }

   private boolean isArtifactIncluded(Artifact artifact) {
      boolean excludedArtifact =
         (excludeFolders && artifact.isOfType(CoreArtifactTypes.Folder)) || (artifactsToExclude.containsKey(
            ArtifactId.create(artifact)) || emptyFolders.contains(artifact));
      boolean excludedArtifactType = excludeArtifactTypes != null && isOfType(artifact, excludeArtifactTypes);

      return !excludedArtifact && !excludedArtifactType;
   }
}
