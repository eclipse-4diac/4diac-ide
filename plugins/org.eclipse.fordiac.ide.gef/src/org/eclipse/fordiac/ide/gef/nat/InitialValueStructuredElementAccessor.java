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

import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

public interface InitialValueStructuredElementAccessor<T> extends InitialValueElementAccessor<T> {

	/**
	 * Get the reference element for the element
	 *
	 * @return the reference element (may be null)
	 */
	ITypedElement getReferenceElement(T element);
}
