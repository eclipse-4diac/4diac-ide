
/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.command;

import org.eclipse.core.runtime.ILog;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.ReplayDebuggingView;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

/**
 * Finds the command stack of the timeline view, which all comment commands are
 * executed on.
 */
public final class TimelineViewCommandStack {

	private TimelineViewCommandStack() {
	}

	/**
	 * @param window the workbench window to look in
	 * @param open   whether to show the timeline view if it is not open
	 * @return the stack of the timeline view's viewer, or {@code null} if the view
	 *         is not open (and {@code open} is false or opening failed)
	 */
	public static CommandStack find(final IWorkbenchWindow window, final boolean open) {
		final IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null) {
			return null;
		}
		IViewPart view = page.findView(ReplayDebuggingView.ID);
		if (view == null && open) {
			try {
				view = page.showView(ReplayDebuggingView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
			} catch (final PartInitException e) {
				ILog.get().error("Could not open the timeline view", e); //$NON-NLS-1$
				return null;
			}
		}
		final GraphicalViewer viewer = view == null ? null : view.getAdapter(GraphicalViewer.class);
		return viewer == null ? null : viewer.getEditDomain().getCommandStack();
	}
}
