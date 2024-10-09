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

import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.commands.Command;

public class SetPositionCommand extends Command {
	private final PositionableElement positionableElement;
//	private final double dx;
//	private final double dy;
	private Position oldPos;
	private Position newPos;

	public PositionableElement getPositionableElement() {
		return positionableElement;
	}

	private SetPositionCommand(final PositionableElement positionableElement) {
		this.positionableElement = positionableElement;
		setLabel(Messages.ViewSetPositionCommand_LABEL_Move);
	}

	public SetPositionCommand(final PositionableElement positionableElement, final double dx, final double dy) {
		this(positionableElement);
		if (positionableElement != null) {
			oldPos = getPositionableElement().getPosition();
			newPos = createNewPosition(oldPos, dx, dy);
		}
	}

	public SetPositionCommand(final PositionableElement positionableElement, final int dx, final int dy) {
		this(positionableElement);
		if (positionableElement != null) {
			oldPos = getPositionableElement().getPosition();
			newPos = createNewPosition(oldPos, dx, dy);
		}
	}

	@Override
	public boolean canExecute() {
		return positionableElement != null;
	}

	@Override
	public void execute() {
		setPosition(newPos);
	}

	@Override
	public void redo() {
		setPosition(newPos);
	}

	@Override
	public void undo() {
		setPosition(oldPos);
	}

	private static Position createNewPosition(final Position oldPos, final double dx, final double dy) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(oldPos.getX() + dx);
		pos.setY(oldPos.getY() + dy);
		return pos;
	}

	private static Position createNewPosition(final Position oldPos, final int dx, final int dy) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(newPosFromScreenDelta(dx, oldPos.getX()));
		pos.setY(newPosFromScreenDelta(dy, oldPos.getY()));
		return pos;
	}

	private static double newPosFromScreenDelta(final int delta, final double oldPos) {
		final int oldPosScreen = CoordinateConverter.INSTANCE.iec61499ToScreen(oldPos);
		return CoordinateConverter.INSTANCE.screenToIEC61499(oldPosScreen + delta);
	}

	protected void setPosition(final Position pos) {
		positionableElement.setPosition(pos);
	}
}
