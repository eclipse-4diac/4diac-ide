/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class CreateSubAppInstanceCommand extends AbstractCreateFBNetworkElementCommand {

	private final SubApplicationTypePaletteEntry paletteEntry;

	public CreateSubAppInstanceCommand(final SubApplicationTypePaletteEntry paletteEntry, final FBNetwork fbNetwork,
			final int x, final int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createSubApp(), x, y);
		this.paletteEntry = paletteEntry;
		setLabel(Messages.CreateSubAppInstanceCommand_CreateSubapplicationInstance);
		getSubApp().setPaletteEntry(paletteEntry);
	}

	@Override
	public boolean canExecute() {
		return paletteEntry != null && super.canExecute();
	}

	@Override
	protected InterfaceList getTypeInterfaceList() {
		return paletteEntry.getType().getInterfaceList();
	}

	public SubApp getSubApp() {
		return (SubApp) getElement();
	}

	public SubApplicationTypePaletteEntry getPaletteEntry() {
		return paletteEntry;
	}

}
