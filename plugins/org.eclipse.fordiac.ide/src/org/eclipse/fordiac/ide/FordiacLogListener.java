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
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.issuereport.GitIssueCreator;
import org.eclipse.fordiac.ide.issuereport.PreferenceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class FordiacLogListener implements ILogListener {

	private static final class LogErrorDialog extends ErrorDialog {
		private LogErrorDialog(final Shell parentShell, final IStatus status) {
			super(parentShell, Messages.FordiacLogListener_ErrorDialogTitle, getMessage(), status,
					IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
		}

		private static String getMessage() {
			final StringBuilder sb = new StringBuilder();
			sb.append(Messages.FordiacLogListener_ErrorDialogRestartSave);
			sb.append(System.lineSeparator());
			sb.append(System.lineSeparator());
			if (PreferenceConstants.getReportMode() == PreferenceConstants.ReportMode.AUTO_REPORT) {
				sb.append(Messages.FordiacLogListener_ErrorDialogAutoReportInfo);
			} else {
				sb.append(Messages.FordiacLogListener_ErrorDialogReportPrompt);
			}
			return sb.toString();
		}

		public boolean shouldReportIssue() {
			return PreferenceConstants.getReportMode() == PreferenceConstants.ReportMode.AUTO_REPORT
					|| getReturnCode() == IDialogConstants.OK_ID;
		}

		@Override
		protected void createButtonsForButtonBar(final Composite parent) {
			final var repMode = PreferenceConstants.getReportMode();
			if (repMode == PreferenceConstants.ReportMode.AUTO_REPORT) {
				// "this will be reported" -> OK, Details
				createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			} else if (repMode == PreferenceConstants.ReportMode.PROMPT_REPORT) {
				// "please report this issue" -> Report Issue, Cancel, Details
				createButton(parent, IDialogConstants.OK_ID, Messages.FordiacLogListener_ErrorDialogReportIssue, true);
				createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
			} else {
				// "please report this issue" -> Close, Details
				createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CLOSE_LABEL, true);
			}
			createDetailsButton(parent);
		}
	}

	private final AtomicBoolean singleWindow = new AtomicBoolean();

	@Override
	public void logging(final IStatus status, final String plugin) {
		if ((status.getSeverity() == IStatus.ERROR) && (null != status.getException())
				&& (status.getPlugin().startsWith(Activator.PLUGIN_ID)
						|| status.getPlugin().equals(PlatformUI.PLUGIN_ID))
				// checking/setting the flag must be last, so that we only set it when actually
				// showing an error dialog and resetting the flag afterwards
				&& !singleWindow.getAndSet(true)) {
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
			final LogErrorDialog dialog = new LogErrorDialog(Display.getCurrent().getActiveShell(), displayStatus);
			dialog.open();
			if (dialog.shouldReportIssue()) {
				GitIssueCreator.createIssue(displayStatus);
			}
		}
	}
}
