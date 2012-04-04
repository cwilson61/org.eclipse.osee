/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.osee.framework.core.dsl.oseeDsl.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import org.eclipse.osee.framework.core.dsl.oseeDsl.util.OseeDslAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OseeDslItemProviderAdapterFactory extends OseeDslAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
   /**
    * This keeps track of the root adapter factory that delegates to this adapter factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ComposedAdapterFactory parentAdapterFactory;

   /**
    * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected IChangeNotifier changeNotifier = new ChangeNotifier();

   /**
    * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected Collection<Object> supportedTypes = new ArrayList<Object>();

   /**
    * This constructs an instance.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public OseeDslItemProviderAdapterFactory() {
      supportedTypes.add(IEditingDomainItemProvider.class);
      supportedTypes.add(IStructuredItemContentProvider.class);
      supportedTypes.add(ITreeItemContentProvider.class);
      supportedTypes.add(IItemLabelProvider.class);
      supportedTypes.add(IItemPropertySource.class);
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeDsl} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected OseeDslItemProvider oseeDslItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeDsl}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createOseeDslAdapter() {
      if (oseeDslItemProvider == null) {
         oseeDslItemProvider = new OseeDslItemProvider(this);
      }

      return oseeDslItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Import} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ImportItemProvider importItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Import}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createImportAdapter() {
      if (importItemProvider == null) {
         importItemProvider = new ImportItemProvider(this);
      }

      return importItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeElement} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected OseeElementItemProvider oseeElementItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeElement}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createOseeElementAdapter() {
      if (oseeElementItemProvider == null) {
         oseeElementItemProvider = new OseeElementItemProvider(this);
      }

      return oseeElementItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeType} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected OseeTypeItemProvider oseeTypeItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OseeType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createOseeTypeAdapter() {
      if (oseeTypeItemProvider == null) {
         oseeTypeItemProvider = new OseeTypeItemProvider(this);
      }

      return oseeTypeItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XArtifactType} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XArtifactTypeItemProvider xArtifactTypeItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XArtifactType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXArtifactTypeAdapter() {
      if (xArtifactTypeItemProvider == null) {
         xArtifactTypeItemProvider = new XArtifactTypeItemProvider(this);
      }

      return xArtifactTypeItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XAttributeTypeRef} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XAttributeTypeRefItemProvider xAttributeTypeRefItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XAttributeTypeRef}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXAttributeTypeRefAdapter() {
      if (xAttributeTypeRefItemProvider == null) {
         xAttributeTypeRefItemProvider = new XAttributeTypeRefItemProvider(this);
      }

      return xAttributeTypeRefItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XAttributeType} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XAttributeTypeItemProvider xAttributeTypeItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XAttributeType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXAttributeTypeAdapter() {
      if (xAttributeTypeItemProvider == null) {
         xAttributeTypeItemProvider = new XAttributeTypeItemProvider(this);
      }

      return xAttributeTypeItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumType} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XOseeEnumTypeItemProvider xOseeEnumTypeItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXOseeEnumTypeAdapter() {
      if (xOseeEnumTypeItemProvider == null) {
         xOseeEnumTypeItemProvider = new XOseeEnumTypeItemProvider(this);
      }

      return xOseeEnumTypeItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumEntry} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XOseeEnumEntryItemProvider xOseeEnumEntryItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumEntry}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXOseeEnumEntryAdapter() {
      if (xOseeEnumEntryItemProvider == null) {
         xOseeEnumEntryItemProvider = new XOseeEnumEntryItemProvider(this);
      }

      return xOseeEnumEntryItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumOverride} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XOseeEnumOverrideItemProvider xOseeEnumOverrideItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XOseeEnumOverride}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXOseeEnumOverrideAdapter() {
      if (xOseeEnumOverrideItemProvider == null) {
         xOseeEnumOverrideItemProvider = new XOseeEnumOverrideItemProvider(this);
      }

      return xOseeEnumOverrideItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OverrideOption} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected OverrideOptionItemProvider overrideOptionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.OverrideOption}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createOverrideOptionAdapter() {
      if (overrideOptionItemProvider == null) {
         overrideOptionItemProvider = new OverrideOptionItemProvider(this);
      }

      return overrideOptionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AddEnum} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected AddEnumItemProvider addEnumItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AddEnum}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createAddEnumAdapter() {
      if (addEnumItemProvider == null) {
         addEnumItemProvider = new AddEnumItemProvider(this);
      }

      return addEnumItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RemoveEnum} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RemoveEnumItemProvider removeEnumItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RemoveEnum}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRemoveEnumAdapter() {
      if (removeEnumItemProvider == null) {
         removeEnumItemProvider = new RemoveEnumItemProvider(this);
      }

      return removeEnumItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XRelationType} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XRelationTypeItemProvider xRelationTypeItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XRelationType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXRelationTypeAdapter() {
      if (xRelationTypeItemProvider == null) {
         xRelationTypeItemProvider = new XRelationTypeItemProvider(this);
      }

      return xRelationTypeItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Condition} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ConditionItemProvider conditionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Condition}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createConditionAdapter() {
      if (conditionItemProvider == null) {
         conditionItemProvider = new ConditionItemProvider(this);
      }

      return conditionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.SimpleCondition} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected SimpleConditionItemProvider simpleConditionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.SimpleCondition}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createSimpleConditionAdapter() {
      if (simpleConditionItemProvider == null) {
         simpleConditionItemProvider = new SimpleConditionItemProvider(this);
      }

      return simpleConditionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.CompoundCondition} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected CompoundConditionItemProvider compoundConditionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.CompoundCondition}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createCompoundConditionAdapter() {
      if (compoundConditionItemProvider == null) {
         compoundConditionItemProvider = new CompoundConditionItemProvider(this);
      }

      return compoundConditionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XArtifactMatcher} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected XArtifactMatcherItemProvider xArtifactMatcherItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.XArtifactMatcher}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createXArtifactMatcherAdapter() {
      if (xArtifactMatcherItemProvider == null) {
         xArtifactMatcherItemProvider = new XArtifactMatcherItemProvider(this);
      }

      return xArtifactMatcherItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Role} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RoleItemProvider roleItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.Role}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRoleAdapter() {
      if (roleItemProvider == null) {
         roleItemProvider = new RoleItemProvider(this);
      }

      return roleItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ReferencedContext} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ReferencedContextItemProvider referencedContextItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ReferencedContext}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createReferencedContextAdapter() {
      if (referencedContextItemProvider == null) {
         referencedContextItemProvider = new ReferencedContextItemProvider(this);
      }

      return referencedContextItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.UsersAndGroups} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected UsersAndGroupsItemProvider usersAndGroupsItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.UsersAndGroups}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createUsersAndGroupsAdapter() {
      if (usersAndGroupsItemProvider == null) {
         usersAndGroupsItemProvider = new UsersAndGroupsItemProvider(this);
      }

      return usersAndGroupsItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AccessContext} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected AccessContextItemProvider accessContextItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AccessContext}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createAccessContextAdapter() {
      if (accessContextItemProvider == null) {
         accessContextItemProvider = new AccessContextItemProvider(this);
      }

      return accessContextItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.HierarchyRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected HierarchyRestrictionItemProvider hierarchyRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.HierarchyRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createHierarchyRestrictionAdapter() {
      if (hierarchyRestrictionItemProvider == null) {
         hierarchyRestrictionItemProvider = new HierarchyRestrictionItemProvider(this);
      }

      return hierarchyRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeArtifactTypePredicate} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RelationTypeArtifactTypePredicateItemProvider relationTypeArtifactTypePredicateItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeArtifactTypePredicate}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRelationTypeArtifactTypePredicateAdapter() {
      if (relationTypeArtifactTypePredicateItemProvider == null) {
         relationTypeArtifactTypePredicateItemProvider = new RelationTypeArtifactTypePredicateItemProvider(this);
      }

      return relationTypeArtifactTypePredicateItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeArtifactPredicate} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RelationTypeArtifactPredicateItemProvider relationTypeArtifactPredicateItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeArtifactPredicate}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRelationTypeArtifactPredicateAdapter() {
      if (relationTypeArtifactPredicateItemProvider == null) {
         relationTypeArtifactPredicateItemProvider = new RelationTypeArtifactPredicateItemProvider(this);
      }

      return relationTypeArtifactPredicateItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypePredicate} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RelationTypePredicateItemProvider relationTypePredicateItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypePredicate}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRelationTypePredicateAdapter() {
      if (relationTypePredicateItemProvider == null) {
         relationTypePredicateItemProvider = new RelationTypePredicateItemProvider(this);
      }

      return relationTypePredicateItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ObjectRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ObjectRestrictionItemProvider objectRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ObjectRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createObjectRestrictionAdapter() {
      if (objectRestrictionItemProvider == null) {
         objectRestrictionItemProvider = new ObjectRestrictionItemProvider(this);
      }

      return objectRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ArtifactMatchRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ArtifactMatchRestrictionItemProvider artifactMatchRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ArtifactMatchRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createArtifactMatchRestrictionAdapter() {
      if (artifactMatchRestrictionItemProvider == null) {
         artifactMatchRestrictionItemProvider = new ArtifactMatchRestrictionItemProvider(this);
      }

      return artifactMatchRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ArtifactTypeRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ArtifactTypeRestrictionItemProvider artifactTypeRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.ArtifactTypeRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createArtifactTypeRestrictionAdapter() {
      if (artifactTypeRestrictionItemProvider == null) {
         artifactTypeRestrictionItemProvider = new ArtifactTypeRestrictionItemProvider(this);
      }

      return artifactTypeRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AttributeTypeRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected AttributeTypeRestrictionItemProvider attributeTypeRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.AttributeTypeRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createAttributeTypeRestrictionAdapter() {
      if (attributeTypeRestrictionItemProvider == null) {
         attributeTypeRestrictionItemProvider = new AttributeTypeRestrictionItemProvider(this);
      }

      return attributeTypeRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.LegacyRelationTypeRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected LegacyRelationTypeRestrictionItemProvider legacyRelationTypeRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.LegacyRelationTypeRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createLegacyRelationTypeRestrictionAdapter() {
      if (legacyRelationTypeRestrictionItemProvider == null) {
         legacyRelationTypeRestrictionItemProvider = new LegacyRelationTypeRestrictionItemProvider(this);
      }

      return legacyRelationTypeRestrictionItemProvider;
   }

   /**
    * This keeps track of the one adapter used for all {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeRestriction} instances.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected RelationTypeRestrictionItemProvider relationTypeRestrictionItemProvider;

   /**
    * This creates an adapter for a {@link org.eclipse.osee.framework.core.dsl.oseeDsl.RelationTypeRestriction}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter createRelationTypeRestrictionAdapter() {
      if (relationTypeRestrictionItemProvider == null) {
         relationTypeRestrictionItemProvider = new RelationTypeRestrictionItemProvider(this);
      }

      return relationTypeRestrictionItemProvider;
   }

   /**
    * This returns the root adapter factory that contains this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ComposeableAdapterFactory getRootAdapterFactory() {
      return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
   }

   /**
    * This sets the composed adapter factory that contains this factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
      this.parentAdapterFactory = parentAdapterFactory;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean isFactoryForType(Object type) {
      return supportedTypes.contains(type) || super.isFactoryForType(type);
   }

   /**
    * This implementation substitutes the factory itself as the key for the adapter.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Adapter adapt(Notifier notifier, Object type) {
      return super.adapt(notifier, this);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object adapt(Object object, Object type) {
      if (isFactoryForType(type)) {
         Object adapter = super.adapt(object, type);
         if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
            return adapter;
         }
      }

      return null;
   }

   /**
    * This adds a listener.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void addListener(INotifyChangedListener notifyChangedListener) {
      changeNotifier.addListener(notifyChangedListener);
   }

   /**
    * This removes a listener.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void removeListener(INotifyChangedListener notifyChangedListener) {
      changeNotifier.removeListener(notifyChangedListener);
   }

   /**
    * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void fireNotifyChanged(Notification notification) {
      changeNotifier.fireNotifyChanged(notification);

      if (parentAdapterFactory != null) {
         parentAdapterFactory.fireNotifyChanged(notification);
      }
   }

   /**
    * This disposes all of the item providers created by this factory. 
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public void dispose() {
      if (oseeDslItemProvider != null) oseeDslItemProvider.dispose();
      if (importItemProvider != null) importItemProvider.dispose();
      if (oseeElementItemProvider != null) oseeElementItemProvider.dispose();
      if (oseeTypeItemProvider != null) oseeTypeItemProvider.dispose();
      if (xArtifactTypeItemProvider != null) xArtifactTypeItemProvider.dispose();
      if (xAttributeTypeRefItemProvider != null) xAttributeTypeRefItemProvider.dispose();
      if (xAttributeTypeItemProvider != null) xAttributeTypeItemProvider.dispose();
      if (xOseeEnumTypeItemProvider != null) xOseeEnumTypeItemProvider.dispose();
      if (xOseeEnumEntryItemProvider != null) xOseeEnumEntryItemProvider.dispose();
      if (xOseeEnumOverrideItemProvider != null) xOseeEnumOverrideItemProvider.dispose();
      if (overrideOptionItemProvider != null) overrideOptionItemProvider.dispose();
      if (addEnumItemProvider != null) addEnumItemProvider.dispose();
      if (removeEnumItemProvider != null) removeEnumItemProvider.dispose();
      if (xRelationTypeItemProvider != null) xRelationTypeItemProvider.dispose();
      if (conditionItemProvider != null) conditionItemProvider.dispose();
      if (simpleConditionItemProvider != null) simpleConditionItemProvider.dispose();
      if (compoundConditionItemProvider != null) compoundConditionItemProvider.dispose();
      if (xArtifactMatcherItemProvider != null) xArtifactMatcherItemProvider.dispose();
      if (roleItemProvider != null) roleItemProvider.dispose();
      if (referencedContextItemProvider != null) referencedContextItemProvider.dispose();
      if (usersAndGroupsItemProvider != null) usersAndGroupsItemProvider.dispose();
      if (accessContextItemProvider != null) accessContextItemProvider.dispose();
      if (hierarchyRestrictionItemProvider != null) hierarchyRestrictionItemProvider.dispose();
      if (relationTypeArtifactTypePredicateItemProvider != null) relationTypeArtifactTypePredicateItemProvider.dispose();
      if (relationTypeArtifactPredicateItemProvider != null) relationTypeArtifactPredicateItemProvider.dispose();
      if (relationTypePredicateItemProvider != null) relationTypePredicateItemProvider.dispose();
      if (objectRestrictionItemProvider != null) objectRestrictionItemProvider.dispose();
      if (artifactMatchRestrictionItemProvider != null) artifactMatchRestrictionItemProvider.dispose();
      if (artifactTypeRestrictionItemProvider != null) artifactTypeRestrictionItemProvider.dispose();
      if (attributeTypeRestrictionItemProvider != null) attributeTypeRestrictionItemProvider.dispose();
      if (legacyRelationTypeRestrictionItemProvider != null) legacyRelationTypeRestrictionItemProvider.dispose();
      if (relationTypeRestrictionItemProvider != null) relationTypeRestrictionItemProvider.dispose();
   }

}
