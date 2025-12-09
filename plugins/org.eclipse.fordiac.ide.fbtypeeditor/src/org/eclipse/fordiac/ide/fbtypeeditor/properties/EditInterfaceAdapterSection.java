/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *               - cleaned command stack handling for property section
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceAdapterSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class EditInterfaceAdapterSection extends AbstractEditInterfaceAdapterSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(final IInterfaceElement interfaceElement,
			final boolean isInput) {
		final AdapterType last = getLastUsedAdapterType(getType().getInterfaceList(), interfaceElement, isInput);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateInterfaceElementCommand(last, getCreationName(interfaceElement), getType().getInterfaceList(),
				isInput, pos);
	}

	@Override
	protected CreateInterfaceElementCommand newInsertCommand(final IInterfaceElement interfaceElement,
			final boolean isInput, final int index) {
		return new CreateInterfaceElementCommand(interfaceElement, isInput, getType().getInterfaceList(), index);
	}

	@Override
	protected FBType getInputType(final Object input) {
		return FBTypePropertiesFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterfaceList() : null;
	}

}
