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
package org.eclipse.fordiac.ide.model.commands.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

public abstract class AbstractMarkersCommand extends AbstractWorkspaceCommand {
	public static final String ORIGINAL_MARKER_ID = "org.eclipse.fordiac.ide.model.commands.util.originalMarkerId"; //$NON-NLS-1$

	private final List<IMarker> markers;
	private final List<MarkerDescription> markerDescriptions;

	protected AbstractMarkersCommand(final String label, final List<IMarker> markers,
			final List<MarkerDescription> markerDescriptions) {
		super(label);
		this.markers = markers;
		this.markerDescriptions = markerDescriptions;
	}

	protected void createMarkers() throws CoreException {
		if (markerDescriptions.isEmpty()) {
			return;
		}
		markers.clear();
		for (final MarkerDescription markerDescription : markerDescriptions) {
			markers.add(getOriginalMarker(markerDescription.createMarker()));
		}
		markerDescriptions.clear();
	}

	protected void deleteMarkers() throws CoreException {
		if (markers.isEmpty()) {
			return;
		}
		markerDescriptions.clear();
		for (IMarker marker : markers) {
			marker = findCurrentMarker(marker);
			if (marker.exists()) {
				markerDescriptions.add(new MarkerDescription(marker));
				marker.delete();
			}
		}
		markers.clear();
	}

	protected static IMarker getOriginalMarker(final IMarker marker) {
		final String originalMarkerId = marker.getAttribute(ORIGINAL_MARKER_ID, null);
		if (originalMarkerId != null) {
			return marker.getResource().getMarker(Long.parseLong(originalMarkerId));
		}
		return marker;
	}

	protected static IMarker findCurrentMarker(final IMarker marker) throws CoreException {
		if (!marker.exists()) {
			for (final IMarker candidate : marker.getResource().findMarkers(null, false, 0)) {
				final String originalMarkerId = candidate.getAttribute(ORIGINAL_MARKER_ID, null);
				if (originalMarkerId != null && Long.parseLong(originalMarkerId) == marker.getId()) {
					return candidate;
				}
			}
		}
		return marker;
	}

	protected List<IResource> getResources() {
		return Stream.concat(markers.stream().map(IMarker::getResource),
				markerDescriptions.stream().map(MarkerDescription::getResource)).toList();
	}

	@Override
	protected ISchedulingRule getSchedulingRule() {
		final List<ISchedulingRule> rules = getResources().stream().map(getWorkspace().getRuleFactory()::markerRule)
				.toList();
		return MultiRule.combine(rules.toArray(new ISchedulingRule[rules.size()]));
	}

	public List<IMarker> getMarkers() {
		return markers;
	}

	public List<MarkerDescription> getMarkerDescriptions() {
		return markerDescriptions;
	}

	public static class MarkerDescription {
		private final IResource resource;
		private final String type;
		private final Map<String, Object> attributes;

		public MarkerDescription(final IMarker marker) throws CoreException {
			resource = marker.getResource();
			type = marker.getType();
			attributes = marker.getAttributes();
			attributes.putIfAbsent(ORIGINAL_MARKER_ID, Long.toString(marker.getId()));
		}

		public MarkerDescription(final IResource resource, final String type, final Map<String, Object> attributes) {
			this.resource = resource;
			this.type = type;
			this.attributes = attributes;
		}

		public IMarker createMarker() throws CoreException {
			return resource.createMarker(type, attributes);
		}

		public IResource getResource() {
			return resource;
		}

		public String getType() {
			return type;
		}

		public Map<String, Object> getAttributes() {
			return attributes;
		}

		@Override
		public String toString() {
			return String.format("MarkerDescription [resource=%s, type=%s, attributes=%s]", resource, type, attributes); //$NON-NLS-1$
		}
	}
}
