/*******************************************************************************
 * Copyright (c) 2024  Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.model.util;

import java.lang.module.ModuleDescriptor.Version;
import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

	@Override
	public int compare(final String o1, final String o2) {

		if (o1 == null || o1.isBlank()) {
			return (o2 == null || o2.isBlank()) ? 0 : -1;
		}
		if (o1.equals(o2)) {
			return 0;
		}
		if (o2 == null || o2.isBlank()) {
			return 1;
		}

		final Version v1 = Version.parse(o1);
		final Version v2 = Version.parse(o2);

		return v1.compareTo(v2);
	}

	@SuppressWarnings("nls")
	public boolean contains(final String version, final String localVersion) {
		final Version locVersion = Version.parse(localVersion);
		if ((version.startsWith("(") || version.startsWith("[")) && (version.endsWith(")") || version.endsWith("]"))
				&& version.contains("-")) {
			final Version lowerBound = Version.parse(version.substring(1, version.indexOf("-")));
			final Version upperBound = Version.parse(version.substring(version.indexOf("-") + 1, version.length() - 1));
			return (((version.startsWith("(") && lowerBound.compareTo(locVersion) < 0)
					|| (version.startsWith("[") && lowerBound.compareTo(locVersion) <= 0))
					&& ((version.endsWith(")") && upperBound.compareTo(locVersion) > 0)
							|| (version.endsWith("]") && upperBound.compareTo(locVersion) >= 0)));

		}
		if (!(version.contains("(") || version.contains("[") || version.contains("]") || version.contains(")")
				|| version.contains("-"))) {
			final Version singleVersion = Version.parse(version);
			return (singleVersion.compareTo(locVersion) == 0);
		}
		return false;
	}
}
