/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked and harmonized source/target checking 551042
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

/**
 * The Class EventConnectionCreateCommand.
 *
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class EventConnectionCreateCommand extends AbstractConnectionCreateCommand {

	public EventConnectionCreateCommand(FBNetwork parent) {
		super(parent);
	}

	@Override
	protected Connection createConnectionElement() {
		return LibraryElementFactory.eINSTANCE.createEventConnection();
	}

	@Override
	public boolean canExecute() {
		if (!super.canExecute()) {
			return false;
		}

		if (duplicateConnection()) {
			return false;
		}

		return LinkConstraints.canExistEventConnection((Event) getSource(), (Event) getDestination());
	}

	private boolean duplicateConnection() {
		for (Connection con : getSource().getInputConnections()) {
			// as we are maybe creating a reverse connection we need to check both
			if ((con.getSource() == getDestination()) || (con.getDestination() == getDestination())) {
				return true;
			}
		}
		for (Connection con : getSource().getOutputConnections()) {
			// as we are maybe creating a reverse connection we need to check both
			if ((con.getSource() == getDestination()) || (con.getDestination() == getDestination())) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork) {
		return new EventConnectionCreateCommand(fbNetwork);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getInterfaceType() {
		return Event.class;
	}

}
