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

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public final class FordiacErrorMarkerInterfaceHelper {

	public static ErrorMarkerInterface createErrorMarkerInterface(final DataType type, final String name,
			final boolean isInput, final InterfaceList ieList) {
		return ieList.getErrorMarker().stream().filter(e -> e.getName().equals(name) && isInput == e.isIsInput())
				.findAny().orElseGet(() -> createErrorMarker(type, name, isInput, ieList));
	}

	private static ErrorMarkerInterface createErrorMarker(final DataType type, final String name, final boolean isInput,
			final InterfaceList ieList) {
		final ErrorMarkerInterface errorMarkerInterface = LibraryElementFactory.eINSTANCE.createErrorMarkerInterface();
		errorMarkerInterface.setName(name);
		errorMarkerInterface.setIsInput(isInput);
		errorMarkerInterface.setType(type);
		ieList.getErrorMarker().add(errorMarkerInterface);
		return errorMarkerInterface;
	}

	private FordiacErrorMarkerInterfaceHelper() {
		throw new UnsupportedOperationException();
	}
}
