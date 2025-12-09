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

import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewActionProvider;
import org.eclipse.debug.ui.IDebugView;
import org.eclipse.jface.action.IAction;

@SuppressWarnings("restriction")
public class EvaluatorDebugViewActionProvider implements IViewActionProvider {

	private static final String VARIABLES_VIEW_ID = "org.eclipse.debug.ui.VariableView"; //$NON-NLS-1$

	private final IStackFrame stackFrame;

	public EvaluatorDebugViewActionProvider(final IStackFrame stackFrame) {
		this.stackFrame = stackFrame;
	}

	@Override
	public IAction getAction(final IPresentationContext presentationContext, final String actionID) {
		if (VARIABLES_VIEW_ID.equals(presentationContext.getId()) && IDebugView.FIND_ACTION.equals(actionID)) {
			return new EvaluatorDebugFindAction(stackFrame, presentationContext);
		}
		return null;
	}
}
