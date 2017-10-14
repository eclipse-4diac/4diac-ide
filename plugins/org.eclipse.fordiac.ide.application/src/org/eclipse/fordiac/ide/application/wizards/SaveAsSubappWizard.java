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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
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

	final SubApp subApp;
	
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
				
				if (0 != type.getVersionInfo().size()) {
					VersionInfo versionInfo = type.getVersionInfo().get(0);
					versionInfo.setDate(format.format(new Date(System
							.currentTimeMillis())));
				}
				
				performTypeSetup((SubAppType)type);
				
				CommonElementExporter.saveType(entry);
				
				
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
		
		for (File file : templateFolder.listFiles()) {
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
		return false;
	}

	private IFile getTargetTypeFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(newFilePage.getContainerFullPath() + 
				"/" + newFilePage.getFileName() + TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT));
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
	
	private void openTypeEditor(PaletteEntry entry) {		
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
	InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
		type.setInterfaceList(interfaceList);
		
		//the internal subapp interface elements are the ones which also have an outgoing connection only put this into the interface of the new subapp type
//TODO model refactoring - implement when new subappmodel is finished
//		for (InternalSubAppInterfaceElementView interfaceElement : subAppView.getUiSubAppNetwork().getInterfaceElements()) {
//			IInterfaceElement element = interfaceElement.getIInterfaceElement();
//			
//			IInterfaceElement newInterfaceElement = null;		
//					
//			if(element instanceof Event){
//				newInterfaceElement = createEventElement(type.getInterfaceList(), (Event)element); 
//									
//			}else if (element instanceof AdapterDeclaration){
//				newInterfaceElement = createAdapterElement(type.getInterfaceList(), (AdapterDeclaration)element);					
//			}else if (element instanceof VarDeclaration){
//				newInterfaceElement = createVarElement(type.getInterfaceList(), (VarDeclaration)element);				
//			}
//			
//			if(null != newInterfaceElement){
//				newInterfaceElement.setName(interfaceElement.getLabel().replace('.', '_')); //use the aggregated name for the new interface elements name
//				newInterfaceElement.setComment(element.getComment());
//				newInterfaceElement.setIsInput(!element.isIsInput());				
//			}
//			
//		}
		
	}

	private IInterfaceElement createEventElement(InterfaceList interfaceList,
			Event element) {
		Event e = LibraryElementFactory.eINSTANCE.createEvent();
		if(element.isIsInput()){   //currently the element used in subapp internal interface views is the interface element of the corresponding internal FB therefore we have to mirror the inputs and outputs here
			interfaceList.getEventOutputs().add(e);
		}
		else{	
			interfaceList.getEventInputs().add(e);
		}		
		return e;
	}

	private IInterfaceElement createAdapterElement(InterfaceList interfaceList,
			AdapterDeclaration element) {
		AdapterDeclaration a = LibraryElementFactory.eINSTANCE.createAdapterDeclaration();
		a.setType(element.getType());
		a.setTypeName(element.getTypeName());
		
		if(element.isIsInput()){   //currently the element used in subapp internal interface views is the interface element of the corresponding internal FB therefore we have to mirror the inputs and outputs here
			interfaceList.getPlugs().add(a);
		}
		else{	
			interfaceList.getSockets().add(a);
		}
		return a;
	}

	private IInterfaceElement createVarElement(InterfaceList interfaceList,
			VarDeclaration element) {
		VarDeclaration v = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		v.setType(element.getType());
		v.setTypeName(element.getTypeName());
		v.setArraySize(element.getArraySize());
		
		if(element.isIsInput()){   //currently the element used in subapp internal interface views is the interface element of the corresponding internal FB therefore we have to mirror the inputs and outputs here
			interfaceList.getOutputVars().add(v);
		}
		else{	
			interfaceList.getInputVars().add(v);
		}	
		return v;
	}

	private void performSubappNetworkSetup(SubAppType type) {
		FBNetwork srcNetwork = subApp.getSubAppNetwork();
		FBNetwork dstNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		type.setFBNetwork(dstNetwork);
		
		dstNetwork.getNetworkElements().addAll(EcoreUtil.copyAll(srcNetwork.getNetworkElements()));				
		
		copyEventConnections(type);
		copyDataConnections(type);		
		copyInterfaceConnections(type);
		//TODO src officially does not have adapter connections 
	}

	private void copyEventConnections(SubAppType type) {
		FBNetwork srcNetwork = subApp.getSubAppNetwork();
		for (EventConnection eCon : srcNetwork.getEventConnections()) {
			Event src = getEventConTarget(eCon.getEventSource(), type);
			Event dst = getEventConTarget(eCon.getEventDestination(), type);			
			createEventConnection(type.getFBNetwork(), src, dst, eCon);
		}		
	}

	private void copyDataConnections(SubAppType type) {
		FBNetwork srcNetwork = subApp.getSubAppNetwork();
		for (DataConnection dCon : srcNetwork.getDataConnections()) {
			VarDeclaration src = getDataConTarget(dCon.getDataSource(), type);
			VarDeclaration dst = getDataConTarget(dCon.getDataDestination(), type);			
			createDataConnection(type.getFBNetwork(), src, dst, dCon);
		}
	}	

	private void copyInterfaceConnections(SubAppType type) {
		//currently untyped subapps store interface connection information in the ui model only	
		//TODO with new subapp design this code may not be necessary any more
		FBNetwork dstNetwork = type.getFBNetwork();
		
//		for (ConnectionView iConnectionView : subAppView.getUiSubAppNetwork().getConnections()) {
//			ConnectionView conView = (ConnectionView)iConnectionView;  //we only have ConnectionViews in our network
//			if(conView.getSource().getIInterfaceElement() instanceof Event){				
//				Event src = getEventConTarget(conView.getSource(), type);
//				Event dst = getEventConTarget(conView.getDestination(), type);				
//				createEventConnection(dstNetwork, src, dst, null);				
//			}else{				
//				VarDeclaration src = getDataConTarget(conView.getSource(), type);;
//				VarDeclaration dst = getDataConTarget(conView.getDestination(), type);				
//				createDataConnection(dstNetwork, src, dst, null);
//			}
//		}
	}

	private void createEventConnection(FBNetwork dstNetwork, Event src,
			Event dst, EventConnection refEventCon) {
		if((null != src) && (null != dst)){
			EventConnection eventCon = LibraryElementFactory.eINSTANCE.createEventConnection();
			eventCon.setResTypeConnection(true);
			eventCon.setSource(src);
			eventCon.setDestination(dst);
			if(null != refEventCon){
				performGeneralConnectionConfiguration(eventCon, refEventCon);
			}
			if(!connectionExists(dstNetwork.getEventConnections(), eventCon)){
				dstNetwork.getEventConnections().add(eventCon);
			}
		}
	}
	
	private void createDataConnection(FBNetwork dstNetwork, VarDeclaration src,
			VarDeclaration dst, DataConnection refDataCon) {
		if((null != src) && (null != dst)){
			DataConnection dataCon = LibraryElementFactory.eINSTANCE.createDataConnection();
			dataCon.setResTypeConnection(true);
			dataCon.setSource(src);
			dataCon.setDestination(dst);
			if(null != refDataCon){
				performGeneralConnectionConfiguration(dataCon, refDataCon);
			}
			if(!connectionExists(dstNetwork.getDataConnections(), dataCon)){
				dstNetwork.getDataConnections().add(dataCon);
			}
		}
	}

	//TODO with the new subapp design coming this should also not be necessary anymore
	private boolean connectionExists(EList<? extends Connection> connectionList,
			Connection newConnection) {
		
		for (Connection connection : connectionList) {
			if((connection.getSource().equals(newConnection.getSource())) && 
					(connection.getDestination().equals(newConnection.getDestination()))){
				return true;
			}
		}		
		return false;
	}

	private void performGeneralConnectionConfiguration(Connection con,
			Connection refCon) {
		con.setComment(refCon.getComment());
		con.setDx1(refCon.getDx1());
		con.setDx2(refCon.getDx2());
		con.setDy(refCon.getDy());
	}

	private Event getEventConTarget(Event element, SubAppType type) {
		InterfaceList targetElement = getConTargetElement(element, type);
		if(null != targetElement){
			return targetElement.getEvent(element.getName());
		}
		return null;
	}

