/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class MoveInputPrimitiveToOtherTransactionCommand extends Command {

	private ServiceTransaction srcTransaction;
	private ServiceTransaction dstTransaction;
	private InputPrimitive element;
	
	public MoveInputPrimitiveToOtherTransactionCommand(ServiceTransaction srcTransaction,
			ServiceTransaction dstTransaction, InputPrimitive element) {
		this.srcTransaction = srcTransaction;
		this.dstTransaction = dstTransaction;
		this.element = element;
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		srcTransaction.setInputPrimitive(null);
		dstTransaction.setInputPrimitive(element);
		super.execute();
	}
	
}
