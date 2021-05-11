/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
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
import org.eclipse.gef.commands.Command;

public class ResourceCreateCommand extends Command {
	private final ResourceTypeEntry entry;
	private Resource resource;
	private final Device device;
	private final boolean deviceTypeRes;
	private int index = -1;

	public ResourceCreateCommand(final ResourceTypeEntry entry, final Device device, final boolean deviceTypeRes) {
		this.entry = entry;
		this.device = device;
		this.deviceTypeRes = deviceTypeRes;
	}

	public ResourceCreateCommand(final ResourceTypeEntry entry, final Device device, final int index, final boolean deviceTypeRes) {
		this.entry = entry;
		this.device = device;
		this.index = index;
		this.deviceTypeRes = deviceTypeRes;
	}

	@Override
	public void execute() {
		resource = LibraryElementFactory.eINSTANCE.createResource();
		resource.setDeviceTypeResource(deviceTypeRes);
		resource.setPaletteEntry(entry);
		resource.getVarDeclarations().addAll(EcoreUtil.copyAll(entry.getResourceType().getVarDeclaration()));
		for (final VarDeclaration element : resource.getVarDeclarations()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			element.setValue(value);
		}
		final FBNetwork resourceFBNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		if (entry.getResourceType().getFBNetwork() != null) {
			resource.setFBNetwork(resourceFBNetwork);
			createResourceTypeNetwork(resource, entry.getResourceType(), resourceFBNetwork);
		} else {
			resource.setFBNetwork(resourceFBNetwork);
		}
		if (index < 0 || index > device.getResource().size()) {
			index = device.getResource().size();
		}
		device.getResource().add(index, resource);
		// resource name needs to be added after it is inserted in the device so that
		// name checking works
		resource.setName(NameRepository.createUniqueName(resource, entry.getLabel()));
		SystemManager.INSTANCE.notifyListeners();
	}

	// TODO model refactoring - Can this also be used for the resoruce parsing?
	public void createResourceTypeNetwork(final Resource resource, final ResourceType type,
			final FBNetwork resourceFBNetwork) {
		final Map<String, Event> events = new HashMap<>();
		final Map<String, VarDeclaration> varDecls = new HashMap<>();
		for (final FBNetworkElement element : type.getFBNetwork().getNetworkElements()) {
			final FB copy = LibraryElementFactory.eINSTANCE.createResourceTypeFB();
			resource.getFBNetwork().getNetworkElements().add(copy);
			copy.setPaletteEntry(element.getPaletteEntry());
			copy.setName(element.getName()); // name should be last so that checks
			// are working correctly
			final InterfaceList interfaceList = LibraryElementFactory.eINSTANCE.createInterfaceList();
			for (final VarDeclaration varDecl : element.getInterface().getOutputVars()) {
				final VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
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
			for (final VarDeclaration varDecl : element.getInterface().getInputVars()) {
				final VarDeclaration varDeclCopy = LibraryElementFactory.eINSTANCE.createVarDeclaration();
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
			for (final Event event : element.getInterface().getEventInputs()) {
				final Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventInputs().add(eventCopy);
			}
			for (final Event event : element.getInterface().getEventOutputs()) {
				final Event eventCopy = LibraryElementFactory.eINSTANCE.createEvent();
				eventCopy.setName(event.getName());
				eventCopy.setComment(event.getComment());
				eventCopy.setIsInput(event.isIsInput());
				events.put(element.getName() + "." + event.getName(), eventCopy); //$NON-NLS-1$
				interfaceList.getEventOutputs().add(eventCopy);
			}
			copy.setInterface(interfaceList);
			copy.setPosition(EcoreUtil.copy(element.getPosition()));
		}
		for (final EventConnection eventCon : type.getFBNetwork().getEventConnections()) {
			if (eventCon.getSource() != null && eventCon.getDestination() != null) {
				final FB sourceFB = (FB) eventCon.getSource().eContainer().eContainer();
				final FB destFB = (FB) eventCon.getDestination().eContainer().eContainer();
				final Event source = events.get(sourceFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getSource().getName());
				final Event dest = events.get(destFB.getName() + "." //$NON-NLS-1$
						+ eventCon.getDestination().getName());
				final EventConnection copyEventCon = LibraryElementFactory.eINSTANCE.createEventConnection();
				copyEventCon.setSource(source);
				copyEventCon.setDestination(dest);
				copyEventCon.setResTypeConnection(true);
				resourceFBNetwork.getEventConnections().add(copyEventCon); // TODO check this
			} else {
				// TODO error log -> no valid event connection!
			}
		}
		for (final DataConnection dataCon : type.getFBNetwork().getDataConnections()) {
			if (dataCon.getSource() != null && dataCon.getDestination() != null) {
				final FB sourceFB = (FB) dataCon.getSource().eContainer().eContainer();
				final FB destFB = (FB) dataCon.getDestination().eContainer().eContainer();
				if (null != sourceFB) {
					final VarDeclaration source = varDecls.get(sourceFB.getName() + "." //$NON-NLS-1$
							+ dataCon.getSource().getName());
					final VarDeclaration dest = varDecls.get(destFB.getName() + "." //$NON-NLS-1$
							+ dataCon.getDestination().getName());
					final DataConnection copyDataCon = LibraryElementFactory.eINSTANCE.createDataConnection();
					copyDataCon.setSource(source);
					copyDataCon.setDestination(dest);
					copyDataCon.setResTypeConnection(true);
					resourceFBNetwork.getDataConnections().add(copyDataCon);
				} else {
					final VarDeclaration devVar = LibraryElementFactory.eINSTANCE.createVarDeclaration();
					devVar.setName(dataCon.getSource().getName());
					devVar.setIsInput(true);
					final Value value = LibraryElementFactory.eINSTANCE.createValue();
					value.setValue(dataCon.getDataSource().getValue().getValue());
					devVar.setValue(value);
					device.getVarDeclarations().add(devVar);
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
