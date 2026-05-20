/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A Change that represents all new Struct Connections of one System for the
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring
 * ConnectionsToStructRefactoring}. The old (and due to the Refactoring now
 * broken) Connections are seleted. Grouping by System is needed, as creating a
 * Change for each individually leads to longer execution times.
 */
public class SystemConnectStructModelEdit extends ModelEdit<AutomationSystem> {
	private final Map<String, String> replaceableConMap;
	private final Map<URI, URI> sourceMap;
	private final String sourceVarName;
	private final String destinationVarName;
	private final List<URI> conlist;

	/**
	 * Creates a new Instance
	 *
	 * @param elementURI         URI of the target System
	 * @param list               URIs of the destination FBs which can be connected
	 *                           by a new Struct Connection
	 * @param sourceMap          Mapping of destination FB URIs to source FB URIs
	 * @param replaceableConMap  Mapping of the Output Variables to the Input
	 *                           Variables
	 * @param sourceVarName      Name of the Struct Variable Output at the Source
	 * @param destinationVarName Name of the Struct Variable Input at the
	 *                           Destination
	 */
	public SystemConnectStructModelEdit(final URI elementURI, final List<URI> list, final Map<URI, URI> sourceMap,
			final Map<String, String> replaceableConMap, final String sourceVarName, final String destinationVarName) {
		super(Objects.requireNonNull(elementURI).trimFileExtension().lastSegment()
				+ Messages.SystemConnectStructChange_Name, elementURI, AutomationSystem.class);
		this.conlist = Objects.requireNonNull(list);
		this.sourceMap = Objects.requireNonNull(sourceMap);
		this.replaceableConMap = Objects.requireNonNull(replaceableConMap);
		this.sourceVarName = Objects.requireNonNull(sourceVarName);
		this.destinationVarName = Objects.requireNonNull(destinationVarName);
	}

	@Override
	public void initializeValidationData(final AutomationSystem element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final AutomationSystem element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		conlist.forEach(uri -> {
			if (!uri.toPlatformString(true).equals(this.getElementURI().toPlatformString(true))) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						uri + Messages.ConnectionsToStructRefactoring_FBNotInSystem + this.getElementURI()));
			}
		});
		return status;
	}

	@Override
	protected Command createCommand(final AutomationSystem element) {
		return new Command() {
			private CompoundCommand commands;

			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			public void execute() {
				commands = createStructConnectionCommands(element);
			}

			@Override
			public boolean canUndo() {
				return commands != null && commands.canUndo();
			}

			@Override
			public void undo() {
				if (commands != null) {
					commands.undo();
				}
			}

			@Override
			public boolean canRedo() {
				return commands != null && commands.canRedo();
			}

			@Override
			public void redo() {
				if (commands != null) {
					commands.redo();
				}
			}
		};
	}

	private CompoundCommand createStructConnectionCommands(final AutomationSystem element) {
		final CompoundCommand cmds = new CompoundCommand();
		conlist.forEach(uri -> {
			if (element.eResource().getEObject(uri.fragment()) instanceof final BlockFBNetworkElement fbnelem) {
				BlockFBNetworkElement source = getSourceElement(element, uri);
				List<Connection> connections = getReplaceableConnections(fbnelem, source);
				if (connections.isEmpty()) {
					connections = getReplaceableConnections(fbnelem, null);
					source = getSourceElement(connections);
				}
				if (source != null) {
					final IInterfaceElement structSource = source.getInterface().getOutput(List.of(sourceVarName));
					final IInterfaceElement structDestination = fbnelem.getInterface()
							.getInput(List.of(destinationVarName));
					if (structSource != null && structDestination != null) {
						final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
								fbnelem.getFbNetwork());

						structCon.setDestination(structDestination);
						structCon.setSource(structSource);
						if (!hasStructConnection(structSource, structDestination)) {
							executeAndRemember(cmds, structCon);
						}
						if (hasStructConnection(structSource, structDestination)) {
							connections.forEach(con -> executeAndRemember(cmds, new DeleteConnectionCommand(con)));
						}
					}
				}
			}
		});
		return cmds;
	}

	private static BlockFBNetworkElement getSourceElement(final List<Connection> connections) {
		return connections.stream().map(Connection::getSourceElement).filter(Objects::nonNull).findFirst().orElse(null);
	}

	private BlockFBNetworkElement getSourceElement(final AutomationSystem element, final URI destinationURI) {
		final URI sourceURI = sourceMap.get(destinationURI);
		if (sourceURI != null
				&& element.eResource().getEObject(sourceURI.fragment()) instanceof final BlockFBNetworkElement source) {
			return source;
		}
		return null;
	}

	private List<Connection> getReplaceableConnections(final BlockFBNetworkElement destination,
			final BlockFBNetworkElement source) {
		return destination.getFbNetwork().getDataConnections().stream().map(Connection.class::cast)
				.filter(con -> source == null || source.equals(con.getSourceElement()))
				.filter(con -> destination.equals(con.getDestinationElement()))
				.filter(con -> con.getSource() != null && con.getDestination() != null)
				.filter(con -> replaceableConMap.containsKey(con.getSource().getName()))
				.filter(con -> replaceableConMap.get(con.getSource().getName()).equals(con.getDestination().getName()))
				.toList();
	}

	private static void executeAndRemember(final CompoundCommand commands, final Command command) {
		command.execute();
		commands.add(command);
	}

	private static boolean hasStructConnection(final IInterfaceElement source, final IInterfaceElement destination) {
		return source != null && destination != null
				&& source.getOutputConnections().stream().anyMatch(con -> destination.equals(con.getDestination()));
	}

}
