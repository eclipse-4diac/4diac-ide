/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.commands;

import org.eclipse.gef.commands.Command;

/** Executes an EMF command on a GEF command stack. */
public class EMFCommandWrapper extends Command {

	private final org.eclipse.emf.common.command.Command emfCommand;

	public EMFCommandWrapper(final org.eclipse.emf.common.command.Command emfCommand) {
		super(emfCommand.getLabel());
		this.emfCommand = emfCommand;
	}

	@Override
	public boolean canExecute() {
		return emfCommand.canExecute();
	}

	@Override
	public void execute() {
		emfCommand.execute();
	}

	@Override
	public boolean canUndo() {
		return emfCommand.canUndo();
	}

	@Override
	public void undo() {
		emfCommand.undo();
	}

	@Override
	public void redo() {
		emfCommand.redo();
	}

	@Override
	public void dispose() {
		emfCommand.dispose();
	}
}
