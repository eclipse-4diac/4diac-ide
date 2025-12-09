/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class AttributeSelectionContentProvider implements ITypeSelectionContentProvider {

	public static final AttributeSelectionContentProvider INSTANCE = new AttributeSelectionContentProvider();

	protected AttributeSelectionContentProvider() {
	}

	@Override
	public Collection<LibraryElement> getTypes(final Object input) {
		return Collections.emptyList();
	}

	@Override
	public Collection<TypeEntry> getTypeEntries(final Object input) {
		if (input instanceof final TypeLibrary typeLibrary) {
			return Collections.unmodifiableCollection(typeLibrary.getAttributeTypes());
		}
		return Collections.emptyList();
	}
}
