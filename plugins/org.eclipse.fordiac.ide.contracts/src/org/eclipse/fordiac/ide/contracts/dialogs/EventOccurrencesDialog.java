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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EventOccurrencesDialog extends MessageDialog {

	private static final int OK = 0;
	private static final int LOAD_FILE = 1;
	private static final int LOAD_EXAMPLE = 2;

	private final Shell parentShell;
	private Text inputText;
	private List<EventOccurrence> eventOccurrences;
	private String formatError;

	public EventOccurrencesDialog(final Shell parentShell) {
		super(parentShell, Messages.EventOccurrence_Title, null, Messages.EventOccurrence_Info,
				MessageDialog.INFORMATION, 0, Messages.ContractCheck_OK, Messages.EventOccurrence_LoadFromFile,
				Messages.EventOccurrence_LoadExample, Messages.EventOccurrence_Cancel);
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
		return dialogArea;
	}

	private void loadFromFile() {
		final FileDialog dialog = new FileDialog(parentShell);
		final String fname = dialog.open();
		if (fname == null) {
			return;
		}
		try (FileInputStream fstream = new FileInputStream(fname)) {
			final String ftext = new String(fstream.readAllBytes());
			inputText.setText(ftext);
		} catch (final Exception ex) {
			// could display an error message here
		}
	}

	private void loadExample() {
		inputText.setText("""
				App.SubApp.Q 8ms
				App.SubApp3.EO 10ns
				App.SubApp2.EI 25us
				App.SubApp.INIT 1s
				App.SubApp.EI 6ms ID3
				App.SubApp4.P 4ms SomeID
				"""); //$NON-NLS-1$
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		switch (buttonId) {
		case OK:
			eventOccurrences = createEOList(inputText.getText());
			if (formatError != null) {
				MessageDialog.openError(parentShell, Messages.EventOccurrence_Error_Title,
						Messages.EventOccurrence_Error_Info + formatError);
			} else {
				super.buttonPressed(buttonId);
			}
			break;
		case LOAD_FILE:
			loadFromFile();
			break;
		case LOAD_EXAMPLE:
			loadExample();
			break;
		default: // cancel
			super.buttonPressed(buttonId);
		}
	}

	private List<EventOccurrence> createEOList(final String text) {
		formatError = null;
		final List<EventOccurrence> eos = new ArrayList<>();
		final String[] lines = text.split("\n"); //$NON-NLS-1$

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].isBlank()) {
				continue; // allow empty lines
			}
			final EventOccurrence eo = parseLine(lines[i], i);
			if (eo == null) {
				return List.of();
			}
			eos.add(eo);
		}
		return eos;
	}

	@SuppressWarnings("boxing")
	private EventOccurrence parseLine(final String line, final int lineIdx) {
		// Grammar:
		// {QualifiedEventName " " Number Unit [" " EventID] "\n"}
		final String[] parts = line.split(" "); //$NON-NLS-1$
		String eventID;

		if (parts.length == 2) {
			eventID = ""; //$NON-NLS-1$
		} else if (parts.length == 3) {
			eventID = parts[2].strip();
		} else {
			formatError = Messages.EventOccurrence_Format_Error_General.formatted(lineIdx);
			return null;
		}
		// TODO: maybe error if event pin with such a name does not exist in system?
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
			formatError = Messages.EventOccurrence_Format_Error_Time.formatted(lineIdx);
			return null;
		}

		final double value;
		final String part = eventTime.substring(0, eventTime.length() - unit.getLiteral().length());
		try {
			value = Double.parseDouble(part);
		} catch (final Exception e) {
			formatError = Messages.EventOccurrence_Format_Error_Number.formatted(lineIdx, part);
			return null;
		}
		if (value < 0) {
			formatError = Messages.EventOccurrence_Format_Error_Negative.formatted(lineIdx);
			return null;
		}

		final double time = Utils.getInNs(value, unit);
		return new EventOccurrence(eventName, time, eventID);
	}
}
