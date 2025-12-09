/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

/**
 * A generic search match
 */
public class Match {
	private final URI uri;
	private final String location;
	private final String type;

	/**
	 * Create a new generic search match based on an object
	 *
	 * @param object The object
	 */
	public Match(final EObject object) {
		this(EcoreUtil.getURI(object), FordiacMarkerHelper.getLocation(object),
				object.eClass().getEPackage().getName());
	}

	/**
	 * Create a new generic search match
	 *
	 * @param uri      The uri of the match (must not be null)
	 * @param location The location string of the match (must not be null)
	 * @param type     The type of the match
	 */
	public Match(final URI uri, final String location, final String type) {
		this.uri = Objects.requireNonNull(uri);
		this.location = Objects.requireNonNull(location);
		this.type = type;
	}

	public final URI getUri() {
		return uri;
	}

	public final String getLocation() {
		return location;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uri, location, type);
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
		final Match other = (Match) obj;
		return Objects.equals(uri, other.uri) && Objects.equals(location, other.location)
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return String.format("%s [uri=%s, location=%s, type=%s]", getClass().getName(), uri, location, type); //$NON-NLS-1$
	}
}
