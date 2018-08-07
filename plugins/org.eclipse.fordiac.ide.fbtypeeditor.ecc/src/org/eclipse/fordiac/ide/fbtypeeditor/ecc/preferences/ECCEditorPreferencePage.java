/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page which allows to select different default colors for the
 * elements of the ECC Editor.
 */

public class ECCEditorPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public ECCEditorPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.FordiacECCPreferencePage_LABEL_PreferencePageDescription);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {
		addField(new ColorFieldEditor(PreferenceConstants.P_ECC_STATE_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCStateColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ECC_STATE_BORDER_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCStateBorderColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ECC_ALGORITHM_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCAlgorithmColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ECC_ALGORITHM_BORDER_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCAlgorithmBorderColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceConstants.P_ECC_EVENT_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCEventColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ECC_EVENT_BORDER_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCEventBorderColor,
				getFieldEditorParent()));
		addField(new ColorFieldEditor(
				PreferenceConstants.P_ECC_TRANSITION_COLOR,
				Messages.FordiacECCPreferencePage_LABEL_ECCTransitionColor,
				getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

}