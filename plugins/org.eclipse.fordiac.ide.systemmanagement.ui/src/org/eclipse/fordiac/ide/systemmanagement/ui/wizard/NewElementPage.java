/*******************************************************************************
 * Copyright (c) 2011 - 2013, 2015 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.contentprovider.SystemContentProvider;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemLabelProvider;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The Class NewApplicationPage.
 * 
 * @author gebenh
 */
public abstract class NewElementPage extends WizardPage {

	/** The text. */
	private Text text;

	private AutomationSystem system;

	/**
	 * Instantiates a new new application page.
	 * 
	 * @param pageName
	 *            the page name
	 */
	protected NewElementPage(final String pageName) {
		super(pageName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());

		initializeDialogUnits(parent);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createProjectContainer(composite);
		createFileNameGroup(composite);

		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	/**
	 * Creates the file name group.
	 * 
	 * @param composite
	 *            the composite
	 */
	protected void createFileNameGroup(final Composite composite) {
		Label l = new Label(composite, SWT.NONE);
		l.setText(Messages.NewElementPage_ElementNameLabel);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				setPageComplete(validatePage());

			}

		});
		text.addVerifyListener(new IdentifierVerifyListener());

	}

	/** The tree viewer. */
	private TreeViewer treeViewer;

	/**
	 * Creates the project container.
	 * 
	 * @param parent
	 *            the parent
	 */
	protected void createProjectContainer(final Composite parent) {
		Label l = new Label(parent, SWT.NONE);
		l.setText(Messages.NewElementPage_ParentSystemLabel);
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		treeViewer = new TreeViewer(parent); // , SWT.MULTI | SWT.H_SCROLL |
		// SWT.V_SCROLL);
		treeViewer.setContentProvider(new SystemContentProvider());
		treeViewer.setLabelProvider(new SystemLabelProvider());

		treeViewer.setInput(new Object());
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		if (this.system != null) {
			treeViewer.setSelection(new StructuredSelection(system));
		}
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				setPageComplete(validatePage());
			}
		});
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and
	 *         <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage() {
		if (text == null || text.getText().isEmpty()) {
			setErrorMessage(Messages.NewElementPage_ErrorMessage_EmptyElementName);
			return false;
		}
		if (getSelectedSystem() == null) {
			setErrorMessage(Messages.NewElementPage_ErrorMessageNoSystemSelected);
			return false;
		}

		String result = validateElementName(text.getText());
		setErrorMessage(result);
		return result == null;

	}

	public abstract String validateElementName(String text);

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {
		return text.getText();
	}

	/**
	 * Allows to set a default name
	 * 
	 * @param template
	 */
	public void setFileName(String template) {
		if (this.text != null)
			this.text.setText(template);
	}

	/**
	 * Gets the selected system.
	 * 
	 * @return the selected system
	 */
	public AutomationSystem getSelectedSystem() {
		return (AutomationSystem) ((IStructuredSelection) treeViewer
				.getSelection()).getFirstElement();
	}

	public void setSystem(AutomationSystem system) {
		this.system = system;
	}
}
