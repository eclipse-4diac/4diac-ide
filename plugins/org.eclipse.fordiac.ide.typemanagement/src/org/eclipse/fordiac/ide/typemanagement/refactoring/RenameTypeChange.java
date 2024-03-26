/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceChange;

public class RenameTypeChange extends RenameResourceChange {

	private final TypeEntry typeEntry;

	public RenameTypeChange(final TypeEntry typeEntry, final String newName) {
		super(typeEntry.getFile().getFullPath(), newName);
		this.typeEntry = typeEntry;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final Change perform = super.perform(pm);
		final IPath newPath = renamedResourcePath(typeEntry.getFile().getFullPath(), getNewName());
		final IResource findMember = ResourcesPlugin.getWorkspace().getRoot().findMember(newPath);
		if (findMember instanceof final IFile file) {
			FordiacResourceChangeListener.updateTypeEntryByRename(file, typeEntry);
		}
		return perform;
	}

	private static IPath renamedResourcePath(final IPath path, final String newName) {
		return path.removeLastSegments(1).append(newName);
	}

}
