/*******************************************************************************
 * Copyright (c) 2014  fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - changed new system wizard to a new 4diac project wizard for
 *                 the new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class New4diacProjectPage extends WizardNewProjectCreationPage {

	public static final String APPLICATION_NAME_POSTFIX = "App"; //$NON-NLS-1$

	private boolean openApplication = true;

	private Button advancedButton;

	private InitialNameGroup systemName;
	private InitialNameGroup applicationName;

	/**
	 * Height of the "advanced" linked resource group. Set when the advanced group
	 * is first made visible.
	 */
	private int linkedResourceGroupHeight = -1;

	/** Container for the advanced section in the creation wizard */
	private Composite advancedGroupContainer;

	private Composite advancedGroupParent;

	private boolean blockListeners = false;

	private final Listener nameModifyListener = e -> {
		if (!blockListeners) {
			setPageComplete(validatePage());
		}
	};

	/**
	 * Creates a new project creation wizard page.
	 *
	 * @param pageName the name of this page
	 */
	public New4diacProjectPage(final String pageName) {
		super(pageName);
		setPageComplete(false);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		systemName = new InitialNameGroup((Composite) getControl(), Messages.New4diacProjectWizard_InitialSystemName);
		systemName.addNameModifyListener(nameModifyListener);
		applicationName = new InitialNameGroup((Composite) getControl(),
				Messages.New4diacProjectWizard_InitialApplicationName);
		applicationName.addNameModifyListener(nameModifyListener);

		createAdvancedControls((Composite) getControl());

		final Composite composite = (Composite) getControl();
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		setControl(composite);
		Dialog.applyDialogFont(composite);
	}

	@Override
	protected boolean validatePage() {
		blockListeners = true;
		try {
			if (IdentifierVerifier.verifyIdentifier(getProjectName()).isPresent()) {
				setErrorMessage(Messages.New4diacProjectWizard_SystemNameNotValid);
				return false;
			}

			if (!systemName.validateName(getProjectName())) {
				return false;
			}

			if (!applicationName.validateName(getProjectName() + APPLICATION_NAME_POSTFIX)) {
				return false;
			}

			if (!super.validatePage()) {
				return false;
			}
			// if we are in the default project location area check if there is no directory
			// with given name
			if (useDefaults() && projectNameExistsinWSPath(getProjectName())) {
				setErrorMessage(Messages.New4diacProjectWizard_DirectoryWithProjectNameAlreadyExistsInWorkspace);
				return false;
			}
		} finally {
			blockListeners = false;
		}
		return true;
	}

	public String getInitialSystemName() {
		return systemName.getInitialName();
	}

	public String getInitialApplicationName() {
		return applicationName.getInitialName();
	}

	public boolean getOpenApplication() {
		return openApplication;
	}

	private void createAdvancedControls(final Composite parent) {
		advancedGroupParent = new Composite(parent, SWT.NONE);
		advancedGroupParent.setFont(parent.getFont());
		advancedGroupParent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		advancedGroupParent.setLayout(layout);

		advancedButton = new Button(advancedGroupParent, SWT.PUSH);
		advancedButton.setFont(advancedGroupParent.getFont());
		advancedButton.setText(Messages.NewSystemWizard_ShowAdvanced);
		final GridData data = setButtonLayoutData(advancedButton);
		data.horizontalAlignment = GridData.BEGINNING;
		advancedButton.setLayoutData(data);
		advancedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleAdvancedButtonSelect();
			}
		});
	}

	/** Shows/hides the advanced option widgets. */
	protected void handleAdvancedButtonSelect() {
		if (advancedGroupContainer != null) {
			advancedGroupContainer.dispose();
			advancedGroupContainer = null;
			advancedButton.setText(Messages.NewSystemWizard_ShowAdvanced);
		} else {
			createAdvancedGroup();
			if (linkedResourceGroupHeight == -1) {
				final Point groupSize = advancedGroupContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
				linkedResourceGroupHeight = groupSize.y;
			}
			advancedButton.setText(Messages.NewSystemWizard_HideAdvanced);
		}
		final Composite compo = (Composite) getControl();
		compo.layout();
	}

	private void createAdvancedGroup() {
		advancedGroupContainer = new Composite(advancedGroupParent, SWT.NONE);
		advancedGroupContainer.setLayout(new GridLayout());
		advancedGroupContainer.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		final Button openApplicationCheckbox = new Button(advancedGroupContainer, SWT.CHECK);
		openApplicationCheckbox.setText(Messages.OpenApplicationForEditing);
		openApplicationCheckbox.setSelection(openApplication);
		openApplicationCheckbox.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				openApplication = true;
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				openApplication = false;
			}
		});
	}

	private static boolean projectNameExistsinWSPath(final String projectName) {
		final IPath wsPath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		final IPath localProjectPath = wsPath.append(projectName);
		return localProjectPath.toFile().exists();
	}

}
