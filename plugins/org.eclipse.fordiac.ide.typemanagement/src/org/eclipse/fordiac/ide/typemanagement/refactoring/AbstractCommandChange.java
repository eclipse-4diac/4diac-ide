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
import org.eclipse.core.runtime.Status;
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

	private IEditorPart editor;

	protected AbstractCommandChange(final URI elementURI, final Class<T> elementClass) {
		this(elementURI.lastSegment(), elementURI, elementClass);
	}

	protected AbstractCommandChange(final String name, final URI elementURI, final Class<T> elementClass) {
		this.name = Objects.requireNonNull(name);
		this.elementURI = Objects.requireNonNull(elementURI);
		this.elementClass = Objects.requireNonNull(elementClass);
	}

	@Override
	public final void initializeValidationData(final IProgressMonitor pm) {
		initializeEditor();
		final LibraryElement libraryElement = acquireLibraryElement(false);
		final T element = getElement(libraryElement);
		if (element != null) {
			initializeValidationData(element, pm);
		}
	}

	public abstract void initializeValidationData(T element, final IProgressMonitor pm);

	@Override
	public final RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final LibraryElement libraryElement = acquireLibraryElement(false);
		final T element = getElement(libraryElement);
		if (element == null) {
			status.addFatalError(Messages.AbstractCommandChange_NoSuchElement);
		} else {
			status.merge(isValid(element, pm));
		}
		return status;
	}

	public abstract RefactoringStatus isValid(T element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException;

	@Override
	public final Change perform(final IProgressMonitor pm) throws CoreException {
		if (performInUIThread() && Display.getCurrent() == null) {
			final Change[] change = new Change[1];
			final CoreException[] coreException = new CoreException[1];
			Display.getDefault().syncExec(() -> {
				try {
					change[0] = doPerform(pm);
				} catch (final CoreException e) {
					coreException[0] = e;
				}
			});
			if (coreException[0] != null) {
				throw coreException[0];
			}
			return change[0];
		}
		return doPerform(pm);
	}

	private Change doPerform(final IProgressMonitor pm) throws CoreException {
		final LibraryElement libraryElement = acquireLibraryElement(true);
		final T element = getElement(libraryElement);
		if (element == null) {
			throw new CoreException(Status.error(Messages.AbstractCommandChange_NoSuchElement));
		}
		final Command command = performCommand(element);
		commit(libraryElement, pm);
		return createUndoChange(command, libraryElement);
	}

	protected Command performCommand(final T element) throws CoreException {
		final Command command = Objects.requireNonNull(createCommand(element));
		if (!command.canExecute()) {
			throw new CoreException(Status.error(Messages.AbstractCommandChange_CannotExecuteCommand));
		}
		final CommandStack commandStack = getCommandStack();
		if (commandStack != null) {
			commandStack.execute(command);
		} else {
			command.execute();
		}
		return command;
	}

	protected abstract Command createCommand(T element);

	protected LibraryElement acquireLibraryElement(final boolean editable) {
		if (editor != null) {
			return Adapters.adapt(editor, LibraryElement.class);
		}
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (typeEntry != null) {
			return editable ? typeEntry.copyType() : typeEntry.getType();
		}
		return null;
	}

	protected void commit(final LibraryElement libraryElement, final IProgressMonitor pm) throws CoreException {
		libraryElement.getTypeEntry().save(libraryElement, pm);
		if (editor != null) {
			// if we have an editor mark the save location in the command stack to tell the
			// editor that it is not dirty anymore
			Display.getDefault().syncExec(() -> editor.getAdapter(CommandStack.class).markSaveLocation());
		}
	}

	protected Change createUndoChange(final Command command, final LibraryElement libraryElement) {
		return new CommandUndoChange<>(name, elementURI, elementClass, command, libraryElement);
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

	public IEditorPart getEditor() {
		return editor;
	}

	private T getElement(final LibraryElement libraryElement) {
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

	protected void initializeEditor() {
		final IEditorInput editorInput = new FileEditorInput(getModifiedElement());
		// need sync exec because IWorkbenchPage.findEditor(IEditorInput) may
		// instantiate the editor if it is not loaded (e.g., open editor after a
		// restart), which requires to be in the UI thread
		Display.getDefault()
				.syncExec(() -> editor = Stream.of(PlatformUI.getWorkbench().getWorkbenchWindows())
						.flatMap(window -> Stream.of(window.getPages())).map(page -> page.findEditor(editorInput))
						.filter(Objects::nonNull).findAny().orElse(null));
	}
}
