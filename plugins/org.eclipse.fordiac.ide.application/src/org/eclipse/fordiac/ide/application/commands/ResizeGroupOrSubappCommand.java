/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.AbstractContainerContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.GroupContentEditPart;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.application.policies.ContainerContentLayoutPolicy;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;

public class ResizeGroupOrSubappCommand extends Command {

	GraphicalEditPart graphicalEditPart;
	List<FBNetworkElement> fbnetworkElements;

	Command cmdToExecuteBefore;

	AbstractChangeContainerBoundsCommand changeContainerBoundsCommand;
	List<AbstractChangeContainerBoundsCommand> changeContainerBoundsCommandList = new ArrayList<>();

	public ResizeGroupOrSubappCommand(final GraphicalEditPart groupOrSubAppContentGraphicalEditPart) {
		this.graphicalEditPart = groupOrSubAppContentGraphicalEditPart;
	}

	public ResizeGroupOrSubappCommand(final GraphicalEditPart groupOrSubAppContentGraphicalEditPart,
			final Command commandToExecuteBefore) {
		this(groupOrSubAppContentGraphicalEditPart);
		this.cmdToExecuteBefore = commandToExecuteBefore;
	}

	public ResizeGroupOrSubappCommand(final GraphicalEditPart groupOrSubAppContentGraphicalEditPart,
			final List<FBNetworkElement> fbnetworkElements) {
		this(groupOrSubAppContentGraphicalEditPart);
		this.fbnetworkElements = fbnetworkElements;
	}

	public ResizeGroupOrSubappCommand(final GraphicalEditPart groupOrSubAppContentGraphicalEditPart,
			final List<FBNetworkElement> fbnetworkElements, final Command commandToExecuteBefore) {
		this(groupOrSubAppContentGraphicalEditPart, fbnetworkElements);
		this.cmdToExecuteBefore = commandToExecuteBefore;
	}

	@Override
	public void execute() {
		if (cmdToExecuteBefore != null && cmdToExecuteBefore.canExecute()) {
			cmdToExecuteBefore.execute();
		} else {
			cmdToExecuteBefore = null;
		}

		checkAndCreateResizeCommand();
		if (changeContainerBoundsCommand != null && changeContainerBoundsCommand.canExecute()) {
			changeContainerBoundsCommandList.add(changeContainerBoundsCommand);
			changeContainerBoundsCommand.execute();
		}

		while(findNestedGraphicalEditPart() != null ) {
			this.graphicalEditPart = findNestedGraphicalEditPart();
			this.fbnetworkElements = null;
			changeContainerBoundsCommand = null;
			checkAndCreateResizeCommand();
			if (changeContainerBoundsCommand != null && changeContainerBoundsCommand.canExecute()) {
				changeContainerBoundsCommandList.add(changeContainerBoundsCommand);
				changeContainerBoundsCommand.execute();
			}
		}
	}

	@Override
	public boolean canExecute() {
		return graphicalEditPart != null;
	}

	@Override
	public void undo() {
		for (final AbstractChangeContainerBoundsCommand changeBoundscmd : changeContainerBoundsCommandList) {
			if (changeBoundscmd != null && changeBoundscmd.canUndo()) {
				changeBoundscmd.undo();
			}
		}
		if (cmdToExecuteBefore != null) {
			cmdToExecuteBefore.undo();
		}
	}

	@Override
	public boolean canUndo() {
		return !changeContainerBoundsCommandList.isEmpty();
	}

	@Override
	public void redo() {
		if (cmdToExecuteBefore != null) {
			cmdToExecuteBefore.redo();
		}
		for (int i = changeContainerBoundsCommandList.size() - 1; i >= 0; i--) {
			if (changeContainerBoundsCommandList.get(i) != null && changeContainerBoundsCommandList.get(i).canRedo()) {
				changeContainerBoundsCommandList.get(i).redo();
			}
		}
	}

	@Override
	public boolean canRedo() {
		return !changeContainerBoundsCommandList.isEmpty();
	}

	private GraphicalEditPart findNestedGraphicalEditPart() {
		if (graphicalEditPart.getParent() != null && graphicalEditPart.getParent().getParent() != null
				&& (graphicalEditPart.getParent().getParent() instanceof GroupContentEditPart
						|| graphicalEditPart.getParent().getParent() instanceof UnfoldedSubappContentEditPart)) {
			return (GraphicalEditPart) graphicalEditPart.getParent().getParent();
		}
		return null;
	}

	private void checkAndCreateResizeCommand() {
		graphicalEditPart.getViewer().flush();
		Rectangle fbBounds = null;
		Rectangle containerBounds = null;
		final List<Object> objects = new ArrayList<>();
		if (fbnetworkElements == null) {
			fbnetworkElements = ((AbstractContainerContentEditPart) graphicalEditPart).getModel().getNetworkElements();
		}

		for (final FBNetworkElement element : fbnetworkElements) {
			objects.add(graphicalEditPart.getViewer().getEditPartRegistry().get(element));
		}

		for (final Object object : objects) {
			if (object instanceof GraphicalEditPart) {
				final IFigure fbFigure = ((GraphicalEditPart) object).getFigure();
				if (fbFigure != null) {
					fbBounds = fbFigure.getBounds();
				}

				// add bounds of input pins
				final Rectangle pinBounds = fbBounds;
				final Map<Object, Object> editPartRegistry = graphicalEditPart.getViewer().getEditPartRegistry();
				fbnetworkElements.forEach(el -> el.getInterface().getInputVars().stream().filter(Objects::nonNull)
						.map(ie -> editPartRegistry.get(ie.getValue())).filter(GraphicalEditPart.class::isInstance)
						.forEach(ep -> {
							final Rectangle pin = ((GraphicalEditPart) ep).getFigure().getBounds();
							pinBounds.union(pin);
						}));
			}
		}

		if (graphicalEditPart instanceof GraphicalEditPart) {
			containerBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(graphicalEditPart);
			if (fbBounds != null && !containerBounds.contains(fbBounds)) {
				fbBounds.union(containerBounds);
				if (graphicalEditPart instanceof UnfoldedSubappContentEditPart) {
					this.changeContainerBoundsCommand = ContainerContentLayoutPolicy.createChangeBoundsCommand(
							((UnfoldedSubappContentEditPart) graphicalEditPart).getModel().getSubapp(), containerBounds,
							fbBounds);
				} else if (graphicalEditPart instanceof GroupContentEditPart) {
					this.changeContainerBoundsCommand = ContainerContentLayoutPolicy.createChangeBoundsCommand(
							((GroupContentEditPart) graphicalEditPart).getModel().getGroup(), containerBounds,
							fbBounds);
				}
			}
		}
	}

}
