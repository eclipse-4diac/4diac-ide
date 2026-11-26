/*******************************************************************************
 * Copyright (c) 2026 MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrii Kozhushko - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.preferences.LibraryPreferenceConstants;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * A source may offer a library version as an archive without a manifest next
 * to it. The catalogue shows such versions without dependencies and reports
 * one warning per source, or reads the manifests from the archives if the
 * preference is set.
 */
class OnlineLibraryProviderManifestTest {
	private static final String SOURCE = "Archives only"; //$NON-NLS-1$
	// a source of its own: archives without a manifest are remembered per source for the session
	private static final String BROKEN_SOURCE = "Archives without manifest"; //$NON-NLS-1$
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String V1_0_0 = "1.0.0"; //$NON-NLS-1$
	private static final String V1_1_0 = "1.1.0"; //$NON-NLS-1$

	private Path emptyArchiveFolder;
	private String previousPreference;

	@BeforeEach
	void setup() throws IOException {
		previousPreference = preferences().get(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, null);
		deleteCachedManifests();
		emptyArchiveFolder = Files.createTempDirectory("archives"); //$NON-NLS-1$
	}

	@AfterEach
	void cleanup() throws Exception {
		if (previousPreference == null) {
			preferences().remove(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST);
		} else {
			preferences().put(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, previousPreference);
		}
		preferences().flush();
		deleteCachedManifests();
		try (Stream<Path> paths = Files.walk(emptyArchiveFolder)) {
			for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
				Files.delete(path);
			}
		}
	}

	@Test
	void missingManifestsGiveOneWarningPerSource() throws Exception {
		setReadArchives(false);
		final ArchivesOnly source = new ArchivesOnly();
		final OnlineLibraryProvider catalogue = catalogue(source);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), true);

		assertEquals(IStatus.WARNING, status.getSeverity(), status::toString);
		final List<IStatus> warnings = Stream.of(status.getChildren())
				.filter(child -> child.getSeverity() == IStatus.WARNING).toList();
		assertEquals(1, warnings.size(), status::toString);
		assertTrue(warnings.getFirst().getMessage().startsWith(SOURCE + ": "), warnings.getFirst()::getMessage); //$NON-NLS-1$
		assertTrue(warnings.getFirst().getMessage().contains("3 library versions"), warnings.getFirst()::getMessage); //$NON-NLS-1$
		// the versions are offered without dependencies, no archive is downloaded
		assertEquals(Map.of(), catalogue.getLibrary(TEST02, new Version(V1_1_0)).orElseThrow().dependencies());
		assertEquals(0, source.archiveDownloads.get());
		assertFalse(Files.exists(cachedManifest(TEST02, V1_1_0)));
	}

	@Test
	void readsTheManifestFromTheArchiveIfEnabled() throws Exception {
		setReadArchives(true);
		final ArchivesOnly source = new ArchivesOnly();
		final OnlineLibraryProvider catalogue = catalogue(source);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), true);

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertEquals(Map.of(TEST01, VersionComparator.parseVersionRange("[1.0.0-2.0.0)")), //$NON-NLS-1$
				catalogue.getLibrary(TEST02, new Version(V1_1_0)).orElseThrow().dependencies());
		assertTrue(Files.isRegularFile(cachedManifest(TEST02, V1_1_0)), "the manifest is kept in the cache"); //$NON-NLS-1$
		assertEquals(3, source.archiveDownloads.get());

		// the next refresh uses the cached manifests
		assertTrue(catalogue(source).refresh(new NullProgressMonitor(), true).getSeverity() < IStatus.WARNING);
		assertEquals(3, source.archiveDownloads.get());
	}

	@Test
	void reportsAnArchiveWithoutManifest() throws Exception {
		setReadArchives(true);
		final ArchivesWithoutManifest source = new ArchivesWithoutManifest();
		final OnlineLibraryProvider catalogue = catalogue(source);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), true);

		assertEquals(IStatus.ERROR, status.getSeverity(), status::toString);
		final String expected = MessageFormat.format(Messages.OnlineLibraryProvider_ArchiveWithoutManifest, BROKEN_SOURCE,
				TEST02, new Version(V1_1_0));
		assertTrue(Stream.of(status.getChildren()).anyMatch(child -> expected.equals(child.getMessage())),
				status::toString);
		assertEquals(3, source.archiveDownloads.get());
		assertFalse(Files.exists(cachedManifest(TEST02, V1_1_0)));
	}

	private static OnlineLibraryProvider catalogue(final MockEndpoint endpoint) {
		return new OnlineLibraryProvider(
				new LibraryPackageDownloaderProvider(List.of(new MockDownloaderProvider(List.of(endpoint)))));
	}

	private static org.osgi.service.prefs.Preferences preferences() {
		return InstanceScope.INSTANCE.getNode(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID);
	}

	private static void setReadArchives(final boolean value) throws Exception {
		preferences().putBoolean(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, value);
		preferences().flush();
	}

	private static Path cachedManifest(final String symbolicName, final String version) {
		return LibraryTestFixtures.getDownloadFolder().resolve(symbolicName + "-" + version) //$NON-NLS-1$
				.resolve(LibraryManager.MANIFEST);
	}

	/**
	 * Removes the manifests this test caches and their folders if nothing else is
	 * in them: another test expects that no download folder of test02 exists.
	 */
	private static void deleteCachedManifests() throws IOException {
		for (final String[] version : new String[][] { { TEST01, V1_0_0 }, { TEST01, V1_1_0 }, { TEST02, V1_1_0 } }) {
			final Path manifest = cachedManifest(version[0], version[1]);
			Files.deleteIfExists(manifest);
			try (Stream<Path> content = Files.exists(manifest.getParent()) ? Files.list(manifest.getParent())
					: Stream.empty()) {
				if (content.findAny().isEmpty()) {
					Files.deleteIfExists(manifest.getParent());
				}
			}
		}
	}

	/** Offers archives of the test data, but no manifests next to them. */
	private static class ArchivesOnly extends MockEndpoint {
		final AtomicInteger archiveDownloads = new AtomicInteger();

		ArchivesOnly() {
			this(SOURCE);
		}

		ArchivesOnly(final String name) {
			super(name, Map.of(TEST01, List.of(V1_0_0, V1_1_0), TEST02, List.of(V1_1_0)));
		}

		@Override
		public DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
				final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
			archiveDownloads.incrementAndGet();
			return super.downloadLibrary(symbolicName, range, preferredVersion, monitor);
		}

		@Override
		public DownloadResult<Path> downloadManifest(final String symbolicName, final Version version,
				final IProgressMonitor monitor) throws OperationCanceledException {
			return new DownloadResult<>(DownloadResult.Status.NOT_FOUND, "404 Not Found"); //$NON-NLS-1$
		}
	}

	/** Offers archives that contain no manifest. */
	private class ArchivesWithoutManifest extends ArchivesOnly {
		ArchivesWithoutManifest() {
			super(BROKEN_SOURCE);
		}

		@Override
		public DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
				final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
			archiveDownloads.incrementAndGet();
			final Path archive = emptyArchiveFolder.resolve(symbolicName + "-" + preferredVersion + ".zip"); //$NON-NLS-1$ //$NON-NLS-2$
			try (OutputStream out = Files.newOutputStream(archive); ZipOutputStream zip = new ZipOutputStream(out)) {
				zip.putNextEntry(new ZipEntry(symbolicName + "-" + preferredVersion + "/typelib/")); //$NON-NLS-1$ //$NON-NLS-2$
				zip.closeEntry();
			} catch (final IOException e) {
				return new DownloadResult<>(DownloadResult.Status.ERROR, e.getMessage());
			}
			return new DownloadResult<>(archive);
		}
	}
}
