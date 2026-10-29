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
package org.eclipse.fordiac.ide.library.download;

import java.util.List;

/**
 * Supplies the library package downloaders configured for a library source.
 *
 * <p>
 * Providers are registered with the {@code provider} element of the extension
 * point {@code org.eclipse.fordiac.ide.library.LibraryPackageDownloader}. A
 * source with one fixed endpoint can register its
 * {@link ILibraryPackageDownloader} with the {@code client} element instead.
 *
 * @see LibraryPackageDownloaderProvider
 */
@FunctionalInterface
public interface ILibraryPackageDownloaderProvider {

	/**
	 * Returns one downloader per configured endpoint in configuration order.
	 *
	 * <p>
	 * Every call reads the current local configuration without contacting the
	 * endpoints, because callers may run on the UI thread. Activation is filtered
	 * centrally by {@link LibraryPackageDownloaderProvider}.
	 *
	 * @return the configured downloaders, or an empty list if nothing is configured;
	 *         neither the list nor its elements may be {@code null}
	 */
	List<ILibraryPackageDownloader> getConfiguredDownloaders();
}
