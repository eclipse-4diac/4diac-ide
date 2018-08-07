/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.controls;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class ReferenceChooserDialog.
 * 
 * @author mrooke
 */
public class ReferenceChooserDialog extends Dialog {

	private TableViewer tableViewer;
	private Object selected;

	private final IContentProvider contentProvider;
	private final IBaseLabelProvider labelProvider;

	// Constructor
	/**
	 * Instantiates a new reference chooser dialog.
	 * 
	 * @param contentProvider the content provider
	 * @param labelProvider the label provider
	 * @param shell the shell
	 */
	public ReferenceChooserDialog(final IContentProvider contentProvider,
			final IBaseLabelProvider labelProvider, final Shell shell) {
		super(shell);
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
	}

	/**
	 * Creates the main window's contents
	 * 
	 * @param parent
	 *            the main window
	 * @return Control
	 */
	@Override
	protected Control createContents(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		GridData fill = new GridData();
		fill.horizontalAlignment = SWT.FILL;
		fill.verticalAlignment = SWT.FILL;
		fill.heightHint = 150;

		tableViewer = new TableViewer(composite, SWT.SINGLE);
		tableViewer.getTable().setLayoutData(fill);
		// TableViewer
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setInput(new Object());
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(
							final SelectionChangedEvent event) {
						selected = ((StructuredSelection) event.getSelection())
								.getFirstElement();
					}

				});

		return super.createContents(parent);
	}

	/**
	 * Gets the selected.
	 * 
	 * @return the selected
	 */
	public Object getSelected() {
		return selected;
	}
}
