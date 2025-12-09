/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class InitialValueStructuredCellEditor extends InitialValueCellEditor {

	private Control textControl;
	private boolean variableDialogOpen;
	private boolean hasFocus = true;

	public InitialValueStructuredCellEditor(final Composite parent, final VarDeclaration varDeclaration) {
		super(parent, varDeclaration);
	}

	public InitialValueStructuredCellEditor(final Composite parent, final VarDeclaration varDeclaration,
			final int style) {
		super(parent, varDeclaration, style);
	}

	@Override
	protected Control createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		textControl = super.createControl(composite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textControl);
		textControl.addFocusListener(FocusListener.focusGainedAdapter(event -> hasFocus = true));
		final Button dialogButton = new Button(composite, SWT.FLAT);
		dialogButton.setText("\u2026"); //$NON-NLS-1$
		dialogButton.addFocusListener(FocusListener.focusGainedAdapter(event -> hasFocus = true));
		dialogButton.addFocusListener(FocusListener.focusLostAdapter(event -> focusLost()));
		dialogButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> openDialog()));
		GridDataFactory.swtDefaults().applyTo(dialogButton);
		return composite;
	}

	protected void openDialog() {
		try {
			variableDialogOpen = true;
			final String initialValue = FordiacMessages.ValueTooLarge.equals(text.getText()) ? null : text.getText();
			VariableDialog.open(getControl().getShell(), getVarDeclaration(), initialValue).ifPresent(text::setText);
		} finally {
			textControl.forceFocus();
			variableDialogOpen = false;
		}
	}

	@Override
	protected void focusLost() {
		// delay focus lost in case we open a dialog or regain focus
		hasFocus = false;
		Display.getCurrent().timerExec(100, () -> {
			if (!variableDialogOpen && !hasFocus) {
				super.focusLost();
			}
		});
	}
}
