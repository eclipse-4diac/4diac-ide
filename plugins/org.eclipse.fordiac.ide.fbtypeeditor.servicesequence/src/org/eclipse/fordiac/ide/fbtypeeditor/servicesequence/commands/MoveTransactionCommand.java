/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 Profactor GmbH, fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveTransactionCommand extends Command {

	private final ServiceSequence parent;
	private final ServiceTransaction moveElement;
	private final int oldIndex;
	private final int newIndex;

	public MoveTransactionCommand(final ServiceTransaction model, final int oldIndex, final int newIndex) {
		this.moveElement = model;
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
		this.parent = moveElement.getServiceSequence();
	}

	@Override
	public boolean canExecute() {
		return ((parent != null) && (moveElement != null));
	}

	@Override
	public void execute() {
		moveToIndex(newIndex);
	}


	@Override
	public void undo() {
		moveToIndex(oldIndex);
	}

	@Override
	public void redo() {
		moveToIndex(newIndex);
	}

	private void moveToIndex(final int index) {
		parent.getServiceTransaction().move(index, moveElement);
	}
}
