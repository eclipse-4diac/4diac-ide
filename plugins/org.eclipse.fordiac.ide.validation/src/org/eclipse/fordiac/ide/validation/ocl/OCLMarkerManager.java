/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.validation.handlers.ValidationMarkerConstants;

public final class OCLMarkerManager {

	public static final String OWNER_PROJECT = ValidationMarkerConstants.TYPE + ".ownerProject"; //$NON-NLS-1$

	public static void replaceMarkers(final IProject owner, final List<OCLMarker> markers) throws CoreException {
		deleteMarkers(owner, getMarkerProjects(owner));
		final String ownerId = getOwnerId(owner);
		for (final OCLMarker marker : markers) {
			if (marker.resource().isAccessible()) {
				marker.builder().addAdditionalAttributes(Map.of(OWNER_PROJECT, ownerId)).createMarker(marker.resource());
			}
		}
	}

	public static void deleteMarkers(final IProject owner) throws CoreException {
		if (owner == null || !owner.isAccessible()) {
			return;
		}
		deleteMarkers(owner, List.of(ResourcesPlugin.getWorkspace().getRoot()));
	}

	private static void deleteMarkers(final IProject owner, final List<? extends IResource> markerRoots)
			throws CoreException {
		final String ownerId = getOwnerId(owner);
		for (final IResource markerRoot : markerRoots) {
			for (final IMarker marker : markerRoot.findMarkers(ValidationMarkerConstants.TYPE, true,
					IResource.DEPTH_INFINITE)) {
				final String markerOwner = marker.getAttribute(OWNER_PROJECT, null);
				if (ownerId.equals(markerOwner)
						|| (markerOwner == null && owner.equals(marker.getResource().getProject()))) {
					marker.delete();
				}
			}
		}
	}

	private static List<IProject> getMarkerProjects(final IProject owner) {
		return Stream.concat(Stream.of(owner), OCLSourceScanner.findReferencedProjects(owner).stream())
				.filter(IProject::isAccessible).toList();
	}

	private static String getOwnerId(final IProject owner) {
		return owner.getFullPath().toPortableString();
	}

	private OCLMarkerManager() {
		throw new UnsupportedOperationException();
	}
}
