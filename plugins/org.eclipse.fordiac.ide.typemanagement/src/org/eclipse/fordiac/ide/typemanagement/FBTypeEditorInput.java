/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * An EditorInput for opening FBType files with specified content. The equals
 * method is adapted that EditorInput is equal to another EditorInput if the
 * content is equal.
 */
public class FBTypeEditorInput implements IFileEditorInput {
	private FBType fbType;
	private final TypeEntry entry;

	public FBTypeEditorInput(final FBType fbType, final TypeEntry entry) {
		this.fbType = fbType;
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
		return fbType.getName() == null ? "" : fbType.getName(); //$NON-NLS-1$
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return fbType.getComment() == null ? "" //$NON-NLS-1$
				: fbType.getComment() + " (" + entry.getFile().getProjectRelativePath().toString() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return null;
	}

	public FBType getContent() {
		return fbType;
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

	public void setFbType(final FBType fbType) {
		this.fbType = fbType;
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return getFile();
	}

	@Override
	public IFile getFile() {
		return entry.getFile();
	}
}
