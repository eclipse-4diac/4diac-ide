/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface InitialValueElementAccessor<T> {

	/**
	 * Get the context for the element
	 * @return the context (may be null)
	 */
	LibraryElement getContext(T element);

	/**
	 * Get the type for the element
	 * @return the type (may be null)
	 */
	LibraryElement getType(T element);
}
