/*******************************************************************************
 * Copyright (c) 2008, 2012, 2014 Profactor GmbH, fortiss GmbH
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
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

/**
 * The Class FileChooserControl.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FileChooserControl extends Composite {


	/** The text. */
	private Text text = null;

	/** The label text. */
	private final String labelText;

	private final String[] filterNames;
	private final String[] filterExtensions;

	/**
	 * The Constructor.
	 * 
	 * @param parent the parent
	 * @param style the style
	 * @param label the label
	 * @param filterNames the filter names
	 * @param filterExtensions the filter extensions
	 */
	public FileChooserControl(final Composite parent, final int style,
			final String label, String[] filterNames, String[] filterExtensions) {
		super(parent, style);
		this.labelText = label;
		this.filterNames = filterNames.clone();
		this.filterExtensions = filterExtensions.clone();
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 0;
		CLabel cLabel = new CLabel(this, SWT.NONE);
		cLabel.setText(labelText);
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(gridData);
		Button button = new Button(this, SWT.NONE);
		button.setText("Select");
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
				FileDialog dialog = new FileDialog(Display.getDefault()
						.getActiveShell(), SWT.OPEN);
				String dir = text.getText();

				dialog.setText(labelText);
				dialog.setFilterExtensions(filterExtensions);
				dialog.setFilterNames(filterNames);

				if (!dir.equals("")) { //$NON-NLS-1$
					dialog.setFilterPath(dir);
				}

				String selectedFile = dialog.open();
				if (selectedFile != null) {
					text.setText(selectedFile);
					notifyFileListeners();
				}
			}
		});

		text.addTraverseListener(new TraverseListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.TraverseListener#keyTraversed(org.eclipse
			 * .swt.events.TraverseEvent)
			 */
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					notifyFileListeners();
				}
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
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				notifyFileListeners();
			}

		});

		this.setLayout(gridLayout);
	}

	/** The listeners. */
	private List<IFileChanged> listeners = new ArrayList<>();

	/**
	 * Adds the directory changed listener.
	 * 
	 * @param listener the listener
	 */
	public void addFileChangedListener(final IFileChanged listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the directory changed listener.
	 * 
	 * @param listener the listener
	 */
	public void removeFileChangedListener(final IFileChanged listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notify directory listeners.
	 */
	private void notifyFileListeners() {
		for (Iterator<IFileChanged> iter = listeners.iterator(); iter.hasNext();) {
			IFileChanged l = iter.next();
			l.fileChanged(text.getText());
		}
	}

	/**
	 * Gets the directory.
	 * 
	 * @return the directory
	 */
	public String getFile() {
		return text.getText();
	}

	/**
	 * Sets the directory.
	 * 
	 * @param dir the new directory
	 */
	public void setFile(final String dir) {
		if (dir != null && !dir.equals(text.getText())) {
			text.setText(dir);
			notifyFileListeners();
		} 
	}
} // @jve:decl-index=0:visual-constraint="10,10"
