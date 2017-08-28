/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hoffmann, Alois Zoitl, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class FBCreateCommand extends AbstractCreateFBNetworkElementCommand {
	protected FBTypePaletteEntry paletteEntry;	

	public FBCreateCommand(final FBTypePaletteEntry paletteEntry, final FBNetwork fbNetwork, int x, int y) {
		super(fbNetwork, x, y);
		this.paletteEntry = paletteEntry;
		setLabel(Messages.FBCreateCommand_LABLE_CreateFunctionBlock);
		element = LibraryElementFactory.eINSTANCE.createFB();
		getFB().setPaletteEntry(paletteEntry);
	}

	public FB getFB() {
		return (FB)element;
	}

	public FBTypePaletteEntry getPaletteEntry() {
		return paletteEntry;
	}
	
	@Override
	public boolean canExecute() {
		return paletteEntry != null && super.canExecute();
	}
	
	@Override
	protected InterfaceList getTypeInterfaceList() {
		return paletteEntry.getFBType().getInterfaceList();
	}
	
}