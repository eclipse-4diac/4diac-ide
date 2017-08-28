/*******************************************************************************
 * Copyright (c) 2012 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class AdapterCreateCommand extends FBCreateCommand {

	AdapterDeclaration adapterDecl;
		
	public AdapterCreateCommand(int x, int y, AdapterDeclaration adapterDecl, CompositeFBType parentComposite) {
		super(null, parentComposite.getFBNetwork(), x, y);		
		this.adapterDecl = adapterDecl;
		element = LibraryElementFactory.eINSTANCE.createAdapterFB();
		getAdapterFB().setPaletteEntry(adapterDecl.getType().getPaletteEntry());
		getAdapterFB().setAdapterDecl(adapterDecl);
	}
	
	private AdapterFB getAdapterFB(){
		return (AdapterFB)element;
	}
	
	@Override
	protected InterfaceList getTypeInterfaceList() {
		return getAdapterFB().getType().getInterfaceList();
	}

}
