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

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectSingleStructChange extends AbstractCommandChange<FBNetworkElement> {
	private final Map<String, String> replaceableConMap;
	private final String sourceVarName;
	private final String destinationVarName;

	protected ConnectSingleStructChange(final URI elementURI, final Class<FBNetworkElement> elementClass,
			final Map<String, String> replaceableConMap, final String sourceVarName, final String destinationVarName) {
		super("", elementURI, elementClass);
		this.replaceableConMap = replaceableConMap;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {
		final CompoundCommand connectStructCommand = new CompoundCommand();

		final FBNetworkElement source = element.getInterface().getErrorMarker().stream()
				.flatMap(err -> err.getInputConnections().stream())
				.filter(con -> replaceableConMap.containsValue(con.getDestination().getName())).findFirst().get()
				.getSourceElement();
		final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
				element.getFbNetwork());

		structCon.setDestination(element.getInput(destinationVarName));
		structCon.setSource(source.getOutput(sourceVarName));
		connectStructCommand.add(structCon);
		element.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
				.filter(con -> replaceableConMap.containsValue(con.getDestination().getName()))
				.forEach(con -> connectStructCommand.add(new DeleteConnectionCommand(con)));

		return connectStructCommand;
	}

}
