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
package org.eclipse.fordiac.ide.debug.ui.breakpoint;

import java.util.Collections;
import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class EvaluatorToggleBreakpointsTargetFactory implements IToggleBreakpointsTargetFactory {

	private static final String TARGET_ID = "org.eclipse.fordiac.ide.debug.ui.toggleModelBreakpointTarget"; //$NON-NLS-1$

	@Override
	public Set<String> getToggleTargets(final IWorkbenchPart part, final ISelection selection) {
		if (new ToggleModelBreakpointsTargetExtension().canToggleBreakpoints(part, selection)) {
			return Collections.singleton(TARGET_ID);
		}
		return Collections.emptySet();
	}

	@Override
	public String getDefaultToggleTarget(final IWorkbenchPart part, final ISelection selection) {
		if (new ToggleModelBreakpointsTargetExtension().canToggleBreakpoints(part, selection)) {
			return TARGET_ID;
		}
		return null;
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(final String targetID) {
		if (TARGET_ID.equals(targetID)) {
			return new ToggleModelBreakpointsTargetExtension();
		}
		return null;
	}

	@Override
	public String getToggleTargetName(final String targetID) {
		return "ECC State Breakpoint"; //$NON-NLS-1$
	}

	@Override
	public String getToggleTargetDescription(final String targetID) {
		return "Toggle breakpoint on ECC state"; //$NON-NLS-1$
	}
}