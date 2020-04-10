/*******************************************************************************
 * Copyright (c) 2011, 2015 TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveOutputPrimitiveCommand extends Command {

	private final ServiceTransaction parent;
	private OutputPrimitive moveElement;
	private final OutputPrimitive refElement;
	private int oldIndex;

	public MoveOutputPrimitiveCommand(ServiceTransaction transaction, OutputPrimitive element,
			OutputPrimitive refElement) {
		this.parent = transaction;
		this.moveElement = element;
		this.refElement = refElement;
		this.oldIndex = parent.getOutputPrimitive().indexOf(moveElement);
	}

	@Override
	public boolean canExecute() {
		return ((parent != null) && (moveElement != null));
	}

	@Override
	public void execute() {
		move();
	}

	@Override
	public void undo() {
		if (null == refElement) {
			parent.getOutputPrimitive().move(parent.getOutputPrimitive().size() + 1, moveElement);
		} else {
			parent.getOutputPrimitive().move(oldIndex, moveElement);
		}
	}

	@Override
	public void redo() {
		move();
	}

	private void move() {
		if (null == refElement) {
			parent.getOutputPrimitive().move(parent.getOutputPrimitive().size() - 1, moveElement);
		} else {
			int index = parent.getOutputPrimitive().indexOf(refElement);
			parent.getOutputPrimitive().move(index, moveElement);
		}
	}
}
