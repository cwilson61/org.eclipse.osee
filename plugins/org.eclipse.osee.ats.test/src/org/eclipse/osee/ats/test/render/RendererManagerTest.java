/*******************************************************************************
 * Copyright (c) 2010 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.test.render;

import static org.eclipse.osee.ats.util.AtsArtifactTypes.Action;
import static org.eclipse.osee.framework.core.enums.CoreArtifactTypes.GeneralDocument;
import static org.eclipse.osee.framework.core.enums.CoreArtifactTypes.SoftwareRequirement;
import static org.eclipse.osee.framework.core.enums.CoreArtifactTypes.TestProcedureWML;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.DEFAULT_OPEN;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.DIFF;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.GENERALIZED_EDIT;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.MERGE;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.MERGE_EDIT;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.PREVIEW;
import static org.eclipse.osee.framework.ui.skynet.render.PresentationType.SPECIALIZED_EDIT;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.osee.ats.editor.AtsWorkflowRenderer;
import org.eclipse.osee.framework.core.data.IArtifactType;
import org.eclipse.osee.framework.core.exception.OseeCoreException;
import org.eclipse.osee.framework.core.exception.OseeStateException;
import org.eclipse.osee.framework.core.model.type.ArtifactType;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.skynet.core.artifact.ArtifactTypeManager;
import org.eclipse.osee.framework.skynet.core.artifact.BranchManager;
import org.eclipse.osee.framework.ui.skynet.render.DefaultArtifactRenderer;
import org.eclipse.osee.framework.ui.skynet.render.IRenderer;
import org.eclipse.osee.framework.ui.skynet.render.NativeRenderer;
import org.eclipse.osee.framework.ui.skynet.render.PresentationType;
import org.eclipse.osee.framework.ui.skynet.render.RendererManager;
import org.eclipse.osee.framework.ui.skynet.render.WholeWordRenderer;
import org.eclipse.osee.framework.ui.skynet.render.WordTemplateRenderer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test Case for {@link RendererManager}
 *
 * @author Ryan D. Brooks
 */
@RunWith(Parameterized.class)
public class RendererManagerTest {
   private final ArtifactType artifactType;
   private final PresentationType presentationType;
   private final Class<? extends IRenderer> clazz;

   public RendererManagerTest(IArtifactType artifactType, PresentationType presentationType, Class<? extends IRenderer> clazz) throws OseeCoreException {
      this.artifactType = ArtifactTypeManager.getType(artifactType);
      this.presentationType = presentationType;
      this.clazz = clazz;
   }

   @Test
   public void testGetBestRenderer() throws OseeCoreException {
      Artifact artifact = new Artifact(null, null, null, BranchManager.getCommonBranch(), artifactType);

      try {
         IRenderer renderer = RendererManager.getBestRenderer(presentationType, artifact, null);
         Assert.assertFalse(
            "Expected an OseeStateException to be thrown since no render should be applicable in this case.",
            clazz == null);
         Assert.assertEquals(clazz, renderer.getClass());
      } catch (OseeStateException ex) {
         if (clazz != null) {
            throw ex;
         }
      }
   }

   @Parameters
   public static Collection<Object[]> getData() {
      Collection<Object[]> data = new ArrayList<Object[]>();

      data.add(new Object[] {SoftwareRequirement, GENERALIZED_EDIT, DefaultArtifactRenderer.class});
      data.add(new Object[] {SoftwareRequirement, SPECIALIZED_EDIT, WordTemplateRenderer.class});
      data.add(new Object[] {SoftwareRequirement, DIFF, WordTemplateRenderer.class});
      data.add(new Object[] {SoftwareRequirement, PREVIEW, WordTemplateRenderer.class});
      data.add(new Object[] {SoftwareRequirement, MERGE, WordTemplateRenderer.class});
      data.add(new Object[] {SoftwareRequirement, MERGE_EDIT, WordTemplateRenderer.class});
      data.add(new Object[] {SoftwareRequirement, DEFAULT_OPEN, DefaultArtifactRenderer.class});

      data.add(new Object[] {Action, GENERALIZED_EDIT, DefaultArtifactRenderer.class});
      data.add(new Object[] {Action, SPECIALIZED_EDIT, AtsWorkflowRenderer.class});
      data.add(new Object[] {Action, DIFF, AtsWorkflowRenderer.class});
      data.add(new Object[] {Action, PREVIEW, AtsWorkflowRenderer.class});
      data.add(new Object[] {Action, MERGE, AtsWorkflowRenderer.class});
      data.add(new Object[] {Action, MERGE_EDIT, AtsWorkflowRenderer.class});
      data.add(new Object[] {Action, DEFAULT_OPEN, AtsWorkflowRenderer.class});

      data.add(new Object[] {TestProcedureWML, GENERALIZED_EDIT, DefaultArtifactRenderer.class});
      data.add(new Object[] {TestProcedureWML, SPECIALIZED_EDIT, WholeWordRenderer.class});
      data.add(new Object[] {TestProcedureWML, DIFF, WholeWordRenderer.class});
      data.add(new Object[] {TestProcedureWML, PREVIEW, WholeWordRenderer.class});
      data.add(new Object[] {TestProcedureWML, MERGE, WholeWordRenderer.class});
      data.add(new Object[] {TestProcedureWML, MERGE_EDIT, WholeWordRenderer.class});
      data.add(new Object[] {TestProcedureWML, DEFAULT_OPEN, DefaultArtifactRenderer.class});

      data.add(new Object[] {GeneralDocument, GENERALIZED_EDIT, DefaultArtifactRenderer.class});
      data.add(new Object[] {GeneralDocument, SPECIALIZED_EDIT, NativeRenderer.class});
      data.add(new Object[] {GeneralDocument, DIFF, null});
      data.add(new Object[] {GeneralDocument, PREVIEW, NativeRenderer.class});
      data.add(new Object[] {GeneralDocument, MERGE, null});
      data.add(new Object[] {GeneralDocument, MERGE_EDIT, null});
      data.add(new Object[] {GeneralDocument, DEFAULT_OPEN, NativeRenderer.class});

      return data;
   }
}