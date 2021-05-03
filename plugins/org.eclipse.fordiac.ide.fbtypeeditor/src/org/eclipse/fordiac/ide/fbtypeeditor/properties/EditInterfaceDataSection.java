/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;

public class EditInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final DataType last = getLastUsedDataType(getType().getInterfaceList(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateInterfaceElementCommand(last, getCreationName(interfaceElement), getType().getInterfaceList(),
				isInput, pos);
	}

	@Override
	protected InsertInterfaceElementCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		final DataType last = getLastUsedDataType(getType().getInterfaceList(), isInput, interfaceElement);
		return new InsertInterfaceElementCommand(interfaceElement, last, getType().getInterfaceList(), isInput, index);
	}

	@Override
	protected FBType getInputType(final Object input) {
		if (input instanceof EditPart) {
			final Object model = ((EditPart) input).getModel();
			if (model instanceof FBType) {
				return (FBType) model;
			}
			if ((model instanceof FBNetwork) && (((FBNetwork) model).eContainer() instanceof FBType)) {
				return (FBType) ((FBNetwork) model).eContainer();
			}
		}
		return null;
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

}
