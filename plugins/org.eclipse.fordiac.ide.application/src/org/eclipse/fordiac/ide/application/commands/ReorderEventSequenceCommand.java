/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Erich Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ReorderEventSequenceCommand extends Command implements ScopedCommand {

	private static final EventType DEFAULT_TYPE = EventTypeLibrary.getInstance().getType(null);

	private final List<BlockFBNetworkElement> elements;
	private final BlockFBNetworkElement target;
	private final boolean insertBefore;
	private final FBNetwork network;

	private String errorMessage = ""; //$NON-NLS-1$
	private DataType preferredType = DEFAULT_TYPE;

	private final CompoundCommand deleteCommands = new CompoundCommand();
	private final CompoundCommand createCommands = new CompoundCommand();

	public ReorderEventSequenceCommand(final Collection<? extends BlockFBNetworkElement> elements,
			final BlockFBNetworkElement target, final boolean insertBefore) {
		this.elements = List.copyOf(elements);
		this.target = Objects.requireNonNull(target);
		this.insertBefore = insertBefore;
		network = target.getFbNetwork();
	}

	@Override
	public boolean canExecute() {
		if (elements.isEmpty() || elements.contains(target) || new HashSet<>(elements).size() != elements.size()) {
			errorMessage = Messages.ReorderEventSequenceCommand_InvalidElements;
			return false;
		}
		// elements and target must be in the same network
		if (network == null || !elements.stream().map(FBNetworkElement::getFbNetwork).allMatch(network::equals)) {
			errorMessage = Messages.ReorderEventSequenceCommand_NotInSameNetwork;
			return false;
		}
		// elements must have at least one event
		if (elements.stream().anyMatch(ReorderEventSequenceCommand::hasNoInputEvents)
				|| elements.stream().anyMatch(ReorderEventSequenceCommand::hasNoOutputEvents)) {
			errorMessage = Messages.ReorderEventSequenceCommand_ElementsNoEvents;
			return false;
		}
		// elements must have at most one input and output connection
		if (elements.stream().anyMatch(ReorderEventSequenceCommand::hasMultipleInputConnections)
				|| elements.stream().anyMatch(ReorderEventSequenceCommand::hasMultipleOutputConnections)) {
			errorMessage = Messages.ReorderEventSequenceCommand_ElementsMultipleConnections;
			return false;
		}
		// target must have at least one input or one output event when inserting before
		// or after, respectively
		if (insertBefore ? hasNoInputEvents(target) : hasNoOutputEvents(target)) {
			errorMessage = insertBefore //
					? Messages.ReorderEventSequenceCommand_TargetNoInputEvents
					: Messages.ReorderEventSequenceCommand_TargetNoOutputEvents;
			return false;
		}
		// target must have either at most one input or one output connection when
		// inserting before or after, respectively
		if (insertBefore ? hasMultipleInputConnections(target) : hasMultipleOutputConnections(target)) {
			errorMessage = insertBefore //
					? Messages.ReorderEventSequenceCommand_TargetMultipleInputConnections
					: Messages.ReorderEventSequenceCommand_TargetMultipleOutputConnections;
			return false;
		}
		// reset error message
		errorMessage = ""; //$NON-NLS-1$
		// check commands
		initializeCommands();
		return (deleteCommands.isEmpty() || deleteCommands.canExecute()) && createCommands.canExecute();
	}

	@Override
	public boolean canRedo() {
		return (deleteCommands.isEmpty() || deleteCommands.canRedo()) && createCommands.canRedo();
	}

	@Override
	public boolean canUndo() {
		return createCommands.canUndo() && (deleteCommands.isEmpty() || deleteCommands.canUndo());
	}

	@Override
	public void execute() {
		initializeCommands();
		deleteCommands.execute();
		createCommands.execute();
	}

	@Override
	public void undo() {
		createCommands.undo();
		deleteCommands.undo();
	}

	@Override
	public void redo() {
		deleteCommands.redo();
		createCommands.redo();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private void initializeCommands() {
		if (!createCommands.isEmpty()) {
			return; // already initialized
		}

		if (insertBefore) {
			initializeInsertBeforeCommands();
		} else {
			initializeInsertAfterCommands();
		}
	}

	private void initializeInsertBeforeCommands() {
		final Event targetInput = findInputEvent(target);
		preferredType = targetInput.getType();

		BlockFBNetworkElement previousElement = target;
		for (final BlockFBNetworkElement element : elements.reversed()) {
			final Event elementInput = findInputEvent(element);
			final Event elementOutput = findOutputEvent(element);
			final Event previousInput = findInputEvent(previousElement);
			if (!LinkConstraints.duplicateConnection(elementOutput, previousInput)) {
				createConnection(elementOutput, previousInput);
				final Event pendingInput = deleteOutputConnection(elementOutput);
				if (!isElementInterface(pendingInput) && pendingInput != targetInput) {
					reconnectInputConnection(findBoundaryInputEvent(elementInput), pendingInput);
				}
			}
			previousElement = element;
		}

		final Event firstInput = findInputEvent(elements.getFirst());
		reconnectInputConnection(findBoundaryInputEvent(targetInput), firstInput);
	}

	private void initializeInsertAfterCommands() {
		final Event targetOutput = findOutputEvent(target);
		preferredType = targetOutput.getType();

		BlockFBNetworkElement previousElement = target;
		for (final BlockFBNetworkElement element : elements) {
			final Event elementInput = findInputEvent(element);
			final Event elementOutput = findOutputEvent(element);
			final Event previousOutput = findOutputEvent(previousElement);
			if (!LinkConstraints.duplicateConnection(previousOutput, elementInput)) {
				createConnection(previousOutput, elementInput);
				final Event pendingOutput = deleteInputConnection(elementInput);
				if (!isElementInterface(pendingOutput) && pendingOutput != targetOutput) {
					reconnectOutputConnection(findBoundaryOutputEvent(elementOutput), pendingOutput);
				}
			}
			previousElement = element;
		}

		final Event lastOutput = findOutputEvent(elements.getLast());
		reconnectOutputConnection(findBoundaryOutputEvent(targetOutput), lastOutput);
	}

	private void reconnectInputConnection(final Event oldInput, final Event newInput) {
		if (oldInput == null || newInput == oldInput) {
			return;
		}
		final Event pendingOutput = deleteInputConnection(oldInput);
		if (newInput != null && pendingOutput != null) {
			createConnection(pendingOutput, newInput);
		}
	}

	private void reconnectOutputConnection(final Event oldOutput, final Event newOutput) {
		if (oldOutput == null || newOutput == oldOutput) {
			return;
		}
		final Event pendingInput = deleteOutputConnection(oldOutput);
		if (newOutput != null && pendingInput != null) {
			createConnection(newOutput, pendingInput);
		}
	}

	private Event findBoundaryInputEvent(Event input) {
		while (input != null && isElementInterface(getSource(input))) {
			input = findInputEvent(getSource(input).getBlockFBNetworkElement()); // NOSONAR
		}
		return input;
	}

	private Event findBoundaryOutputEvent(Event output) {
		while (output != null && isElementInterface(getDestination(output))) {
			output = findOutputEvent(getDestination(output).getBlockFBNetworkElement()); // NOSONAR
		}
		return output;
	}

	private boolean isElementInterface(final Event event) {
		return event != null && elements.contains(event.getBlockFBNetworkElement());
	}

	private void createConnection(final Event source, final Event destination) {
		final EventConnectionCreateCommand command = new EventConnectionCreateCommand(network);
		command.setSource(source);
		command.setDestination(destination);
		createCommands.add(command);
	}

	private Event deleteInputConnection(final Event event) {
		if (event == null || event.getInputConnections().isEmpty()) {
			return null;
		}
		final Connection connection = event.getInputConnections().getFirst();
		deleteCommands.add(new DeleteConnectionCommand(connection));
		return (Event) connection.getSource();
	}

	private Event deleteOutputConnection(final Event event) {
		if (event == null || event.getOutputConnections().isEmpty()) {
			return null;
		}
		final Connection connection = event.getOutputConnections().getFirst();
		deleteCommands.add(new DeleteConnectionCommand(connection));
		return (Event) connection.getDestination();
	}

	private Event findInputEvent(final BlockFBNetworkElement element) {
		// find first connected event
		for (final Event event : element.getInterface().getEventInputs()) {
			if (!event.getInputConnections().isEmpty()) {
				return event;
			}
		}
		// find first event with preferred type
		for (final Event event : element.getInterface().getEventInputs()) {
			if (event.getType() == preferredType) {
				return event;
			}
		}
		// take first event
		return element.getInterface().getEventInputs().getFirst();
	}

	private Event findOutputEvent(final BlockFBNetworkElement element) {
		// find first connected event
		for (final Event event : element.getInterface().getEventOutputs()) {
			if (!event.getOutputConnections().isEmpty()) {
				return event;
			}
		}
		// find first event with preferred type
		for (final Event event : element.getInterface().getEventOutputs()) {
			if (event.getType() == preferredType) {
				return event;
			}
		}
		// take first event
		return element.getInterface().getEventOutputs().getFirst();
	}

	private static Event getSource(final Event destination) {
		if (destination == null || destination.getInputConnections().isEmpty()) {
			return null;
		}
		return (Event) destination.getInputConnections().getFirst().getSource();
	}

	private static Event getDestination(final Event source) {
		if (source == null || source.getOutputConnections().isEmpty()) {
			return null;
		}
		return (Event) source.getOutputConnections().getFirst().getDestination();
	}

	private static boolean hasNoInputEvents(final BlockFBNetworkElement element) {
		return element.getInterface().getEventInputs().isEmpty();
	}

	private static boolean hasNoOutputEvents(final BlockFBNetworkElement element) {
		return element.getInterface().getEventOutputs().isEmpty();
	}

	private static boolean hasMultipleInputConnections(final BlockFBNetworkElement element) {
		return element.getInterface().getEventInputs().stream().map(Event::getInputConnections).mapToInt(List::size)
				.sum() > 1;
	}

	private static boolean hasMultipleOutputConnections(final BlockFBNetworkElement element) {
		return element.getInterface().getEventOutputs().stream().map(Event::getOutputConnections).mapToInt(List::size)
				.sum() > 1;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Stream.concat(elements.stream(), Stream.of(target, network)).filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableSet());
	}
}
