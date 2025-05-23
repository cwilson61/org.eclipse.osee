/*********************************************************************
 * Copyright (c) 2015 Boeing
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

package org.eclipse.osee.ats.core.program;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.eclipse.osee.ats.api.AtsApi;
import org.eclipse.osee.ats.api.IAtsObject;
import org.eclipse.osee.ats.api.IAtsWorkItem;
import org.eclipse.osee.ats.api.ai.IAtsActionableItem;
import org.eclipse.osee.ats.api.config.TeamDefinition;
import org.eclipse.osee.ats.api.config.WorkType;
import org.eclipse.osee.ats.api.country.IAtsCountry;
import org.eclipse.osee.ats.api.country.JaxCountry;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.ats.api.data.AtsRelationTypes;
import org.eclipse.osee.ats.api.ev.IAtsWorkPackage;
import org.eclipse.osee.ats.api.insertion.IAtsInsertion;
import org.eclipse.osee.ats.api.insertion.IAtsInsertionActivity;
import org.eclipse.osee.ats.api.program.IAtsProgram;
import org.eclipse.osee.ats.api.program.IAtsProgramManager;
import org.eclipse.osee.ats.api.program.IAtsProgramService;
import org.eclipse.osee.ats.api.program.JaxProgram;
import org.eclipse.osee.ats.api.program.ProgramVersions;
import org.eclipse.osee.ats.api.program.ProjectType;
import org.eclipse.osee.ats.api.query.IAtsConfigQuery;
import org.eclipse.osee.ats.api.team.IAtsTeamDefinition;
import org.eclipse.osee.ats.api.util.IAtsChangeSet;
import org.eclipse.osee.ats.api.version.IAtsVersion;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.api.workflow.IAtsTask;
import org.eclipse.osee.ats.api.workflow.IAtsTeamWorkflow;
import org.eclipse.osee.ats.api.workflow.WorkItemType;
import org.eclipse.osee.ats.core.config.Country;
import org.eclipse.osee.ats.core.config.Program;
import org.eclipse.osee.ats.core.insertion.Insertion;
import org.eclipse.osee.ats.core.insertion.InsertionActivity;
import org.eclipse.osee.ats.core.program.operations.AtsProgramOperations;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.BranchToken;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.QueryOption;
import org.eclipse.osee.framework.jdk.core.type.OseeArgumentException;
import org.eclipse.osee.framework.jdk.core.util.Collections;
import org.eclipse.osee.framework.jdk.core.util.Strings;

/**
 * @author Donald G. Dunne
 */
public class AtsProgramService implements IAtsProgramService {

   private AtsApi atsApi;
   private CacheLoader<IAtsTeamDefinition, IAtsProgram> teamDefToAtsProgramCacheLoader;
   private LoadingCache<IAtsTeamDefinition, IAtsProgram> teamDefToAtsProgramCache;
   AtsProgramOperations programOps;
   private static final List<IAtsProgramManager> programMgrs = new ArrayList<>();

   public AtsProgramService() {
      // for jax-rs
   }

   public AtsProgramService(AtsApi atsApi) {
      this.atsApi = atsApi;
      teamDefToAtsProgramCacheLoader = new CacheLoader<IAtsTeamDefinition, IAtsProgram>() {
         @Override
         public IAtsProgram load(IAtsTeamDefinition teamDef) {
            return loadProgram(teamDef);
         }
      };
      teamDefToAtsProgramCache = CacheBuilder.newBuilder() //
         .expireAfterWrite(15, TimeUnit.MINUTES) //
         .build(teamDefToAtsProgramCacheLoader);
   }

   public void addProgramManager(IAtsProgramManager programMgr) {
      programMgrs.add(programMgr);
   }

   private AtsProgramOperations getProgramOps() {
      if (programOps == null) {
         programOps = new AtsProgramOperations(atsApi);
      }
      return programOps;
   }

   @Override
   public IAtsCountry getCountryById(ArtifactId countryId) {
      IAtsCountry country = null;
      if (countryId instanceof IAtsCountry) {
         country = (IAtsCountry) countryId;
      } else {
         ArtifactToken art = atsApi.getQueryService().getArtifact(countryId);
         if (art.isOfType(AtsArtifactTypes.Country)) {
            country = new Country(atsApi.getLogger(), atsApi, art);
         }
      }
      return country;
   }

