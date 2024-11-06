/*******************************************************************************
 * Copyright (c) 2023, 2024 Paul Pavlicek and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *   Felix Schmid
 *     - redesign to general dialog that can be used for all contract rules
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ContractElementDialog extends MessageDialog {

	private String input;
	private Text inputText;

	public ContractElementDialog(final Shell parentShell, final String suggestion) {
		super(parentShell, Messages.ContractElementDialog_Title, null, Messages.ContractElementDialog_Info,
				MessageDialog.INFORMATION, 0, Messages.ContractElementDialog_Ok);
		input = suggestion;
	}

	String getContractRule() {
		return simplifyRule(input);
	}

	private static String simplifyRule(final String rule) {
		// TODO: very crude approach for now to remove default offset
		// can be replaced with more general method once new model is in place
		final String defaultOffset = "with 0ms offset"; //$NON-NLS-1$
		if (rule.endsWith(defaultOffset)) {
			return rule.substring(0, rule.length() - defaultOffset.length());
		}
		return rule;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final Group group = new Group(parent, SWT.FILL);
		group.setText(Messages.ContractElementDialog_Define);
		group.setLayout(new GridLayout(1, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		inputText = new Text(group, SWT.SINGLE);
		inputText.setText(input);
		inputText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// select the time for easy editing if possible
		final String sel = String.valueOf(DefineFbInterfaceConstraintHandler.DEFAULT_TIME);
		final int startIdx = input.indexOf(sel);

		if (startIdx > 0) {
			final int endIdx = startIdx + sel.length();
			inputText.setSelection(startIdx, endIdx);
		}

		return parent;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		input = inputText.getText();
		super.buttonPressed(buttonId);
	}
}
