/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Vikash Kumar sinha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.breakpoint;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorModelBreakpoint;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

public class ToggleModelBreakpointsTargetExtension implements IToggleBreakpointsTargetExtension {

	@Override
	public boolean canToggleBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return getSelectedElement(selection) != null;
	}

	@Override
	public void toggleBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		final INamedElement element = getSelectedElement(selection);
		if (element == null) {
			return;
		}
		final var manager = DebugPlugin.getDefault().getBreakpointManager();
		for (final IBreakpoint bp : manager.getBreakpoints(EvaluatorModelBreakpoint.DEBUG_MODEL)) {
			if (bp instanceof final EvaluatorModelBreakpoint modelBp
					&& modelBp.getQualifiedName().equals(element.getQualifiedName())) {
				manager.removeBreakpoint(modelBp, true);
				return;
			}
		}
		IResource resource = null;
		if (part instanceof final IEditorPart editorPart) {
			resource = editorPart.getEditorInput().getAdapter(IFile.class);
		}
		if (resource == null) {
			return;
		}
		manager.addBreakpoint(new EvaluatorModelBreakpoint(resource, element));
	}

	@Override
	public boolean canToggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// not supported
	}

	@Override
	public boolean canToggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// not supported
	}

	@Override
	public boolean canToggleWatchpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// not supported
	}

	private static INamedElement getSelectedElement(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structured) {
			final Object first = structured.getFirstElement();
			return Adapters.adapt(first, ECState.class);
		}
		return null;
	}
}