/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr - create command now has enhanced guess
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceEventSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class EditInterfaceEventSection extends AbstractEditInterfaceEventSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(IInterfaceElement interfaceElement, boolean isInput) {
		DataType last = getLastUsedEventType(getType().getInterface(), isInput, interfaceElement);
		int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateSubAppInterfaceElementCommand(last, getCreationName(interfaceElement),
				getType().getInterface(), isInput, pos);
	}

	@Override
	protected SubApp getInputType(Object input) {
		if (input instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) input).getModel();
		}
		if (input instanceof UISubAppNetworkEditPart) {
			return ((UISubAppNetworkEditPart) input).getSubApp();
		}
		if (input instanceof SubApp) {
			return (SubApp) input;
		}
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(IInterfaceElement selection, boolean isInput,
			boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, isInput, moveUp);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}
}
