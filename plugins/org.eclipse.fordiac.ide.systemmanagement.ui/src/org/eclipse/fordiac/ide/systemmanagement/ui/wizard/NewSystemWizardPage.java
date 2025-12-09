/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.util.Optional;

import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewSystemWizardPage extends WizardNewFileCreationPage {

	private Button openApplicationCheckbox;
	private int openApplicationParentHeight = -1;
	private boolean openApplication = true;

	private InitialNameGroup applicationName;
	private boolean blockListeners = false;

	private final Listener nameModifyListener = e -> {
		if (!blockListeners) {
			setPageComplete(validatePage());
		}
	};

	public String getInitialApplicationName() {
		return applicationName.getInitialName();
	}

	public boolean getOpenApplication() {
		return openApplication;
	}

	public NewSystemWizardPage(final IStructuredSelection selection) {
		super(Messages.NewSystemWizardPage_NewSystemTitle, selection);
		this.setTitle(Messages.NewSystemWizardPage_NewSystemTitle);
		this.setDescription(Messages.NewSystemWizardPage_CreateNewSystem);
		this.setAllowExistingResources(true); // needed for correct duplicate type check
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		final Composite composite = (Composite) getControl();
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	@Override
	protected boolean validatePage() {
		blockListeners = true;
		try {
			// use super.getFileName here to get the type name without extension
			final Optional<String> errorMessage = IdentifierVerifier.verifyIdentifier(super.getFileName());
			if (errorMessage.isPresent()) {
				setErrorMessage(errorMessage.get());
				return false;
			}

			if (!applicationName.validateName(super.getFileName() + New4diacProjectPage.APPLICATION_NAME_POSTFIX)) {
				return false;
			}

			setErrorMessage(null);
			return super.validatePage();
		} finally {
			blockListeners = false;
		}
	}

	@Override
	protected String getNewFileLabel() {
		return FordiacMessages.SystemName + ":"; //$NON-NLS-1$
	}

	public String getSystemName() {
		return super.getFileName();
	}

	@Override
	public String getFileName() {
		return super.getFileName() + SystemManager.SYSTEM_FILE_ENDING_WITH_DOT;
	}

	@Override
	protected void createAdvancedControls(final Composite parent) {
		applicationName = new InitialNameGroup(parent, Messages.New4diacProjectWizard_InitialApplicationName);
		applicationName.addNameModifyListener(nameModifyListener);
		super.createAdvancedControls(parent);
	}

	@Override
	protected void handleAdvancedButtonSelect() {
		final Shell shell = getShell();
		final Point shellSize = shell.getSize();
		final Composite composite = (Composite) getControl();

		if (null != openApplicationCheckbox) {
			openApplicationCheckbox.dispose();
			openApplicationCheckbox = null;
			shell.setSize(shellSize.x, shellSize.y - openApplicationParentHeight);
		} else {
			openApplicationCheckbox = createOpenApplicationGroup(composite);
			if (-1 == openApplicationParentHeight) {
				final Point groupSize = openApplicationCheckbox.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				openApplicationParentHeight = groupSize.y;
			}
			shell.setSize(shellSize.x, shellSize.y + openApplicationParentHeight);
		}
		super.handleAdvancedButtonSelect();
	}

	private Button createOpenApplicationGroup(final Composite parent) {
		openApplicationCheckbox = new Button(parent, SWT.CHECK);
		openApplicationCheckbox.setText(Messages.OpenApplicationForEditing);
		openApplicationCheckbox.setSelection(true);
		setPageComplete(validatePage());
		openApplicationCheckbox.addListener(SWT.Selection,
				ev -> openApplication = openApplicationCheckbox.getSelection());
		return openApplicationCheckbox;
	}

}
