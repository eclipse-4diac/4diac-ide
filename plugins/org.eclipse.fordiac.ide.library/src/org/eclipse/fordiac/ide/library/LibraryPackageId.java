/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.osgi.framework.Version;

/**
 * Identity of a library package: its symbolic name and version.
 *
 * @param symbolicName the symbolic name of the library
 * @param version      the version of the library
 */
public record LibraryPackageId(String symbolicName, Version version) {

	// SymbolicName in library.xsd
	private static final Pattern SYMBOLIC_NAME = Pattern.compile("[a-zA-Z][-_a-zA-Z0-9]*"); //$NON-NLS-1$
	// VersionSimple in library.xsd
	private static final Pattern VERSION = Pattern.compile("[0-9]+(?:\\.[0-9]+)*+"); //$NON-NLS-1$

	/**
	 * Creates the identity of a library package.
	 *
	 * @throws IllegalArgumentException if the symbolic name or version is invalid
	 * @throws NullPointerException     if the version is {@code null}
	 */
	public LibraryPackageId {
		if (!isValidSymbolicName(symbolicName)) {
			throw new IllegalArgumentException("Invalid symbolic name: " + symbolicName); //$NON-NLS-1$
		}
		if (!VERSION.matcher(Objects.requireNonNull(version).toString()).matches()) {
			throw new IllegalArgumentException("The library version must be numeric"); //$NON-NLS-1$
		}
	}

	/**
	 * Parses the identity of a library package, e.g. from the catalogue of a
	 * library source.
	 *
	 * @param symbolicName the symbolic name
	 * @param version      the version as text
	 * @return the identity, or an empty optional if name or version are invalid
	 */
	public static Optional<LibraryPackageId> parse(final String symbolicName, final String version) {
		if (!isValidSymbolicName(symbolicName) || version == null || !VERSION.matcher(version).matches()) {
			return Optional.empty();
		}
		try {
			return Optional.of(new LibraryPackageId(symbolicName, Version.valueOf(version)));
		} catch (final IllegalArgumentException e) {
			return Optional.empty();
		}
	}

	/**
	 * Returns whether a text is a valid symbolic name of a library.
	 *
	 * @param symbolicName the text to check
	 * @return {@code true} if the text is a valid symbolic name
	 */
	public static boolean isValidSymbolicName(final String symbolicName) {
		return symbolicName != null && SYMBOLIC_NAME.matcher(symbolicName).matches();
	}

	/**
	 * Returns {@code <symbolicName>-<version>}, the name of the library folder in
	 * the workspace and of the root folder in the library archive.
	 *
	 * @return the folder name
	 */
	public String folderName() {
		return symbolicName + '-' + version;
	}

	@Override
	public String toString() {
		return folderName();
	}
}
