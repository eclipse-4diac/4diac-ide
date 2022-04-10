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
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.gef.commands.Command;

public class ChangeFbTypeCommand extends Command {

	final FB fb;
	FBTypeEntry oldEntry;
	final FBTypeEntry newType;

	public ChangeFbTypeCommand(final FB fb, final FBTypeEntry newType) {
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
		oldEntry = (FBTypeEntry) fb.getType().getTypeEntry();
		setFBType(newType);
	}

	private void setFBType(final FBTypeEntry entry) {
		fb.setTypeEntry(entry);
		fb.setInterface(entry.getType().getInterfaceList().copy());
	}

	@Override
	public void redo() {
		setFBType(newType);
	}

	@Override
	public void undo() {
		setFBType(oldEntry);
	}
}
