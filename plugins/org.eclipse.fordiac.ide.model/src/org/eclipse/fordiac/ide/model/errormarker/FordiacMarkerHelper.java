/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *               2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - extension for connection error markers
 *   Michael Oberlehner
 *               - Refactoring of API
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.errormarker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

public final class FordiacMarkerHelper {

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$
	private static final String CONNECTION_TARGET = "Connection"; //$NON-NLS-1$
	private static final String VALUE_TARGET = "Value"; //$NON-NLS-1$

	public static boolean markerTargetsFBNetworkElement(final IMarker marker) {
		return FB_NETWORK_ELEMENT_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static boolean markerTargetsConnection(final IMarker marker) {
		return CONNECTION_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static boolean markerTargetsValue(final IMarker marker) {
		return VALUE_TARGET.equals(marker.getAttribute(ErrorMarkerBuilder.TARGET_TYPE, null));
	}

	public static String getTargetIdentifier(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		if (element instanceof Connection) {
			return CONNECTION_TARGET;
		}
		if (element instanceof Value) {
			return VALUE_TARGET;
		}
		return null;
	}

	public static String getLocation(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return getFBNElementLocation((FBNetworkElement) element);
		}
		if (element instanceof FBNetwork) {
			return getFBNetworkLocation((FBNetwork) element);
		}
		return null;
	}

	private static String getFBNElementLocation(final FBNetworkElement element) {
		final EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return element.getName();
		}
		final StringBuilder builder = new StringBuilder(element.getName());
		createHierarchicalName(element.eContainer().eContainer(), builder);
		return builder.toString();
	}

	private static String getFBNetworkLocation(final FBNetwork element) {
		final EObject container = element.eContainer();
		if (container instanceof FBType) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder builder = new StringBuilder();
		createHierarchicalName(container, builder);
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1); // remove the last dot
		}

		return builder.toString();
	}

	private static void createHierarchicalName(final EObject container, final StringBuilder builder) {
		EObject runner = container;
		while (runner instanceof SubApp) {
			final SubApp parent = (SubApp) runner;
			builder.insert(0, '.');
			builder.insert(0, parent.getName());
			runner = parent.getFbNetwork().eContainer();
		}
		if (runner instanceof Application) {
			builder.insert(0, '.');
			builder.insert(0, ((Application) runner).getName());
		}

		if (runner instanceof Resource) {
			builder.insert(0, '.');
			builder.insert(0, ((Resource) runner).getName());
			if (runner.eContainer() != null) {
				builder.insert(0, '.');
				builder.insert(0, ((Device) runner.eContainer()).getName());
			}
		}
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}

}
