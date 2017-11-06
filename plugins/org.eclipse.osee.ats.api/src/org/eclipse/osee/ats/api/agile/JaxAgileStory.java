/*******************************************************************************
 * Copyright (c) 2017 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.api.agile;

import org.eclipse.osee.ats.api.config.JaxAtsObject;
import org.eclipse.osee.framework.core.data.ArtifactToken;

/**
 * @author Donald G. Dunne
 */
public class JaxAgileStory extends JaxAtsObject {

   private long programId;

   public long getProgramId() {
      return programId;
   }

   public void setProgramId(long programId) {
      this.programId = programId;
   }

   public static JaxAgileStory construct(IAgileProgramFeature aFeature, ArtifactToken artifact) {
      JaxAgileStory story = new JaxAgileStory();
      story.setName(artifact.getName());
      story.setId(artifact.getId());
      story.setProgramId(aFeature.getId());
      return story;
   }

}