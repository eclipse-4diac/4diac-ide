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
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;

public final class TypedConfigureableObjectAnnotations {

	public static boolean validateType(final TypedConfigureableObject element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getTypeEntry() instanceof ErrorTypeEntry) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.TYPED_CONFIGUREABLE_OBJECT__VALIDATE_TYPE,
						MessageFormat.format(Messages.TypedElementAnnotations_TypeNotFound,
								element.getTypeEntry().getFullTypeName()),
						FordiacMarkerHelper.getDiagnosticData(element,
								LibraryElementPackage.Literals.TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY,
								element.getTypeEntry().getFullTypeName())));
			}
			return false;
		}
		return true;
	}

	private TypedConfigureableObjectAnnotations() {
		throw new UnsupportedOperationException();
	}
}
