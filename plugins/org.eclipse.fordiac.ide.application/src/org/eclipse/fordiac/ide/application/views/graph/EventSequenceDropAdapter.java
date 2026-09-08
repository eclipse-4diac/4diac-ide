/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.views.graph;

import java.util.Comparator;
import java.util.List;

import org.eclipse.fordiac.ide.application.commands.ReorderEventSequenceCommand;
import org.eclipse.fordiac.ide.model.graph.FBNetworkGraph;
import org.eclipse.fordiac.ide.model.graph.FBNetworkTopologyGraph;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.util.ErrorMessenger;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

public class EventSequenceDropAdapter extends ViewerDropAdapter {
	private final CommandStack commandStack;

	public EventSequenceDropAdapter(final Viewer viewer, final CommandStack commandStack) {
		super(viewer);
		this.commandStack = commandStack;
	}

	@Override
	public boolean validateDrop(final Object target, final int operation, final TransferData transferType) {
		if (commandStack == null || !(target instanceof final FBNetworkGraph<?>.Node node)) {
			return false;
		}

		final List<BlockFBNetworkElement> sources = getElements(LocalSelectionTransfer.getTransfer().getSelection());
		if (sources.isEmpty()) {
			return false;
		}

		return sources.stream().allMatch(source -> isValidDrop(source, node.getElement()));
	}

	private static boolean isValidDrop(final BlockFBNetworkElement source, final BlockFBNetworkElement target) {
		return source != target && source.getFbNetwork() == target.getFbNetwork();
	}

	@Override
	public boolean performDrop(final Object data) {
		if (commandStack == null || !(getCurrentTarget() instanceof final FBNetworkGraph<?>.Node target)) {
			return false;
		}

		final List<BlockFBNetworkElement> sources = getElements(LocalSelectionTransfer.getTransfer().getSelection());
		final ReorderEventSequenceCommand command = new ReorderEventSequenceCommand(sources, target.getElement(),
				isInsertBefore());
		if (!command.canExecute()) {
			if (!command.getErrorMessage().isEmpty()) {
				ErrorMessenger.popUpErrorMessage(command.getErrorMessage());
			}
			return false;
		}

		commandStack.execute(command);
		return true;
	}

	private boolean isInsertBefore() {
		// insert after for LOCATION_ON to simplify hitting the target
		return getCurrentLocation() == LOCATION_BEFORE;
	}

	@Override
	public void dragOver(final DropTargetEvent event) {
		super.dragOver(event);
		// change feedback to after for LOCATION_ON
		if (getCurrentLocation() == LOCATION_ON) {
			event.feedback &= ~DND.FEEDBACK_SELECT;
			event.feedback |= DND.FEEDBACK_INSERT_AFTER;
		}
	}

	private static List<BlockFBNetworkElement> getElements(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structuredSelection
				&& structuredSelection.stream().allMatch(FBNetworkTopologyGraph.TopologyNode.class::isInstance)) {
			return structuredSelection.stream().<FBNetworkTopologyGraph<?>
					.TopologyNode>map(FBNetworkTopologyGraph.TopologyNode.class::cast)
					.sorted(Comparator.comparingInt(FBNetworkTopologyGraph<?>.TopologyNode::getIndex))
					.map(FBNetworkTopologyGraph<?>.TopologyNode::getElement).toList();
		}
		return List.of();
	}
}