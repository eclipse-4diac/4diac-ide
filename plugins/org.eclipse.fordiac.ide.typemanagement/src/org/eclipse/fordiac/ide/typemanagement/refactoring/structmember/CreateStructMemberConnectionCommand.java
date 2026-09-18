/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangePinVisibilityCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;

final class CreateStructMemberConnectionCommand extends Command implements ScopedCommand {
	private final LibraryElement targetModel;
	private final AddStructMemberContext context;
	private final String memberName;

	private Command connectionCommand;
	private ChangePinVisibilityCommand visibilityCommand;
	private ContainerVarDeclaration cachedMemberParent;
	private VarDeclaration cachedMember;

	CreateStructMemberConnectionCommand(final LibraryElement targetModel, final AddStructMemberContext context,
			final String memberName) {
		this.targetModel = targetModel;
		this.context = context;
		this.memberName = memberName;
	}

	@Override
	public boolean canExecute() {
		if (!context.matches(targetModel, true)) {
			return false;
		}
		final VarDeclaration connectionPin = context.resolveConnectionPin(targetModel, false);
		final StructuredType structType = resolveStructType();
		if (connectionPin == null || structType == null || structType.getMemberVar(memberName) == null
				|| !LinkConstraints.isWithConstraintOK(connectionPin)) {
			return false;
		}

		return switch (context.getTargetKind()) {
		case STRUCT_PIN -> canConnectToStructPin(connectionPin);
		case MULTIPLEXER, DEMULTIPLEXER -> canConnectToManipulator(connectionPin);
		};
	}

	private boolean canConnectToStructPin(final VarDeclaration connectionPin) {
		final VarDeclaration structPin = context.resolveStructPin(targetModel, false);
		if (!(structPin instanceof final ContainerVarDeclaration) || !LinkConstraints.isWithConstraintOK(structPin)
				|| connectionPin.getBlockFBNetworkElement() == structPin.getBlockFBNetworkElement()) {
			return false;
		}
		return hasAvailableDestination(connectionPin, structPin);
	}

	private boolean canConnectToManipulator(final VarDeclaration connectionPin) {
		final StructManipulator manipulator = context.resolveStructManipulator(targetModel);
		final VarDeclaration memberPin = resolveManipulatorMemberPin(manipulator);
		if (manipulator == null || memberPin == null || connectionPin.getBlockFBNetworkElement() == manipulator
				|| !hasAvailableDestination(connectionPin, memberPin)) {
			return false;
		}
		final Command command = createConnectionCommand(connectionPin, memberPin);
		return command != null && command.canExecute();
	}

	private static boolean hasAvailableDestination(final VarDeclaration connectionPin,
			final VarDeclaration memberPin) {
		final AddStructMemberContext.ConnectionEndpoints endpoints = AddStructMemberContext
				.getDirectConnectionEndpoints(connectionPin, memberPin);
		return endpoints == null || endpoints.destination().getInputConnections().isEmpty();
	}

	@Override
	public void execute() {
		clearExecutionState();
		final VarDeclaration connectionPin = context.resolveConnectionPin(targetModel, true);
		final StructuredType structType = resolveStructType();
		if (connectionPin == null || structType == null) {
			throw new IllegalStateException(Messages.AddStructMemberRefactoring_InvalidContext);
		}

		final VarDeclaration memberPin = switch (context.getTargetKind()) {
		case STRUCT_PIN -> prepareStructMemberPin();
		case MULTIPLEXER, DEMULTIPLEXER -> resolveManipulatorMemberPin(
				context.resolveStructManipulator(targetModel));
		};
		if (memberPin == null) {
			rollbackPreparation();
			throw new IllegalStateException(Messages.AddStructMemberRefactoring_CannotConnect);
		}

		connectionCommand = createConnectionCommand(connectionPin, memberPin);
		if (connectionCommand == null || !connectionCommand.canExecute()) {
			rollbackPreparation();
			throw new IllegalStateException(Messages.AddStructMemberRefactoring_CannotConnect);
		}
		connectionCommand.execute();
	}

	private VarDeclaration prepareStructMemberPin() {
		if (!(context.resolveStructPin(targetModel, false) instanceof final ContainerVarDeclaration structPin)) {
			return null;
		}
		cachedMemberParent = structPin;
		cachedMember = structPin.getCachedMember(List.of(memberName), false);
		final boolean newCachedMember = cachedMember == null;
		if (newCachedMember) {
			cachedMember = structPin.getCachedMember(List.of(memberName), true);
		}
		if (cachedMember != null && !cachedMember.isVisible()) {
			visibilityCommand = new ChangePinVisibilityCommand(cachedMember, true);
			visibilityCommand.execute();
		}
		if (!newCachedMember) {
			cachedMemberParent = null;
		}
		return cachedMember;
	}

	private VarDeclaration resolveManipulatorMemberPin(final StructManipulator manipulator) {
		return manipulator != null
				&& manipulator.getInterface().getInterfaceElement(List.of(memberName)) instanceof final VarDeclaration pin
				? pin
				: null;
	}

	private Command createConnectionCommand(final VarDeclaration connectionPin, final VarDeclaration memberPin) {
		final AddStructMemberContext.ConnectionEndpoints endpoints = AddStructMemberContext
				.getDirectConnectionEndpoints(connectionPin, memberPin);
		if (endpoints != null) {
			final AbstractConnectionCreateCommand command = AbstractConnectionCreateCommand.createCommand(
					endpoints.network(), endpoints.source(), endpoints.destination());
			command.setSource(endpoints.source());
			command.setDestination(endpoints.destination());
			return command;
		}
		final IInterfaceElement source = memberPin.isIsInput() ? connectionPin : memberPin;
		final IInterfaceElement destination = memberPin.isIsInput() ? memberPin : connectionPin;
		return context.createBorderCrossingConnectionCommand(source, destination);
	}

	private StructuredType resolveStructType() {
		return StructMemberRefactoringSupport.resolveDataType(targetModel.getTypeLibrary(),
				context.getStructTypeName()) instanceof final StructuredType type ? type : null;
	}

	private void rollbackPreparation() {
		if (visibilityCommand != null) {
			visibilityCommand.undo();
			visibilityCommand = null;
		}
		removeNewCachedMember();
	}

	@Override
	public boolean canUndo() {
		return connectionCommand != null && connectionCommand.canUndo();
	}

	@Override
	public void undo() {
		connectionCommand.undo();
		if (visibilityCommand != null) {
			visibilityCommand.undo();
		}
		removeNewCachedMember();
	}

	@Override
	public boolean canRedo() {
		return connectionCommand != null;
	}

	@Override
	public void redo() {
		execute();
	}

	private void removeNewCachedMember() {
		if (cachedMemberParent != null && cachedMember != null) {
			cachedMemberParent.getCachedMembers().remove(cachedMember);
		}
	}

	private void clearExecutionState() {
		connectionCommand = null;
		visibilityCommand = null;
		cachedMemberParent = null;
		cachedMember = null;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(targetModel);
	}
}
