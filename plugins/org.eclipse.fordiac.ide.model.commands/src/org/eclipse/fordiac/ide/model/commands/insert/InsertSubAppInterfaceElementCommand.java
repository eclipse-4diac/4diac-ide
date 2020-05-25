/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.insert;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class InsertSubAppInterfaceElementCommand extends InsertInterfaceElementCommand {

	private InsertInterfaceElementCommand mirroredElement = null;

	public InsertSubAppInterfaceElementCommand(IInterfaceElement entry, DataType dataType, InterfaceList interfaceList,
			boolean isInput, int index) {
		super(entry, dataType, interfaceList, isInput, index);
	}

	public InsertInterfaceElementCommand getMirroredElement() {
		return mirroredElement;
	}

	@Override
	public void execute() {
		super.execute();
		if (getInterfaceList().getFBNetworkElement().isMapped()) {
			// the subapp is mapped so we need to created the interface element also in the
			// opposite entry
			mirroredElement = new InsertInterfaceElementCommand(getEntry(), getDataType(),
					getInterfaceList().getFBNetworkElement().getOpposite().getInterface(), isInput(), getIndex());
			mirroredElement.execute();
			// Set the same name as the one we have also on the mirrored
			mirroredElement.getInterfaceElement().setName(getInterfaceElement().getName());
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
