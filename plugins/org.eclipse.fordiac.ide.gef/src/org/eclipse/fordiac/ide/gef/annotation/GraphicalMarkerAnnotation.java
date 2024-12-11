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
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

public class GraphicalMarkerAnnotation extends GraphicalAnnotation {

	private final IMarker marker;
	private String text;
	private String location;
	private Map<String, Object> attributes;

	protected GraphicalMarkerAnnotation(final IMarker marker, final String type, final Object target) {
		super(type, target);
		this.marker = marker;
		refresh();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public Object getAttribute(final String attributeName) {
		return attributes.get(attributeName);
	}

	@Override
	public Map<String, Object> getAttributes() {
		try {
			return Objects.requireNonNullElse(marker.getAttributes(), Collections.emptyMap());
		} catch (final CoreException e) {
			return Collections.emptyMap();
		}
	}

	public void refresh() {
		text = marker.getAttribute(IMarker.MESSAGE, null);
		location = marker.getAttribute(IMarker.LOCATION, null);
		try {
			attributes = marker.getAttributes();
		} catch (final CoreException e) {
			attributes = Map.of();
		}
	}

	public IMarker getMarker() {
		return marker;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(marker);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GraphicalMarkerAnnotation other = (GraphicalMarkerAnnotation) obj;
		return Objects.equals(marker, other.marker);
	}
}
