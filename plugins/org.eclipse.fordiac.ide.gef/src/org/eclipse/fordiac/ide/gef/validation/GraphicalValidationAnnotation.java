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
package org.eclipse.fordiac.ide.gef.validation;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public class GraphicalValidationAnnotation extends GraphicalAnnotation {

	private final String message;
	private final Map<String, Object> attributes;

	protected GraphicalValidationAnnotation(final String type, final Object target, final String message,
			final Map<String, Object> attributes) {
		super(type, target);
		this.message = message;
		this.attributes = Map.copyOf(attributes);
	}

	public static Optional<GraphicalValidationAnnotation> forDiagnostic(final Diagnostic diagnostic) {
		final EObject target = FordiacMarkerHelper.getDiagnosticTarget(diagnostic);
		final Map<String, Object> attributes = FordiacMarkerHelper.getDiagnosticAttributes(diagnostic);
		if (target != null && !attributes.isEmpty()) {
			return Optional.of(new GraphicalValidationAnnotation(getType(diagnostic), target, diagnostic.getMessage(),
					attributes));
		}
		return Optional.empty();
	}

	@Override
	public String getText() {
		return message;
	}

	@Override
	public String getLocation() {
		if (attributes.get(IMarker.LOCATION) instanceof final String location) {
			return location;
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Object getAttribute(final String attributeName) {
		return attributes.get(attributeName);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getType(), getTarget(), message);
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
		final GraphicalValidationAnnotation other = (GraphicalValidationAnnotation) obj;
		return Objects.equals(getType(), other.getType()) && Objects.equals(message, other.message)
				&& Objects.equals(getTarget(), other.getTarget());
	}

	protected static String getType(final Diagnostic diagnostic) {
		return switch (diagnostic.getSeverity()) {
		case Diagnostic.INFO -> TYPE_INFO;
		case Diagnostic.WARNING -> TYPE_WARNING;
		case Diagnostic.ERROR -> TYPE_ERROR;
		default -> TYPE_UNKNOWN;
		};
	}
}
