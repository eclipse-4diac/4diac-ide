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
package org.eclipse.fordiac.ide.test.library;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.library.IArchiveDownloader;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class MockDownloader implements IArchiveDownloader {
	private boolean active = false;
	private final VersionComparator versionComparator = new VersionComparator();

	@SuppressWarnings("nls")
	private final Map<String, List<String>> archiveMap = Map.ofEntries(
			Map.entry("test01", List.of("1.0.0", "1.1.0", "1.5.0", "2.0.0")),
			Map.entry("test02", List.of("1.0.0", "1.1.0")), Map.entry("test03", List.of("1.0.0", "1.1.0")),
			Map.entry("test04", List.of("1.0.0", "1.1.0")), Map.entry("test05", List.of("1.0.0")),
			Map.entry("test06", List.of("1.0.0")), Map.entry("test07", List.of("1.0.0")));
	private final static String FORMAT_STRING = "data/%s-%s.zip"; //$NON-NLS-1$

	@Override
	public List<String> availableLibraries() throws IOException {
		return List.copyOf(archiveMap.keySet());
	}

	@Override
	public List<String> availableVersions(final String symbolicName) throws IOException {
		return archiveMap.getOrDefault(symbolicName, Collections.emptyList());
	}

	@Override
	public Path downloadLibrary(final String symbolicName, final VersionRange range, final Version preferredVersion)
			throws IOException {
		if (!archiveMap.containsKey(symbolicName)) {
			return null;
		}
		final var st = availableVersions(symbolicName).stream();
		if (range != null) {
			st.filter(v -> VersionComparator.contains(range, v));
		}
		final var archives = st.sorted(versionComparator).toList();

		if (archives.isEmpty()) {
			return null;
		}
		String version = null;
		if (preferredVersion != null) {
			version = archives.stream().filter(v -> preferredVersion.compareTo(new Version(v)) == 0).findFirst()
					.orElse(null);
		}
		if (version == null) {
			version = archives.getLast();
		}

		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.library"); //$NON-NLS-1$

		try {
			return Paths.get(FileLocator
					.toFileURL(FileLocator.find(bundle,
							new org.eclipse.core.runtime.Path(String.format(FORMAT_STRING, symbolicName, version))))
					.toURI());
		} catch (URISyntaxException | IOException e) {
			// empty
		}
		return null;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(final boolean active) {
		this.active = active;
	}

}
