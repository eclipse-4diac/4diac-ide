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
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Added moving of node with same parent
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;

public class MoveNodeOperation extends AbstractChangeHierarchyOperation {

	private final Node node;
	private EObject parent;
	private final EObject newParent;
	private int index;
	private int targetIndex;
	private boolean sameParent;

	public MoveNodeOperation(final EObject newParent, final Node node, final int targetIndex) {
		super("Move Node");
		this.node = node;
		this.newParent = newParent;
		this.targetIndex = targetIndex;
	}

	@Override
	public IStatus execute(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		parent = node.eContainer();
		index = getParentIndex();
		sameParent = parent == newParent;

		if (sameParent && index < targetIndex) {
			targetIndex--;
		}
		return performMove(monitor);
	}

	@Override
	public IStatus redo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		return performMove(monitor);
	}

	private IStatus performMove(final IProgressMonitor monitor) {
		if (sameParent) {
			move(targetIndex, index);
		} else {
			moveToContainer(newParent, node, targetIndex);
		}
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
		if (sameParent) {
			move(index, targetIndex);
		} else {
			moveToContainer(parent, node, index);
		}
		saveHierarchy(parent, monitor);
		return Status.OK_STATUS;
	}

	private void move(final int target, final int current) {
		final EList<? extends Node> parentContainer = getParentContainer();
		parentContainer.move(target, current);
	}

	private static void moveToContainer(final EObject container, final Node targetNode, final int targetIdx) {
		if (container instanceof final Level level) {
			if (targetIdx == -1) {
				level.getChildren().add(targetNode);
			} else {
				level.getChildren().add(targetIdx, targetNode);
			}
		} else if (container instanceof final RootLevel rootLevel && targetNode instanceof final Level targetLevel) {
			if (targetIdx == -1) {
				rootLevel.getLevels().add(targetLevel);
			} else {
				rootLevel.getLevels().add(targetIdx, targetLevel);
			}
		}
	}

	private int getParentIndex() {
		final EList<? extends Node> parentContainer = getParentContainer();
		return parentContainer.indexOf(node);
	}

	private EList<? extends Node> getParentContainer() {
		if (parent instanceof final RootLevel root) {
			return root.getLevels();
		}
		if (parent instanceof final Level level) {
			return level.getChildren();
		}
		return ECollections.emptyEList();
	}
}
