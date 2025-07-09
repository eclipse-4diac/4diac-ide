/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.dialogs;

import java.util.List;

import org.eclipse.fordiac.ide.contracts.ContractIssue;
import org.eclipse.fordiac.ide.contracts.ContractSystem;
import org.eclipse.fordiac.ide.contracts.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ContractCheckResultDialog extends MessageDialog {

	private static final int MAX_LIST_HEIGHT = 200;
	private final ContractSystem contractSys;

	public ContractCheckResultDialog(final ContractSystem contractSys, final boolean networkCheck, final Shell shell) {
		super(shell,
				contractSys.getIssues().isEmpty() ? Messages.ContractCheckSuccess_Title
						: Messages.ContractCheckIssue_Title,
				null, createDialogMessage(contractSys, networkCheck),
				contractSys.getIssues().isEmpty() ? MessageDialog.INFORMATION : MessageDialog.ERROR, 0,
				Messages.ContractCheck_OK);

		this.contractSys = contractSys;
	}

	@SuppressWarnings("boxing") // boxing because of .formatted
	private static String createDialogMessage(final ContractSystem contractSys, final boolean networkCheck) {
		final StringBuilder sb = new StringBuilder();
		if (networkCheck) {
			sb.append(Messages.ContractCheckNetworkSize.formatted(contractSys.getNComponents()));
		} else {
			sb.append(Messages.ContractCheckSelectionSize.formatted(contractSys.getNComponents()));
		}
		sb.append(System.lineSeparator());

		if (contractSys.getIssues().isEmpty()) {
			sb.append(Messages.ContractCheckNoIssues);
		} else if (contractSys.getIssues().size() == 1) {
			sb.append(Messages.ContractCheckOneIssue);
		} else {
			sb.append(Messages.ContractCheckNIssues.formatted(contractSys.getIssues().size()));
		}
		return sb.toString();
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final List<ContractIssue> issues = contractSys.getIssues();
		if (!issues.isEmpty()) {
			parent.setLayout(new GridLayout(1, false));
			final var issueList = new org.eclipse.swt.widgets.List(parent, SWT.V_SCROLL | SWT.BORDER);
			final GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);

			if (issues.size() * issueList.getItemHeight() > MAX_LIST_HEIGHT) {
				gData.heightHint = MAX_LIST_HEIGHT;
			}
			issueList.setLayoutData(gData);

			for (final ContractIssue issue : issues) {
				final String prefix = switch (issue.getSeverity()) {
				case ERROR -> "[ERROR] "; //$NON-NLS-1$
				case WARNING -> "[WARN] "; //$NON-NLS-1$
				case INFO -> "[INFO] "; //$NON-NLS-1$
				case IGNORE -> "[IGNORE] "; //$NON-NLS-1$
				};
				issueList.add(prefix + issue.getMessage());
			}
		}
		return parent;
	}
}
