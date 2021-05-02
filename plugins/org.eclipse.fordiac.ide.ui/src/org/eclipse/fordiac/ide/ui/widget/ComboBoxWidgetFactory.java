/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ComboBoxWidgetFactory {

	private static final class ComboTypeaheadKeyListener implements KeyListener {
		private static final int SEQUENCE_CLEAR_TIME_OUT = 1000;

		private final CCombo combo;
		private final StringBuilder keySequence = new StringBuilder();
		private long lastKeyTimeStamp = 0;

		private ComboTypeaheadKeyListener(final CCombo combo) {
			this.combo = combo;
		}

		@Override
		public void keyReleased(final KeyEvent e) {
			// Nothing to do here
		}

		@Override
		public void keyPressed(final KeyEvent keyEvent) {
			if (Character.isLetterOrDigit(keyEvent.character)) {
				// only process the key press when it is a letter or digit, ignore, e.g., arrow
				// keys
				checkTime();
				keySequence.append(Character.toLowerCase(keyEvent.character));
				findMatchingEntry(keySequence.toString(), keyEvent);
			}
		}

		public void findMatchingEntry(final String currentSequence, final KeyEvent keyEvent) {
			int index = 0;
			for (final String item : combo.getItems()) {
				if (item.toLowerCase().startsWith(currentSequence)) {
					// Prevent any other handler to use that key
					keyEvent.doit = false;
					final int oldIndex = combo.getSelectionIndex();
					if (oldIndex != index) {
						combo.select(index);
						final Event e = new Event();
						e.time = keyEvent.time;
						e.stateMask = keyEvent.stateMask;
						combo.notifyListeners(SWT.Selection, e);
					}
					break;
				}
				index++;
			}
		}

		public void checkTime() {
			final long now = System.currentTimeMillis();
			if ((now - lastKeyTimeStamp) > SEQUENCE_CLEAR_TIME_OUT) {
				keySequence.setLength(0);
			}
			lastKeyTimeStamp = now;
		}
	}

	/**
	 * Create a Combobox Cell editor for a table
	 *
	 * This helper method will apply certain style options so that on activation the
	 * combo box is always unfolded making the usage more intuitive and quicker.
	 *
	 * @param table the table this cell editor will be used in
	 * @param items the items shown in the combo box
	 * @param style the style of the combobox cell editor
	 * @return the newly created combobox cell editor
	 */
	public static ComboBoxCellEditor createComboBoxCellEditor(final Composite parent, final String[] items, final int style) {
		final ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(parent, items, style);
		cellEditor.setActivationStyle(
				ComboBoxCellEditor.DROP_DOWN_ON_KEY_ACTIVATION | ComboBoxCellEditor.DROP_DOWN_ON_MOUSE_ACTIVATION
				| ComboBoxCellEditor.DROP_DOWN_ON_PROGRAMMATIC_ACTIVATION
				| ComboBoxCellEditor.DROP_DOWN_ON_TRAVERSE_ACTIVATION);

		configureTypeaheadHandling((CCombo) cellEditor.getControl());

		return cellEditor;
	}

	public static void configureTypeaheadHandling(final CCombo combo) {
		combo.addKeyListener(new ComboTypeaheadKeyListener(combo));
	}

	public static CCombo createCombo(final TabbedPropertySheetWidgetFactory factory, final Composite parent) {
		final CCombo combo = factory.createCCombo(parent, SWT.SINGLE | SWT.READ_ONLY);
		configureTypeaheadHandling(combo);
		return combo;
	}

	public static CCombo createCombo(final Composite parent) {
		final CCombo combo = new CCombo(parent, SWT.SINGLE | SWT.READ_ONLY);
		configureTypeaheadHandling(combo);
		return combo;
	}

	public static ComboBoxCellEditor createComboBoxCellEditor(final Composite parent, final String[] items) {
		return createComboBoxCellEditor(parent, items, SWT.NONE);
	}

	private ComboBoxWidgetFactory() {
		throw new UnsupportedOperationException("Combobox Widget Factory should not be instantiated"); //$NON-NLS-1$
	}
}
