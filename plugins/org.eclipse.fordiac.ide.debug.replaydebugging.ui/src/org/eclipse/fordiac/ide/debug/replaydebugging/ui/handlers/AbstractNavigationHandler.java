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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.NavigationRequest;

public abstract class AbstractNavigationHandler extends AbstractHandler {

	protected abstract NavigationRequest.Direction getDirection();

	protected boolean isJump() {
		return false;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		HandlerHelper.executeOrBubbleUp(event, new NavigationRequest(getDirection(), isJump()));
		return null;
	}

}
