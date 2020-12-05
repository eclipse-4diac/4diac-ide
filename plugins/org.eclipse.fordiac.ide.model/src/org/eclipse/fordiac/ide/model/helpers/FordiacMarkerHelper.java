/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class FordiacMarkerHelper {

	public static final String TARGET_TYPE = "Target.Type"; //$NON-NLS-1$

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$
	private static final String CONNECTION_TARGET = "Connection"; //$NON-NLS-1$

	public static boolean markerTargetsFBNetworkElement(final Map<String, Object> attrs) {
		return FB_NETWORK_ELEMENT_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static void addTargetIdentifier(final INamedElement element, final Map<String, String> attrs) {
		final String targetIdentifier = getTargetIdentifier(element);
		if (null != targetIdentifier) {
			attrs.put(TARGET_TYPE, targetIdentifier);
		}
	}

	public static void addLocation(final INamedElement element, final Map<String, String> attrs) {
		final String location = getLocation(element);
		if (null != location) {
			attrs.put(IMarker.LOCATION, location);
		}
	}

	public static String getTargetIdentifier(final INamedElement element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		if (element instanceof Connection) {
			return CONNECTION_TARGET;
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
		builder.deleteCharAt(builder.length() - 1); // remove the last dot
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
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}
}
