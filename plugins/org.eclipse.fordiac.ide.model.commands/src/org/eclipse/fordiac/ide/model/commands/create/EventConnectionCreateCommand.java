/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
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

	public EventConnectionCreateCommand(FBNetwork parent){
		super(parent);
	}

	@Override
	protected Connection createConnectionElement() {
		return LibraryElementFactory.eINSTANCE.createEventConnection();
	}

	@Override
	public boolean canExecute() {
		if (source == null || destination == null) {
			return false;
		}
		if(source == destination){
			return false;
		}
		if (!(source instanceof Event)) {
			return false;
		}
		if (!(destination instanceof Event)) {
			return false;
		}
		
		if(duplicateConnection()){
			return false;
		}
		
		return LinkConstraints.canExistEventConnection((Event) source, (Event)destination);
	}
	
	private boolean duplicateConnection() {
		for (Connection con : source.getInputConnections()) {
			//as we are maybe creating a reverse connection we need to check both 
			if((con.getSource() == destination) || (con.getDestination() == destination)){
				return true;
			}
		}
		for (Connection con : source.getOutputConnections()) {
			//as we are maybe creating a reverse connection we need to check both
			if((con.getSource() == destination) || (con.getDestination() == destination)){
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork) {
		return new EventConnectionCreateCommand(fbNetwork);
	}

}
