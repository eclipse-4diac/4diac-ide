/*******************************************************************************
 * Copyright (c) 2023 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

public class StructDataConnectionCreateCommand extends DataConnectionCreateCommand {
	private ChangeStructCommand changeStructCommand;

	public StructDataConnectionCreateCommand(final FBNetwork parent) {
		super(parent);
	}

	@Override
	protected boolean canExecuteConType() {
		final IInterfaceElement source = getSource();
		final IInterfaceElement target = getDestination();
		if (isStructPin(source) && isStructPin(target)) {
			return LinkConstraints.canExistDataConnection(getSource(), getDestination(), getParent(), null);
		}
		return false;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public void execute() {
		final IInterfaceElement source = getSource();
		final IInterfaceElement target = getDestination();

		if (source.getType() instanceof final StructuredType sourceVar
				&& target.getType() instanceof final StructuredType targetVar
				&& !sourceVar.getName().equals(targetVar.getName())) {

			if (isUnconfiguredStructManipulatorDefPin(source)) {
				changeStructCommand = new ChangeStructCommand((StructManipulator) source.getFBNetworkElement(),
						targetVar);
				changeStructCommand.execute();
				setSource(changeStructCommand.getNewMux().getInterfaceElement(getSource().getName()));
			} else if (isUnconfiguredStructManipulatorDefPin(target)) {
				changeStructCommand = new ChangeStructCommand((StructManipulator) target.getFBNetworkElement(),
						sourceVar);
				changeStructCommand.execute();
				setDestination(changeStructCommand.getNewMux().getInterfaceElement(getDestination().getName()));
			}
		}
		super.execute();
	}

	@Override
	public void undo() {
		super.undo();
		if (changeStructCommand != null) {
			changeStructCommand.undo();
		}
	}

	@Override
	public void redo() {
		if (changeStructCommand != null) {
			changeStructCommand.redo();
		}
		super.redo();
	}

	private static boolean isStructPin(final IInterfaceElement pin) {
		return pin instanceof final VarDeclaration varDecl && !varDecl.isArray()
				&& varDecl.getType() instanceof StructuredType;
	}

	private static boolean isUnconfiguredStructManipulatorDefPin(final IInterfaceElement pin) {
		return AbstractConnectionCreateCommand.isStructManipulatorDefPin(pin)
				&& GenericTypes.ANY_STRUCT == pin.getType();
	}

}
