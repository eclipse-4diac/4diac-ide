/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	ChangeTypeCommand mirroredElement = null;

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
