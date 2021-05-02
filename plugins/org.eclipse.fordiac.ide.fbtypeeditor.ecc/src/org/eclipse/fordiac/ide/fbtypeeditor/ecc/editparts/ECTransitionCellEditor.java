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
 *   Lisa Sonnleithner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class ECTransitionCellEditor extends TextCellEditor {

	private Composite container;
	private CCombo comboBox;
	private ECTransition transition = null;

	public ECTransitionCellEditor() {
		super();
	}

	public ECTransitionCellEditor(final Composite parent) {
		this(parent, SWT.NONE);
	}

	public ECTransitionCellEditor(final Composite parent, final int style) {
		super(parent, style);
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
					ECTransitionCellEditor.this.focusLost();
				}
			}
		});
		comboBox.addListener(SWT.KeyDown, event -> {
			if (event.keyCode == SWT.ESC) {
				fireCancelEditor();
			}
		});
	}

	void checkTextEnabled() {
		if (null != comboBox && null != text) {
			text.setVisible(
					!comboBox.getItem(comboBox.getSelectionIndex()).equals(ECCContentAndLabelProvider.ONE_CONDITION));
		}
	}

	private void editText() {
		if (comboBox.getItem(comboBox.getSelectionIndex()).equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			text.setText("");
		}
	}

	private void createText(final Composite parent) {
		final CLabel label1 = new CLabel(parent, getStyle());
		label1.setText("[");
		text = new Text(parent, SWT.SINGLE);
		final CLabel label2 = new CLabel(parent, getStyle());
		label2.setText("]");
		parent.setBackground(label2.getBackground());
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				if (!insideAnyEditorArea(e)) {
					ECTransitionCellEditor.this.focusLost();
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

	private void populateComboBoxItems(final List<String> eventNames) {
		if (comboBox != null && eventNames != null) {
			comboBox.removeAll();
			for (final String item : eventNames) {
				comboBox.add(item);
			}
			setValueValid(true);
		}
	}

	private void initData() {
		if (null == transition) {
			return;
		}
		final List<String> eventNames = ECCContentAndLabelProvider
				.getTransitionConditionEventNames(transition.getECC().getBasicFBType());

		final int selected = getIndexToSelect(eventNames);

		populateComboBoxItems(eventNames);
		comboBox.select(selected);
		if (!eventNames.get(selected).equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			text.setText(transition.getConditionExpression());
		}
		checkTextEnabled();

	}

	// gets the index that should be selected in the comboBox
	protected int getIndexToSelect(final List<String> eventNames) {
		if (transition.getConditionEvent() != null) {
			return eventNames.indexOf(transition.getConditionEvent().getName());
		}
		if (transition.getConditionExpression().equals(ECCContentAndLabelProvider.ONE_CONDITION)) {
			return eventNames.indexOf(ECCContentAndLabelProvider.ONE_CONDITION);
		}
		return eventNames.size() - 1;
	}

	public CCombo getCCombo() {
		return comboBox;
	}

	public Text getText() {
		return text;
	}

	public ECTransition getTransition() {
		return transition;
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
		Assert.isTrue((value instanceof ECTransition));
		this.transition = (ECTransition) value;
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
		final GridLayout contLayout = new GridLayout(4, false);
		contLayout.horizontalSpacing = 0;
		contLayout.marginTop = 0;
		contLayout.marginBottom = 0;
		contLayout.marginWidth = 0;
		contLayout.marginHeight = 0;
		contLayout.verticalSpacing = 0;
		contLayout.horizontalSpacing = 0;
		newContainer.setLayout(contLayout);
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
