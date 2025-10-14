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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.elk.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class LayoutPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public LayoutPreferencePage() {
		super(GRID);
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, ElkPreferences.ELK_PREFERENCES_ID));
	}

	@Override
	protected void createFieldEditors() {
		final Composite parent = getFieldEditorParent();

		final IntegerFieldEditor integerFieldEditorValue = new IntegerFieldEditor(
				ElkPreferences.CONNECTION_LAYOUT_TIMEOUT, Messages.LayoutPreferences_ConnectionLayoutTimeout, parent);
		integerFieldEditorValue.setValidRange(100, 1000000);
		addField(integerFieldEditorValue);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do
	}

}
