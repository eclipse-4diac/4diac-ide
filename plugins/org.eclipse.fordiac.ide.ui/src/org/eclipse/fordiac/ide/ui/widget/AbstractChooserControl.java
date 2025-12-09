/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted from DirectoryChooser Control
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Base class for a chooser control, which has combined a text field and a
 * button.
 *
 */
public abstract class AbstractChooserControl extends Composite {

	public interface IChooserValueChanged {

		/**
		 * Value in the c hooser control has changed.
		 *
		 * @param newValue the new value in the chooser control
		 */
		void chooserChanged(String newValue);

	}

	private final List<IChooserValueChanged> listeners = new ArrayList<>();

	private Text text = null;

	/**
	 * Instantiates a new directory chooser control.
	 *
	 * @param parent the parent
	 * @param style  the style
	 * @param label  the label
	 */
	protected AbstractChooserControl(final Composite parent, final int style, final String label,
			final boolean labelInParent) {
		super(parent, style);
		initialize(label, labelInParent);
	}

	/**
	 * Initialize.
	 *
	 * @param labelInParent
	 */
	private void initialize(final String labelText, final boolean labelInParent) {
		GridLayoutFactory.fillDefaults().numColumns(3).margins(0, 5).applyTo(this);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).grab(true, false).applyTo(this);

		final Label label = new Label(labelInParent ? getParent() : this, SWT.NONE);
		label.setText(labelText);
		if (labelInParent) {
			label.moveAbove(this);
		}

		text = new Text(this, SWT.BORDER);
		GridDataFactory.fillDefaults().align(GridData.FILL, GridData.CENTER).grab(true, false).applyTo(text);

		final Button button = new Button(this, SWT.NONE);
		button.setText(FordiacMessages.DirectoryChooserControl_LABEL_Browse);
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {

				final String selectedValue = openChooserDialog(label.getText(), text.getText());

				if (selectedValue != null) {
					text.setText(selectedValue);
					notifyListeners();
				}

			}

		});

		text.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				notifyListeners();
			}
		});

		text.addFocusListener(new FocusAdapter() {

			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt
			 * .events.FocusEvent)
			 */
			@Override
			public void focusLost(final org.eclipse.swt.events.FocusEvent e) {
				notifyListeners();
			}

		});

	}

	protected abstract String openChooserDialog(final String labelText, final String value);

	/**
	 * Adds the chooser changed listener.
	 *
	 * @param listener the listener
	 */
	public void addChooserValueChangedListener(final IChooserValueChanged listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the chooser changed listener.
	 *
	 * @param listener the listener
	 */
	public void removeChooserChangedListener(final IChooserValueChanged listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify chooser listeners.
	 */
	private void notifyListeners() {
		for (final IChooserValueChanged l : listeners) {
			l.chooserChanged(text.getText());
		}
	}

	public String getValue() {
		return text.getText();
	}

	/**
	 * Sets the value
	 *
	 * @param newValue the new value for the chooser dialog
	 */
	public void setValue(final String newValue) {
		text.setText(newValue);
	}
}
