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
 *   Michael Oberlehner - fix version selection and provide fixture manifests
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Library package downloader serving the library archives in the {@code data}
 * folder of this test bundle.
 */
public class MockDownloader implements ILibraryPackageDownloader {
	// The extension registry creates a new downloader instance for every lookup,
	// so the activation state has to be shared between all instances.
	private static final AtomicBoolean ACTIVE = new AtomicBoolean(false);

	private static final String BUNDLE_ID = "org.eclipse.fordiac.ide.test.library"; //$NON-NLS-1$
	private static final String FORMAT_STRING = "data/%s-%s.zip"; //$NON-NLS-1$
	private static final String MANIFEST_DIRECTORY_PREFIX = "fordiac-mock-manifests"; //$NON-NLS-1$

	private static Path manifestDirectory;

	private final VersionComparator versionComparator = new VersionComparator();

	@SuppressWarnings("nls")
	private final Map<String, List<String>> archiveMap = Map.ofEntries(
			Map.entry("test01", List.of("1.0.0", "1.1.0", "1.5.0", "2.0.0")),
			Map.entry("test02", List.of("1.0.0", "1.1.0")), Map.entry("test03", List.of("1.0.0", "1.1.0")),
			Map.entry("test04", List.of("1.0.0", "1.1.0")), Map.entry("test05", List.of("1.0.0")),
			Map.entry("test06", List.of("1.0.0")), Map.entry("test07", List.of("1.0.0")));

	@Override
	public String getName() {
		return "Mock"; //$NON-NLS-1$
	}

	@Override
	public DownloadResult<List<String>> availableLibraries(final IProgressMonitor monitor)
			throws OperationCanceledException {
		return new DownloadResult<>(List.copyOf(archiveMap.keySet()));
	}

	@Override
	public DownloadResult<List<String>> availableVersions(final String symbolicName, final IProgressMonitor monitor)
			throws OperationCanceledException {
		return new DownloadResult<>(archiveMap.getOrDefault(symbolicName, Collections.emptyList()));
	}

	@Override
	public DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
			final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
		final SubMonitor progress = SubMonitor.convert(monitor, "", 2); //$NON-NLS-1$
		if (!archiveMap.containsKey(symbolicName)) {
			return new DownloadResult<>(DownloadResult.Status.NOT_FOUND, Messages.Library_Not_Found);
		}
		final List<String> candidates = availableVersions(symbolicName, progress.split(1)).result().stream()
				.filter(version -> range == null || VersionComparator.contains(range, version))
				.sorted(versionComparator).toList();
		if (candidates.isEmpty()) {
			return new DownloadResult<>(DownloadResult.Status.NOT_FOUND, Messages.Version_Not_Found);
		}
		// the preferred version is only used if it is contained in the range
		final String version = candidates.stream().filter(
				candidate -> preferredVersion != null && preferredVersion.compareTo(new Version(candidate)) == 0)
				.findFirst().orElse(candidates.getLast());
		progress.worked(1);
		return findArchive(symbolicName, version);
	}

	@Override
	public DownloadResult<Path> downloadManifest(final String symbolicName, final Version version,
			final IProgressMonitor monitor) throws OperationCanceledException {
		final List<String> versions = archiveMap.get(symbolicName);
		if (versions == null) {
			return new DownloadResult<>(DownloadResult.Status.NOT_FOUND, Messages.Library_Not_Found);
		}
		final String archiveVersion = versions.stream()
				.filter(candidate -> version != null && version.compareTo(new Version(candidate)) == 0).findFirst()
				.orElse(null);
		if (archiveVersion == null) {
			return new DownloadResult<>(DownloadResult.Status.NOT_FOUND, Messages.Version_Not_Found);
		}
		final DownloadResult<Path> archive = findArchive(symbolicName, archiveVersion);
		if (archive.status() != DownloadResult.Status.OK) {
			return archive;
		}
		return extractManifest(archive.result(), symbolicName + "-" + archiveVersion); //$NON-NLS-1$
	}

	@Override
	public boolean isActive() {
		return ACTIVE.get();
	}

	@Override
	public void setActive(final boolean active) {
		ACTIVE.set(active);
	}

	@Override
	public DownloadResult<Map<String, List<String>>> availableLibrariesAndVersions(final IProgressMonitor monitor)
			throws OperationCanceledException {
		return new DownloadResult<>(archiveMap);
	}

	/**
	 * Deletes the manifests extracted by {@link #downloadManifest}.
	 *
	 * @throws IOException if a manifest cannot be deleted
	 */
	public static synchronized void cleanUp() throws IOException {
		if (manifestDirectory == null) {
			return;
		}
		try (Stream<Path> paths = Files.walk(manifestDirectory)) {
			for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
				Files.delete(path);
			}
		}
		manifestDirectory = null;
	}

	private static DownloadResult<Path> findArchive(final String symbolicName, final String version) {
		final String archive = String.format(FORMAT_STRING, symbolicName, version);
		final Bundle bundle = Platform.getBundle(BUNDLE_ID);
		final URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(archive));
		if (url == null) {
			return new DownloadResult<>(DownloadResult.Status.ERROR,
					MessageFormat.format(Messages.Archive_Not_Found, archive));
		}
		try {
			return new DownloadResult<>(Paths.get(URIUtil.toURI(FileLocator.toFileURL(url))));
		} catch (final IOException | URISyntaxException e) {
			return new DownloadResult<>(DownloadResult.Status.ERROR,
					MessageFormat.format(Messages.Archive_Not_Accessible, archive, e.getMessage()));
		}
	}

	private static DownloadResult<Path> extractManifest(final Path archive, final String libraryFolder) {
		// extract to a temporary directory instead of the workspace download folder,
		// where OnlineLibraryProvider would pick it up as a cached manifest
		try (ZipFile zipFile = new ZipFile(archive.toFile())) {
			final ZipEntry entry = zipFile.getEntry(libraryFolder + "/" + LibraryManager.MANIFEST); //$NON-NLS-1$
			if (entry == null) {
				return new DownloadResult<>(DownloadResult.Status.ERROR,
						MessageFormat.format(Messages.Manifest_Not_Found, archive.getFileName()));
			}
			final Path manifest = getManifestDirectory().resolve(libraryFolder).resolve(LibraryManager.MANIFEST);
			Files.createDirectories(manifest.getParent());
			try (InputStream input = zipFile.getInputStream(entry)) {
				Files.copy(input, manifest, StandardCopyOption.REPLACE_EXISTING);
			}
			return new DownloadResult<>(manifest);
		} catch (final IOException e) {
			return new DownloadResult<>(DownloadResult.Status.ERROR,
					MessageFormat.format(Messages.Manifest_Not_Accessible, archive.getFileName(), e.getMessage()));
		}
	}

	private static synchronized Path getManifestDirectory() throws IOException {
		if (manifestDirectory == null) {
			manifestDirectory = Files.createTempDirectory(MANIFEST_DIRECTORY_PREFIX);
		}
		return manifestDirectory;
	}
}
