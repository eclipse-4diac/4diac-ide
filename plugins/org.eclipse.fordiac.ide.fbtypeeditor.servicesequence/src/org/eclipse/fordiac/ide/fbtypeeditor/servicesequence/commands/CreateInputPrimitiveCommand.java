/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, fortiss GmbH
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.ServiceInterfacePaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

public class CreateInputPrimitiveCommand extends Command {

	private final String type;
	private final ServiceTransaction element;
	private InputPrimitive newPrimitive;

	public CreateInputPrimitiveCommand(final String type, final ServiceTransaction element) {
		this.type = type;
		this.element = element;
	}

	@Override
	public boolean canExecute() {
		return (type != null) && (element != null) && (element.getInputPrimitive() != null);
	}

	@Override
	public void execute() {
		final Service service = element.getServiceSequence().getService();
		createInputPrimitive(service);
		element.setInputPrimitive(newPrimitive);
	}

	private void createInputPrimitive(final Service service) {
		newPrimitive = LibraryElementFactory.eINSTANCE.createInputPrimitive();
		final EList<Event> eventInputs = service.getFBType().getInterfaceList().getEventInputs();
		if (eventInputs.isEmpty()) {
			newPrimitive.setEvent("INIT"); //$NON-NLS-1$
		} else {
			newPrimitive.setEvent(eventInputs.get(0).getName());
		}

		if (type.equals(ServiceInterfacePaletteFactory.LEFT_INPUT_PRIMITIVE)) {
			newPrimitive.setInterface(service.getLeftInterface());
		} else {
			newPrimitive.setInterface(service.getRightInterface());
		}
	}

	@Override
	public void undo() {
		element.setInputPrimitive(null);
	}

	@Override
	public void redo() {
		element.setInputPrimitive(newPrimitive);
	}
}
