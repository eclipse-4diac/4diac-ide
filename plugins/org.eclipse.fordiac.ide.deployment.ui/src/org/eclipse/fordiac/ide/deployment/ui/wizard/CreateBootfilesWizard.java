/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.ui.Activator;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
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

public class CreateBootfilesWizard extends Wizard implements IExportWizard {

	private static final String FORDIAC_CREATE_BOOTFILES_SECTION = "4DIAC_CREATE_BOOTFILES_SECTION"; //$NON-NLS-1$
	
	private IStructuredSelection selection;
	private CreateBootFilesWizardPage page;

	public CreateBootfilesWizard() {
		setWindowTitle(Messages.FordiacCreateBootfilesWizard_LABEL_Window_Title);
		IDialogSettings settings = Activator.getDefault().getDialogSettings();

		if (null != settings.getSection(FORDIAC_CREATE_BOOTFILES_SECTION)) {
			//if section does not exist create it
			settings.addNewSection(FORDIAC_CREATE_BOOTFILES_SECTION);
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
		page = new CreateBootFilesWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		IRunnableWithProgress iop = new IRunnableWithProgress(){

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {				
				String outputDirectory = page.getDirectory();
				Map<Device, List<Object> > workLoad = prepareWorkload(); 				
				monitor.beginTask("Generating boot-files for the selected resources", workLoad.size());				
				for (Entry<Device, List<Object>> entry : workLoad.entrySet()) {
					String fileName = outputDirectory +  File.separatorChar +  entry.getKey().getAutomationSystem().getName() + "_" + entry.getKey().getName() + ".fboot";				
					BootFileDeviceManagementCommunicationHandler.createBootFile(entry.getValue(), fileName, getShell());
					monitor.worked(1);
				}
				monitor.done();				
			}			
		};
		
		try {	    
			new ProgressMonitorDialog(getShell()).run(false, false, iop);
	    } catch (Exception e) {
	    	MessageBox msg = new MessageBox(getShell(), SWT.ERROR);
			msg.setMessage("Boot-file creation error:\n" + e.getMessage());
			msg.open();
			Activator.getDefault().logError(msg.getMessage(), e);
	    }	

		return true;
	}

	private Map<Device, List<Object>> prepareWorkload() {
		Object[] selectedElements = page.getSelectedElements();
		Map<Device, List<Object> > workLoad = new HashMap<>();

		for (Object object : selectedElements) {
			if(object instanceof Resource){
				insertResource(workLoad, (Resource)object);
			}else if (object instanceof Device){
				//if the device is selected we need to add it so that its parameters are added as well
				getWorkLoadEntryList(workLoad, (Device)object).add(object);
			}
		}		
		return workLoad;
	}

	private static void insertResource(Map<Device, List<Object>> workLoad, Resource res) {
		List<Object> resList = getWorkLoadEntryList(workLoad, res.getDevice());
		resList.add(res);
	}

	private static List<Object> getWorkLoadEntryList(Map<Device, List<Object>> workLoad, Device device) {
		if(!workLoad.containsKey(device)){
			workLoad.put(device, new ArrayList<Object>());
		}
		return workLoad.get(device);
	}
	
}
