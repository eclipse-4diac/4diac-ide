/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractEditInterfaceAdapterSection extends AbstractEditInterfaceSection {


	@Override
	protected String[] fillTypeCombo() {
		final List<String> list = new ArrayList<>();
		if ((null != getType()) && (null != getTypeLibrary())) {
			getTypeLibrary().getAdapterTypesSorted().forEach(adpType -> list.add(adpType.getTypeName()));
		}
		return list.toArray(new String[list.size()]);
	}

	protected AdapterType getLastUsedAdapterType(final InterfaceList interfaceList, final IInterfaceElement interfaceElement,
			final boolean isInput) {
		if (null != interfaceElement) {
			return (AdapterType) interfaceElement.getType();
		}
		final EList<AdapterDeclaration> adapterList = getAdapterList(interfaceList, isInput);
		if (!adapterList.isEmpty()) {
			return adapterList.get(adapterList.size() - 1).getAdapterType();
		}
		return getTypeLibrary().getAdapterTypes().values().iterator().next().getType();
	}

	@Override
	protected int getInsertingIndex(final IInterfaceElement interfaceElement, final boolean isInput) {
		if (null != interfaceElement) {
			final InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getAdapterList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<AdapterDeclaration> getAdapterList(final InterfaceList interfaceList, final boolean isInput) {
		return isInput ? interfaceList.getSockets() : interfaceList.getPlugs();
	}

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		if (entry instanceof AdapterDeclaration) {
			cmd.add(newInsertCommand((AdapterDeclaration) entry, isInputsViewer(), index));
		}
	}


}
