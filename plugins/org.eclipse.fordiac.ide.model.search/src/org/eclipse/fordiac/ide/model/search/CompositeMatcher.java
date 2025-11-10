/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class CompositeMatcher implements IModelMatcher {

	private final List<IModelMatcher> matchers;

	public CompositeMatcher(final List<IModelMatcher> matchers) {
		this.matchers = matchers;
	}

	@Override
	public boolean matches(final EObject object) {
		return matchers.stream().anyMatch(matcher -> matcher.matches(object));
	}
}
