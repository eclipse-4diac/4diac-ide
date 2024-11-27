/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui.actions;

import java.util.Arrays;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.model.elements.VariableContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.debug.internal.ui.views.variables.VariablesView;
import org.eclipse.fordiac.ide.debug.ui.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.texteditor.IUpdate;

@SuppressWarnings("restriction")
public class EvaluatorDebugFindAction extends Action implements IUpdate {

	private static final String ID = "org.eclipse.fordiac.ide.debug.ui.FindElementAction"; //$NON-NLS-1$

	private final IStackFrame stackFrame;
	private final IPresentationContext context;
	private final InternalVariableContentProvider contentProvider = new InternalVariableContentProvider();

	public EvaluatorDebugFindAction(final IStackFrame stackFrame, final IPresentationContext context) {
		this.stackFrame = stackFrame;
		this.context = context;
		setId(ID);
		setText(Messages.EvaluatorDebugFindAction_Text);
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_FIND_AND_REPLACE);
	}

	@Override
	public void run() {
		final Shell shell = context.getPart().getSite().getShell();
		final EvaluatorDebugFindDialog dialog = new EvaluatorDebugFindDialog(shell, stackFrame);
		if (dialog.open() == Window.OK) {
			final Object[] elements = dialog.getResult();
			if (elements != null && elements.length == 1 && elements[0] instanceof final IVariable variable) {
				try {
					selectAndReveal(variable);
				} catch (final CoreException e) {
					ErrorDialog.openError(shell, null, null, e.getStatus());
				}
			}
		}
	}

	protected void selectAndReveal(final IVariable variable) throws CoreException {
		if (context.getPart() instanceof final VariablesView variablesView
				&& variablesView.getViewer() instanceof final TreeModelViewer viewer) {
			selectAndReveal(variable, viewer);
		}
	}

	protected void selectAndReveal(final IVariable variable, final TreeModelViewer viewer) throws CoreException {
		final ModelDelta root = new ModelDelta(viewer.getInput(), IModelDelta.NO_CHANGE);
		buildModelDelta(root, stackFrame.getVariables(), variable);
		viewer.updateViewer(root);
	}

	protected ModelDelta buildModelDelta(final ModelDelta delta, final IVariable[] variables, final IVariable target)
			throws CoreException {
		for (int index = 0; index < variables.length; index++) {
			final IVariable variable = variables[index];
			final IVariable[] children = contentProvider.getValueChildren(variable, variable.getValue(), context);
			if (variable == target) {
				return delta.addNode(variable, index, IModelDelta.SELECT | IModelDelta.FORCE, children.length);
			}
			if (isAncestor(variable, target)) {
				return buildModelDelta(delta.addNode(variable, index, IModelDelta.EXPAND, children.length), children,
						target);
			}
		}
		return delta;
	}

	protected static boolean isAncestor(final IVariable variable, final IVariable target) throws DebugException {
		for (final IVariable child : variable.getValue().getVariables()) {
			if (child == target || isAncestor(child, target)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		try {
			setEnabled(stackFrame != null && stackFrame.hasVariables());
		} catch (final DebugException e) {
			setEnabled(false);
		}
	}

	private static class InternalVariableContentProvider extends VariableContentProvider {

		@Override
		protected IVariable[] getValueChildren(final IDebugElement parent, final IValue value,
				final IPresentationContext context) throws CoreException {
			final Object[] children = super.getValueChildren(parent, value, context);
			if (children instanceof final IVariable[] variableChildren) {
				return variableChildren;
			}
			return Arrays.copyOf(children, children.length, IVariable[].class);
		}
	}
}
