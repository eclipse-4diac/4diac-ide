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
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class InsertFBIntoExecutionChainCommand extends Command implements ScopedCommand {

	private final SubApp subApp;
	private final FB insertedFB;
	private final IInterfaceElement predecessorOutputPin;
	private final CompoundCommand commands = new CompoundCommand();

	public InsertFBIntoExecutionChainCommand(final SubApp subApp, final FB insertedFB) {
		this.subApp = subApp;
		this.insertedFB = insertedFB;
		this.predecessorOutputPin = getExecutionChainEnd();
	}

	@Override
	public boolean canExecute() {
		return subApp != null && insertedFB != null && predecessorOutputPin != null
				&& !predecessorOutputPin.getOutputConnections().isEmpty();
	}

	@Override
	public void execute() {
		if (insertedFB.getInterface() == null || insertedFB.getInterface().getEventInputs().isEmpty()
				|| insertedFB.getInterface().getEventOutputs().isEmpty()) {
			return;
		}
		final Optional<IInterfaceElement> inputInsertedFB = Optional
				.ofNullable(insertedFB.getInterface().getEventInputs().getFirst());
		final Optional<IInterfaceElement> outputInsertedFB = Optional
				.ofNullable(insertedFB.getInterface().getEventOutputs().getFirst());

		if (inputInsertedFB.isEmpty() || outputInsertedFB.isEmpty()) {
			return;
		}
		commands.add(
				getCreateConnectionCommand(insertedFB.getFbNetwork(), predecessorOutputPin, inputInsertedFB.get()));
		commands.add(getCreateConnectionCommand(insertedFB.getFbNetwork(), outputInsertedFB.get(),
				predecessorOutputPin.getOutputConnections().getFirst().getDestination()));
		commands.add(new DeleteConnectionCommand(predecessorOutputPin.getOutputConnections().getFirst()));

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

	private IInterfaceElement getExecutionChainEnd() {
		final Optional<IInterfaceElement> saEventOutput = subApp.getInterface().getOutputs()
				.filter(ie -> !ie.getInputConnections().isEmpty()).findFirst();
		if (saEventOutput.isPresent() && !saEventOutput.get().getInputConnections().isEmpty()) {
			return saEventOutput.get().getInputConnections().getFirst().getSource();
		}
		return null;
	}

}
