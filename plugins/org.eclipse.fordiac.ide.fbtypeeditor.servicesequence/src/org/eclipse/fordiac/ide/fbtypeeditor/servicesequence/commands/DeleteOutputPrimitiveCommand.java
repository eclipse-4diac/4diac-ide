/*******************************************************************************
 * Copyright (c) 2008, 2021  Profactor GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

/**
 * DeleteOutputPrimitiveCommand removes an OutputPrimitve from its parent. If
 * the primitive is the last one of the parent (and also no InputPrimitive is
 * available), then also the parent (ServiceTransaction) is removed.
 *
 * @author gebenh
 */
public class DeleteOutputPrimitiveCommand extends Command {

	private final OutputPrimitive primitive;
	private ServiceTransaction parent;
	private int index;
	private DeleteTransactionCommand deleteTransactionCmd = null;

	/** @param primitive the primitive to delete */
	public DeleteOutputPrimitiveCommand(final OutputPrimitive primitive) {
		this.primitive = primitive;
	}

	@Override
	public void execute() {
		parent = primitive.getServiceTransaction();
		index = parent.getOutputPrimitive().indexOf(primitive);
		parent.getOutputPrimitive().remove(primitive);

		if ((parent.getInputPrimitive() == null) && parent.getOutputPrimitive().isEmpty()) {
			deleteTransactionCmd = new DeleteTransactionCommand(parent);
			deleteTransactionCmd.execute();
		}
	}

	@Override
	public void undo() {
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.undo();
		}
		parent.getOutputPrimitive().add(index, primitive);
	}

	@Override
	public void redo() {
		index = parent.getOutputPrimitive().indexOf(primitive);
		parent.getOutputPrimitive().remove(primitive);
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.redo();
		}
	}

}
