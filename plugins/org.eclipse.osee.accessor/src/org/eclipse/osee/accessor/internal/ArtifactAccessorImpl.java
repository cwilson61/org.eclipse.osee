/*********************************************************************
 * Copyright (c) 2021 Boeing
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
package org.eclipse.osee.accessor.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.eclipse.osee.accessor.ArtifactAccessor;
import org.eclipse.osee.accessor.types.ArtifactAccessorResult;
import org.eclipse.osee.accessor.types.ArtifactMatch;
import org.eclipse.osee.accessor.types.AttributeQuery;
import org.eclipse.osee.accessor.types.AttributeQueryElement;
import org.eclipse.osee.accessor.types.RelatedArtifact;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactReadable;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.data.AttributeTypeId;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.RelationTypeSide;
import org.eclipse.osee.framework.core.data.TransactionId;
import org.eclipse.osee.framework.core.enums.QueryOption;
import org.eclipse.osee.framework.core.enums.RelationSide;
import org.eclipse.osee.framework.jdk.core.util.SortOrder;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.orcs.OrcsApi;
import org.eclipse.osee.orcs.QueryType;
import org.eclipse.osee.orcs.core.ds.FollowRelation;
import org.eclipse.osee.orcs.core.ds.QueryData;
import org.eclipse.osee.orcs.search.QueryBuilder;

/**
 * @author Luciano T. Vaglienti
 * @param <T> Class for storing/presenting artifact
 */
public class ArtifactAccessorImpl<T extends ArtifactAccessorResult> implements ArtifactAccessor<T> {
   private ArtifactTypeToken artifactType = ArtifactTypeToken.SENTINEL;
   private OrcsApi orcsApi;

   @SuppressWarnings("unchecked")
   private Class<T> getType() {
      return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
   }

   public ArtifactAccessorImpl(ArtifactTypeToken artifactType, OrcsApi orcsApi) {
      this.setArtifactType(artifactType);
      this.bindOrcsApi(orcsApi);
   }

   public void bindOrcsApi(OrcsApi orcsApi) {
      this.orcsApi = orcsApi;
   }

   /**
    * @return the artifactType
    */
   public ArtifactTypeToken getArtifactType() {
      return artifactType;
   }

   /**
    * @param artifactType the artifactType to set
    */
   public void setArtifactType(ArtifactTypeToken artifactType) {
      this.artifactType = artifactType;
   }

   @SuppressWarnings("unused")
   private boolean hasSetApplic(Class<?> type) {
      if (getSetApplic(type) != null) {
         return true;
      }
      return false;
   }

   private Method getSetApplic(Class<?> type) {
      for (Method method : type.getMethods()) {
         if (method.getName().startsWith("set") && method.getParameterTypes().length == 1 && void.class.equals(
            method.getReturnType())) {
            //is a setter
            if (method.getName().endsWith("Applicability")) {
               return method;
            }
         }
      }
      return null;
   }

