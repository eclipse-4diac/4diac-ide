/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.FileEditorInputFactory;

/**
 * An EditorInput for opening any type file with specified content. The equals
 * method is adapted that EditorInput is equal to another EditorInput if the
 * content is equal.
 */
public class TypeEditorInput implements IFileEditorInput, IPersistableElement {
	private LibraryElement type;
	private final TypeEntry entry;

	public TypeEditorInput(final LibraryElement type, final TypeEntry entry) {
		this.type = type;
		this.entry = entry;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return type.getName() == null ? "" : type.getName(); //$NON-NLS-1$
	}

	@Override
	public IPersistableElement getPersistable() {
		return this;
	}

	@Override
	public String getToolTipText() {
		return type.getComment() == null ? "" //$NON-NLS-1$
				: type.getComment() + " (" + entry.getFile().getProjectRelativePath().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}

	public LibraryElement getContent() {
		return type;
	}

	public TypeEntry getTypeEntry() {
		return entry;
	}

	@Override
	public boolean equals(final Object obj) {
		// must yield the same result as FileEditorInput#equals(Object)
		// to be compatible with different editing contexts (e.g., refactoring)
		if (this == obj) {
			return true;
		}
		if (obj instanceof final IFileEditorInput other) {
			return getFile().equals(other.getFile());
		}
		return false;
	}

	@Override
	public int hashCode() {
		// must yield the same result as FileEditorInput#hashCode()
		// to be compatible with different editing contexts (e.g., refactoring)
		return getFile().hashCode();
	}

	@Override
	public void saveState(final IMemento memento) {
		FileEditorInputFactory.saveState(memento, new FileEditorInput(getFile()));
	}

	public void setType(final LibraryElement fbType) {
		this.type = fbType;
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return getFile();
	}

	@Override
	public String getFactoryId() {
		return FileEditorInputFactory.getFactoryId();
	}

	@Override
	public IFile getFile() {
		return entry.getFile();
	}

}
