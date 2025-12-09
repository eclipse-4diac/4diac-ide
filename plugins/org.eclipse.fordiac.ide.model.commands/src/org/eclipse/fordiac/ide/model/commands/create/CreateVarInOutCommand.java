/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class CreateVarInOutCommand extends CreateInterfaceElementCommand {

	public CreateVarInOutCommand(final IInterfaceElement copySrc, final InterfaceList targetInterfaceList,
			final int index) {
		super(copySrc, true, targetInterfaceList, index);
	}

	public CreateVarInOutCommand(final DataType dataType, final String name, final InterfaceList interfaceList,
			final int index) {
		super(dataType, name, interfaceList, true, index);
	}

	public CreateVarInOutCommand(final DataType dataType, final InterfaceList interfaceList, final int index) {
		super(dataType, getNameProposal(), interfaceList, true, index);
	}

	@Override
	protected EList<? extends IInterfaceElement> getInterfaceListContainer() {
		return getInterfaceList().getInOutVars();
	}

	protected static String getNameProposal() {
		return FordiacKeywords.VARINOUT;
	}

}
