/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.changelistener;

import java.util.List;

public class ExcludedElements {
	private static final List<String> paths = List.of("MANIFEST.MF"); //$NON-NLS-1$

	public static boolean contains(final String pathToString) {
		if (paths.contains(pathToString)) {
			return true;
		}
		return false;
	}

	public static String stringRep() {
		return paths.toString();
	}

}
