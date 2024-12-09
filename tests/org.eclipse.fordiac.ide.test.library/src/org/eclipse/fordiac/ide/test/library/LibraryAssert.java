/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class LibraryAssert {
	private LibraryAssert() {
	}

	public static void assertDependencyLinked(final IProject project, final Manifest manifest,
			final String symbolicName, final String version, final int index, final String folderName) {
		assertLibraryLinked(project, symbolicName, version, folderName);
		assertNotNull(manifest.getDependencies());
		assertNotNull(manifest.getDependencies().getRequired().get(index));
		assertEquals(symbolicName, manifest.getDependencies().getRequired().get(index).getSymbolicName());
		assertTrue(
				VersionComparator.contains(manifest.getDependencies().getRequired().get(index).getVersion(), version));
	}

	public static void assertLibraryLinked(final IProject project, final String symbolicName, final String version,
			final String folderName) {
		assertTrue(project.getFolder(folderName).getFolder(symbolicName).exists());
		assertNotNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project).find(symbolicName + "::Block")); //$NON-NLS-1$
		final var libManifest = ManifestHelper
				.getContainerManifest(project.getFolder(folderName).getFolder(symbolicName));
		assertNotNull(libManifest);
		assertEquals(version, libManifest.getProduct().getVersionInfo().getVersion());
	}
}
