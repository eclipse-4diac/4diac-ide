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
package org.eclipse.fordiac.ide.structuredtextcore.ui.cleanup;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

public class STCoreSaveActionsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private BooleanFieldEditor enableSaveActions;
	private BooleanFieldEditor enableFormat;

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public STCoreSaveActionsPreferencePage() {
		super(GRID);
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return preferenceStoreAccess.getWritablePreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		enableSaveActions = new BooleanFieldEditor(STCoreSaveActionsPreferences.ENABLE_SAVE_ACTIONS,
				Messages.STCoreSaveActionsPreferencePage_EnableSaveActions, getFieldEditorParent());
		addField(enableSaveActions);
		enableFormat = new BooleanFieldEditor(STCoreSaveActionsPreferences.ENABLE_FORMAT,
				Messages.STCoreSaveActionsPreferencePage_EnableFormat, getFieldEditorParent());
		enableFormat.setEnabled(getPreferenceStore().getBoolean(STCoreSaveActionsPreferences.ENABLE_SAVE_ACTIONS),
				getFieldEditorParent());
		addField(enableFormat);
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
		enableFormat.setEnabled(enableSaveActions.getBooleanValue(), getFieldEditorParent());
	}

	@Override
	public void init(final IWorkbench workbench) {
		// do nothing
	}
}
