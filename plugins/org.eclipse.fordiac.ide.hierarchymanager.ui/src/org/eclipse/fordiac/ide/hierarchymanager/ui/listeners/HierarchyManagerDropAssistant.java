/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Added Reordering for Nodes
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.CreateLeafOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.MoveNodeOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.view.PlantHierarchyView;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.navigator.CommonViewer;

public class HierarchyManagerDropAssistant extends CommonDropAdapterAssistant {

	@Override
	public IStatus validateDrop(final Object target, final int operation, final TransferData transferType) {
		if (operation != DND.DROP_MOVE) {
			return Status.CANCEL_STATUS;
		}

		if (!super.isSupportedType(transferType)) {
			return Status.CANCEL_STATUS;
		}

		// prevent duplicates
		if ((getCurrentEvent().data instanceof final TreeSelection selection)
				&& (selection.getFirstElement() instanceof final SubApp subapp) && target instanceof final EObject refObj) {
			final RootLevel root = (RootLevel) EcoreUtil.getRootContainer(refObj);
			if (root != null && root.getLevels().stream().anyMatch(l -> hasChild(l, subapp.getQualifiedName()))) {
				return Status.CANCEL_STATUS;
			}
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus handleDrop(final CommonDropAdapter aDropAdapter, final DropTargetEvent aDropTargetEvent,
			final Object aTarget) {
		if (!(aTarget instanceof final Node dropNode)
				|| !(aDropTargetEvent.data instanceof final TreeSelection treeSelection)) {
			return Status.CANCEL_STATUS;
		}

		if (treeSelection.getFirstElement() instanceof final SubApp subapp
				&& getTargetProject() != getSourceProject(subapp)) {
			return Status.CANCEL_STATUS;
		}

		if (!isSupportedType(aDropAdapter.getCurrentTransfer())) {
			return Status.CANCEL_STATUS;
		}

		if (treeSelection.getFirstElement() instanceof final SubApp subapp) {
			final Level parent = dropNode instanceof Leaf ? (Level) dropNode.eContainer() : (Level) dropNode;
			if (parent == null) {
				return Status.CANCEL_STATUS;
			}
			final CreateLeafOperation operation = new CreateLeafOperation(parent, subapp);
			AbstractHierarchyHandler.executeOperation(operation);
		} else if (treeSelection.getFirstElement() instanceof final Node node) {
			return moveNode(aDropAdapter, dropNode, node);
		}
		return Status.OK_STATUS;
	}

	private static IStatus moveNode(final CommonDropAdapter aDropAdapter, final Node dropNode, final Node node) {
		if (node instanceof final Level level && checkIfChild(level, dropNode)) {
			return Status.CANCEL_STATUS;
		}

		EObject parent;
		if (dropNode instanceof Level && aDropAdapter.getCurrentLocation() != ViewerDropAdapter.LOCATION_ON) {
			parent = dropNode.eContainer();
		} else {
			parent = dropNode instanceof Leaf ? (Level) dropNode.eContainer() : (Level) dropNode;
		}

		final int targetIndex = getTargetIndex(aDropAdapter.getCurrentLocation(), dropNode);
		final MoveNodeOperation operation = new MoveNodeOperation(parent, node, targetIndex);
		AbstractHierarchyHandler.executeOperation(operation);
		return Status.OK_STATUS;
	}

	@Override
	public boolean isSupportedType(final TransferData aTransferType) {
		return (getCurrentEvent().data instanceof final TreeSelection selection
				&& (selection.getFirstElement() instanceof Node || selection.getFirstElement() instanceof SubApp))
				&& super.isSupportedType(aTransferType);
	}

	@Override
	public void setCommonDropAdapter(final CommonDropAdapter dropAdapter) {
		super.setCommonDropAdapter(dropAdapter);
		dropAdapter.setFeedbackEnabled(true);
	}

	private static int getTargetIndex(final int dropLocation, final Node dropTarget) {
		if (dropTarget instanceof final Level level && dropLocation == ViewerDropAdapter.LOCATION_ON) {
			return level.getChildren().size();
		}
		final int locationModifier = dropLocation == ViewerDropAdapter.LOCATION_BEFORE ? 0 : 1;
		if (dropTarget.eContainer() instanceof final RootLevel root) {
			return root.getLevels().indexOf(dropTarget) + locationModifier;
		}

		final int nodeIndex = ((Level) dropTarget.eContainer()).getChildren().indexOf(dropTarget);

		return nodeIndex + locationModifier;
	}

	private IProject getTargetProject() {
		return ((PlantHierarchyView) ((CommonViewer) ((NavigatorContentService) getContentService()).getViewer())
				.getCommonNavigator()).getCurrentProject();
	}

	private static IProject getSourceProject(final SubApp subapp) {
		final EObject rootContainer = EcoreUtil.getRootContainer(subapp);
		if (rootContainer instanceof final LibraryElement libEl) {
			return libEl.getTypeEntry().getFile().getProject();
		}
		return null;
	}

	private static boolean checkIfChild(final Level source, final EObject target) {
		if (target instanceof RootLevel) {
			return false;
		}

		if (target instanceof final Level level && source == level) {
			return true;
		}

		return checkIfChild(source, target.eContainer());
	}

	private static boolean hasChild(final Level level, final String subappName) {
		return level.getChildren().stream().anyMatch(node -> {
			if (node instanceof final Leaf leaf) {
				return leaf.getRef().equals(subappName);
			}
			return node instanceof final Level l && hasChild(l, subappName);
		});
	}
}