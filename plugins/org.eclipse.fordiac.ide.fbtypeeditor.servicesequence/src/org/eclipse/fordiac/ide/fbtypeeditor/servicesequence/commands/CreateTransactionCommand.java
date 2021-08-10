/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 		 2019, 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Melanie Winter - add insertion after refelement
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class CreateTransactionCommand extends AbstractCreationCommand {
	private ServiceTransaction newTransaction;
	private final ServiceSequence parent;
	private ServiceTransaction refElement;

	public CreateTransactionCommand(final ServiceSequence sequence) {
		this.parent = sequence;
	}

	public CreateTransactionCommand(final ServiceSequence sequence, final ServiceTransaction refElement) {
		this(sequence);
		this.refElement = refElement;

	}
	@Override
	public void execute() {
		newTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		final InputPrimitive primitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		primitive.setEvent("INIT"); //$NON-NLS-1$
		primitive.setInterface(parent.getService().getLeftInterface());
		newTransaction.setInputPrimitive(primitive);
		addNewTransaction();
	}

	private void addNewTransaction() {
		if (null == refElement) {
			parent.getServiceTransaction().add(newTransaction);
		} else {
			final int index = parent.getServiceTransaction().indexOf(refElement) + 1;
			parent.getServiceTransaction().add(index, newTransaction);
		}
	}

	@Override
	public void undo() {
		parent.getServiceTransaction().remove(newTransaction);
	}

	@Override
	public void redo() {
		addNewTransaction();
	}

	@Override
	public Object getCreatedElement() {
		return newTransaction;
	}
}
