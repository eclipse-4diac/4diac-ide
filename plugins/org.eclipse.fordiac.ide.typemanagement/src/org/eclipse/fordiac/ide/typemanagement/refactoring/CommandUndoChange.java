/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class CommandUndoChange<T extends EObject> extends AbstractCommandChange<T> {

	private final Command command;
	private final LibraryElement libraryElement;

	public CommandUndoChange(final String name, final URI elementURI, final Class<T> elementClass,
			final Command command, final LibraryElement libraryElement) {
		super(name, elementURI, elementClass);
		this.command = Objects.requireNonNull(command);
		this.libraryElement = Objects.requireNonNull(libraryElement);
	}

	@Override
	public void initializeValidationData(final T element, final IProgressMonitor pm) {
		// do nothing
	}

	@Override
	public RefactoringStatus isValid(final T element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final CommandStack commandStack = getCommandStack();
		if ((commandStack != null && commandStack.getUndoCommand() != command) || !command.canUndo()) {
			status.addFatalError(Messages.CommandUndoChange_CannotUndoCommand);
		}
		return status;
	}

	@Override
	protected Command createCommand(final T element) {
		// we need an existing command to undo
		throw new UnsupportedOperationException();
	}

	@Override
	protected Command performCommand(final T element) {
		final CommandStack commandStack = getCommandStack();
		if (commandStack != null) {
			commandStack.undo();
		} else {
			command.undo();
		}
		return command;
	}

	@Override
	protected LibraryElement acquireLibraryElement(final boolean editable) {
		return libraryElement;
	}

	@Override
	protected Change createUndoChange(final Command command, final LibraryElement libraryElement) {
		return new CommandRedoChange<>(getName(), getElementURI(), getElementClass(), command, libraryElement);
	}
}
