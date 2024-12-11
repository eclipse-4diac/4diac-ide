/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;

public abstract class ResourceMarkerGraphicalAnnotationModel extends AbstractGraphicalAnnotationModel {

	private final IResource resource;
	private final Map<IMarker, GraphicalMarkerAnnotation> markerAnnotations = new ConcurrentHashMap<>();
	private final IResourceChangeListener resourceChangeListener = this::resourceChanged;

	protected ResourceMarkerGraphicalAnnotationModel(final IResource resource) {
		this.resource = Objects.requireNonNull(resource);
		resource.getWorkspace().addResourceChangeListener(resourceChangeListener);
	}

	@Override
	public void reload() {
		final Set<IMarker> markers = findMarkers();
		final Set<GraphicalAnnotation> added = new HashSet<>();
		final Set<GraphicalAnnotation> removed = new HashSet<>();
		markerAnnotations.keySet().forEach(marker -> markerRemoved(marker, removed));
		markers.forEach(marker -> markerAdded(marker, added));
		updateAnnotations(added, removed, Collections.emptySet());
	}

	@Override
	public void dispose() {
		resource.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		markerAnnotations.clear();
	}

	protected void resourceChanged(final IResourceChangeEvent event) {
		final IResourceDelta delta = event.getDelta();
		if (delta != null) {
			final IResourceDelta child = delta.findMember(resource.getFullPath());
			if (child != null) {
				markersChanged(child.getMarkerDeltas());
			}
		}
	}

	protected void markersChanged(final IMarkerDelta[] deltas) {
		if (deltas.length == 0) {
			return;
		}

		final Set<GraphicalAnnotation> added = new HashSet<>();
		final Set<GraphicalAnnotation> removed = new HashSet<>();
		final Set<GraphicalAnnotation> changed = new HashSet<>();

		for (final IMarkerDelta delta : deltas) {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED -> markerAdded(delta.getMarker(), added);
			case IResourceDelta.REMOVED -> markerRemoved(delta.getMarker(), removed);
			case IResourceDelta.CHANGED -> markerChanged(delta, added, removed, changed);
			default -> {
				// empty
			}
			}
		}

		updateAnnotations(added, removed, changed);
	}

	protected void markerAdded(final IMarker marker, final Set<GraphicalAnnotation> added) {
		final GraphicalMarkerAnnotation annotation = markerAnnotations.computeIfAbsent(marker,
				this::createMarkerAnnotation);
		if (annotation != null) {
			added.add(annotation);
		}
	}

	protected void markerRemoved(final IMarker marker, final Set<GraphicalAnnotation> removed) {
		markerAnnotations.computeIfPresent(marker, (unused, annotation) -> {
			removed.add(annotation);
			return null;
		});
	}

	protected void markerChanged(final IMarkerDelta delta, final Set<GraphicalAnnotation> added,
			final Set<GraphicalAnnotation> removed, final Set<GraphicalAnnotation> changed) {
		markerAnnotations.computeIfPresent(delta.getMarker(), (marker, annotation) -> {
			if (isTargetChanged(delta)) {
				removed.add(annotation);
				annotation = createMarkerAnnotation(marker);
				if (annotation != null) {
					added.add(annotation);
				}
			} else {
				annotation.refresh();
				changed.add(annotation);
			}
			return annotation;
		});
	}

	protected abstract GraphicalMarkerAnnotation createMarkerAnnotation(IMarker marker);

	protected abstract boolean isTargetChanged(IMarkerDelta delta);

	protected Set<IMarker> findMarkers() {
		try {
			return Set.of(resource.findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO));
		} catch (final CoreException e) {
			return Collections.emptySet();
		}
	}

	@Override
	public long getModificationStamp() {
		return resource.getModificationStamp();
	}

	public IResource getResource() {
		return resource;
	}
}
