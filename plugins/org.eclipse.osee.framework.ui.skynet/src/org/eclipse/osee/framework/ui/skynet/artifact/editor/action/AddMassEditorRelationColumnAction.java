/*********************************************************************
 * Copyright (c) 2025 Boeing
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
package org.eclipse.osee.framework.ui.skynet.artifact.editor.action;

import org.eclipse.osee.framework.ui.skynet.artifact.massEditor.MassXViewer;

/**
 * @author Donald G. Dunne
 */
public class AddMassEditorRelationColumnAction extends AbstractAddRelationColumnAction {

   public AddMassEditorRelationColumnAction(MassXViewer xViewer, boolean asToken) {
      super(xViewer, asToken);
   }

}
