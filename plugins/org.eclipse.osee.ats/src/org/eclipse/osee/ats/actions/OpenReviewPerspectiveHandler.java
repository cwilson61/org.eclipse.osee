/*******************************************************************************
 * Copyright (c) 2014 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.osee.ats.review.ReviewPerspective;
import org.eclipse.osee.framework.ui.plugin.util.AWorkbench;

/**
 * @author Donald G. Dunne
 */
public class OpenReviewPerspectiveHandler extends AbstractHandler {

   public OpenReviewPerspectiveHandler() {
   }

   @Override
   public Object execute(ExecutionEvent event) {
      AWorkbench.openPerspective(ReviewPerspective.ID);
      return null;
   }
}
