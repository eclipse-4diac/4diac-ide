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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommonConstants;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.statescomparison.StatesComparisonView;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.Request;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddToComparisonHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		if (HandlerHelper.executeOrBubbleUp(event, new Request(CommonConstants.ADD_TO_COMPARISON_REQUEST))) {
			// open the state comparison table if not open
			final var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			final var page = window.getActivePage();
			try {
				page.showView(StatesComparisonView.VIEW_ID);
			} catch (final PartInitException e) {
				FordiacLogHelper.logError("Failed to open States Comparison View", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final IStructuredSelection s) {
			setBaseEnabled(!s.isEmpty() && s.getFirstElement() instanceof final EventMarkerEditPart ep
					&& ep.getModel().getComparisonColor() == null);
		}
	}
}