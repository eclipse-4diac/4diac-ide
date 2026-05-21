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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.action;

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.ResourceEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

/**
 * @brief Action to move one event forward in the replay navigator.
 */
public class MoveOneEventForward extends Action {

	private final GraphicalViewer viewer;

	public MoveOneEventForward(final GraphicalViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		final var selected = viewer.getSelectedEditParts();
		if (!selected.isEmpty() && selected.get(0) instanceof final ResourceEditPart ep) {
			ep.moveForward();
		}
	}
}