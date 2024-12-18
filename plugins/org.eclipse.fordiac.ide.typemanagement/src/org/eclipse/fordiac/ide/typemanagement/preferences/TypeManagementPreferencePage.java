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
				new ScopedPreferenceStore(InstanceScope.INSTANCE, TypeManagementPreferenceConstants.TYPE_MANAGEMENT_PREFERENCES_ID));
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

		final StringFieldEditor standard = new StringFieldEditor(TypeManagementPreferenceConstants.P_STANDARD,
				TypeManagementPreferenceConstants.P_STANDARD, identificationGroup);
		addField(standard);
		final StringFieldEditor classification = new StringFieldEditor(TypeManagementPreferenceConstants.P_CLASSIFICATION,
				TypeManagementPreferenceConstants.P_CLASSIFICATION, identificationGroup);
		addField(classification);
		final StringFieldEditor applicationDomain = new StringFieldEditor(TypeManagementPreferenceConstants.P_APPLICATION_DOMAIN,
				TypeManagementPreferenceConstants.P_APPLICATION_DOMAIN, identificationGroup);
		addField(applicationDomain);
		final StringFieldEditor function = new StringFieldEditor(TypeManagementPreferenceConstants.P_FUNCTION,
				TypeManagementPreferenceConstants.P_FUNCTION, identificationGroup);
		addField(function);
		final StringFieldEditor type = new StringFieldEditor(TypeManagementPreferenceConstants.P_TYPE, TypeManagementPreferenceConstants.P_TYPE,
				identificationGroup);
		addField(type);
		final StringFieldEditor description = new StringFieldEditor(TypeManagementPreferenceConstants.P_DESCRIPTION,
				TypeManagementPreferenceConstants.P_DESCRIPTION, identificationGroup);
		addField(description);

		identificationGroup.setLayoutData(gridData);
		identificationGroup.setLayout(gridLayout);

		final Group versionGroup = new Group(getFieldEditorParent(), SWT.NONE);
		versionGroup.setText(Messages.typeManagementPreferencePageVersionTitle);

		final StringFieldEditor version = new StringFieldEditor(TypeManagementPreferenceConstants.P_VERSION, TypeManagementPreferenceConstants.P_VERSION,
				versionGroup);
		addField(version);
		final StringFieldEditor organization = new StringFieldEditor(TypeManagementPreferenceConstants.P_ORGANIZATION,
				TypeManagementPreferenceConstants.P_ORGANIZATION, versionGroup);
		addField(organization);
		final StringFieldEditor author = new StringFieldEditor(TypeManagementPreferenceConstants.P_AUTHOR, TypeManagementPreferenceConstants.P_AUTHOR,
				versionGroup);
		addField(author);
		final StringFieldEditor remarks = new StringFieldEditor(TypeManagementPreferenceConstants.P_REMARKS, TypeManagementPreferenceConstants.P_REMARKS,
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
