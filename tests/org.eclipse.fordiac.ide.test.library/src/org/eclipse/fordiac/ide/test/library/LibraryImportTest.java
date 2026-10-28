/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH, Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - restore the test state after the tests
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryMarkerFactory;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.ProjectCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryImportTest {
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String TEST03 = "test03"; //$NON-NLS-1$
	private static final String TEST04 = "test04"; //$NON-NLS-1$
	private static final String TEST05 = "test05"; //$NON-NLS-1$
	private static final String TEST06 = "test06"; //$NON-NLS-1$
	private static final String TEST07 = "test07"; //$NON-NLS-1$
	private static final String MATH = "math"; //$NON-NLS-1$
	private static final String V1_0_0 = "1.0.0"; //$NON-NLS-1$
	private static final String V1_1_0 = "1.1.0"; //$NON-NLS-1$
	private static final String V1_5_0 = "1.5.0"; //$NON-NLS-1$
	private static final String V2_0_0 = "2.0.0"; //$NON-NLS-1$
	private static final String LIB_LOC = "WORKSPACE_LOC/.lib/"; //$NON-NLS-1$
	private static final String PROJECT = "testproject"; //$NON-NLS-1$

	private static boolean previousMockDownloaderActive;

	private IProject project;

	@BeforeAll
	static void setupBeforeClass() throws Exception {
		previousMockDownloaderActive = LibraryTestFixtures.isMockDownloaderActive();
		// the library builder of the automatic build resolves the dependencies
		assertTrue(LibraryTestFixtures.isAutoBuilding(), "the automatic build has to be enabled"); //$NON-NLS-1$
		// dependencies are resolved from the extracted test libraries only
		LibraryTestFixtures.setMockDownloaderActive(false);

		final IProject proj = ProjectCreator
				.of(PROJECT, ResourcesPlugin.getWorkspace().getRoot().getLocation().append(PROJECT))
				.create(new NullProgressMonitor());
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);

		LibraryTestFixtures.extractAll();
	}

	@AfterAll
	static void cleanupAfterClass() {
		// every step runs, even if an earlier one fails, and all failures are reported
		assertAll("restore the test state", //$NON-NLS-1$
				LibraryTestFixtures::waitForBuild, () -> LibraryTestFixtures.deleteProject(PROJECT),
				LibraryTestFixtures::removeFixtureLibraries,
				() -> LibraryTestFixtures.setMockDownloaderActive(previousMockDownloaderActive));

		assertEquals(Boolean.valueOf(previousMockDownloaderActive),
				Boolean.valueOf(LibraryTestFixtures.isMockDownloaderActive()));
		assertFalse(LibraryTestFixtures.hasFixtureLibraries());
	}

	@BeforeEach
	void setupBeforeEach() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
	}

	@AfterEach
	void cleanupAfterEach() throws Exception {
		LibraryTestFixtures.resetProject(project);
	}

	static void waitForBuild() throws Exception {
		LibraryTestFixtures.waitForBuild();
	}

	@Test
	void testSimpleImport() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST01 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportUpdateNoOverwrite() throws Exception {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST01 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);

		waitForBuild();

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals("[1.0.0-2.0.0)", manifest.getDependencies().getRequired().get(0).getVersion()); //$NON-NLS-1$
	}

	@Test
	void testImportUpdateOverwrite() throws Exception {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST01 + "-2.0.0"), //$NON-NLS-1$
				true, false);

		waitForBuild();

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V2_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(V2_0_0, manifest.getDependencies().getRequired().get(0).getVersion());
	}

	@Test
	void testCheckManifest() throws Exception {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);
		project.build(IncrementalProjectBuilder.FULL_BUILD, null);

		waitForBuild();

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_5_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithoutResolve() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithResolve() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);
		final Manifest projectManifest = ManifestHelper.getContainerManifest(project);
		LibraryManager.INSTANCE.resolveDependencies(project, projectManifest, null);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithRangeResolve() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_1_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlap() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_0_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_1_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlap2() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_0_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlapError() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_1_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_1_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFolder(TEST01).exists());

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertEquals(1, markers.length);
		assertEquals(TEST01, findFirstDependencyMarker(markers).orElse(null));

		assertTrue(project.findMaxProblemSeverity(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_ZERO) >= IMarker.SEVERITY_ERROR);
	}

	@Test
	void testDependencyChain() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST06 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST06, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());

		LibraryAssert.assertLibraryLinked(project, TEST05, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertLibraryLinked(project, TEST02, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testMissingStandardLibrary() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST07 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);

		waitForBuild();

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST07, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME).getFolder(MATH).exists());

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertEquals(1, markers.length);
		assertEquals(MATH, findFirstDependencyMarker(markers).orElse(null));

		assertTrue(project.findMaxProblemSeverity(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_ZERO) >= IMarker.SEVERITY_ERROR);
	}

	@Test
	void testUnresolvedDependencyKeepsLinks() throws Exception {
		// the unavailable dependency is resolved first and stops the resolution while
		// test01 is still queued
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(MATH, "0.3.1415")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, java.net.URI.create(LIB_LOC + TEST01 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);

		final Manifest projectManifest = ManifestHelper.getContainerManifest(project);
		assertDoesNotThrow(() -> LibraryManager.INSTANCE.resolveDependencies(project, projectManifest, null));

		waitForBuild();

		manifest = ManifestHelper.getContainerManifest(project);
		assertEquals(2, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_0_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertEquals(1, markers.length);
		assertEquals(MATH, findFirstDependencyMarker(markers).orElse(null));
	}

	static Optional<String> findFirstDependencyMarker(final IMarker[] markers) {
		return Stream.of(markers).map(marker -> marker.getAttribute(LibraryMarkerFactory.MARKER_ATTRIBUTE, null))
				.filter(Objects::nonNull).findFirst();
	}
}
