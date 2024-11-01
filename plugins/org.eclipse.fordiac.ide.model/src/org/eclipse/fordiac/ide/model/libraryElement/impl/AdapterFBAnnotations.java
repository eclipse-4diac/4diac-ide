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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;

public final class AdapterFBAnnotations {

	static String getQualifiedName(final AdapterFB element) {
		if (element.eContainer() instanceof final AdapterDeclaration declaration) {
			return declaration.getQualifiedName(); // skip double name for declaration and adapter FB
		}
		return FBNetworkElementAnnotations.getQualifiedName(element);
	}

	private AdapterFBAnnotations() {
		throw new UnsupportedOperationException();
	}
}
