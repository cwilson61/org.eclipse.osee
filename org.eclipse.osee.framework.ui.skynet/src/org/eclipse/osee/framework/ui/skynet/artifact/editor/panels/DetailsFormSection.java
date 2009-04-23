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
package org.eclipse.osee.framework.ui.skynet.artifact.editor.panels;

import java.util.Date;
import org.eclipse.osee.framework.skynet.core.User;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.osee.framework.ui.skynet.artifact.editor.implementations.NewArtifactEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author Roberto E. Escobar
 */
public class DetailsFormSection extends ArtifactEditorFormSection {

   public DetailsFormSection(NewArtifactEditor editor, Composite parent, FormToolkit toolkit, int style) {
      super(editor, parent, toolkit, style);
   }

   /* (non-Javadoc)
    * @see org.eclipse.ui.forms.AbstractFormPart#initialize(org.eclipse.ui.forms.IManagedForm)
    */
   @Override
   public void initialize(IManagedForm form) {
      super.initialize(form);
      final FormToolkit toolkit = form.getToolkit();
      Section section = getSection();
      section.setText("Details");

      section.setLayout(new GridLayout());
      section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

      FormText formText = toolkit.createFormText(section, false);
      formText.setText(getDetailsText(getEditorInput().getArtifact()), true, true);
      formText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

      section.setClient(formText);
      toolkit.paintBordersFor(section);
   }

   private String getDetailsText(Artifact artifact) {
      String template = "<p><b>%s:</b> %s</p>";
      StringBuilder sb = new StringBuilder();
      sb.append("<form>");
      sb.append(String.format(template, "GUID", artifact.getGuid()));
      sb.append(String.format(template, "HRID", artifact.getHumanReadableId()));
      sb.append(String.format(template, "Branch", artifact.getBranch().toString()));
      sb.append(String.format(template, "Branch Id", String.valueOf(artifact.getBranch().getBranchId())));
      sb.append(String.format(template, "Artifact Id", String.valueOf(artifact.getArtId())));
      sb.append(String.format(template, "Artifact Type Name", artifact.getArtifactTypeName()));
      sb.append(String.format(template, "Artifact Type Id", String.valueOf(artifact.getArtTypeId())));
      sb.append(String.format(template, "Gamma Id", String.valueOf(artifact.getGammaId())));
      sb.append(String.format(template, "Historical", String.valueOf(artifact.isHistorical())));
      sb.append(String.format(template, "Deleted", String.valueOf(artifact.isDeleted())));
      sb.append(String.format(template, "Revision", String.valueOf(artifact.getTransactionNumber())));
      Date lastModified = null;
      try {
         lastModified = artifact.getLastModified();
      } catch (Exception ex) {

      }
      sb.append(String.format(template, "Last Modified",
            lastModified != null ? String.valueOf(lastModified) : "Error - unknown"));
      User lastAuthor = null;
      try {
         lastAuthor = artifact.getLastModifiedBy();
      } catch (Exception ex) {

      }
      sb.append(String.format(template, "Last Modified By", lastAuthor != null ? lastAuthor : "Error - unknown"));
      sb.append("</form>");
      return sb.toString();
   }

}
