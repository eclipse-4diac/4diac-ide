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

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (o1 == null) return (o2 == null) ? 0 : -1;
		if (o1.equals(o2)) return 0;
		if (o2 == null) return 1;
		
		String[] v1 = o1.split("\\.");
		String[] v2 = o2.split("\\.");
		
		int shared = Math.min(v1.length, v2.length);
		for (int i = 0; i < shared; i++) {
			int comp = Integer.parseInt(v1[i]) - Integer.parseInt(v2[i]);
			if (comp != 0) return Integer.signum(comp);
		}
		for (int i = shared; i < v1.length; i++) {
			int comp = Integer.parseInt(v1[i]);
			if (comp > 0) return 1;
		}
		for (int i = shared; i < v2.length; i++) {
			int comp = Integer.parseInt(v2[i]);
			if (comp > 0) return -1;
		}
		
		return 0;
	}

}
