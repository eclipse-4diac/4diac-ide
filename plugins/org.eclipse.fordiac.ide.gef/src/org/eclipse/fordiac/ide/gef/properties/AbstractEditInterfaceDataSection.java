/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
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
 *   Daniel Lindhuber - added addEntry method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IContentProvider;

public abstract class AbstractEditInterfaceDataSection extends AbstractEditInterfaceSection {

	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new InterfaceContentProvider(false, InterfaceContentProviderType.DATA);
	}

	@Override
	protected IContentProvider getInputsContentProvider() {
		return new InterfaceContentProvider(true, InterfaceContentProviderType.DATA);
	}

	@Override
	protected String[] fillTypeCombo() {
		List<String> list = new ArrayList<>();
		for (DataType dataType : getDataTypeLib().getDataTypesSorted()) {
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}

	protected DataType getLastUsedDataType(InterfaceList interfaceList, boolean isInput,
			IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return interfaceElement.getType();
		}
		EList<VarDeclaration> dataList = getDataList(interfaceList, isInput);
		if (!dataList.isEmpty()) {
			return dataList.get(dataList.size() - 1).getType();
		}
		return getDataTypeLib().getType("bool");//$NON-NLS-1$ // bool is default
	}

	@Override
	protected int getInsertingIndex(IInterfaceElement interfaceElement, boolean isInput) {
		if (null != interfaceElement) {
			InterfaceList interfaceList = (InterfaceList) interfaceElement.eContainer();
			return getInsertingIndex(interfaceElement, getDataList(interfaceList, isInput));
		}
		return -1;
	}

	private static EList<VarDeclaration> getDataList(InterfaceList interfaceList, boolean isInput) {
		return isInput ? interfaceList.getInputVars() : interfaceList.getOutputVars();
	}

	@Override
	public void addEntry(Object entry, int index, CompoundCommand cmd) {
		// can not use instanceof since AdapterImplementation is derived from
		// VarDeclaration and this would break the addEntry method in the adapter
		// section
		if (entry.getClass().equals(VarDeclarationImpl.class)) {
			cmd.add(newInsertCommand((IInterfaceElement) entry, getIsInputsViewer(), index));
		}
	}

}