   @Override
   public IAtsInsertion getInsertionById(ArtifactId insertionId) {
      IAtsInsertion insertion = null;
      if (insertionId instanceof IAtsInsertion) {
         insertion = (IAtsInsertion) insertionId;
      } else {
         ArtifactToken art = atsApi.getQueryService().getArtifact(insertionId);
         if (art.isOfType(AtsArtifactTypes.Insertion)) {
            insertion = new Insertion(atsApi.getLogger(), atsApi, art);
         }
      }
      return insertion;
   }

   @Override
   public IAtsInsertionActivity getInsertionActivityById(ArtifactId insertionActivityId) {
      IAtsInsertionActivity insertionActivity = null;
      if (insertionActivityId instanceof IAtsInsertionActivity) {
         insertionActivity = (IAtsInsertionActivity) insertionActivityId;
      } else {
         ArtifactToken art = atsApi.getQueryService().getArtifact(insertionActivityId);
         if (art.isOfType(AtsArtifactTypes.InsertionActivity)) {
            insertionActivity = new InsertionActivity(atsApi.getLogger(), atsApi, art);
         }
      }
      return insertionActivity;
   }

   @Override
   public Collection<IAtsInsertionActivity> getInsertionActivities(IAtsInsertion insertion) {
      List<IAtsInsertionActivity> insertionActivitys = new ArrayList<>();
      for (ArtifactId artifact : atsApi.getRelationResolver().getRelated(
         atsApi.getQueryService().getArtifact(insertion.getId()),
         AtsRelationTypes.InsertionToInsertionActivity_InsertionActivity)) {
         insertionActivitys.add(atsApi.getProgramService().getInsertionActivityById(artifact));
      }
      return insertionActivitys;
   }

   @Override
   public IAtsInsertionActivity getInsertionActivity(Long insertionActivityId) {
      return atsApi.getProgramService().getInsertionActivityById(
         atsApi.getQueryService().getArtifact(insertionActivityId));
   }

   @Override
   public IAtsInsertionActivity getInsertionActivity(IAtsWorkPackage workPackage) {
      ArtifactId wpArt = atsApi.getQueryService().getArtifact(workPackage.getId());
      Collection<ArtifactToken> related = atsApi.getRelationResolver().getRelated(wpArt,
         AtsRelationTypes.InsertionActivityToWorkPackage_InsertionActivity);
      if (related.size() > 0) {
         return atsApi.getProgramService().getInsertionActivityById(related.iterator().next());
      }
      return null;
   }

   @Override
   public IAtsInsertion getInsertion(IAtsInsertionActivity activity) {
      Collection<ArtifactToken> related = atsApi.getRelationResolver().getRelated(activity.getStoreObject(),
         AtsRelationTypes.InsertionToInsertionActivity_Insertion);
      if (related.size() > 0) {
         return atsApi.getProgramService().getInsertionById(related.iterator().next());
      }
      return null;
   }

   @Override
   public IAtsProgram getProgram(IAtsInsertion insertion) {
      if (insertion != null) {
         Collection<ArtifactToken> related = atsApi.getRelationResolver().getRelated(insertion.getStoreObject(),
            AtsRelationTypes.ProgramToInsertion_Program);
         if (related.size() > 0) {
            return atsApi.getProgramService().getProgramById(related.iterator().next());
         }
      }
      return null;
   }

   @Override
   public IAtsInsertion getInsertion(Long insertionId) {
      return atsApi.getProgramService().getInsertionById(atsApi.getQueryService().getArtifact(insertionId));
   }

   @Override
   public Collection<IAtsInsertion> getInsertions(IAtsProgram program) {
      List<IAtsInsertion> insertions = new ArrayList<>();
      for (ArtifactId artifact : atsApi.getRelationResolver().getRelated(program,
         AtsRelationTypes.ProgramToInsertion_Insertion)) {
         insertions.add(atsApi.getProgramService().getInsertionById(artifact));
      }
      return insertions;
   }

   @Override
   public Collection<IAtsProgram> getPrograms() {
      List<IAtsProgram> programs = new ArrayList<>();
      for (ArtifactId artifact : atsApi.getQueryService().createQuery(AtsArtifactTypes.Program).getArtifacts()) {
         programs.add(atsApi.getProgramService().getProgramById(artifact));
      }
      return programs;
   }

