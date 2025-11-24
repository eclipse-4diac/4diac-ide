/*******************************************************************************
 * Copyright (c) 2025, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.ui.IEditorInput;

/**
 * A library element provider manages library elements for particular editor
 * inputs.
 *
 * @apiNote A library element provider must only be accessed from the UI thread,
 *          except for {@link #getLibraryElement(IEditorInput)}, otherwise an
 *          {@link IllegalStateException} is thrown.
 * @see LibraryElement
 * @see AbstractLibraryElementProvider
 */
public interface LibraryElementProvider {

	LibraryElementProvider INSTANCE = new FileLibraryElementProvider();

	/**
	 * Connect the input to this library element provider. This tells the provider
	 * that the caller of this method is interested to work with the library
	 * element.
	 *
	 * @param input The input
	 * @exception CoreException if the library element for the input cannot be
	 *                          provided, such as if the underlying file cannot be
	 *                          loaded
	 */
	void connect(IEditorInput input) throws CoreException;

	/**
	 * Disconnect the input from this library element provider. This tells the
	 * provider that the caller of this method is no longer interested in working
	 * with the library element.
	 *
	 * @param input The input
	 */
	void disconnect(IEditorInput input);

	/**
	 * Get the library element for the given input.
	 *
	 * @param input The input
	 * @return The library element or {@code null} if none is currently connected
	 */
	LibraryElement getLibraryElement(IEditorInput input);

	/**
	 * Reset the library element for the given input to its last saved state.
	 *
	 * @param input   The input
	 * @param monitor A progress monitor
	 * @exception CoreException if the library element for the input cannot be
	 *                          reset, such as if the underlying file cannot be
	 *                          loaded
	 */
	void resetLibraryElement(IEditorInput input, final IProgressMonitor monitor) throws CoreException;

	/**
	 * Save the library element for the given input.
	 *
	 * @param input   The input
	 * @param monitor A progress monitor
	 * @exception CoreException if the library element for the input cannot be
	 *                          saved, such as if the underlying file cannot be
	 *                          written
	 */
	void saveLibraryElement(IEditorInput input, IProgressMonitor monitor) throws CoreException;

	/**
	 * Synchronize the library element provided for the given input with the
	 * underlying storage.
	 *
	 * @apiNote Afterwards, {@link #getModificationStamp(IEditorInput)} and
	 *          {@link #getSynchronizationStamp(IEditorInput)} will return the same
	 *          value.
	 *
	 * @param input   The input
	 * @param monitor A progress monitor
	 * @exception CoreException if the synchronization could not be performed
	 */
	void synchronize(IEditorInput input, final IProgressMonitor monitor) throws CoreException;

	/**
	 * Check whether the information provided for the given input is in sync with
	 * the underlying storage.
	 *
	 * @param input The input
	 * @return {@code true} if the information is in sync with the element,
	 *         {@code false} otherwise
	 */
	boolean isSynchronized(IEditorInput input);

	/**
	 * Get the modification stamp of the given input.
	 *
	 * @param input The input
	 * @return The modification stamp of the given input
	 */
	long getModificationStamp(IEditorInput input);

	/**
	 * Get the time stamp of the last synchronization between the library element
	 * and the underlying storage for the given input.
	 *
	 * @param input The input
	 * @return The synchronization stamp of the given input
	 */
	long getSynchronizationStamp(IEditorInput input);

	/**
	 * Get whether the library element for the given input is read-only.
	 *
	 * @param input The input
	 * @return {@code true} if the library element is read-only, {@code false}
	 *         otherwise
	 */
	boolean isReadOnly(IEditorInput input);

	/**
	 * Get whether the library element for the given input has been deleted.
	 *
	 * @param input The input
	 * @return {@code true} if the library element has been deleted, {@code false}
	 *         otherwise
	 */
	boolean isDeleted(IEditorInput input);

	/**
	 * Get whether the library element provided for the given input must be saved,
	 * that is, if there is only one interested caller left connected.
	 *
	 * @param input The input
	 * @return {@code true} if the library element must be saved, {@code false}
	 *         otherwise
	 */
	boolean mustSaveLibraryElement(IEditorInput input);

	/**
	 * Get whether the library element provided for the given input can be saved,
	 * that is, if there are unsaved modifications.
	 *
	 * @param input The input
	 * @return {@code true} if the library element can be saved, {@code false}
	 *         otherwise
	 */
	boolean canSaveLibraryElement(IEditorInput input);

	/**
	 * Get the annotation model for the given input.
	 *
	 * @param input The input
	 * @return The annotation model or {@code null} if none is currently connected
	 */
	GraphicalAnnotationModel getAnnotationModel(IEditorInput input);
}