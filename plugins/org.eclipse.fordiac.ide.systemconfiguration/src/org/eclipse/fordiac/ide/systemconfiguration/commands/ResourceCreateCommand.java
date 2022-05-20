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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
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
		resource.setTypeEntry(entry);
		createResourceInputs();
		resource.setFBNetwork(createResourceFBNetwork());

		if (index < 0 || index > device.getResource().size()) {
			index = device.getResource().size();
		}
		device.getResource().add(index, resource);
		// resource name needs to be added after it is inserted in the device so that
		// name checking works
		resource.setName(NameRepository.createUniqueName(resource, entry.getTypeName()));
		SystemManager.INSTANCE.notifyListeners();
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

	private void createResourceInputs() {
		resource.getVarDeclarations().addAll(EcoreUtil.copyAll(entry.getType().getVarDeclaration()));
		for (final VarDeclaration element : resource.getVarDeclarations()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			element.setValue(value);
		}
	}

	private FBNetwork createResourceFBNetwork() {
		FBNetwork resourceFBNetwork = null;
		if (entry.getType().getFBNetwork() != null) {
			// create a dummy interface list so that we can use the copyFBNetwork method
			final InterfaceList il = LibraryElementFactory.eINSTANCE.createInterfaceList();
			il.getInputVars().addAll(resource.getVarDeclarations());
			resourceFBNetwork = FBNetworkHelper.createResourceFBNetwork(entry.getType().getFBNetwork(), il);
			resource.getVarDeclarations().addAll(il.getInputVars());  // ensure that the data inputs are back with us.
		} else {
			resourceFBNetwork = LibraryElementFactory.eINSTANCE.createFBNetwork();
		}
		return resourceFBNetwork;
	}

	public Resource getResource() {
		return resource;
	}
}
