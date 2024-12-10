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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.gef.dialogs.VariableDialog;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class InitialValueStructuredCellEditor extends InitialValueCellEditor {

	protected final FocusListener compositeFocusListener = new CompositeFocusListener();

	private Composite composite;
	private StyledText textControl;
	private Button dialogButton;

	public InitialValueStructuredCellEditor(final IRowDataProvider<? extends ITypedElement> dataProvider) {
		super(dataProvider);
	}

	public InitialValueStructuredCellEditor(final IRowDataProvider<? extends ITypedElement> dataProvider,
			final boolean moveSelectionOnEnter) {
		super(dataProvider, moveSelectionOnEnter);
	}

	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		super.activateCell(parent, originalCanonicalValue);
		return composite;
	}

	@Override
	protected StyledText createEditorControl(final Composite parent, final int style) {
		composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		textControl = super.createEditorControl(composite, style);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textControl);
		dialogButton = new Button(composite, SWT.FLAT);
		dialogButton.setText("\u2026"); //$NON-NLS-1$
		dialogButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(event -> openDialog()));
		GridDataFactory.swtDefaults().applyTo(dialogButton);
		return textControl;
	}

	protected void openDialog() {
		try {
			if (focusListener instanceof final InlineFocusListener inlineFL) {
				inlineFL.handleFocusChanges = false;
			}
			final String initialValue = FordiacMessages.ValueTooLarge.equals(getEditorValue()) ? null
					: getEditorValue();
			VariableDialog.open(composite.getShell(), getRowObject(), initialValue).ifPresent(this::setEditorValue);
		} finally {
			textControl.forceFocus();
			if (focusListener instanceof final InlineFocusListener inlineFL) {
				inlineFL.handleFocusChanges = true;
			}
		}
	}

	@Override
	public void addEditorControlListeners() {
		super.addEditorControlListeners();
		if (editMode == EditModeEnum.INLINE) {
			if (textControl != null && !textControl.isDisposed()) {
				textControl.addFocusListener(compositeFocusListener);
			}
			if (dialogButton != null && !dialogButton.isDisposed()) {
				dialogButton.addFocusListener(compositeFocusListener);
			}
		}
	}

	@Override
	public void removeEditorControlListeners() {
		super.removeEditorControlListeners();
		if (textControl != null && !textControl.isDisposed()) {
			textControl.removeFocusListener(compositeFocusListener);
		}
		if (dialogButton != null && !dialogButton.isDisposed()) {
			dialogButton.removeFocusListener(compositeFocusListener);
		}
	}

	@Override
	public Control getEditorControl() {
		return composite;
	}

	protected class CompositeFocusListener implements FocusListener {

		private boolean hasFocus;

		@Override
		public void focusGained(final FocusEvent e) {
			hasFocus = true;
			focusListener.focusGained(e);
		}

		@Override
		public void focusLost(final FocusEvent e) {
			hasFocus = false;
			Display.getCurrent().timerExec(100, () -> {
				if (!hasFocus) {
					focusListener.focusLost(e);
				}
			});
		}
	}
}
