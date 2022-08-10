/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed issues in adapter update
 *               - moved adapter type handling to own adapter command
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public class ChangeDataTypeCommand extends Command {
	private final IInterfaceElement interfaceElement;
	private final DataType dataType;
	private DataType oldDataType;


	public ChangeDataTypeCommand(final IInterfaceElement interfaceElement, final DataType dataType) {
		super();
		this.interfaceElement = interfaceElement;
		this.dataType = dataType;
	}

	@Override
	public void execute() {
		oldDataType = interfaceElement.getType();
		setNewType();
	}


	@Override
	public void undo() {
		interfaceElement.setType(oldDataType);
		interfaceElement.setTypeName(oldDataType.getName());

	}

	@Override
	public void redo() {
		setNewType();
	}

	private void setNewType() {
		interfaceElement.setType(dataType);
		interfaceElement.setTypeName(dataType.getName());
	}

	protected IInterfaceElement getInterfaceElement() {
		return interfaceElement;
	}
}
