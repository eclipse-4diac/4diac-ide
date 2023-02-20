/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *               2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - extension for connection error markers
 *   Michael Oberlehner
 *               - Refactoring of API
 *   Martin Jobst - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.errormarker;

import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class FordiacErrorMarkerInterfaceHelper {
	public static ErrorMarkerInterface createErrorMarkerInterfaceElement(final FBNetworkElement newElement,
			final IInterfaceElement oldInterface, final String errorMessage,
			final List<ErrorMarkerBuilder> errorMarkerBuilders) {
		final boolean markerExists = newElement.getInterface().getErrorMarker().stream().anyMatch(
				e -> e.getName().equals(oldInterface.getName()) && (e.isIsInput() == oldInterface.isIsInput()));

		final ErrorMarkerInterface interfaceElement = createErrorMarkerInterface(oldInterface.getType(),
				oldInterface.getName(), oldInterface.isIsInput(), newElement.getInterface(), errorMessage);

		if (oldInterface instanceof VarDeclaration && ((VarDeclaration) oldInterface).getValue() != null
				&& !((VarDeclaration) oldInterface).getValue().getValue().isBlank()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(((VarDeclaration) oldInterface).getValue().getValue());
			value.setErrorMessage(((VarDeclaration) oldInterface).getValue().getErrorMessage());
			interfaceElement.setValue(value);
		}

		if (!markerExists && errorMarkerBuilders != null) {
			errorMarkerBuilders
					.add(ErrorMarkerBuilder.createErrorMarkerBuilder(errorMessage).setTarget(interfaceElement));
		}
		return interfaceElement;
	}

	public static ErrorMarkerInterface createErrorMarkerInterface(final DataType type, final String name,
			final boolean isInput, final InterfaceList ieList, final String errorMessage) {
		return ieList.getErrorMarker().stream().filter(e -> e.getName().equals(name) && isInput == e.isIsInput())
				.findAny().orElseGet(() -> createErrorMarker(type, name, isInput, ieList, errorMessage));
	}

	private static ErrorMarkerInterface createErrorMarker(final DataType type, final String name, final boolean isInput,
			final InterfaceList ieList, final String errorMessage) {
		final ErrorMarkerInterface errorMarkerInterface = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		errorMarkerInterface.setName(name);
		errorMarkerInterface.setIsInput(isInput);
		errorMarkerInterface.setType(type);
		errorMarkerInterface.setTypeName(type.getName());
		errorMarkerInterface.setErrorMessage(errorMessage);
		ieList.getErrorMarker().add(errorMarkerInterface);
		return errorMarkerInterface;
	}

	private FordiacErrorMarkerInterfaceHelper() {
		throw new UnsupportedOperationException();
	}
}
