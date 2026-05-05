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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.model.impl;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.Dependencies;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public final class ManifestAnnotations {

	public static boolean validateManifestVersions(final Manifest manifest, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final var dependencies = manifest.getDependencies();

		if (diagnostics != null && dependencies != null) {
			boolean result = validateRequiredVersions(dependencies, diagnostics);
			if (manifest.getProduct() != null) {
				result &= validateProductVersion(manifest.getProduct(), diagnostics);
			}
			return result;
		}
		return true;
	}

	private static boolean validateRequiredVersions(final Dependencies dependencies,
			final DiagnosticChain diagnostics) {
		final var invalidDependencies = dependencies.getRequired().stream()
				.filter(r -> !VersionComparator.isValidRange(r.getVersion())).toList();
		if (invalidDependencies.isEmpty()) {
			return true;
		}
		invalidDependencies.forEach(r -> diagnostics.add(
				createVersionDiagnostic(getMessage(r), r, LibraryPackage.Literals.REQUIRED__VERSION, r.getVersion())));
		return false;
	}

	private static boolean validateProductVersion(final Product product, final DiagnosticChain diagnostics) {
		final var versionInfo = product.getVersionInfo();
		if (versionInfo != null && !VersionComparator.isValidRange(versionInfo.getVersion())) {
			diagnostics.add(createVersionDiagnostic(getMessage(product), versionInfo,
					LibraryPackage.Literals.VERSION_INFO__VERSION, versionInfo.getVersion()));
			return false;
		}
		return true;
	}

	private static Diagnostic createVersionDiagnostic(final String message, final EObject object,
			final EStructuralFeature feature, final String data) {
		return new BasicDiagnostic(Diagnostic.ERROR, LibraryValidator.DIAGNOSTIC_SOURCE,
				LibraryValidator.MANIFEST__VALIDATE_MANIFEST_VERSIONS, message,
				FordiacMarkerHelper.getDiagnosticData(object, feature, data));
	}

	private static String getMessage(final EObject object) {
		if (object instanceof final Required r) {
			return MessageFormat.format(Messages.VersionValidaton_DeclarationError, r.getVersion(),
					r.getSymbolicName());
		}
		if (object instanceof final Product product) {
			final String productName = Optional.ofNullable(product.getName())
					.or(() -> Optional.ofNullable(product.getSymbolicName())).orElse("Product"); //$NON-NLS-1$
			return MessageFormat.format(Messages.VersionValidaton_DeclarationError,
					product.getVersionInfo().getVersion(), productName);
		}
		return "Unknown"; //$NON-NLS-1$
	}

	private ManifestAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
