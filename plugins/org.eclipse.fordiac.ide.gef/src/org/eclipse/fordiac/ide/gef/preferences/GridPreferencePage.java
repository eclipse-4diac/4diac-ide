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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
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
		final Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText(Messages.DiagramPreferences_FieldEditors_RulerAndGrid);

		final BooleanFieldEditor showRulers = new BooleanFieldEditor(GefPreferenceConstants.SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, group);
		addField(showRulers);

		final BooleanFieldEditor showGrid = new BooleanFieldEditor(GefPreferenceConstants.SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, group);
		addField(showGrid);

		final GridLayout gridLayout = new GridLayout(1, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do
	}

}
