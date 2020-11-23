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

import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceEventSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.EditPart;

public class EditInterfaceEventSection extends AbstractEditInterfaceEventSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(IInterfaceElement interfaceElement, boolean isInput) {
		DataType last = getLastUsedEventType(getType().getInterfaceList(), isInput, interfaceElement);
		int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateInterfaceElementCommand(last, getCreationName(interfaceElement), getType().getInterfaceList(),
				isInput, pos);
	}

	@Override
	protected InsertInterfaceElementCommand newInsertCommand(IInterfaceElement interfaceElement, boolean isInput,
			int index) {
		DataType last = getLastUsedEventType(getType().getInterfaceList(), isInput, interfaceElement);
		return new InsertInterfaceElementCommand(interfaceElement, last, getType().getInterfaceList(), isInput, index);
	}

	@Override
	protected FBType getInputType(Object input) {
		if (input instanceof EditPart) {
			Object model = ((EditPart) input).getModel();
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
	protected DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected TypeLibrary getTypeLibrary() {
		return getType().getTypeLibrary();
	}

}
