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
package org.eclipse.osee.framework.server.admin.management;

import java.util.Arrays;
import org.eclipse.osee.framework.core.server.CoreServerActivator;
import org.eclipse.osee.framework.server.admin.BaseCmdWorker;

/**
 * @author Roberto E. Escobar
 */
public class GetServerVersionWorker extends BaseCmdWorker {

   /* (non-Javadoc)
    * @see org.eclipse.osee.framework.server.admin.BaseCmdWorker#doWork(long)
    */
   @Override
   protected void doWork(long startTime) throws Exception {
      StringBuffer buffer = new StringBuffer();
      buffer.append("\nOsee Application Server: ");
      buffer.append(Arrays.deepToString(CoreServerActivator.getApplicationServerManager().getSupportedVersions()));
      buffer.append("\n");
      println(buffer.toString());
   }
}
