/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015, 2016 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateECStateCommand.
 */
public class CreateECStateCommand extends Command {

	/** The location. */
	private final Position pos;

	/** The ec state. */
	private final ECState ecState;

	/** The parent. */
	private final ECC parent;

	/**
	 * Instantiates a new creates the ec state command.
	 *
	 * @param ecState  the ec state
	 * @param location the location
	 * @param parent   the parent
	 */
	public CreateECStateCommand(final ECState ecState, final Point location, final ECC parent) {
		this.pos = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(location.x, location.y);
		this.ecState = ecState;
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		ecState.setPosition(pos);
		if (parent.getECState().isEmpty()) {
			parent.setStart(ecState);
		}
		parent.getECState().add(ecState);
		ecState.setName(NameRepository.createUniqueName(ecState, ecState.getName()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		parent.getECState().remove(ecState);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Co mmand#redo()
	 */
	@Override
	public void redo() {
		parent.getECState().add(ecState);
	}
}
