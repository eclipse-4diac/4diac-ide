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
 *   Qemal Alliu - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;

public class UpdateLeafContainerFileNameOperation extends AbstractChangeHierarchyOperation {

	private final Leaf leaf;
	private final String oldPath; // path of the file or folder that is renamed
	private final String newPath; // full path of the renamed file or folder

	public UpdateLeafContainerFileNameOperation(final Leaf leaf, final String oldPath, final String newPath) {
		super("Update File Reference on Plant Hierarchy"); //$NON-NLS-1$

		this.leaf = leaf;
		this.oldPath = oldPath;
		this.newPath = newPath;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setContainerFileName(leaf.getContainerFileName().replace(oldPath, newPath));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setContainerFileName(leaf.getContainerFileName().replace(oldPath, newPath));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setContainerFileName(leaf.getContainerFileName().replace(newPath, oldPath));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

}
