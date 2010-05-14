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
package org.eclipse.osee.framework.skynet.core.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.eclipse.osee.framework.core.data.IRelationType;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactCache;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactModType;
import org.eclipse.osee.framework.skynet.core.artifact.BranchManager;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;
import org.eclipse.osee.framework.skynet.core.internal.Activator;
import org.eclipse.osee.framework.skynet.core.relation.RelationEventType;
import org.eclipse.osee.framework.skynet.core.relation.RelationLink;
import org.eclipse.osee.framework.skynet.core.relation.RelationTypeManager;
import org.eclipse.osee.framework.ui.plugin.event.UnloadedArtifact;
import org.eclipse.osee.framework.ui.plugin.event.UnloadedRelation;

/**
 * Collection of all the data that makes up a single SkynetTransaction and helper methods that allow the applications to
 * easily determine how the changes should be handled. Since events changing artifacts and relations can happen
 * internally in a single client or externally through a server or another client, this class separates the events into
 * cached and unloaded objects.<br>
 * <br>
 * In most cases, applications should only care about artifacts/relations that are loaded into their client's cache.
 * This class provides easy access to this information. In cases where more information is necessary, the Unloaded
 * objects are provided with the data needed to 1) determine if application cares about event prior to loading 2) data
 * needed to load the objects.<br>
 * <br>
 * Care needs to be taken by applications to no load every unloaded artifact and relation to determine if the event
 * needs to be handled. If unloaded objects need to be loaded, their artIds should be collected and bulk loaded through
 * the ArtifactQuery methods such as ArtifactQuery.getArtifactsByIds.
 * 
 * @author Donald G. Dunne
 */
public class FrameworkTransactionData {

   Collection<ArtifactTransactionModifiedEvent> xModifiedEvents;

   // artifact collections of artifacts based on artifactModType that are currently loaded in the client's artifact cache
   public Set<Artifact> cacheChangedArtifacts = new HashSet<Artifact>();
   public Set<Artifact> cacheDeletedArtifacts = new HashSet<Artifact>();
   public Set<Artifact> cacheAddedArtifacts = new HashSet<Artifact>();

   // collection of unloaded artifact changes that are NOT currently loaded in the client's artifact cache;  
   // where UnloadedArtifact contains artifact id, branch and artifact type id 
   public Set<UnloadedArtifact> unloadedChangedArtifacts = new HashSet<UnloadedArtifact>();
   public Set<UnloadedArtifact> unloadedDeletedArtifacts = new HashSet<UnloadedArtifact>();
   public Set<UnloadedArtifact> unloadedAddedArtifacts = new HashSet<UnloadedArtifact>();

   // cacheRelations are relations where one side artifact is already loaded in client's cache
   // NOTE: Change relations are Rationale or Order changes only
   public Set<LoadedRelation> cacheChangedRelations = new HashSet<LoadedRelation>();
   public Set<LoadedRelation> cacheAddedRelations = new HashSet<LoadedRelation>();
   public Set<LoadedRelation> cacheDeletedRelations = new HashSet<LoadedRelation>();

   // unloadedRelations are relations where neither side artifact is loaded in client's cache; normally don't care about these
   // NOTE: Change relations are Rationale or Order changes only
   public Set<UnloadedRelation> unloadedChangedRelations = new HashSet<UnloadedRelation>();
   public Set<UnloadedRelation> unloadedAddedRelations = new HashSet<UnloadedRelation>();
   public Set<UnloadedRelation> unloadedDeletedRelations = new HashSet<UnloadedRelation>();

   // artifact collection of artifacts on either side of a relation that are loaded in client's cache
   // NOTE: Change relations are Rationale or Order changes only
   public Set<Artifact> cacheRelationChangedArtifacts = new HashSet<Artifact>();
   public Set<Artifact> cacheRelationDeletedArtifacts = new HashSet<Artifact>();
   public Set<Artifact> cacheRelationAddedArtifacts = new HashSet<Artifact>();

   public int branchId = -1;

