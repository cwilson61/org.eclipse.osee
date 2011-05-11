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
package org.eclipse.osee.ats.config;

import static org.junit.Assert.assertFalse;
import java.util.Arrays;
import org.eclipse.osee.ats.core.config.ActionableItemArtifact;
import org.eclipse.osee.ats.core.config.TeamDefinitionManagerCore;
import org.eclipse.osee.ats.core.type.AtsArtifactTypes;
import org.eclipse.osee.ats.util.AtsUtil;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.search.ArtifactQuery;

/**
 * @author Donald G. Dunne
 */
public class AtsActionableItemToTeamDefinitionTest {

   @org.junit.Test
   public void testAtsActionableItemToTeamDefinition() throws Exception {
      boolean error = false;
      StringBuffer sb = new StringBuffer();
      for (Artifact artifact : ArtifactQuery.getArtifactListFromType(AtsArtifactTypes.ActionableItem,
         AtsUtil.getAtsBranch())) {
         ActionableItemArtifact aia = (ActionableItemArtifact) artifact;
         if (aia.isActionable()) {
            if (TeamDefinitionManagerCore.getImpactedTeamDefs(Arrays.asList(aia)).isEmpty()) {
               sb.append("Actionable Item \"" + aia + "\" has no Team Def associated and is Actionable.");
               error = true;
            }
         }
      }
      assertFalse(sb.toString(), error);
   }
}
