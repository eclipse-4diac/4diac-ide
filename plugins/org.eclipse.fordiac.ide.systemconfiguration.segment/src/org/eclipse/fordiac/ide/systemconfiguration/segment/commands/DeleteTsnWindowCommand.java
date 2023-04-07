/*******************************************************************************
 * Copyright (c) 2022 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.commands;

import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteTsnWindowCommand extends Command {
	private final TsnConfiguration config;
	private final TsnWindow toDelete;
	private final CompoundCommand unmapCommands = new CompoundCommand();

	public DeleteTsnWindowCommand(final TsnConfiguration config, final TsnWindow toDelete) {
		this.config = config;
		this.toDelete = toDelete;
	}

	@Override
	public boolean canExecute() {
		return config.getWindows().contains(toDelete);
	}

	@Override
	public void execute() {
		handleMappedElements();
		config.getWindows().remove(toDelete);
	}

	private void handleMappedElements() {
		for (final FBNetworkElement el : toDelete.getMappedElements()) {
			unmapCommands.add(new UnmapCommand(el));
		}
		unmapCommands.execute();
	}

	@Override
	public void undo() {
		config.getWindows().add(toDelete);
		unmapCommands.undo();
	}

	@Override
	public void redo() {
		config.getWindows().remove(toDelete);
		unmapCommands.redo();
	}
}