   public static enum ChangeType {
      Changed, Deleted, Added, All
   };

   public FrameworkTransactionData(Collection<ArtifactTransactionModifiedEvent> xModifiedEvents) {
      this.xModifiedEvents = xModifiedEvents;
   }

   /**
    * Return branchId of loaded artifacts or -1 if no loaded artifacts
    */
   public Integer getBranchId() {
      return branchId;
   }

   public Collection<Artifact> getArtifactsInRelations(ChangeType changeType, IRelationType relationType) {
      Set<Artifact> artifacts = new HashSet<Artifact>();
      getArtifactsInRelations(changeType, relationType, artifacts, cacheAddedRelations);
      getArtifactsInRelations(changeType, relationType, artifacts, cacheDeletedRelations);
      getArtifactsInRelations(changeType, relationType, artifacts, cacheChangedRelations);
      return artifacts;
   }

   private void getArtifactsInRelations(ChangeType changeType, IRelationType relationType, Set<Artifact> artifacts, Set<LoadedRelation> cache) {
      if (changeType == ChangeType.Added || changeType == ChangeType.All) {
         for (LoadedRelation loadedRelation : cache) {
            if (loadedRelation.getRelationType().equals(relationType)) {
               if (loadedRelation.getArtifactA() != null) {
                  artifacts.add(loadedRelation.getArtifactA());
               }
               if (loadedRelation.getArtifactB() != null) {
                  artifacts.add(loadedRelation.getArtifactB());
               }
            }
         }
      }
   }

   /**
    * Return artifacts related to this artifact from event service loadedRelations collection. This will bulk load all
    * opposite-side artifacts if they are not already loaded.
    */
   public Collection<Artifact> getRelatedArtifacts(int artId, int relationTypeId, int branchId, Collection<LoadedRelation> loadedRelations) throws OseeCoreException {
      Set<Artifact> artifacts = new HashSet<Artifact>();
      try {
         Set<Integer> artifactIds = new HashSet<Integer>();
         for (LoadedRelation loadedRelation : loadedRelations) {
            // If given artId is artA
            if (loadedRelation.getArtifactA() != null && loadedRelation.getArtifactA().getArtId() == artId) {
               if (loadedRelation.getRelationType().getId() == relationTypeId) {
                  if (loadedRelation.getArtifactB() != null) {
                     artifacts.add(loadedRelation.getArtifactB());
                  } else {
                     artifactIds.add(loadedRelation.getUnloadedRelation().getArtifactBId());
                  }
               }
            }
            // If given artId is ArtB
            if (loadedRelation.getArtifactB() != null && loadedRelation.getArtifactB().getArtId() == artId) {
               if (loadedRelation.getRelationType().getId() == relationTypeId) {
                  if (loadedRelation.getArtifactA() != null) {
                     artifacts.add(loadedRelation.getArtifactA());
                  } else {
                     artifactIds.add(loadedRelation.getUnloadedRelation().getArtifactAId());
                  }
               }
            }
         }
         if (artifactIds.size() > 0) {
            artifacts.addAll(ArtifactQuery.getArtifactListFromIds(artifactIds, BranchManager.getBranch(branchId)));
         }
      } catch (Exception ex) {
         throw new OseeCoreException(ex);
      }
      return artifacts;
   }

   public boolean isRelAddedChangedDeleted(Artifact artifact) {
      return isRelAddedChangedDeleted(artifact.getArtId());
   }

   public boolean isRelAddedChangedDeleted(int artId) {
      return isRelChange(artId) || isRelAdded(artId) || isRelDeleted(artId);

   }

   public boolean isHasEvent(Artifact artifact) {
      return isHasEvent(artifact.getArtId());
   }

   public boolean isHasEvent(int artId) {
      return isChanged(artId) || isDeleted(artId) || isRelChange(artId) || isRelDeleted(artId) || isRelAdded(artId);
   }

   public boolean isDeleted(Artifact artifact) {
      return isDeleted(artifact.getArtId());
   }

