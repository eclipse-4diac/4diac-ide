/*******************************************************************************
 * Copyright (c) 2012, 2024 TU Wien ACIN, Profactor GmbH, fortiss GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

class AdapterFBCreateCommand extends FBCreateCommand {
	final AdapterDeclaration adapterDecl;

	AdapterFBCreateCommand(final int x, final int y, final AdapterDeclaration adapterDecl, final FBType parent) {
		super((FBTypeEntry) adapterDecl.getType().getTypeEntry(), getFBNetwork(parent),
				LibraryElementFactory.eINSTANCE.createAdapterFB(), x, y);
		this.adapterDecl = adapterDecl;
		getAdapterFB().setAdapterDecl(adapterDecl);
	}

	private AdapterFB getAdapterFB() {
		return (AdapterFB) getElement();
	}

	@Override
	public void execute() {
		adapterDecl.setAdapterFB(getAdapterFB());
		if (getFBNetwork() == null) {
			// if it is a non CFB we need to set the interface only adapter FB ref for
			// keeping the adapter FBs containment
			adapterDecl.setInterfaceOnlyAdapterFB(getAdapterFB());
		}
		super.execute();

	}

	@Override
	protected void checkName() {
		// for adapters we already have a correct name and do not want to change that.
		// Also for basics, simples, and
		// SIFBs we are not in an fbnetwork and name checking wouldn't even work.
		getAdapterFB().setName(getAdapterFB().getAdapterDecl().getName());
	}

	private static FBNetwork getFBNetwork(final FBType parent) {
		if (parent instanceof final CompositeFBType cFBType && !(parent instanceof SubAppType)) {
			return cFBType.getFBNetwork();
		}
		return null;
	}

	@Override
	protected InterfaceList createInterfaceList() {
		return getAdapterFB().getType().getInterfaceList().copy();
	}

}
