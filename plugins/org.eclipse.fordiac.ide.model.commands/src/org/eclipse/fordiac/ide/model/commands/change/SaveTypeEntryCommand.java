/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class SaveTypeEntryCommand extends Command {
	private final StructuredType typeEntry;

	public SaveTypeEntryCommand(final StructuredType typeEntry) {
		super(Messages.UntypeSubappCommand_Label);
		this.typeEntry = typeEntry;
	}

	@Override
	public boolean canExecute() {
		return null != typeEntry;
	}

	@Override
	public void execute() {
		try {
			typeEntry.getTypeEntry().save(typeEntry);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	public void redo() {
	}

	@Override
	public void undo() {
	}

}
