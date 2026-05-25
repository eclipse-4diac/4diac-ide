/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class EditUntypedSubappVarInOutSection extends AbstractEditTypeVarInOutSection {

	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement ie) {
		return new CreateVarInOutCommand(getLastUsedDataType(getType().getInterface(), ie), getType().getInterface(),
				getInsertingIndex(ie));
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement ie, final int index) {
		return new CreateVarInOutCommand(ie, getType().getInterface(), index);
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(input);
	}
}