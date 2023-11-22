/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.gef.commands.Command;

public abstract class AbstractChangeAttributeCommand extends Command {
	private final Attribute attribute;

	protected AbstractChangeAttributeCommand(final Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public boolean canExecute() {
		return attribute != null;
	}

	@Override
	public final void execute() {
		doExecute();
	}

	@Override
	public final void undo() {
		doUndo();
	}

	@Override
	public final void redo() {
		doRedo();
	}

	protected abstract void doExecute();

	protected abstract void doRedo();

	protected abstract void doUndo();

	public Attribute getAttribute() {
		return attribute;
	}
}
