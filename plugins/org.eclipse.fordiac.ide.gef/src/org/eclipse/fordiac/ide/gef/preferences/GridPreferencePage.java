/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.ui.preferences.FixedScopedPreferenceStore;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class GridPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public GridPreferencePage() {
		super(GRID);
		setPreferenceStore(
				new FixedScopedPreferenceStore(InstanceScope.INSTANCE, GefPreferenceConstants.GEF_PREFERENCES_ID));
	}

	@Override
	protected void createFieldEditors() {
		final BooleanFieldEditor showRulers = new BooleanFieldEditor(GefPreferenceConstants.SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, getFieldEditorParent());
		addField(showRulers);

		final BooleanFieldEditor showGrid = new BooleanFieldEditor(GefPreferenceConstants.SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, getFieldEditorParent());
		addField(showGrid);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do
	}

}
