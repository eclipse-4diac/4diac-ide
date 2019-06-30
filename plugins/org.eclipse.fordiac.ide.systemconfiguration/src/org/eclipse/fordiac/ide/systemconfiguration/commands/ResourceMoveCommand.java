/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IEditorPart;

public class ResourceMoveCommand extends Command {
	private Resource resource;
	private Device device;
	private Device targetDevice;
	private int indexNew;
	private int indexOld;

	public ResourceMoveCommand(Resource resource, Device targetDevice, int indexNew) {
		this.resource = resource;
		this.indexNew = indexNew;
		this.targetDevice = targetDevice;
	}
	
	@Override
	public void execute() {
		device = resource.getDevice();
		indexOld = device.getResource().indexOf(resource);
		redo();
	}

	@Override
	public void undo() {
		if(device == targetDevice) {
			device.getResource().move(indexOld, resource);
		}else {
			targetDevice.getResource().remove(resource);
			device.getResource().add(indexOld, resource);
		}
		SystemManager.INSTANCE.notifyListeners();
	}

	@Override
	public void redo() {
		if(device == targetDevice) {
			if(indexNew >= device.getResource().size()) {
				device.getResource().move(device.getResource().size() - 1, resource);
			}else {
				device.getResource().move(indexNew, resource);
			}
		}else {
			device.getResource().remove(resource);
			targetDevice.getResource().add(indexNew, resource);
		}
		SystemManager.INSTANCE.notifyListeners();
	}
}
