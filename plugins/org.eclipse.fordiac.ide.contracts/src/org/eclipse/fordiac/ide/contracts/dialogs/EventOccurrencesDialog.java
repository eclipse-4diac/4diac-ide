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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contractSpec.Unit;
import org.eclipse.fordiac.ide.contracts.EventOccurrence;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EventOccurrencesDialog extends MessageDialog {

	private final Shell parentShell;
	private Text inputText;
	private List<EventOccurrence> eventOccurrences;

	public EventOccurrencesDialog(final Shell parentShell) {
		super(parentShell, "Enter event occurrences", null, "Info message with more details... (TODO)",
				MessageDialog.INFORMATION, 0, "OK", "Cancel");
		this.parentShell = parentShell;
	}

	public List<EventOccurrence> getEventOccurrences() {
		return eventOccurrences;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		inputText = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		inputText.setLayoutData(gridData);
		gridData.heightHint = 10 * inputText.getLineHeight();

		final Button btn = new Button(parent, 0);
		btn.setText("Load from file");
		btn.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final FileDialog dialog = new FileDialog(parentShell);
				final String fname = dialog.open();
				if (fname == null) {
					return;
				}
				try (FileInputStream fstream = new FileInputStream(fname)) {
					final String ftext = new String(fstream.readAllBytes());
					inputText.setText(ftext);
				} catch (final Exception ex) {
					return;
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do
			}
		});
		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		eventOccurrences = createEOList(inputText.getText());

		if (eventOccurrences == null && buttonId == 0) {
			// don't allow pressing OK if text has invalid format
			MessageDialog.openError(parentShell, "Format Error", "The entered text does not have the valid format.");
			return;
		}
		super.buttonPressed(buttonId);
	}

	// # Grammar:
	// {QualifiedEventName " " Number Unit "\n"}
	// e.g.:
	// App.SubApp.EI 10ms
	// App.SubApp.EO 20us
	// ...
	private static List<EventOccurrence> createEOList(final String text) {
		final String[] lines = text.split("\n"); //$NON-NLS-1$
		final List<EventOccurrence> eos = new ArrayList<>();

		for (final String line : lines) {
			if (line.isBlank()) {
				continue; // allow empty lines
			}
			final String[] parts = line.split(" "); //$NON-NLS-1$

			if (parts.length != 2) {
				return null;
			}
			// TODO: error if event pin with such a name does not exist
			final String eventName = parts[0].strip();
			final String eventTime = parts[1].strip();

			final Unit unit;
			if (eventTime.endsWith("ns")) { //$NON-NLS-1$
				unit = Unit.NS;
			} else if (eventTime.endsWith("us")) { //$NON-NLS-1$
				unit = Unit.US;
			} else if (eventTime.endsWith("ms")) { //$NON-NLS-1$
				unit = Unit.MS;
			} else if (eventTime.endsWith("s")) { //$NON-NLS-1$
				unit = Unit.S;
			} else {
				return null;
			}

			final double value;
			try {
				final String part = eventTime.substring(0, eventTime.length() - unit.getLiteral().length());
				value = Double.parseDouble(part);
			} catch (final Exception e) {
				return null; // double parse error
			}
			if (value < 0) {
				return null; // all values must be greater 0
			}

			final double time = Utils.getInNs(value, unit);
			eos.add(new EventOccurrence(eventName, time));
		}
		return eos;
	}
}