//	private Event getEventConTarget(InterfaceElementView interfaceElementView, SubAppType type) {
//		SubAppInterfaceList targetElement = getInterfaceConTargetElement(interfaceElementView, type);
//		String targetPortName = getConTargetPortName(interfaceElementView);
//		if(null != targetElement){
//			return targetElement.getEvent(targetPortName);
//		}
//		return null;
//	}
	
	private VarDeclaration getDataConTarget(VarDeclaration element,
			SubAppType type) {
		InterfaceList targetElement = getConTargetElement(element, type);
		if(null != targetElement){
			return targetElement.getVariable(element.getName());
		}
		return null;
	}

//	private VarDeclaration getDataConTarget(InterfaceElementView interfaceElementView, SubAppType type) {
//		SubAppInterfaceList targetElement = getInterfaceConTargetElement(interfaceElementView, type);
//		String targetPortName = getConTargetPortName(interfaceElementView);
//		if(null != targetElement){
//			return targetElement.getVariable(targetPortName);
//		}
//		return null;
//	}
//	
//	private SubAppInterfaceList getInterfaceConTargetElement(
//			InterfaceElementView interfaceElementView, SubAppType type) {
//		if(interfaceElementView instanceof InternalSubAppInterfaceElementView){
//			return type.getInterfaceList();
//		}else{
//			return getConTargetElement(interfaceElementView.getIInterfaceElement(), type);
//		}
//	}

	private InterfaceList getConTargetElement(
			IInterfaceElement iInterfaceElement, SubAppType type) {
		
		FBNetworkElement target =  iInterfaceElement.getFBNetworkElement();
		for (FBNetworkElement element : type.getFBNetwork().getNetworkElements()) {
			if(element.getName().equals(target.getName())){
				return element.getInterface();
			}			
		}
		return null;
	}

//	private String getConTargetPortName(InterfaceElementView interfaceElementView) {
//		if(interfaceElementView instanceof InternalSubAppInterfaceElementView){
//			return interfaceElementView.getLabel().replace('.', '_');
//		}
//		return interfaceElementView.getIInterfaceElement().getName();
//	}

}
