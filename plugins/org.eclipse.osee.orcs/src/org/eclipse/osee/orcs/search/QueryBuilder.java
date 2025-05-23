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

package org.eclipse.osee.orcs.search;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactReadable;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.data.AttributeReadable;
import org.eclipse.osee.framework.core.data.AttributeTypeJoin;
import org.eclipse.osee.framework.core.data.AttributeTypeToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.RelationTypeSide;
import org.eclipse.osee.framework.core.data.RelationTypeToken;
import org.eclipse.osee.framework.core.data.TransactionId;
import org.eclipse.osee.framework.core.enums.QueryOption;
import org.eclipse.osee.framework.jdk.core.type.ResultSet;
import org.eclipse.osee.framework.jdk.core.util.SortOrder;

/**
 * @author Ryan D. Brooks
 * @author Roberto E. Escobar
 */
public interface QueryBuilder extends Query {

   ArtifactToken asArtifactToken();

   /**
    * @return a single ArtifactToken if exactly one found. Return ArtifactToken.SENTINEL is none found, else throw
    * exception for finding more than one
    */
   ArtifactToken asArtifactTokenOrSentinel();

   List<ArtifactToken> asArtifactTokens();

   List<ArtifactReadable> asArtifacts();

   /**
    * Gets a map of the artifacts selected by the query.
    *
    * @return a map of the loaded artifacts keyed by the artifact identifiers.
    */

   Map<ArtifactId, ArtifactReadable> asArtifactMap();

   /**
    * Gets a map of the artifacts selected by the query. The found artifacts are loaded into the map specified by the
    * parameter <code>artifacts</code>. If the <code>artifacts</code> map already contains an entry for an artifact
    * found by the query, the artifact is replaced in the map. If the map specified by the parameter
    * <code>artifacts</code> is <code>null</code>, a new map is created and returned.
    *
    * @param artifacts the map to add the loaded artifacts into. This parameter maybe <code>null</code>.
    * @return a map of the loaded artifacts keyed by the artifact identifiers.
    */

   Map<ArtifactId, ArtifactReadable> asArtifactMap(Map<ArtifactId, ArtifactReadable> artifacts);

   ArtifactReadable asArtifact();

   Map<ArtifactId, ArtifactToken> asArtifactTokenMap();

   List<ArtifactId> asArtifactIds();

   ArtifactId asArtifactId();

   List<Map<String, Object>> asArtifactMaps();

   ArtifactTable asArtifactsTable();

   /**
    * @return a single ArtifactId if exactly one found. Return ArtifactId.SENTINEL is none found, else throw exception
    * for finding more than one
    */
   ArtifactId asArtifactIdOrSentinel();

   /**
    * @param attributeType is used in place of the natural Name attribute to populate the name fields in the returned
    * artifact tokens
    */
   List<ArtifactToken> asArtifactTokens(AttributeTypeToken attributeType);

   Map<ArtifactId, ArtifactReadable> asViewToArtifactMap();

   /**
    * @return artifact search results
    */
   ResultSet<ArtifactReadable> getResults();

   ArtifactReadable getArtifact();

   /**
    * @return artifact search results with match locations
    */
   ResultSet<Match<ArtifactReadable, AttributeReadable<?>>> getMatches();

   ArtifactToken getArtifactOrNull();

   /**
    * @return first artifact or sentinal
    */
   ArtifactToken getArtifactOrSentinal();

   public static AttributeTypeToken ANY_ATTRIBUTE_TYPE =
      AttributeTypeToken.valueOf(Long.MIN_VALUE, "Any Attribute Type");

   QueryBuilder includeDeletedArtifacts();

   QueryBuilder includeDeletedArtifacts(boolean enabled);

   boolean areDeletedArtifactsIncluded();

   QueryBuilder includeDeletedAttributes();

   QueryBuilder includeDeletedAttributes(boolean enabled);

   boolean areDeletedAttributesIncluded();

   QueryBuilder includeDeletedRelations();

   QueryBuilder includeDeletedRelations(boolean enabled);

   boolean areDeletedRelationsIncluded();

   QueryBuilder includeApplicabilityTokens();

   boolean areApplicabilityTokensIncluded();

   QueryBuilder includeTransactionDetails();

   boolean areTransactionDetailsIncluded();

