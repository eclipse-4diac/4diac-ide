/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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

import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;

/**
 * A class providing support for searches
 */
public interface ISearchSupport {

	/**
	 * Search in the target element
	 *
	 * @param matcher The matcher to search with
	 * @return The matches
	 * @throws CoreException if there are any exception during the search
	 */
	Stream<Match> search(IModelMatcher matcher);

	/**
	 * Get the (actually used) imported namespaces of the target element
	 *
	 * @return The imported namespaces
	 */
	Set<String> getImportedNamespaces();

	/**
	 * Check if there were any errors that may lead to an incomplete result
	 *
	 * @return true if the result may be incomplete, false otherwise
	 */
	boolean isIncompleteResult();
}
