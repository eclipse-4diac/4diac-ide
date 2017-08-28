/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeTypeCommand extends Command {
	private final VarDeclaration interfaceElement;
	private DataType dataType;
	private DataType oldDataType;

	public ChangeTypeCommand(final VarDeclaration interfaceElement,
			final DataType dataType) {
		super();
		this.interfaceElement = interfaceElement;
		this.dataType = dataType;
	}

	@Override
	public void execute() {
		oldDataType = interfaceElement.getType();
		redo();
	}

	@Override
	public void undo() {
		interfaceElement.setType(oldDataType);
		interfaceElement.setTypeName(oldDataType.getName());

	}

	@Override
	public void redo() {
		interfaceElement.setType(dataType);
		interfaceElement.setTypeName(dataType.getName());
	}
}
