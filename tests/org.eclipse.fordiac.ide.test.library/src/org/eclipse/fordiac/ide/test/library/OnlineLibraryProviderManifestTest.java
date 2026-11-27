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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.preferences.LibraryPreferenceConstants;
import org.eclipse.fordiac.ide.library.provider.OnlineLibraryProvider;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;
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
	// archives without a usable manifest are remembered by source for the session: see unique()
	private static final String BROKEN_SOURCE = "Archives without manifest"; //$NON-NLS-1$
	private static final String LIMITED_SOURCE = "Archives with a small metadata limit"; //$NON-NLS-1$
	private static final String PROJECT = "OnlineLibraryProviderManifestTest"; //$NON-NLS-1$
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String V1_0_0 = "1.0.0"; //$NON-NLS-1$
	private static final String V1_1_0 = "1.1.0"; //$NON-NLS-1$

	private Path archiveFolder;
	private String previousPreference;

	@BeforeEach
	void setup() throws IOException {
		previousPreference = preferences().get(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, null);
		deleteCachedManifests();
		archiveFolder = Files.createTempDirectory("archives"); //$NON-NLS-1$
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
		try (Stream<Path> paths = Files.walk(archiveFolder)) {
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
		assertEquals(MessageFormat.format(Messages.OnlineLibraryProvider_ManifestsNotFound, SOURCE, Integer.valueOf(3)),
				warnings.getFirst().getMessage());
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
		assertArchiveProblem(Messages.OnlineLibraryProvider_ArchiveWithoutManifest,
				(name, version) -> Map.of(name + "-" + version + "/typelib/", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	void reportsAnArchiveWithAnEmptyManifest() throws Exception {
		assertArchiveProblem(Messages.OnlineLibraryProvider_ArchiveInvalidManifest,
				(name, version) -> Map.of(name + "-" + version + "/" + LibraryManager.MANIFEST, "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	void reportsAnArchiveWithAManifestThatCannotBeRead() throws Exception {
		assertArchiveProblem(Messages.OnlineLibraryProvider_ArchiveInvalidManifest,
				(name, version) -> Map.of(name + "-" + version + "/" + LibraryManager.MANIFEST, "this is no manifest")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Test
	void reportsAnArchiveWithSeveralManifests() throws Exception {
		assertArchiveProblem(Messages.OnlineLibraryProvider_ArchiveAmbiguousManifest,
				(name, version) -> Map.of("first/" + LibraryManager.MANIFEST, "<a/>", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						"second/" + LibraryManager.MANIFEST, "<b/>")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void rejectsAManifestAboveTheMetadataLimit() throws Exception {
		setReadArchives(true);
		final String limited = unique(LIMITED_SOURCE);
		final ArchivesOnly source = new ArchivesOnly(limited);

		final IStatus status = refreshWithMetadataLimit(source, "16"); //$NON-NLS-1$

		assertEquals(IStatus.ERROR, status.getSeverity(), status::toString);
		final String tooLarge = MessageFormat.format(Messages.OnlineLibraryProvider_ManifestTooLarge,
				TEST02 + "-" + V1_1_0 + "/" + LibraryManager.MANIFEST, Long.valueOf(16), //$NON-NLS-1$ //$NON-NLS-2$
				AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY);
		final String expected = MessageFormat.format(Messages.OnlineLibraryProvider_ArchiveDownloadFailed, limited,
				TEST02, new Version(V1_1_0), tooLarge);
		assertTrue(Stream.of(status.getChildren()).anyMatch(child -> expected.equals(child.getMessage())),
				status::toString);
		assertEquals(3, source.archiveDownloads.get());
		assertFalse(Files.exists(cachedManifest(TEST02, V1_1_0)));
		assertNoPartialFiles();
	}

	@Test
	void readsTheManifestWithAMetadataLimitAboveTheIntegerRange() throws Exception {
		setReadArchives(true);
		final ArchivesOnly source = new ArchivesOnly(unique(LIMITED_SOURCE));

		// 8 GiB: a limit above Integer.MAX_VALUE must not wrap around
		final IStatus status = refreshWithMetadataLimit(source, "8589934592"); //$NON-NLS-1$

		assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
		assertTrue(Files.isRegularFile(cachedManifest(TEST02, V1_1_0)));
	}

	@Test
	void theProjectSettingReadsTheArchivesIfTheWorkspaceSettingDoesNot() throws Exception {
		assertProjectSetting(false, true, true, true);
	}

	@Test
	void theProjectSettingKeepsTheArchivesIfTheWorkspaceSettingReadsThem() throws Exception {
		assertProjectSetting(true, true, false, false);
	}

	@Test
	void theWorkspaceSettingAppliesIfTheProjectSettingsAreDisabled() throws Exception {
		assertProjectSetting(true, false, false, true);
	}

	private void assertArchiveProblem(final String pattern, final BiFunction<String, Version, Map<String, String>> entries)
			throws Exception {
		setReadArchives(true);
		final String name = unique(BROKEN_SOURCE);
		final ArchivesWith source = new ArchivesWith(name, entries);
		final OnlineLibraryProvider catalogue = catalogue(source);

		final IStatus status = catalogue.refresh(new NullProgressMonitor(), true);

		assertEquals(IStatus.ERROR, status.getSeverity(), status::toString);
		final String expected = MessageFormat.format(pattern, name, TEST02, new Version(V1_1_0));
		assertTrue(Stream.of(status.getChildren()).anyMatch(child -> expected.equals(child.getMessage())),
				status::toString);
		assertEquals(3, source.archiveDownloads.get());
		assertFalse(Files.exists(cachedManifest(TEST02, V1_1_0)));
		assertNoPartialFiles();

		// the archive is not downloaded again in this session
		assertEquals(IStatus.ERROR, catalogue(source).refresh(new NullProgressMonitor(), true).getSeverity());
		assertEquals(3, source.archiveDownloads.get());
	}

	private static IStatus refreshWithMetadataLimit(final MockEndpoint source, final String limit) {
		final String previousLimit = System.getProperty(AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY);
		System.setProperty(AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY, limit);
		try {
			return catalogue(source).refresh(new NullProgressMonitor(), true);
		} finally {
			if (previousLimit == null) {
				System.clearProperty(AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY);
			} else {
				System.setProperty(AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY, previousLimit);
			}
		}
	}

	/** archives without a manifest are remembered by source name: use one per run */
	private static String unique(final String name) {
		return name + " " + System.nanoTime(); //$NON-NLS-1$
	}

	/** no staging file of a failed manifest copy is left in the cache */
	private static void assertNoPartialFiles() throws IOException {
		final Path folder = cachedManifest(TEST02, V1_1_0).getParent();
		if (Files.isDirectory(folder)) {
			try (Stream<Path> files = Files.list(folder)) {
				assertEquals(List.of(), files.filter(file -> file.getFileName().toString().endsWith(".part")).toList()); //$NON-NLS-1$
			}
		}
	}

	private static void assertProjectSetting(final boolean workspaceValue, final boolean projectSettingsActive,
			final boolean projectValue, final boolean readsArchives) throws Exception {
		setReadArchives(workspaceValue);
		// a project of its own, no project of the workspace is touched
		final IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJECT + System.nanoTime());
		testProject.create(null);
		try {
			testProject.open(null);
			final org.osgi.service.prefs.Preferences node = new ProjectScope(testProject)
					.getNode(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID);
			node.putBoolean(PreferenceProvider.PROJECT_STORE_ACTIVE, projectSettingsActive);
			node.putBoolean(LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, projectValue);
			node.flush();
			final ArchivesOnly source = new ArchivesOnly(unique(SOURCE));

			final IStatus status = new OnlineLibraryProvider(downloaders(source), testProject)
					.refresh(new NullProgressMonitor(), true);

			if (readsArchives) {
				assertTrue(status.getSeverity() < IStatus.WARNING, status::toString);
				assertEquals(3, source.archiveDownloads.get());
			} else {
				assertEquals(IStatus.WARNING, status.getSeverity(), status::toString);
				assertEquals(0, source.archiveDownloads.get());
			}
		} finally {
			testProject.delete(true, true, null);
		}
	}

	private static OnlineLibraryProvider catalogue(final MockEndpoint endpoint) {
		return new OnlineLibraryProvider(downloaders(endpoint));
	}

	private static LibraryPackageDownloaderProvider downloaders(final MockEndpoint endpoint) {
		return new LibraryPackageDownloaderProvider(List.of(new MockDownloaderProvider(List.of(endpoint))));
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

	/** Offers archives with the given entries (entry name to content). */
	private class ArchivesWith extends ArchivesOnly {
		private final BiFunction<String, Version, Map<String, String>> entries;

		ArchivesWith(final String name, final BiFunction<String, Version, Map<String, String>> entries) {
			super(name);
			this.entries = entries;
		}

		@Override
		public DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
				final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
			archiveDownloads.incrementAndGet();
			final Path archive = archiveFolder.resolve(symbolicName + "-" + preferredVersion + ".zip"); //$NON-NLS-1$ //$NON-NLS-2$
			try (OutputStream out = Files.newOutputStream(archive); ZipOutputStream zip = new ZipOutputStream(out)) {
				for (final var entry : entries.apply(symbolicName, preferredVersion).entrySet()) {
					zip.putNextEntry(new ZipEntry(entry.getKey()));
					zip.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
					zip.closeEntry();
				}
			} catch (final IOException e) {
				return new DownloadResult<>(DownloadResult.Status.ERROR, e.getMessage());
			}
			return new DownloadResult<>(archive);
		}
	}
}
