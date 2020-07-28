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

package org.eclipse.osee.ats.ide.walker;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.osee.ats.api.data.AtsArtifactTypes;
import org.eclipse.osee.ats.api.workflow.IAtsAction;
import org.eclipse.osee.ats.ide.internal.AtsClientService;
import org.eclipse.osee.ats.ide.workflow.goal.GoalArtifact;
import org.eclipse.osee.ats.ide.workflow.teamwf.TeamWorkFlowArtifact;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.skynet.ArtifactImageManager;
import org.eclipse.swt.graphics.Image;

/**
 * @author Donald G. Dunne
 */
public class ActionWalkerLabelProvider implements ILabelProvider {

   @Override
   public Image getImage(Object obj) {
      if (obj instanceof Artifact) {
         return ArtifactImageManager.getImage(AtsClientService.get().getQueryServiceClient().getArtifact(obj));
      } else if (obj instanceof IActionWalkerItem) {
         return ((IActionWalkerItem) obj).getImage();
      }
      return null;
   }

   @Override
   public String getText(Object obj) {
      String str = "";
      if (obj instanceof TeamWorkFlowArtifact) {
         try {
            str = ((TeamWorkFlowArtifact) obj).getEditorTitle();
         } catch (OseeCoreException ex) {
            str = "Exception - " + ex.getLocalizedMessage();
         }
      } else if (obj instanceof GoalArtifact) {
         try {
            str = ((GoalArtifact) obj).getEditorTitle();
         } catch (OseeCoreException ex) {
            str = "Exception - " + ex.getLocalizedMessage();
         }
      } else if (obj instanceof Artifact && AtsClientService.get().getQueryServiceClient().getArtifact(obj).isOfType(
         AtsArtifactTypes.AgileSprint)) {
         try {
            str = "Agile Sprint : " + AtsClientService.get().getQueryServiceClient().getArtifact(obj).getName();
         } catch (OseeCoreException ex) {
            str = "Exception - " + ex.getLocalizedMessage();
         }
      } else if (obj instanceof Artifact && !(obj instanceof IAtsAction)) {
         str = obj.toString();
      } else if (obj instanceof IActionWalkerItem) {
         str = ((IActionWalkerItem) obj).getName();
      }
      return Strings.truncate(str, 50, true);
   }

   @Override
   public void addListener(ILabelProviderListener arg0) {
      // do nothing
   }

   @Override
   public void dispose() {
      // do nothing
   }

   @Override
   public boolean isLabelProperty(Object arg0, String arg1) {
      return false;
   }

   @Override
   public void removeListener(ILabelProviderListener arg0) {
      // do nothing
   }

}