   @Override
   public IAtsProgram getProgramById(ArtifactId programId) {
      IAtsProgram program = null;
      if (programId instanceof IAtsProgram) {
         program = (IAtsProgram) programId;
      } else if (programId instanceof ArtifactToken) {
         program = new Program(atsApi.getLogger(), atsApi, (ArtifactToken) programId);
      } else {
         ArtifactToken art = atsApi.getQueryService().getArtifact(programId);
         if (art.isOfType(AtsArtifactTypes.Program)) {
            program = new Program(atsApi.getLogger(), atsApi, art);
         }
      }
      return program;
   }

   @Override
   public List<IAtsProgram> getPrograms(IAtsCountry atsCountry) {
      List<IAtsProgram> programs = new LinkedList<>();
      ArtifactId artifact = atsCountry.getStoreObject();
      if (artifact != null) {
         for (ArtifactId related : atsApi.getRelationResolver().getRelated(artifact,
            AtsRelationTypes.CountryToProgram_Program)) {
            programs.add(atsApi.getProgramService().getProgramById(related));
         }
      }
      return programs;
   }

   @Override
   public IAtsCountry getCountry(IAtsProgram atsProgram) {
      IAtsCountry country = null;
      ArtifactId artifact = atsProgram.getStoreObject();
      if (artifact != null) {
         ArtifactId countryArt =
            atsApi.getRelationResolver().getRelatedOrSentinel(artifact, AtsRelationTypes.CountryToProgram_Country);
         if (countryArt.isValid()) {
            country = atsApi.getProgramService().getCountryById(countryArt);
         }
      }
      return country;
   }

   @Override
   public IAtsProgram getProgram(IAtsWorkItem workItem) {
      IAtsTeamDefinition teamDef = workItem.getParentTeamWorkflow().getTeamDefinition();
      return getProgram(teamDef);
   }

   @Override
   public IAtsProgram getProgram(IAtsTeamDefinition teamDef) {
      try {
         return teamDefToAtsProgramCache.get(teamDef);
      } catch (Exception ex) {
         // do nothing
      }
      return null;
   }

   private IAtsProgram loadProgram(IAtsTeamDefinition teamDef) {
      IAtsProgram program = null;
      TeamDefinition teamDef2 = atsApi.getConfigService().getConfigurations().getIdToTeamDef().get(teamDef.getId());
      String progIdStr = teamDef2.getProgramId();
      if (Strings.isNumeric(progIdStr)) {
         program = atsApi.getProgramService().getProgramById(ArtifactId.valueOf(progIdStr));
      }
      if (program == null) {
         ArtifactId art = atsApi.getAttributeResolver().getSoleAttributeValue(teamDef, AtsAttributeTypes.ProgramId,
            ArtifactId.SENTINEL);
         if (art.isValid()) {
            program = atsApi.getProgramService().getProgramById(art);
         }
      }
      if (program == null) {
         IAtsTeamDefinition topTeamDef = atsApi.getTeamDefinitionService().getTeamDefHoldingVersions(teamDef);
         if (topTeamDef != null && teamDef.notEqual(topTeamDef)) {
            program = loadProgram(atsApi.getTeamDefinitionService().getParentTeamDef(teamDef));
         }
      }
      if (program == null) {
         program = (IAtsProgram) atsApi.getQueryService().createQuery(AtsArtifactTypes.Program).andAttr(
            AtsAttributeTypes.TeamDefinitionReference,
            teamDef.getIdString()).getConfigObjectResultSet().getExactlyOne();
      }
      return program;
   }

   @Override
   public String getDescription(IAtsProgram program) {
      return atsApi.getAttributeResolver().getSoleAttributeValue(program, AtsAttributeTypes.Description, "");
   }

   @Override
   public IAtsTeamDefinition getTeamDefHoldingVersions(IAtsProgram program) {
      return getTeamDefinition(program);
   }

