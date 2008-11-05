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
package org.eclipse.osee.framework.ui.skynet.render.word;

import java.util.ArrayList;
import org.eclipse.osee.framework.db.connection.exception.OseeCoreException;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactPersistenceManager;
import org.eclipse.osee.framework.skynet.core.artifact.Branch;
import org.eclipse.osee.framework.skynet.core.utility.Requirements;
import org.eclipse.osee.framework.ui.skynet.blam.VariableMap;

/**
 * @author Jeff C. Phillips
 */
public class SrsProducer implements IWordMlProducer {

   public VariableMap process(VariableMap variableMap) throws OseeCoreException {
      if (variableMap == null) throw new IllegalArgumentException("variableMap must not be null");

      String name = variableMap.getString("Name");
      Branch branch = variableMap.getBranch("Branch");
      Artifact root = ArtifactPersistenceManager.getDefaultHierarchyRootArtifact(branch);
      Artifact softwareRequirement = root.getChild(Requirements.SOFTWARE_REQUIREMENTS);
      Artifact crewInterface = softwareRequirement.getChild("Crew Interface");
      Artifact subsystemManagement = softwareRequirement.getChild("Subsystem Management");
      Artifact appendices = softwareRequirement.getChild("SRS Appendices");

      ArrayList<Artifact> artifacts = new ArrayList<Artifact>(500);

      process(crewInterface, artifacts, name);
      process(subsystemManagement, artifacts, name);
      process(appendices, artifacts, name);

      variableMap.setValue("srsProducer.objects", artifacts);
      return variableMap;
   }

   private void process(Artifact parent, ArrayList<Artifact> artifacts, String name) throws OseeCoreException {
      if (parent.hasChild(name)) {
         artifacts.add(parent.getChild(name));
      }
   }
}
