/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 *               2021 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr, Melanie Winter - clean up, implemented undo and redo
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveInputPrimitiveToOtherTransactionCommand extends Command {

	private final ServiceTransaction srcTransaction;
	private final ServiceTransaction dstTransaction;
	private final InputPrimitive element;

	public MoveInputPrimitiveToOtherTransactionCommand(final ServiceTransaction srcTransaction,
			final ServiceTransaction dstTransaction, final InputPrimitive element) {
		this.srcTransaction = srcTransaction;
		this.dstTransaction = dstTransaction;
		this.element = element;
	}

	@Override
	public boolean canExecute() {
		return ((srcTransaction != null) && (dstTransaction != null) && (element != null));
	}

	@Override
	public void execute() {
		movePrimitive(null, element);
	}

	@Override
	public void undo() {
		movePrimitive(element, null);
	}

	@Override
	public void redo() {
		movePrimitive(null, element);
	}

	private void movePrimitive(final InputPrimitive src, final InputPrimitive dst) {
		srcTransaction.setInputPrimitive(src);
		dstTransaction.setInputPrimitive(dst);
	}
}
