/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class DeleteSubAppInterfaceElementCommand extends DeleteInterfaceCommand {

	private DeleteInterfaceCommand opposite = null;

	public DeleteSubAppInterfaceElementCommand(final IInterfaceElement interfaceElement) {
		super(interfaceElement);
		if (interfaceElement.getFBNetworkElement().isMapped()) {
			final FBNetworkElement fbE = interfaceElement.getFBNetworkElement().getOpposite();
			final IInterfaceElement element = fbE.getInterfaceElement(interfaceElement.getName());
			if (null != element) {
				opposite = new DeleteInterfaceCommand(element);
			}
		}
	}

	@Override
	public void execute() {
		super.execute();
		if (null != opposite) {
			opposite.execute();
		}
	}

	@Override
	public void undo() {
		super.undo();
		if (null != opposite) {
			opposite.undo();
		}
	}

	@Override
	public void redo() {
		super.redo();
		if (null != opposite) {
			opposite.redo();
		}
	}

	public DeleteInterfaceCommand getOpposite() {
		return opposite;
	}

}
