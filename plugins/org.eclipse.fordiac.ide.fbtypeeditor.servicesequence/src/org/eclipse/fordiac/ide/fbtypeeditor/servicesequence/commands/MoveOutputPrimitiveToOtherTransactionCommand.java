/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveOutputPrimitiveToOtherTransactionCommand extends Command {

	private ServiceTransaction srcTransaction;
	private ServiceTransaction dstTransaction;
	private OutputPrimitive element;
	private OutputPrimitive refElement;

	public MoveOutputPrimitiveToOtherTransactionCommand(ServiceTransaction srcTransaction,
			ServiceTransaction dstTransaction, OutputPrimitive element, OutputPrimitive refElement) {
		this.srcTransaction = srcTransaction;
		this.dstTransaction = dstTransaction;
		this.element = element;
		this.refElement = refElement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return ((srcTransaction != null) && (dstTransaction != null) && (element != null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		srcTransaction.getOutputPrimitive().remove(element);
		if (null == refElement) {
			dstTransaction.getOutputPrimitive().add(element);
		} else {
			int index = dstTransaction.getOutputPrimitive().indexOf(refElement);
			dstTransaction.getOutputPrimitive().add(index, element);
		}
		super.execute();
	}

}