   @Override
   public IAtsTeamDefinition getTeamDefinition(IAtsProgram program) {
      IAtsTeamDefinition teamDefinition = null;
      ArtifactId artId = atsApi.getAttributeResolver().getSoleArtifactIdReference(program,
         AtsAttributeTypes.TeamDefinitionReference, ArtifactId.SENTINEL);
      if (artId.isValid()) {
         teamDefinition = atsApi.getConfigService().getConfigurations().getIdToTeamDef().get(artId.getId());
         if (teamDefinition == null) {
            teamDefinition = atsApi.getQueryService().getConfigItem(artId);
         }
      }
      return teamDefinition;
   }

   @Override
   public Collection<IAtsActionableItem> getAis(IAtsProgram program) {
      return Collections.castAll(atsApi.getQueryService() //
         .createQuery(AtsArtifactTypes.ActionableItem) //
         .andAttr(AtsAttributeTypes.ProgramId, program.getIdString()) //
         .getConfigObjectResultSet().getList());
   }

   @Override
   public Collection<IAtsTeamDefinition> getTeamDefs(IAtsProgram program) {
      return Collections.castAll(atsApi.getQueryService() //
         .createQuery(AtsArtifactTypes.TeamDefinition) //
         .andAttr(AtsAttributeTypes.ProgramId, program.getIdString()) //
         .getConfigObjectResultSet().getList());
   }

   @Override
   public Collection<IAtsProgram> getPrograms(ArtifactTypeToken artifactType) {
      return Collections.castAll(atsApi.getQueryService() //
         .createQuery(artifactType).getConfigObjectResultSet().getList());
   }

   @Override
   public Collection<String> getCscis(IAtsProgram program) {
      return atsApi.getAttributeResolver().getAttributesToStringList(program, AtsAttributeTypes.CSCI);
   }

   @Override
   public Collection<IAtsActionableItem> getAis(IAtsProgram program, WorkType workType) {
      return getAis(program, java.util.Collections.singleton(workType));
   }

   @Override
   public Collection<IAtsActionableItem> getAis(IAtsProgram program, Collection<WorkType> workTypes) {
      IAtsConfigQuery query = atsApi.getQueryService() //
         .createQuery(AtsArtifactTypes.ActionableItem) //
         .andAttr(AtsAttributeTypes.ProgramId, program.getIdString());
      List<String> types = new LinkedList<>();
      for (WorkType type : workTypes) {
         types.add(type.name());
      }
      query.andAttr(AtsAttributeTypes.WorkType, types, QueryOption.EXACT_MATCH_OPTIONS);
      return Collections.castAll(query.getConfigObjectResultSet().getList());
   }

   @Override
   public Collection<IAtsTeamDefinition> getTeamDefs(IAtsProgram program, Collection<WorkType> workTypes) {
      IAtsConfigQuery query = atsApi.getQueryService() //
         .createQuery(AtsArtifactTypes.TeamDefinition) //
         .andAttr(AtsAttributeTypes.ProgramId, program.getIdString());
      List<String> types = new LinkedList<>();
      for (WorkType type : workTypes) {
         types.add(type.name());
      }
      query.andAttr(AtsAttributeTypes.WorkType, types, QueryOption.EXACT_MATCH_OPTIONS);
      return Collections.castAll(query.getConfigObjectResultSet().getList());
   }

   @Override
   public WorkType getWorkType(IAtsTeamWorkflow teamWf) {
      return getWorkType(teamWf.getTeamDefinition());

   }

   @Override
   public WorkType getWorkType(IAtsTeamDefinition teamDef) {
      WorkType workType = WorkType.None;
      try {
         String typeStr =
            atsApi.getAttributeResolver().getSoleAttributeValueAsString(teamDef, AtsAttributeTypes.WorkType, "");
         if (Strings.isValid(typeStr)) {
            workType = WorkType.valueOfOrNone(typeStr);
         }
      } catch (Exception ex) {
         workType = WorkType.Custom;
      }
      return workType;
   }

   @Override
   public ProjectType getProjectType(IAtsProgram program) {
      if (atsApi.getAttributeResolver().getAttributeCount(program, AtsAttributeTypes.CSCI) > 1) {
         return ProjectType.MultiProcessor;
      } else {
         return ProjectType.SingleProcessor;
      }
   }

   @Override
   public String getNamespace(IAtsProgram program) {
      return atsApi.getAttributeResolver().getSoleAttributeValue(program, AtsAttributeTypes.Namespace, "");
   }

