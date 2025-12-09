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

/**
 * A matcher for model elements
 */
public interface IModelMatcher {

	/**
	 * Test whether the object matches this matcher
	 *
	 * @param object The object to match
	 * @return true if the object matches, false otherwise
	 */
	boolean matches(EObject object);
}
