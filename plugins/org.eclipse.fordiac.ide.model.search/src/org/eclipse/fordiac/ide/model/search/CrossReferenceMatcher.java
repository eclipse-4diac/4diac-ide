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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A matcher for outgoing cross references from an object.
 */
public class CrossReferenceMatcher implements IModelMatcher {

	private final URI reference;

	/**
	 * Create a new cross-reference matcher
	 *
	 * @param reference The reference URI
	 */
	public CrossReferenceMatcher(final URI reference) {
		this.reference = toExternalURI(reference);
	}

	/**
	 * Create a new cross-reference matcher
	 *
	 * @param object The referenced object
	 */
	public CrossReferenceMatcher(final EObject object) {
		this(EcoreUtil.getURI(object));
	}

	@Override
	public boolean matches(final EObject object) {
		return object.eCrossReferences().stream().map(CrossReferenceMatcher::getExternalURI)
				.anyMatch(uri -> uri.equals(reference));
	}

	private static URI getExternalURI(final EObject object) {
		return toExternalURI(EcoreUtil.getURI(object));
	}

	private static URI toExternalURI(final URI uri) {
		if (uri != null && uri.hasFragment() && uri.fragment().startsWith("/1")) { //$NON-NLS-1$
			return uri.trimQuery().trimFragment().appendFragment("/" + uri.fragment().substring(2)); //$NON-NLS-1$
		}
		return uri;
	}
}
