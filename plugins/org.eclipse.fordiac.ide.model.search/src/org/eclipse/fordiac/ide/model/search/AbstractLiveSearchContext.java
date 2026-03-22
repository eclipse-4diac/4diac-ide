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
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.ui.part.FileEditorInput;

public abstract class AbstractLiveSearchContext implements ISearchContext {

	private final TypeLibrary typelib;

	protected AbstractLiveSearchContext(final TypeLibrary typelib) {
		this.typelib = typelib;
	}

	protected AbstractLiveSearchContext(final IProject project) {
		this(Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project)));
	}

	@Override
	public EObject mapTypes(final URI uri) {
		final TypeEntry typeEntry = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri));
		return getLiveType(typeEntry);
	}

	protected TypeLibrary getTypelib() {
		return typelib;
	}

	/**
	 * this method searches the type, it checks whether it is currently openeed in
	 * an editor or if it is inside the file system If an editor is openened this
	 * method returns the type which is bounded to the editor
	 *
	 * @return
	 */
	private static LibraryElement getLiveType(final TypeEntry typeEntry) {
		final LibraryElement libElement = LibraryElementProvider.INSTANCE
				.getLibraryElement(new FileEditorInput(typeEntry.getFile()));

		return (libElement != null) ? libElement : typeEntry.getType();
	}

}