/*******************************************************************************
 * Copyright (c) 2024 Primetals Technology Austria GmbH
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
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;

public class UpdateLeafRefOperation extends AbstractChangeHierarchyOperation {

	private final Leaf leaf;
	private final String newRef;
	private final String oldRef;

	public UpdateLeafRefOperation(final Leaf level, final String newRef, final String oldRef) {
		super("Update Reference");
		this.leaf = level;
		this.newRef = newRef;
		this.oldRef = oldRef;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setRef(leaf.getRef().replace(oldRef, newRef));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setRef(leaf.getRef().replace(oldRef, newRef));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		leaf.setRef(leaf.getRef().replace(newRef, oldRef));
		saveHierarchy(leaf, monitor);
		return Status.OK_STATUS;
	}

}
