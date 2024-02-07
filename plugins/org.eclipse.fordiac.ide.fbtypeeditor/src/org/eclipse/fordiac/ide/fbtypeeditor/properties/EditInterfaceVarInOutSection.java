/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *   Martin Jobst - lock editing for function FBs
 *   Patrick Aigner - moved functionality to abstract parent class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractEditVarInOutSection;
import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class EditInterfaceVarInOutSection extends AbstractEditVarInOutSection {

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected FBType getInputType(final Object input) {
		return FBTypePropertiesFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected void setTableInput() {
		inputProvider.setInput(getType().getInterfaceList().getInOutVars());
		if (isShowTableEditButtons()) {
			inputButtons.setEnabled(isEditable());
		}
	}

	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement ie) {
		return new CreateVarInOutCommand(getLastUsedDataType(getType().getInterfaceList(), ie),
				getType().getInterfaceList(), getInsertingIndex(ie));
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement ie, final int index) {
		return new CreateVarInOutCommand(ie, getType().getInterfaceList(), index);
	}
}
