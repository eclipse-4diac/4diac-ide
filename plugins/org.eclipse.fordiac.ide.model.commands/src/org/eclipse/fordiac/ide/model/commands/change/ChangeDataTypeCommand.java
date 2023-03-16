/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *               2023 Martin Erich Jobst
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
 *   Martin Jobst - add value validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeDataTypeCommand extends AbstractChangeInterfaceElementCommand {
	private final IInterfaceElement oldElement;
	private final DataType dataType;
	private DataType oldDataType;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	public ChangeDataTypeCommand(final IInterfaceElement interfaceElement, final DataType dataType) {
		super(interfaceElement);
		this.dataType = dataType;
		this.oldElement = interfaceElement;
	}

	@Override
	protected void doExecute() {
		oldDataType = getInterfaceElement().getType();
		setNewType();
		if (oldDataType instanceof ErrorMarkerDataType) {
			getErrorMarkerUpdateCmds().add(
					FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(oldElement)));
		}
		additionalCommands.execute();
	}


	@Override
	protected void doUndo() {
		additionalCommands.undo();
		getInterfaceElement().setType(oldDataType);
		getInterfaceElement().setTypeName(oldDataType.getName());
	}

	@Override
	protected void doRedo() {
		setNewType();
		additionalCommands.redo();
	}

	private void setNewType() {
		getInterfaceElement().setType(dataType);
		getInterfaceElement().setTypeName(dataType.getName());
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}
}
