/*******************************************************************************
 * Copyright (c) 2009 - 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui.preferences;

import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.export.utils.CompareEditorOpenerUtil;
import org.eclipse.fordiac.ide.export.utils.ICompareEditorOpener;
import org.eclipse.fordiac.ide.export.utils.PreferenceConstants;
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
public class FORTEExportPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * Instantiates a new fORTE export preferences.
	 */
	public FORTEExportPreferences() {
		super(GRID);
		setPreferenceStore(org.eclipse.fordiac.ide.export.Activator.getDefault()
				.getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		// Create a Group to hold the compare editor fields
		Group compare = new Group(getFieldEditorParent(), SWT.NONE);
		compare.setText("Compare Editor for Merging");
		GridLayout routerLayout = new GridLayout(2, false);

		Map<String, ICompareEditorOpener> compareEditorOpeners = CompareEditorOpenerUtil.getCompareEditorOpeners();

		
		Set<String> keySet = compareEditorOpeners.keySet();
		String nameArray[][] = new String[keySet.size()][2];
		int i = 0;
		for (String key : keySet) {
			nameArray[i][0] = key;
			nameArray[i][1] = key;
			i++;
		}

		ComboFieldEditor compareEditor = new ComboFieldEditor(
				PreferenceConstants.P_COMPARE_EDITOR, "Default CompareEditor Opener",
				nameArray,
				compare);
		addField(compareEditor);

		GridData comparedata = new GridData(GridData.FILL_HORIZONTAL);
		comparedata.grabExcessHorizontalSpace = true;
		comparedata.horizontalSpan = 2;

		compare.setLayoutData(comparedata);
		compare.setLayout(routerLayout);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		//Currently nothing to do here
	}

}