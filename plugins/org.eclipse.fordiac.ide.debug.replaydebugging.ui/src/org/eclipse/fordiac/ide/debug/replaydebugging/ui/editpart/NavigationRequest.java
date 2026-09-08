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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.Request;

public class NavigationRequest extends Request {

	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	private final Direction direction;
	private final boolean jump;
	private Set<Integer> highlighted = new HashSet<>();

	public NavigationRequest(final Direction direction, final boolean jump) {
		super(Messages.NavigationRequest_Type);
		this.direction = direction;
		this.jump = jump;
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isJump() {
		return jump;
	}

	public Set<Integer> getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(final Set<Integer> highlighted) {
		this.highlighted = highlighted;
	}

}