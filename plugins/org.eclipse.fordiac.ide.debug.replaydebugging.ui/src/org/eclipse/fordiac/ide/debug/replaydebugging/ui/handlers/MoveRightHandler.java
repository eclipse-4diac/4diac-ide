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

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.NavigationRequest;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.NavigationRequest.Direction;

public class MoveRightHandler extends AbstractNavigationHandler {
	@Override
	protected NavigationRequest.Direction getDirection() {
		return Direction.RIGHT;
	}
}
