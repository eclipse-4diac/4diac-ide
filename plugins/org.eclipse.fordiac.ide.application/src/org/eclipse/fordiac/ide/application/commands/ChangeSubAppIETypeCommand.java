/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeSubAppIETypeCommand extends ChangeTypeCommand {
	private ChangeTypeCommand mirroredElement = null;

	public ChangeSubAppIETypeCommand(VarDeclaration interfaceElement, DataType dataType) {
		super(interfaceElement, dataType);

		if (interfaceElement.getFBNetworkElement().isMapped()) {
			mirroredElement = new ChangeTypeCommand((VarDeclaration) interfaceElement.getFBNetworkElement()
					.getOpposite().getInterfaceElement(interfaceElement.getName()), dataType);
		}
	}

	@Override
	public void execute() {
		super.execute();
		if (null != mirroredElement) {
			mirroredElement.execute();
		}
	}

	@Override
	public void redo() {
		super.redo();
		if (null != mirroredElement) {
			mirroredElement.redo();
		}
	}

	@Override
	public void undo() {
		super.undo();
		if (null != mirroredElement) {
			mirroredElement.undo();
		}
	}
}
