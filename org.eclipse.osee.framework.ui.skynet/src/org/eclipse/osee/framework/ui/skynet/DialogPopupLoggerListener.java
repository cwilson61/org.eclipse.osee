/*
 * Created on Jun 25, 2008
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.eclipse.osee.framework.ui.skynet;

import java.util.logging.Level;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.osee.framework.logging.ILoggerListener;
import org.eclipse.swt.widgets.Display;

/**
 * @author b1528444
 *
 */
public class DialogPopupLoggerListener implements ILoggerListener {

	/* (non-Javadoc)
	 * @see org.eclipse.osee.framework.logging.ILoggerListener#log(java.lang.String, java.lang.String, java.util.logging.Level, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(String loggerName, Level level, String message,
			Throwable th) {
		 final IStatus status = new Status(Status.ERROR, loggerName, message, th);
         Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
               ErrorDialog.openError(
                     Display.getDefault().getActiveShell(),
                     "Osee Error",
                     status.getMessage(),
                     status);
            }
         });
	}

}
