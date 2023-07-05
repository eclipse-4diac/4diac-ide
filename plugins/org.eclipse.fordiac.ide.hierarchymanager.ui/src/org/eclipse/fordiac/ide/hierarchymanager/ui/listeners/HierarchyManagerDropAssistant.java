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

		if (!(target instanceof Node)) {
			return Status.CANCEL_STATUS;
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

		if (!isNodeDrop && getTargetProject() != getSourceProject(
				(SubApp) (((TreeSelection) aDropTargetEvent.data).getFirstElement()))) {
			return Status.CANCEL_STATUS;
		}

		if (!this.isSupportedType(aDropAdapter.getCurrentTransfer())) {
			return Status.CANCEL_STATUS;
		}
		Level parent = null;
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

			final MoveNodeOperation operation = new MoveNodeOperation(parent,
					(Node) (((TreeSelection) aDropTargetEvent.data).getFirstElement()));
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
}