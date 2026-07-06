/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.provider;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.IArchiveDownloader;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class OnlineLibraryProvider extends AbstractLibraryProvider {

	private final AtomicReference<Map<String, List<LibraryDescriptor>>> availableLibs = new AtomicReference<>(Map.of());

	@Override
	public IStatus refresh(final IProgressMonitor monitor, final boolean fetchDependencies) {
		final Map<String, List<LibraryDescriptor>> refreshedLibs = new HashMap<>();

		final List<IArchiveDownloader> downloaderExtensions = TypeLibraryManager
				.listExtensions(LibraryManager.DOWNLOADER_EXTENSION, IArchiveDownloader.class);

		if (downloaderExtensions.isEmpty()) {
			return Status.error("No downloader extension available"); //$NON-NLS-1$
		}

		final MultiStatus status = new MultiStatus(PLUGIN_ID, IStatus.OK, "Download versions status"); //$NON-NLS-1$

		for (final IArchiveDownloader downloaderExtension : downloaderExtensions) {
			for (final IArchiveDownloader endpointDownloader : downloaderExtension.convertEndpointsToDownloader()) {

				if (!endpointDownloader.isActive()) {
					continue;
				}

				final DownloadResult<Map<String, List<String>>> downloadResult = endpointDownloader
						.availableLibrariesAndVersions(monitor);

				if (downloadResult.status() == DownloadResult.Status.OK) {
					refreshedLibs.putAll(toLibraryDescriptors(downloadResult.result(), fetchDependencies,
							endpointDownloader, monitor, status));
					status.add(Status.info(endpointDownloader.getName() + ": OK")); //$NON-NLS-1$
				} else {
					status.add(Status.error(endpointDownloader.getName() + ": " + downloadResult.message())); //$NON-NLS-1$
				}
			}
		}

		availableLibs.set(Map.copyOf(refreshedLibs));

		return status;
	}

	private static Map<String, List<LibraryDescriptor>> toLibraryDescriptors(final Map<String, List<String>> versions,
			final boolean fetchDependencies, final IArchiveDownloader downloader, final IProgressMonitor monitor,
			final MultiStatus status) {
		return versions.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().map(version -> {
					final Version parsedVersion = Version.valueOf(version);
					final Map<String, VersionRange> dependencies = fetchDependencies
							? getDependencies(downloader, entry.getKey(), parsedVersion, monitor, status)
							: Map.of();

					return new LibraryDescriptor(entry.getKey(), parsedVersion, dependencies);
				}).toList()));
	}

	private static Map<String, VersionRange> getDependencies(final IArchiveDownloader downloader,
			final String symbolicName, final Version version, final IProgressMonitor monitor,
			final MultiStatus status) {

		final DownloadResult<Path> result = downloader.downloadManifest(symbolicName, version, monitor);

		if (result.status() != DownloadResult.Status.OK) {
			status.add(Status.error(MessageFormat.format("{0}: Failed to download manifest for {1} {2}: {3}", //$NON-NLS-1$
					downloader.getName(), symbolicName, version, result.message())));
			return Collections.emptyMap();
		}

		final Manifest manifest = ManifestHelper.getFolderManifest(result.result());

		final Map<String, VersionRange> dependencies = new HashMap<>();
		if (manifest != null && manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().forEach(req -> dependencies.put(req.getSymbolicName(),
					VersionComparator.parseVersionRange(req.getVersion())));
		}

		return dependencies;
	}

	@Override
	protected Map<String, List<LibraryDescriptor>> getLibraryLookup() {
		return availableLibs.get();
	}
}