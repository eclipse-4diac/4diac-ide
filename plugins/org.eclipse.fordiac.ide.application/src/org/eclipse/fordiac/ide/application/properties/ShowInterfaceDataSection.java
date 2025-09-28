/*******************************************************************************
 * Copyright (c) 2022, 2025 Primetals Technologies Germany GmbH
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

import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

public class ShowInterfaceDataSection extends AbstractEditInterfaceDataSection {
	@Override
	protected BlockFBNetworkElement getInputType(final Object input) {
		return ShowInterfaceAdapterSection.getFBNetworkElementFromInput(input);
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
	protected BlockFBNetworkElement getType() {
		return (BlockFBNetworkElement) type;
	}

	@Override
	protected void setTableInput() {
		if (getType() instanceof final StructManipulator structSel) {
			setTableInput(structSel.getInterface());
		} else {
			super.setTableInput();
		}
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public boolean isShowTableEditButtons() {
		return false;
	}

	@Override
	protected InterfaceList getInterface() {
		return getType().getTypeInterface();
	}

}
