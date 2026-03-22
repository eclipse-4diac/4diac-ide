/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;

/**
 * An abstract change encapsulating a {@link Command} to be executed on a
 * particular element directly in a file or via the {@link CommandStack} of an
 * open editor.
 *
 * @implSpec Do NOT keep any direct references to any elements, since they may
 *           become out of date. Always use the element parameter passed to the
 *           respective methods.
 */
public abstract class AbstractCommandChange<T extends EObject> extends CompositeChange {

	private final URI elementURI;
	private final Class<T> elementClass;
	private final TypeEntry typeEntry;

	private boolean isOpenInEditor;

	/**
	 * Create an abstract command change with the last segment of the element URI as
	 * the name.
	 *
	 * @param elementURI   The element URI
	 * @param elementClass The element class
	 */
	protected AbstractCommandChange(final URI elementURI, final Class<T> elementClass) {
		this(elementURI.lastSegment(), elementURI, elementClass);
	}

	/**
	 * Create an abstract command change
	 *
	 * @param name         The name of the change
	 * @param elementURI   The element URI
	 * @param elementClass The element class
	 */
	protected AbstractCommandChange(final String name, final URI elementURI, final Class<T> elementClass) {
		super(name);
		this.elementURI = Objects.requireNonNull(elementURI);
		this.elementClass = Objects.requireNonNull(elementClass);
		this.typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
	}

	/**
	 * @implNote Also initializes the editor reference.
	 */
	@Override
	public final void initializeValidationData(final IProgressMonitor pm) {
		initializeEditor();
		final LibraryElement libraryElement = acquireLibraryElement(false);
		final T element = getElement(libraryElement);
		if (element != null) {
			initializeValidationData(element, pm);
		}
	}

	/**
	 * Initialize the validation data based on the element
	 *
	 * @param element The element
	 * @param pm      The progress monitor
	 */
	public abstract void initializeValidationData(T element, final IProgressMonitor pm);

	/**
	 * @implNote Also checks if the element can be retrieved.
	 */
	@Override
	public final RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		final LibraryElement libraryElement = acquireLibraryElement(false);
		final T element = getElement(libraryElement);
		if (element == null) {
			status.addFatalError(MessageFormat.format(Messages.AbstractCommandChange_NoSuchElement, elementURI));
		} else {
			status.merge(isValid(element, pm));
		}
		return status;
	}

	/**
	 * Check if valid based on the element
	 *
	 * @param element The element
	 * @param pm      The progress monitor
	 */
	public abstract RefactoringStatus isValid(T element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException;

	/**
	 * @implNote If {@link #performInUIThread()} returns true, perform in the UI
	 *           thread using {@link Display#syncExec(Runnable)}.
	 */
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
			throw new CoreException(
					Status.error(MessageFormat.format(Messages.AbstractCommandChange_NoSuchElement, elementURI)));
		}
		final Command command = performCommand(element);
		commit(libraryElement, pm);
		return createUndoChange(command, libraryElement);
	}

	/**
	 * Perform the command on the element
	 *
	 * @param element The element
	 * @return The command
	 * @throws CoreException if the command could not be executed
	 * @implNote This should only be overridden by {@link CommandUndoChange} and
	 *           {@link CommandRedoChange}.
	 */
	protected Command performCommand(final T element) throws CoreException {
		final Command command = Objects.requireNonNull(createCommand(element));
		if (!command.canExecute()) {
			throw new CoreException(
					Status.error(MessageFormat.format(Messages.AbstractCommandChange_CannotExecuteCommand,
							command.getClass().getName(), getClass().getName(), elementURI)));
		}
		command.execute();
		return command;
	}

	/**
	 * Create the command for the element
	 *
	 * @param element The element
	 * @return The command (must not be null)
	 * @throws CoreException if the command could not be created
	 */
	protected abstract Command createCommand(T element) throws CoreException;

	/**
	 * Acquire the library element for the element URI
	 *
	 * @param editable if the library element must be editable
	 * @return The (editable) library element
	 * @implNote This should only be overridden by {@link CommandUndoChange} and
	 *           {@link CommandRedoChange}.
	 */
	protected LibraryElement acquireLibraryElement(final boolean editable) {
		if (isOpenInEditor) {
			return LibraryElementProvider.INSTANCE.getLibraryElement(getFileEditorInput());
		}
		if (typeEntry != null) {
			return editable ? typeEntry.copyType() : typeEntry.getType();
		}
		return null;
	}

	/**
	 * Commit the changes to the library element
	 *
	 * @param libraryElement The library element
	 * @param pm             The progress monitor
	 * @throws CoreException if there was a problem saving the library element
	 */
	private void commit(final LibraryElement libraryElement, final IProgressMonitor pm) throws CoreException {
		// save type entry
		if (isOpenInEditor) {
			LibraryElementProvider.INSTANCE.saveLibraryElement(getFileEditorInput(), pm);
		} else if (typeEntry != null) {
			typeEntry.save(libraryElement, pm);
		}
	}

	/**
	 * Create an undo change for this change and the command and library element
	 *
	 * @param command        The command
	 * @param libraryElement The library element
	 * @return The undo change
	 * @implNote This should only be overridden by {@link CommandUndoChange} and
	 *           {@link CommandRedoChange}.
	 */
	protected Change createUndoChange(final Command command, final LibraryElement libraryElement) {
		return new CommandUndoChange<>(getName(), elementURI, elementClass, command, libraryElement);
	}

	/**
	 * @throws UnsupportedOperationException adding changes is not supported
	 * @implNote We need to inherit from {@link CompositeChange} to support showing
	 *           child nodes in subclasses, but do not actually support adding any
	 *           changes.
	 */
	@Override
	public final void add(final Change change) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Cannot add any changes to this " + getClass().getName()); //$NON-NLS-1$
	}

	@Override
	public final IFile getModifiedElement() {
		if (typeEntry != null) {
			return typeEntry.getFile();
		}

		if (elementURI.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(elementURI.toPlatformString(true)));
		}
		return null;
	}

	@Override
	public final Object[] getAffectedObjects() {
		final IFile modifiedElement = getModifiedElement();
		if (modifiedElement != null) {
			return new Object[] { modifiedElement };
		}
		return super.getAffectedObjects();
	}

	/**
	 * Get the element URI
	 *
	 * @return The element URI (will never be null)
	 */
	public final URI getElementURI() {
		return elementURI;
	}

	/**
	 * Get the element class
	 *
	 * @return The element class (will never be null)
	 */
	public final Class<T> getElementClass() {
		return elementClass;
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

	/**
	 * Whether to perform the command in the UI thread
	 *
	 * @return true if the command should be performed in the UI thread, false
	 *         otherwise
	 * @implNote This typically returns whether there is an editor open for the
	 *           corresponding file.
	 */
	protected boolean performInUIThread() {
		return isOpenInEditor;
	}

	private void initializeEditor() {
		isOpenInEditor = LibraryElementProvider.INSTANCE.getLibraryElement(getFileEditorInput()) != null;
	}

	private IFileEditorInput getFileEditorInput() {
		return new FileEditorInput(getModifiedElement());
	}
}
