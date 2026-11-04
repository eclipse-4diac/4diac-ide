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
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.provider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class OnlineLibraryProvider extends AbstractLibraryProvider {

	private final AtomicReference<Map<String, List<LibraryDescriptor>>> availableLibs = new AtomicReference<>(Map.of());
	private final LibraryPackageDownloaderProvider downloaderProvider;

	public OnlineLibraryProvider() {
		this(new LibraryPackageDownloaderProvider());
	}

	public OnlineLibraryProvider(final LibraryPackageDownloaderProvider downloaderProvider) {
		this.downloaderProvider = Objects.requireNonNull(downloaderProvider);
	}

	@Override
	public IStatus refresh(final IProgressMonitor monitor, final boolean fetchDependencies) {
		final Map<String, List<LibraryDescriptor>> refreshedLibs = new HashMap<>();
		final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.OK, "Download versions status"); //$NON-NLS-1$
		boolean sourceConfigured = false;
		final var sources = downloaderProvider.getActiveDownloaders();
		status.merge(sources.status());

		for (final ILibraryPackageDownloader downloader : sources.downloaders()) {
			final DownloadResult<Map<String, List<String>>> downloadResult = downloader
					.availableLibrariesAndVersions(monitor);

			switch (downloadResult.status()) {
			case OK -> {
				refreshedLibs.putAll(
						toLibraryDescriptors(downloadResult.result(), fetchDependencies, downloader, monitor, status));
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
		availableLibs.set(Map.copyOf(refreshedLibs));

		return status;
	}

	private static Map<String, List<LibraryDescriptor>> toLibraryDescriptors(final Map<String, List<String>> versions,
			final boolean fetchDependencies, final ILibraryPackageDownloader downloader, final IProgressMonitor monitor,
			final MultiStatus status) {
		final Map<String, List<LibraryDescriptor>> descriptors = new HashMap<>();
		for (final Map.Entry<String, List<String>> library : versions.entrySet()) {
			final List<LibraryDescriptor> offered = new ArrayList<>();
			for (final String version : library.getValue()) {
				toLibraryDescriptor(library.getKey(), version, fetchDependencies, downloader, monitor, status)
						.ifPresent(offered::add);
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
			final boolean fetchDependencies, final ILibraryPackageDownloader downloader, final IProgressMonitor monitor,
			final MultiStatus status) {
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
		return Optional.ofNullable(getDependencies(downloader, symbolicName, parsedVersion, monitor, status))
				.map(dependencies -> new LibraryDescriptor(symbolicName, parsedVersion, dependencies));
	}

	private static Map<String, VersionRange> getDependencies(final ILibraryPackageDownloader downloader,
			final String symbolicName, final Version version, final IProgressMonitor monitor,
			final MultiStatus status) {

		Path manifestPath = getCachedManifestPath(symbolicName, version);
		if (manifestPath == null) {
			final DownloadResult<Path> result = downloader.downloadManifest(symbolicName, version, monitor);

			if (result.status() != DownloadResult.Status.OK) {
				status.add(Status.error(MessageFormat.format("{0}: Failed to download manifest for {1} {2}: {3}", //$NON-NLS-1$
						downloader.getName(), symbolicName, version, result.message())));
				return Collections.emptyMap();
			}
			manifestPath = result.result();
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
		final Path cachedManifest = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY).resolve(symbolicName + "-" + version) //$NON-NLS-1$
				.resolve(LibraryManager.MANIFEST);
		return Files.isRegularFile(cachedManifest) ? cachedManifest : null;
	}

	@Override
	protected Map<String, List<LibraryDescriptor>> getLibraryLookup() {
		return availableLibs.get();
	}
}
