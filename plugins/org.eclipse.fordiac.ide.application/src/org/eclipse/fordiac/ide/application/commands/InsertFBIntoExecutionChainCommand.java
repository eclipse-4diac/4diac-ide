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
import org.eclipse.fordiac.ide.application.editparts.AbstractBlockFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.FBNetworkRootEditPart;
import org.eclipse.fordiac.ide.application.handlers.MarkPredecessorHandler;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;

public class InsertFBIntoExecutionChainCommand extends Command implements ScopedCommand {

	private final SubApp subApp;
	private final FB insertedFB;
	private final IInterfaceElement predecessorOutputPin;
	private final CompoundCommand commands = new CompoundCommand();

	public InsertFBIntoExecutionChainCommand(final SubApp subApp, final FB insertedFB,
			final FBNetworkRootEditPart rootEP) {
		this.subApp = subApp;
		this.insertedFB = insertedFB;
		this.predecessorOutputPin = getPredecessorEventOutput(MarkPredecessorHandler.getPredecessor(rootEP));
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
		commands.add(new SetPredecessorCommand(getEP(insertedFB)));

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

	private IInterfaceElement getPredecessorEventOutput(final AbstractBlockFBNElementEditPart predecessor) {
		if (predecessor == null) {
			return getExecutionChainEnd();
		}
		if (!predecessor.getModel().getInterface().getEventOutputs().isEmpty()) {
			return predecessor.getModel().getInterface().getEventOutputs().getFirst();
		}
		return null;
	}

	private IInterfaceElement getExecutionChainEnd() {
		final Optional<IInterfaceElement> saEventOutput = subApp.getInterface().getOutputs()
				.filter(ie -> !ie.getInputConnections().isEmpty()).findFirst();
		if (saEventOutput.isPresent() && !saEventOutput.get().getInputConnections().isEmpty()) {
			return saEventOutput.get().getInputConnections().getFirst().getSource();
		}
		return null;
	}

	private static AbstractBlockFBNElementEditPart getEP(final FBNetworkElement elem) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer.getEditPartRegistry().get(elem) instanceof final AbstractBlockFBNElementEditPart ep) {
				return ep;
			}
		}
		return null;
	}

	public class SetPredecessorCommand extends Command {

		private AbstractBlockFBNElementEditPart predecessor;
		private AbstractBlockFBNElementEditPart oldPredecessor;

		public SetPredecessorCommand(final AbstractBlockFBNElementEditPart predecessor) {
			this.predecessor = predecessor;
		}

		@Override
		public boolean canExecute() {
			return predecessor != null;
		}

		@Override
		public void execute() {
			if (predecessor.getRoot() instanceof final FBNetworkRootEditPart root) {
				oldPredecessor = MarkPredecessorHandler.getPredecessor(root);
				if (oldPredecessor != null) {
					MarkPredecessorHandler.setPredecessor(root, predecessor);
				}
			}
		}

		@Override
		public void undo() {
			if (predecessor != null && predecessor.getRoot() instanceof final FBNetworkRootEditPart root) {
				predecessor = MarkPredecessorHandler.getPredecessor(root);
				MarkPredecessorHandler.setPredecessor(root, oldPredecessor);
			}
		}

		@Override
		public void redo() {
			execute();
		}
	}

}