   private Collection<T> fetchCollection(QueryBuilder query, BranchId branch)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      List<T> artifactList = new LinkedList<T>();
      for (ArtifactReadable artifact : query.asArtifacts()) {
         if (artifact.isValid()) {
            T returnObj = this.getType().getDeclaredConstructor(ArtifactReadable.class).newInstance(artifact);
            if (getSetApplic(this.getType()) != null && !query.areApplicabilityTokensIncluded()) {
               getSetApplic(this.getType()).invoke(returnObj,
                  orcsApi.getQueryFactory().applicabilityQuery().getApplicabilityToken(artifact, branch));
            }
            artifactList.add(returnObj);
         }
      }
      return artifactList;
   }

   private T fetchSingle(QueryBuilder query, BranchId branch) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      ArtifactReadable artifact = query.asArtifactOrSentinel();
      if (artifact.isValid()) {
         T returnObj = this.getType().getDeclaredConstructor(ArtifactReadable.class).newInstance(artifact);
         if (getSetApplic(this.getType()) != null && !query.areApplicabilityTokensIncluded()) {
            getSetApplic(this.getType()).invoke(returnObj,
               orcsApi.getQueryFactory().applicabilityQuery().getApplicabilityToken(artifact, branch));
         }
         return returnObj;
      }
      return this.getType().getDeclaredConstructor().newInstance();
   }

   private Map<ArtifactId, T> fetchSingleForAllViews(QueryBuilder query, BranchId branch)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      Map<ArtifactId, ArtifactReadable> results = query.asViewToArtifactMap();
      Map<ArtifactId, T> mapToReturn = new HashMap<>();
      for (Entry<ArtifactId, ArtifactReadable> entry : results.entrySet()) {
         if (entry.getValue().isValid()) {
            T returnObj = this.getType().getDeclaredConstructor(ArtifactReadable.class).newInstance(entry.getValue());
            if (getSetApplic(this.getType()) != null && !query.areApplicabilityTokensIncluded()) {
               getSetApplic(this.getType()).invoke(returnObj,
                  orcsApi.getQueryFactory().applicabilityQuery().getApplicabilityToken(entry.getValue(), branch));
            }
            mapToReturn.put(entry.getKey(), returnObj);
         }
      }
      return mapToReturn;
   }

   @Override
   public Collection<ArtifactMatch> getAffectedArtifacts(BranchId branch, ArtifactId relatedId,
      Collection<RelationTypeSide> relations) throws IllegalArgumentException, SecurityException {
      List<ArtifactMatch> artifactList = new LinkedList<ArtifactMatch>();
      QueryBuilder query = orcsApi.getQueryFactory().fromBranch(branch);
      for (RelationTypeSide relation : relations) {
         query = query.andRelatedTo(relation, relatedId);
      }
      for (ArtifactReadable artifact : query.asArtifacts()) {
         if (artifact.isValid()) {
            artifactList.add(new ArtifactMatch(artifact));
         }
      }
      return artifactList;
   }

   @Override
   public T get(BranchId branch, ArtifactId artId) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.get(branch, artId, new LinkedList<FollowRelation>());
   }

   @Override
   public T get(BranchId branch, ArtifactId artId, Collection<FollowRelation> followRelations)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return get(branch, artId, followRelations, ArtifactId.SENTINEL);
   }

   @Override
   public T get(BranchId branch, ArtifactId artId, Collection<FollowRelation> followRelations, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(
            artifactType).andId(artId);
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchSingle(query, branch);
   }

   @Override
   public Map<ArtifactId, T> getForAllViews(BranchId branch, ArtifactId artId,
      Collection<FollowRelation> followRelations) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getForAllViews(branch, artId, followRelations, TransactionId.SENTINEL);
   }

   @Override
   public Map<ArtifactId, T> getForAllViews(BranchId branch, ArtifactId artId,
      Collection<FollowRelation> followRelations, TransactionId transactionId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andIsOfType(artifactType).andId(
            artId);
      if (transactionId.isValid()) {
         query = query.fromTransaction(transactionId);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchSingleForAllViews(query, branch);
   }

   @Override
   public Collection<T> get(BranchId branch, Collection<ArtifactId> artIds)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.get(branch, artIds, new LinkedList<>());
   }

   @Override
   public Collection<T> get(BranchId branch, Collection<ArtifactId> artIds, Collection<FollowRelation> followRelations)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andIsOfType(artifactType).andIds(
            artIds);
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAll(BranchId branch) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAll(branch, new LinkedList<FollowRelation>());
   }

   @Override
   public Collection<T> getAll(BranchId branch, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, new LinkedList<>(), "", new LinkedList<>(), 0L, 0L, AttributeTypeId.SENTINEL, viewId);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, 0L, 0L);
   }

   @Override
   public Collection<T> getAll(BranchId branch, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, new LinkedList<FollowRelation>(), pageCount, pageSize);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, pageCount, pageSize, AttributeTypeId.SENTINEL);
   }

   @Override
   public Collection<T> getAll(BranchId branch, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAll(BranchId branch, long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, pageCount, pageSize, orderByAttribute, ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAll(BranchId branch, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAll(branch, new LinkedList<FollowRelation>(), pageCount, pageSize, orderByAttribute, viewId);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, "", new LinkedList<>(), pageCount, pageSize, orderByAttribute);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, "", new LinkedList<>(), pageCount, pageSize, orderByAttribute,
         viewId);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations, String filter,
      Collection<AttributeTypeId> attributes, long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAll(branch, followRelations, filter, attributes, pageCount, pageSize, orderByAttribute,
         ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAll(BranchId branch, Collection<FollowRelation> followRelations, String filter,
      Collection<AttributeTypeId> attributes, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, new LinkedList<FollowRelation>());
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, 0L, 0L, AttributeTypeId.SENTINEL, viewId);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, followRelations, 0L, 0L);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      long pageCount, long pageSize) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, new LinkedList<FollowRelation>(), pageCount, pageSize);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, followRelations, pageCount, pageSize,
         AttributeTypeId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, new LinkedList<FollowRelation>(), orderByAttribute);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, followRelations, 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, pageCount, pageSize, orderByAttribute,
         ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, new LinkedList<FollowRelation>(), pageCount, pageSize,
         orderByAttribute, viewId);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, attributes, followRelations, pageCount, pageSize, orderByAttribute,
         ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public int getAllByFilterAndCount(BranchId branch, String filter, Collection<AttributeTypeId> attributes) {
      return this.getAllByFilterAndCount(branch, filter, attributes, ArtifactId.SENTINEL);
   }

   @Override
   public int getAllByFilterAndCount(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      ArtifactId viewId) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      return query.getCount();
   }

   @Override
   public T getByRelationWithoutId(BranchId branch, RelationTypeSide relation, ArtifactId relatedId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getByRelationWithoutId(branch, relation, relatedId, new LinkedList<FollowRelation>());
   }

   @Override
   public T getByRelationWithoutId(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andRelatedTo(relation, relatedId);
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchSingle(query, branch);
   }

   @Override
   public T getByRelation(BranchId branch, ArtifactId artId, RelationTypeSide relation, ArtifactId relatedId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getByRelation(branch, artId, relation, relatedId, new LinkedList<FollowRelation>());
   }

   @Override
   public T getByRelation(BranchId branch, ArtifactId artId, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getByRelation(branch, artId, relation, relatedId, followRelations, ArtifactId.SENTINEL);
   }

   @Override
   public T getByRelation(BranchId branch, ArtifactId artId, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andRelatedTo(relation,
            relatedId).andId(artId);
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchSingle(query, branch);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, new LinkedList<FollowRelation>(), viewId);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, followRelations, ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, followRelations, 0L, 0L, viewId);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      long pageCount, long pageSize) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, new LinkedList<FollowRelation>(), pageCount, pageSize);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, followRelations, pageCount, pageSize,
         ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, followRelations, pageCount, pageSize,
         AttributeTypeId.SENTINEL, viewId);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, new LinkedList<FollowRelation>(), orderByAttribute);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, followRelations, 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, pageCount, pageSize, orderByAttribute,
         ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, new LinkedList<FollowRelation>(), pageCount, pageSize,
         orderByAttribute, viewId);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, "", new LinkedList<>(), followRelations, pageCount,
         pageSize, orderByAttribute, new LinkedList<>(), ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelation(branch, relation, relatedId, "", new LinkedList<>(), followRelations, pageCount,
         pageSize, orderByAttribute, new LinkedList<>(), viewId);
   }

   @Override
   public Collection<T> getAllByRelation(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, Collection<AttributeTypeId> followAttributes, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(
            artifactType).andRelatedTo(relation, relatedId);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
         if (followAttributes.size() > 0) {
            query = query.followSearch(
               followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(
                  Collectors.toList()),
               filter);
         }
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAllByRelationThrough(BranchId branch, LinkedList<RelationTypeSide> relations,
      ArtifactId relatedId, String filter, Collection<AttributeTypeId> attributes,
      Collection<FollowRelation> followRelations, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      Collection<AttributeTypeId> followAttributes, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(
            artifactType).andRelatedToThroughRels(relations, relatedId);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
         if (followAttributes.size() > 0) {
            query = query.followSearch(
               followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(
                  Collectors.toList()),
               filter);
         }
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>());
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, 0L, 0L);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), 0L, 0L);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, AttributeTypeId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), orderByAttribute);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, 0L, 0L,
         orderByAttribute);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute, ArtifactId viewId) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), pageCount, pageSize, orderByAttribute, viewId);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute, SortOrder orderByAttributeDirection, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), pageCount, pageSize, orderByAttribute, orderByAttributeDirection, viewId);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, orderByAttribute, new LinkedList<AttributeTypeId>());
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, orderByAttribute, SortOrder.ASCENDING, new LinkedList<AttributeTypeId>(), viewId);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, SortOrder orderByAttributeDirection, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, orderByAttribute, orderByAttributeDirection, new LinkedList<AttributeTypeId>(), viewId);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, Collection<AttributeTypeId> followAttributes)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, orderByAttribute, SortOrder.ASCENDING, followAttributes, ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, Collection<AttributeTypeId> followAttributes, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByRelationAndFilter(branch, relation, relatedId, filter, attributes, followRelations, pageCount,
         pageSize, orderByAttribute, SortOrder.ASCENDING, followAttributes, viewId);
   }

   @Override
   public Collection<T> getAllByRelationAndFilter(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, SortOrder orderByAttributeDirection,
      Collection<AttributeTypeId> followAttributes, ArtifactId viewId)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andRelatedTo(relation,
            relatedId);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (followAttributes.size() > 0) {
         query = query.followSearch(
            followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(
            orcsApi.tokenService().getAttributeType(orderByAttribute)).setOrderByAttributeDirection(
               orderByAttributeDirection);
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<FollowRelation> followRelations,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute, Collection<AttributeTypeId> followAttributes)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByFilter(branch, filter, followRelations, pageCount, pageSize, orderByAttribute,
         followAttributes, ArtifactId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByFilter(BranchId branch, String filter, Collection<FollowRelation> followRelations,
      long pageCount, long pageSize, AttributeTypeId orderByAttribute, Collection<AttributeTypeId> followAttributes,
      ArtifactId viewId) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (followAttributes.size() > 0) {
         query = query.followSearch(
            followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, false);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, boolean isExact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), isExact);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, Collection<FollowRelation> followRelations,
      boolean isExact) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, followRelations, isExact, 0L, 0L);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), false, pageCount, pageSize);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, boolean isExact, long pageCount,
      long pageSize) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), isExact, pageCount, pageSize);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, Collection<FollowRelation> followRelations,
      boolean isExact, long pageCount, long pageSize) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, followRelations, isExact, pageCount, pageSize, AttributeTypeId.SENTINEL);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, AttributeTypeId orderByAttribute)
      throws IllegalArgumentException, SecurityException {
      return this.getAllByQuery(branch, query, false, orderByAttribute);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, boolean isExact,
      AttributeTypeId orderByAttribute) throws IllegalArgumentException, SecurityException {
      return null;
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, Collection<FollowRelation> followRelations,
      boolean isExact, AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), isExact, 0L, 0L, orderByAttribute);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), false, pageCount, pageSize,
         orderByAttribute);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, boolean isExact, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllByQuery(branch, query, new LinkedList<FollowRelation>(), isExact, pageCount, pageSize,
         orderByAttribute);
   }

   @Override
   public Collection<T> getAllByQuery(BranchId branch, AttributeQuery query, Collection<FollowRelation> followRelations,
      boolean isExact, long pageCount, long pageSize, AttributeTypeId orderByAttribute)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      /**
       * Perform a query using the: relation defined in query.getRelated()(if it exists) attribute type id list defined
       * in query.getQueries() value list defined in query.getQueries()
       */
      QueryOption[] queryOptions = isExact ? QueryOption.EXACT_MATCH_OPTIONS : QueryOption.CONTAINS_MATCH_OPTIONS;
      QueryBuilder executeQuery =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andIsOfType(artifactType);
      if (!query.getRelated().equals(RelatedArtifact.SENTINEL)) {
         executeQuery = executeQuery.andRelatedTo(RelationTypeSide.create(query.getRelated().getRelation(),
            RelationSide.fromString(query.getRelated().getSide())), query.getRelated().getRelatedId());
      }
      for (AttributeQueryElement q : query.getQueries()) {
         executeQuery =
            executeQuery.and(orcsApi.tokenService().getAttributeType(q.getAttributeId()), q.getValue(), queryOptions);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         executeQuery = executeQuery.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (pageCount != 0L && pageSize != 0L) {
         executeQuery = executeQuery.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         executeQuery = buildFollowRelationQuery(executeQuery, rel);
      }
      return fetchCollection(executeQuery, branch);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes) {
      return this.getAllByRelationAndFilterAndCount(branch, relation, relatedId, filter, attributes,
         ArtifactId.SENTINEL);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, ArtifactId viewId) {
      return this.getAllByRelationAndFilterAndCount(branch, relation, relatedId, filter, attributes,
         new LinkedList<FollowRelation>(), viewId);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations) {
      return this.getAllByRelationAndFilterAndCount(branch, relation, relatedId, filter, attributes, followRelations,
         ArtifactId.SENTINEL);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations,
      ArtifactId viewId) {
      return this.getAllByRelationAndFilterAndCount(branch, relation, relatedId, filter, attributes, followRelations,
         new LinkedList<AttributeTypeId>(), viewId);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations,
      Collection<AttributeTypeId> followAttributes) {
      return this.getAllByRelationAndFilterAndCount(branch, relation, relatedId, filter, attributes, followRelations,
         followAttributes, ArtifactId.SENTINEL);
   }

   @Override
   public int getAllByRelationAndFilterAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      String filter, Collection<AttributeTypeId> attributes, Collection<FollowRelation> followRelations,
      Collection<AttributeTypeId> followAttributes, ArtifactId viewId) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andRelatedTo(relation,
            relatedId).and(
               attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
               filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      if (followAttributes.size() > 0) {
         query = query.followSearch(
            followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return query.getCount();
   }

   @Override
   public int getAllByFilterAndCount(BranchId branch, String filter, Collection<FollowRelation> followRelations,
      Collection<AttributeTypeId> followAttributes, ArtifactId viewId) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andIsOfType(artifactType);
      if (followAttributes.size() > 0) {
         query = query.followSearch(
            followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return query.getCount();
   }

   @Override
   public int getAllByRelationAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId) {
      return this.getAllByRelationAndCount(branch, relation, relatedId, ArtifactId.SENTINEL);
   }

   @Override
   public int getAllByRelationAndCount(BranchId branch, RelationTypeSide relation, ArtifactId relatedId,
      ArtifactId viewId) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(
            artifactType).andRelatedTo(relation, relatedId);
      return query.getCount();
   }

   @Override
   public int getAllByRelationThroughAndCount(BranchId branch, LinkedList<RelationTypeSide> relations,
      ArtifactId relatedId, String filter, Collection<AttributeTypeId> attributes, ArtifactId viewId)
      throws IllegalArgumentException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(
            artifactType).andRelatedToThroughRels(relations, relatedId);

      return query.getCount();
   }

   private QueryBuilder buildFollowRelationQuery(QueryBuilder query, FollowRelation followRelation) {
      if (followRelation.isFork()) {
         return query.followFork(followRelation.getFollowRelation(),
            buildFollowRelationSubQuery(followRelation.getChildren()));
      }
      return query.follow(followRelation.getFollowRelation());
   }

   /**
    * Recursive helper function that should only be called from buildFollowRelationQuery
    */
   private QueryBuilder buildFollowRelationSubQuery(List<FollowRelation> followRelations) {
      if (followRelations.isEmpty()) {
         return null;
      }
      QueryBuilder subQuery = new QueryData(QueryType.SELECT, orcsApi.tokenService());
      for (FollowRelation rel : followRelations) {
         if (rel.isFork()) {
            subQuery = subQuery.followFork(rel.getFollowRelation(), buildFollowRelationSubQuery(rel.getChildren()));
         } else {
            subQuery = subQuery.follow(rel.getFollowRelation());
         }
      }
      return subQuery;
   }

   @Override
   public Collection<T> getAllLackingRelationByFilter(BranchId branch, String filter,
      Collection<AttributeTypeId> attributes, Collection<RelationTypeSide> relations, long pageCount, long pageSize)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      QueryBuilder query = orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(artifactType);
      for (RelationTypeSide rel : relations) {
         query = query.andRelationNotExists(rel);
      }
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public int getAllLackingRelationByFilterAndCount(BranchId branch, String filter,
      Collection<AttributeTypeId> attributes, Collection<RelationTypeSide> relations)
      throws IllegalArgumentException, SecurityException {
      QueryBuilder query = orcsApi.getQueryFactory().fromBranch(branch).andIsOfType(artifactType);
      for (RelationTypeSide rel : relations) {
         query = query.andRelationNotExists(rel).setLegacyPostProcessing(false);
      }
      query = query.and(
         attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()), filter,
         QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      return query.getCount();
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<RelationTypeSide> unrelatedSide,
      ArtifactId unrelatedArtifact) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, new LinkedList<FollowRelation>(), unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, ArtifactId viewId,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, new LinkedList<>(), "", new LinkedList<>(), 0L, 0L,
         AttributeTypeId.SENTINEL, viewId, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, 0L, 0L, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, long pageCount, long pageSize,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, new LinkedList<FollowRelation>(), pageCount, pageSize, unrelatedSide,
         unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, pageCount, pageSize, AttributeTypeId.SENTINEL,
         unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, AttributeTypeId orderByAttribute,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, 0L, 0L, orderByAttribute, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations,
      AttributeTypeId orderByAttribute, Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, 0L, 0L, orderByAttribute, unrelatedSide,
         unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute, Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, pageCount, pageSize, orderByAttribute, ArtifactId.SENTINEL, unrelatedSide,
         unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, long pageCount, long pageSize,
      AttributeTypeId orderByAttribute, ArtifactId viewId, Collection<RelationTypeSide> unrelatedSide,
      ArtifactId unrelatedArtifact) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, new LinkedList<FollowRelation>(), pageCount, pageSize, orderByAttribute,
         viewId, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, Collection<RelationTypeSide> unrelatedSide,
      ArtifactId unrelatedArtifact) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, "", new LinkedList<>(), pageCount, pageSize,
         orderByAttribute, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations, long pageCount,
      long pageSize, AttributeTypeId orderByAttribute, ArtifactId viewId, Collection<RelationTypeSide> unrelatedSide,
      ArtifactId unrelatedArtifact) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, "", new LinkedList<>(), pageCount, pageSize,
         orderByAttribute, viewId, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations, String filter,
      Collection<AttributeTypeId> attributes, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      return this.getAllNotRelatedTo(branch, followRelations, filter, attributes, pageCount, pageSize, orderByAttribute,
         ArtifactId.SENTINEL, unrelatedSide, unrelatedArtifact);
   }

   @Override
   public Collection<T> getAllNotRelatedTo(BranchId branch, Collection<FollowRelation> followRelations, String filter,
      Collection<AttributeTypeId> attributes, long pageCount, long pageSize, AttributeTypeId orderByAttribute,
      ArtifactId viewId, Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
      NoSuchMethodException, SecurityException {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      unrelatedArtifact = unrelatedArtifact == null ? ArtifactId.SENTINEL : unrelatedArtifact;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (orderByAttribute != null && orderByAttribute.isValid()) {
         query = query.setOrderByAttribute(orcsApi.tokenService().getAttributeType(orderByAttribute));
      }
      if (unrelatedArtifact.isValid()) {

         for (RelationTypeSide side : unrelatedSide) {
            query = query.andNotRelatedTo(side, unrelatedArtifact);
         }
      }
      if (pageCount != 0L && pageSize != 0L) {
         query = query.isOnPage(pageCount, pageSize);
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return fetchCollection(query, branch);
   }

   @Override
   public int getAllNotRelatedToByFilterAndCount(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact) {
      return this.getAllNotRelatedToByFilterAndCount(branch, filter, attributes, ArtifactId.SENTINEL, unrelatedSide,
         unrelatedArtifact);
   }

   @Override
   public int getAllNotRelatedToByFilterAndCount(BranchId branch, String filter, Collection<AttributeTypeId> attributes,
      ArtifactId viewId, Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      unrelatedArtifact = unrelatedArtifact == null ? ArtifactId.SENTINEL : unrelatedArtifact;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch, viewId).includeApplicabilityTokens().andIsOfType(artifactType);
      if (Strings.isValid(filter)) {
         query = query.and(
            attributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter, QueryOption.TOKEN_DELIMITER__ANY, QueryOption.CASE__IGNORE, QueryOption.TOKEN_MATCH_ORDER__ANY);
      }
      if (unrelatedArtifact.isValid()) {

         for (RelationTypeSide side : unrelatedSide) {
            query = query.andNotRelatedTo(side, unrelatedArtifact);
         }
      }
      return query.getCount();
   }

   @Override
   public int getAllNotRelatedToByFilterAndCount(BranchId branch, String filter,
      Collection<FollowRelation> followRelations, Collection<AttributeTypeId> followAttributes, ArtifactId viewId,
      Collection<RelationTypeSide> unrelatedSide, ArtifactId unrelatedArtifact) {
      viewId = viewId == null ? ArtifactId.SENTINEL : viewId;
      unrelatedArtifact = unrelatedArtifact == null ? ArtifactId.SENTINEL : unrelatedArtifact;
      QueryBuilder query =
         orcsApi.getQueryFactory().fromBranch(branch).includeApplicabilityTokens().andIsOfType(artifactType);
      if (followAttributes.size() > 0) {
         query = query.followSearch(
            followAttributes.stream().map(a -> orcsApi.tokenService().getAttributeType(a)).collect(Collectors.toList()),
            filter);
      }
      if (unrelatedArtifact.isValid()) {

         for (RelationTypeSide side : unrelatedSide) {
            query = query.andNotRelatedTo(side, unrelatedArtifact);
         }
      }
      for (FollowRelation rel : followRelations) {
         query = buildFollowRelationQuery(query, rel);
      }
      return query.getCount();
   }

}
