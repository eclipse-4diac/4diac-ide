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

import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveOutputPrimitiveToOtherTransactionCommand extends Command {

	private final ServiceTransaction srcTransaction;
	private final ServiceTransaction dstTransaction;
	private final OutputPrimitive element;
	private final OutputPrimitive refElement;
	private int oldIndex;

	public MoveOutputPrimitiveToOtherTransactionCommand(final ServiceTransaction srcTransaction,
			final ServiceTransaction dstTransaction, final OutputPrimitive element, final OutputPrimitive refElement) {
		this.srcTransaction = srcTransaction;
		this.dstTransaction = dstTransaction;
		this.element = element;
		this.refElement = refElement;
	}

	@Override
	public boolean canExecute() {
		return ((srcTransaction != null) && (dstTransaction != null) && (element != null));
	}

	@Override
	public void execute() {
		oldIndex = srcTransaction.getOutputPrimitive().indexOf(element);
		move();
	}

	@Override
	public void undo() {
		dstTransaction.getOutputPrimitive().remove(element);
		srcTransaction.getOutputPrimitive().add(oldIndex, element);
	}

	@Override
	public void redo() {
		move();
	}

	private void move() {
		srcTransaction.getOutputPrimitive().remove(element);
		if (null == refElement) {
			dstTransaction.getOutputPrimitive().add(element);
		} else {
			final int index = dstTransaction.getOutputPrimitive().indexOf(refElement);
			dstTransaction.getOutputPrimitive().add(index, element);
		}
	}

}
