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

import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;

public class STMatcher implements IModelMatcher {

	private final Predicate<String> predicate;

	public STMatcher(final Predicate<String> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean matches(final EObject object) {
		if (object instanceof final STFeatureExpression featureExpression) {
			return predicate.test(featureExpression.getFeature().getName());
		}
		if (object instanceof final INamedElement element) {
			return predicate.test(element.getName());
		}
		return false;
	}
}
