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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

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
		setPreferenceStore(
				new ScopedPreferenceStore(InstanceScope.INSTANCE, PreferenceConstants.TYPE_MANAGEMENT_PREFERENCES_ID));
		setDescription(Messages.typeManagementPreferencePageTitle);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		final Group identificationGroup = new Group(getFieldEditorParent(), SWT.NONE);
		identificationGroup.setText(Messages.typeManagementPreferencePageIdentificationTitle);

		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		final StringFieldEditor standard = new StringFieldEditor(PreferenceConstants.P_STANDARD,
				PreferenceConstants.P_STANDARD, identificationGroup);
		addField(standard);
		final StringFieldEditor classification = new StringFieldEditor(PreferenceConstants.P_CLASSIFICATION,
				PreferenceConstants.P_CLASSIFICATION, identificationGroup);
		addField(classification);
		final StringFieldEditor applicationDomain = new StringFieldEditor(PreferenceConstants.P_APPLICATION_DOMAIN,
				PreferenceConstants.P_APPLICATION_DOMAIN, identificationGroup);
		addField(applicationDomain);
		final StringFieldEditor function = new StringFieldEditor(PreferenceConstants.P_FUNCTION,
				PreferenceConstants.P_FUNCTION, identificationGroup);
		addField(function);
		final StringFieldEditor type = new StringFieldEditor(PreferenceConstants.P_TYPE, PreferenceConstants.P_TYPE,
				identificationGroup);
		addField(type);
		final StringFieldEditor description = new StringFieldEditor(PreferenceConstants.P_DESCRIPTION,
				PreferenceConstants.P_DESCRIPTION, identificationGroup);
		addField(description);

		identificationGroup.setLayoutData(gridData);
		identificationGroup.setLayout(gridLayout);

		final Group versionGroup = new Group(getFieldEditorParent(), SWT.NONE);
		versionGroup.setText(Messages.typeManagementPreferencePageVersionTitle);

		final StringFieldEditor version = new StringFieldEditor(PreferenceConstants.P_VERSION, PreferenceConstants.P_VERSION,
				versionGroup);
		addField(version);
		final StringFieldEditor organization = new StringFieldEditor(PreferenceConstants.P_ORGANIZATION,
				PreferenceConstants.P_ORGANIZATION, versionGroup);
		addField(organization);
		final StringFieldEditor author = new StringFieldEditor(PreferenceConstants.P_AUTHOR, PreferenceConstants.P_AUTHOR,
				versionGroup);
		addField(author);
		final StringFieldEditor remarks = new StringFieldEditor(PreferenceConstants.P_REMARKS, PreferenceConstants.P_REMARKS,
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
	@Override
	public void init(final IWorkbench workbench) {
		setDescription(Messages.typeManagementPreferencePageDescription);
	}

}
