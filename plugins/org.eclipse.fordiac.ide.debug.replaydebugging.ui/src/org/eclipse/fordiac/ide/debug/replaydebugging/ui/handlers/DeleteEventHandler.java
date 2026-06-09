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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class DeleteEventHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		HandlerHelper.executeOrBubbleUp(event, new GroupRequest(RequestConstants.REQ_DELETE));
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final IStructuredSelection s) {
			setBaseEnabled(!s.isEmpty() && s.getFirstElement() instanceof EventMarkerEditPart);
		}
	}
}