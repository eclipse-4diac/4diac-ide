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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RepairBrokenConnectionChange extends AbstractCommandChange<FBNetworkElement> {
	private final URI structURI;
	private final Map<String, String> replaceableConMap;
	private final boolean isSource;

	public RepairBrokenConnectionChange(final URI elementURI, final Class<FBNetworkElement> elementClass,
			final URI structURI, final Map<String, String> replaceableConMap, final boolean isSource) {
		super(createName(elementURI), elementURI, elementClass);
		this.replaceableConMap = replaceableConMap;
		this.structURI = structURI;
		this.isSource = isSource;

	}

	private static String createName(final URI elementURI) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (entry != null && entry.getType().eResource()
				.getEObject(elementURI.fragment()) instanceof final FBNetworkElement fbnelement) {
			return "Repair " + fbnelement.getQualifiedName();
		}
		return "";
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
		final CompoundCommand cmd = new CompoundCommand();
		final Stream<ErrorMarkerInterface> errormarkers = element.getInterface().getErrorMarker().stream();
		final Stream<Connection> connections;
		final Function<Connection, String> connectToVar;

		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(structURI)
				.getType() instanceof final StructuredType structType) {
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

			connections.forEach(con -> cmd
					.add(new RepairBrokenConnectionCommand(con, isSource, structType, connectToVar.apply(con))));
		}
		System.out.println(getName());
		return cmd;
	}

}
