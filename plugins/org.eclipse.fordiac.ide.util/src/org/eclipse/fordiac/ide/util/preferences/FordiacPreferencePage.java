/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.preferences;

import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class FordiacPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public FordiacPreferencePage() {
		super(GRID);
	}

	@Override
	public void createFieldEditors() {

		addField(new ColorFieldEditor(
				PreferenceConstants.P_EVENT_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultEventConnectorColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_DATA_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultDataConnectorColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor,
				getFieldEditorParent()));

		addField(new ComboFieldEditor(
				PreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE,
				Messages.FordiacPreferencePage_LABEL_DefaultComplianceProfile,
				getSupportedProfiles(), getFieldEditorParent()));

	}

	private String[][] getSupportedProfiles() {
		// FIXME return installed/supported profiles
		return new String[][] { { "HOLOBLOC", "HOLOBLOC" }, {"FBDK2", "FBDK2"}, {"DynamicTypeLoad", "DynamicTypeLoad"}}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	}

	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.FordiacPreferencePage_LABEL_PreferencePageDescription);
	}
	
}