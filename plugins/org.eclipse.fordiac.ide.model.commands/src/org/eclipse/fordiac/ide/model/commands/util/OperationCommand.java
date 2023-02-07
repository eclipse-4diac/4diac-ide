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
package org.eclipse.fordiac.ide.model.commands.util;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class OperationCommand extends Command {

	private final IUndoableOperation operation;
	private final boolean critical;

	/** Create a new command wrapping an {@link IUndoableOperation}.
	 *
	 * @param operation The operation to wrap */
	public OperationCommand(final IUndoableOperation operation) {
		this(operation, true);
	}

	/** Create a new command wrapping an {@link IUndoableOperation}.
	 *
	 * @param operation The operation to wrap
	 * @param critical  If the operation is considered critical, indicating how to handle execptions during execution,
	 *                  undo, and redo. This allows to subdue execptions for less critical operations that may fail
	 *                  spuriously. */
	public OperationCommand(final IUndoableOperation operation, final boolean critical) {
		super(operation.getLabel());
		this.operation = operation;
		this.critical = critical;
	}

	@Override
	public boolean canExecute() {
		return operation.canExecute();
	}

	@Override
	public boolean canRedo() {
		return operation.canRedo();
	}

	@Override
	public boolean canUndo() {
		return operation.canUndo();
	}

	@Override
	public void dispose() {
		operation.dispose();
	}

	@Override
	public void execute() {
		try {
			operation.execute(null, null);
		} catch (final Exception e) {
			handleException(e);
		}
	}

	@Override
	public void redo() {
		try {
			operation.redo(null, null);
		} catch (final Exception e) {
			handleException(e);
		}
	}

	@Override
	public void undo() {
		try {
			operation.undo(null, null);
		} catch (final Exception e) {
			handleException(e);
		}
	}

	protected void handleException(final Exception e) {
		if (critical) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} else {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	public IUndoableOperation getOperation() {
		return operation;
	}
}
