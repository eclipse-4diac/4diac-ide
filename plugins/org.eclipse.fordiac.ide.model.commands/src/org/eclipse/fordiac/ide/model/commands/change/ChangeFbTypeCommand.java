/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;


import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.gef.commands.Command;

public class ChangeFbTypeCommand extends Command {

	final FB fb;
	FBTypePaletteEntry oldFBTypePaletteEntry;
	final FBTypePaletteEntry newType;

	public ChangeFbTypeCommand(final FB fb, final FBTypePaletteEntry newType) {
		super();
		this.fb = fb;
		this.newType = newType;
	}

	@Override
	public boolean canExecute() {
		return FBNetworkHelper.isTypeInsertionSave(newType.getType(), fb);
	}

	@Override
	public void execute() {
		oldFBTypePaletteEntry = (FBTypePaletteEntry) fb.getType().getPaletteEntry();
		setFBType(newType);
	}

	private void setFBType(final FBTypePaletteEntry paletteEntry) {
		fb.setPaletteEntry(paletteEntry);
		fb.setInterface(paletteEntry.getType().getInterfaceList().copy());
	}

	@Override
	public void redo() {
		setFBType(newType);
	}

	@Override
	public void undo() {
		setFBType(oldFBTypePaletteEntry);
	}
}
