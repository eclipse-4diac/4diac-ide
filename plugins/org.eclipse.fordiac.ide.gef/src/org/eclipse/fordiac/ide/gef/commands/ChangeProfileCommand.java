/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.gef.commands.Command;

public class ChangeProfileCommand extends Command {
	private Device device;
	private String profileOld;
	private String profile;
	
	public ChangeProfileCommand(Device device, String profile){
		this.device = device;
		this.profile = profile;
	}
	
	@Override
	public boolean canExecute() {
		return null != profile && null != device;
	}
	
	@Override
	public void execute() {
		profileOld = device.getProfile();
		redo();
	}
	
	@Override
	public void redo() {
		device.setProfile(profile);
	}
	
	@Override
	public void undo() {
		device.setProfile(profileOld);
	}
}
