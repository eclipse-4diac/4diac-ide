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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.library.IArchiveDownloader;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

class LibraryImportTest {
	private static String PROJECT = "testproject"; //$NON-NLS-1$
	private IProject project;
	@SuppressWarnings("nls")
	public static final List<String> archives = List.of("data/test01-1.0.0.zip", "data/test01-1.1.0.zip",
			"data/test01-1.5.0.zip", "data/test01-2.0.0.zip", "data/test02-1.0.0.zip", "data/test02-1.1.0.zip",
			"data/test03-1.0.0.zip", "data/test03-1.1.0.zip", "data/test04-1.0.0.zip", "data/test04-1.1.0.zip",
			"data/test05-1.0.0.zip", "data/test06-1.0.0.zip", "data/test07-1.0.0.zip");

	@BeforeAll
	static void setupBeforeClass() throws Exception {
		final IProject proj = SystemManager.INSTANCE.createNew4diacProject(PROJECT,
				ResourcesPlugin.getWorkspace().getRoot().getLocation().append(PROJECT), Collections.emptyMap(),
				new NullProgressMonitor());
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);

		// extract test libraries

		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.library"); //$NON-NLS-1$
		for (final var archive : archives) {
			final URL url = FileLocator.toFileURL(FileLocator.find(bundle, new Path(archive)));
			LibraryManager.INSTANCE.extractLibrary(Paths.get(url.toURI()), null, false, false);
		}

		// deactivate other downloaders
		TypeLibraryManager.useExtensions(LibraryManager.DOWNLOADER_EXTENSION, IArchiveDownloader.class, downloader -> {
			downloader.setActive(downloader instanceof MockDownloader);
		});
	}

	@BeforeEach
	void setupBeforeEach() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
	}

	@AfterEach
	void cleanupAfterEach() throws Exception {
		final var manifest = ManifestHelper.getContainerManifest(project);
		if (manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().clear();
			ManifestHelper.saveManifest(manifest);
		}
		FordiacMarkerHelper.updateMarkers(project.getFile(LibraryManager.MANIFEST), FordiacErrorMarker.LIBRARY_MARKER,
				Collections.emptyList(), true);
		project.getFolder(LibraryManager.TYPE_LIB_FOLDER_NAME).accept(resource -> {
			if (resource instanceof final IFolder folder) {
				if (folder.getName().equals(LibraryManager.TYPE_LIB_FOLDER_NAME)) {
					return true;
				}
				folder.delete(false, null);
			}
			return false;
		});
	}

	@Test
	void testSimpleImport() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test01-1.0.0"), //$NON-NLS-1$
				true, false);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test01", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testImportUpdateNoOverwrite() {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired("test01", "[1.0.0-2.0.0)")); //$NON-NLS-1$ //$NON-NLS-2$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test01-1.1.0"), //$NON-NLS-1$
				true, false);

		manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test01", "1.1.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals("[1.0.0-2.0.0)", manifest.getDependencies().getRequired().get(0).getVersion()); //$NON-NLS-1$
	}

	@Test
	void testImportUpdateOverwrite() {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired("test01", "[1.0.0-2.0.0)")); //$NON-NLS-1$ //$NON-NLS-2$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test01-2.0.0"), //$NON-NLS-1$
				true, false);

		manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test01", "2.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals("2.0.0", manifest.getDependencies().getRequired().get(0).getVersion()); //$NON-NLS-1$
	}

	@Test
	void testCheckManifest() throws Exception {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired("test01", "[1.0.0-2.0.0)")); //$NON-NLS-1$ //$NON-NLS-2$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.checkManifestFile(project, TypeLibraryManager.INSTANCE.getTypeLibrary(project));
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test01", "1.5.0", 0); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testImportWithoutResolve() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test02-1.0.0"), //$NON-NLS-1$
				true, false);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test02", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testImportWithResolve() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test02-1.0.0"), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.resolveDependencies(project, TypeLibraryManager.INSTANCE.getTypeLibrary(project));

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test02", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(1, manifest.getDependencies().getRequired().size());
		libraryAssert("test01", "1.0.0"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testImportWithRangeResolve() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test02-1.1.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test02", "1.1.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(1, manifest.getDependencies().getRequired().size());
		libraryAssert("test01", "1.5.0"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testRangeOverlap() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test03-1.0.0"), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test04-1.0.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test03", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		dependencyAssert(manifest, "test04", "1.0.0", 1); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(2, manifest.getDependencies().getRequired().size());
		libraryAssert("test01", "1.1.0"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testRangeOverlap2() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test03-1.1.0"), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test04-1.0.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test03", "1.1.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		dependencyAssert(manifest, "test04", "1.0.0", 1); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(2, manifest.getDependencies().getRequired().size());
		libraryAssert("test01", "1.5.0"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testRangeOverlapError() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test03-1.1.0"), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test04-1.1.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test03", "1.1.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		dependencyAssert(manifest, "test04", "1.1.0", 1); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(2, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(LibraryManager.TYPE_LIB_FOLDER_NAME).getFolder("test01").exists()); //$NON-NLS-1$

		LibraryManager.INSTANCE.getJobGroup().join(1000, null);
		FordiacMarkerHelper.JOB_GROUP.join(1000, null);

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertTrue(markers.length > 0);
		// markers[2].getAttribute(IMarker.MESSAGE);
	}

	@Test
	void testDependencyChain() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test06-1.0.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test06", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(1, manifest.getDependencies().getRequired().size());

		libraryAssert("test05", "1.0.0"); //$NON-NLS-1$//$NON-NLS-2$
		libraryAssert("test02", "1.0.0"); //$NON-NLS-1$//$NON-NLS-2$
		libraryAssert("test01", "1.0.0"); //$NON-NLS-1$//$NON-NLS-2$
	}

	@Test
	void testMissingStandardLibrary() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create("WORKSPACE_LOC/.lib/test07-1.0.0"), //$NON-NLS-1$
				true, true);
		LibraryManager.INSTANCE.getJobGroup().join(5000, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		dependencyAssert(manifest, "test07", "1.0.0", 0); //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(1, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(LibraryManager.TYPE_LIB_FOLDER_NAME).getFolder("math").exists()); //$NON-NLS-1$

		LibraryManager.INSTANCE.getJobGroup().join(1000, null);
		FordiacMarkerHelper.JOB_GROUP.join(1000, null);

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertTrue(markers.length > 0);
	}

	private void dependencyAssert(final Manifest manifest, final String symbolicName, final String version,
			final int index) {
		libraryAssert(symbolicName, version);
		assertNotNull(manifest.getDependencies());
		assertNotNull(manifest.getDependencies().getRequired().get(index));
		assertNotNull(symbolicName, manifest.getDependencies().getRequired().get(index).getSymbolicName());
		assertTrue(
				VersionComparator.contains(manifest.getDependencies().getRequired().get(index).getVersion(), version));
	}

	private void libraryAssert(final String symbolicName, final String version) {
		assertTrue(project.getFolder(LibraryManager.TYPE_LIB_FOLDER_NAME).getFolder(symbolicName).exists());
		assertNotNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project).find(symbolicName + "::Block")); //$NON-NLS-1$
		final var libManifest = ManifestHelper
				.getContainerManifest(project.getFolder(LibraryManager.TYPE_LIB_FOLDER_NAME).getFolder(symbolicName));
		assertNotNull(libManifest);
		assertEquals(version, libManifest.getProduct().getVersionInfo().getVersion());
	}

}
