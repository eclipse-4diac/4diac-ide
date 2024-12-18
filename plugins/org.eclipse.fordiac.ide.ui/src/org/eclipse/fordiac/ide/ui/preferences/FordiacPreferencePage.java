/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - added separate colors for different data types
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.preference.BooleanFieldEditor;
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

		addField(new ColorFieldEditor(UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_DefaultEventConnectorColor, getFieldEditorParent()));

		createDataColorFields();

		addField(new ColorFieldEditor(UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor, getFieldEditorParent()));

		addField(new ComboFieldEditor(UIPreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE,
				FordiacMessages.FordiacPreferencePage_LABEL_DefaultComplianceProfile, getSupportedProfiles(),
				getFieldEditorParent()));

		createCheckBoxFields();

	}

	private void createDataColorFields() {
		addField(new ColorFieldEditor(UIPreferenceConstants.P_BOOL_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_BoolConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(UIPreferenceConstants.P_ANY_BIT_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_AnyBitConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(UIPreferenceConstants.P_ANY_INT_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_AnyIntConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(UIPreferenceConstants.P_ANY_REAL_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_AnyRealConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(UIPreferenceConstants.P_ANY_STRING_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_AnyStringConnectorColor, getFieldEditorParent()));
		addField(new ColorFieldEditor(UIPreferenceConstants.P_REMAINING_DATA_CONNECTOR_COLOR,
				FordiacMessages.FordiacPreferencePage_LABEL_DataConnectorColor, getFieldEditorParent()));
	}

	private static String[][] getSupportedProfiles() {
		// FIXME return installed/supported profiles
		return new String[][] { { "HOLOBLOC", "HOLOBLOC" }, { "FBDK2", "FBDK2" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				{ "DynamicTypeLoad", "DynamicTypeLoad" } }; //$NON-NLS-1$ //$NON-NLS-2$

	}

	private void createCheckBoxFields() {
		addField(new BooleanFieldEditor(UIPreferenceConstants.P_SHOW_ERRORS_AT_MOUSE_CURSOR,
				FordiacMessages.FordiacPreferencePage_LABEL_ShowErrorsAtMouseCursor, getFieldEditorParent()));

		addField(new BooleanFieldEditor(UIPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX,
				FordiacMessages.FordiacPreferencePage_LABEL_DeactivateTransferingComments_DEMUX_to_MUX,
				getFieldEditorParent()));
	}

	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(UIPreferenceConstants.STORE);
		setDescription(FordiacMessages.FordiacPreferencePage_LABEL_PreferencePageDescription);
	}

}