   QueryBuilder setOrderByAttribute(AttributeTypeToken AttributeTypeToken);

   QueryBuilder setNoLoadRelations();

   AttributeTypeToken orderByAttribute();

   QueryBuilder setOrderByAttributeDirection(SortOrder direction);

   SortOrder orderByAttributeDirection();

   /**
    * Used to set the order mechanism i.e. RELATION, ATTRIBUTE, or RELATION AND ATTRIBUTE It is advisable instead to use
    * orderByAttribute() as the default OrderMechanism should be RELATION
    */
   QueryBuilder setOrderMechanism(String orderMechanism);

   String orderMechanism();

   QueryBuilder setMaxTime(Date maxTime);

   Date getMaxTime();

   QueryBuilder setLegacyPostProcessing(boolean postProcessing);

   boolean getLegacyPostProcessing();

   void setTableOptions(ArtifactTableOptions tableOptions);

   QueryBuilder fromTransaction(TransactionId transaction);

   TransactionId getFromTransaction();

   QueryBuilder headTransaction();

   boolean isHeadTransaction();

   QueryBuilder excludeDeleted();

   QueryBuilder isOnPage(long pageNum, long pageSize);

   QueryBuilder followSearch(Collection<AttributeTypeToken> attributeTypes, Collection<String> values,
      QueryOption... options);

   QueryBuilder followSearch(Collection<AttributeTypeToken> attributeTypes, String value, QueryOption... options);

   QueryBuilder andId(ArtifactId id);

   QueryBuilder andIds(Collection<? extends ArtifactId> ids);

   QueryBuilder andIds(ArtifactId... ids);

   /**
    * Search criteria that finds the artifact with given artifact id
    */
   QueryBuilder andUuid(long id);

   /**
    * Search criteria that finds the artifacts of given uuids (artifact ids)
    */
   QueryBuilder andUuids(Collection<Long> uuids);

   /**
    * Search criteria that finds the artifacts of given uuids (artifact ids)
    */
   QueryBuilder andIdsL(Collection<Long> ids);

   /**
    * Search criteria that finds a given artifact with guid
    */
   QueryBuilder andGuid(String guid);

   /**
    * Search criteria that finds a given artifact with guids
    */
   QueryBuilder andGuids(Collection<String> ids);

   /**
    * Search criteria that finds a given artifact type using type inheritance
    */
   QueryBuilder andIsOfType(ArtifactTypeToken... artifactType);

   /**
    * Search criteria that finds a given artifact types using type inheritance
    */
   QueryBuilder andIsOfType(Collection<ArtifactTypeToken> artifactType);

   QueryBuilder andTxComment(String commentPattern, AttributeTypeJoin typeJoin);

   /**
    * Search criteria that finds a given artifact types by matching type exactly
    */
   QueryBuilder andTypeEquals(ArtifactTypeToken... artifactType);

   /**
    * Search criteria that finds a given artifact types by matching type exactly
    */
   QueryBuilder andTypeEquals(Collection<ArtifactTypeToken> artifactType);

   /**
    * Search criteria that checks for the existence of an attribute type(s).
    */
   QueryBuilder andExists(AttributeTypeToken... attributeType);

   /**
    * Search criteria that checks for the existence of an attribute types.
    */
   QueryBuilder andExists(Collection<AttributeTypeToken> attributeTypes);

   /**
    * Search criteria that checks for the non-existence of an attribute type(s).
    */
   QueryBuilder andNotExists(Collection<AttributeTypeToken> attributeTypes);

   /**
    * Search criteria that checks for the non-existence of an attribute type(s).
    */
   QueryBuilder andNotExists(AttributeTypeToken attributeType);

   /**
    * Search criteria that checks for the non-existence of an attribute type(s).
    */
   QueryBuilder andNotExists(AttributeTypeToken attributeType, String value);

   /**
    * Search criteria that follows the relation link ending on the given side
    *
    * @param relationType the type to start following the link from
    */
   QueryBuilder andRelationExists(RelationTypeToken relationType);

   /**
    * Search criteria that checks for non-existence of a relation type
    *
    * @param relationTypeSide the type to check for non-existence
    */
   QueryBuilder andRelationNotExists(RelationTypeSide relationType);

