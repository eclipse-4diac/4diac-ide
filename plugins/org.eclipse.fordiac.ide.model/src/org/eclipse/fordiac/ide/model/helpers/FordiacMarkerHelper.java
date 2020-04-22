/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public final class FordiacMarkerHelper {

	private static final String TARGET_TYPE = "Target.Type"; //$NON-NLS-1$

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$

	public static boolean markerTargetsFBNetworkElement(Map<String, Object> attrs) {
		return FB_NETWORK_ELEMENT_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static void addTargetIdentifier(INamedElement element, Map<String, String> attrs) {
		String targetIdentifier = getTargetIdentifier(element);
		if (null != targetIdentifier) {
			attrs.put(TARGET_TYPE, targetIdentifier);
		}
	}

	private static String getTargetIdentifier(INamedElement element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		return null;
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}
}
