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

import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class UntypeSubAppCommand extends Command {
	private final SubApp subapp;
	private final SubApplicationTypePaletteEntry typeEntry;

	public UntypeSubAppCommand(final SubApp subapp) {
		super(Messages.UntypeSubappCommand_Label);
		this.subapp = subapp;
		typeEntry = (SubApplicationTypePaletteEntry) subapp.getPaletteEntry();
	}

	@Override
	public boolean canExecute() {
		return null != typeEntry;
	}

	@Override
	public void execute() {
		if (subapp.getSubAppNetwork() == null) {
			// the subapp network was not yet copied from the type, i.e., subapp was never shown in viewer
			subapp.setSubAppNetwork(
					FBNetworkHelper.copyFBNetWork(subapp.getType().getFBNetwork(), subapp.getInterface()));
		}
		removeType();
	}

	@Override
	public void redo() {
		removeType();
	}

	@Override
	public void undo() {
		subapp.setPaletteEntry(typeEntry);
	}

	private void removeType() {
		subapp.setPaletteEntry(null);
	}

}
