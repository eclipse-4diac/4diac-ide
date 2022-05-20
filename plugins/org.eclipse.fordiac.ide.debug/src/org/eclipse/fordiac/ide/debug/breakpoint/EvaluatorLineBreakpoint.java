/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.breakpoint;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;

public class EvaluatorLineBreakpoint extends LineBreakpoint {

	public static final String DEBUG_MODEL = "org.eclipse.fordiac.ide.debug.model"; //$NON-NLS-1$

	public static final String BREAKPOINT_MARKER = "org.eclipse.fordiac.ide.debug.evaluatorLineBreakpointMarker"; //$NON-NLS-1$

	public EvaluatorLineBreakpoint() {
	}

	public EvaluatorLineBreakpoint(final IResource resource, final int lineNumber) throws CoreException {
		run(getMarkerRule(resource), monitor -> createMarker(resource, lineNumber));
	}

	protected IMarker createMarker(final IResource resource, final int lineNumber) throws CoreException {
		final IMarker marker = resource.createMarker(getMarkerId());
		setMarker(marker);
		marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		marker.setAttribute(IMarker.MESSAGE, "Line Breakpoint: " + resource.getName() + " [line: " + lineNumber + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		marker.setAttribute(IBreakpoint.ENABLED, true);
		marker.setAttribute(IBreakpoint.PERSISTED, true);
		marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
		return marker;
	}

	public String getMarkerId() {
		return BREAKPOINT_MARKER;
	}

	@Override
	public String getModelIdentifier() {
		return DEBUG_MODEL;
	}
}
