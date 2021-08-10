/*******************************************************************************
 * Copyright (c) 2008, 2009  Profactor GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

/**
 * DeleteTransactionCommand. Is used to delete a transaction.
 *
 * @author gebenh
 */
public class DeleteTransactionCommand extends Command {

	private final ServiceTransaction transactionElement;
	private ServiceSequence parent;
	private int index;

	/**
	 * Constructor.
	 *
	 * @param transactionElement to be deleted
	 */
	public DeleteTransactionCommand(ServiceTransaction transactionElement) {
		this.transactionElement = transactionElement;
	}

	@Override
	public void execute() {
		parent = transactionElement.getServiceSequence();
		if (parent != null) {
			index = parent.getServiceTransaction().indexOf(transactionElement);
			parent.getServiceTransaction().remove(transactionElement);
		}
	}

	@Override
	public void undo() {
		parent.getServiceTransaction().add(index, transactionElement);
	}

	@Override
	public void redo() {
		index = parent.getServiceTransaction().indexOf(transactionElement);
		parent.getServiceTransaction().remove(transactionElement);
	}

}
