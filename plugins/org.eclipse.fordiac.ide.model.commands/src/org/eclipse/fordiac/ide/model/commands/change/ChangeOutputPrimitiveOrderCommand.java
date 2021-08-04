/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Melanie Winter
 *      - Initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.gef.commands.Command;

public class ChangeOutputPrimitiveOrderCommand extends Command {
	private final OutputPrimitive selection;
	private EList<OutputPrimitive> outputPrimitives;
	private int oldIndex;
	private int newIndex;

	private ChangeOutputPrimitiveOrderCommand(OutputPrimitive selection) {
		this.selection = selection;
		if (null != selection) {
			outputPrimitives = selection.getServiceTransaction().getOutputPrimitive();
			oldIndex = outputPrimitives.indexOf(selection);
		}
	}

	public ChangeOutputPrimitiveOrderCommand(OutputPrimitive selection, boolean moveUp) {
		this(selection);
		this.newIndex = moveUp ? oldIndex - 1 : oldIndex + 1;

		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= outputPrimitives.size()) {
			newIndex = outputPrimitives.size() - 1;
		}
	}

	public ChangeOutputPrimitiveOrderCommand(OutputPrimitive selection, int newIndex) {
		this(selection);
		this.newIndex = newIndex;
	}

	@Override
	public boolean canExecute() {
		return (null != selection) && (outputPrimitives.size() > 1) && (outputPrimitives.size() > newIndex);
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
		outputPrimitives.move(index, selection);
	}
}
