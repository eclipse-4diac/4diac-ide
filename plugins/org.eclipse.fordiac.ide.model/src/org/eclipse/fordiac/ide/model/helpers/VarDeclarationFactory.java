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
 *   Alois Zoitl - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class VarDeclarationFactory {

	public static VarDeclaration createVarDecl(final DataType type) {
		return switch (type) {
		case final StructuredType structType -> LibraryElementFactory.eINSTANCE.createContainerVarDeclaration();
		case final ErrorDataType errorType -> LibraryElementFactory.eINSTANCE.createContainerVarDeclaration();
		default -> LibraryElementFactory.eINSTANCE.createVarDeclaration();

		};
	}

	private VarDeclarationFactory() {
		throw new UnsupportedOperationException();
	}

}
