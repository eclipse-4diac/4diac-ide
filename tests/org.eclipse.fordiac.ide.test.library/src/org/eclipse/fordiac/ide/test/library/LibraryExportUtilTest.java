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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.library.export.LibraryExportUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings({ "nls", "static-method" })
class LibraryExportUtilTest {

	private static IProject project;

	@BeforeAll
	static void setUpBeforeClass() throws CoreException, IOException {
		project = new FordiacProjectLoader(FrameworkUtil.getBundle(LibraryExportUtilTest.class),
				new org.eclipse.core.runtime.Path("data/LibraryExportUtilTest")).getEclipseProject();
	}

	@AfterAll
	static void tearDownAfterClass() throws CoreException {
		// the project is opened in place, so its files are kept
		project.delete(false, true, null);
	}

	@Test
	void testFunctionIsExportedAsFunctionWithoutBody() throws Exception {
		final TypeEntry entry = TypeLibraryManager.INSTANCE
				.getTypeEntryForFile(project.getFile("Type Library/ExportFunction.fct"));
		assertNotNull(entry);

		final Path destination = Files.createTempDirectory("LibraryExportUtilTest");
		try {
			LibraryExportUtil.exportTypeAsSIFB(entry, destination.toFile());

			assertFalse(Files.exists(destination.resolve("ExportFunction.fbt")), "function exported as FB type");
			final Path exported = destination.resolve("ExportFunction.fct");
			assertTrue(Files.exists(exported), "function not exported as function type");

			final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE
					.createResource(URI.createFileURI(exported.toString()));
			resource.load(Map.of(LibraryElementResource.OPTION_TYPE_ENTRY, entry));
			assertTrue(resource.getErrors().isEmpty(), "errors loading the exported function");
			final FunctionFBType function = assertInstanceOf(FunctionFBType.class, resource.getLibraryElement());
			assertNull(function.getBody(), "function body exported");
			assertEquals(List.of("IN1", "IN2"),
					function.getInterfaceList().getInputVars().stream().map(INamedElement::getName).toList());
		} finally {
			deleteTree(destination);
		}
	}

	private static void deleteTree(final Path root) throws IOException {
		try (Stream<Path> paths = Files.walk(root)) {
			for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
				Files.delete(path);
			}
		}
	}
}
