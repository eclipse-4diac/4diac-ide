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

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.Command;

public class ChangeFbTypeCommand extends Command {

	final FB fb;
	FBType oldType;
	final FBType newType;

	public ChangeFbTypeCommand(final FB fb, final FBType newType) {
		super();
		this.fb = fb;
		this.newType = newType;
	}

	@Override
	public boolean canExecute() {
		return FBNetworkHelper.isTypeInsertionSave(newType, fb);
	}

	@Override
	public void execute() {
		oldType = fb.getType();
		redo();
	}

	@Override
	public void redo() {
		fb.setPaletteEntry(newType.getPaletteEntry());
	}

	@Override
	public void undo() {
		fb.setPaletteEntry(oldType.getPaletteEntry());
	}

}
