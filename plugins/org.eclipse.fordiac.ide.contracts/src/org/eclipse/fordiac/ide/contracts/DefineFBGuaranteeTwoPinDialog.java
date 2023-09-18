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

import org.eclipse.fordiac.ide.application.utilities.IntervalVerifyListener;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DefineFBGuaranteeTwoPinDialog extends MessageDialog {
	private static final int NUM_COLUMNS = 4;
	private Text inputTime;
	private final Event pinFrom;
	private final Event pinTo;
	private String inputTimeText;

	public DefineFBGuaranteeTwoPinDialog(final Shell parentShell, final Event pinFrom, final Event pinTo) {
		super(parentShell, Messages.DefineFBReactionTwoPinDialog_Title, null,
				Messages.DefineFBReactionTwoPinDialog_Info, MessageDialog.INFORMATION, 0,
				Messages.DefineFBReactionTwoPinDialog_Button);
		this.pinFrom = pinFrom;
		this.pinTo = pinTo;
	}

	public String getTime() {
		return inputTimeText;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.FILL);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		final GridLayout layout = new GridLayout(2, false);
		dialogArea.setLayout(layout);

		final Group group = new Group(dialogArea, SWT.FILL);

		group.setText(Messages.DefineFBReactionThreePinDialog_DefineGuarantee);
		group.setLayout(new GridLayout(NUM_COLUMNS, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);

		label.setText("After " + pinFrom.getName() + " the event " + pinTo.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());

		label = new Label(group, SWT.None);
		label.setText("occurs within"); //$NON-NLS-1$

		inputTime = new Text(group, SWT.RIGHT);
		inputTime.addListener(SWT.KeyDown, new IntervalVerifyListener(inputTime));
		inputTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		label = new Label(group, SWT.None);
		label.setText(" ms"); //$NON-NLS-1$

		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		inputTimeText = inputTime.getText();
		final String[] s = DefineContractUtils.getTimeIntervalFromString(inputTime.getText());
		if (inputTimeText.isBlank() || (s.length == 2 && (Integer.parseInt(s[0]) > Integer.parseInt(s[1])))) {
			MessageDialog.openError(this.getShell(), Messages.DefineFBReactionOnePinDialog_Error,
					Messages.DefineFBReactionOnePinDialog_PleaseFill);
		} else {
			super.buttonPressed(buttonId);
		}
	}
}
