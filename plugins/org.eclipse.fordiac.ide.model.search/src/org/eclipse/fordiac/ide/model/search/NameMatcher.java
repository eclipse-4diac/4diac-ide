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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * A matcher for the name of an object.
 */
public class NameMatcher implements IModelMatcher {

	private final String name;

	/**
	 * Create a new name matcher
	 *
	 * @param name The name to match
	 */
	public NameMatcher(final String name) {
		this.name = name;
	}

	@Override
	public boolean matches(final EObject object) {
		if (object instanceof final INamedElement element) {
			return element.getName().contains(name);
		}
		return false;
	}
}
