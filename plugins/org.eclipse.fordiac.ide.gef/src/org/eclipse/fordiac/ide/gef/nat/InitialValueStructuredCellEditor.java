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

import java.util.Optional;

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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InitialValueStructuredCellEditor<T> extends InitialValueCellEditor<T> {

	private Composite composite;
	private StyledText textControl;
	private Button dialogButton;

	public InitialValueStructuredCellEditor(final IRowDataProvider<? extends T> dataProvider,
			final InitialValueStructuredElementAccessor<T> elementAccessor) {
		super(dataProvider, elementAccessor);
	}

	public InitialValueStructuredCellEditor(final IRowDataProvider<? extends T> dataProvider,
			final InitialValueStructuredElementAccessor<T> elementAccessor, final boolean moveSelectionOnEnter) {
		super(dataProvider, elementAccessor, moveSelectionOnEnter);
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
		focusListener = new CompositeFocusListener();
		return textControl;
	}

	protected void openDialog() {
		try {
			final String initialValue = FordiacMessages.ValueTooLarge.equals(getEditorValue()) ? null
					: getEditorValue();
			openVariableDialog(composite.getShell(), getElementAccessor().getReferenceElement(getRowObject()),
					initialValue).ifPresent(this::setEditorValue);
		} finally {
			if (textControl != null && !textControl.isDisposed()) {
				textControl.forceFocus();
			}
		}
	}

	protected Optional<String> openVariableDialog(final Shell shell, final ITypedElement element,
			final String initialValue) {
		return VariableDialog.open(shell, element, initialValue);
	}

	@Override
	public void addEditorControlListeners() {
		super.addEditorControlListeners();
		if (editMode == EditModeEnum.INLINE) {
			if (textControl != null && !textControl.isDisposed()) {
				textControl.addFocusListener(focusListener);
			}
			if (dialogButton != null && !dialogButton.isDisposed()) {
				dialogButton.addFocusListener(focusListener);
			}
		}
	}

	@Override
	public void removeEditorControlListeners() {
		super.removeEditorControlListeners();
		if (textControl != null && !textControl.isDisposed()) {
			textControl.removeFocusListener(focusListener);
		}
		if (dialogButton != null && !dialogButton.isDisposed()) {
			dialogButton.removeFocusListener(focusListener);
		}
	}

	@Override
	public Control getEditorControl() {
		return composite;
	}

	@Override
	protected InitialValueStructuredElementAccessor<T> getElementAccessor() {
		return (InitialValueStructuredElementAccessor<T>) super.getElementAccessor();
	}

	protected class CompositeFocusListener extends InlineFocusListener {

		@Override
		public void focusLost(final FocusEvent e) {
			final Point cursorLocation = Display.getDefault().getCursorLocation();
			final Point relativeCursorLocation = dialogButton.getParent().toControl(cursorLocation);
			if (!dialogButton.getBounds().contains(relativeCursorLocation)) {
				super.focusLost(e);
			}
		}
	}
}
