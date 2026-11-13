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

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The parts of the GitLab REST API responses read by the
 * {@link GitLabDownloader}. Components missing in a response are {@code null}.
 */
final class GitLabResponses {

	/**
	 * An element of {@code GET /projects}. {@code packages_enabled} is deprecated
	 * since GitLab 17.10 in favour of {@code package_registry_access_level}.
	 */
	record ProjectResponse(Long id, String name, @SerializedName("packages_enabled") JsonElement packagesEnabled,
			@SerializedName("package_registry_access_level") String packageRegistryAccessLevel) {
	}

	/** An element of {@code GET /projects/:id/packages}. */
	record PackageResponse(Long id, String name, String version,
			@SerializedName("package_type") String packageType) {
	}

	/** An element of {@code GET /projects/:id/packages/:package_id/package_files}. */
	record PackageFileResponse(@SerializedName("file_name") JsonElement fileName) {
	}

	private GitLabResponses() {
		throw new UnsupportedOperationException();
	}
}
