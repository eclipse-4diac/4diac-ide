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
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class ConfigurableFBAnnotations {

	public static boolean validateDataType(final ConfigurableFB element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getDataType() == null) {
			if (diagnostics != null) {
				diagnostics.add(createTypeValidationDiagnostic(Messages.TypedElementAnnotations_TypeNotSet, element));
			}
			return false;
		}
		if (element.getDataType() instanceof ErrorMarkerDataType) {
			if (diagnostics != null) {
				diagnostics.add(createTypeValidationDiagnostic(
						MessageFormat.format(Messages.TypedElementAnnotations_TypeNotFound, getFullTypeName(element)),
						element));
			}
			return false;
		}
		return true;
	}

	private static Diagnostic createTypeValidationDiagnostic(final String message, final ConfigurableFB element) {
		return new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
				LibraryElementValidator.CONFIGURABLE_FB__VALIDATE_DATA_TYPE, message,
				FordiacMarkerHelper.getDiagnosticData(element,
						LibraryElementPackage.Literals.CONFIGURABLE_FB__DATA_TYPE, getFullTypeName(element)));
	}

	private static String getFullTypeName(final ConfigurableFB element) {
		return PackageNameHelper.getFullTypeName(element.getDataType());
	}

	private ConfigurableFBAnnotations() {
		throw new UnsupportedOperationException();
	}
}
