/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.gef.commands.Command;

public class ChangeCommunicationConfigurationCommand extends Command {

	private CommunicationConfiguration oldConfig;
	private final CommunicationConfiguration newConfig;
	private final Segment type;

	/** changes the communication configuration of a segment to the provided detail implementation
	 *
	 * @param newConfig the communication configuration that should be set
	 * @param segment   the segment to be configured */
	public ChangeCommunicationConfigurationCommand(final CommunicationConfiguration newConfig, final Segment segment) {
		this.newConfig = newConfig;
		this.type = segment;
	}

	/** deletes a communication configuration of a segment
	 *
	 * @param segment the segment that should not be configured */
	public ChangeCommunicationConfigurationCommand(final Segment segment) {
		this(null, segment);
	}

	@Override
	public boolean canExecute() {
		return type != null;
	}

	@Override
	public void execute() {
		this.oldConfig = type.getCommunication();
		type.setCommunication(newConfig);
	}

	@Override
	public void undo() {
		type.setCommunication(oldConfig);
	}

	@Override
	public void redo() {
		type.setCommunication(newConfig);
	}
}
