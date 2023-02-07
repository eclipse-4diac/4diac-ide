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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public final class NamedElementAnnotations {
	private static final String QUALIFIED_NAME_DELIMITER = "."; //$NON-NLS-1$

	/** Do not call directly! Use {@link INamedElement#getQualifiedName()} instead.
	 *
	 * Must be accessible from derived models. */
	public static String getQualifiedName(final INamedElement element) {
		final INamedElement namedContainer = getNamedContainer(element);
		if(namedContainer != null) {
			return namedContainer.getQualifiedName() + QUALIFIED_NAME_DELIMITER + element.getName();
		}
		return element.getName();
	}

	private static INamedElement getNamedContainer(EObject object) {
		while (object != null) {
			object = object.eContainer();
			if (object instanceof INamedElement) {
				return (INamedElement) object;
			}
		}
		return null;
	}

	private NamedElementAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
