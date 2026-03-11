/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *     - refactored for generic library elements and type entries
 ******************************************************************************/

package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

/**
 * An interface to provide content for a type selection.
 */
public interface ITypeSelectionContentProvider {
	/**
	 * Get the elements for the type selection
	 *
	 * @return a list of types to display in the viewer
	 */
	Stream<LibraryElement> getTypes(Object input);

	/**
	 * Get the type entries for the type selection
	 *
	 * @return a list of types to display in the viewer
	 */
	Stream<TypeEntry> getTypeEntries(Object input);
}
