/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface ISearchContext {

	Stream<URI> getTypes();

	@Deprecated
	Collection<URI> getAllTypes();

	@Deprecated
	Collection<URI> getSubappTypes();

	@Deprecated
	Collection<URI> getFBTypes();

	LibraryElement getLibraryElement(URI uri);

}
