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
import org.eclipse.fordiac.ide.application.handlers.MarkPredecessorHandler;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.fordiac.util.marker.MarkerStore;
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
		this.predecessorOutputPin = getPredecessorEventOutput(getCurrentPredecessor());
	}

	@Override
	public boolean canExecute() {
		return subApp != null && insertedFB != null;
	}

	@Override
	public void execute() {
		if (insertedFB.getInterface() == null || insertedFB.getInterface().getEventInputs().isEmpty()
				|| insertedFB.getInterface().getEventOutputs().isEmpty() || predecessorOutputPin == null
				|| predecessorOutputPin.getOutputConnections().isEmpty()) {
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
		commands.add(new SetPredecessorCommand(insertedFB));

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
		if (source.getBlockFBNetworkElement().getFbNetwork() != target.getBlockFBNetworkElement().getFbNetwork()) {
			return CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, target);
		}
		final AbstractConnectionCreateCommand createConnectionCommand = AbstractConnectionCreateCommand
				.createCommand(network, source, target);
		createConnectionCommand.setSource(source);
		createConnectionCommand.setDestination(target);
		return createConnectionCommand;
	}

	private static BlockFBNetworkElement getCurrentPredecessor() {
		final Optional<MarkerStore> store = MarkerStore.getStoreFromEditor();
		if (store.isPresent()) {
			final EObject markedElement = store.get().getMarkedElement(MarkerDescriptor.PREDECESSOR.ID());
			if (markedElement instanceof final BlockFBNetworkElement elem) {
				return elem;
			}
		}
		return null;
	}

	private IInterfaceElement getPredecessorEventOutput(final BlockFBNetworkElement predecessor) {
		if (predecessor == null) {
			return getExecutionChainEnd();
		}
		if (!predecessor.getInterface().getEventOutputs().isEmpty()) {
			return predecessor.getInterface().getEventOutputs().getFirst();
		}
		return null;
	}

	private IInterfaceElement getExecutionChainEnd() {
		final Optional<IInterfaceElement> saEventOutput = subApp.getInterface().getAllOutputs()
				.filter(ie -> !ie.getInputConnections().isEmpty()).findFirst();
		if (saEventOutput.isPresent() && !saEventOutput.get().getInputConnections().isEmpty()) {
			return saEventOutput.get().getInputConnections().getFirst().getSource();
		}
		return null;
	}

	public class SetPredecessorCommand extends Command {

		private final FBNetworkElement predecessor;
		private FBNetworkElement oldPredecessor;
		private final Optional<MarkerStore> store;

		public SetPredecessorCommand(final FBNetworkElement predecessor) {
			this.predecessor = predecessor;
			this.store = MarkerStore.getStoreFromEditor();
		}

		@Override
		public boolean canExecute() {
			return predecessor != null && store.isPresent();
		}

		@Override
		public void execute() {
			if (store.get()
					.getMarkedElement(MarkerDescriptor.PREDECESSOR.ID()) instanceof final FBNetworkElement elem) {
				oldPredecessor = elem;
				MarkPredecessorHandler.removePredecessor();
				MarkPredecessorHandler.setPredecessor(predecessor);
			}
		}

		@Override
		public void undo() {
			MarkPredecessorHandler.removePredecessor();
			MarkPredecessorHandler.setPredecessor(oldPredecessor);
		}

		@Override
		public void redo() {
			execute();
		}
	}

}
