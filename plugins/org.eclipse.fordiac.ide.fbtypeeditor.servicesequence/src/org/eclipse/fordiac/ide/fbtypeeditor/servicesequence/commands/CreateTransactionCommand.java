/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class CreateTransactionCommand extends Command {

	private ServiceTransaction transaction;	
	private ServiceSequence parent;
	
	public CreateTransactionCommand(ServiceSequence sequence) {
		this.parent = sequence;
	}

	@Override
	public boolean canUndo() {
		return transaction != null;
	}

	@Override
	public void execute() {
		transaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		InputPrimitive primitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		primitive.setEvent("INIT"); //$NON-NLS-1$
		primitive.setInterface(((Service)parent.eContainer()).getLeftInterface());
		transaction.setInputPrimitive(primitive);
		parent.getServiceTransaction().add(transaction);
	}

	@Override
	public void undo() {
		parent.getServiceTransaction().remove(transaction);
	}

	@Override
	public void redo() {
		parent.getServiceTransaction().add(transaction);
	}
}
