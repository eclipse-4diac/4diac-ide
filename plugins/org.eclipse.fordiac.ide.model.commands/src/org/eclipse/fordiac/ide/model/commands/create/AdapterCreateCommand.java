/*******************************************************************************
 * Copyright (c) 2012 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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

	public AdapterCreateCommand(int x, int y, AdapterDeclaration adapterDecl, CompositeFBType parentComposite) {
		super(parentComposite.getFBNetwork(), LibraryElementFactory.eINSTANCE.createAdapterFB(), x, y);
		getAdapterFB().setPaletteEntry(adapterDecl.getType().getPaletteEntry());
		getAdapterFB().setAdapterDecl(adapterDecl);
	}

	private AdapterFB getAdapterFB() {
		return (AdapterFB) getElement();
	}

	@Override
	protected InterfaceList getTypeInterfaceList() {
		return getAdapterFB().getType().getInterfaceList();
	}

}
