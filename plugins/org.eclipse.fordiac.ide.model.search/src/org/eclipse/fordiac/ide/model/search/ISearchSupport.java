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
	Stream<Match> search(IModelMatcher matcher) throws CoreException;
}
