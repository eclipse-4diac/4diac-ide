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

import java.util.List;

import org.eclipse.fordiac.ide.application.utilities.IntervalVerifyListener;
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

public class DefineFBReactionThreePinDialog extends ContractElementDialog {
	private static final int NUM_COLUMNS = 3;
	List<Event> outputEvents;

	public DefineFBReactionThreePinDialog(final Shell parentShell, final Event inputEvent,
			final List<Event> outputEvents) {
		super(parentShell, inputEvent, Messages.DefineFBReactionOnePinDialog_Title,
				Messages.DefineFBReactionOnePinDialog_Info);
		this.outputEvents = outputEvents;

	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		final Composite dialogArea = (Composite) super.createCustomArea(parent);
		final Group group = new Group(dialogArea, SWT.FILL);

		group.setText(Messages.DefineFBReactionThreePinDialog_DefineGuarantee);
		group.setLayout(new GridLayout(NUM_COLUMNS, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);

		label.setText("After " + pinFrom.getName() + " the events " + ContractKeywords.EVENTS_OPEN //$NON-NLS-1$//$NON-NLS-2$
				+ outputEvents.get(0).getName() + " "  //$NON-NLS-1$
				+ outputEvents.get(0).getName() + ContractKeywords.EVENTS_CLOSE);
		label.setLayoutData(GridDataFactory.fillDefaults().span(NUM_COLUMNS, 1).grab(true, true).create());

		label = new Label(group, SWT.None);
		label.setText(ContractKeywords.OCCURS + " " + ContractKeywords.WITHIN); //$NON-NLS-1$

		inputTimeText = new Text(group, SWT.RIGHT);
		inputTimeText.addListener(SWT.KeyDown, new IntervalVerifyListener(inputTimeText));
		inputTimeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		label = new Label(group, SWT.None);
		label.setText(" " + ContractKeywords.UNIT_OF_TIME); //$NON-NLS-1$

		return dialogArea;
	}

}
