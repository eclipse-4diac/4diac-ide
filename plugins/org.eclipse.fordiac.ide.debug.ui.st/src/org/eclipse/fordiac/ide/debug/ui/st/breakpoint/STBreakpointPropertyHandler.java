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
package org.eclipse.fordiac.ide.debug.ui.st.breakpoint;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

public class STBreakpointPropertyHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection.size() == 1 && selection.getFirstElement() instanceof EvaluatorLineBreakpoint) {
			PreferencesUtil.createPropertyDialogOn(HandlerUtil.getActiveShell(event),
					(EvaluatorLineBreakpoint) selection.getFirstElement(), null, null, null).open();
		}
		return null;
	}
}
