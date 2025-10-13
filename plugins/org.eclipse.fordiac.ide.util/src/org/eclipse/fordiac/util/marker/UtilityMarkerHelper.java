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
package org.eclipse.fordiac.util.marker;

import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;

public class UtilityMarkerHelper {

	public static EObject getMarkedElement(final MarkerDescriptor markerDescriptor, final IResource resource) {
		try {
			return Stream.of(resource.findMarkers(markerDescriptor.ID(), true, IResource.DEPTH_ZERO))
					.map(UtilityMarkerHelper::getTarget).findAny().orElse(null);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Error accessing marked element", e); //$NON-NLS-1$
		}
		return null;
	}

	public static IMarker getMarker(final MarkerDescriptor markerDescriptor) {
		if (EditorUtils.getCurrentActiveEditor()
				.getAdapter(LibraryElement.class) instanceof final AutomationSystem sys) {
			try {
				return Stream
						.of(sys.getTypeEntry().getFile().findMarkers(markerDescriptor.ID(), true, IResource.DEPTH_ZERO))
						.findAny().orElse(null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Error accessing marked element", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	public static void deleteElementMarker(final MarkerDescriptor markerDescriptor, final IResource resource) {
		deleteMarker(markerDescriptor.ID(), resource);
	}

	public static void deleteElementMarker(final String id) {
		if ((EditorUtils.getCurrentActiveEditor()
				.getAdapter(LibraryElement.class) instanceof final AutomationSystem sys)
				&& (sys.getTypeEntry().getFile() instanceof final IResource resource)) {
			deleteMarker(id, resource);
		}
	}

	private static void deleteMarker(final String id, final IResource resource) {
		try {
			resource.deleteMarkers(id, false, IResource.DEPTH_ZERO);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Error deleting marker", e); //$NON-NLS-1$
		}
	}

	public static void addElementMarker(final MarkerDescriptor markerDescriptor, final IResource resource,
			final EObject target) {
		try {
			ErrorMarkerBuilder.createErrorMarkerBuilder(markerDescriptor.name()).setType(markerDescriptor.ID())
					.setTarget(target).setSeverity(IMarker.SEVERITY_INFO).createMarker(resource);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Cannot add marker", e); //$NON-NLS-1$
		}
	}

	public static boolean hasMarker(final MarkerDescriptor markerDescriptor, final IResource resource) {
		try {
			return Stream.of(resource.findMarkers(markerDescriptor.ID(), true, IResource.DEPTH_ZERO)).findAny()
					.isPresent();
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Cannot fetch marker", e); //$NON-NLS-1$
		}
		return false;
	}

	public static Stream<IMarker> getAllUtilityMarkers() {
		if (EditorUtils.getCurrentActiveEditor()
				.getAdapter(LibraryElement.class) instanceof final AutomationSystem sys) {
			try {
				return Stream.concat(
						Stream.of(sys.getTypeEntry().getFile().findMarkers(MarkerDescriptor.CONNECTION_SOURCE.ID(),
								false, IResource.DEPTH_ZERO)),
						Stream.of(sys.getTypeEntry().getFile().findMarkers(MarkerDescriptor.PREDECESSOR.ID(), false,
								IResource.DEPTH_ZERO)));
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Cannot fetch marker", e); //$NON-NLS-1$
			}
		}
		return Stream.empty();
	}

	public static EObject getTarget(final IMarker marker) {
		if (EditorUtils.getCurrentActiveEditor()
				.getAdapter(LibraryElement.class) instanceof final AutomationSystem sys) {
			return sys.eResource().getEObject(FordiacErrorMarker.getTargetUri(marker).fragment());
		}
		return null;
	}

	private UtilityMarkerHelper() {
		throw new UnsupportedOperationException();
	}

}
