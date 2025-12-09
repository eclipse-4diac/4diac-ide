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

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;

public final class TypedElementAnnotations {

	public static boolean validateType(final ITypedElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getType() == null) {
			if (diagnostics != null) {
				diagnostics.add(createTypeValidationDiagnostic(Messages.TypedElementAnnotations_TypeNotSet, element));
			}
			return false;
		}
		if (element.getType() instanceof final LibraryElement libraryElement
				&& libraryElement.getTypeEntry() instanceof ErrorTypeEntry) {
			if (diagnostics != null) {
				diagnostics.add(createTypeValidationDiagnostic(
						MessageFormat.format(Messages.TypedElementAnnotations_TypeNotFound, getFullTypeName(element)),
						element));
			}
			return false;
		}
		return true;
	}

	private static Diagnostic createTypeValidationDiagnostic(final String message, final ITypedElement element) {
		return new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
				LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE, message,
				FordiacMarkerHelper.getDiagnosticData(element, getTypeFeature(element), getFullTypeName(element)));
	}

	private static String getFullTypeName(final ITypedElement element) {
		if (element.getType() instanceof final LibraryElement libraryElement) {
			return PackageNameHelper.getFullTypeName(libraryElement);
		}
		return element.getTypeName();
	}

	private static EStructuralFeature getTypeFeature(final ITypedElement element) {
		final EStructuralFeature feature = element.eClass().getEStructuralFeature("type"); //$NON-NLS-1$
		if (feature != null && !feature.isMany() && feature.getEType() instanceof final EClass featureClass
				&& LibraryElementPackage.Literals.INAMED_ELEMENT.isSuperTypeOf(featureClass)) {
			return feature;
		}
		return null;
	}

	private TypedElementAnnotations() {
		throw new UnsupportedOperationException();
	}
}
