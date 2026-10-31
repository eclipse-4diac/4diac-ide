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
package org.eclipse.fordiac.ide.test.model.dataimport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResourceFactory;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings({ "nls", "static-method" })
public class ImportExportTest {

	private static IProject project;
	private static IProject recoveryProject;

	@BeforeAll
	static void setUpBeforeClass() throws CoreException, IOException {
		final Bundle bundle = FrameworkUtil.getBundle(ImportExportTest.class);
		final FordiacProjectLoader loader = new FordiacProjectLoader(bundle, new Path("data/ImportExportTest"));
		project = loader.getEclipseProject();
		final FordiacProjectLoader recoveryLoader = new FordiacProjectLoader(bundle,
				new Path("data/ImportRecoveryTest"));
		recoveryProject = recoveryLoader.getEclipseProject();
	}

	static Stream<Named<IFile>> typeEntries() throws CoreException {
		final List<IFile> files = new ArrayList<>();
		project.accept(resource -> {
			if (resource.getName().startsWith(".")) {
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
	void testImportExport(final IFile file) throws IOException, CoreException {
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE.createResource(uri);
		resource.load(new HashMap<>());
		assertTrue(resource.getErrors().isEmpty(),
				file.getFullPath() + " errors: " + resource.getErrors().stream().map(Diagnostic::getMessage).toList());
		try (final ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			resource.save(stream, new HashMap<>());
			final String actual = new String(stream.toByteArray());
			final String expected = file.readString();
			assertEquals(expected, actual, "exported contents differ for " + file.getFullPath());
		}
	}

	@Test
	void testImporterRecovery() throws IOException {
		final IFile file = recoveryProject.getFolder("Type Library").getFile("RecoveryComposite.fbt");
		assertTrue(file.exists(), "recovery fixture exists");
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE.createResource(uri);
		resource.load(new HashMap<>());

		assertTrue(resource.getErrors().isEmpty(), "recovery fixture errors: " + resource.getErrors());
		assertEquals(2, resource.getWarnings().size());
		assertEquals(Set.of("x", "Retain (Type=USINT)"),
				resource.getWarnings().stream().map(Diagnostic::getLocation).collect(Collectors.toSet()));

		final CompositeFBType composite = (CompositeFBType) resource.getLibraryElement();
		assertNotNull(composite);
		assertNotNull(composite.getFBNetwork(), "recovery model content is available");
		final FB recoverable = composite.getFBNetwork().getFBNamed("Recoverable");
		final FB missingType = composite.getFBNetwork().getFBNamed("MissingType");

		assertNotNull(recoverable, "Recoverable FB instance is available");
		assertNotNull(missingType, "MissingType FB instance is available");

		assertEquals(0.0, recoverable.getPosition().getX(), 0.0);
		assertNotNull(missingType.getTypeEntry(), "missing type has a type entry");
		assertTrue(missingType.getTypeEntry().hasError(), "missing type is represented by an error type entry");
		assertTrue(
				recoverable.getInterface().getErrorMarker().stream()
						.anyMatch(marker -> "UnknownParameter".equals(marker.getName())),
				"unknown parameter is represented by an error marker interface");

		final var input = recoverable.getInterface().getInputVars().get(0);
		assertEquals("legacy input comment", input.getComment());
		assertFalse(input.isVisible());
		assertTrue(input.isVarConfig());
	}

	@Test
	void testAdapterDeclarationAttributes() throws IOException {
		final IFile file = project.getFolder("Type Library").getFile("BasicTest.fbt");
		assertTrue(file.exists(), "fixture exists");
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final LibraryElementResource resource = FordiacTypeResourceFactory.INSTANCE.createResource(uri);
		resource.load(new HashMap<>());
		assertTrue(resource.getErrors().isEmpty());

		final FBType fbType = (FBType) resource.getLibraryElement();
		assertNotNull(fbType);
		final AdapterDeclaration adapterDecl = fbType.getInterfaceList().getPlugs().get(0);
		assertNotNull(adapterDecl);
		assertEquals(1, adapterDecl.getAttributes().size());
		assertEquals("AdapterAttribute", adapterDecl.getAttributes().get(0).getName());
		assertEquals("AdapterValue", adapterDecl.getAttributes().get(0).getValue());
		assertEquals(0, adapterDecl.getAdapterFB().getAttributes().size());
	}
}
