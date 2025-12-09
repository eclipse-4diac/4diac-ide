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
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;

public class AddLeafOperation extends AbstractChangeHierarchyOperation {

	private final Level level;
	private final Leaf leaf;

	public AddLeafOperation(final Level level, final Leaf leaf) {
		super("Add a leaf to a level");
		this.level = level;
		this.leaf = leaf;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.getChildren().add(leaf);
		saveHierarchy(level, monitor);

		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.getChildren().add(leaf);
		saveHierarchy(level, monitor);

		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		level.getChildren().remove(leaf);
		saveHierarchy(level, monitor);

		return Status.OK_STATUS;
	}
}
