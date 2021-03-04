/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

public class SetPositionCommand extends Command {
	private final Rectangle newBounds;
	private Rectangle oldBounds;
	private final ChangeBoundsRequest request;
	private final PositionableElement positionableElement;

	public SetPositionCommand(final PositionableElement positionableElement, final ChangeBoundsRequest req,
			final Rectangle newBounds) {
		this.positionableElement = positionableElement;
		this.request = req;
		this.newBounds = newBounds.getCopy();
		setLabel(Messages.ViewSetPositionCommand_LABEL_Move);
	}

	@Override
	public boolean canExecute() {
		final Object type = request.getType();
		// make sure the Request is of a type we support: (Move or
		// Move_Children)
		// e.g. a FB moves within an application
		return RequestConstants.REQ_MOVE.equals(type) || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type);
	}

	/**
	 * Sets the new Position of the affected UIFB.
	 */
	@Override
	public void execute() {
		oldBounds = new Rectangle(positionableElement.getPosition().getX(), positionableElement.getPosition().getY(),
				-1, -1);
		redo();
	}

	@Override
	public void redo() {
		setPosition(newBounds);
	}

	@Override
	public void undo() {
		setPosition(oldBounds);
	}

	private void setPosition(final Rectangle bounds) {
		positionableElement.updatePosition(bounds.getTopLeft());
	}
}
