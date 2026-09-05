/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Keppler University Linz
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

import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

public class StructDataConnectionCreateCommand extends DataConnectionCreateCommand {
	private ConfigureFBCommand changeStructCommand;

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

		if (shouldChangeStruct(source, target)) {
			if (isUnconfiguredStructManipulatorDefPin(source)
					&& target.getType() instanceof final StructuredType targetVar) {
				changeStructCommand = new ConfigureFBCommand((ConfigurableFB) source.getBlockFBNetworkElement(),
						targetVar);
				changeStructCommand.execute();
				setSource(changeStructCommand.getNewElement().getInterface().getInterfaceElement(getSource()));
			} else if (isUnconfiguredStructManipulatorDefPin(target)
					&& source.getType() instanceof final StructuredType sourceVar) {
				changeStructCommand = new ConfigureFBCommand((ConfigurableFB) target.getBlockFBNetworkElement(),
						sourceVar);
				changeStructCommand.execute();
				setDestination(
						changeStructCommand.getNewElement().getInterface().getInterfaceElement(getDestination()));
			}
		}
		super.execute();
	}

	private static boolean shouldChangeStruct(final IInterfaceElement source, final IInterfaceElement target) {
		final var sourceType = source.getType();
		final var targetType = target.getType();
		final var sourceParent = source.eContainer().eContainer();
		final var targetParent = target.eContainer().eContainer();

		if (sourceType instanceof final StructuredType sourceVar && targetType instanceof final StructuredType targetVar
				&& !sourceVar.getName().equals(targetVar.getName())) {
			return true;
		}

		if (sourceType instanceof StructuredType && targetParent instanceof ConfigurableMoveFB
				&& !source.getName().equals(target.getName())) {
			return true;
		}

		return (targetType instanceof StructuredType && sourceParent instanceof ConfigurableMoveFB
				&& !target.getName().equals(source.getName()));
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
				&& ((varDecl.getType() instanceof StructuredType)
						|| pin.eContainer().eContainer() instanceof ConfigurableMoveFB);
	}

	private static boolean isUnconfiguredStructManipulatorDefPin(final IInterfaceElement pin) {
		return AbstractConnectionCreateCommand.isStructManipulatorDefPin(pin)
				&& (GenericTypes.ANY_STRUCT == pin.getType() || GenericTypes.ANY == pin.getType());
	}

}
