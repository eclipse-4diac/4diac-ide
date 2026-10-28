/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.test.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;
import org.eclipse.fordiac.ide.model.util.LibraryElementHasher;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings({ "nls", "static-method" })
public class LibraryElementHasherTest {

	private static final String EXPECTED_TYPE_HASH_FILE_NAME = "ExpectedTypeHash.atp";
	private static final String EXPECTED_TYPE_HASH_ATTRIBUTE = "eclipse4diac::test::ExpectedTypeHash";

	private static IProject project;

	@BeforeAll
	static void setUpBeforeClass() throws CoreException, IOException {
		final Bundle bundle = FrameworkUtil.getBundle(LibraryElementHasherTest.class);
		final FordiacProjectLoader loader = new FordiacProjectLoader(bundle, new Path("data/LibraryElementHasherTest"));
		project = loader.getEclipseProject();
	}

	static Stream<Named<IFile>> typeEntries() throws CoreException {
		final List<IFile> files = new ArrayList<>();
		project.accept(resource -> {
			if (resource.getName().startsWith(".") || resource.getName().equals(EXPECTED_TYPE_HASH_FILE_NAME)) {
				return false;
			}
			if (resource instanceof final IFile file) {
				files.add(file);
				return false;
			}
			return true;
		});
		assertFalse(files.isEmpty(), "No fixtures found");
		return files.stream().map(file -> Named.named(file.getFullPath().toString(), file));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("typeEntries")
	void testHash(final IFile file) throws IOException, LibraryElementHashException {
		final LibraryElement libraryElement = loadLibraryElement(file);
		final String expectedValue = libraryElement.getAttributeValue(EXPECTED_TYPE_HASH_ATTRIBUTE);
		assertNotNull(expectedValue, EXPECTED_TYPE_HASH_ATTRIBUTE + " exists for " + file.getFullPath());

		final String expected = StringValueConverter.INSTANCE.toValue(expectedValue);

		final String actual = LibraryElementHasher.hash(libraryElement);
		assertEquals(expected, actual, "hash differs for " + file.getFullPath());

		final String actualRehashed = LibraryElementHasher.hash(libraryElement);
		assertEquals(expected, actualRehashed, "rehashed hash differs for " + file.getFullPath());

		final LibraryElement libraryElementReloaded = loadLibraryElement(file);
		final String actualReloaded = LibraryElementHasher.hash(libraryElementReloaded);
		assertEquals(expected, actualReloaded, "reloaded hash differs for " + file.getFullPath());
	}

	@Test
	void testHashInvalidParameters() throws IOException {
		final IFile file = project.getFile(new Path("Type Library/BasicHashTest.fbt"));
		final LibraryElement libraryElement = loadLibraryElement(file);
		assertThrows(LibraryElementHashException.class,
				() -> LibraryElementHasher.hash(libraryElement, "v999", LibraryElementHasher.DEFAULT_HASH_ALGORITHM));
		assertThrows(LibraryElementHashException.class, () -> LibraryElementHasher.hash(libraryElement,
				LibraryElementHasher.CURRENT_HASH_VERSION, "no-such-algorithm"));
	}

	private LibraryElement loadLibraryElement(final IFile file) throws IOException {
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE.createResource(uri);
		resource.load(new HashMap<>());
		assertTrue(resource.getErrors().isEmpty(),
				file.getFullPath() + " errors: " + resource.getErrors().stream().map(Diagnostic::getMessage).toList());
		assertNotNull(resource.getLibraryElement(), "library element is non-null for " + file.getFullPath());
		return resource.getLibraryElement();
	}
}
