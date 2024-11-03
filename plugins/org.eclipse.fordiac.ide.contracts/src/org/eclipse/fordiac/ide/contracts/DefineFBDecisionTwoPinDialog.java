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
 *   Felix Schmid
 *     - improved UX by replacing checkboxes with buttons
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DefineFBDecisionTwoPinDialog extends MessageDialog {

	private static final int CREATE_REACTION = 0;
	private static final int CREATE_GUARANTEE = 1;

	private int pressedButtonId = -1;

	public DefineFBDecisionTwoPinDialog(final Shell parentShell) {
		super(parentShell, Messages.DefineFBReactionOnePinDialog_Title, null,
				Messages.DefineFBDecisionTwoPinDialog_Info, MessageDialog.INFORMATION, 0,
				Messages.DefineFBDecisionTwoPinDialog_CreateReaction,
				Messages.DefineFBDecisionTwoPinDialog_CreateGuarantee);
	}

	public boolean isReaction() {
		return pressedButtonId == CREATE_REACTION;
	}

	public boolean isGuarantee() {
		return pressedButtonId == CREATE_GUARANTEE;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		pressedButtonId = buttonId;
		super.buttonPressed(buttonId);
	}

}
