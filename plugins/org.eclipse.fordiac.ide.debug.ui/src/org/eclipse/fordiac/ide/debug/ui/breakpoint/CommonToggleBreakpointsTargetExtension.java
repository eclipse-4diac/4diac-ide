/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 * 	 Christoph Binder - Extracted code from STToggleBreakpointsTargetExtension, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.breakpoint;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetExtension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;

public abstract class CommonToggleBreakpointsTargetExtension implements IAdapterFactory, IToggleBreakpointsTargetExtension{

	@Override
	public void toggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// empty
	}

	@Override
	public boolean canToggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// empty
	}

	@Override
	public boolean canToggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// empty
	}

	@Override
	public boolean canToggleWatchpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}
	
	@Override
	public boolean canToggleBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return true;
	}
	
	@SuppressWarnings("boxing")
	@Override
	public void toggleBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		if (part instanceof XtextEditor) {
			final XtextEditor editor = (XtextEditor) part;
			final IEditorInput editorInput = editor.getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				final IResource resource = ((IFileEditorInput) editorInput).getFile();
				if (selection instanceof ITextSelection) {
					final int line = ((ITextSelection) selection).getStartLine() + 1;

					final EvaluatorLineBreakpoint existingBreakpoint = findExistingBreakpoint(resource, line);

					if (existingBreakpoint != null) {
						existingBreakpoint.delete();
						return;
					}

					if (!editor.getDocument().tryReadOnly(state -> isValidLineForBreakPoint(state, line))) {
						return;
					}

					final EvaluatorLineBreakpoint breakpoint = createBreakpoint(resource, line);
					DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(breakpoint);
				}
			}
		}
	}
	

	protected EvaluatorLineBreakpoint findExistingBreakpoint(final IResource res, final int line) throws CoreException {
		final IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		final IBreakpoint[] breakpoints = manager.getBreakpoints(EvaluatorLineBreakpoint.DEBUG_MODEL);
		for (final IBreakpoint breakpoint : breakpoints) {
			final IMarker marker = breakpoint.getMarker();
			if (isValidBreakpointType(breakpoint) && marker.getResource().equals(res)) {
				final EvaluatorLineBreakpoint testBreakpoint = (EvaluatorLineBreakpoint) breakpoint;
				if (testBreakpoint.getLineNumber() == line) {
					return testBreakpoint;
				}
			}
		}
		return null;
	}
	
	protected boolean isValidLineForBreakPoint(final XtextResource resource, final int line) {
		final IParseResult parseResult = resource.getParseResult();
		if (parseResult == null) {
			return false;
		}
		final ICompositeNode node = parseResult.getRootNode();
		return isValidLineForBreakPoint(node, line);
	}
	
	protected boolean isValidLineForBreakPoint(final ICompositeNode node, final int line) {
		for (final INode n : node.getChildren()) {
			final ITextRegionWithLineInformation textRegion = n.getTextRegionWithLineInformation();
			if (textRegion.getLineNumber() <= line && textRegion.getEndLineNumber() >= line) {
				if (n.hasDirectSemanticElement() && textRegion.getLineNumber() == line) {
					final EObject eObject = n.getSemanticElement();
					if (isValidSematicElementForBreakpoint(eObject)) {
						return true;
					}
				}
				if (n instanceof ICompositeNode && isValidLineForBreakPoint((ICompositeNode) n, line)) {
					return true;
				}
			}
			if (textRegion.getLineNumber() > line) {
				return false;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (adaptableObject instanceof XtextEditor && isApplicable((XtextEditor) adaptableObject)
				&& adapterType == IToggleBreakpointsTarget.class) {
			return (T) this;
		}
		return null;
	}
	
	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IToggleBreakpointsTarget.class };
	}

	protected abstract boolean isApplicable(XtextEditor adaptableObject);

	protected abstract boolean isValidSematicElementForBreakpoint(EObject eObject);

	protected abstract boolean isValidBreakpointType(IBreakpoint breakpoint);

	protected abstract EvaluatorLineBreakpoint createBreakpoint(final IResource res, final int line) throws CoreException;

	
}
