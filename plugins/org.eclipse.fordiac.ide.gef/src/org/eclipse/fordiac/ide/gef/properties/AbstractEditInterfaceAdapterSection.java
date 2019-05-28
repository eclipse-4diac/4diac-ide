/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *   Monika Wenger - initial implementation
 *   Alois Zoitl - moved adapter search code to palette
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;
import java.util.List;

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
}
