/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.fordiac.ide.ui.Messages;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class FordiacPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public FordiacPreferencePage() {
		super(GRID);
	}

	@Override
	public void createFieldEditors() {

		addField(new ColorFieldEditor(PreferenceConstants.P_EVENT_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultEventConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.P_DATA_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultDataConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR,
				Messages.FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor, getFieldEditorParent()));

		addField(new ComboFieldEditor(PreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE,
				Messages.FordiacPreferencePage_LABEL_DefaultComplianceProfile, getSupportedProfiles(),
				getFieldEditorParent()));

	}

	private String[][] getSupportedProfiles() {
		// FIXME return installed/supported profiles
		return new String[][] { { "HOLOBLOC", "HOLOBLOC" }, { "FBDK2", "FBDK2" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				{ "DynamicTypeLoad", "DynamicTypeLoad" } }; //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(UIPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.FordiacPreferencePage_LABEL_PreferencePageDescription);
	}

}