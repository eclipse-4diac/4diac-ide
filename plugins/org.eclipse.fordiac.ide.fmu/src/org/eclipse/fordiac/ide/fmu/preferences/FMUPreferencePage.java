/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class FMUPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private Group librariesGroup;
	private BooleanFieldEditor win32Field;
	private BooleanFieldEditor win64Field;
	private BooleanFieldEditor linux32Field;
	private BooleanFieldEditor linux64Field;

	/**
	 * Instantiates a new forte preference page.
	 */
	public FMUPreferencePage() {
		super(GRID);
		setPreferenceStore(FMUPreferenceConstants.STORE);
		setDescription(Messages.FMUPreferencePage_FMUPreferencesPage);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		addField(new DirectoryFieldEditor(FMUPreferenceConstants.P_PATH, Messages.FMUPreferencePage_BinariesLocation,
				getFieldEditorParent()));

		librariesGroup = new Group(getFieldEditorParent(), SWT.NONE);
		librariesGroup.setText(Messages.FMUPreferencePage_IncludeTheFollowingLibrariesInExportedFMU);

		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		// Add the fields to the group
		win32Field = new BooleanFieldEditor(FMUPreferenceConstants.P_FMU_WIN32, FMUPreferenceConstants.P_FMU_WIN32,
				librariesGroup);
		win32Field.setEnabled(false, librariesGroup);
		addField(win32Field);

		win64Field = new BooleanFieldEditor(FMUPreferenceConstants.P_FMU_WIN64, FMUPreferenceConstants.P_FMU_WIN64,
				librariesGroup);
		win64Field.setEnabled(false, librariesGroup);
		addField(win64Field);

		linux32Field = new BooleanFieldEditor(FMUPreferenceConstants.P_FMU_LIN32, FMUPreferenceConstants.P_FMU_LIN32,
				librariesGroup);
		linux32Field.setEnabled(false, librariesGroup);
		addField(linux32Field);

		linux64Field = new BooleanFieldEditor(FMUPreferenceConstants.P_FMU_LIN64, FMUPreferenceConstants.P_FMU_LIN64,
				librariesGroup);
		linux64Field.setEnabled(false, librariesGroup);
		addField(linux64Field);

		librariesGroup.setLayoutData(gridData);
		librariesGroup.setLayout(gridLayout);

		updateEnabledLibraries(true, FMUPreferenceConstants.STORE.getString(FMUPreferenceConstants.P_PATH));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		if (event.getSource() instanceof final DirectoryFieldEditor fieldEditor
				&& fieldEditor.getPreferenceName().equals(FMUPreferenceConstants.P_PATH)) {
			if (event.getNewValue() instanceof final String newValue) {
				updateEnabledLibraries(true, newValue);
			} else if (event.getNewValue() instanceof Boolean) {// When directory doesn't exists, the event is a false
				// boolean
				updateEnabledLibraries(false, null);
			}

		}
		super.propertyChange(event);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(final IWorkbench workbench) {
		setDescription(Messages.FMUPreferencePage_InsideTheSelectedPathTheFilesSearchedFor);
	}

	private void updateEnabledLibraries(final boolean validPath, final String pathString) {

		if (validPath) {
			win32Field.setEnabled(new File(pathString + File.separatorChar + "win32Forte.dll"). //$NON-NLS-1$
					exists(), librariesGroup);
			win64Field.setEnabled(new File(pathString + File.separatorChar + "win64Forte.dll"). //$NON-NLS-1$
					exists(), librariesGroup);
			linux32Field.setEnabled(new File(pathString + File.separatorChar + "linux32Forte.so"). //$NON-NLS-1$
					exists(), librariesGroup);
			linux64Field.setEnabled(new File(pathString + File.separatorChar + "linux64Forte.so"). //$NON-NLS-1$
					exists(), librariesGroup);
		} else {
			win32Field.setEnabled(false, librariesGroup);
			win64Field.setEnabled(false, librariesGroup);
			linux32Field.setEnabled(false, librariesGroup);
			linux64Field.setEnabled(false, librariesGroup);
		}

	}

	public static List<String> getFoundLibraries() {
		final List<String> found = new ArrayList<>();
		final String pathString = FMUPreferenceConstants.STORE.getString(FMUPreferenceConstants.P_PATH);
		if ((new File(pathString + File.separatorChar + "win32Forte.dll").exists())) { //$NON-NLS-1$
			found.add(FMUPreferenceConstants.P_FMU_WIN32);
		}
		if ((new File(pathString + File.separatorChar + "win64Forte.dll").exists())) { //$NON-NLS-1$
			found.add(FMUPreferenceConstants.P_FMU_WIN64);
		}
		if ((new File(pathString + File.separatorChar + "linux32Forte.so").exists())) { //$NON-NLS-1$
			found.add(FMUPreferenceConstants.P_FMU_LIN32);
		}
		if ((new File(pathString + File.separatorChar + "linux64Forte.so").exists())) { //$NON-NLS-1$
			found.add(FMUPreferenceConstants.P_FMU_LIN64);
		}

		return found;
	}

}
