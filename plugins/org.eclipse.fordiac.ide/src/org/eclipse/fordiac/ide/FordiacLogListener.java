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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
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
	private static final String FORDIAC_IDE_ISSUE_URL = "https://github.com/eclipse-4diac/4diac-ide/issues/new"; //$NON-NLS-1$

	private static final class LogErrorDialog extends ErrorDialog {
		private LogErrorDialog(final Shell parentShell, final IStatus status) {
			super(parentShell, Messages.FordiacLogListener_ErrorDialogTitle,
					Messages.FordiacLogListener_ErrorDialogDescription, status,
					IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
		}

		@Override
		protected void createButtonsForButtonBar(final Composite parent) {
			createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			createDetailsButton(parent);
			final Button button = createButton(parent, IDialogConstants.YES_ID, Messages.FordiacLogListener_ReportIssue,
					false);
			button.addListener(SWT.Selection, ev -> {
				setReturnCode(IDialogConstants.YES_ID);
				close();
			});
		}
	}

	private final AtomicBoolean singleWindow = new AtomicBoolean();

	@Override
	public void logging(final IStatus status, final String plugin) {
		if ((status.getSeverity() == IStatus.ERROR) && (null != status.getException()) && !singleWindow.getAndSet(true)
				&& (status.getPlugin().startsWith(Activator.PLUGIN_ID)
						|| status.getPlugin().equals(PlatformUI.PLUGIN_ID))) {
			// inform the user that an error has happened
			// we currently only treat errors with exception and from a 4diac IDE or the
			// Platform UI plug-in as noteworthy
			// if a error dialog is already showing we will not show another one.
			try {
				showErrorDialog(createStatusWithStackTrace(status));
			} finally {
				singleWindow.set(false);
			}
		}
	}

	private static IStatus createStatusWithStackTrace(final IStatus status) {
		final String stackTrace = getStackTrace(status.getException());
		final IStatus[] stackList = Arrays.stream(stackTrace.split(System.getProperty("line.separator"))) //$NON-NLS-1$
				.map(l -> new Status(IStatus.ERROR, status.getPlugin(), l)).toArray(IStatus[]::new);

		return new MultiStatus(status.getPlugin(), IStatus.ERROR, stackList, status.getMessage(),
				status.getException());
	}

	private static String getStackTrace(final Throwable exception) {
		final StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	private static void showErrorDialog(final IStatus displayStatus) {
		if ((null != Display.getCurrent()) && (null != Display.getCurrent().getActiveShell())) {
			final ErrorDialog dialog = new LogErrorDialog(Display.getCurrent().getActiveShell(), displayStatus);
			if (IDialogConstants.YES_ID == dialog.open()) {
				openBugReportBrowser();
			}
		}
	}

	private static void openBugReportBrowser() {
		try {
			final IWorkbench wb = PlatformUI.getWorkbench();
			final IWebBrowser browser = wb.getBrowserSupport().createBrowser(Activator.PLUGIN_ID);
			browser.openURL(new URI(FORDIAC_IDE_ISSUE_URL).toURL());
		} catch (PartInitException | MalformedURLException | URISyntaxException e) {
			FordiacLogHelper.logError("Error in opening the 4diac bugzilla web-page!", e); //$NON-NLS-1$
		}
	}

}
