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
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.eclipse.fordiac.ide.library.LibraryPackageId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.osgi.framework.Version;

/** Checks the rules of {@code library.xsd} for the identity of a library package. */
@SuppressWarnings("static-method")
class LibraryPackageIdTest {

	@Test
	void parsesNameAndVersionOfManifest() {
		final LibraryPackageId id = LibraryPackageId.parse("iec61131-3", "3.0").orElseThrow(); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals("iec61131-3", id.symbolicName()); //$NON-NLS-1$
		assertEquals(new Version(3, 0, 0), id.version());
		assertEquals("iec61131-3-3.0.0", id.folderName()); //$NON-NLS-1$
	}

	@ParameterizedTest
	@CsvSource({ "1motor, 1.0.0", "motor.ext, 1.0.0", "motor/x, 1.0.0", "'', 1.0.0", "motor, ''", "motor, 1.0.0.beta",
			"motor, 1.0-2.0", "motor, v1.0" })
	void rejectsWhatTheManifestSchemaRejects(final String symbolicName, final String version) {
		assertEquals(Optional.empty(), LibraryPackageId.parse(symbolicName, version));
	}

	@Test
	void rejectsVersionWithTooManyComponents() {
		final String version = "1" + ".0".repeat(16_384); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(LibraryPackageId.parse("motor", version).isEmpty()); //$NON-NLS-1$
	}

	@Test
	void rejectsNonNumericQualifierInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new LibraryPackageId("motor", new Version(1, 0, 0, "beta"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void rejectsMissingValues() {
		assertTrue(LibraryPackageId.parse(null, "1.0.0").isEmpty()); //$NON-NLS-1$
		assertTrue(LibraryPackageId.parse("motor", null).isEmpty()); //$NON-NLS-1$
		assertThrows(IllegalArgumentException.class, () -> new LibraryPackageId("1motor", Version.emptyVersion)); //$NON-NLS-1$
		assertThrows(NullPointerException.class, () -> new LibraryPackageId("motor", null)); //$NON-NLS-1$
	}
}
