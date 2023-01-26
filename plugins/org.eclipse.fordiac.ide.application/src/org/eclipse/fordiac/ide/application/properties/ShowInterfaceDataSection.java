/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber
 *     - updated label provider
 *   Fabio Gandolfi - removed special label providers
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.FordiacInterfaceListProvider;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationListProvider;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;

public class ShowInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected FBNetworkElement getInputType(final Object input) {
		if ((input instanceof SubAppForFBNetworkEditPart) || (input instanceof AbstractFBNElementEditPart)) {
			return (FBNetworkElement) ((EditPart) input).getModel();
		}
		if (input instanceof FBNetworkElement) {
			return (FBNetworkElement) input;
		}
		return null;
	}

	@Override
	protected CreateInterfaceElementCommand newCreateCommand(final IInterfaceElement interfaceElement,
			final boolean isInput) {
		return null;
	}

	@Override
	protected CreateInterfaceElementCommand newInsertCommand(final IInterfaceElement interfaceElement,
			final boolean isInput, final int index) {
		return null;
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return null;
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return null;
	}

	@Override
	protected ChangeDataTypeCommand newChangeTypeCommand(final VarDeclaration data, final DataType newType) {
		return null;
	}

	@Override
	protected FBNetworkElement getType() {
		return (FBNetworkElement) type;
	}

	@Override
	protected void setTableInput() {
		final FBNetworkElement selection = getType();
		if (selection instanceof Multiplexer) {
			((FordiacInterfaceListProvider) inputProvider)
			.setInput(((StructManipulator) selection).getStructType().getMemberVariables()); //
			((FordiacInterfaceListProvider) outputProvider)
			.setInput(((StructManipulator) selection).getInterface().getOutputVars());
		} else if (selection instanceof Demultiplexer) {
			((FordiacInterfaceListProvider) inputProvider)
			.setInput(((StructManipulator) selection).getInterface().getInputVars());
			((FordiacInterfaceListProvider) outputProvider)
			.setInput(((StructManipulator) selection).getStructType().getMemberVariables()); //

		} else if (selection instanceof SubApp && selection.getType() != null) { // untyped subapp in typed subapp //
			((VarDeclarationListProvider) inputProvider)
					.setInput(selection.getType().getInterfaceList().getInputVars());
			((VarDeclarationListProvider) outputProvider)
					.setInput(selection.getType().getInterfaceList().getOutputVars());
		}
		else {
			super.setTableInput();
		}

	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	protected void setTableInputFBType(final FBType type) {

	}
}
