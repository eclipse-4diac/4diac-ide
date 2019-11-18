/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.jface.viewers.IContentProvider;

public abstract class AbstractEditInterfaceAdapterSection extends AbstractEditInterfaceSection {

	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new InterfaceContentProvider(false, InterfaceContentProviderType.ADAPTER);
	}

	@Override
	protected IContentProvider getInputsContentProvider() {
		return new InterfaceContentProvider(true, InterfaceContentProviderType.ADAPTER);
	}

	@Override
	protected String[] fillTypeCombo() {
		List<String> list = new ArrayList<>();
		if (null != getType()) {
			getPalette().getAdapterTypesSorted().forEach(adpType -> list.add(adpType.getLabel()));
		}
		return list.toArray(new String[list.size()]);
	}

	protected AdapterType getLastUsedAdapterType(InterfaceList interfaceList, IInterfaceElement interfaceElement,
			boolean isInput) {
		if (null != interfaceElement) {
			return (AdapterType) interfaceElement.getType();
		}
		EList<AdapterDeclaration> adapterList = getAdapterList(interfaceList, isInput);
		if (!adapterList.isEmpty()) {
			return adapterList.get(adapterList.size() - 1)
					.getType();
		}
		return getPalette().getAdapterTypes().get(0).getType();
	}

	protected int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput) {
		if (null != interfaceElement) {
			InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getAdapterList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<AdapterDeclaration> getAdapterList(InterfaceList interfaceList, boolean isInput) {
		return isInput ? interfaceList.getSockets() : interfaceList.getPlugs();
	}
}
