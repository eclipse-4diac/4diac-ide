/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *   Alexander Lumplecker - extracted from ChangeVariableCommand
 *   					  - changed variable types to Algorithm
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.gef.commands.Command;

public class ChangeAlgorithmOrderCommand extends Command {
	private final Algorithm var;
	private final EList<Algorithm> type;
	private final int oldIndex;
	private int newIndex;

	public ChangeAlgorithmOrderCommand(final EList<Algorithm> type, final Algorithm var, int indexChanged) {
		this.type = type;
		this.var = var;

		oldIndex = type.indexOf(var);

		// move up : -1
		// move down : +1
		if (indexChanged == 1) {
			newIndex = oldIndex + 1;
		} else if (indexChanged == -1) {
			newIndex = oldIndex - 1;
		}

		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= type.size()) {
			newIndex = type.size() - 1;
		}
	}

	public ChangeAlgorithmOrderCommand(final EList<Algorithm> type, final Algorithm var, boolean moveUp) {
		this(type, var, moveUp ? -1 : 1);
	}

	@Override
	public boolean canExecute() {
		return (null != var) && (type.size() > 1) && (type.size() > newIndex);
	}

	@Override
	public void execute() {
		moveTo(newIndex);
	}

	@Override
	public void redo() {
		moveTo(newIndex);
	}

	@Override
	public void undo() {
		moveTo(oldIndex);
	}

	private void moveTo(int index) {
		type.move(index, var);
	}
}
