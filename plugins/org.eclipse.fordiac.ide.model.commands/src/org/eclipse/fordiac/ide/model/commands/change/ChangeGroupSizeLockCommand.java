/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.Command;

public class ChangeGroupSizeLockCommand extends Command implements ScopedCommand {

	private final Group model;
	private final boolean value;
	private boolean oldValue;

	public ChangeGroupSizeLockCommand(final Group model, final boolean value) {
		this.model = Objects.requireNonNull(model);
		this.value = value;
	}

	@Override
	public void execute() {
		oldValue = model.isLocked();
		model.setLocked(value);
	}

	@Override
	public void redo() {
		model.setLocked(value);
	}

	@Override
	public void undo() {
		// don't just toggle for undo, otherwise writing the same value would result in
		// wrong undos
		model.setLocked(oldValue);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(model);
	}
}
