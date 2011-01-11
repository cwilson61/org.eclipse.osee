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
package org.eclipse.osee.framework.ui.skynet.widgets;

import java.util.logging.Level;
import org.eclipse.osee.framework.logging.OseeLog;
import org.eclipse.osee.framework.ui.skynet.SkynetGuiPlugin;
import org.eclipse.osee.framework.ui.skynet.widgets.XRadioButton.ButtonType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class XRadioButtonTest extends Composite {

   public XRadioButtonTest(Composite parent, int style) {
      super(parent, style);
      Composite c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(2, false));
      c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      final XRadioButton rb = new XRadioButton("Single Button");
      rb.createWidgets(c, 2);
      rb.addSelectionListener(new SelectionAdapter() {

         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            try {
               System.out.println("isSelected *" + rb.isSelected() + "*");
            } catch (Exception ex) {
               OseeLog.log(SkynetGuiPlugin.class, Level.SEVERE, ex);
            }
         };
      });

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(2, false));
      c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      final XRadioButton rb2 = new XRadioButton("Label After");
      rb2.setLabelAfter(true);
      rb2.createWidgets(c, 2);
      rb2.addSelectionListener(new SelectionAdapter() {

         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            try {
               System.out.println("isSelected *" + rb2.isSelected() + "*");
            } catch (Exception ex) {
               OseeLog.log(SkynetGuiPlugin.class, Level.SEVERE, ex);
            }
         };
      });

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(2, false));
      c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      final XRadioButton rb3 = new XRadioButton("Check Box");
      rb3.setButtonType(ButtonType.Check);
      rb3.setLabelAfter(true);
      rb3.createWidgets(c, 2);

      rb3.addSelectionListener(new SelectionAdapter() {

         @Override
         public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            try {
               System.out.println("isSelected *" + rb3.isSelected() + "*");
            } catch (Exception ex) {
               OseeLog.log(SkynetGuiPlugin.class, Level.SEVERE, ex);
            }
         };
      });

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(1, false));
      c.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

      final XRadioButtons rb4 = new XRadioButtons("Radios", "radios");
      rb4.addButton("aaaaaa", "First tool tip");
      rb4.addButton("ccccc", "2nd tool tip");
      rb4.addButton("bbbbb", "3rd tool tip");
      rb4.createWidgets(c, 1);

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(2, false));

      rb4.setLabel("Sorted Radios");
      rb4.setSortNames(true);
      rb4.createWidgets(c, 7);

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(1, false));
      final XRadioButtons rb5 = new XRadioButtons("Multi Select", "multiSelect");
      rb5.addButton("aaaaaa", "First tool tip");
      rb5.addButton("ddddd", "2nd tool tip");
      rb5.addButton("fffff", "3rd tool tip");
      rb5.addButton("bbbb", "3rd tool tip");
      rb5.addButton("ccccc", "3rd tool tip");
      rb5.setMultiSelect(true);
      rb5.createWidgets(c, 11);

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(1, false));

      rb5.setLabel("Sorted Multi Select");
      rb5.setSortNames(true);
      rb5.createWidgets(c, 11);

      c = new Composite(parent, SWT.NONE);
      c.setLayout(new GridLayout(1, false));
      final XRadioButtons rb6 = new XRadioButtons("Multi Select", "multiSelect");
      rb6.addButtons(new String[] {"a", "k", "b", "c", "l", "d", "e", "m", "f", "g", "h", "i", "j"});
      rb6.setVertical(true, 7);
      rb6.setSortNames(true);
      rb6.setMultiSelect(true);
      rb6.createWidgets(c, 11);

   }

   public static void main(String[] args) {
      Display Display_1 = Display.getDefault();
      Shell Shell_1 = new Shell(Display_1, SWT.SHELL_TRIM);
      Shell_1.setBounds(0, 0, 500, 500);
      Shell_1.setLayout(new GridLayout());
      Shell_1.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.HORIZONTAL_ALIGN_BEGINNING));

      new XRadioButtonTest(Shell_1, SWT.NONE);

      Shell_1.open();
      while (!Shell_1.isDisposed()) {
         if (!Display_1.readAndDispatch()) {
            Display_1.sleep();
         }
      }

      Display_1.dispose();
   }
}
