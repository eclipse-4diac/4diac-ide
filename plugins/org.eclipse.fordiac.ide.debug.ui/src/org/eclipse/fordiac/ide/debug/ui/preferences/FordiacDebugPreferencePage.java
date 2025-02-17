/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.debug.preferences.FordiacDebugPreferences;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class FordiacDebugPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public FordiacDebugPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, FordiacDebugPreferences.DEBUG_PREFERENCES_ID));
		setDescription(Messages.FordiacDebugPreferencePage_Description);
	}

	@Override
	public void createFieldEditors() {
		addField(new IntegerFieldEditor(FordiacDebugPreferences.VALUE_MAX_DISPLAY_LENGTH,
				Messages.FordiacDebugPreferencePage_ValueMaxDisplayLength, getFieldEditorParent()));
	}
}