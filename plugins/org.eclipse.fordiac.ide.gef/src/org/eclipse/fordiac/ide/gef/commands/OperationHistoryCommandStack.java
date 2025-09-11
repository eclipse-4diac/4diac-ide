/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.commands.operations.UndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.commands.UnexecutableCommand;

public class OperationHistoryCommandStack extends CommandStack {

	private IUndoContext undoContext;
	private final IOperationHistory opHistory;
	private IUndoableOperation saveLocation;
	private OperationHistoryListener opHistoryListener;

	public OperationHistoryCommandStack() {
		this(new UndoContext());
	}

	public OperationHistoryCommandStack(final IUndoContext undoContext) {
		this.undoContext = undoContext;
		opHistory = OperationHistoryFactory.getOperationHistory();
	}

	@Override
	public void addCommandStackEventListener(final CommandStackEventListener listener) {
		super.addCommandStackEventListener(listener);
		if (opHistoryListener == null) {
			opHistoryListener = new OperationHistoryListener();
			opHistory.addOperationHistoryListener(opHistoryListener);
		}
	}

	/**
	 * As deprecated in #{@link org.eclipse.gef.commands.CommandStack} this method
	 * is not implemented. Use
	 * {@link #addCommandStackEventListener(CommandStackEventListener)} instead.
	 **/
	@Override
	public void addCommandStackListener(final CommandStackListener listener) {
		throw new UnsupportedOperationException(
				"addCommandStackListener shall not be used anymore. Use addCommandStackEventListener instead!"); //$NON-NLS-1$
	}

	@Override
	public boolean canRedo() {
		return opHistory.canRedo(undoContext);
	}

	@Override
	public boolean canUndo() {
		return opHistory.canUndo(undoContext);
	}

	@Override
	public void dispose() {
		if (opHistoryListener != null) {
			opHistory.removeOperationHistoryListener(opHistoryListener);
			opHistoryListener = null;
		}
	}

	@Override
	public void execute(final Command command) {
		if (command == null || !command.canExecute()) {
			return;
		}

		final CommandWrapper cmd = new CommandWrapper(command);
		cmd.addContext(undoContext);
		try {
			opHistory.execute(cmd, null, null);
		} catch (final ExecutionException e) {
			FordiacLogHelper.logError("Couldn't execute command: ", e); //$NON-NLS-1$
		}
	}

	@Override
	public void flush() {
		notifyListeners(null, PRE_FLUSH);
		// clear the entire undo/redo stack
		opHistory.dispose(undoContext, true, true, false);
		saveLocation = null;
		notifyListeners(null, POST_FLUSH);
	}

	@Override
	public Object[] getCommands() {
		final IUndoableOperation[] redoHistory = opHistory.getRedoHistory(undoContext);
		final IUndoableOperation[] undoHistory = opHistory.getUndoHistory(undoContext);
		final List<Command> commands = new ArrayList<>(redoHistory.length + undoHistory.length);

		for (final IUndoableOperation undoOp : undoHistory) {
			if (undoOp instanceof final CommandWrapper wrapper) {
				commands.add(wrapper.getCommand());
			}
		}

		for (int i = redoHistory.length - 1; i >= 0; i--) {
			if (redoHistory[i] instanceof final CommandWrapper wrapper) {
				commands.add(wrapper.getCommand());
			}
		}
		return commands.toArray();
	}

	private static Command getLastCommandFromHistory(final IUndoableOperation[] operationHistory) {
		for (int i = operationHistory.length - 1; i >= 0; i--) {
			if (operationHistory[i] instanceof final CommandWrapper wrapper) {
				return wrapper.getCommand();
			}
		}
		return null;
	}

	@Override
	public Command getRedoCommand() {
		return getLastCommandFromHistory(opHistory.getRedoHistory(undoContext));
	}

	@Override
	public Command getUndoCommand() {
		return getLastCommandFromHistory(opHistory.getUndoHistory(undoContext));
	}

	@Override
	public int getUndoLimit() {
		return opHistory.getLimit(undoContext);
	}

	@Override
	public boolean isDirty() {
		return opHistory.getUndoOperation(undoContext) != saveLocation;
	}

	@Override
	public void markSaveLocation() {
		notifyListeners(null, PRE_MARK_SAVE);
		saveLocation = opHistory.getUndoOperation(undoContext);
		notifyListeners(null, POST_MARK_SAVE);
	}

	@Override
	public void redo() {
		try {
			opHistory.redo(undoContext, null, null);
		} catch (final ExecutionException e) {
			FordiacLogHelper.logError("Couldn't redo command: ", e); //$NON-NLS-1$
		}
	}

