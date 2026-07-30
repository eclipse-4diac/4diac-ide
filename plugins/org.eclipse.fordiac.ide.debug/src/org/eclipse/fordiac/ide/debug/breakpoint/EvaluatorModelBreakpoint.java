/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.breakpoint;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugStackFrame;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class EvaluatorModelBreakpoint extends Breakpoint implements IEvaluatorBreakpoint {

	public static final String DEBUG_MODEL = "org.eclipse.fordiac.ide.debug.model"; //$NON-NLS-1$

	public static final String BREAKPOINT_MARKER = "org.eclipse.fordiac.ide.debug.evaluatorModelBreakpointMarker"; //$NON-NLS-1$

	public EvaluatorModelBreakpoint() {
		// required by Eclipse breakpoint framework for persistence/restore
	}

	public EvaluatorModelBreakpoint(final IResource resource, final INamedElement element) throws CoreException {
		run(getMarkerRule(resource), monitor -> createMarker(resource, element));
	}

	private IMarker createMarker(final IResource resource, final INamedElement element) throws CoreException {
		final IMarker marker = resource.createMarker(getMarkerId());
		marker.setAttributes(Map.of(IBreakpoint.ENABLED, Boolean.TRUE, IBreakpoint.PERSISTED, Boolean.TRUE,
				IBreakpoint.ID, getModelIdentifier(), IMarker.LOCATION, element.getQualifiedName()));
		setMarker(marker);
		return marker;
	}

	public String getQualifiedName() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public boolean matches(final EvaluatorDebugStackFrame frame, final Object context) {
		try {
			if (!isEnabled() || !isApplicable(frame.getEvaluator())) {
				return false;
			}
			if (context instanceof final INamedElement element) {
				return getQualifiedName().equals(element.getQualifiedName());
			}
		} catch (final CoreException e) {
			// ignore broken breakpoints
		}
		return false;
	}

	@Override
	public boolean isApplicable(final Evaluator evaluator) {
		// will be narrowed to BasicFBEvaluator once wired in ECC editor
		return true;
	}

	@SuppressWarnings("static-method")
	public String getMarkerId() {
		return BREAKPOINT_MARKER;
	}

	@Override
	public String getModelIdentifier() {
		return DEBUG_MODEL;
	}
}
