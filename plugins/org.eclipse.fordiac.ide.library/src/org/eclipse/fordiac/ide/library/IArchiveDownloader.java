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
package org.eclipse.fordiac.ide.library;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public interface IArchiveDownloader {
	/**
	 * Lists the available libraries
	 *
	 * @return symbolic names of the available libraries
	 */
	List<String> availableLibraries() throws IOException;

	/**
	 * Lists the available versions of a specific library
	 *
	 * @param symbolicName symbolic name of library
	 * @return the available versions of the specified library, or an empty list if
	 *         library is not available
	 */
	List<String> availableVersions(String symbolicName) throws IOException;

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
	 * @return {@code Path} of the downloaded library archive, or {@code null} if
	 *         archive couldn't be downloaded
	 */
	Path downloadLibrary(String symbolicName, VersionRange range, Version preferredVersion) throws IOException;

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
