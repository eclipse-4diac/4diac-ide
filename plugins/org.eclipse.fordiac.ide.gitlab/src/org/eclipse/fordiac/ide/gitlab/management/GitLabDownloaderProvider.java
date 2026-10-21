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
package org.eclipse.fordiac.ide.gitlab.management;

import java.util.List;

import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpointsStore;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloaderProvider;

/**
 * Provides one {@link GitLabDownloader} for each GitLab endpoint configured in
 * the preferences.
 */
public final class GitLabDownloaderProvider implements ILibraryPackageDownloaderProvider {

	@Override
	public List<ILibraryPackageDownloader> getConfiguredDownloaders() {
		return GitLabEndpointsStore.loadEndpoints().stream().<ILibraryPackageDownloader>map(GitLabDownloader::new)
				.toList();
	}
}
