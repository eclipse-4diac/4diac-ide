/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018, 2022 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl   - fixed bounds issues and removed redo call from execute to
 *   				 allow better subclasing
 *   Bianca Wiesmayr - reuse abstract change order class
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class ChangeInterfaceOrderCommand extends AbstractChangeListElementOrderCommand<IInterfaceElement> {

	@SuppressWarnings("unchecked")
	public ChangeInterfaceOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		super(selection, moveUp, (EList<IInterfaceElement>) getInterfaceList(selection));
	}

	@SuppressWarnings("unchecked")
	public ChangeInterfaceOrderCommand(final IInterfaceElement selection, final int newIndex) {
		super(selection, newIndex, (EList<IInterfaceElement>) getInterfaceList(selection));
	}

	private static EList<? extends IInterfaceElement> getInterfaceList(final IInterfaceElement selection) {
		if ((null != selection) && (selection.eContainer() instanceof InterfaceList)) {
			final InterfaceList interfaceList = (InterfaceList) selection.eContainer();
			if (selection.isIsInput()) {
				return getInputList(selection, interfaceList);
			}
			return getOutputList(selection, interfaceList);
		}
		return (EList<? extends IInterfaceElement>) Collections.<Event>emptyList();
	}

	private static EList<? extends IInterfaceElement> getOutputList(final IInterfaceElement selection,
			final InterfaceList interfaceList) {
		if (selection instanceof Event) {
			return interfaceList.getEventOutputs();
		}
		if (selection instanceof AdapterDeclaration) {
			return interfaceList.getPlugs();
		}
		return interfaceList.getOutputVars();
	}

	private static EList<? extends IInterfaceElement> getInputList(final IInterfaceElement selection,
			final InterfaceList interfaceList) {
		if (selection instanceof Event) {
			return interfaceList.getEventInputs();
		}
		if (selection instanceof AdapterDeclaration) {
			return interfaceList.getSockets();
		}
		return interfaceList.getInputVars();
	}
}
