/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner
 *     - initial API and implementation and/or initial documentation
 *   Melanie Winter - extracted to common super class
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.fordiac.ide.gef.utilities.CellEditorLayoutFactory;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractCombinedCellEditor<T> extends TextCellEditor {
	private static final int NUM_COLUMNS = 4;
	private Composite container;
	private CCombo comboBox;
	private T element;

	protected AbstractCombinedCellEditor(final T element) {
		this.element = element;
	}

	protected AbstractCombinedCellEditor(final Composite parent) {
		super(parent, SWT.NONE);
	}

	protected AbstractCombinedCellEditor(final T element, final Composite parent) {
		this(element, parent, SWT.NONE);
	}

	protected AbstractCombinedCellEditor(final T element, final Composite parent, final int style) {
		super(parent, style);
		this.element = element;
	}

	protected CCombo getCombobox() {
		return comboBox;
	}

	@Override
	protected Control createControl(final Composite parent) {
		container = createContainer(parent);
		createComboBox(container);
		createText(container);
		return container;
	}

	private void createComboBox(final Composite parent) {
		comboBox = new CCombo(parent, getStyle());
		comboBox.setFont(parent.getFont());
		comboBox.setEditable(false);

		comboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(final SelectionEvent event) {
				applyEditorValueAndDeactivate();
			}

			@Override
			public void widgetSelected(final SelectionEvent event) {
				checkTextEnabled();
				editText();
			}
		});
		comboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				super.focusLost(e);
				if (!insideAnyEditorArea(e)) {
					AbstractCombinedCellEditor.this.focusLost();
				}
			}
		});
		comboBox.addListener(SWT.KeyDown, event -> {
			if (event.keyCode == SWT.ESC) {
				fireCancelEditor();
			}
		});
	}

	protected abstract void checkTextEnabled();

	protected abstract void editText();

	private void createText(final Composite parent) {
		final CLabel label1 = new CLabel(parent, getStyle());
		label1.setText("["); //$NON-NLS-1$
		text = new Text(parent, SWT.SINGLE);
		final CLabel label2 = new CLabel(parent, getStyle());
		label2.setText("]"); //$NON-NLS-1$
		parent.setBackground(label2.getBackground());
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				if (!insideAnyEditorArea(e)) {
					AbstractCombinedCellEditor.this.focusLost();
				}
			}
		});
		text.addListener(SWT.KeyDown, event -> {
			switch (event.keyCode) {
			case SWT.CR:
				focusLost();
				break;
			case SWT.ESC:
				fireCancelEditor();
				break;
			default:
				break;
			}
		});
	}

	private boolean insideAnyEditorArea(final FocusEvent e) {
		final Point cursorLocation = e.display.getCursorLocation();
		final Point containerRelativeCursor = container.getParent().toControl(cursorLocation);
		return container.getBounds().contains(containerRelativeCursor);
	}

	protected void populateComboBoxItems(final List<String> eventNames) {
		if (comboBox != null && eventNames != null) {
			comboBox.removeAll();
			for (final String item : eventNames) {
				comboBox.add(item);
			}
			setValueValid(true);
		}
	}

	protected abstract void initData();

	// gets the index that should be selected in the comboBox
	protected abstract int getIndexToSelect(final List<String> eventNames);

	public CCombo getCCombo() {
		return comboBox;
	}

	public Text getText() {
		return text;
	}

	public T getElement() {
		return element;
	}

	@Override
	public Object doGetValue() {
		return new String[] { String.valueOf(comboBox.getSelectionIndex()), text.getText() };
	}

	// setValue and getValue use different object types because
	// the EditPart needs to get the selection and the conditionExpression,
	// but the CellEditor needs the ECTransition to work.
	@Override
	protected void doSetValue(final Object value) {
		this.element = (T) value;
		initData();
	}

	@Override
	protected void doSetFocus() {
		comboBox.setFocus();
	}

	// make the fireCancleEditor publicly available for the direct edit manager
	@Override
	public void fireCancelEditor() {
		super.fireCancelEditor();
	}

	@Override
	protected void handleDefaultSelection(final SelectionEvent event) {
		if (!((Text) event.getSource()).getText().isEmpty()) {
			super.handleDefaultSelection(event);
		}
	}

	private static Composite createContainer(final Composite parent) {
		final Composite newContainer = new Composite(parent, SWT.NONE);
		newContainer.setBackground(parent.getBackground());
		newContainer.setForeground(parent.getForeground());
		// set layout with minimal space to keep the cell editor compact
		newContainer.setLayout(CellEditorLayoutFactory.getNewGridZeroLayout(NUM_COLUMNS));
		return newContainer;
	}

	@Override
	protected void focusLost() {
		applyEditorValueAndDeactivate();
	}

	void applyEditorValueAndDeactivate() {
		// must set the selection before getting value

		final Object newValue = doGetValue();
		markDirty();
		final boolean isValid = isCorrect(newValue);
		setValueValid(isValid);

		if (!isValid) {
			handleInvalidSelection();
		}

		fireApplyEditorValue();
		deactivate();
	}

	protected void handleInvalidSelection() {
		final int selection = comboBox.getSelectionIndex();
		final String[] items = comboBox.getItems();
		// Only format if the 'index' is valid
		if (items.length > 0 && selection >= 0 && selection < items.length) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(), items[selection]));
		} else {
			// Since we don't have a valid index, assume we're using an
			// 'edit'
			// combo so format using its text value
			setErrorMessage(MessageFormat.format(getErrorMessage(), comboBox.getText()));
		}
	}

}
