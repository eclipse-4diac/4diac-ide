/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.wizard;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.deployment.ui.views.DownloadSelectionTree;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.controls.DirectoryChooserControl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class CreateBootFilesWizardPage extends WizardPage {

	private static final String SETTING_CURRENT_DIR = "currentDir"; //$NON-NLS-1$
	private IStructuredSelection selection;
	private DirectoryChooserControl dcc;
	private DownloadSelectionTree systemTree;
	
	public CreateBootFilesWizardPage(IStructuredSelection selection) {
		super(Messages.FordiacCreateBootfilesWizard_PageName);
		
		this.selection = selection;
		
		setDescription(Messages.FordiacCreateBootfilesWizard_PageDESCRIPTION);
		setTitle(Messages.FordiacCreateBootfilesWizard_PageTITLE);
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
		dcc.addDirectoryChangedListener(newDirectory -> {
				saveDir(newDirectory);
				setPageComplete(validatePage());
		});

		dcc.setLayoutData(stretch);
		loadDir();
	}
	
	/**
	 * Returns whether this page's controls currently all contain valid values.
	 * 
	 * @return <code>true</code> if all controls are valid, and <code>false</code>
	 *         if at least one is invalid
	 */
	private boolean validatePage() {
		if (dcc.getDirectory() == null || dcc.getDirectory().equals("")) { //$NON-NLS-1$
			setErrorMessage("Destination not selected!");
			return false;
		}
				
		if(0 == systemTree.getCheckedElements().length){
			setErrorMessage("Nothing selected for boot-file generation!");
			return false;
		}

		setErrorMessage(null);
		return true;
	}
	
	/**
	 * Loads cached directory, if available.
	 */
	private void loadDir() {
		if (getDialogSettings() != null) {
			String cachedDir = getDialogSettings().get(SETTING_CURRENT_DIR);
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
		getDialogSettings().put(SETTING_CURRENT_DIR, currentDir);
	}

	
	private void checkSelectedElements() {
		
		//first expand all selected elements
		for (Object obj : selection.toArray()) {
			if(obj instanceof AutomationSystem){
				expandSystem(obj);
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
