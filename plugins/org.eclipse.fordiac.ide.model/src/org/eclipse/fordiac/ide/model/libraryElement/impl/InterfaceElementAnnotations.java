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

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class InterfaceElementAnnotations {

	public static String getFullTypeName(final IInterfaceElement element) {
		return element.getTypeName();
	}

	public static String getFullTypeName(final VarDeclaration element) {
		final String typeName = element.getTypeName();
		if (element.isArray() && typeName != null && !typeName.isBlank()) {
			final String arraySize = getArraySize(element);
			if (!arraySize.contains("..")) { //$NON-NLS-1$
				try {
					return "ARRAY [0.." + (Integer.parseInt(arraySize) - 1) + "] OF " + typeName; //$NON-NLS-1$ //$NON-NLS-2$
				} catch (final NumberFormatException e) {
					// fallthrough
				}
			}
			return "ARRAY [" + arraySize + "] OF " + typeName; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return typeName;
	}

	private InterfaceElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
