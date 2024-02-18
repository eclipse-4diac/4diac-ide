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
import org.eclipse.ui.IEditorPart;

public class CommandUndoChange<T extends EObject> extends AbstractCommandChange<T> {

	public CommandUndoChange(final String name, final URI elementURI, final Class<T> elementClass,
			final Command command, final IEditorPart editor, final LibraryElement libraryElement) {
		super(name, elementURI, elementClass, Objects.requireNonNull(command), editor, libraryElement);
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final Command command = getCommand();
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
	protected void performCommand() {
		final Command command = getCommand();
		final CommandStack commandStack = getCommandStack();
		if (commandStack != null) {
			commandStack.undo();
		} else {
			command.undo();
		}
	}

	@Override
	protected Change createUndoChange() {
		return new CommandRedoChange<>(getName(), getElementURI(), getElementClass(), getCommand(), getEditor(),
				getLibraryElement());
	}
}
