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

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FBTypeSelectionContentProvider implements ITypeSelectionContentProvider {

	public static final FBTypeSelectionContentProvider INSTANCE = new FBTypeSelectionContentProvider();

	private final Predicate<FBTypeEntry> filter;

	protected FBTypeSelectionContentProvider() {
		this(unused -> true);
	}

	public FBTypeSelectionContentProvider(final Predicate<FBTypeEntry> filter) {
		this.filter = filter;
	}

	@Override
	public Stream<LibraryElement> getTypes(final Object input) {
		return Stream.empty();
	}

	@Override
	public Stream<TypeEntry> getTypeEntries(final Object input) {
		if (input instanceof final TypeLibrary typeLibrary) {
			return typeLibrary.getFbTypes().filter(filter).map(Function.identity());
		}
		return Stream.empty();
	}
}
