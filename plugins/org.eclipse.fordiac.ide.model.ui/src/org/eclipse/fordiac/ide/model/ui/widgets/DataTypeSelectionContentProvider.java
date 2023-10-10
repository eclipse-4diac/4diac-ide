/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class DataTypeSelectionContentProvider implements ITypeSelectionContentProvider {

	public static final DataTypeSelectionContentProvider INSTANCE = new DataTypeSelectionContentProvider();

	protected DataTypeSelectionContentProvider() {
	}

	@Override
	public Collection<LibraryElement> getTypes(final Object input) {
		return Collections.unmodifiableCollection(Stream
				.concat(ElementaryTypes.getAllElementaryType().stream(), GenericTypes.getAllGenericTypes().stream())
				.toList());
	}

	@Override
	public Collection<TypeEntry> getTypeEntries(final Object input) {
		if (input instanceof final TypeLibrary typeLibrary) {
			return Collections.unmodifiableCollection(typeLibrary.getDataTypeLibrary().getDerivedDataTypes());
		}
		return Collections.emptyList();
	}
}
