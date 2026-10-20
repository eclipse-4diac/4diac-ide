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

import java.util.List;

import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloaderProvider;

/**
 * Additional library source backed by local fixtures. The registered contribution
 * always supplies one fixture endpoint; injected instances have their own
 * configuration and share no mutable state.
 */
public final class MockDownloaderProvider implements ILibraryPackageDownloaderProvider {

	public static final String FIXTURE_ENDPOINT = "Fixture endpoint"; //$NON-NLS-1$

	private List<ILibraryPackageDownloader> endpoints;

	public MockDownloaderProvider() {
		this(List.of(new MockEndpoint(FIXTURE_ENDPOINT)));
	}

	public MockDownloaderProvider(final List<ILibraryPackageDownloader> endpoints) {
		this.endpoints = List.copyOf(endpoints);
	}

	@Override
	public List<ILibraryPackageDownloader> getConfiguredDownloaders() {
		return endpoints;
	}

	public void configure(final ILibraryPackageDownloader... configuredEndpoints) {
		endpoints = List.of(configuredEndpoints);
	}
}
