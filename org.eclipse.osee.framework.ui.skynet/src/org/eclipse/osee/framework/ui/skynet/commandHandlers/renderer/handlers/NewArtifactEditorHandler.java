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
package org.eclipse.osee.framework.ui.skynet.commandHandlers.renderer.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.osee.framework.ui.skynet.artifact.editor.implementations.NewArtifactEditor;

/**
 * Opens an artifact editor as long as the user has Read permission
 * 
 * @author Jeff C. Phillips
 */
public class NewArtifactEditorHandler extends AbstractEditorHandler {
   /*
    * (non-Javadoc)
    * 
    * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
    */
   @Override
   public Object execute(ExecutionEvent myExecutionEvent) throws ExecutionException {
      NewArtifactEditor.editArtifacts(artifacts);
      return null;
   }
}
