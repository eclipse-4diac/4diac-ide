/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.HelperTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

final class LibraryElementAnnotations {

	static void setDocumentation(final LibraryElement type, final String value) {
		type.setAttribute(LibraryElementTags.DOCUMENTATION, HelperTypes.CDATA, value, null);
	}

	static String getDocumentation(final LibraryElement type) {
		final Attribute attribute = type.getAttribute(LibraryElementTags.DOCUMENTATION);
		return attribute != null ? attribute.getValue() : ""; //$NON-NLS-1$
	}

	private LibraryElementAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

}
