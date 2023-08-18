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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DefineFBDecisionTwoPinDialog extends MessageDialog {
	private boolean isReaction;
	private Button reactionCheckbox;
	private Button guaranteeCheckbox;

	public DefineFBDecisionTwoPinDialog(final Shell parentShell) {
		super(parentShell, Messages.DefineFBReactionOnePinDialog_Title, null,
				Messages.DefineFBDecisionTwoPinDialog_Info, MessageDialog.INFORMATION, 0,
				Messages.DefineFBReactionOnePinDialog_Button);
	}

	public boolean isReaction() {
		return isReaction;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.FILL);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		dialogArea.setLayout(new GridLayout(2, false));

		final Composite dialog = new Composite(dialogArea, SWT.NONE);
		dialog.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		dialog.setLayout(new GridLayout(3, false));

		reactionCheckbox = new Button(dialog, SWT.CHECK);
		reactionCheckbox.setText(Messages.DefineFBDecisionTwoPinDialog_CreateReaction);
		reactionCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		reactionCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				guaranteeCheckbox.setEnabled(!reactionCheckbox.getSelection());

			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// is never called
			}
		});

		guaranteeCheckbox = new Button(dialog, SWT.CHECK);
		guaranteeCheckbox.setText(Messages.DefineFBDecisionTwoPinDialog_CreateGuarantee);
		guaranteeCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		guaranteeCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				reactionCheckbox.setEnabled(!guaranteeCheckbox.getSelection());

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
		isReaction = reactionCheckbox.getSelection();
		if (reactionCheckbox.getSelection() == guaranteeCheckbox.getSelection()) {
			MessageDialog.openError(this.getShell(), Messages.DefineFBDecisionTwoPinDialog_Error,
					Messages.DefineFBDecisionTwoPinDialog_ErrorInfo);
		} else {
			super.buttonPressed(buttonId);
		}
	}

}
