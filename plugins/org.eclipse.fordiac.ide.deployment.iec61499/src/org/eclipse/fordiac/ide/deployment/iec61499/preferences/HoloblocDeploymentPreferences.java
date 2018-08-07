/*******************************************************************************
 * Copyright (c) 2009, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.preferences;

import org.eclipse.fordiac.ide.deployment.iec61499.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The Class HoloblocDeploymentPreferences.
 */
public class HoloblocDeploymentPreferences extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	/** The Constant CONNECTION_TIMEOUT. */
	static final String CONNECTION_TIMEOUT = "Connection Timeout"; //$NON-NLS-1$
	
	/* default conneciton timeout value in ms */
	static final int CONNECTION_TIMEOUT_DEFAULT_VALUE = 3000;
	
	/*check if there is a conneciton timeout value set and if not return the default value*/
	public static int getConnectionTimeout(){
		int retVal = Activator.getDefault().getPreferenceStore().getInt(HoloblocDeploymentPreferences.CONNECTION_TIMEOUT);		
		if(0 == retVal){
			retVal = CONNECTION_TIMEOUT_DEFAULT_VALUE;
		}				
		return retVal;
	}

	/**
	 * Instantiates a new holobloc deployment preferences.
	 */
	public HoloblocDeploymentPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(
				CONNECTION_TIMEOUT, "Connection Timout in ms", getFieldEditorParent(),
				3000);
		integerFieldEditor.setValidRange(1, 60000);

		addField(integerFieldEditor);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		//nothing todo here
	}

}