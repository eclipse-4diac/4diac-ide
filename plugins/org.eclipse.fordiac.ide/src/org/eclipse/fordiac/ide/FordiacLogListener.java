/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add preference qualifier and issue URL parameter
 *******************************************************************************/
package org.eclipse.fordiac.ide;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.issuereport.GitIssueCreator;
import org.eclipse.fordiac.ide.issuereport.PreferenceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class FordiacLogListener implements ILogListener {

	private static final class LogErrorDialog extends ErrorDialog {

		private final IStatus status;
		private final String preferenceQualifier;
		private Link reportInfo;

		private LogErrorDialog(final Shell parentShell, final IStatus status, final String preferenceQualifier) {
			super(parentShell, Messages.FordiacLogListener_ErrorDialogTitle,
					Messages.FordiacLogListener_ErrorDialogRestartSave, status,
					IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
			this.status = status;
			this.preferenceQualifier = preferenceQualifier;
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Control control = super.createDialogArea(parent);

			reportInfo = new Link(parent, SWT.NONE);
			reportInfo.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					GitIssueCreator.openLinkInBrowser(e.text);
				}

				@Override
				public void widgetDefaultSelected(final SelectionEvent e) {
					// nothing to do
				}
			});

			if (PreferenceConstants.getReportMode(preferenceQualifier) == PreferenceConstants.ReportMode.AUTO_REPORT) {
				report(); // immediately start report in this mode
			} else {
				reportInfo.setText(Messages.FordiacLogListener_ErrorDialogReportPrompt);
			}
			return control;
		}

		private void report() {
			reportInfo.setText(Messages.FordiacLogListener_ReportInProgress);
			new Thread(() -> {
				final Optional<String> url = GitIssueCreator.createIssue(status, preferenceQualifier);
				Display.getDefault().asyncExec(() -> {
					if (reportInfo == null || reportInfo.isDisposed()) {
						return; // dialog was already closed
					}
					if (PreferenceConstants.getReportDestination(
							preferenceQualifier) == PreferenceConstants.ReportDestination.GITHUB_MANUAL) {
						reportInfo.setText(Messages.FordiacLogListener_BrowserOpened);
					} else if (url.isEmpty()) {
						reportInfo.setText(Messages.FordiacLogListener_ReportingError);
					} else {
						reportInfo.setText(Messages.FordiacLogListener_ReportingResult.formatted(url.get()));
					}
					reportInfo.requestLayout();
				});
			}).start();
		}

		@Override
		protected void createButtonsForButtonBar(final Composite parent) {
			switch (PreferenceConstants.getReportMode(preferenceQualifier)) {
			case AUTO_REPORT: // "this will be reported" -> OK, Details
				createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
				break;
			case PROMPT_REPORT: // "please report this issue" -> Report Issue, Close, Details
				final Button rep = createButton(parent, IDialogConstants.YES_ID,
						Messages.FordiacLogListener_ErrorDialogReportIssue, true);
				rep.addListener(SWT.Selection, e -> {
					rep.setEnabled(false);
					report();
				});
				createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CLOSE_LABEL, false);
				break;
			default: // "please report this issue" -> Close, Details
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
				showErrorDialog(createStatusWithStackTrace(status), PreferenceConstants.P_BUG_REPORT_PREFERENCE_ID);
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

	private static void showErrorDialog(final IStatus displayStatus, final String preferenceQualifier) {
		if ((null != Display.getCurrent()) && (null != Display.getCurrent().getActiveShell())) {
			new LogErrorDialog(Display.getCurrent().getActiveShell(), displayStatus, preferenceQualifier).open();
		}
	}
}
