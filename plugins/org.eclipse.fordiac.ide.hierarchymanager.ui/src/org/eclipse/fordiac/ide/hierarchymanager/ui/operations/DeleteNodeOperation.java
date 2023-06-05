/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
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
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;

public class DeleteNodeOperation extends AbstractChangeHierarchyOperation {

	private final Node node;
	private EObject parent;
	private int index;

	public DeleteNodeOperation(final Node node) {
		super("Delete Node");
		this.node = node;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		parent = node.eContainer();
		index = getParentIndex();
		removeFromParent();
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private int getParentIndex() {
		final EList<? extends Node> parentContainer = getParentContainer();
		if (parentContainer != null) {
			return parentContainer.indexOf(node);
		}
		return -1;
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		removeFromParent();
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private void removeFromParent() {
		final EList<? extends Node> parentContainer = getParentContainer();
		if (parentContainer != null && index != -1) {
			parentContainer.remove(index);
		}
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		// for adding we can not use getParentContainer as the root node has levels and not nodes
		if (parent instanceof final RootLevel root) {
			root.getLevels().add(index, (Level) node);
		}
		if (parent instanceof final Level level) {
			level.getChildren().add(index, node);
		}
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private EList<? extends Node> getParentContainer() {
		if (parent instanceof final RootLevel root) {
			return root.getLevels();
		}
		if (parent instanceof final Level level) {
			return level.getChildren();
		}
		return null;
	}

}