   /**
    * Search criteria that follows the relation link ending on the given side
    *
    * @param relationType the type to start following the link from
    */
   QueryBuilder andRelationExists(RelationTypeSide relationType);

   /**
    * Search criteria that checks for non-existence of a relation type
    *
    * @param relationType the type to check for non-existence
    */
   QueryBuilder andRelationNotExists(RelationTypeToken relationType);

   /**
    * Artifact name exactly equals value
    */
   QueryBuilder andNameEquals(String artifactName);

   /**
    * Search criteria that finds an attribute of the given type with its current value exactly equal (or not equal) to
    * any one of the given literal values. If the list only contains one value, then the search is conducted exactly as
    * if the single value constructor was called. This search does not support the (* wildcard) for multiple values.
    */
   QueryBuilder and(AttributeTypeToken attributeType, Collection<String> values, QueryOption... options);

   /**
    * Search criteria that finds an attribute of the given type with its current value relative to the given value.
    */
   QueryBuilder and(AttributeTypeToken attributeType, String value, QueryOption... options);

   /**
    * Search criteria that finds an attribute of the given type with its current value exactly equal (or not equal) to
    * any one of the given literal values. If the list only contains one value, then the search is conducted exactly as
    * if the single value constructor was called. This search does not support the (* wildcard) for multiple values.
    */
   QueryBuilder and(Collection<AttributeTypeToken> attributeTypes, String value, QueryOption... options);

   QueryBuilder and(Collection<AttributeTypeToken> attributeTypes, Collection<String> value, QueryOption... options);

   /**
    * Search for related artifacts
    *
    * @param relationTypeSide the type-side to search on
    */
   QueryBuilder andRelatedTo(RelationTypeSide relationTypeSide, Collection<? extends ArtifactId> artifacts);

   /**
    * Search for related artifacts
    *
    * @param relationTypeSide the type-side to search on
    */
   QueryBuilder andRelatedTo(RelationTypeSide relationTypeSide, ArtifactId artifactId);

   QueryBuilder andRelatedToThroughRels(LinkedList<RelationTypeSide> relationTypeSides, ArtifactId artifactId);

   /**
    * Search for artifacts which aren't related to the given artifact via relationTypeSide
    */
   QueryBuilder andNotRelatedTo(RelationTypeSide relationTypeSide, ArtifactId artifact);

   QueryBuilder andRelatedRecursive(RelationTypeSide relationTypeSide, ArtifactId artifactId);

   QueryBuilder andRelatedRecursive(RelationTypeSide relationTypeSide, ArtifactId artifactId, boolean upstream);

   /**
    * @return DefaultHeirarchicalRootArtifact
    */
   QueryBuilder andIsHeirarchicalRootArtifact();

   QueryBuilder andAttributeIs(AttributeTypeToken attributeType, String value);

   QueryBuilder andAttributeValueRange(AttributeTypeToken attributeType, String fromValue, String toValue);

   QueryBuilder follow(RelationTypeSide relationTypeSide);

   QueryBuilder followAll();

   QueryBuilder followAll(Boolean singleLevel);

   /**
    * @param relationTypeSide side of of the relation following to (not starting from)
    * @param artifacType of the artifacts following to
    * @return
    */
   QueryBuilder follow(RelationTypeSide relationTypeSide, ArtifactTypeToken artifacType);

   QueryBuilder followFork(RelationTypeSide relationTypeSide);

   QueryBuilder followFork(RelationTypeSide relationTypeSide, QueryBuilder query);

   /**
    * @param relationTypeSide side of of the relation following to (not starting from)
    * @param artifacType of the artifacts following to
    * @return
    */
   QueryBuilder followFork(RelationTypeSide relationTypeSide, ArtifactTypeToken artifacType, QueryBuilder query);

   QueryBuilder followNoSelect(RelationTypeSide relationTypeSide, ArtifactTypeToken artifacType);

   /**
    * @param attributeType of attribute which is an artifact id reference
    */
   QueryBuilder getReferenceArtifact(BranchId branchId, AttributeTypeToken attributeType);

   /**
    * @deprecated use follow instead, currently still needed only for ORCS script
    */
   @Deprecated
   QueryBuilder followRelation(RelationTypeSide typeSide);

   List<RelationTypeSide> getRelationTypesForLevel(int level);

   ArtifactReadable asArtifactOrSentinel();
}