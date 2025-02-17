/*******************************************************************************
 * Copyright (c) 2009, 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.preferences;

import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class HoloblocDeploymentPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new holobloc deployment preferences.
	 */
	public HoloblocDeploymentPreferencePage() {
		super(GRID);
		setPreferenceStore(IEC61499PreferenceConstants.STORE);
		setDescription(Messages.HoloblocDeploymentPreferences_PreferencePageDescription);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		final IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(
				IEC61499PreferenceConstants.P_CONNECTION_TIMEOUT,
				Messages.HoloblocDeploymentPreferences_ConnectionTimout, getFieldEditorParent(), 3000);
		integerFieldEditor.setValidRange(1, 60000);

		addField(integerFieldEditor);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(final IWorkbench workbench) {
		// nothing todo here
	}

}