/*******************************************************************************
 * Copyright (c) 2008, 2010, 2015 Profactor GmbH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
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
 *   Alois Zoitl - updated new application wizard to new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewApplicationPage extends WizardPage {

	private final AutomationSystem system;
	private Text applicationName;
	private Button openApplicationCheckbox;

	protected NewApplicationPage(AutomationSystem system) {
		super(Messages.NewApplicationWizardTitle);
		this.setTitle(Messages.NewApplicationWizardTitle);
		this.system = system;
		this.setDescription(MessageFormat.format(Messages.NewApplicationPage_NewApplicationDescription,
				(null != system) ? system.getName() : Messages.NewApplicationPage_ErrorMessageNoSystemSelected));
	}

	public String getApplicationName() {
		return applicationName.getText();
	}

	public boolean getOpenApplication() {
		return openApplicationCheckbox.getSelection();
	}

	@Override
	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createApplicationNameGroup(composite);
		createOpenApplication(composite);

		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	protected void createApplicationNameGroup(final Composite composite) {
		Label l = new Label(composite, SWT.NONE);
		l.setText(FordiacMessages.ApplicationName + ":"); //$NON-NLS-1$
		l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		applicationName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		applicationName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		applicationName.addModifyListener(e -> setPageComplete(validatePage()));
		applicationName.addVerifyListener(new IdentifierVerifyListener());

	}

	private void createOpenApplication(Composite parent) {
		openApplicationCheckbox = new Button(parent, SWT.CHECK);
		openApplicationCheckbox.setText(Messages.NewApplicationPage_OpenApplicationForEditing);
		openApplicationCheckbox.setSelection(true);
	}

	protected boolean validatePage() {
		if (null == system) {
			// safety net if the wizard is invoked on something which is not a system
			setErrorMessage(Messages.NewApplicationPage_ErrorMessageNoSystemSelected);
			return false;
		}
		if (applicationName.getText().isEmpty()) {
			setErrorMessage(Messages.NewApplicationPage_ErrorMessage_EmptyElementName);
			return false;
		}

		if (!isValidAppName(applicationName.getText(), system)) {
			setErrorMessage(Messages.NewApplicationPage_ErrorMessageInvalidAppName);
			return false;
		}
		setErrorMessage(null);
		return true;

	}

	/**
	 * Checks if is valid app name.
	 *
	 * @param appNameProposal the proposed new name for the application
	 * @param selectedSystem  the selected system
	 *
	 * @return true, if is valid app name
	 */
	private static boolean isValidAppName(final String appNameProposal, final AutomationSystem selectedSystem) {
		if (!IdentifierVerifyer.isValidIdentifier(appNameProposal)) {
			return false;
		}
		for (Application app : selectedSystem.getApplication()) {
			if (appNameProposal.equalsIgnoreCase(app.getName())) {
				return false;
			}
		}
		return true;
	}
}
