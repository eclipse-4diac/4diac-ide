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
import java.util.function.Function;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectStructChange extends AbstractCommandChange<FBNetwork> {

	private final Map<String, String> replaceableConMap;
	private final URI source;
	private final URI destination;
	private final String sourceVarName;
	private final String destinationVarName;

	protected ConnectStructChange(final URI elementURI, final Class<FBNetwork> elementClass,
			final Map<String, String> replaceableConMap, final URI source, final URI destination,
			final String sourceVarName, final String destinationVarName) {
		super(elementURI, elementClass);
		this.replaceableConMap = replaceableConMap;
		this.source = source;
		this.destination = destination;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
	}

	@Override
	public void initializeValidationData(final FBNetwork element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBNetwork element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBNetwork element) {
		final CompoundCommand connectStructCommand = new CompoundCommand();
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(source).getType() instanceof final FBType sourceType
				&& TypeLibraryManager.INSTANCE.getTypeEntryForURI(destination)
						.getType() instanceof final FBType destinationType) {
			// Create map between correct connected FBs
			getElementsOfType(destinationType).stream().forEach(instance -> {
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream());

				// Collect all correct connections
				final List<Connection> cons = instance.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replaceableConMap.containsKey(con.getSource().getName())
								&& replaceableConMap.get(con.getSource().getName())
										.equals(con.getDestination().getName())
								&& con.getSourceElement().getType().getName().equals(sourceType.getName()))
						.toList();

				// Check if all connections between 2 instances are correct -> store in map
				if (cons.stream()
						.map((Function<? super Connection, ? extends FBNetworkElement>) Connection::getSourceElement)
						.distinct().count() == 1 && cons.size() == replaceableConMap.size()) {

					final FBNetworkElement source = instance.getInterface().getErrorMarker().stream()
							.flatMap(err -> err.getInputConnections().stream())
							.filter(con -> replaceableConMap.containsValue(con.getDestination().getName())).findFirst()
							.get().getSourceElement();
					final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
							instance.getFbNetwork());

					structCon.setDestination(instance.getInput(destinationVarName));
					structCon.setSource(source.getOutput(sourceVarName));
					connectStructCommand.add(structCon);
					instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
							.filter(con -> replaceableConMap.containsValue(con.getDestination().getName()))
							.forEach(con -> connectStructCommand.add(new DeleteConnectionCommand(con)));
				}
			});
		}
		return connectStructCommand;
	}

	// TODO: find right place
	private static List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}

}
