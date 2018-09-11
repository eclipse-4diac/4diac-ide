/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.application.wizards;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class SaveAsSubappWizard extends Wizard {

	private final SubApp subApp;
	
	private SaveAsSubappWizardPage newFilePage;
	
	/** The format. */
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public SaveAsSubappWizard(SubApp subApp) {
		setWindowTitle(Messages.SaveAsSubApplicationTypeAction_WizardTitle);
		this.subApp = subApp;
	}
	
	@Override
	public void addPages() {
		IProject project = subApp.getSubAppNetwork().getAutomationSystem().getProject();
		StructuredSelection selection = new StructuredSelection(project);     //select the current project
		newFilePage = new SaveAsSubappWizardPage(Messages.SaveAsSubApplicationTypeAction_WizardPageName, selection);	
		newFilePage.setFileName(subApp.getName());		
		addPage(newFilePage);
	}
	
	@Override
	public boolean performFinish() {
		boolean perform = true;
		
		IFile targetFile = getTargetTypeFile();
		if(targetFile.exists()){
			perform = askOverwrite();
		}
		
		
		if(perform){		
			if(createSubAppTemplateCopy()){  		// copy the subapp template so that we don't need to write code for any basic type information stuff (e.g., version, coments etc.)
				PaletteEntry entry = getPalletEntry();
				LibraryElement type = entry.getType();
				type.setName(TypeLibrary.getTypeNameFromFile(entry.getFile()));
				
				setupVersionInfo(type);				
				performTypeSetup((SubAppType)type);				
				CommonElementExporter.saveType(entry);	
				entry.setType(type);
				
				if(newFilePage.getOpenType()){
					openTypeEditor(entry);
				}
				
				return true;
			}		
			return false;
		}
		
		return true;
	}

	private boolean askOverwrite() {	
		return MessageDialog.openConfirm(getShell(), Messages.SaveAsSubApplicationTypeAction_WizardOverrideTitle, 
				Messages.SaveAsSubApplicationTypeAction_WizardOverrideMessage);
	}

	private boolean createSubAppTemplateCopy(){
		String templateFolderPath = Platform.getInstallLocation().getURL().getFile();
		File templateFolder = new File(templateFolderPath + File.separatorChar + "template"); //$NON-NLS-1$
		File [] fileList = templateFolder.listFiles();
		if(null != fileList) {
			for (File file : fileList) {
				String fileName = file.getName().toUpperCase(); 			
				if(fileName.endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING)){
					IFile targetTypeFile = getTargetTypeFile();
					try {
						ImportUtils.copyFile(file, targetTypeFile);
						return true;
					} catch (IOException | CoreException e) {
						ApplicationPlugin.getDefault().logError(e.getMessage(), e);
					}
				}
			}
		}
		return false;
	}

	private IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath() + 
				File.separator + newFilePage.getFileName() + TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT));
	}
	
	private PaletteEntry getPalletEntry() {
		Palette palette = subApp.getSubAppNetwork().getAutomationSystem().getPalette();
		IFile targetTypeFile = getTargetTypeFile();
		PaletteEntry entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);
		
		if(null == entry){
			//refresh the palette and retry to fetch the entry
			TypeLibrary.refreshPalette(palette);
			entry = TypeLibrary.getPaletteEntry(palette, targetTypeFile);
		}
		
		return entry;
	}
	
	private static void openTypeEditor(PaletteEntry entry) {		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
		IEditorDescriptor desc = PlatformUI.getWorkbench().
		        getEditorRegistry().getDefaultEditor(entry.getFile().getName());
		try {
			page.openEditor(new FileEditorInput(entry.getFile()), desc.getId());
		} catch (PartInitException e) {
			ApplicationPlugin.getDefault().logError(e.getMessage(), e);
		}				
	}
	
	private void performTypeSetup(SubAppType type) {
		performInterfaceSetup(type);
		performSubappNetworkSetup(type);		
	}

	private void performInterfaceSetup(SubAppType type) {
		//replace interface list with newly generated
		InterfaceList interfaceList = EcoreUtil.copy(subApp.getInterface());
		type.setInterfaceList(interfaceList);		
	}

	private void performSubappNetworkSetup(SubAppType type) {
		FBNetwork dstNetwork = EcoreUtil.copy(subApp.getSubAppNetwork());
		
		dstNetwork.getEventConnections().clear();
		for (Connection connection : subApp.getSubAppNetwork().getEventConnections()) {
			dstNetwork.getEventConnections().add((EventConnection)createConnection(type, dstNetwork, connection));
		}
		
		dstNetwork.getDataConnections().clear();
		for (Connection connection : subApp.getSubAppNetwork().getDataConnections()) {
			dstNetwork.getDataConnections().add((DataConnection)createConnection(type, dstNetwork, connection));
		}
		
		dstNetwork.getAdapterConnections().clear();
		for (Connection connection : subApp.getSubAppNetwork().getAdapterConnections()) {
			dstNetwork.getAdapterConnections().add((AdapterConnection)createConnection(type, dstNetwork, connection));
		}
		
		type.setFBNetwork(dstNetwork);		
	}

	private Connection createConnection(SubAppType type, FBNetwork dstNetwork, Connection connection) {
		Connection newConn = EcoreUtil.copy(connection);
		newConn.setSource(getInterfaceElement(connection.getSource(), type.getInterfaceList(), dstNetwork));
		newConn.setDestination(getInterfaceElement(connection.getDestination(), type.getInterfaceList(), dstNetwork));
		return newConn;
	}

	private IInterfaceElement getInterfaceElement(IInterfaceElement ie, InterfaceList typeInterface, FBNetwork dstNetwork) {
		if(subApp.equals(ie.getFBNetworkElement())) {
			return typeInterface.getInterfaceElement(ie.getName());
		}		
		FBNetworkElement element = dstNetwork.getElementNamed(ie.getFBNetworkElement().getName());	
		if(null == element) {
			return null;
		}
		return element.getInterfaceElement(ie.getName());
	}

	private void setupVersionInfo(LibraryElement type) {
		//copied from newFBTypeWizard  TODO consider if we can put this into a comon place
		VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		versionInfo.setAuthor(System.getProperty("user.name")); //$NON-NLS-1$
		versionInfo.setDate(format.format(new Date(System.currentTimeMillis())));
		versionInfo.setVersion("1.0"); //$NON-NLS-1$
		if(type instanceof AdapterType) {
			type = ((AdapterType)type).getAdapterFBType();
		}		
		type.getVersionInfo().clear();
		type.getVersionInfo().add(versionInfo);
	}

}
