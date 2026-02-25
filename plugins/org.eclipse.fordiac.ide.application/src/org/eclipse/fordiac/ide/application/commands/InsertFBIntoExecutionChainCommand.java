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

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class InsertFBIntoExecutionChainCommand extends Command implements ScopedCommand {

	private final SubApp subApp;
	private final FB insertedFB;
	private final CompoundCommand commands = new CompoundCommand();

	public InsertFBIntoExecutionChainCommand(final SubApp subApp, final FB insertedFB) {
		this.subApp = subApp;
		this.insertedFB = insertedFB;
	}

	@Override
	public boolean canExecute() {
		return subApp != null && insertedFB != null;
	}

	@Override
	public void execute() {
		if (insertedFB.getInterface() == null || insertedFB.getInterface().getEventInputs().isEmpty()
				|| insertedFB.getInterface().getEventOutputs().isEmpty()) {
			return;
		}

		final BlockFBNetworkElement predecessor = getValidActivePredecessor(subApp);
		final IInterfaceElement eventOutput = predecessor != null ? getPredecessorEventOutput(predecessor)
				: getExecutionChainEnd();

		if (!hasOutputConnections(eventOutput)) {
			return;
		}

		final IInterfaceElement eventOutputDestination = eventOutput.getOutputConnections().getFirst().getDestination();

		final IInterfaceElement inputInsertedFB = getMatchingEventPin(insertedFB.getInterface().getEventInputs(),
				eventOutput.getType());
		final IInterfaceElement outputInsertedFB = getMatchingEventPin(insertedFB.getInterface().getEventOutputs(),
				eventOutputDestination.getType());

		commands.add(getCreateConnectionCommand(insertedFB.getFbNetwork(), eventOutput, inputInsertedFB));
		commands.add(getCreateConnectionCommand(insertedFB.getFbNetwork(), outputInsertedFB, eventOutputDestination));
		commands.add(new DeleteConnectionCommand(eventOutput.getOutputConnections().getFirst()));

		if (predecessor != null) {
			commands.add(new SetPredecessorCommand(insertedFB));
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

	private static IInterfaceElement getMatchingEventPin(final EList<Event> pins, final DataType type) {
		return pins.stream().filter(event -> event.getType().equals(type)).findFirst().orElse(pins.getFirst());
	}

	private static boolean hasOutputConnections(final IInterfaceElement output) {
		return output != null && !output.getOutputConnections().isEmpty();
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

	private static BlockFBNetworkElement getValidActivePredecessor(final SubApp subApp) {
		if (UtilityMarkerHelper.getMarkedElement(UtilityMarkerHelper.PREDECESSOR_MARKER_ID,
				subApp) instanceof final BlockFBNetworkElement block && isContainedInSubappNetwork(block, subApp)) {
			return block;
		}
		return null;
	}

	private static IInterfaceElement getPredecessorEventOutput(final BlockFBNetworkElement predecessor) {
		if (!predecessor.getInterface().getEventOutputs().isEmpty()) {
			return predecessor.getInterface().getEventOutputs().stream()
					.filter(oe -> !oe.getOutputConnections().isEmpty()).findFirst().orElse(null);
		}
		return null;
	}

	private static boolean isContainedInSubappNetwork(final BlockFBNetworkElement elem, final SubApp sa) {
		return sa.getSubAppNetwork() == elem.getFbNetwork();
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

		private final BlockFBNetworkElement predecessor;
		private BlockFBNetworkElement oldPredecessor;
		private final IResource resource;

		public SetPredecessorCommand(final BlockFBNetworkElement predecessor) {
			this.predecessor = predecessor;
			resource = getResource(predecessor);
		}

		@Override
		public boolean canExecute() {
			return resource != null && predecessor != null;
		}

		@Override
		public void execute() {
			if (UtilityMarkerHelper.getMarkedElement(UtilityMarkerHelper.PREDECESSOR_MARKER_ID,
					predecessor) instanceof final BlockFBNetworkElement elem) {
				oldPredecessor = elem;
				setMarkedElement(predecessor);
			}
		}

		@Override
		public void undo() {
			if (oldPredecessor != null) {
				setMarkedElement(oldPredecessor);
			} else {
				UtilityMarkerHelper.deleteElementMarker(UtilityMarkerHelper.PREDECESSOR_MARKER_ID, resource);
			}
		}

		@Override
		public void redo() {
			setMarkedElement(predecessor);
		}

		private void setMarkedElement(final BlockFBNetworkElement element) {
			UtilityMarkerHelper.setMarkedElement(UtilityMarkerHelper.PREDECESSOR_MARKER_ID, element);
		}

		private static IResource getResource(final BlockFBNetworkElement predecessor) {
			final EObject rootContainer = EcoreUtil.getRootContainer(predecessor);
			if (rootContainer instanceof final LibraryElement libEl && libEl.getTypeEntry() != null) {
				return libEl.getTypeEntry().getFile();
			}
			return null;
		}

	}

}
