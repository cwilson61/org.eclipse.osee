/*******************************************************************************
 * Copyright (c) 2015 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.column;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.nebula.widgets.xviewer.IAltLeftClickProvider;
import org.eclipse.nebula.widgets.xviewer.IXViewerLazyLoadColumn;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.osee.ats.api.IAtsObject;
import org.eclipse.osee.ats.artifact.MembersManager;
import org.eclipse.osee.ats.core.client.artifact.CollectorArtifact;
import org.eclipse.osee.ats.core.client.artifact.GoalArtifact;
import org.eclipse.osee.ats.util.xviewer.column.XViewerAtsColumn;
import org.eclipse.osee.ats.world.WorldXViewer;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.skynet.util.LogUtil;

/**
 * @author Donald G. Dunne
 */
public abstract class AbstractMembersOrderColumn extends XViewerAtsColumn implements IXViewerLazyLoadColumn, IAltLeftClickProvider {

   public static final Integer DEFAULT_WIDTH = 45;
   Map<Long, String> multiGoalValueMap = new HashMap<Long, String>();
   boolean loading = false;

   public AbstractMembersOrderColumn(String id, String name, int width, int align, boolean show, SortDataType sortDataType, boolean multiColumnEditable, String description) {
      super(id, name, width, align, show, sortDataType, multiColumnEditable, description);
   }

   @Override
   public Long getKey(Object obj) {
      Long result = 0L;
      if (obj instanceof IAtsObject) {
         result = ((IAtsObject) obj).getUuid();
      }
      return result;
   }

   public abstract Artifact getParentMembersArtifact(WorldXViewer worldXViewer);

   public abstract MembersManager<?> getMembersManager();

   @Override
   public String getText(Object obj, Long key, String cachedValue) {
      String result = "";
      if (!loading) {
         XViewer xViewer = getXViewer();
         if (obj instanceof Artifact && xViewer instanceof WorldXViewer) {
            WorldXViewer worldXViewer = (WorldXViewer) xViewer;
            GoalArtifact parentMembersArtifact = (GoalArtifact) getParentMembersArtifact(worldXViewer);
            if (parentMembersArtifact != null) {
               if (Strings.isValid(cachedValue)) {
                  result = cachedValue;
               }
            } else {
               String cachedObjectValue = multiGoalValueMap.get(((Artifact) obj).getUuid());
               if (Strings.isValid(cachedObjectValue)) {
                  result = cachedObjectValue;
               }
            }
         }
      }
      return result;
   }

   @SuppressWarnings("unchecked")
   @Override
   public void populateCachedValues(Collection<?> objects, Map<Long, String> preComputedValueMap) {
      MembersManager<CollectorArtifact> manager = (MembersManager<CollectorArtifact>) getMembersManager();
      for (Object element : objects) {
         try {
            if (element instanceof Artifact && getXViewer() instanceof WorldXViewer) {
               WorldXViewer worldXViewer = (WorldXViewer) getXViewer();
               GoalArtifact parentGoalArtifact = worldXViewer.getParentGoalArtifact();
               if (parentGoalArtifact != null) {
                  String value = manager.getMemberOrder(parentGoalArtifact, (Artifact) element);
                  preComputedValueMap.put(getKey(element), value);
               } else {
                  String value = manager.getMemberOrder((Artifact) element);
                  multiGoalValueMap.put(getKey(element), value);
               }
            }
         } catch (OseeCoreException ex) {
            LogUtil.getCellExceptionString(ex);
         }
      }
   }

   @Override
   public void setLoading(boolean loading) {
      this.loading = loading;
   }

}
