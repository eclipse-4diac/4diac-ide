/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.Saveable;

/**
 * A library element changed in the search results, saved separately in the save
 * dialogs of the workbench.
 */
final class LibraryElementSaveable extends Saveable {

	private final IEditorInput input;

	LibraryElementSaveable(final IEditorInput input) {
		this.input = input;
	}

	@Override
	public String getName() {
		return input.getName();
	}

	@Override
	public String getToolTipText() {
		return input.getToolTipText();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return input.getImageDescriptor();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) throws CoreException {
		LibraryElementProvider.INSTANCE.saveLibraryElement(input, monitor);
	}

	@Override
	public boolean isDirty() {
		return LibraryElementProvider.INSTANCE.canSaveLibraryElement(input);
	}

	@Override
	public boolean equals(final Object object) {
		return object instanceof final LibraryElementSaveable other && input.equals(other.input);
	}

	@Override
	public int hashCode() {
		return input.hashCode();
	}
}
