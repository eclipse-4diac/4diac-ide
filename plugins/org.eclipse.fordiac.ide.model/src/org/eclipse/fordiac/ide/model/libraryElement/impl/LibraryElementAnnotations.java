/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.HelperTypes;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

final class LibraryElementAnnotations {

	static void setDocumentation(final LibraryElement type, final String value) {
		type.setAttribute(LibraryElementTags.DOCUMENTATION, HelperTypes.CDATA, value, null);
	}

	static String getDocumentation(final LibraryElement type) {
		final Attribute attribute = type.getAttribute(LibraryElementTags.DOCUMENTATION);
		return attribute != null ? attribute.getValue() : ""; //$NON-NLS-1$
	}

	public static boolean validateName(final LibraryElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		boolean isValid = true;
		if (element.eContainer() == null && element.getTypeEntry() != null
				&& element.getTypeEntry().getFile() != null) {
			final TypeEntry entry = element.getTypeEntry();
			final String fileName = TypeEntry.getTypeNameFromFile(entry.getFile());

			if (!fileName.equals(entry.getTypeName())) {
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.LIBRARY_ELEMENT__VALIDATE_NAME,
							MessageFormat.format(Messages.IdentifierVerifier_NotMatchingWithFilename,
									entry.getTypeName(), entry.getFile().getName()),
							FordiacMarkerHelper.getDiagnosticData(element,
									LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
				}
				isValid = false;
			}
		}
		return NamedElementAnnotations.validateName(element, diagnostics, context) && isValid;
	}

	public static boolean validatePackage(final LibraryElement element, final DiagnosticChain diagnostics) {
		if (element.eContainer() == null && element.getTypeEntry() != null) {
			final TypeEntry typeEntry = element.getTypeEntry();

			if (!Objects.equals(typeEntry.getPackageName(), getExpectedPackageName(typeEntry))) {
				if (diagnostics != null) {
					final int severity = ValidationPreferences.getDiagnosticSeverity(
							ValidationPreferences.PACKAGENAME_MISMATCH_FOLDER, Diagnostic.OK, element.eResource());
					diagnostics.add(new BasicDiagnostic(severity, LibraryElementValidator.DIAGNOSTIC_SOURCE,
							LibraryElementValidator.LIBRARY_ELEMENT__VALIDATE_PACKAGE,
							Messages.IdentifierVerifier_PackageNameMismatch, FordiacMarkerHelper.getDiagnosticData(
									element, LibraryElementPackage.Literals.COMPILER_INFO__PACKAGE_NAME)));
				}
				return false;
			}
		}
		return true;
	}

	private static String getExpectedPackageName(final TypeEntry entry) {
		final IPath relativePath = BuildpathUtil
				.findRelativePath(entry.getTypeLibrary().getBuildpath(), entry.getFile().getParent())
				.orElse(entry.getFile().getParent().getFullPath());
		return Stream.of(relativePath.segments()).collect(Collectors.joining(PackageNameHelper.PACKAGE_NAME_DELIMITER));
	}

	private LibraryElementAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

}
