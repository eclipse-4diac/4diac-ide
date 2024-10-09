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
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class ArraySizeHelper {

	public static String getArraySize(final IInterfaceElement variable) {
		if (variable instanceof final VarDeclaration decl && decl.getArraySize() != null) {
			return decl.getArraySize().getValue();
		}
		return ""; //$NON-NLS-1$
	}

	public static void setArraySize(final VarDeclaration variable, final String arraySize) {
		if (arraySize == null || arraySize.isBlank()) {
			variable.setArraySize(null);
		} else {
			if (variable.getArraySize() == null) {
				variable.setArraySize(LibraryElementFactory.eINSTANCE.createArraySize());
			}
			variable.getArraySize().setValue(arraySize);
		}
	}

	private ArraySizeHelper() {
		throw new UnsupportedOperationException();
	}
}
