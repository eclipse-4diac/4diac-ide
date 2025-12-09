/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, TU Wien ACIN fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeviceDeleteCommand extends Command {
	private final Device device;
	private SystemConfiguration deviceParent;
	private CompoundCommand commands;

	public DeviceDeleteCommand(final Device device) {
		this.device = device;
	}

	@Override
	public void execute() {
		deviceParent = (SystemConfiguration) device.eContainer();
		commands = new CompoundCommand();
		for (final Resource resource : device.getResource()) {
			commands.add(new ResourceDeleteCommand(resource));
		}
		for (final Link link : device.getInConnections()) {
			commands.add(new DeleteLinkCommand(link));
		}
		commands.execute();
		deviceParent.getDevices().remove(device);
	}

	@Override
	public void undo() {
		deviceParent.getDevices().add(device);
		commands.undo();
	}

	@Override
	public void redo() {
		commands.redo();
		deviceParent.getDevices().remove(device);
	}
}
