/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.codemining;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

public class STCoreCodeMiningPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private BooleanFieldEditor enableCodeMinings;
	private BooleanFieldEditor enableLiteralTypeCodeMinings;
	private BooleanFieldEditor enableReferencedVariablesCodeMinings;

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public STCoreCodeMiningPreferencePage() {
		super(GRID);
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return preferenceStoreAccess.getWritablePreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		enableCodeMinings = new BooleanFieldEditor(STCoreCodeMiningPreferences.ENABLE_CODE_MININGS,
				Messages.STCoreCodeMiningPreferencePage_EnableCodeMinings, getFieldEditorParent());
		addField(enableCodeMinings);
		enableLiteralTypeCodeMinings = new BooleanFieldEditor(
				STCoreCodeMiningPreferences.ENABLE_LITERAL_TYPE_CODE_MININGS,
				Messages.STCoreCodeMiningPreferencePage_EnableLiteralTypeCodeMinings, getFieldEditorParent());
		enableLiteralTypeCodeMinings.setEnabled(
				getPreferenceStore().getBoolean(STCoreCodeMiningPreferences.ENABLE_CODE_MININGS),
				getFieldEditorParent());
		addField(enableLiteralTypeCodeMinings);
		enableReferencedVariablesCodeMinings = new BooleanFieldEditor(
				STCoreCodeMiningPreferences.ENABLE_REFERENCED_VARIABLES_CODE_MININGS,
				Messages.STCoreCodeMiningPreferencePage_EnableReferencedVariablesCodeMinings, getFieldEditorParent());
		enableReferencedVariablesCodeMinings.setEnabled(
				getPreferenceStore().getBoolean(STCoreCodeMiningPreferences.ENABLE_CODE_MININGS),
				getFieldEditorParent());
		addField(enableReferencedVariablesCodeMinings);
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		updateFieldEnablement();
	}

	@Override
	public void propertyChange(final PropertyChangeEvent event) {
		super.propertyChange(event);
		updateFieldEnablement();
	}

	protected void updateFieldEnablement() {
		enableLiteralTypeCodeMinings.setEnabled(enableCodeMinings.getBooleanValue(), getFieldEditorParent());
		enableReferencedVariablesCodeMinings.setEnabled(enableCodeMinings.getBooleanValue(), getFieldEditorParent());
	}

	@Override
	public void init(final IWorkbench workbench) {
		// do nothing
	}
}
