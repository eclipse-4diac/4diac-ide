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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

class ResolveNode {
	private final String symbolicName;
	private final Version version;
	private final java.net.URI uri;
	private final String error;
	private final Map<String, VersionRange> dependencies;

	public ResolveNode(final String symbolicName, final String error) {
		this(symbolicName, Version.emptyVersion, null, error);
	}

	public ResolveNode(final String symbolicName, final Version version, final java.net.URI uri, final String error) {
		this.symbolicName = symbolicName;
		this.version = version;
		this.uri = uri;
		this.error = error;
		dependencies = new HashMap<>();
	}

	public ResolveNode(final LibraryRecord lib) {
		this(lib.symbolicName(), lib.version(), lib.uri(), null);
		final Manifest manifest = ManifestHelper.getFolderManifest(lib.path());
		if (manifest != null && manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().forEach(req -> {
				dependencies.put(req.getSymbolicName(), VersionComparator.parseVersionRange(req.getVersion()));
			});
		}
	}

	public Version getVersion() {
		return version;
	}

	public java.net.URI getUri() {
		return uri;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public Map<String, VersionRange> getDependencies() {
		return dependencies;
	}

	public boolean isValid() {
		return uri != null && error == null;
	}

	public String getError() {
		return error;
	}
}