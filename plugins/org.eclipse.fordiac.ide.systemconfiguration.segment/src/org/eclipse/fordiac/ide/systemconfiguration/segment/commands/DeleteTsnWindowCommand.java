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

import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;
import org.eclipse.gef.commands.Command;

public class DeleteTsnWindowCommand extends Command {
	private final TsnConfiguration config;
	private final TsnWindow toDelete;

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
		config.getWindows().remove(toDelete);
	}

	@Override
	public void undo() {
		config.getWindows().add(toDelete);
	}

	@Override
	public void redo() {
		config.getWindows().remove(toDelete);
	}
}