   public boolean isDeleted(int artId) {
      for (Artifact art : cacheDeletedArtifacts) {
         if (art.getArtId() == artId) {
            return true;
         }
      }
      return false;
   }

   public boolean isChanged(Artifact artifact) {
      return isChanged(artifact.getArtId());
   }

   public boolean isChanged(int artId) {
      for (Artifact art : cacheChangedArtifacts) {
         if (art.getArtId() == artId) {
            return true;
         }
      }
      return false;
   }

   /**
    * Relation rationale or order changed
    */
   public boolean isRelChange(Artifact artifact) {
      return isRelChange(artifact.getArtId());
   }

   /**
    * Relation rationale or order changed
    */
   public boolean isRelChange(int artId) {
      for (Artifact art : cacheRelationChangedArtifacts) {
         if (art.getArtId() == artId) {
            return true;
         }
      }
      return false;
   }

   public boolean isRelDeleted(Artifact artifact) {
      return isRelDeleted(artifact.getArtId());
   }

   public boolean isRelDeleted(int artId) {
      for (Artifact art : cacheRelationDeletedArtifacts) {
         if (art.getArtId() == artId) {
            return true;
         }
      }
      return false;
   }

   public boolean isRelAdded(Artifact artifact) {
      return isRelAdded(artifact.getArtId());
   }

   public boolean isRelAdded(int artId) {
      for (Artifact art : cacheRelationAddedArtifacts) {
         if (art.getArtId() == artId) {
            return true;
         }
      }
      return false;
   }

   public Collection<ArtifactTransactionModifiedEvent> getXModifiedEvents() {
      return xModifiedEvents;
   }

   public void setXModifiedEvents(Collection<ArtifactTransactionModifiedEvent> modifiedEvents) {
      xModifiedEvents = modifiedEvents;
      createTransactionDataRollup();
   }

