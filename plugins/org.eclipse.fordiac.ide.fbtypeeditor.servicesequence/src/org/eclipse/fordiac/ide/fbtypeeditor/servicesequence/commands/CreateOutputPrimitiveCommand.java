/*******************************************************************************
 * Copyright (c) 2011, 2021 TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
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

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class CreateOutputPrimitiveCommand extends AbstractCreationCommand {

	private final boolean isLeftInterface;
	private final ServiceTransaction transaction;
	private final OutputPrimitive refElement;
	private OutputPrimitive newElement;


	public CreateOutputPrimitiveCommand(final ServiceTransaction element, final OutputPrimitive refElement,
			final boolean isLeftInterface) {
		this.isLeftInterface = isLeftInterface;
		this.transaction = element;
		this.refElement = refElement;
	}

	@Override
	public boolean canExecute() {
		return (transaction != null);
	}

	@Override
	public void execute() {
		final Service service = transaction.getServiceSequence().getService();
		newElement = LibraryElementFactory.eINSTANCE.createOutputPrimitive();
		final FBType fb = (FBType) service.eContainer();
		final String event;
		if (fb.getInterfaceList().getEventOutputs().isEmpty()) {
			event = "not available";
		} else {
			event = fb.getInterfaceList().getEventOutputs().get(0).getName();
		}
		newElement.setEvent(event);
		if (isLeftInterface) {
			newElement.setInterface(service.getLeftInterface());
		} else {
			newElement.setInterface(service.getRightInterface());
		}
		addNewPrimitive();
	}

	private void addNewPrimitive() {
		if (null == refElement) {
			transaction.getOutputPrimitive().add(newElement);
		} else {
			final int index = transaction.getOutputPrimitive().indexOf(refElement);
			transaction.getOutputPrimitive().add(index, newElement);
		}
	}

	@Override
	public void undo() {
		transaction.getOutputPrimitive().remove(newElement);
	}

	@Override
	public void redo() {
		addNewPrimitive();
	}

	@Override
	public Object getCreatedElement() {
		return newElement;
	}
}
