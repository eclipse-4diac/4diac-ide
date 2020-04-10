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
	private ServiceTransaction parent;
	private DeleteTransactionCommand deleteTransactionCmd = null;

	public DeleteInputPrimitiveCommand(InputPrimitive primitive) {
		this.primitive = primitive;
		this.parent = (ServiceTransaction) primitive.eContainer();
	}

	@Override
	public boolean canExecute() {
		if (null == primitive) {
			return false;
		}
		if (primitive.eContainer() == null || !(primitive.eContainer() instanceof ServiceTransaction)) {
			return false;
		}
		return true;
	}

	@Override
	public void execute() {
		if (null != parent) {
			parent.setInputPrimitive(null);
			if (parent.getOutputPrimitive().size() == 0) {
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
