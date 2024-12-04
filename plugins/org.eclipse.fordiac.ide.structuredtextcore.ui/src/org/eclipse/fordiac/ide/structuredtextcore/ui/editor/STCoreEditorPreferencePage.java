/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor;

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

public class STCoreEditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private IntegerFieldEditor performanceModeThreshold;

	@Inject
	private IPreferenceStoreAccess preferenceStoreAccess;

	public STCoreEditorPreferencePage() {
		super(GRID);
	}

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return preferenceStoreAccess.getWritablePreferenceStore();
	}

	@Override
	protected void createFieldEditors() {
		performanceModeThreshold = new IntegerFieldEditor(STCoreEditorPreferences.PERFORMANCE_MODE_THRESHOLD,
				Messages.STCoreEditorPreferencePage_PerformanceModeThreshold, getFieldEditorParent());
		addField(performanceModeThreshold);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// do nothing
	}
}
