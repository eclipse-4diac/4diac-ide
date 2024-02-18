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
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public abstract class AbstractCommandChange<T extends EObject> extends Change {

	private final String name;
	private final URI elementURI;
	private final Class<T> elementClass;

	private Command command;
	private IEditorPart editor;
	private LibraryElement libraryElement;

	protected AbstractCommandChange(final URI elementURI, final Class<T> elementClass) {
		this(elementURI.lastSegment(), elementURI, elementClass);
	}

	protected AbstractCommandChange(final String name, final URI elementURI, final Class<T> elementClass) {
		this.name = Objects.requireNonNull(name);
		this.elementURI = Objects.requireNonNull(elementURI);
		this.elementClass = Objects.requireNonNull(elementClass);
	}

	protected AbstractCommandChange(final String name, final URI elementURI, final Class<T> elementClass,
			final Command command, final IEditorPart editor, final LibraryElement libraryElement) {
		this(name, elementURI, elementClass);
		this.command = command;
		this.editor = editor;
		this.libraryElement = libraryElement;
	}

	protected abstract Command createCommand(T element);

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// get library element
		if (libraryElement == null) {
			editor = findEditor(new FileEditorInput(getModifiedElement()));
			if (editor != null) { // from the editor (and save via editor later)
				libraryElement = Adapters.adapt(editor, LibraryElement.class);
			} else { // or a copy from the type entry
				final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
				if (typeEntry != null) {
					libraryElement = typeEntry.copyType();
				}
			}
		}
		// create command from library element
		if (command == null) {
			final T element = getElement();
			if (element != null) {
				command = Objects.requireNonNull(createCommand(element));
			}
		}
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (libraryElement == null) {
			status.addFatalError(Messages.AbstractCommandChange_NoSuchElement);
		} else if (command == null || !command.canExecute()) {
			status.addFatalError(Messages.AbstractCommandChange_CannotExecuteCommand);
		}
		return status;
	}

	@Override
	public final Change perform(final IProgressMonitor pm) throws CoreException {
		if (performInUIThread() && Display.getCurrent() == null) {
			final CoreException[] coreException = new CoreException[1];
			Display.getDefault().syncExec(() -> {
				try {
					performCommand();
					commit(pm);
				} catch (final CoreException e) {
					coreException[0] = e;
				}
			});
			if (coreException[0] != null) {
				throw coreException[0];
			}
		} else {
			performCommand();
			commit(pm);
		}
		return createUndoChange();
	}

	protected void performCommand() {
		final CommandStack commandStack = getCommandStack();
		if (commandStack != null) {
			commandStack.execute(command);
		} else {
			command.execute();
		}
	}

	protected void commit(final IProgressMonitor pm) throws CoreException {
		if (editor != null) {
			editor.doSave(pm);
		} else {
			libraryElement.getTypeEntry().save(libraryElement, pm);
		}
	}

	protected Change createUndoChange() {
		return new CommandUndoChange<>(name, elementURI, elementClass, command, editor, libraryElement);
	}

	@Override
	public IFile getModifiedElement() {
		if (elementURI.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(elementURI.toPlatformString(true)));
		}
		return null;
	}

	@Override
	public Object[] getAffectedObjects() {
		final IFile modifiedElement = getModifiedElement();
		if (modifiedElement != null) {
			return new Object[] { modifiedElement };
		}
		return super.getAffectedObjects();
	}

	@Override
	public String getName() {
		return name;
	}

	public URI getElementURI() {
		return elementURI;
	}

	public Class<T> getElementClass() {
		return elementClass;
	}

	public Command getCommand() {
		return command;
	}

	public IEditorPart getEditor() {
		return editor;
	}

	public LibraryElement getLibraryElement() {
		return libraryElement;
	}

	protected T getElement() {
		if (libraryElement != null && libraryElement.eResource() != null) {
			final EObject element;
			if (elementURI.hasFragment()) {
				element = libraryElement.eResource().getEObject(elementURI.fragment());
			} else {
				element = libraryElement;
			}
			if (elementClass.isInstance(element)) {
				return elementClass.cast(element);
			}
		}
		return null;
	}

	protected CommandStack getCommandStack() {
		return Adapters.adapt(editor, CommandStack.class);
	}

	protected boolean performInUIThread() {
		return editor != null;
	}

	protected static IEditorPart findEditor(final IEditorInput editorInput) {
		return Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows())
				.flatMap(window -> Stream.of(window.getPages())).map(page -> page.findEditor(editorInput))
				.filter(Objects::nonNull).findAny().orElse(null);
	}
}
