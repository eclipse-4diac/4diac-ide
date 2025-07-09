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
import org.eclipse.fordiac.ide.contracts.Messages;
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
	private String formatError;

	public EventOccurrencesDialog(final Shell parentShell) {
		super(parentShell, Messages.EventOccurrence_Title, null, Messages.EventOccurrence_Info,
				MessageDialog.INFORMATION, 0, Messages.ContractCheck_OK, Messages.EventOccurrence_Cancel);
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
		btn.setText(Messages.EventOccurrence_LoadFromFile);
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
			MessageDialog.openError(parentShell, Messages.EventOccurrence_Error_Title,
					Messages.EventOccurrence_Error_Info + formatError);
			return; // don't allow pressing OK if text has invalid format
		}
		super.buttonPressed(buttonId);
	}

	@SuppressWarnings("boxing")
	private List<EventOccurrence> createEOList(final String text) {
		// # Grammar:
		// {QualifiedEventName " " Number Unit "\n"}
		// e.g.:
		// App.SubApp.EI 2s
		// App.SubApp.EO 10ms
		// ...
		formatError = null;
		final List<EventOccurrence> eos = new ArrayList<>();
		final String[] lines = text.split("\n"); //$NON-NLS-1$

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].isBlank()) {
				continue; // allow empty lines
			}
			final String[] parts = lines[i].split(" "); //$NON-NLS-1$

			if (parts.length != 2) {
				formatError = Messages.EventOccurrence_Format_Error_General.formatted(i);
				return null;
			}
			// TODO: maybe error if event pin with such a name does not exist?
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
				formatError = Messages.EventOccurrence_Format_Error_Time.formatted(i);
				return null;
			}

			final double value;
			final String part = eventTime.substring(0, eventTime.length() - unit.getLiteral().length());
			try {
				value = Double.parseDouble(part);
			} catch (final Exception e) {
				formatError = Messages.EventOccurrence_Format_Error_Number.formatted(i, part);
				return null;
			}
			if (value < 0) {
				formatError = Messages.EventOccurrence_Format_Error_Negative.formatted(i);
				return null;
			}

			final double time = Utils.getInNs(value, unit);
			eos.add(new EventOccurrence(eventName, time));
		}
		return eos;
	}
}
