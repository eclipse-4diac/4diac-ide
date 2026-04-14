/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 * 					  Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Erich Jobst - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Extracted into own class
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class BulkEditorHelper {

	private BulkEditorHelper() {
		// utility class
	}

	public static <T extends EObject> List<T> findEditableResults(final List<? extends T> list) {
		return list.stream().<T>map(BulkEditorHelper::findEditableResult).filter(Objects::nonNull).toList();
	}

	@SuppressWarnings("unchecked")
	private static <T extends EObject> T findEditableResult(final T original) {
		if (EcoreUtil.getRootContainer(original) instanceof final LibraryElement originalLibraryElement) {
			final IEditorInput editorInput = getEditorInput(originalLibraryElement);
			final String relativeFragment = EcoreUtil.getRelativeURIFragmentPath(originalLibraryElement, original);
			final LibraryElement editorLibraryElement = LibraryElementProvider.INSTANCE.getLibraryElement(editorInput);
			if (relativeFragment.isEmpty()) {
				return (T) editorLibraryElement;
			}
			return (T) EcoreUtil.getEObject(editorLibraryElement, relativeFragment);
		}
		return null;
	}

	public static IEditorInput getEditorInput(final EObject object) {
		if (EcoreUtil.getRootContainer(object) instanceof final LibraryElement originalLibraryElement) {
			return getEditorInput(originalLibraryElement);
		}
		return null;
	}

	private static IEditorInput getEditorInput(final LibraryElement libraryElement) {
		final TypeEntry typeEntry = libraryElement.getTypeEntry();
		if (typeEntry != null) {
			final IFile file = typeEntry.getFile();
			if (file != null) {
				return new FileEditorInput(file);
			}
		}
		return null;
	}
}
