/*******************************************************************************
 * Copyright (c) 2009 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.preferences;

import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.export.ICompareEditorOpener;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.ui.Messages;
import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The Class FORTEExportPreferences.
 */
public class FORTEExportPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Instantiates a new fORTE export preferences.
	 */
	public FORTEExportPreferencePage() {
		super(GRID);
		setPreferenceStore(PreferenceConstants.STORE);
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		// Create a Group to hold the compare editor fields
		final Group compare = new Group(getFieldEditorParent(), SWT.NONE);
		compare.setText(Messages.FORTEExportPreferences_CompareEditorForMerging);
		final GridLayout compareLayout = new GridLayout(2, false);

		final Map<String, ICompareEditorOpener> compareEditorOpeners = CompareEditorOpenerUtil
				.getCompareEditorOpeners();

		final Set<String> keySet = compareEditorOpeners.keySet();
		final String[][] nameArray = new String[keySet.size()][2];
		int i = 0;
		for (final String key : keySet) {
			nameArray[i][0] = key;
			nameArray[i][1] = key;
			i++;
		}

		final ComboFieldEditor compareEditor = new ComboFieldEditor(PreferenceConstants.P_COMPARE_EDITOR,
				Messages.FORTEExportPreferences_DefaultCompareEditorOpener, nameArray, compare);
		addField(compareEditor);

		final GridData comparedata = new GridData(GridData.FILL_HORIZONTAL);
		comparedata.grabExcessHorizontalSpace = true;
		comparedata.horizontalSpan = 2;

		compare.setLayoutData(comparedata);
		compare.setLayout(compareLayout);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(final IWorkbench workbench) {
		// Currently nothing to do here
	}

}