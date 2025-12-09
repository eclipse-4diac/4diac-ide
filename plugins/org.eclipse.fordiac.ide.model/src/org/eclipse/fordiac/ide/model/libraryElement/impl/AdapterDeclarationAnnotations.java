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

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public final class AdapterDeclarationAnnotations {

	static Stream<INamedElement> findBySimpleName(final AdapterDeclaration root, final String name) {
		final AdapterFB adapterFB = root.getInterfaceOnlyAdapterFB();
		if (adapterFB != null) {
			return Stream.concat(NamedElementAnnotations.findBySimpleName(root, name)
					.filter(Predicate.not(AdapterFB.class::isInstance)), adapterFB.findBySimpleName(name));
		}
		return NamedElementAnnotations.findBySimpleName(root, name);
	}

	private AdapterDeclarationAnnotations() {
		throw new UnsupportedOperationException();
	}
}
