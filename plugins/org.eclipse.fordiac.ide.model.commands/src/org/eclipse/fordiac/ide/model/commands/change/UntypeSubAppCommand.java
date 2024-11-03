/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

public class UntypeSubAppCommand extends AbstractUpdateFBNElementCommand {

	public UntypeSubAppCommand(final SubApp subapp) {
		super(subapp);
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && oldElement instanceof final TypedSubApp subapp && subapp.getType() != null;
	}

	@Override
	protected void createNewFB() {
		super.createNewFB();
		// the FBNetwork can only be copied at the end when the interface is correctly
		// setup
		((UntypedSubApp) newElement).setSubAppNetwork(FBNetworkHelper
				.copyFBNetWork(((TypedSubApp) oldElement).getType().getFBNetwork(), newElement.getInterface()));
	}

	@Override
	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		return LibraryElementFactory.eINSTANCE.createUntypedSubApp();
	}

	@Override
	protected void setInterface() {
		newElement.setInterface(oldElement.getType().getInterfaceList().copy());
	}
}
