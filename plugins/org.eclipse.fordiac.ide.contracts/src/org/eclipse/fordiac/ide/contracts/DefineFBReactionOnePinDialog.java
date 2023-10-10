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
import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DefineFBReactionOnePinDialog extends ContractElementDialog {
	private static final int NUM_COLUMNS = 3;
	private Text offset;
	private final Event pinTo;
	private String offsetText;
	private Button offsetCheckbox;
	private boolean defineOffset;
	private String state;

	public DefineFBReactionOnePinDialog(final Shell parentShell, final Event pinFrom) {
		super(parentShell, pinFrom, Messages.DefineFBReactionOnePinDialog_Title,
				Messages.DefineFBReactionOnePinDialog_Info);
		this.pinTo = null;
	}

	public boolean hasOffset() {
		return defineOffset;
	}

	public String getOffsetText() {
		return offsetText;
	}

	public String getState() {
		return state;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Composite dialogArea = (Composite) super.createCustomArea(parent);
		final Group group = new Group(dialogArea, SWT.FILL);

		group.setText(Messages.DefineFBReactionOnePinDialog_DefineAssumption);
		group.setLayout(new GridLayout(NUM_COLUMNS, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);
		if (pinTo != null) {
			label.setText(ContractKeywords.REACTION + " " + ContractKeywords.EVENTS_OPEN + pinFrom.getName() //$NON-NLS-1$
					+ ContractKeywords.COMMA + pinTo.getName() + ContractKeywords.EVENTS_CLOSE);

			label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());
		} else {
			label.setText(ContractKeywords.EVENT + " " + pinFrom.getName()); //$NON-NLS-1$
			label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());
		}

		label = new Label(group, SWT.None);
		label.setText(ContractKeywords.OCCURS + " " + ContractKeywords.EVERY); //$NON-NLS-1$

		inputTimeText = new Text(group, SWT.RIGHT);
		inputTimeText.addListener(SWT.KeyDown, new IntervalVerifyListener(inputTimeText));
		inputTimeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		label = new Label(group, SWT.None);
		label.setText(" " + ContractKeywords.UNIT_OF_TIME); //$NON-NLS-1$

		final Label labelOffset = new Label(group, SWT.None);
		labelOffset.setText(Messages.DefineFBReactionOnePinDialog_SpecifyOffset);
		labelOffset.setVisible(false);

		offset = new Text(group, SWT.RIGHT);
		offset.addListener(SWT.KeyDown, new IntervalVerifyListener(offset));

		offset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		offset.setVisible(false);
		final Label labelOffsetMs = new Label(group, SWT.None);
		labelOffsetMs.setText(" " + ContractKeywords.UNIT_OF_TIME); //$NON-NLS-1$
		labelOffsetMs.setVisible(false);

		offsetCheckbox = new Button(dialogArea, SWT.CHECK);
		offsetCheckbox.setText(ContractKeywords.OFFSET);
		offsetCheckbox.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		offsetCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				offset.setEnabled(offsetCheckbox.getSelection());
				labelOffset.setVisible(offsetCheckbox.getSelection());
				offset.setVisible(offsetCheckbox.getSelection());
				labelOffsetMs.setVisible(offsetCheckbox.getSelection());

			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// is never called
			}
		});

		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		defineOffset = offsetCheckbox.getSelection();
		if (offsetCheckbox.isEnabled()) {
			offsetText = offset.getText();
		}
		inputTime = inputTimeText.getText();
		final String[] strInputTime = DefineContractUtils.getTimeIntervalFromString(inputTime);
		String[] strOffset = { "0", "0" };  //$NON-NLS-1$//$NON-NLS-2$
		if (offset.isVisible()) {
			strOffset = DefineContractUtils.getTimeIntervalFromString(offsetText);
		}
		if (!isCorrect(strInputTime, strOffset)) {
			MessageDialog.openError(this.getShell(), Messages.DefineFBReactionOnePinDialog_Error,
					Messages.DefineFBReactionOnePinDialog_PleaseFill);
		} else {
			super.buttonPressed(buttonId);
		}
	}

	private boolean isCorrect(final String[] strInputTime, final String[] strOffset) {
		if (inputTime.isBlank()) {
			return false;
		}
		if ((offset.isVisible() && offset.getText().isBlank())) {
			return false;
		}
		if ((strInputTime.length == 2 && (Integer.parseInt(strInputTime[0]) > Integer.parseInt(strInputTime[1])))) {
			return false;
		}
		return !(offset.isVisible() && strOffset.length == 2
				&& (Integer.parseInt(strOffset[0]) > Integer.parseInt(strOffset[1])));
	}

}
