/*******************************************************************************
 * Copyright (c) 2017, 2021 fortiss GmbH, Johannes Kepler Unviersity Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *   Alois Zoitl - updated FMU exporting for new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import org.eclipse.fordiac.ide.deployment.bootfile.wizard.CreateBootFilesWizardPage;
import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.fordiac.ide.fmu.preferences.FMUPreferenceConstants;
import org.eclipse.fordiac.ide.fmu.preferences.FMUPreferencePage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class CreateFMUWizardPage extends CreateBootFilesWizardPage {

	public Button getWin32Field() {
		return win32Field;
	}

	public Button getWin64Field() {
		return win64Field;
	}

	public Button getLinux32Field() {
		return linux32Field;
	}

	public Button getLinux64Field() {
		return linux64Field;
	}

	public Button getStoreSelectedLibaries() {
		return storeSelectedLibaries;
	}

	private Button win32Field = null;
	private Button win64Field;
	private Button linux32Field;
	private Button linux64Field;
	private Button storeSelectedLibaries;

	public CreateFMUWizardPage(final IStructuredSelection selection) {
		super(Messages.FordiacCreateFMUWizard_PageName, selection);

	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		setDescription(Messages.FordiacCreateFMUWizard_PageDESCRIPTION);
		setTitle(Messages.FordiacCreateFMUWizard_PageTITLE);
	}

	@Override
	protected void createOptionsGroup(final Composite parent) {
		final Group librariesGroup = new Group(parent, SWT.NONE);
		librariesGroup.setText(Messages.CreateFMUWizardPage_IncludeTheFollowingLibrariesInExportedFMU);

		final GridLayout gridLayout = new GridLayout(1, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;

		win32Field = new Button(librariesGroup, SWT.CHECK);
		win64Field = new Button(librariesGroup, SWT.CHECK);
		linux32Field = new Button(librariesGroup, SWT.CHECK);
		linux64Field = new Button(librariesGroup, SWT.CHECK);
		storeSelectedLibaries = new Button(librariesGroup, SWT.CHECK);
		final Button[] buttons = { win32Field, win64Field, linux32Field, linux64Field, storeSelectedLibaries };
		final String[] preferences = { FMUPreferenceConstants.P_FMU_WIN32, FMUPreferenceConstants.P_FMU_WIN64,
				FMUPreferenceConstants.P_FMU_LIN32, FMUPreferenceConstants.P_FMU_LIN64,
				Messages.CreateFMUWizardPage_SaveSelectedLibrariesForFutureFMUExports };

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setText(preferences[i]);
			buttons[i].setEnabled(false);
			buttons[i].setSelection(false);
		}

		storeSelectedLibaries.setEnabled(true);

		// Enable the found libraries
		for (final String found : FMUPreferencePage.getFoundLibraries()) {
			if (found.equals(FMUPreferenceConstants.P_FMU_WIN32)) {
				win32Field.setEnabled(true);
			} else if (found.equals(FMUPreferenceConstants.P_FMU_WIN64)) {
				win64Field.setEnabled(true);
			} else if (found.equals(FMUPreferenceConstants.P_FMU_LIN32)) {
				linux32Field.setEnabled(true);
			} else if (found.equals(FMUPreferenceConstants.P_FMU_LIN64)) {
				linux64Field.setEnabled(true);
			}
		}

		// Check the selected libraries from preferences
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setSelection(FMUPreferenceConstants.STORE.getBoolean(preferences[i]));
			buttons[i].addListener(SWT.Selection, e -> updatePageCompletion());
		}

		librariesGroup.setLayoutData(gridData);
		librariesGroup.setLayout(gridLayout);
	}

	@Override
	protected boolean validateOptionsGroup() {
		if (!isSomeLibrarySelected()) {
			setErrorMessage(Messages.CreateFMUWizardPage_NoLibrariesSelectedToInclude);
			return false;
		}

		setErrorMessage(null);
		return super.validateOptionsGroup();
	}

	private boolean isSomeLibrarySelected() {
		return (isSelected(win32Field) || isSelected(win64Field) || isSelected(linux32Field)
				|| isSelected(linux64Field));
	}

	private static boolean isSelected(final Button toCheck) {
		return (toCheck.isEnabled() && toCheck.getSelection());
	}

}