   @Override
   public long getCountryId(IAtsProgram program) {
      long countryId = 0L;
      ArtifactId countryArt =
         atsApi.getRelationResolver().getRelatedOrSentinel(program, AtsRelationTypes.CountryToProgram_Country);
      if (countryArt.isValid()) {
         countryId = countryArt.getId();
      }
      return countryId;
   }

   @Override
   public Collection<IAtsTeamWorkflow> getWorkflows(IAtsProgram program, WorkType workType, IAtsWorkItem workItem) {
      return getWorkflows(program, java.util.Collections.singleton(workType), workItem);
   }

   @Override
   public Collection<IAtsTeamWorkflow> getWorkflows(IAtsProgram program) {
      Collection<IAtsTeamDefinition> workTypeTeamDefs = getTeamDefs(program);
      return atsApi.getQueryService().createQuery(WorkItemType.TeamWorkflow).andTeam(workTypeTeamDefs).getItems(
         IAtsTeamWorkflow.class);
   }

   @Override
   public Collection<IAtsTeamWorkflow> getWorkflows(IAtsProgram program, Collection<WorkType> workTypes,
      IAtsWorkItem workItem) {
      if (workTypes.contains(WorkType.All)) {
         throw new OseeArgumentException("Invalid option ALL for getWorkflow");
      }
      Set<IAtsTeamWorkflow> teamWfs = new HashSet<>();
      Collection<IAtsTeamDefinition> workTypeTeamDefs = getTeamDefs(program, workTypes);
      if (workTypeTeamDefs.size() > 0) {

         IAtsObject useWorkItem = workItem;
         if (useWorkItem instanceof IAtsTask) {
            useWorkItem = (IAtsObject) ((IAtsTask) useWorkItem).getParentAction().getStoreObject();
         }
         if (useWorkItem instanceof IAtsTeamWorkflow) {
            useWorkItem = (IAtsObject) ((IAtsTeamWorkflow) useWorkItem).getParentAction().getStoreObject();
         }
         if (useWorkItem != null && useWorkItem instanceof IAtsAction) {
            for (IAtsTeamWorkflow teamWf : atsApi.getWorkItemService().getTeams(useWorkItem)) {
               if (workTypeTeamDefs.contains(teamWf.getTeamDefinition())) {
                  teamWfs.add(teamWf);
               }
            }
         }
      }
      return teamWfs;
   }

   @Override
   public boolean isActive(IAtsProgram program) {
      return atsApi.getAttributeResolver().getSoleAttributeValue(program, AtsAttributeTypes.Active, true);
   }

   @Override
   public Collection<IAtsTeamDefinition> getTeamDefs(IAtsProgram program, WorkType workType) {
      return getTeamDefs(program, java.util.Collections.singleton(workType));
   }

   @Override
   public Collection<IAtsTeamWorkflow> getWorkflows(IAtsProgram program, Collection<WorkType> workTypes) {
      Collection<IAtsTeamDefinition> teamDefs =
         atsApi.getQueryService().createQuery(AtsArtifactTypes.TeamDefinition).andProgram(program).andWorkType(
            workTypes).getConfigObjects();
      return Collections.castAll(
         atsApi.getQueryService().createQuery(WorkItemType.TeamWorkflow).andTeam(teamDefs).getResults().getList());
   }

   @Override
   public Collection<IAtsVersion> getVersions(IAtsProgram program) {
      IAtsTeamDefinition teamDefHoldingVersions = atsApi.getProgramService().getTeamDefHoldingVersions(program);
      if (teamDefHoldingVersions != null) {
         return atsApi.getVersionService().getVersions(teamDefHoldingVersions);
      }
      return java.util.Collections.emptyList();
   }

   @Override
   public Collection<IAtsTeamWorkflow> getWorkflows(IAtsProgram program, WorkType workType) {
      return getWorkflows(program, java.util.Collections.singleton(workType));
   }

   @Override
   public IAtsVersion getVersion(IAtsProgram program, String versionName) {
      for (IAtsVersion version : getVersions(program)) {
         if (version.getName().equals(versionName)) {
            return version;
         }
      }
      return null;
   }

