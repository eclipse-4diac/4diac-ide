/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class ResourceCreateCommand extends Command {
	private final ResourceTypeEntry entry;
	private Resource resource;
	private IEditorPart editor;
	private final Device device;
	private final boolean deviceTypeRes;
	private int index = -1;

	public ResourceCreateCommand(final ResourceTypeEntry entry, Device device, boolean deviceTypeRes) {
		this.entry = entry;
		this.device = device;
		this.deviceTypeRes = deviceTypeRes;
	}

	public ResourceCreateCommand(final ResourceTypeEntry entry, Device device, int index, boolean deviceTypeRes) {
		this.entry = entry;
		this.device = device;
		this.index = index;
		this.deviceTypeRes = deviceTypeRes;
	}

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public void execute() {
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
		resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(deviceTypeRes);
		resource.setPaletteEntry(entry);
		resource.getVarDeclarations().addAll(EcoreUtil.copyAll(entry.getResourceType().getVarDeclaration()));
		for (VarDeclaration element : resource.getVarDeclarations()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			element.setValue(value);
		}
		FBNetwork resourceFBNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		if(entry.getResourceType().getFBNetwork() != null) {
			resource.setFBNetwork(resourceFBNetwork);
			createResourceTypeNetwork(resource, entry.getResourceType(), resourceFBNetwork);
		} else {
			resource.setFBNetwork(resourceFBNetwork);
		}
		if (index < 0 || index > device.getResource().size()) {
			index = device.getResource().size();
		}
		device.getResource().add(index, resource);
		// resource name needs to be added after it is inserted in the device so that name checking works
		resource.setName(NameRepository.createUniqueName(resource, entry.getLabel()));
		SystemManager.INSTANCE.notifyListeners();
	}

	//TODO model refactoring - Can this also be used for the resoruce parsing?
	public void createResourceTypeNetwork(final Resource resource,  final ResourceType type, final FBNetwork resourceFBNetwork) {
		Map<String, Event> events = new HashMap<>();
		Map<String, VarDeclaration> varDecls = new HashMap<>();
		for (FBNetworkElement element : type.getFBNetwork().getNetworkElements()) {
			FB copy = LibraryElementFactory.eINSTANCE.createResourceTypeFB();
			resource.getFBNetwork().getNetworkElements().add(copy);
			copy.setPaletteEntry(element.getPaletteEntry());
			copy.setName(element.getName()); // name should be last so that checks
										// are working correctly
			InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
			for (VarDeclaration varDecl : element.getInterface().getOutputVars()) {
				VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
				varDeclCopy.setType(varDecl.getType());
				varDeclCopy.setName(varDecl.getName());
				varDeclCopy.setComment(varDecl.getComment());
				varDeclCopy.setIsInput(varDecl.isIsInput());
				if (varDecl.getValue() != null) {
					varDeclCopy.setValue(EcoreUtil.copy(varDecl.getValue()));
				}
				varDecls.put(element.getName() + "." + varDeclCopy.getName(), //$NON-NLS-1$
						varDeclCopy);
				interfaceList.getOutputVars().add(varDeclCopy);
			}
			for(VarDeclaration varDecl : element.getInterface().getInputVars()) {
				VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
				varDeclCopy.setType(varDecl.getType());
				varDeclCopy.setName(varDecl.getName());
				varDeclCopy.setComment(varDecl.getComment());
				varDeclCopy.setIsInput(varDecl.isIsInput());
				if (varDecl.getValue() != null) {
					varDeclCopy.setValue(EcoreUtil.copy(varDecl.getValue()));
				}
				varDecls.put(element.getName() + "." + varDeclCopy.getName(), //$NON-NLS-1$
						varDeclCopy);
				interfaceList.getInputVars().add(varDeclCopy);
			}
			for (Event event : element.getInterface().getEventInputs()) {
				Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				if (event.getValue() != null) {
					eventCopy.setValue(EcoreUtil.copy(event.getValue()));
				}
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventInputs().add(eventCopy);
			}
			for (Event event : element.getInterface().getEventOutputs()) {
				Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				if (event.getValue() != null) {
					eventCopy.setValue(EcoreUtil.copy(event.getValue()));
				}
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventOutputs().add(eventCopy);
			}
			copy.setInterface(interfaceList);
			copy.setX(element.getX());
			copy.setY(element.getY());
		}
		for (EventConnection eventCon : type.getFBNetwork().getEventConnections()) {
			if (eventCon.getSource() != null && eventCon.getDestination() != null) {
				FB sourceFB = (FB) eventCon.getSource().eContainer().eContainer();
				FB destFB = (FB) eventCon.getDestination().eContainer().eContainer();
				Event source = events.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getSource().getName());
				Event dest = events.get(destFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getDestination().getName());
				EventConnection copyEventCon = LibraryElementFactory.eINSTANCE.createEventConnection();
				copyEventCon.setSource(source);
				copyEventCon.setDestination(dest);
				copyEventCon.setResTypeConnection(true);
				resourceFBNetwork.getEventConnections().add(copyEventCon); // TODO check this
			} else {
				// TODO error log -> no valid event connection!
			}
		}
		for (DataConnection dataCon : type.getFBNetwork().getDataConnections()) {
			if (dataCon.getSource() != null && dataCon.getDestination() != null) {
				FB sourceFB = (FB) dataCon.getSource().eContainer().eContainer();
				FB destFB = (FB) dataCon.getDestination().eContainer().eContainer();	
				if(null != sourceFB){
					VarDeclaration source = varDecls.get(sourceFB.getName() + "." //$NON-NLS-1$
							+ dataCon.getSource().getName());
					VarDeclaration dest = varDecls.get(destFB.getName() + "." //$NON-NLS-1$
							+ dataCon.getDestination().getName());
					DataConnection copyDataCon = LibraryElementFactory.eINSTANCE.createDataConnection();					
					copyDataCon.setSource(source);
					copyDataCon.setDestination(dest);
					copyDataCon.setResTypeConnection(true);
					resourceFBNetwork.getDataConnections().add(copyDataCon);
				}else{	
					VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();
					var.setName(dataCon.getSource().getName());
					var.setIsInput(true);
					Value value = LibraryElementFactory.eINSTANCE.createValue();
					value.setValue(dataCon.getDataSource().getVarInitialization().getInitialValue());
					var.setValue(value);
					device.getVarDeclarations().add(var);
				}
			} else {
				// TODO error log -> no valid data connection
			}
		}
	}

	@Override
	public void undo() {
		device.getResource().remove(resource);
		SystemManager.INSTANCE.notifyListeners();
	}

	@Override
	public void redo() {
		device.getResource().add(index, resource);
		SystemManager.INSTANCE.notifyListeners();
	}

	public Resource getResource() {
		return resource;
	}
}
