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
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EditInterfaceDataSection extends AbstractEditInterfaceSection {
	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		inputsViewer.setContentProvider(new InterfaceContentProvider(true, InterfaceContentProviderType.DATA));
		outputsViewer.setContentProvider(new InterfaceContentProvider(false, InterfaceContentProviderType.DATA));
		setCellEditors();
	}

	@Override
	protected CreateSubAppInterfaceElementCommand newCommand(boolean isInput) {
		return new CreateSubAppInterfaceElementCommand(DataTypeLibrary.getInstance().getType(fillTypeCombo()[2]), getType().getInterface(), isInput, -1);
	}

	@Override
	protected String[] fillTypeCombo() {
		ArrayList<String> list = new ArrayList<String>();
		for(DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()){
			list.add(dataType.getName());
		}
		return list.toArray(new String[0]);
	}
}