   @Override
   public List<ProgramVersions> getProgramVersions(ArtifactTypeToken artType, boolean activeOnly) {
      return getProgramOps().getProgramVersionsList(artType, activeOnly);
   }

   @Override
   public ProgramVersions getProgramVersions(ArtifactToken program, boolean onlyActive) {
      ProgramVersions progVer = getProgramOps().getProgramVersions(program, onlyActive);
      return progVer;
   }

   @Override
   public ArtifactToken getProgramFromVersion(ArtifactId version) {
      return getProgramOps().getProgramFromVersion(version);
   }

   @Override
   public BranchToken getProductLineBranch(IAtsProgram program) {
      String branchIdStr =
         atsApi.getAttributeResolver().getSoleAttributeValue(program, AtsAttributeTypes.ProductLineBranchId, "");
      if (Strings.isNumeric(branchIdStr)) {
         Long branchId = Long.valueOf(branchIdStr);
         BranchToken branchToken =
            atsApi.getConfigService().getConfigurations().getBranchIdToBranchToken().get(branchId);
         if (branchToken == null) {
            branchToken = atsApi.getBranchService().getBranch(BranchId.valueOf(branchId));
         } else {
            return branchToken;
         }
      }
      return BranchToken.SENTINEL;
   }

   @Override
   public IAtsProgramManager getProgramManager(IAtsTeamWorkflow teamWf) {
      for (IAtsProgramManager programMgr : programMgrs) {
         if (programMgr.isApplicable(teamWf)) {
            return programMgr;
         }
      }
      return null;
   }

   @Override
   public JaxCountry createCountry(JaxCountry jaxCountry, IAtsChangeSet changes) {
      ArtifactToken countryArt = atsApi.getQueryService().getArtifact(jaxCountry.getId());
      if (countryArt == null) {
         countryArt = changes.createArtifact(AtsArtifactTypes.Country, jaxCountry.getName(), jaxCountry.getId());
      }
      if (!countryArt.getName().equals(jaxCountry.getName())) {
         changes.setSoleAttributeValue(countryArt, CoreAttributeTypes.Name, jaxCountry.getName());
      }
      changes.setSoleAttributeValue(countryArt, AtsAttributeTypes.Active, jaxCountry.isActive());
      return getCountry(countryArt);
   }

   @Override
   public JaxProgram createProgram(JaxProgram jaxProgram, IAtsChangeSet changes) {
      ArtifactToken programArt = atsApi.getQueryService().getArtifact(jaxProgram.getId());
      if (programArt == null) {
         programArt = changes.createArtifact(AtsArtifactTypes.Program, jaxProgram.getName(), jaxProgram.getId());
      }
      if (!programArt.getName().equals(jaxProgram.getName())) {
         changes.setSoleAttributeValue(programArt, CoreAttributeTypes.Name, jaxProgram.getName());
      }
      changes.setSoleAttributeValue(programArt, AtsAttributeTypes.Active, jaxProgram.isActive());
      changes.setSoleAttributeValue(programArt, AtsAttributeTypes.Description, jaxProgram.getDescription());
      long countryId = jaxProgram.getCountryId();
      changes.relate(ArtifactId.valueOf(countryId), AtsRelationTypes.CountryToProgram_Program, programArt);
      return getJaxProgram(programArt);
   }

   @Override
   public JaxProgram getJaxProgram(ArtifactId artifact) {
      JaxProgram jaxProgram = new JaxProgram();
      IAtsProgram program = atsApi.getProgramService().getProgramById(artifact);
      if (program == null) {
         throw new NullPointerException("Program is null");
      }
      jaxProgram.setName(program.getName());
      jaxProgram.setId(program.getId());
      jaxProgram.setActive(program.isActive());
      jaxProgram.setDescription(program.getDescription());
      return jaxProgram;
   }

   @Override
   public JaxCountry getCountry(ArtifactId artifact) {
      JaxCountry jaxCountry = new JaxCountry();
      IAtsCountry country = atsApi.getProgramService().getCountryById(artifact);
      if (country == null) {
         throw new NullPointerException("Country is null");
      }
      jaxCountry.setName(country.getName());
      jaxCountry.setId(country.getId());
      jaxCountry.setActive(country.isActive());
      jaxCountry.setDescription(country.getDescription());
      return jaxCountry;
   }
}
