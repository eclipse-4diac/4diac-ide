/*******************************************************************************
 * Copyright (c) 2008, 2011, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * The Class DirectoryChooserControl.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class DirectoryChooserControl extends Composite {

	/** The text. */
	private Text text = null;

	/** The label text. */
	private final String labelText;

	/**
	 * Instantiates a new directory chooser control.
	 * 
	 * @param parent the parent
	 * @param style the style
	 * @param label the label
	 */
	public DirectoryChooserControl(final Composite parent, final int style,
			final String label) {
		super(parent, style);
		this.labelText = label;
		initialize(false);
	}

	/**
	 * Initialize.
	 */
	private void initialize(boolean useLabel) {
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 0;
		CLabel cLabel = new CLabel(this, SWT.NONE);
		if (useLabel) {
			cLabel.setText(this.labelText);
		} else {
			cLabel.setText(Messages.DirectoryChooserControl_LABEL_ChooseDirectory);
		}
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(gridData);
		Button button = new Button(this, SWT.NONE);
		button.setText(Messages.DirectoryChooserControl_LABEL_Browse);
		button
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					@Override
					public void widgetSelected(
							final org.eclipse.swt.events.SelectionEvent e) {
						DirectoryDialog dialog = new DirectoryDialog(Display
								.getDefault().getActiveShell(), SWT.SAVE);
						String dir = text.getText();

						dialog.setText(labelText);
						dialog
								.setMessage(Messages.DirectoryChooserControl_LABEL_SelectdDirectoryDialogMessage);

						if (!dir.equals("")) { //$NON-NLS-1$
							dialog.setFilterPath(dir);
						}

						String selectedDirectory = dialog.open();

						if (selectedDirectory != null) {
							text.setText(selectedDirectory);
							notifyDirectoryListeners();
						}

					}
				});

		text.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				notifyDirectoryListeners();
			}
		});

		text.addFocusListener(new FocusAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt
			 * .events.FocusEvent)
			 */
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				notifyDirectoryListeners();
			}

		});

		this.setLayout(gridLayout);
	}

	/** The listeners. */
	private List<IDirectoryChanged> listeners = new ArrayList<>();

	/**
	 * Adds the directory changed listener.
	 * 
	 * @param listener the listener
	 */
	public void addDirectoryChangedListener(final IDirectoryChanged listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the directory changed listener.
	 * 
	 * @param listener the listener
	 */
	public void removeDirectoryChangedListener(final IDirectoryChanged listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notify directory listeners.
	 */
	private void notifyDirectoryListeners() {
		for (Iterator<IDirectoryChanged> iter = listeners.iterator(); iter
				.hasNext();) {
			IDirectoryChanged l = iter.next();
			l.directoryChanged(text.getText());
		}
	}

	/**
	 * Gets the directory.
	 * 
	 * @return the directory
	 */
	public String getDirectory() {
		return text.getText();
	}

	/**
	 * Sets the directory.
	 * 
	 * @param dir the new directory
	 */
	public void setDirectory(final String dir) {
		text.setText(dir);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
