/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class OverrideAttributeAnnotations {
	private OverrideAttributeAnnotations() {
		throw new UnsupportedOperationException();
	}

	public static String getFullName(final OverrideAttribute attribute) {
		return attribute.getLocation() + NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + attribute.getName();
	}

	public static String getQualifiedName(final OverrideAttribute attribute) {
		final TypedSubApp container = (TypedSubApp) attribute.eContainer();
		return container.getQualifiedName() + NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + attribute.getLocation()
				+ NamedElementAnnotations.QUALIFIED_NAME_DELIMITER + attribute.getName();
	}

	public static boolean validateLocation(final OverrideAttribute attribute, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		Optional<String> err = Optional.empty();

		if (attribute.eContainer() instanceof final TypedSubApp tsa) {
			final Stream<INamedElement> element = tsa.findByQualifiedName(attribute.getLocation());
			if (element.toList().isEmpty()) {
				err = Optional.of(MessageFormat.format(Messages.OverrideAttributeVerifier_InvalidPath,
						attribute.getLocation(), attribute.getName()));
			}
		}

		if (err.isPresent()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.OVERRIDE_ATTRIBUTE__VALIDATE_LOCATION, err.get(),
						FordiacMarkerHelper.getDiagnosticData(attribute,
								LibraryElementPackage.Literals.OVERRIDE_ATTRIBUTE__LOCATION)));
			}
			return false;
		}
		return true;
	}
}