/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ContractElementDialog extends MessageDialog {
	protected String inputTime;
	protected final Event pinFrom;
	protected Text inputTimeText;

	public ContractElementDialog(final Shell parentShell, final Event pinFrom, final String title, final String info) {
		super(parentShell, title, null, info, MessageDialog.INFORMATION, 0,
				Messages.DefineFBReactionOnePinDialog_Button);
		this.pinFrom = pinFrom;

	}

	String getTime() {
		return inputTime;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		inputTime = inputTimeText.getText();
		final String[] s = DefineContractUtils.getTimeIntervalFromString(inputTimeText.getText());
		if (inputTime.isBlank() || (s.length == 2 && (Integer.parseInt(s[0]) > Integer.parseInt(s[1])))) {
			MessageDialog.openError(this.getShell(), Messages.DefineFBReactionOnePinDialog_Error,
					Messages.DefineFBReactionOnePinDialog_PleaseFill);
		} else {
			super.buttonPressed(buttonId);
		}
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.FILL);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		final GridLayout layout = new GridLayout(2, false);
		dialogArea.setLayout(layout);
		return dialogArea;
	}

}
