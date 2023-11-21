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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public final class FBNetworkElementAnnotations {
	private static final String NAMED_ELEMENTS_KEY = FBNetworkElementAnnotations.class.getName() + ".NAMED_ELEMENTS"; //$NON-NLS-1$

	public static boolean validateName(final FBNetworkElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (isErrorMarkerOrComment(element)) {
			return true; // do not check error markers or comments
		}
		return NamedElementAnnotations.validateName(element, diagnostics, context)
				&& NamedElementAnnotations.validateDuplicateName(element, diagnostics, context, NAMED_ELEMENTS_KEY);
	}

	static boolean isErrorMarkerOrComment(final FBNetworkElement element) {
		return element instanceof ErrorMarkerFBNElement || element instanceof Comment;
	}

	private FBNetworkElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
