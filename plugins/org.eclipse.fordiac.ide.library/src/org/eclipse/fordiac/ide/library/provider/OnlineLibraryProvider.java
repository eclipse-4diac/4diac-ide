/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH, Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - use the shared downloader lookup
 *   Michael Oberlehner - keep the libraries of all sources in the catalogue
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.preferences.LibraryPreferenceConstants;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class OnlineLibraryProvider extends AbstractLibraryProvider {

	private static final String MACOS_METADATA_FOLDER = "__MACOSX"; //$NON-NLS-1$

	/**
	 * The problem with the manifest of an archive (none, several, unreadable) by
	 * source, library and version: such an archive is not downloaded again in this
	 * session
	 */
	private static final Map<String, String> ARCHIVE_PROBLEMS = new ConcurrentHashMap<>();

	private final AtomicReference<Map<String, List<LibraryDescriptor>>> availableLibs = new AtomicReference<>(Map.of());
	private final LibraryPackageDownloaderProvider downloaderProvider;
	private final IProject project;

	public OnlineLibraryProvider() {
		this(new LibraryPackageDownloaderProvider());
	}

	/**
	 * @param project project whose library preferences apply, or {@code null} for
	 *                the workspace preferences
	 */
	public OnlineLibraryProvider(final IProject project) {
		this(new LibraryPackageDownloaderProvider(), project);
	}

	public OnlineLibraryProvider(final LibraryPackageDownloaderProvider downloaderProvider) {
		this(downloaderProvider, null);
	}

	private OnlineLibraryProvider(final LibraryPackageDownloaderProvider downloaderProvider, final IProject project) {
		this.downloaderProvider = Objects.requireNonNull(downloaderProvider);
		this.project = project;
	}

	@Override
	public IStatus refresh(final IProgressMonitor monitor, final boolean fetchDependencies) {
		final Map<String, List<LibraryDescriptor>> refreshedLibs = new HashMap<>();
		final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.OK, "Download versions status"); //$NON-NLS-1$
		boolean sourceConfigured = false;
		final var sources = downloaderProvider.getActiveDownloaders();
		status.merge(sources.status());
		final boolean readArchives = PreferenceProvider.getBoolean(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID,
				LibraryPreferenceConstants.DOWNLOAD_ARCHIVE_WITHOUT_MANIFEST, false, project);

		for (final ILibraryPackageDownloader downloader : sources.downloaders()) {
			final DownloadResult<Map<String, List<String>>> downloadResult = downloader
					.availableLibrariesAndVersions(monitor);

			switch (downloadResult.status()) {
			case OK -> {
				final ManifestLookup lookup = new ManifestLookup(downloader, readArchives, status, monitor);
				// a version offered by several sources is listed once per source, in lookup order
				toLibraryDescriptors(downloadResult.result(), fetchDependencies, lookup)
						.forEach((symbolicName, descriptors) -> refreshedLibs
								.computeIfAbsent(symbolicName, _ -> new ArrayList<>()).addAll(descriptors));
				if (lookup.missing().get() > 0) {
					// one warning per source instead of one per version
					status.add(Status.warning(MessageFormat.format(Messages.OnlineLibraryProvider_ManifestsNotFound,
							downloader.getName(), Integer.valueOf(lookup.missing().get()))));
				}
				status.add(Status.info(downloader.getName() + ": OK")); //$NON-NLS-1$
				sourceConfigured = true;
			}
			case NO_CONFIG -> {
				// a source without any configuration is skipped, as for library downloads
			}
			default -> {
				status.add(Status.error(downloader.getName() + ": " + downloadResult.message())); //$NON-NLS-1$
				sourceConfigured = true;
			}
			}
		}

		if (!sourceConfigured && status.isOK()) {
			status.add(Status.info(Messages.OnlineLibraryProvider_NoLibrarySource));
		}
		refreshedLibs.replaceAll((_, descriptors) -> List.copyOf(descriptors));
		availableLibs.set(Map.copyOf(refreshedLibs));

		return status;
	}

	/**
	 * What is needed to get the manifests of one source
	 *
	 * @param downloader   the source
	 * @param readArchives read the manifest from the archive if the source offers
	 *                     no manifest
	 * @param status       collects the problems
	 * @param monitor      the progress monitor
	 * @param missing      number of versions whose manifest was not found
	 */
	private record ManifestLookup(ILibraryPackageDownloader downloader, boolean readArchives, MultiStatus status,
			IProgressMonitor monitor, AtomicInteger missing) {
		ManifestLookup(final ILibraryPackageDownloader downloader, final boolean readArchives,
				final MultiStatus status, final IProgressMonitor monitor) {
			this(downloader, readArchives, status, monitor, new AtomicInteger());
		}
	}

	private static Map<String, List<LibraryDescriptor>> toLibraryDescriptors(final Map<String, List<String>> versions,
			final boolean fetchDependencies, final ManifestLookup lookup) {
		final Map<String, List<LibraryDescriptor>> descriptors = new HashMap<>();
		for (final Map.Entry<String, List<String>> library : versions.entrySet()) {
			final List<LibraryDescriptor> offered = new ArrayList<>();
			for (final String version : library.getValue()) {
				toLibraryDescriptor(library.getKey(), version, fetchDependencies, lookup).ifPresent(offered::add);
			}
			descriptors.put(library.getKey(), Collections.unmodifiableList(offered));
		}
		return descriptors;
	}

	/**
	 * Describes one version offered by the downloader. Nothing is returned for a
	 * version that cannot be parsed or whose manifest has an invalid dependency
	 * range.
	 */
	private static Optional<LibraryDescriptor> toLibraryDescriptor(final String symbolicName, final String version,
			final boolean fetchDependencies, final ManifestLookup lookup) {
		final Version parsedVersion;
		try {
			parsedVersion = Version.valueOf(version);
		} catch (final IllegalArgumentException e) {
			return Optional.empty();
		}
		if (!fetchDependencies) {
			return Optional.of(new LibraryDescriptor(symbolicName, parsedVersion, Map.of()));
		}
		// null if the manifest has an invalid dependency range, the error is already reported
		return Optional.ofNullable(getDependencies(lookup, symbolicName, parsedVersion))
				.map(dependencies -> new LibraryDescriptor(symbolicName, parsedVersion, dependencies));
	}

	private static Map<String, VersionRange> getDependencies(final ManifestLookup lookup, final String symbolicName,
			final Version version) {
		final ILibraryPackageDownloader downloader = lookup.downloader();
		final MultiStatus status = lookup.status();

		Path manifestPath = getCachedManifestPath(symbolicName, version);
		if (manifestPath == null) {
			final DownloadResult<Path> result = downloader.downloadManifest(symbolicName, version, lookup.monitor());

			if (result.status() == DownloadResult.Status.NOT_FOUND) {
				if (!lookup.readArchives()) {
					// the version is shown without dependencies, the summary warning names the preference
					lookup.missing().incrementAndGet();
					return Collections.emptyMap();
				}
				manifestPath = readManifestFromArchive(lookup, symbolicName, version);
				if (manifestPath == null) {
					return Collections.emptyMap();
				}
			} else if (result.status() != DownloadResult.Status.OK) {
				status.add(Status.error(MessageFormat.format("{0}: Failed to download manifest for {1} {2}: {3}", //$NON-NLS-1$
						downloader.getName(), symbolicName, version, result.message())));
				return Collections.emptyMap();
			} else {
				manifestPath = result.result();
			}
		}

		final Manifest manifest = ManifestHelper.getManifest(manifestPath);

		final Map<String, VersionRange> dependencies = new HashMap<>();
		if (manifest != null && manifest.getDependencies() != null) {
			for (final Required req : manifest.getDependencies().getRequired()) {
				try {
					dependencies.put(req.getSymbolicName(), VersionComparator.parseVersionRange(req.getVersion()));
				} catch (final IllegalArgumentException e) {
					status.add(Status.error(MessageFormat.format(Messages.OnlineLibraryProvider_InvalidDependencyRange,
							downloader.getName(), symbolicName, version, req.getSymbolicName(), req.getVersion())));
					return null;
				}
			}
		}

		return dependencies;
	}

	private static Path getCachedManifestPath(final String symbolicName, final Version version) {
		final Path cachedManifest = manifestCachePath(symbolicName, version);
		return Files.isRegularFile(cachedManifest) ? cachedManifest : null;
	}

	private static Path manifestCachePath(final String symbolicName, final Version version) {
		return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY).resolve(symbolicName + "-" + version) //$NON-NLS-1$
				.resolve(LibraryManager.MANIFEST);
	}

	/**
	 * Download the archive of a version and keep its manifest in the cache of
	 * downloaded manifests.
	 *
	 * @return the cached manifest, or {@code null} if it could not be read (the
	 *         error is added to the status)
	 */
	private static Path readManifestFromArchive(final ManifestLookup lookup, final String symbolicName,
			final Version version) {
		final String source = lookup.downloader().getName();
		final String key = String.join("\n", source, symbolicName, version.toString()); //$NON-NLS-1$
		final String knownProblem = ARCHIVE_PROBLEMS.get(key);
		if (knownProblem != null) {
			lookup.status().add(Status.error(knownProblem));
			return null;
		}
		final DownloadResult<Path> archive = lookup.downloader().downloadLibrary(symbolicName,
				new VersionRange(VersionRange.LEFT_CLOSED, version, version, VersionRange.RIGHT_CLOSED), version,
				lookup.monitor());
		if (archive.status() != DownloadResult.Status.OK) {
			lookup.status().add(Status.error(MessageFormat.format(Messages.OnlineLibraryProvider_ArchiveDownloadFailed,
					source, symbolicName, version, archive.message())));
			return null;
		}
		try {
			return extractManifest(archive.result(), symbolicName + "-" + version, //$NON-NLS-1$
					manifestCachePath(symbolicName, version), lookup.monitor());
		} catch (final ArchiveContentException e) {
			final String message = e.format(source, symbolicName, version);
			ARCHIVE_PROBLEMS.put(key, message);
			lookup.status().add(Status.error(message));
			return null;
		} catch (final IOException e) {
			lookup.status().add(Status.error(MessageFormat.format(Messages.OnlineLibraryProvider_ArchiveDownloadFailed,
					source, symbolicName, version, e.getMessage())));
			return null;
		}
	}

	/**
	 * Copy the manifest of the library folder of an archive to {@code target}. The
	 * manifest lies directly in a top-level folder of the archive: the folder named
	 * after the library version, otherwise the only folder that has one.
	 *
	 * @return {@code target}
	 * @throws ArchiveContentException if the archive has no such manifest, the
	 *                                 manifests of several other folders, or a
	 *                                 manifest that is empty or cannot be read
	 * @throws IOException             if the archive cannot be read, the manifest
	 *                                 is too large or cannot be written
	 */
	private static Path extractManifest(final Path archive, final String folderName, final Path target,
			final IProgressMonitor monitor) throws IOException {
		if (monitor != null && monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		try (ZipFile zip = new ZipFile(archive.toFile())) {
			final List<ZipEntry> candidates = new ArrayList<>();
			ZipEntry expected = null;
			for (final ZipEntry entry : Collections.list(zip.entries())) {
				final List<String> segments = new ArrayList<>(List.of(entry.getName().replace('\\', '/').split("/"))); //$NON-NLS-1$
				segments.removeIf(segment -> segment.isEmpty() || ".".equals(segment)); //$NON-NLS-1$
				if (!entry.isDirectory() && segments.size() == 2 && LibraryManager.MANIFEST.equals(segments.get(1))
						&& !"..".equals(segments.get(0)) && !MACOS_METADATA_FOLDER.equals(segments.get(0))) { //$NON-NLS-1$
					candidates.add(entry);
					if (folderName.equals(segments.get(0))) {
						expected = entry;
					}
				}
			}
			if (expected == null && candidates.isEmpty()) {
				throw new ArchiveContentException(Messages.OnlineLibraryProvider_ArchiveWithoutManifest);
			}
			if (expected == null && candidates.size() > 1) {
				throw new ArchiveContentException(Messages.OnlineLibraryProvider_ArchiveAmbiguousManifest);
			}
			final ZipEntry manifest = expected != null ? expected : candidates.getFirst();
			copyManifest(zip, manifest, target, monitor);
			return target;
		}
	}

	/**
	 * Copy a manifest of an archive to {@code target}: the size is limited as for a
	 * manifest download, and the file appears only if it is a readable manifest.
	 */
	private static void copyManifest(final ZipFile zip, final ZipEntry manifest, final Path target,
			final IProgressMonitor monitor) throws IOException {
		final long limit = AbstractLibraryPackageDownloader.maxMetadataBytes();
		if (manifest.getSize() > limit) {
			throw manifestTooLarge(manifest, limit);
		}
		Files.createDirectories(target.getParent());
		final Path partial = Files.createTempFile(target.getParent(), "manifest-", ".part"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			long total = 0;
			try (InputStream in = zip.getInputStream(manifest); OutputStream out = Files.newOutputStream(partial)) {
				final byte[] buffer = new byte[8192];
				int read;
				while ((read = in.read(buffer)) != -1) {
					total += read;
					if (total > limit) {
						throw manifestTooLarge(manifest, limit);
					}
					if (monitor != null && monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					out.write(buffer, 0, read);
				}
			}
			if (total == 0 || !isReadableManifest(partial)) {
				throw new ArchiveContentException(Messages.OnlineLibraryProvider_ArchiveInvalidManifest);
			}
			Files.move(partial, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
		} finally {
			Files.deleteIfExists(partial);
		}
	}

	private static boolean isReadableManifest(final Path manifest) {
		try {
			return ManifestHelper.getManifest(manifest) != null;
		} catch (final RuntimeException e) {
			return false;
		}
	}

	private static IOException manifestTooLarge(final ZipEntry manifest, final long limit) {
		return new IOException(MessageFormat.format(Messages.OnlineLibraryProvider_ManifestTooLarge,
				manifest.getName(), Long.valueOf(limit), AbstractLibraryPackageDownloader.MAX_METADATA_BYTES_PROPERTY));
	}

	/**
	 * The archive has no manifest that can be used. Another download of the archive
	 * gives the same result, unlike the other failures.
	 */
	private static final class ArchiveContentException extends IOException {
		private static final long serialVersionUID = 1L;

		/** the message with the placeholders source, library and version */
		private final String pattern;

		ArchiveContentException(final String pattern) {
			super(pattern);
			this.pattern = pattern;
		}

		String format(final String source, final String symbolicName, final Version version) {
			return MessageFormat.format(pattern, source, symbolicName, version);
		}
	}

	@Override
	protected Map<String, List<LibraryDescriptor>> getLibraryLookup() {
		return availableLibs.get();
	}
}
