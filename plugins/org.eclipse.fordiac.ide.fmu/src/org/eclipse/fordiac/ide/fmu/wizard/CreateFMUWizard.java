/*******************************************************************************
 * Copyright (c) 2007 - 2013 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.fmu.Activator;
import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.fordiac.ide.fmu.preferences.PreferenceConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class CreateFMUWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_CREATE_FMU_SECTION = "4DIAC_CREATE_FMU_SECTION"; //$NON-NLS-1$
	
	private IStructuredSelection selection;
	private CreateFMUWizardPage page;

	public CreateFMUWizard() {
		setWindowTitle(Messages.FordiacCreateFMUWizard_LABEL_Window_Title);
		
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		IDialogSettings dialogSettings = settings.getSection(FORDIAC_CREATE_FMU_SECTION);
		if (dialogSettings == null) {
			settings.addNewSection(FORDIAC_CREATE_FMU_SECTION);
		}
		setDialogSettings(settings);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = new StructuredSelection(selection.toList());
	}
	
	@Override
	public void addPages() {
		super.addPages();
		page = new CreateFMUWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		
		//Store the selection in the preferences
		if (page.getStoreSelectedLibaries().getSelection()){
			Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_FMU_WIN32, page.getWin32Field().getSelection());
			Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_FMU_WIN64, page.getWin64Field().getSelection());
			Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_FMU_LIN32, page.getLinux32Field().getSelection());
			Activator.getDefault().getPreferenceStore().setValue(PreferenceConstants.P_FMU_LIN64, page.getLinux64Field().getSelection());
		}
		
		IRunnableWithProgress iop = new IRunnableWithProgress(){
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				
				String outputDirectory = page.getDirectory();
				Map<Device, List<Resource> > toDeploy = addResourcesAndDevices(); 
				//Store the selected libraries to include in the FMU
				List<String> librariesToAdd = getLibraries();
				
				for (Entry<Device, List<Resource>> entry : toDeploy.entrySet()) {
					FMUDeviceManagementCommunicationHandler.createFMU(entry.getKey(), entry.getValue(), librariesToAdd, outputDirectory, getShell(), monitor);
					
				}
				monitor.done();
			}
		};
		
		try {	    
			new ProgressMonitorDialog(getShell()).run(false, false, iop);
	    } catch (Exception e) {
	    	MessageBox msg = new MessageBox(getShell(), SWT.ERROR);
			msg.setMessage("FMU creation error:\n" + e.getMessage());
			msg.open();
			Activator.getDefault().logError(msg.getMessage(), e);
	    }	

		return true;
	}
	
	private List<String> getLibraries() {
		
		List<String> libs = new ArrayList<>();
		if(page.getWin32Field().isEnabled() && page.getWin32Field().getSelection()) {
			libs.add(PreferenceConstants.P_FMU_WIN32);
		}
		if(page.getWin64Field().isEnabled() && page.getWin64Field().getSelection()) {
			libs.add(PreferenceConstants.P_FMU_WIN64);
		}
		if (page.getLinux32Field().isEnabled() && page.getLinux32Field().getSelection()) {
			libs.add(PreferenceConstants.P_FMU_LIN32);
		}
		if (page.getLinux64Field().isEnabled() && page.getLinux64Field().getSelection()) {
			libs.add(PreferenceConstants.P_FMU_LIN64);
		}
		return libs;
	}

	private Map<Device, List<Resource>> addResourcesAndDevices() {
		Object[] selectedElements = page.getSelectedElements();
		HashMap<Device, List<Resource> > toDeploy = new HashMap<>();

		for (Object object : selectedElements) {
			if(object instanceof Resource){
				insertResource(toDeploy, (Resource)object);
			}else if (object instanceof Device){
				//if the device is selected we need to add it so that its parameters are added as well
				getWorkLoadEntryList(toDeploy, (Device)object);
			}
		}		
		return toDeploy;
	}

	private void insertResource(Map<Device, List<Resource>> workLoad,
			Resource res) {
		List<Resource> resList = getWorkLoadEntryList(workLoad, res.getDevice());
		resList.add(res);
	}

	private List<Resource> getWorkLoadEntryList(
			Map<Device, List<Resource>> toDeploy, Device device) {
		if(!toDeploy.containsKey(device)){
			toDeploy.put(device, new ArrayList<Resource>());
		}
		
		return toDeploy.get(device);
	}
	
}
