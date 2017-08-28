/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.gef.commands.Command;

public class DeleteLinkCommand extends Command {
	private Link link;
	private Segment source; 
	private Device destination;
	private SystemConfiguration sysConf;

	public DeleteLinkCommand(Link link) {
		this.link = link;
	}

	@Override
	public boolean canExecute() {
		return null != link;
	}

	@Override
	public void execute() {
		source = link.getSegment();
		destination = link.getDevice();		
		sysConf = (SystemConfiguration) link.eContainer();
		redo();
	}

	@Override
	public void undo() {
		sysConf.getLinks().add(link);
		source.getOutConnections().add(link);
		destination.getInConnections().add(link);
	}

	@Override
	public void redo() {
		source.getOutConnections().remove(link);
		destination.getInConnections().remove(link);
		sysConf.getLinks().remove(link);
	}
}
