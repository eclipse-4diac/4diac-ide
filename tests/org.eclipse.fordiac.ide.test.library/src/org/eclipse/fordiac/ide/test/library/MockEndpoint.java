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

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Endpoint of the {@link MockDownloaderProvider}. Serves the same test archives
 * as the {@link MockDownloader}, but has its own name and activation state, can
 * stand for an endpoint with a missing or incomplete configuration.
 */
public class MockEndpoint extends MockDownloader {
	private final String name;
	private final DownloadResult.Status configuration;
	private volatile boolean active = true;

	/**
	 * Creates a configured endpoint.
	 *
	 * @param name name of the endpoint
	 */
	public MockEndpoint(final String name) {
		this(name, DownloadResult.Status.OK);
	}

	/**
	 * Creates an endpoint with the given configuration state.
	 *
	 * @param name          name of the endpoint
	 * @param configuration {@code OK} for a configured endpoint, otherwise the
	 *                      status of all requests, e.g. {@code NO_CONFIG} or
	 *                      {@code CONFIG_ERROR}
	 */
	public MockEndpoint(final String name, final DownloadResult.Status configuration) {
		this.name = name;
		this.configuration = configuration;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(final boolean active) {
		this.active = active;
	}

	@Override
	public DownloadResult<List<String>> availableLibraries(final IProgressMonitor monitor)
			throws OperationCanceledException {
		if (configuration != DownloadResult.Status.OK) {
			return notAvailable();
		}
		return super.availableLibraries(monitor);
	}

	@Override
	public DownloadResult<List<String>> availableVersions(final String symbolicName, final IProgressMonitor monitor)
			throws OperationCanceledException {
		if (configuration != DownloadResult.Status.OK) {
			return notAvailable();
		}
		return super.availableVersions(symbolicName, monitor);
	}

	@Override
	public DownloadResult<Map<String, List<String>>> availableLibrariesAndVersions(final IProgressMonitor monitor)
			throws OperationCanceledException {
		if (configuration != DownloadResult.Status.OK) {
			return notAvailable();
		}
		return super.availableLibrariesAndVersions(monitor);
	}

	@Override
	public DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
			final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
		if (configuration != DownloadResult.Status.OK) {
			return notAvailable();
		}
		return super.downloadLibrary(symbolicName, range, preferredVersion, monitor);
	}

	@Override
	public DownloadResult<Path> downloadManifest(final String symbolicName, final Version version,
			final IProgressMonitor monitor) throws OperationCanceledException {
		if (configuration != DownloadResult.Status.OK) {
			return notAvailable();
		}
		return super.downloadManifest(symbolicName, version, monitor);
	}

	private <T> DownloadResult<T> notAvailable() {
		return new DownloadResult<>(configuration, MessageFormat.format(Messages.Endpoint_Not_Available, name,
				configuration));
	}
}
