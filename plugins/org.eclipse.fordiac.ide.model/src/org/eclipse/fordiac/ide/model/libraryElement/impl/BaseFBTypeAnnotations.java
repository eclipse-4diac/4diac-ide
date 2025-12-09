/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;

final class BaseFBTypeAnnotations {

	private BaseFBTypeAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

	static EList<Algorithm> getAlgorithm(final BaseFBType fbType) {
		return new DelegatingEcoreEList.UnmodifiableEList<>((InternalEObject) fbType,
				LibraryElementPackage.eINSTANCE.getBaseFBType_Algorithm(),
				fbType.getCallables().stream().filter(Algorithm.class::isInstance).map(Algorithm.class::cast).toList());
	}

	static EList<Method> getMethods(final BaseFBType fbType) {
		return new DelegatingEcoreEList.UnmodifiableEList<>((InternalEObject) fbType,
				LibraryElementPackage.eINSTANCE.getBaseFBType_Methods(),
				fbType.getCallables().stream().filter(Method.class::isInstance).map(Method.class::cast).toList());
	}
}
