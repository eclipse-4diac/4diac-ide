/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.commands;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteElementInExecutionChainCommand extends Command implements ScopedCommand {

	private final FBNetworkElement elementToDelete;
	private final CompoundCommand commands = new CompoundCommand();

	public DeleteElementInExecutionChainCommand(final FBNetworkElement toDelete) {
		this.elementToDelete = toDelete;
	}

	@Override
	public boolean canExecute() {
		return elementToDelete != null;
	}

	@Override
	public void execute() {
		if (elementToDelete.getInterface() == null || elementToDelete.getInterface().getEventInputs().isEmpty()
				|| elementToDelete.getInterface().getEventOutputs().isEmpty()) {
			return;
		}

		commands.add(new DeleteFBNetworkElementCommand(elementToDelete));

		final Optional<Event> inputEvent = elementToDelete.getInterface().getEventInputs().stream()
				.filter(e -> !e.getInputConnections().isEmpty()).findFirst();
		final Optional<Event> outputEvent = elementToDelete.getInterface().getEventOutputs().stream()
				.filter(e -> !e.getOutputConnections().isEmpty()).findFirst();

		if (outputEvent.isPresent() && inputEvent.isPresent()) {
			final IInterfaceElement conSource = inputEvent.get().getInputConnections().getFirst().getSource();
			final IInterfaceElement conTarget = outputEvent.get().getOutputConnections().getFirst().getDestination();
			if (conTarget != null && conSource != null) {
				commands.add(getCreateConnectionCommand(elementToDelete.getFbNetwork(), conSource, conTarget));
			}
		}
		if (commands.canExecute()) {
			commands.execute();
		}
	}

	@Override
	public void undo() {
		commands.undo();
	}

	@Override
	public void redo() {
		commands.redo();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return commands.getCommands().stream().filter(ScopedCommand.class::isInstance)
				.map(obj -> ((ScopedCommand) obj).getAffectedObjects()).flatMap(Set::stream)
				.collect(Collectors.toUnmodifiableSet());
	}

	private static Command getCreateConnectionCommand(final FBNetwork network, final IInterfaceElement source,
			final IInterfaceElement target) {
		if (source.getFBNetworkElement().getFbNetwork() != target.getFBNetworkElement().getFbNetwork()) {
			return CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, target);
		}
		final AbstractConnectionCreateCommand createConnectionCommand = AbstractConnectionCreateCommand
				.createCommand(network, source, target);
		createConnectionCommand.setSource(source);
		createConnectionCommand.setDestination(target);
		return createConnectionCommand;
	}

}
