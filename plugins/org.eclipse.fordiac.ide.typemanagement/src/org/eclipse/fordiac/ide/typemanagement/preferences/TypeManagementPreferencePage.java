/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement.preferences;

import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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

public class TypeManagementPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new forte preference page.
	 */
	public TypeManagementPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.typeManagementPreferencePageTitle);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		Group identificationGroup = new Group(getFieldEditorParent(), SWT.NONE);
		identificationGroup.setText(Messages.typeManagementPreferencePageIdentificationTitle);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		StringFieldEditor standard = new StringFieldEditor(PreferenceConstants.P_STANDARD,
				PreferenceConstants.P_STANDARD, identificationGroup);
		addField(standard);
		StringFieldEditor classification = new StringFieldEditor(PreferenceConstants.P_CLASSIFICATION,
				PreferenceConstants.P_CLASSIFICATION, identificationGroup);
		addField(classification);
		StringFieldEditor applicationDomain = new StringFieldEditor(PreferenceConstants.P_APPLICATION_DOMAIN,
				PreferenceConstants.P_APPLICATION_DOMAIN, identificationGroup);
		addField(applicationDomain);
		StringFieldEditor function = new StringFieldEditor(PreferenceConstants.P_FUNCTION,
				PreferenceConstants.P_FUNCTION, identificationGroup);
		addField(function);
		StringFieldEditor type = new StringFieldEditor(PreferenceConstants.P_TYPE, PreferenceConstants.P_TYPE,
				identificationGroup);
		addField(type);
		StringFieldEditor description = new StringFieldEditor(PreferenceConstants.P_DESCRIPTION,
				PreferenceConstants.P_DESCRIPTION, identificationGroup);
		addField(description);

		identificationGroup.setLayoutData(gridData);
		identificationGroup.setLayout(gridLayout);

		Group versionGroup = new Group(getFieldEditorParent(), SWT.NONE);
		versionGroup.setText(Messages.typeManagementPreferencePageVersionTitle);

		StringFieldEditor version = new StringFieldEditor(PreferenceConstants.P_VERSION, PreferenceConstants.P_VERSION,
				versionGroup);
		addField(version);
		StringFieldEditor organization = new StringFieldEditor(PreferenceConstants.P_ORGANIZATION,
				PreferenceConstants.P_ORGANIZATION, versionGroup);
		addField(organization);
		StringFieldEditor author = new StringFieldEditor(PreferenceConstants.P_AUTHOR, PreferenceConstants.P_AUTHOR,
				versionGroup);
		addField(author);
		StringFieldEditor remarks = new StringFieldEditor(PreferenceConstants.P_REMARKS, PreferenceConstants.P_REMARKS,
				versionGroup);
		addField(remarks);

		versionGroup.setLayoutData(gridData);
		versionGroup.setLayout(gridLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setDescription(Messages.typeManagementPreferencePageDescription);
	}

}
