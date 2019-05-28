/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppIETypeCommand;
import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceAdapterSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class EditInterfaceAdapterSection extends AbstractEditInterfaceAdapterSection {
	@Override
	protected CreateInterfaceElementCommand newCreateCommand(boolean isInput) {
		AdapterType type = getPalette().getAdapterTypes().get(0).getType();
		return new CreateSubAppInterfaceElementCommand(type, getType().getInterface(), isInput, -1);
	}

	@Override
	protected INamedElement getInputType(Object input) {
		if (input instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) input).getModel();
		}
		if (input instanceof UISubAppNetworkEditPart) {
			return ((UISubAppNetworkEditPart) input).getSubApp();
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
	protected ChangeTypeCommand newChangeTypeCommand(VarDeclaration data, DataType newType) {
		return new ChangeSubAppIETypeCommand(data, newType);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}
}
