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

		if (!(target instanceof Level)) {
			return Status.CANCEL_STATUS;
		}

		if ((getCurrentEvent().data instanceof final TreeSelection selection)
				&& (selection.getFirstElement() instanceof final SubApp subapp)) {
			final RootLevel root = (RootLevel) EcoreUtil.getRootContainer((Level) target);
			if (root != null && root.getLevels().stream().anyMatch(l -> hasChild(l, subapp.getQualifiedName()))) {
				return Status.CANCEL_STATUS;
			}
		}

		return Status.OK_STATUS;
	}

	@Override
	public IStatus handleDrop(final CommonDropAdapter aDropAdapter, final DropTargetEvent aDropTargetEvent,
			final Object aTarget) {
		if (aTarget == null || aDropTargetEvent.data == null) {
			return Status.CANCEL_STATUS;
		}
		final boolean isNodeDrop = aDropTargetEvent.data instanceof final TreeSelection selection
				&& selection.getFirstElement() instanceof Node;

		if (((TreeSelection) aDropTargetEvent.data).getFirstElement() instanceof final SubApp subapp
				&& getTargetProject() != getSourceProject(subapp)) {
			return Status.CANCEL_STATUS;
		}

		if (!this.isSupportedType(aDropAdapter.getCurrentTransfer())) {
			return Status.CANCEL_STATUS;
		}

		Level parent = null;
		int targetIndex = -1;
		if (aTarget instanceof final Level targetLevel) {
			parent = targetLevel;
		} else if (aTarget instanceof final Leaf targetLeaf) {
			parent = (Level) targetLeaf.eContainer();
		}

		if (parent != null) {
			if (!isNodeDrop) {
				final CreateLeafOperation operation = new CreateLeafOperation(parent,
						(SubApp) (((TreeSelection) aDropTargetEvent.data).getFirstElement()));
				AbstractHierarchyHandler.executeOperation(operation);

				return Status.OK_STATUS;
			}

			final Node node = (Node) (((TreeSelection) aDropTargetEvent.data).getFirstElement());

			// don't allow to drop a level on it's own leaves
			if (node instanceof final Level level && checkIfChild(level, (EObject) aTarget)) {
				return Status.CANCEL_STATUS;
			}

			if (aTarget instanceof final Leaf targetLeaf && (node instanceof Leaf || node instanceof Level)) {
				targetIndex = ((Level) targetLeaf.eContainer()).getChildren().indexOf(targetLeaf);
			}

			final MoveNodeOperation operation = new MoveNodeOperation(parent, node, targetIndex);
			AbstractHierarchyHandler.executeOperation(operation);

			return Status.OK_STATUS;
		}

		return Status.CANCEL_STATUS;

	}

	@Override
	public boolean isSupportedType(final TransferData aTransferType) {
		return (getCurrentEvent().data instanceof final TreeSelection selection
				&& (selection.getFirstElement() instanceof Node || selection.getFirstElement() instanceof SubApp))
				&& super.isSupportedType(aTransferType);
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