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
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;

public class ErrorMarkerAttribute {
	private final int lineNumber;
	private final Map<String, Object> attributes;
	private ErrorMarkerRef errorMarkerRef;

	@SuppressWarnings("boxing")
	protected ErrorMarkerAttribute(final int lineNumber, final Map<String, Object> attributes,
			final ErrorMarkerInterface errorMarkerIe) {
		super();
		this.lineNumber = lineNumber;
		this.attributes = attributes;
		this.attributes.put(IMarker.LINE_NUMBER, lineNumber);
		this.attributes.put(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		this.attributes.put(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		this.errorMarkerRef = errorMarkerIe;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public ErrorMarkerRef getErrorMarkerRef() {
		return errorMarkerRef;
	}

	public void setErrorMarkerIe(final ErrorMarkerInterface errorMarkerIe) {
		this.errorMarkerRef = errorMarkerIe;
	}

	public void addId(final long id) {
		if (errorMarkerRef != null) {
			errorMarkerRef.setFileMarkerId(id);
		}
	}
}
