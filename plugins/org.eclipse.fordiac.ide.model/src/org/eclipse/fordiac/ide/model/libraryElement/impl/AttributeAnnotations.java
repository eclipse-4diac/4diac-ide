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

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;

public final class AttributeAnnotations {
	private static final String NAMED_ELEMENTS_KEY = AttributeAnnotations.class.getName() + ".NAMED_ELEMENTS"; //$NON-NLS-1$

	public static String getQualifiedName(final Attribute attribute) {
		if (attribute.eContainer() instanceof final Connection connection) {
			return ConnectionAnnotations.getConnectionQualifiedName(connection)
					+ NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + attribute.getName();
		}
		return NamedElementAnnotations.getQualifiedName(attribute);
	}

	public static boolean validateName(final Attribute attribute, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (InternalAttributeDeclarations.isInternalAttribue(attribute.getAttributeDeclaration())) {
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
