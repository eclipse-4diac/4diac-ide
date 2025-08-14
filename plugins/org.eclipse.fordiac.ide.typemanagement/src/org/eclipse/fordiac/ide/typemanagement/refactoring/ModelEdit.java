/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public abstract class ModelEdit<T extends EObject> {
	private final String name;
	private final URI elementURI;
	private final Class<T> elementClass;

	/**
	 * Create a model edit
	 *
	 * @param name         The edit name
	 * @param elementURI   The element URI
	 * @param elementClass The element class
	 */
	protected ModelEdit(final String name, final URI elementURI, final Class<T> elementClass) {
		this.name = Objects.requireNonNull(name);
		this.elementURI = Objects.requireNonNull(elementURI);
		this.elementClass = Objects.requireNonNull(elementClass);
	}

	/**
	 * Initialize the validation data based on the library element
	 *
	 * @param libraryElement The library element
	 * @param pm             The progress monitor
	 */
	public final void initializeValidationData(final LibraryElement libraryElement, final IProgressMonitor pm) {
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
	 * Check if valid based on the library element
	 *
	 * @param libraryElement The library element
	 * @param pm             The progress monitor
	 */
	public final RefactoringStatus isValid(final LibraryElement libraryElement, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
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
	 * Create the command based on the library element
	 *
	 * @param libraryElement The library element
	 * @return The command (will not be null)
	 */
	public final Command createCommand(final LibraryElement libraryElement) throws CoreException {
		final T element = getElement(libraryElement);
		if (element == null) {
			throw new CoreException(
					Status.error(MessageFormat.format(Messages.AbstractCommandChange_NoSuchElement, elementURI)));
		}
		return Objects.requireNonNull(createCommand(element));
	}

	/**
	 * Create the command for the element
	 *
	 * @param element The element
	 * @return The command (must not be null)
	 */
	protected abstract Command createCommand(T element);

	/**
	 * Get the name
	 *
	 * @return The name (will never be null)
	 */
	public String getName() {
		return name;
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
	 * Get the library element URI
	 *
	 * @return The library element URI (will never be null)
	 */
	public final URI getLibraryElementURI() {
		return elementURI.trimFragment();
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
}
