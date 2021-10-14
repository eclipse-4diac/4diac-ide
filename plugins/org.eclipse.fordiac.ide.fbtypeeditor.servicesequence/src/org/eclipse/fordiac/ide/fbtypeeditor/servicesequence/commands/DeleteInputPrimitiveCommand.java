/*******************************************************************************
 * Copyright (c) 2008 - 2014 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

/**
 * DeleteInputPrimitiveCommand removes an InputPrimitive from its parent. If the
 * primitive is the last one of the parent, then also the parent
 * (ServiceTransaction) is removed.
 */
public class DeleteInputPrimitiveCommand extends Command {

	private final InputPrimitive primitive;
	private final ServiceTransaction parent;
	private DeleteTransactionCommand deleteTransactionCmd = null;

	public DeleteInputPrimitiveCommand(final InputPrimitive primitive) {
		this.primitive = primitive;
		this.parent = primitive.getServiceTransaction();
	}

	@Override
	public boolean canExecute() {
		return (null != primitive) && (null != parent);
	}

	@Override
	public void execute() {
		if (null != parent) {
			parent.setInputPrimitive(null);
			if (parent.getOutputPrimitive().isEmpty()) {
				deleteTransactionCmd = new DeleteTransactionCommand(parent);
				deleteTransactionCmd.execute();
			}
		}
	}

	@Override
	public void undo() {
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.undo();
		}
		parent.setInputPrimitive(primitive);
	}

	@Override
	public void redo() {
		parent.setInputPrimitive(null);
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.redo();
		}
	}
}
