/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz and others
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.ui;

import org.eclipse.fordiac.ide.fb.interpreter.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SelectAdapterEventDialog extends MessageDialog {
	private CCombo inputEventCombo;
	private String event;
	private final AdapterDeclaration aDecl;

	public SelectAdapterEventDialog(final Shell parentShell, final AdapterDeclaration aDecl) {
		super(parentShell, Messages.SelectAdapterEventDialog_0, null,
				Messages.SelectAdapterEventDialog_1, MessageDialog.INFORMATION, 0,
				Messages.SelectAdapterEventDialog_2);
		this.aDecl = aDecl;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.NONE);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		final GridLayout layout = new GridLayout(2, false);
		dialogArea.setLayout(layout);
		final Label label = new Label(dialogArea, SWT.None);
		label.setText(Messages.SelectAdapterEventDialog_3);
		inputEventCombo = ComboBoxWidgetFactory.createCombo(dialogArea);

		final String[] eventLabels = aDecl.getAdapterFB().getInterface().getEventOutputs().stream().map(Event::getName)
				.toArray(String[]::new);
		inputEventCombo.setItems(eventLabels);
		inputEventCombo.select(0);
		return dialogArea;

	}

	@Override
	protected void buttonPressed(final int buttonId) {
		event = inputEventCombo.getText();
		super.buttonPressed(buttonId);
	}

	public String getSelectedEvent() {
		return event;
	}
}
