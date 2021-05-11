/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.dataimport;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;

public class ErrorMarkerBuilder {

	private Map<String, Object> attributes;
	private ErrorMarkerRef errorMarkerRef;

	@SuppressWarnings("boxing")
	public ErrorMarkerBuilder(final Map<String, Object> attributes, final ErrorMarkerRef errorMarkerIe) {
		this.attributes = attributes;
		this.attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		this.attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		this.errorMarkerRef = errorMarkerIe;
	}

	public ErrorMarkerBuilder(final IMarker marker, final ErrorMarkerRef errorMarkerRef) {
		this.errorMarkerRef = errorMarkerRef;
		try {
			this.attributes = marker.getAttributes();
		} catch (final CoreException e) {
			Activator.getDefault().getLog().error("Error Marker not found", e); //$NON-NLS-1$
		}


	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public ErrorMarkerRef getErrorMarkerRef() {
		return errorMarkerRef;
	}

	public void setErrorMarkerIe(final ErrorMarkerRef errorMarkerRef) {
		this.errorMarkerRef = errorMarkerRef;
	}

	public void addId(final long id) {
		if (errorMarkerRef != null) {
			errorMarkerRef.setFileMarkerId(id);
		}
	}
}
