/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

public class FordiacLogListener implements ILogListener {
	private static final String FORDIAC_IDE_BUGZILLA_URL = "https://bugs.eclipse.org/bugs/enter_bug.cgi?product=4DIAC&component=4DIAC-IDE"; //$NON-NLS-1$

	private static final class LogErrorDialog extends ErrorDialog {
		private LogErrorDialog(Shell parentShell, IStatus status) {
			super(parentShell, Messages.FordiacLogListener_ErrorDialogTitle,
					Messages.FordiacLogListener_ErrorDialogDescription, status,
					IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			createDetailsButton(parent);
			Button button = createButton(parent, IDialogConstants.YES_ID, Messages.FordiacLogListener_ReportIssue,
					false);
			button.addListener(SWT.Selection, ev -> {
				setReturnCode(IDialogConstants.YES_ID);
				close();
			});
		}
	}

	@Override
	public void logging(IStatus status, String plugin) {

		if (status.getSeverity() == IStatus.ERROR) {
			// inform the user that an error has happened
			IStatus displayStatus = status;
			if (null != status.getException()) {
				displayStatus = createStatusWithStackTrace(status);
			}
			showErrorDialog(displayStatus);
		}
	}

	private static IStatus createStatusWithStackTrace(IStatus status) {
		String stackTrace = getStackTrace(status.getException());
		List<IStatus> stackList = Arrays.stream(stackTrace.split(System.getProperty("line.separator"))) //$NON-NLS-1$
				.map(l -> new Status(IStatus.ERROR, status.getPlugin(), l)).collect(Collectors.toList());

		return new MultiStatus(status.getPlugin(), IStatus.ERROR, stackList.toArray(new IStatus[stackList.size()]),
				status.getMessage(), status.getException());
	}

	private static String getStackTrace(Throwable exception) {
		StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	private static void showErrorDialog(IStatus displayStatus) {
		if ((null != Display.getCurrent()) && (null != Display.getCurrent().getActiveShell())) {
			ErrorDialog dialog = new LogErrorDialog(Display.getCurrent().getActiveShell(), displayStatus);
			if (IDialogConstants.YES_ID == dialog.open()) {
				openBugReportBrowser();
			}
		}
	}

	private static void openBugReportBrowser() {
		try {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWebBrowser browser = wb.getBrowserSupport().createBrowser(Activator.PLUGIN_ID);
			browser.openURL(new URL(FORDIAC_IDE_BUGZILLA_URL));
		} catch (PartInitException | MalformedURLException e) {
			UIPlugin.getDefault().logError("Error in opening the 4diac bugzilla web-page!", e); //$NON-NLS-1$
		}

	}

}
