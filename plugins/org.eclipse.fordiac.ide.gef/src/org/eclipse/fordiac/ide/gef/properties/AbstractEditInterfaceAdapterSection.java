/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Monika Wenger - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractEditInterfaceAdapterSection extends AbstractEditInterfaceSection {
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		inputsViewer.setContentProvider(new InterfaceContentProvider(true, InterfaceContentProviderType.ADAPTER));
		outputsViewer.setContentProvider(new InterfaceContentProvider(false, InterfaceContentProviderType.ADAPTER));
	}

	@Override
	protected void setType(Object input) {
		super.setType(input);
		setCellEditors();  //only now the types are correctly set
	}

	@Override
	protected String[] fillTypeCombo() {
		ArrayList<String> list = new ArrayList<String>();
		if(null != getType()) {
			for (AdapterTypePaletteEntry adaptertype : getAdapterTypes(getPalette())){
				list.add(adaptertype.getLabel());
			}
		}
		return list.toArray(new String[list.size()]);		
	}
}
