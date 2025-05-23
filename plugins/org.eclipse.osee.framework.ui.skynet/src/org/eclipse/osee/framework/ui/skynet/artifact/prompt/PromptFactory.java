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

package org.eclipse.osee.framework.ui.skynet.artifact.prompt;

import java.text.NumberFormat;
import java.util.Collection;
import org.eclipse.osee.ats.api.data.AtsAttributeTypes;
import org.eclipse.osee.framework.core.data.AttributeTypeToken;
import org.eclipse.osee.framework.jdk.core.type.OseeStateException;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;

/**
 * @author Jeff C. Phillips
 */
public final class PromptFactory implements IPromptFactory {

   @Override
   public IHandlePromptChange createPrompt(Collection<? extends Artifact> artifacts, AttributeTypeToken attributeType,
      String displayName, boolean persist, String viewName) {
      IHandlePromptChange promptChange;

      if (attributeType.equals(AtsAttributeTypes.ReviewedBy) || attributeType.equals(
         AtsAttributeTypes.ReviewedByDate)) {
         promptChange = new SignoffHandlePromptChange(artifacts, attributeType, displayName, persist, null);
      } else if (attributeType.isDate()) {
         promptChange = new DateHandlePromptChange(artifacts, attributeType, displayName, persist);
      } else if (attributeType.isDouble()) {
         promptChange = new StringHandlePromptChange(attributeType, persist, displayName, artifacts,
            NumberFormat.getInstance(), viewName);
      } else if (attributeType.isInteger()) {
         promptChange = new StringHandlePromptChange(attributeType, persist, displayName, artifacts,
            NumberFormat.getIntegerInstance(), viewName);
      } else if (attributeType.isLong()) {
         promptChange = new StringHandlePromptChange(attributeType, persist, displayName, artifacts,
            NumberFormat.getNumberInstance(), viewName);
      } else if (attributeType.isBoolean()) {
         promptChange = new BooleanHandlePromptChange(artifacts, attributeType, displayName, persist, null);
      } else if (attributeType.isEnumerated()) {
         promptChange = new EnumeratedHandlePromptChange(artifacts, attributeType.toEnum(), displayName, persist);
      } else if (attributeType.isString()) {
         promptChange = new StringHandlePromptChange(attributeType, persist, displayName, artifacts, null, viewName);
      } else if (attributeType.isArtifactId()) {
         promptChange = new ArtifactIdHandlePromptChange(artifacts, attributeType, displayName, persist);
      } else {
         throw new OseeStateException("Unhandled attribute type.  Can't edit through this view");
      }
      return promptChange;
   }
}
