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
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.Command;

public class CreateFBElementInGroupCommand extends Command {

	private final Group group;
	private final AbstractCreateFBNetworkElementCommand elementCreateCmd;

	public CreateFBElementInGroupCommand(final PaletteEntry paletteEntry, final Group group, final int x,
			final int y) {
		this.group = group;
		elementCreateCmd = createCreateCommand(paletteEntry, group.getFbNetwork(), x, y);

	}

	@Override
	public boolean canExecute() {
		return group != null && elementCreateCmd != null && elementCreateCmd.canExecute();
	}

	@Override
	public void execute() {
		elementCreateCmd.execute();
		group.getGroupElements().add(elementCreateCmd.getElement());
	}

	@Override
	public void undo() {
		group.getGroupElements().remove(elementCreateCmd.getElement());
		elementCreateCmd.undo();
	}

	@Override
	public void redo() {
		elementCreateCmd.redo();
		group.getGroupElements().add(elementCreateCmd.getElement());
	}

	private static AbstractCreateFBNetworkElementCommand createCreateCommand(final PaletteEntry paletteEntry,
			final FBNetwork fbNetwork, final int x, final int y) {
		if (paletteEntry instanceof FBTypePaletteEntry) {
			return new FBCreateCommand((FBTypePaletteEntry) paletteEntry, fbNetwork, x, y);
		}
		if (paletteEntry instanceof SubApplicationTypePaletteEntry) {
			return new CreateSubAppInstanceCommand((SubApplicationTypePaletteEntry) paletteEntry, fbNetwork, x, y);
		}
		return null;
	}

}
