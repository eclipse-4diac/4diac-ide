/*******************************************************************************
 * Copyright (c) 2021, 2023 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *  Paul Pavlicek
 *     - cleanup and extracting code, added random generation
 *  Felix Roithmayr
 *     - added extra support for context menu entry
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RecordSequenceDialog extends MessageDialog {
	private static final int DEFAULT_RANDOMCOUNT = 3;
	private Text inputEventText;
	private Text inputParameterText;
	private Text inputCount;
	private Button appendCheckbox;
	private Button randomEventsCheckbox;
	private Button randomParametersCheckbox;
	private CCombo inputStartStateCombo;
	private final List<String> events;
	private final List<String> parameters;
	private boolean append;
	private boolean isEventBoxChecked;
	private boolean isParameterBoxChecked;
	private int count;
	private String startState;
	private Label labeldialog;

	private final FBType type;

	public RecordSequenceDialog(final Shell parentShell, final List<String> events, final List<String> parameters,
			final FBType type) {
		super(parentShell, Messages.RecordSequenceDialog_Title, null, Messages.RecordSequenceDialog_Info,
				MessageDialog.INFORMATION, 0, Messages.RecordSequenceDialog_Button);
		this.events = events;
		this.parameters = parameters;
		this.type = type;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.NONE);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		final GridLayout layout = new GridLayout(1, false);
		dialogArea.setLayout(layout);

		final Composite dialogTop = new Composite(dialogArea, SWT.NONE);
		dialogTop.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		dialogTop.setLayout(new GridLayout(3, false));

		randomEventsCheckbox = new Button(dialogTop, SWT.CHECK);
		randomEventsCheckbox.setText(Messages.RecordSequenceDialog_AddRandomEvents);
		randomEventsCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		randomEventsCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				inputEventText.setEnabled(!randomEventsCheckbox.getSelection());
				inputParameterText.setEnabled(!randomEventsCheckbox.getSelection());
				inputCount.setVisible(randomEventsCheckbox.getSelection());
				labeldialog.setVisible(randomEventsCheckbox.getSelection());

			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// is never called
			}
		});
		labeldialog = new Label(dialogTop, SWT.None);
		labeldialog.setText("n="); //$NON-NLS-1$
		labeldialog.setVisible(false);

		inputCount = new Text(dialogTop, SWT.NONE);
		inputCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		inputCount.setVisible(false);

		randomParametersCheckbox = new Button(dialogTop, SWT.CHECK);
		randomParametersCheckbox.setText(Messages.RecordSequenceDialog_AddRandomValues);
		randomParametersCheckbox.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

		final Group group = new Group(dialogArea, SWT.NONE);
		group.setText(Messages.RecordSequenceDialog_ProvideInputs);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(group, SWT.None);
		label.setText(Messages.RecordSequenceDialog_InputEvents);

		inputEventText = new Text(group, SWT.LEFT);
		inputEventText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		inputEventText.setToolTipText(Messages.RecordSequenceDialog_SpecifyEventsTooltipText);

		label = new Label(group, SWT.None);
		label.setText(Messages.RecordSequenceDialog_InitialData);

		inputParameterText = new Text(group, SWT.LEFT);
		inputParameterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		inputParameterText.setToolTipText(Messages.RecordSequenceDialog_SpecifyParametersTooltipText);
		// $NON-NLS-1$

		label = new Label(group, SWT.None);
		label.setText(Messages.RecordSequenceDialog_StartState);

		if (type instanceof final BasicFBType bfbType) {
			inputStartStateCombo = ComboBoxWidgetFactory.createCombo(group);
			inputStartStateCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			final String[] startnames = Stream
					.concat(bfbType.getECC().getECState().stream().map(ECState::getName), Stream.of("")) //$NON-NLS-1$
					.toArray(String[]::new);
			inputStartStateCombo.setItems(startnames);
			inputStartStateCombo.select(0);
		} else {
			inputStartStateCombo.setEnabled(false);
		}

		appendCheckbox = new Button(group, SWT.CHECK);
		appendCheckbox.setText("Append Sequence to current Record"); //$NON-NLS-1$
		appendCheckbox.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).span(2, 1).create());

		return dialogArea;
	}

	@Override
	protected void buttonPressed(final int buttonId) {
		events.addAll(getEvents());
		parameters.addAll(getParameters());
		append = appendCheckbox.getSelection();
		isEventBoxChecked = randomEventsCheckbox.getSelection();
		isParameterBoxChecked = randomParametersCheckbox.getSelection();
		if (!getCountText().isBlank()) {
			count = Integer.parseInt(getCountText());
		} else {
			count = DEFAULT_RANDOMCOUNT;

		}
		final int selectedStartState = inputStartStateCombo.getSelectionIndex();
		if (selectedStartState == -1) { // nothing selected
			startState = "START"; //$NON-NLS-1$
		} else {
			startState = inputStartStateCombo.getItem(selectedStartState);
		}
		super.buttonPressed(buttonId);
	}

	private List<String> getEvents() {
		return ServiceSequenceUtils.splitAndCleanList(inputEventText.getText(), ";"); //$NON-NLS-1$
	}

	private List<String> getParameters() {
		return ServiceSequenceUtils.splitAndCleanList(inputParameterText.getText(), ";"); //$NON-NLS-1$
	}

	private String getCountText() {
		return (inputCount.getText());
	}

	public boolean isAppend() {
		return append;
	}

	public boolean isRandomEventBoxChecked() {
		return isEventBoxChecked;
	}

	public boolean isRandomParameterBoxChecked() {
		return isParameterBoxChecked;
	}

	public int getCount() {
		return count;
	}

	public String getStartState() {
		return startState;
	}
}
