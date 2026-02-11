/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class InitialValueTypedElementAccessor<T extends ITypedElement>
		implements InitialValueStructuredElementAccessor<T> {

	public static final InitialValueTypedElementAccessor<ITypedElement> INSTANCE = new InitialValueTypedElementAccessor<>();

	protected InitialValueTypedElementAccessor() {
	}

	@Override
	public LibraryElement getContext(final T element) {
		return EcoreUtil.getRootContainer(element) instanceof final LibraryElement libraryElement ? libraryElement
				: null;
	}

	@Override
	public LibraryElement getType(final T element) {
		if (element instanceof final VarDeclaration varDeclaration && varDeclaration.isArray()) {
			return TypeDeclarationParser.parseTypeDeclaration(varDeclaration.getType(),
					varDeclaration.getArraySizeString());
		}
		return element.getType();
	}

	@Override
	public ITypedElement getReferenceElement(final T element) {
		return element;
	}
}
