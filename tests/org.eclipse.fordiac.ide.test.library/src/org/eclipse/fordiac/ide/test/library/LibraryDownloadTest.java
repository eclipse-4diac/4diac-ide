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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryMarkerFactory;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.ProjectCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Version;

/**
 * Resolves library dependencies through the {@link MockDownloader} or an
 * endpoint of the {@link MockDownloaderProvider}. Only the archives a test
 * imports directly are extracted, so all dependencies have to be downloaded.
 *
 * <p>
 * The automatic build is disabled and the project is built explicitly, so that
 * downloads and markers are completed when the build returns.
 */
class LibraryDownloadTest {
	private static final String PROJECT = "librarydownloadtest"; //$NON-NLS-1$
	private static final String LIB_LOC = "WORKSPACE_LOC/.lib/"; //$NON-NLS-1$
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String TEST02_ARCHIVE = "test02-1.1.0"; //$NON-NLS-1$
	private static final String V1_1_0 = "1.1.0"; //$NON-NLS-1$
	private static final String V1_5_0 = "1.5.0"; //$NON-NLS-1$

	private static boolean previousAutoBuilding = true;
	private static boolean previousMockDownloaderActive;

	private IProject project;

	@BeforeAll
	static void setupBeforeClass() throws Exception {
		previousAutoBuilding = LibraryTestFixtures.isAutoBuilding();
		previousMockDownloaderActive = LibraryTestFixtures.isMockDownloaderActive();
		LibraryTestFixtures.setAutoBuilding(false);
		LibraryTestFixtures.setMockDownloaderActive(true);
		LibraryTestFixtures.removeFixtureLibraries();
		final IProject proj = ProjectCreator
				.of(PROJECT, ResourcesPlugin.getWorkspace().getRoot().getLocation().append(PROJECT))
				.create(new NullProgressMonitor());
		assertNotNull(proj);
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	@AfterAll
	static void cleanupAfterClass() {
		// every step runs, even if an earlier one fails, and all failures are reported
		assertAll("restore the test state", //$NON-NLS-1$
				() -> LibraryTestFixtures.deleteProject(PROJECT), LibraryTestFixtures::removeFixtureLibraries,
				MockDownloader::cleanUp,
				() -> LibraryTestFixtures.setMockDownloaderActive(previousMockDownloaderActive),
				() -> LibraryTestFixtures.setAutoBuilding(previousAutoBuilding));

		assertEquals(Boolean.valueOf(previousMockDownloaderActive),
				Boolean.valueOf(LibraryTestFixtures.isMockDownloaderActive()));
		assertEquals(Boolean.valueOf(previousAutoBuilding), Boolean.valueOf(LibraryTestFixtures.isAutoBuilding()));
		assertFalse(LibraryTestFixtures.hasFixtureLibraries());
	}

	@BeforeEach
	void setupBeforeEach() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
	}

	@AfterEach
	void cleanupAfterEach() {
		assertAll("reset the test state", //$NON-NLS-1$
				() -> LibraryTestFixtures.setMockDownloaderActive(true),
				() -> LibraryTestFixtures.resetProject(project), LibraryTestFixtures::removeFixtureLibraries);
	}

	@Test
	void downloadsHighestMatchingVersion() throws Exception {
		assertHighestMatchingVersionDownloaded();
	}

	@Test
	void downloadsWithoutFileSystemNotifications() throws Exception {
		final Field watchServiceField = LibraryManager.class.getDeclaredField("watchService"); //$NON-NLS-1$
		watchServiceField.setAccessible(true);
		final Object originalWatchService = watchServiceField.get(LibraryManager.INSTANCE);
		try {
			watchServiceField.set(LibraryManager.INSTANCE, null);
			assertHighestMatchingVersionDownloaded();
			// Replacing the same library must not duplicate its cached version.
			LibraryTestFixtures.extract(TEST01 + "-" + V1_5_0); //$NON-NLS-1$
			assertEquals(List.of(new Version(V1_5_0)), LibraryTestFixtures.getExtractedVersions(TEST01));
		} finally {
			watchServiceField.set(LibraryManager.INSTANCE, originalWatchService);
		}
		assertEquals(List.of(new Version(V1_5_0)), LibraryTestFixtures.getExtractedVersions(TEST01));
	}

	private void assertHighestMatchingVersionDownloaded() throws Exception {
		// test02 1.1.0 requires test01 [1.0.0-2.0.0)
		LibraryTestFixtures.extract(TEST02_ARCHIVE);
		assertTrue(LibraryTestFixtures.getExtractedVersions(TEST01).isEmpty(),
				"no test01 version may be extracted, otherwise the download is bypassed"); //$NON-NLS-1$

		assertTrue(LibraryManager.INSTANCE.importLibrary(project, URI.create(LIB_LOC + TEST02_ARCHIVE), true, false));
		buildProject();

		final Manifest manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(List.of(new Version(V1_5_0)), LibraryTestFixtures.getExtractedVersions(TEST01));
		final IMarker[] markers = findManifestMarkers();
		assertEquals(0, markers.length, () -> describe(markers));
	}

	@Test
	void downloadsFromConfiguredEndpoint() throws Exception {
		// only the endpoint of the additional provider can serve the dependency
		LibraryTestFixtures.setMockDownloaderActive(false);
		LibraryTestFixtures.extract(TEST02_ARCHIVE);

		assertTrue(LibraryManager.INSTANCE.importLibrary(project, URI.create(LIB_LOC + TEST02_ARCHIVE), true, false));
		buildProject();

		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(List.of(new Version(V1_5_0)), LibraryTestFixtures.getExtractedVersions(TEST01));
		final IMarker[] markers = findManifestMarkers();
		assertEquals(0, markers.length, () -> describe(markers));
	}

	@Test
	void reportsMissingVersionWithoutDownload() throws Exception {
		final Manifest manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[3.0.0-4.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		buildProject();

		assertFalse(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFolder(TEST01).exists());
		assertTrue(LibraryTestFixtures.getExtractedVersions(TEST01).isEmpty());
		final IMarker[] markers = findManifestMarkers();
		assertEquals(1, markers.length, () -> describe(markers));
		assertEquals(TEST01, markers[0].getAttribute(LibraryMarkerFactory.MARKER_ATTRIBUTE, null));
	}

	@SuppressWarnings("static-method")
	@Test
	void catalogueReadsFixtureManifests() {
		assertFalse(Files.exists(LibraryTestFixtures.getDownloadFolder().resolve(TEST02_ARCHIVE)),
				"a cached manifest would bypass the downloader"); //$NON-NLS-1$

		final OnlineLibraryProvider provider = new OnlineLibraryProvider();
		final IStatus status = provider.refresh(new NullProgressMonitor(), true);

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertEquals(Map.of(TEST01, VersionComparator.parseVersionRange("[1.0.0-2.0.0)")), //$NON-NLS-1$
				provider.getLibrary(TEST02, new Version(V1_1_0)).orElseThrow().dependencies());
		assertEquals(Map.of(), provider.getLibrary(TEST01, new Version(V1_5_0)).orElseThrow().dependencies());
	}

	private void buildProject() throws Exception {
		project.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		LibraryTestFixtures.waitForBuild();
	}

	private IMarker[] findManifestMarkers() throws CoreException {
		return project.getFile(LibraryManager.MANIFEST).findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_ZERO);
	}

	private static String describe(final IMarker[] markers) {
		return Stream.of(markers).map(marker -> marker.getAttribute(IMarker.MESSAGE, "")) //$NON-NLS-1$
				.collect(Collectors.joining("; ")); //$NON-NLS-1$
	}
}
