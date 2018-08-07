/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GbmH, fortiss GmbH, 
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.gef.commands.Command;

public class LinkCreateCommand extends Command {
	private boolean segmentDeviceLink;
	private SystemConfiguration systemConfiguration;
	private Segment source;
	private Device destination;
	private Link link;

	public void setSystemConfigurationNetwork(final SystemConfiguration systemConfigurationNetwork) {
		this.systemConfiguration = systemConfigurationNetwork;
	}

	public void setSource(final Segment source) {
		this.source = source;
	}

	public void setDestination(final Device destination) {
		this.destination = destination;
	}

	@Override
	public boolean canExecute() {
		return source != null && destination != null;
	}

	@Override
	public void execute() {
		link = LibraryElementFactory.eINSTANCE.createLink();
		redo();
	}

	@Override
	public void undo() {
		source.getOutConnections().remove(link);
		destination.getInConnections().remove(link);
		systemConfiguration.getLinks().remove(link);
	}

	@Override
	public void redo() {
		source.getOutConnections().add(link);
		destination.getInConnections().add(link);
		systemConfiguration.getLinks().add(link);
	}

	public boolean isSegmentDeviceLink() {
		return segmentDeviceLink;
	}

	public void setSegmentDeviceLink(final boolean segmentDeviceLink) {
		this.segmentDeviceLink = segmentDeviceLink;
	}

	public Link getLink() {
		return link;
	}
}