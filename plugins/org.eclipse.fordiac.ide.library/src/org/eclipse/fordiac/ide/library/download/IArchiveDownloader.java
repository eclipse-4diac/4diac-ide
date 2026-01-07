/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.download;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public interface IArchiveDownloader {
	/**
	 * Returns configured name of this downloader
	 *
	 * @return the configured name
	 */
	String getName();

	/**
	 * Lists the available libraries
	 *
	 * @param monitor progress monitor, can be {@code null}
	 * @return symbolic names of the available libraries
	 */
	DownloadResult<List<String>> availableLibraries(IProgressMonitor monitor) throws OperationCanceledException;

	/**
	 * Lists the available versions of a specific library
	 *
	 * @param symbolicName symbolic name of library
	 * @param monitor      progress monitor, can be {@code null}
	 * @return the available versions of the specified library, or an empty list if
	 *         library is not available
	 */
	DownloadResult<List<String>> availableVersions(String symbolicName, IProgressMonitor monitor)
			throws OperationCanceledException;

	/**
	 * Download latest available version of the specified library included in the
	 * given range or the preferred version if it is contained in the range
	 *
	 * <p>
	 * Use {@link VersionRange#includes()}) for the range check
	 *
	 * @param symbolicName     symbolic name of library
	 * @param range            version range of the specified library
	 * @param preferredVersion preferred version to be downloaded, ignored if
	 *                         {@code null} or not contained in version range
	 * @param monitor          progress monitor, can be {@code null}
	 * @return {@code DownloadResult} of the downloaded library archive
	 * @throws OperationCanceledException
	 */
	DownloadResult<Path> downloadLibrary(String symbolicName, VersionRange range, Version preferredVersion,
			IProgressMonitor monitor) throws OperationCanceledException;

	/**
	 * Download manifest of the specified library with the given version
	 *
	 * @param symbolicName symbolic name of library
	 * @param version      specific version
	 * @param monitor      progress monitor, can be {@code null}
	 * @return {@code DownloadResult} of the downloaded library manifest
	 * @throws OperationCanceledException
	 */
	DownloadResult<Path> downloadManifest(String symbolicName, Version version, IProgressMonitor monitor)
			throws OperationCanceledException;

	/**
	 * Returns if downloader is active (standard value is {@code true})
	 *
	 * @return {@code true} if active, else {@code false}
	 */
	boolean isActive();

	/**
	 * Set active state
	 *
	 * @param active active state
	 */
	void setActive(boolean active);
}