   private void createTransactionDataRollup() {
      // Roll-up change information

      for (ArtifactTransactionModifiedEvent xModifiedEvent : getXModifiedEvents()) {
         if (xModifiedEvent instanceof ArtifactModifiedEvent) {
            ArtifactModifiedEvent xArtifactModifiedEvent = (ArtifactModifiedEvent) xModifiedEvent;
            if (xArtifactModifiedEvent.artifactModType == ArtifactModType.Added) {
               if (xArtifactModifiedEvent.artifact != null) {
                  cacheAddedArtifacts.add(xArtifactModifiedEvent.artifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.artifact.getBranch().getId();
                  }
               } else {
                  unloadedAddedArtifacts.add(xArtifactModifiedEvent.unloadedArtifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.unloadedArtifact.getBranchId();
                  }
               }
            }
            if (xArtifactModifiedEvent.artifactModType == ArtifactModType.Deleted) {
               if (xArtifactModifiedEvent.artifact != null) {
                  cacheDeletedArtifacts.add(xArtifactModifiedEvent.artifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.artifact.getBranch().getId();
                  }
               } else {
                  unloadedDeletedArtifacts.add(xArtifactModifiedEvent.unloadedArtifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.unloadedArtifact.getBranchId();
                  }
               }
            }
            if (xArtifactModifiedEvent.artifactModType == ArtifactModType.Changed) {
               if (xArtifactModifiedEvent.artifact != null) {
                  cacheChangedArtifacts.add(xArtifactModifiedEvent.artifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.artifact.getBranch().getId();
                  }
               } else {
                  unloadedChangedArtifacts.add(xArtifactModifiedEvent.unloadedArtifact);
                  if (branchId == -1) {
                     branchId = xArtifactModifiedEvent.unloadedArtifact.getBranchId();
                  }
               }
            }
         }
         if (xModifiedEvent instanceof RelationModifiedEvent) {
            RelationModifiedEvent xRelationModifiedEvent = (RelationModifiedEvent) xModifiedEvent;
            UnloadedRelation unloadedRelation = xRelationModifiedEvent.unloadedRelation;
            LoadedRelation loadedRelation = null;
            // If link is loaded, get information from link
            if (xRelationModifiedEvent.link != null) {
               RelationLink link = xRelationModifiedEvent.link;
               // Get artifact A/B if loaded in artifact cache
               Artifact artA = ArtifactCache.getActive(link.getAArtifactId(), link.getABranch());
               Artifact artB = ArtifactCache.getActive(link.getBArtifactId(), link.getBBranch());
               try {
                  loadedRelation =
                        new LoadedRelation(artA, artB, xRelationModifiedEvent.link.getRelationType(),
                              xRelationModifiedEvent.branch, unloadedRelation);
               } catch (Exception ex) {
                  OseeLog.log(Activator.class, Level.SEVERE, ex);
               }
            }
            // Else, get information from unloadedRelation (if != null)
            else if (unloadedRelation != null) {
               Artifact artA = ArtifactCache.getActive(unloadedRelation.getArtifactAId(), unloadedRelation.getId());
               Artifact artB = ArtifactCache.getActive(unloadedRelation.getArtifactBId(), unloadedRelation.getId());
               if (artA != null || artB != null) {
                  try {
                     loadedRelation =
                           new LoadedRelation(artA, artB, RelationTypeManager.getType(unloadedRelation.getTypeId()),
                                 artA != null ? artA.getBranch() : artB.getBranch(), unloadedRelation);
                  } catch (OseeCoreException ex) {
                     OseeLog.log(Activator.class, Level.SEVERE, ex);
                  }
               }
            }
            if (xRelationModifiedEvent.relationEventType == RelationEventType.Added) {
               if (loadedRelation != null) {
                  cacheAddedRelations.add(loadedRelation);
                  if (loadedRelation.getArtifactA() != null) {
                     cacheRelationAddedArtifacts.add(loadedRelation.getArtifactA());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactA().getBranch().getId();
                     }
                  }
                  if (loadedRelation.getArtifactB() != null) {
                     cacheRelationAddedArtifacts.add(loadedRelation.getArtifactB());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactB().getBranch().getId();
                     }
                  }
               }
               if (unloadedRelation != null) {
                  unloadedAddedRelations.add(unloadedRelation);
               }
            }
            if (xRelationModifiedEvent.relationEventType == RelationEventType.Deleted) {
               if (loadedRelation != null) {
                  cacheDeletedRelations.add(loadedRelation);
                  if (loadedRelation.getArtifactA() != null) {
                     cacheRelationDeletedArtifacts.add(loadedRelation.getArtifactA());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactA().getBranch().getId();
                        loadedRelation.getBranch();
                     }
                  }
                  if (loadedRelation.getArtifactB() != null) {
                     cacheRelationDeletedArtifacts.add(loadedRelation.getArtifactB());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactB().getBranch().getId();
                     }
                  }
               }
               if (unloadedRelation != null) {
                  unloadedDeletedRelations.add(unloadedRelation);
                  if (branchId == -1) {
                     branchId = unloadedRelation.getId();
                  }
               }
            }
            if (xRelationModifiedEvent.relationEventType == RelationEventType.ModifiedRationale) {
               if (loadedRelation != null) {
                  cacheChangedRelations.add(loadedRelation);
                  if (loadedRelation.getArtifactA() != null) {
                     cacheRelationChangedArtifacts.add(loadedRelation.getArtifactA());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactA().getBranch().getId();
                     }
                  }
                  if (loadedRelation.getArtifactB() != null) {
                     cacheRelationChangedArtifacts.add(loadedRelation.getArtifactB());
                     if (branchId == -1) {
                        branchId = loadedRelation.getArtifactB().getBranch().getId();
                     }
                  }
               }
               if (unloadedRelation != null) {
                  unloadedChangedRelations.add(unloadedRelation);
                  if (branchId == -1) {
                     branchId = unloadedRelation.getId();
                  }
               }
            }
         }
      }

      // Clean out known duplicates
      cacheChangedArtifacts.removeAll(cacheDeletedArtifacts);
      cacheAddedArtifacts.removeAll(cacheDeletedArtifacts);

   }

}
