/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Monika Wenger - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EditInterfaceAdapterSection extends AbstractEditInterfaceSection {
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
	protected CreateInterfaceElementCommand newCommand(boolean isInput) {
		AdapterType type = (AdapterType) getType().getFbNetwork().getApplication().getAutomationSystem().getPalette().getTypeEntry(fillTypeCombo()[0]).getType();
		return new CreateInterfaceElementCommand(type, "", "", getType().getInterface(), isInput, -1);
	}

	@Override
	protected String[] fillTypeCombo() {
		ArrayList<String> list = new ArrayList<String>();
		if(null != getType()) {
			for (AdapterTypePaletteEntry adaptertype : getAdapterTypes(getType().getFbNetwork().getApplication().getAutomationSystem().getPalette())){
				list.add(adaptertype.getAdapterType().getName());
			}
		}
		return list.toArray(new String[0]);		
	}
	
	private static ArrayList<AdapterTypePaletteEntry> getAdapterTypes(final Palette systemPalette){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();		
		Palette pal = systemPalette;
		if(null == pal){
			pal = TypeLibrary.getInstance().getPalette();
		}			
		retVal.addAll(getAdapterGroup(pal.getRootGroup()));		
		return retVal;
	}
	
	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroup(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();	
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator.hasNext();) {
			PaletteGroup paletteGroup = iterator.next();
			retVal.addAll(getAdapterGroup(paletteGroup));		
		}		
		retVal.addAll(getAdapterGroupEntries(group));		
		return retVal;
	}
	
	private static ArrayList<AdapterTypePaletteEntry> getAdapterGroupEntries(final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group){
		ArrayList<AdapterTypePaletteEntry> retVal = new ArrayList<AdapterTypePaletteEntry>();	
		for (PaletteEntry entry : group.getEntries()) {
			if(entry instanceof AdapterTypePaletteEntry){
				retVal.add((AdapterTypePaletteEntry) entry);				
			}
		}
		return retVal;
	}
}
