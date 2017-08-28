/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, TU Wien ACIN fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.controls.Abstract4DIACUIPlugin;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;

public class DeviceDeleteCommand extends Command {
	private IEditorPart editor;
	private Device device;
	private SystemConfiguration deviceParent;
	private CompoundCommand commands;

	public DeviceDeleteCommand(final Device device) {
		this.device = device;
		editor = Abstract4DIACUIPlugin.getCurrentActiveEditor();
	}

	@Override
	public boolean canUndo() {
		return editor.equals(Abstract4DIACUIPlugin.getCurrentActiveEditor());
	}

	@Override
	public void execute() {
		deviceParent = (SystemConfiguration) device.eContainer();
		commands = new CompoundCommand();
		for(Resource resource : device.getResource()){
			commands.add(new ResourceDeleteCommand(resource));
		}
		for(Link link : device.getInConnections()) {
			commands.add(new DeleteLinkCommand(link));
		}
		redo();
	}

	@Override
	public void undo() {
		deviceParent.getDevices().add(device);
		commands.undo();
		SystemManager.INSTANCE.notifyListeners();
	}

	@Override
	public void redo() {
		commands.execute();
		deviceParent.getDevices().remove(device);
		SystemManager.INSTANCE.notifyListeners();
	}
}
