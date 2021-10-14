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
 *   Melanie Winter - add insertion after refelement/index, clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class CreateTransactionCommand extends AbstractCreateElementCommand<ServiceTransaction> {
	private final FBType fbType;

	public CreateTransactionCommand(final ServiceSequence sequence) {
		super(sequence.getServiceTransaction());
		this.fbType = sequence.getService().getFBType();
	}

	public CreateTransactionCommand(final ServiceSequence sequence, final ServiceTransaction refElement) {
		super(sequence.getServiceTransaction(), refElement);
		this.fbType = sequence.getService().getFBType();
	}

	@Override
	protected ServiceTransaction createNewElement() {
		final ServiceTransaction newTransaction = LibraryElementFactory.eINSTANCE.createServiceTransaction();
		final InputPrimitive primitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();

		final EList<Event> eventInputs = fbType.getService().getFBType().getInterfaceList().getEventInputs();
		if (eventInputs.isEmpty()) {
			primitive.setEvent("INIT"); //$NON-NLS-1$
		} else {
			primitive.setEvent(eventInputs.get(0).getName());
		}
		primitive.setInterface(fbType.getService().getLeftInterface());
		newTransaction.setInputPrimitive(primitive);

		return newTransaction;
	}

}
