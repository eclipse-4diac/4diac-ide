/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class ModelPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public ModelPreferences() {
		super(GRID);
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.fordiac.ide.model"));//$NON-NLS-1$
		setDescription("Settings for the Model Plug-In.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		IntegerFieldEditor allocationSize = new IntegerFieldEditor(UIPreferenceConstants.P_ALLOCATION_SIZE,
				"Allocation block size in MiB", getFieldEditorParent(),
				UIPreferenceConstants.P_ALLOCATION_SIZE_DEFAULT_VALUE);
		allocationSize.setValidRange(UIPreferenceConstants.P_ALLOCATION_SIZE_MIN_VALUE,
				UIPreferenceConstants.P_ALLOCATION_SIZE_MAX_VALUE);
		addField(allocationSize);
	}

	@Override
	public void init(IWorkbench workbench) {
		// nothing todo here
	}

}