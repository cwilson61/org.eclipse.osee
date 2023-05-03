/*********************************************************************
 * Copyright (c) 2023 Boeing
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
package org.eclipse.osee.orcs.rest.model.transaction;

/**
 * @author David W. Miller
 */
import org.eclipse.osee.framework.core.data.ArtifactReadable;

public class ArtifactSortContainerCreate extends ArtifactSortContainer {

   private CreateArtifact createArt;

   public ArtifactSortContainerCreate(ArtifactReadable art) {
      super(art);
   }

   public CreateArtifact getCreateArt() {
      return createArt;
   }

   public void setCreateArtifact(CreateArtifact createArt) {
      this.createArt = createArt;
   }
}