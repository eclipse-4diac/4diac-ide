/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.RepairBrokenConnectionCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A Change that represents Repairing a broken Connections of one System for
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.ConnectionsToStructRefactoring
 * ConnectionsToStructRefactoring} with
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.RepairBrokenConnectionCommand
 * RepairBrokenConnectionCommands}. Grouping by System is needed, as creating a
 * Change for each individually leads to longer execution times.
 */
public class SystemRepairBrokenConnectionChange extends AbstractCommandChange<AutomationSystem> {
	private final URI structURI;
	private final Map<String, String> replaceableConMap;
	private final List<URI> list;
	private final boolean isSource;

	/**
	 * Creates a new Instance
	 *
	 * @param elementURI        URI of the Target System
	 * @param structURI         URI of the Struct which is used to repair the FBs
	 * @param replaceableConMap mapping of the Output Variables to the Input
	 *                          Variables
	 * @param list              URIs of FBs to be repaired
	 * @param isSource          Whether the FB is the Source for the broken
	 *                          Connection
	 */
	public SystemRepairBrokenConnectionChange(final URI elementURI, final URI structURI,
			final Map<String, String> replaceableConMap, final List<URI> list, final boolean isSource) {
		super(Objects.requireNonNull(elementURI).trimFileExtension().lastSegment()
				+ Messages.RepairBrokenConnectionChange_Name, elementURI, AutomationSystem.class);
		this.structURI = Objects.requireNonNull(structURI);
		this.replaceableConMap = Objects.requireNonNull(replaceableConMap);
		this.list = Objects.requireNonNull(list);
		this.isSource = isSource;
	}

	@Override
	public void initializeValidationData(final AutomationSystem element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final AutomationSystem element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		list.forEach(uri -> {
			if (!uri.toPlatformString(true).equals(this.getElementURI().toPlatformString(true))) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						uri + Messages.ConnectionsToStructRefactoring_FBNotInSystem + this.getElementURI()));
			}
		});
		return status;
	}

	@Override
	protected Command createCommand(final AutomationSystem element) {
		final CompoundCommand cmd = new CompoundCommand();
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
				.getType() instanceof final StructuredType structType) {
			list.forEach(uri -> {
				if (element.eResource().getEObject(uri.fragment()) instanceof final FBNetworkElement fbnelem) {
					final Stream<ErrorMarkerInterface> errormarkers = fbnelem.getInterface().getErrorMarker().stream();
					final Stream<Connection> connections;
					final Function<Connection, String> connectToVar;

					if (isSource) {
						connections = errormarkers.filter(err -> replaceableConMap.containsKey(err.getName()))
								.map(ErrorMarkerInterface::getOutputConnections).flatMap(EList::stream);
						connectToVar = t -> t.getSource().getName();
					} else {
						connections = errormarkers.filter(err -> replaceableConMap.containsValue(err.getName()))
								.map(ErrorMarkerInterface::getInputConnections).flatMap(EList::stream);
						connectToVar = t -> replaceableConMap.entrySet().stream()
								.filter(entry -> entry.getValue().equals(t.getDestination().getName())).findAny().get()
								.getKey();

					}
					connections.forEach(con -> cmd.add(
							new RepairBrokenConnectionCommand(con, isSource, structType, connectToVar.apply(con))));
				}
			});
		}

		return cmd;
	}

}
