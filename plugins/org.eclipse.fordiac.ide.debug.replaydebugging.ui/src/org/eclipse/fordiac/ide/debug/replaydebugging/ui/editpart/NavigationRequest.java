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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.Request;

public class NavigationRequest extends Request {

	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	private final Direction direction;

	public NavigationRequest(final Direction direction) {
		super(Messages.NavigationRequest_Type);
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
}