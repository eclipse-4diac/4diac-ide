/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2016, 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

/**
 * The Class SegmentSetConstraintCommand.
 */
public class SegmentSetConstraintCommand extends Command {

	private final Position newPos;
	private Position oldPos;

	private final int newWidth;
	private int oldWidth;

	/** The request. */
	private final ChangeBoundsRequest request;

	private final Segment segment;

	/**
	 * Instantiates a new segment set constraint command.
	 */
	public SegmentSetConstraintCommand(final Segment segment, final Rectangle newBounds,
			final ChangeBoundsRequest request) {
		setLabel("Move/Resize");
		this.segment = segment;
		this.request = request;
		newPos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(newBounds.x, newBounds.y);
		newWidth = newBounds.width;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		final Object type = request.getType();
		// make sure the Request is of a type we support: (Move or
		// Move_Children/Resize or Resize_Children)
		return RequestConstants.REQ_MOVE.equals(type) || RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_RESIZE.equals(type) || RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
				|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type);
	}

	/**
	 * Sets the new Position and Dimension of the affected Segment.
	 *
	 * @see #redo()
	 */
	@Override
	public void execute() {
		oldPos = segment.getPosition();
		oldWidth = segment.getWidth();
		setSegementPosAndWidth(newPos, newWidth);
	}

	/**
	 * Redo.
	 *
	 * @see #execute()
	 */
	@Override
	public void redo() {
		setSegementPosAndWidth(newPos, newWidth);

	}

	/**
	 * Restores the old position/Dimension.
	 */
	@Override
	public void undo() {
		setSegementPosAndWidth(oldPos, oldWidth);
	}

	private void setSegementPosAndWidth(final Position pos, final int width) {
		segment.setPosition(pos);
		segment.setWidth(width);
	}

}
