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

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;

public class StructDataConnectionCreateCommand extends DataConnectionCreateCommand {
	private ChangeStructCommand changeStructCommand;

	public StructDataConnectionCreateCommand(final FBNetwork parent) {
		super(parent);
	}

	@Override
	protected boolean canExecuteConType() {
		if (isStructPin(getSource()) && isStructPin(getDestination())) {
			// only when source and destination are structs this command shall be exectuable
			return canExistDataConnection(getSource(), getDestination(), getParent(), null);

		}
		return false;
	}

	@Override
	public void execute() {
		final IInterfaceElement source = getSource();
		final IInterfaceElement target = getDestination();

		if (source.getType() instanceof final StructuredType sourceVar
				&& target.getType() instanceof final StructuredType targetVar
				&& !sourceVar.getName().equals(targetVar.getName())) {

			if (AbstractConnectionCreateCommand.isStructManipulatorDefPin(source)) {
				changeStructCommand = new ChangeStructCommand((StructManipulator) source.getFBNetworkElement(),
						targetVar);
				changeStructCommand.execute();
				setSource(changeStructCommand.getNewMux().getInterfaceElement(getSource().getName()));
			} else {
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

	// a subset of the same function in the LinkConstraints
	private static boolean canExistDataConnection(IInterfaceElement source, IInterfaceElement target,
			final FBNetwork parent, final Connection con) {

		if (!LinkConstraints.isDataPin(source) || !LinkConstraints.isDataPin(target)) {
			ErrorMessenger.popUpErrorMessage(Messages.ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		if (LinkConstraints.isSwapNeeded(source, parent)) {
			final IInterfaceElement temp = source;
			source = target;
			target = temp;
		}

		if (!LinkConstraints.sourceAndDestCheck(source, target, parent)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (!LinkConstraints.hasAlreadyInputConnectionsCheck(source, target, con)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
			return false;
		}

		return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(target);
	}

	private static boolean isStructPin(final IInterfaceElement pin) {
		return pin instanceof final VarDeclaration varDecl && !varDecl.isArray()
				&& varDecl.getType() instanceof StructuredType;
	}

}
