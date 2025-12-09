/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.gef.commands.Command;

public abstract class AbstractChangeDirectlyDerivedTypeCommand extends Command implements ScopedCommand {
	private final DirectlyDerivedType directlyDerivedType;

	protected AbstractChangeDirectlyDerivedTypeCommand(final DirectlyDerivedType directlyDerivedType) {
		this.directlyDerivedType = Objects.requireNonNull(directlyDerivedType);
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

	public DirectlyDerivedType getDirectlyDerivedType() {
		return directlyDerivedType;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(directlyDerivedType);
	}
}
