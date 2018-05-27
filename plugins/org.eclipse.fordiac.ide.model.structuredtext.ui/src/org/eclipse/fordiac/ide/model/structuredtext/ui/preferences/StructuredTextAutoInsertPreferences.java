/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University Linz (JKU)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.ui.preferences;

import org.eclipse.fordiac.ide.model.structuredtext.ui.ExtendedStructuredTextActivator;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class StructuredTextAutoInsertPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public StructuredTextAutoInsertPreferences() {
		super(GRID);
		setPreferenceStore(ExtendedStructuredTextActivator.getInstance().getPreferenceStore());
	}
	
	@Override
	public void init(IWorkbench workbench) {
		//nothing todo here
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(PreferenceInitializer.AUTO_INSERT, "Automatic insert closing braces and quotes", getFieldEditorParent()));
	}

}
