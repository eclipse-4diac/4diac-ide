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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The Class DataConnectionCreateCommand.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public class DataConnectionCreateCommand extends AbstractConnectionCreateCommand {

	public DataConnectionCreateCommand(FBNetwork parent) {
		super(parent);
	}

	@Override
	protected Connection createConnectionElement(){
		return LibraryElementFactory.eINSTANCE.createDataConnection();
	}
	
	@Override
	public boolean canExecute() {
		if (source == null || destination == null) {
			return false;
		}
		if (!(source instanceof VarDeclaration)) {
			return false;
		}
		if (!(destination instanceof VarDeclaration)) {
			return false;
		}

		return LinkConstraints.canCreateDataConnection((VarDeclaration) source, (VarDeclaration) destination);
	}

	@Override
	protected AbstractConnectionCreateCommand createMirroredConnectionCommand(FBNetwork fbNetwork) {
		return new DataConnectionCreateCommand(fbNetwork);
	}

}
