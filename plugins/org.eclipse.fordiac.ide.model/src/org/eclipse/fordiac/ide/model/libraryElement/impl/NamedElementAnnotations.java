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

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class NamedElementAnnotations {
	private static final String QUALIFIED_NAME_DELIMITER = "."; //$NON-NLS-1$

	/** Do not call directly! Use {@link INamedElement#getQualifiedName()} instead.
	 *
	 * Must be accessible from derived models. */
	public static String getQualifiedName(final INamedElement element) {
		final INamedElement namedContainer = getNamedContainer(element);
		if (namedContainer != null) {
			return namedContainer.getQualifiedName() + QUALIFIED_NAME_DELIMITER + element.getName();
		}
		return element.getName();
	}

	static INamedElement getNamedContainer(EObject object) {
		while (object != null) {
			object = object.eContainer();
			if (object instanceof final INamedElement element) {
				return element;
			}
		}
		return null;
	}

	public static boolean validateName(final INamedElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getName() != null && !element.getName().isEmpty()) {
			final Optional<String> errorMessage = IdentifierVerifier.verifyIdentifier(element.getName(), element);
			if (errorMessage.isPresent()) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.INAMED_ELEMENT__VALIDATE_NAME, errorMessage.get(),
							FordiacMarkerHelper.getDiagnosticData(element)));
				}
				return false;
			}
		}
		return true;
	}

	private NamedElementAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
