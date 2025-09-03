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
package org.eclipse.fordiac.ide.model.helpers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class ModelHelper {

	/**
	 * Get the {@link LibraryElement} from the given context {@link EObject}. This
	 * is done by getting the root object in the EMF containment hierarchy.
	 *
	 * @param context object for which to get the root library element
	 * @return {@link LibraryElement} the context object is contained in
	 * @throws IllegalStateException if the context object's root is not a
	 *                               {@link LibraryElement}
	 */
	public static LibraryElement getLibraryElementFromContextChecked(final EObject context) {
		if (EcoreUtil.getRootContainer(context) instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		throw new IllegalStateException("Could not determine root LibraryElement for given element: " + context); //$NON-NLS-1$
	}

	/**
	 * Get the {@link LibraryElement} from the given context {@link EObject}. This
	 * is done by getting the root object in the EMF containment hierarchy.
	 *
	 * @param context object for which to get the root library element, or
	 *                {@code null} if the root object is not a
	 *                {@link LibraryElement}.
	 * @return {@link LibraryElement} the context object is contained in
	 */
	public static LibraryElement getLibraryElementFromContext(final EObject context) {
		if (EcoreUtil.getRootContainer(context) instanceof final LibraryElement libraryElement) {
			return libraryElement;
		}
		return null;
	}

	/**
	 * Get the {@code IFile} the given context object is coming from. This is
	 * achieved by getting the root {@code LibraryElement} and its
	 * {@code TypeEntry}.
	 *
	 * @param context object for which to get the {@link IFile}
	 * @return {@link IFile} the context object is contained in
	 * @throws IllegalStateException if the {@link IFile} can not be determined from
	 *                               context object
	 */
	public static IFile getFileFromContextChecked(final EObject context) {
		final TypeEntry typeEntry = getLibraryElementFromContextChecked(context).getTypeEntry();
		if (typeEntry != null) {
			return typeEntry.getFile();
		}
		throw new IllegalStateException("No type entry set for root element of: " + context); //$NON-NLS-1$
	}

	/**
	 * Get the {@code IFile} the given context object is coming from. This is
	 * achieved by getting the root {@code LibraryElement} and its
	 * {@code TypeEntry}.
	 *
	 * @param context object for which to get the {@link IFile}
	 * @return {@link IFile} the context object is contained in, or {@code null} if
	 *         the {@link IFile} could not be determined
	 */
	public static IFile getFileFromContext(final EObject context) {
		final LibraryElement libraryElement = getLibraryElementFromContext(context);
		if (libraryElement != null) {
			final TypeEntry typeEntry = libraryElement.getTypeEntry();
			if (typeEntry != null) {
				return libraryElement.getTypeEntry().getFile();
			}
		}
		return null;
	}

	/**
	 * Get the {@code IProject} the given context object is coming from. This is
	 * achieved by getting the root {@code LibraryElement} and its
	 * {@code TypeEntry}.
	 *
	 * @param context object for which to get the {@link IProject}
	 * @return {@link IProject} the context object is contained in
	 * @throws IllegalStateException if the {@link IProject} can not be determined
	 *                               from context object
	 */
	public static IProject getProjectFromContextChecked(final EObject context) {
		final TypeLibrary typeLibrary = getLibraryElementFromContextChecked(context).getTypeLibrary();
		if (typeLibrary != null) {
			return typeLibrary.getProject();
		}
		throw new IllegalStateException("Could not determine type library for root element of: " + context); //$NON-NLS-1$
	}

	/**
	 * Get the {@code IProject} the given context object is coming from. This is
	 * achieved by getting the root {@code LibraryElement} and its
	 * {@code TypeEntry}.
	 *
	 * @param context object for which to get the {@link IProject}
	 * @return {@link IProject} the context object is contained in, or {@code null}
	 *         if the {@link IProject} could not be determined
	 */
	public static IProject getProjectFromContext(final EObject context) {
		final LibraryElement libraryElement = getLibraryElementFromContext(context);
		if (libraryElement != null) {
			final TypeLibrary typeLibrary = libraryElement.getTypeLibrary();
			if (typeLibrary != null) {
				return typeLibrary.getProject();
			}
		}
		return null;
	}

	private ModelHelper() {
		throw new UnsupportedOperationException();
	}

}
