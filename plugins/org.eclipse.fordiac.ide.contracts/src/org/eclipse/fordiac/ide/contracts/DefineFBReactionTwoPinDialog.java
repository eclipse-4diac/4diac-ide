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

import org.eclipse.fordiac.ide.contracts.model.ContractKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DefineFBReactionTwoPinDialog extends ContractElementDialog {
	private static final int NUM_COLUMNS = 4;
	private final Event pinTo;

	public DefineFBReactionTwoPinDialog(final Shell parentShell, final Event pinFrom, final Event pinTo) {
		super(parentShell, pinFrom, Messages.DefineFBReactionTwoPinDialog_Title,
				Messages.DefineFBReactionTwoPinDialog_Info);
		this.pinTo = pinTo;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Composite dialogArea = (Composite) super.createCustomArea(parent);
		final Group group = new Group(dialogArea, SWT.FILL);

		group.setText(Messages.DefineFBReactionThreePinDialog_DefineGuarantee);
		group.setLayout(new GridLayout(NUM_COLUMNS, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);

		label.setText(ContractKeywords.REACTION + " " + ContractKeywords.EVENTS_OPEN + pinFrom.getName() //$NON-NLS-1$
				+ ContractKeywords.COMMA + pinTo.getName() + ContractKeywords.EVENTS_CLOSE);
		label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());

		label = new Label(group, SWT.None);
		label.setText(ContractKeywords.OCCURS + " " + ContractKeywords.WITHIN); //$NON-NLS-1$

		inputTimeText = new Text(group, SWT.RIGHT);
		inputTimeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		label = new Label(group, SWT.None);
		label.setText(" " + ContractKeywords.UNIT_OF_TIME); //$NON-NLS-1$

		return dialogArea;
	}

}