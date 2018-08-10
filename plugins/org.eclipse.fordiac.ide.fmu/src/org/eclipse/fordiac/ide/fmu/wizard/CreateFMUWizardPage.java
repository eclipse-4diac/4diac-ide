/*******************************************************************************
 * Copyright (c) 2007 - 2013 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import org.eclipse.fordiac.ide.fmu.Activator;
import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.fordiac.ide.fmu.preferences.FMUPreferencePage;
import org.eclipse.fordiac.ide.fmu.preferences.PreferenceConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTree;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.controls.DirectoryChooserControl;

public class CreateFMUWizardPage extends WizardPage {

	private IStructuredSelection selection;
	private DirectoryChooserControl dcc;
	private DownloadSelectionTree systemTree;
	
	public Button getWin32Field() {
		return win32Field;
	}

	public Button getWin64Field() {
		return win64Field;
	}

	public Button getLinux32Field() {
		return linux32Field;
	}


	public Button getLinux64Field() {
		return linux64Field;
	}

	public Button getStoreSelectedLibaries() {
		return storeSelectedLibaries;
	}

	private Button win32Field = null;
	private Button win64Field;
	private Button linux32Field;
	private Button linux64Field;
	private Button storeSelectedLibaries;
	
	public CreateFMUWizardPage(IStructuredSelection selection) {
		super(Messages.FordiacCreateFMUWizard_PageName);
		
		this.selection = selection;
		
		setDescription(Messages.FordiacCreateFMUWizard_PageDESCRIPTION);
		setTitle(Messages.FordiacCreateFMUWizard_PageTITLE);
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());

		initializeDialogUnits(parent);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		createSystemsContainer(composite);
		createDestinationGroup(composite);
		createSelectionLibrariesGroup(composite);
		
		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);

	}
	
	
	public Object[] getSelectedElements(){
		return systemTree.getCheckedElements();
	}


	private void createSystemsContainer(Composite composite) {
		systemTree = new DownloadSelectionTree( composite, SWT.FULL_SELECTION
						| SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		
		GridData fillBoth = new GridData();
		fillBoth.horizontalAlignment = GridData.FILL;
		fillBoth.grabExcessHorizontalSpace = true;
		fillBoth.verticalAlignment = GridData.FILL;
		fillBoth.grabExcessVerticalSpace = true;
		systemTree.getTree().setLayoutData(fillBoth);
		
		
		systemTree.setInput(this); //the systemTree needs this only as reference
		
		systemTree.addCheckStateListener(event -> setPageComplete(validatePage()));
		
		checkSelectedElements();
	}
	
	/**
	 * Creates the file name group.
	 * 
	 * @param composite
	 *          the composite
	 */
	private void createDestinationGroup(final Composite composite) {

		GridData stretch = new GridData();
		stretch.grabExcessHorizontalSpace = true;
		stretch.horizontalAlignment = SWT.FILL;

		dcc = new DirectoryChooserControl(composite, SWT.NONE, "Destination: ");
		dcc.addDirectoryChangedListener(newDirectory ->  {
				saveDir(newDirectory);
				setPageComplete(validatePage());
		});

		dcc.setLayoutData(stretch);
		loadDir();
	}
	
	
	/**
	 * Creates the selection libraries group.
	 * 
	 * @param composite
	 *          the composite
	 */
	private void createSelectionLibrariesGroup(final Composite composite) {
		
		Group librariesGroup = new Group(composite, SWT.NONE);
		librariesGroup.setText("Include the following libraries in exported FMU");
		
		GridLayout gridLayout = new GridLayout(1, false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;

		win32Field = new Button(librariesGroup, SWT.CHECK);
		win64Field = new Button(librariesGroup, SWT.CHECK);
		linux32Field = new Button(librariesGroup, SWT.CHECK);
		linux64Field = new Button(librariesGroup, SWT.CHECK);
		storeSelectedLibaries = new Button(librariesGroup, SWT.CHECK);
		Button[] buttons = { win32Field, win64Field, linux32Field, linux64Field, storeSelectedLibaries};
		String[] preferences = {PreferenceConstants.P_FMU_WIN32, PreferenceConstants.P_FMU_WIN64, PreferenceConstants.P_FMU_LIN32, PreferenceConstants.P_FMU_LIN64, "Save selected libraries for future FMU exports"};
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setText(preferences[i]);
			buttons[i].setEnabled(false);
			buttons[i].setSelection(false);
		}
		
		storeSelectedLibaries.setEnabled(true);
		
		//Enable the found libraries
		for (String found : FMUPreferencePage.getFoundLibraries()){
			if (found.equals(PreferenceConstants.P_FMU_WIN32)){
				win32Field.setEnabled(true);
			}else if(found.equals(PreferenceConstants.P_FMU_WIN64)){
				win64Field.setEnabled(true);
			}
			else if(found.equals(PreferenceConstants.P_FMU_LIN32)){
				linux32Field.setEnabled(true);
			}
			else if(found.equals(PreferenceConstants.P_FMU_LIN64)){
				linux64Field.setEnabled(true);
			}
		}
		
		SelectionListener listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}	
		}; 
			
		//Check the selected libraries from preferences
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setSelection(Activator.getDefault().getPreferenceStore().getBoolean(preferences[i]));
			buttons[i].addSelectionListener(listener);
		}
		
		librariesGroup.setLayoutData(gridData);
		librariesGroup.setLayout(gridLayout);
	}
	
	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and <code>false</code>
	 *         if at least one is invalid
	 */
	private boolean validatePage() {
		if (dcc.getDirectory() == null || dcc.getDirectory().equals("")) {
			setErrorMessage("Destination not selected!");
			return false;
		}
				
		if(0 == systemTree.getCheckedElements().length){
			setErrorMessage("Nothing selected for FMU generation!");
			return false;
		}
		
		if (!isSomeLibrarySelected()){
			setErrorMessage("No libraries selected to include");
			return false;
		}

		setErrorMessage(null);
		return true;
	}
	
	private boolean isSomeLibrarySelected() {
		return (isSelected(win32Field) 
				|| isSelected(win64Field) 
				|| isSelected(linux32Field) 
				|| isSelected(linux64Field));
	}
	
	private boolean isSelected(Button toCheck) {
		return (toCheck.isEnabled() && toCheck.getSelection());
	}
	
	/**
	 * Loads cached directory, if available.
	 */
	private void loadDir() {
		if (getDialogSettings() != null) {
			String cachedDir = getDialogSettings().get("currentDir");
			if (cachedDir != null) {
				setDirectory(cachedDir);
			}
		}
	}
	
	/**
	 * Sets the directory.
	 * 
	 * @param dir the new directory
	 */
	public void setDirectory(final String dir) {
		dcc.setDirectory(dir);
	}
	
	public String getDirectory() {
		return dcc.getDirectory();
	}
	
	/**
	 * Saves current directory for next session.
	 * 
	 * @param currentDir
	 *          the current dir
	 */
	private void saveDir(final String currentDir) {
		getDialogSettings().put("currentDir", currentDir);
	}

	
	private void checkSelectedElements() {
		
		//first expand all selected elements
		for (Object obj : selection.toArray()) {
			if(obj instanceof AutomationSystem){
				expandSystem((AutomationSystem)obj);
			}else if(obj instanceof Device){
				expandDevice((Device)obj);
			}else if(obj instanceof Resource){
				expandResource((Resource)obj);
			}			
		}
		
		//second select them and then check them.
		systemTree.setSelection(selection);

		systemTree.setCheckedElements(selection.toArray());
	}

	private void expandResource(Resource obj) {
		expandDevice(obj.getDevice());
	}

	private void expandDevice(Device obj) {
		expandSystem(obj.getAutomationSystem());
		systemTree.setExpandedState(obj, true);
	}

	private void expandSystem(Object obj) {
		systemTree.setExpandedState(obj, true);
	}

	
}
