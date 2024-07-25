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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConnectStructChange extends Change {

	private final Map<String, String> replaceableConMap;
	private final URI source;
	private final URI destination;
	private final String sourceVarName;
	private final String destinationVarName;

	private final CompositeChange connectStructChange;

	protected ConnectStructChange(final URI elementURI, final Class<FBNetwork> elementClass,
			final Map<String, String> replaceableConMap, final URI source, final URI destination,
			final String sourceVarName, final String destinationVarName) {
		this.replaceableConMap = replaceableConMap;
		this.source = source;
		this.destination = destination;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;

		connectStructChange = new CompositeChange("");
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(source).getType() instanceof final FBType sourceType
				&& TypeLibraryManager.INSTANCE.getTypeEntryForURI(destination)
						.getType() instanceof final FBType destinationType) {

			getElementsOfType(destinationType).stream().forEach(instance -> {

				// Collect all correct connections
				final List<Connection> cons = instance.getInterface().getInputs()
						.map(IInterfaceElement::getInputConnections).flatMap(EList::stream)
						.filter(con -> replaceableConMap.containsKey(con.getSource().getName())
								&& replaceableConMap.get(con.getSource().getName())
										.equals(con.getDestination().getName())
								&& con.getSourceElement().getType().getName().equals(sourceType.getName()))
						.toList();

				// Check if all connections between 2 instances are correct
				if (cons.stream()
						.map((Function<? super Connection, ? extends FBNetworkElement>) Connection::getSourceElement)
						.distinct().count() == 1 && cons.size() == replaceableConMap.size()) {
					connectStructChange.add(new ConnectSingleStructChange(EcoreUtil.getURI(instance),
							FBNetworkElement.class, replaceableConMap, sourceVarName, destinationVarName));
				}
			});
		}
	}

	// TODO: find right place
	private static List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		return connectStructChange.perform(pm);
	}

	@Override
	public Object getModifiedElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
