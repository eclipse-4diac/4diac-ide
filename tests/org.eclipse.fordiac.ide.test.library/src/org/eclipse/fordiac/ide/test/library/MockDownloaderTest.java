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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.IArchiveDownloader;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

class MockDownloaderTest {
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String UNKNOWN = "unknown"; //$NON-NLS-1$

	private final MockDownloader downloader = new MockDownloader();

	@AfterAll
	static void cleanupAfterClass() throws IOException {
		MockDownloader.cleanUp();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("versionSelections")
	void selectsArchiveForRangeAndPreferredVersion(final String description, final VersionRange range,
			final Version preferredVersion, final String expectedArchive) {
		final DownloadResult<Path> result = downloader.downloadLibrary(TEST01, range, preferredVersion, null);

		assertEquals(DownloadResult.Status.OK, result.status(), result::message);
		assertEquals(expectedArchive, result.result().getFileName().toString());
		assertTrue(Files.isRegularFile(result.result()));
	}

	@SuppressWarnings("nls")
	static Stream<Arguments> versionSelections() {
		final VersionRange range = VersionComparator.parseVersionRange("[1.0.0-2.0.0)");
		return Stream.of(Arguments.of("highest matching version", range, null, "test01-1.5.0.zip"),
				Arguments.of("preferred version in range", range, new Version(1, 1, 0), "test01-1.1.0.zip"),
				Arguments.of("preferred version outside range", range, new Version(2, 0, 0), "test01-1.5.0.zip"),
				Arguments.of("preferred version not available", range, new Version(1, 2, 0), "test01-1.5.0.zip"),
				Arguments.of("exact version", VersionComparator.parseVersionRange("1.0.0"), null, "test01-1.0.0.zip"),
				Arguments.of("all versions", LibraryManager.ALL_RANGE, null, "test01-2.0.0.zip"),
				Arguments.of("no range", null, null, "test01-2.0.0.zip"));
	}

	@Test
	void reportsMissingVersionAsNotFound() {
		final DownloadResult<Path> result = downloader.downloadLibrary(TEST01,
				VersionComparator.parseVersionRange("[3.0.0-4.0.0)"), null, null); //$NON-NLS-1$

		assertEquals(DownloadResult.Status.NOT_FOUND, result.status());
		assertNull(result.result());
	}

	@Test
	void reportsUnknownLibraryAsNotFound() {
		final DownloadResult<Path> result = downloader.downloadLibrary(UNKNOWN, LibraryManager.ALL_RANGE, null,
				null);

		assertEquals(DownloadResult.Status.NOT_FOUND, result.status());
		assertNull(result.result());
	}

	@Test
	void providesManifestWithDependencies() {
		final DownloadResult<Path> result = downloader.downloadManifest(TEST02, new Version(1, 1, 0), null);

		assertEquals(DownloadResult.Status.OK, result.status(), result::message);
		final Manifest manifest = ManifestHelper.getManifest(result.result());
		assertNotNull(manifest);
		assertEquals(TEST02, manifest.getProduct().getSymbolicName());
		assertEquals("1.1.0", manifest.getProduct().getVersionInfo().getVersion()); //$NON-NLS-1$
		final List<Required> required = manifest.getDependencies().getRequired();
		assertEquals(1, required.size());
		assertEquals(TEST01, required.get(0).getSymbolicName());
		assertEquals("[1.0.0-2.0.0)", required.get(0).getVersion()); //$NON-NLS-1$
	}

	@Test
	void providesManifestWithoutDependencies() {
		final DownloadResult<Path> result = downloader.downloadManifest(TEST01, new Version(1, 0, 0), null);

		assertEquals(DownloadResult.Status.OK, result.status(), result::message);
		final Manifest manifest = ManifestHelper.getManifest(result.result());
		assertNotNull(manifest);
		assertEquals(TEST01, manifest.getProduct().getSymbolicName());
		assertEquals("1.0.0", manifest.getProduct().getVersionInfo().getVersion()); //$NON-NLS-1$
		assertTrue(manifest.getDependencies() == null || manifest.getDependencies().getRequired().isEmpty());
	}

	@Test
	void reportsUnknownManifestAsNotFound() {
		assertEquals(DownloadResult.Status.NOT_FOUND,
				downloader.downloadManifest(TEST01, new Version(9, 9, 9), null).status());
		assertEquals(DownloadResult.Status.NOT_FOUND,
				downloader.downloadManifest(UNKNOWN, new Version(1, 0, 0), null).status());
	}

	@Test
	void keepsManifestsOutOfDownloadCache() {
		final Path manifest = downloader.downloadManifest(TEST02, new Version(1, 1, 0), null).result();
		final Path downloadCache = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY);

		assertNotNull(manifest);
		assertFalse(manifest.toAbsolutePath().startsWith(downloadCache.toAbsolutePath()));
	}

	@Test
	void removesExtractedManifestsOnCleanUp() throws IOException {
		final Path manifest = downloader.downloadManifest(TEST01, new Version(1, 0, 0), null).result();
		assertNotNull(manifest);

		MockDownloader.cleanUp();

		assertFalse(Files.exists(manifest));
	}

	@Test
	void sharesActivationStateBetweenInstances() {
		final boolean previous = downloader.isActive();
		try {
			new MockDownloader().setActive(true);
			assertTrue(new MockDownloader().isActive());
			new MockDownloader().setActive(false);
			assertFalse(downloader.isActive());
		} finally {
			downloader.setActive(previous);
		}
	}

	@Test
	void representsSingleEndpoint() {
		assertFalse(downloader.hasMultipleEndpoints());
		assertEquals(List.of(downloader), downloader.convertEndpointsToDownloader());
	}

	@SuppressWarnings("static-method")
	@Test
	void isRegisteredAsArchiveDownloader() {
		final List<IArchiveDownloader> downloaders = TypeLibraryManager
				.listExtensions(LibraryManager.DOWNLOADER_EXTENSION, IArchiveDownloader.class);

		assertTrue(downloaders.stream().anyMatch(MockDownloader.class::isInstance));
	}
}
