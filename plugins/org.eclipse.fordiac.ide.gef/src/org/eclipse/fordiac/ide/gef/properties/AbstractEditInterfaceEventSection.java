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
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.jface.viewers.IContentProvider;

public abstract class AbstractEditInterfaceEventSection extends AbstractEditInterfaceSection {

	@Override
	protected IContentProvider getOutputsContentProvider() {
		return new InterfaceContentProvider(false, InterfaceContentProviderType.EVENT);
	}
	
	@Override
	protected IContentProvider getInputsContentProvider() {
		return new InterfaceContentProvider(true, InterfaceContentProviderType.EVENT);
	}

	@Override
	protected String[] fillTypeCombo() {		
		List<String> list = new ArrayList<>();
		for(DataType dataType : EventTypeLibrary.getInstance().getEventTypes()){
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}
}
