/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.gef.commands.Command;

public class LayoutCommand extends Command {

	private Set<AbstractFBNElementEditPart> fbNetworkElements = new HashSet<>();
	private Set<ConnectionEditPart> connections = new HashSet<>();
	private Map<AbstractFBNElementEditPart, Point> oldFBPositions = new HashMap<>();

	public LayoutCommand(final Set<AbstractFBNElementEditPart> fbNetworkElements,
			final Set<ConnectionEditPart> connections) {
		this.fbNetworkElements.addAll(fbNetworkElements);
		this.connections.addAll(connections);
		fbNetworkElements.forEach(fb -> oldFBPositions.put(fb, new Point(fb.getModel().getX(), fb.getModel().getY())));
	}

	@Override
	public void undo() {
		fbNetworkElements.forEach(fb -> {
			if (fb != null) {
				fb.getModel().setX(oldFBPositions.get(fb).x);
				fb.getModel().setY(oldFBPositions.get(fb).y);
			}
		});
		connections.forEach(conn -> {
			if (conn != null) {
				// set everything to 0 so the MoveableRouter will route the connections
				conn.getModel().setDx1(0);
				conn.getModel().setDx2(0);
				conn.getModel().setDy(0);
			}
		});
	}

	@Override
	public boolean canRedo() {
		return false;
	}
}
