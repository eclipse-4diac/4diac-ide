/*******************************************************************************
 * Copyright (c) 2026 MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tim Grocki - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class EsiIoLibraryImportTest {

	private final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("esiImport");

	@Test
	void requestsIoStandardLibrary() {
		final List<String> requested = new ArrayList<>();

		assertTrue(EsiFileImporter.importIoLibrary(project, (target, symbolicName) -> {
			assertSame(project, target);
			requested.add(symbolicName);
			return true;
		}));

		assertEquals(List.of("io"), requested);
	}

	@Test
	void reportsFailedImport() {
		assertFalse(EsiFileImporter.importIoLibrary(project, (_, _) -> false));
	}
}
