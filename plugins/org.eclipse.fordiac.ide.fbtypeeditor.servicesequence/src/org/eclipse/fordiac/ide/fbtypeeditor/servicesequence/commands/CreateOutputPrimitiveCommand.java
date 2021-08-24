/*******************************************************************************
 * Copyright (c) 2011, 2021 TU Wien ACIN, fortiss GmbH,
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class CreateOutputPrimitiveCommand extends AbstractCreateElementCommand<OutputPrimitive> {

	private final boolean isLeftInterface;
	private final ServiceTransaction transaction;

	public CreateOutputPrimitiveCommand(final ServiceTransaction transaction, final boolean isLeftInterface) {
		super(transaction.getOutputPrimitive());
		this.isLeftInterface = isLeftInterface;
		this.transaction = transaction;

	}

	public CreateOutputPrimitiveCommand(final ServiceTransaction transaction, final OutputPrimitive refElement,
			final boolean isLeftInterface) {
		super(transaction.getOutputPrimitive(), refElement);
		this.isLeftInterface = isLeftInterface;
		this.transaction = transaction;
	}

	@Override
	protected OutputPrimitive createNewElement() {
		final Service service = transaction.getServiceSequence().getService();
		final OutputPrimitive newOutputPrimitive = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		final FBType fb = service.getFBType();
		final String event;
		if (fb.getInterfaceList().getEventOutputs().isEmpty()) {
			event = Messages.CreateOutputPrimitiveCommand_NotAvailable;
		} else {
			event = fb.getInterfaceList().getEventOutputs().get(0).getName();
		}
		newOutputPrimitive.setEvent(event);
		if (isLeftInterface) {
			newOutputPrimitive.setInterface(service.getLeftInterface());
		} else {
			newOutputPrimitive.setInterface(service.getRightInterface());
		}

		return newOutputPrimitive;
	}
}