/*******************************************************************************
 * Copyright (c) 2007 - 2010 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fmu.Activator;
import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class FMUPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	
	private Group librariesGroup;
	private BooleanFieldEditor win32Field;
	private BooleanFieldEditor win64Field;
	private BooleanFieldEditor linux32Field;
	private BooleanFieldEditor linux64Field;
	
	/**
	 * Instantiates a new forte preference page.
	 */
	public FMUPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("FMU Preferences Page");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Binaries Location:", getFieldEditorParent()));
		
		librariesGroup = new Group(getFieldEditorParent(), SWT.NONE);
		librariesGroup.setText("Include the following libraries in exported FMU");
		
		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		// Add the fields to the group
		win32Field = new BooleanFieldEditor(PreferenceConstants.P_FMU_WIN32, PreferenceConstants.P_FMU_WIN32, librariesGroup);
		win32Field.setEnabled(false, librariesGroup);
		addField(win32Field);
		
		win64Field = new BooleanFieldEditor(PreferenceConstants.P_FMU_WIN64, PreferenceConstants.P_FMU_WIN64, librariesGroup);
		win64Field.setEnabled(false, librariesGroup);
		addField(win64Field);
		
		linux32Field = new BooleanFieldEditor(PreferenceConstants.P_FMU_LIN32, PreferenceConstants.P_FMU_LIN32, librariesGroup);
		linux32Field.setEnabled(false, librariesGroup);
		addField(linux32Field);
		
		linux64Field = new BooleanFieldEditor(PreferenceConstants.P_FMU_LIN64, PreferenceConstants.P_FMU_LIN64, librariesGroup);
		linux64Field.setEnabled(false, librariesGroup);
		addField(linux64Field);
		
		librariesGroup.setLayoutData(gridData);
		librariesGroup.setLayout(gridLayout);
		
		updateEnabledLibraries(true, Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH));
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		 if (event.getSource() instanceof DirectoryFieldEditor && ((DirectoryFieldEditor)event.getSource()).getPreferenceName().equals(PreferenceConstants.P_PATH)){
			 if (event.getNewValue() instanceof String){
				 updateEnabledLibraries(true, (String)event.getNewValue());	 
			 }else if(event.getNewValue() instanceof Boolean){//When directory doesn't exists, the event is a false boolean
				 updateEnabledLibraries(false, null);	 
			 }
			 
         }
		super.propertyChange(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setDescription("Inside the selected path the files named win32Forte.dll, win64Forte.dll, linux32Forte.so and linux64Forte.are searched for");
	}
	
	private void updateEnabledLibraries(Boolean validPath, String pathString){
		
		if (validPath){
			win32Field.setEnabled(new File(pathString + File.separatorChar + "win32Forte.dll").
					exists(), librariesGroup);
			win64Field.setEnabled(new File(pathString + File.separatorChar + "win64Forte.dll").
					exists(), librariesGroup);
			linux32Field.setEnabled(new File(pathString + File.separatorChar + "linux32Forte.so").
					exists(), librariesGroup);
			linux64Field.setEnabled(new File(pathString + File.separatorChar + "linux64Forte.so").
					exists(), librariesGroup);
		}else{
			win32Field.setEnabled(false, librariesGroup);
			win64Field.setEnabled(false, librariesGroup);
			linux32Field.setEnabled(false, librariesGroup);
			linux64Field.setEnabled(false, librariesGroup);
		}
		
	}
	
	public static List<String> getFoundLibraries(){
		List<String> found = new ArrayList<>();
		String pathString = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH);
		if ((new File(pathString + File.separatorChar + "win32Forte.dll").exists())){
			found.add(PreferenceConstants.P_FMU_WIN32);
		}
		if ((new File(pathString + File.separatorChar + "win64Forte.dll").exists())){
			found.add(PreferenceConstants.P_FMU_WIN64);
		}
		if ((new File(pathString + File.separatorChar + "linux32Forte.so").exists())){
			found.add(PreferenceConstants.P_FMU_LIN32);
		}
		if ((new File(pathString + File.separatorChar + "linux64Forte.so").exists())){
			found.add(PreferenceConstants.P_FMU_LIN64);
		}
		
		return found;
	}
	
	
}