	/**
	 * As deprecated in #{@link org.eclipse.gef.commands.CommandStack} this method
	 * is not implemented. Use
	 * {@link #removeCommandStackEventListener(CommandStackEventListener)} instead.
	 **/
	@Override
	public void removeCommandStackListener(final CommandStackListener listener) {
		throw new UnsupportedOperationException(
				"removeCommandStackListener shall not be used anymore. Use removeCommandStackEventListener instead!"); //$NON-NLS-1$
	}

	public IUndoContext getUndoContext() {
		return undoContext;
	}

	public void setUndoContext(final IUndoContext undoContext) {
		flush();
		this.undoContext = undoContext;
	}

	@Override
	public void setUndoLimit(final int undoLimit) {
		opHistory.setLimit(undoContext, undoLimit);
	}

	@Override
	public void undo() {
		try {
			opHistory.undo(undoContext, null, null);
		} catch (final ExecutionException e) {
			FordiacLogHelper.logError("Couldn't undo command: ", e); //$NON-NLS-1$
		}
	}

	private static class CommandWrapper extends AbstractOperation {

		private final Command cmd;

		public CommandWrapper(final Command cmd) {
			super(cmd.getLabel() != null ? cmd.getLabel() : ""); //$NON-NLS-1$
			this.cmd = cmd;
		}

		public Command getCommand() {
			return cmd;
		}

		@Override
		public boolean canExecute() {
			return cmd.canExecute();
		}

		@Override
		public boolean canRedo() {
			return cmd.canRedo();
		}

		@Override
		public boolean canUndo() {
			return cmd.canUndo();
		}

		@Override
		public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			cmd.execute();
			return Status.OK_STATUS;
		}

		@Override
		public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			cmd.redo();
			return Status.OK_STATUS;
		}

		@Override
		public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			cmd.undo();
			return Status.OK_STATUS;
		}

	}

	private class OperationHistoryListener implements IOperationHistoryListener {
		private int prevState;
		private IUndoableOperation prevOperation;

		private Command getCommand(final IUndoableOperation operation) {
			return (operation instanceof final CommandWrapper wrapper) ? wrapper.getCommand()
					: UnexecutableCommand.INSTANCE; // if we have a non GEF based operation provide dummy command for
													// listeners expecting command not being null
		}

		private int getCommandStackState(final OperationHistoryEvent event) {
			return switch (event.getEventType()) {
			case OperationHistoryEvent.ABOUT_TO_EXECUTE -> PRE_EXECUTE;
			case OperationHistoryEvent.ABOUT_TO_REDO -> PRE_REDO;
			case OperationHistoryEvent.REDONE -> POST_REDO;
			case OperationHistoryEvent.ABOUT_TO_UNDO -> PRE_UNDO;
			case OperationHistoryEvent.UNDONE -> POST_UNDO;
			case OperationHistoryEvent.OPERATION_NOT_OK -> handleOperationNotOk(event);
			case OperationHistoryEvent.OPERATION_ADDED -> handleOperationAdd(event);
			// for the following notifications we currently do not need to forward anything
			case OperationHistoryEvent.DONE, OperationHistoryEvent.OPERATION_REMOVED,
					OperationHistoryEvent.OPERATION_CHANGED ->
				0;
			default -> 0; // no notification needed
			};
		}

		private int handleOperationAdd(final OperationHistoryEvent event) {
			if (event.getOperation() != prevOperation) {
				return 0;
			}
			if (prevState == OperationHistoryEvent.DONE) {
				// only after the operation has been added to the stack we can send
				// POST_EXECUTE. Done is sent before that.
				return POST_EXECUTE;
			}
			return prevState;
		}

		private int handleOperationNotOk(final OperationHistoryEvent event) {
			if (event.getOperation() != prevOperation) {
				return 0;
			}
			return switch (prevState) {
			case OperationHistoryEvent.ABOUT_TO_EXECUTE -> POST_EXECUTE;
			case OperationHistoryEvent.ABOUT_TO_REDO -> POST_REDO;
			case OperationHistoryEvent.ABOUT_TO_UNDO -> POST_UNDO;
			default -> 0;
			};
		}

		@Override
		public void historyNotification(final OperationHistoryEvent event) {
			if (!event.getOperation().hasContext(undoContext)) {
				return;
			}

			final int state = getCommandStackState(event);
			if (state != 0) {
				notifyListeners(getCommand(event.getOperation()), state);
			}
			prevState = event.getEventType();
			prevOperation = event.getOperation();
		}

	}

}
