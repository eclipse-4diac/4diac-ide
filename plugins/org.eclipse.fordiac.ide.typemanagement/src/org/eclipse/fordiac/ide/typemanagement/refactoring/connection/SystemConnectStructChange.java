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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
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
public class SystemConnectStructChange extends AbstractCommandChange<AutomationSystem> {
	private final Map<String, String> replaceableConMap;
	private final String sourceVarName;
	private final String destinationVarName;
	private final List<URI> conlist;

	/**
	 * Creates a new Instance
	 *
	 * @param elementURI         URI of the target System
	 * @param list               URIs of the destination FBs which can be connected
	 *                           by a new Struct Connection
	 * @param replaceableConMap  Mapping of the Output Variables to the Input
	 *                           Variables
	 * @param sourceVarName      Name of the Struct Variable Output at the Source
	 * @param destinationVarName Name of the Struct Variable Input at the
	 *                           Destination
	 */
	public SystemConnectStructChange(final URI elementURI, final List<URI> list,
			final Map<String, String> replaceableConMap, final String sourceVarName, final String destinationVarName) {
		super(Objects.requireNonNull(elementURI).trimFileExtension().lastSegment()
				+ Messages.SystemConnectStructChange_Name, elementURI, AutomationSystem.class);
		this.conlist = Objects.requireNonNull(list);
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
		final CompoundCommand cmds = new CompoundCommand();
		conlist.forEach(uri -> {
			if (element.eResource().getEObject(uri.fragment()) instanceof final FBNetworkElement fbnelem) {
				final FBNetworkElement source = fbnelem.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replaceableConMap.containsValue(con.getDestination().getName())).findFirst()
						.get().getSourceElement();
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						fbnelem.getFbNetwork());

				structCon.setDestination(fbnelem.getInput(destinationVarName));
				structCon.setSource(source.getOutput(sourceVarName));
				cmds.add(structCon);
				fbnelem.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replaceableConMap.containsValue(con.getDestination().getName()))
						.forEach(con -> cmds.add(new DeleteConnectionCommand(con)));
			}
		});
		return cmds;
	}

}
