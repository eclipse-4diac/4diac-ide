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

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;

public final class AttributeAnnotations {
	private static final String NAMED_ELEMENTS_KEY = AttributeAnnotations.class.getName() + ".NAMED_ELEMENTS"; //$NON-NLS-1$

	public static String getQualifiedName(final Attribute attribute) {
		if (attribute.eContainer() instanceof final Connection connection) {
			return ConnectionAnnotations.getConnectionQualifiedName(connection)
					+ NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + attribute.getName();
		}
		return NamedElementAnnotations.getQualifiedName(attribute);
	}

	public static boolean validateAttributeDeclaration(final Attribute attribute, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (null != attribute.getAttributeDeclaration()
				&& attribute.getAttributeDeclaration().getTypeEntry() instanceof ErrorTypeEntry) {
			if (diagnostics != null) {
				diagnostics.add(createAttributeValidationDiagnostic(MessageFormat.format(
						Messages.AttributeAnnotations_MissingAttributeDeclaration, attribute.getName()), attribute));
			}
			return false;
		}
		return true;
	}

	private static Diagnostic createAttributeValidationDiagnostic(final String message, final Attribute attribute) {
		return new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
				LibraryElementValidator.ATTRIBUTE__VALIDATE_ATTRIBUTE_DECLARATION, message,
				FordiacMarkerHelper.getDiagnosticData(attribute,
						LibraryElementPackage.Literals.ATTRIBUTE__ATTRIBUTE_DECLARATION, attribute.getName()));
	}

	public static boolean validateName(final Attribute attribute, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (InternalAttributeDeclarations.isInternalAttribute(attribute.getAttributeDeclaration())) {
			return true;// do not validate internal attributes
		}
		return (attribute.getAttributeDeclaration() != null // do not validate name when a declaration is set
				|| NamedElementAnnotations.validateName(attribute, diagnostics, context))
				&& NamedElementAnnotations.validateDuplicateName(attribute, diagnostics, context, NAMED_ELEMENTS_KEY);
	}

	private AttributeAnnotations() {
		throw new UnsupportedOperationException();
	}
}
