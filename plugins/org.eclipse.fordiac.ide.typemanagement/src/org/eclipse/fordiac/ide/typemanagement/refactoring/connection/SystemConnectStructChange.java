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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class SystemConnectStructChange extends AbstractCommandChange<AutomationSystem> {
	private final Map<String, String> replaceableConMap;
	private final String sourceVarName;
	private final String destinationVarName;
	private final List<URI> conlist;

	public SystemConnectStructChange(final URI elementURI, final List<URI> list,
			final Map<String, String> replaceableConMap, final String sourceVarName, final String destinationVarName) {
		super(elementURI.trimFileExtension().lastSegment() + ": Connect identical Patterns with Struct Connection",
				elementURI, AutomationSystem.class);
		this.conlist = list;
		this.replaceableConMap = replaceableConMap;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
	}

	@Override
	public void initializeValidationData(final AutomationSystem element, final IProgressMonitor pm) {
		// TODO
	}

	@Override
	public RefactoringStatus isValid(final AutomationSystem element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO
		return null;
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
