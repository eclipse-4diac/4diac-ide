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

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.DelegatingEcoreEList;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

final class BaseFBTypeAnnotations {

	private static final Set<EClass> ALLOWED_INTERNAL_FB_TYPE_CLASSES = Set.of(
			LibraryElementPackage.Literals.BASIC_FB_TYPE, //
			LibraryElementPackage.Literals.SIMPLE_FB_TYPE, //
			LibraryElementPackage.Literals.SERVICE_INTERFACE_FB_TYPE, //
			LibraryElementPackage.Literals.ERROR_FB_TYPE // also allow error FB types to avoid follow-up errors
	);

	private BaseFBTypeAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

	static EList<Algorithm> getAlgorithm(final BaseFBType fbType) {
		return new DelegatingEcoreEList.UnmodifiableEList<>((InternalEObject) fbType,
				LibraryElementPackage.eINSTANCE.getBaseFBType_Algorithm(), fbType.getSourceElements().stream()
						.filter(Algorithm.class::isInstance).map(Algorithm.class::cast).toList());
	}

	static EList<Method> getMethods(final BaseFBType fbType) {
		return new DelegatingEcoreEList.UnmodifiableEList<>((InternalEObject) fbType,
				LibraryElementPackage.eINSTANCE.getBaseFBType_Methods(),
				fbType.getSourceElements().stream().filter(Method.class::isInstance).map(Method.class::cast).toList());
	}

	static EList<ICallable> getCallables(final BaseFBType fbType) {
		return new DelegatingEcoreEList.UnmodifiableEList<>((InternalEObject) fbType,
				LibraryElementPackage.eINSTANCE.getBaseFBType_Callables(), fbType.getSourceElements().stream()
						.filter(ICallable.class::isInstance).map(ICallable.class::cast).toList());
	}

	static boolean validateInternalFBs(final BaseFBType fbType, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		for (final FB internalFB : fbType.getInternalFbs()) {
			final EClass internalFBTypeClass = internalFB.getTypeEntry().getTypeEClass();
			if (!ALLOWED_INTERNAL_FB_TYPE_CLASSES.contains(internalFBTypeClass)) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.BASE_FB_TYPE__VALIDATE_INTERNAL_FBS,
							MessageFormat.format(Messages.BaseFBTypeAnnotations_UnsupportedInternalFBType,
									internalFB.getFullTypeName(), internalFB.getName()),
							FordiacMarkerHelper.getDiagnosticData(internalFB,
									LibraryElementPackage.Literals.TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY)));
				}
				return false;
			}
		}
		return true;
	}
}
