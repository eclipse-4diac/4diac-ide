/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.jface.viewers.IContentProvider;

public abstract class AbstractEditInterfaceDataSection extends AbstractEditInterfaceSection {
	
	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new InterfaceContentProvider(true, InterfaceContentProviderType.DATA);
	}
	
	@Override
	protected IContentProvider getInputsContentProvider() {
		return new InterfaceContentProvider(false, InterfaceContentProviderType.DATA);
	}

	@Override
	protected String[] fillTypeCombo() {
		List<String> list = new ArrayList<>();
		for(DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()){
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}
}
