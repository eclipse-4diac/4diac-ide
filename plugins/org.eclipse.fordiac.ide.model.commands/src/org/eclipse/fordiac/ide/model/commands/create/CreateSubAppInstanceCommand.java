/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class CreateSubAppInstanceCommand extends AbstractCreateFBNetworkElementCommand {
	
	protected SubApplicationTypePaletteEntry paletteEntry;
	
	public CreateSubAppInstanceCommand(final SubApplicationTypePaletteEntry paletteEntry, final FBNetwork fbNetwork, int x, int y) {
		super(fbNetwork, x, y);
		this.paletteEntry = paletteEntry;
		setLabel("Create Subapplication Instance");
		element = LibraryElementFactory.eINSTANCE.createSubApp();
		getSubApp().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		getSubApp().setPaletteEntry(paletteEntry);
	}
	
	@Override
	public boolean canExecute() {
		return paletteEntry != null && super.canExecute();
	}
			
	@Override
	protected InterfaceList getTypeInterfaceList() {
		return paletteEntry.getSubApplicationType().getInterfaceList();
	}
	
	public SubApp getSubApp() {
		return (SubApp)element;
	}
}
