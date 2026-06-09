/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class UtilityMarkerHelper {

	public static final String PREDECESSOR_MARKER_ID = "org.eclipse.fordiac.ide.model.ui.predecessor"; //$NON-NLS-1$
	public static final String CONNECTION_SRC_MARKER_ID = "org.eclipse.fordiac.ide.model.ui.connectionSource"; //$NON-NLS-1$

	public static EObject getMarkedElement(final String markerId, final EObject refElement) {
		final EObject rootContainer = EcoreUtil.getRootContainer(refElement);
		if (!(rootContainer instanceof final LibraryElement libEl) || libEl.getTypeEntry() == null) {
			return null;
		}

		final IResource resource = libEl.getTypeEntry().getFile();

		try {
			return Stream.of(resource.findMarkers(markerId, true, IResource.DEPTH_ZERO))
					.map(marker -> FordiacErrorMarker.getTargetRelative(marker, libEl)).findAny().orElse(null);
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning("Error accessing marked element", e); //$NON-NLS-1$
		}
		return null;
	}

	public static void deleteElementMarker(final String markerId, final IResource resource) {
		FordiacMarkerHelper.updateMarkers(resource, markerId, List.of());
	}

	public static void setMarkedElement(final String markerId, final EObject target) {
		final EObject rootContainer = EcoreUtil.getRootContainer(target);
		if (!(rootContainer instanceof final LibraryElement libEl) || libEl.getTypeEntry() == null) {
			return;
		}
		final IResource resource = libEl.getTypeEntry().getFile();

		final ErrorMarkerBuilder markerBuilder = ErrorMarkerBuilder.createErrorMarkerBuilder(markerId).setType(markerId)
				.setTarget(target).setSeverity(IMarker.SEVERITY_INFO);
		FordiacMarkerHelper.updateMarkers(resource, markerId, List.of(markerBuilder));
	}

	private UtilityMarkerHelper() {
		throw new UnsupportedOperationException();
	}

}
