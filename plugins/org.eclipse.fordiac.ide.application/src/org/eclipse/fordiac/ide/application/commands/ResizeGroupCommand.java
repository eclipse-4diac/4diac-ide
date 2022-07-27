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

import java.util.Map;
import java.util.Objects;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.policies.ContainerContentLayoutPolicy;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;

public class ResizeGroupCommand extends Command {

	CreateFBElementInGroupCommand createFBElementInGroupCommand;
	AbstractChangeContainerBoundsCommand changeContainerBoundsCommand;
	EditPartViewer viewer;
	GraphicalEditPart groupContentEditPart;

	public ResizeGroupCommand(final CreateFBElementInGroupCommand createFBElementInGroupCommand,
			final EditPartViewer editPartViewer, final GraphicalEditPart graphicalEditPart) {
		this.createFBElementInGroupCommand = createFBElementInGroupCommand;
		this.viewer = editPartViewer;
		this.groupContentEditPart = graphicalEditPart;
	}

	@Override
	public void execute() {
		createFBElementInGroupCommand.execute();
		checkGroupBoundsAndResize();
		if (changeContainerBoundsCommand != null && changeContainerBoundsCommand.canExecute()) {
			changeContainerBoundsCommand.execute();
		}
	}

	@Override
	public boolean canExecute() {
		return viewer != null && groupContentEditPart != null && createFBElementInGroupCommand.canExecute();
	}

	@Override
	public void redo() {
		createFBElementInGroupCommand.redo();
		if (changeContainerBoundsCommand != null) {
			changeContainerBoundsCommand.redo();
		}
	}

	@Override
	public boolean canRedo() {
		return createFBElementInGroupCommand.canRedo();
	}

	@Override
	public void undo() {
		if (changeContainerBoundsCommand != null) {
			changeContainerBoundsCommand.undo();
		}
		createFBElementInGroupCommand.undo();
	}

	@Override
	public boolean canUndo() {
		return createFBElementInGroupCommand.canUndo();
	}

	private void checkGroupBoundsAndResize() {
		viewer.flush();
		Rectangle fbBounds = null;
		Rectangle groupBounds = null;
		final Object object = viewer.getEditPartRegistry()
				.get(createFBElementInGroupCommand.getElementCreateCmd().getElement());
		if (object instanceof GraphicalEditPart) {
			final IFigure fbFigure = ((GraphicalEditPart) object).getFigure();
			if (fbFigure != null) {
				fbBounds = fbFigure.getBounds();
			}

			// add bounds of input pins
			final Rectangle pinBounds = fbBounds;
			final Map<Object, Object> editPartRegistry = viewer.getEditPartRegistry();
			createFBElementInGroupCommand.getElementCreateCmd().getElement().getInterface().getInputVars().stream()
			.filter(Objects::nonNull)
			.map(ie -> editPartRegistry.get(ie.getValue())).filter(GraphicalEditPart.class::isInstance)
			.forEach(ep -> {
				final Rectangle pin = ((GraphicalEditPart) ep).getFigure().getBounds();
				pinBounds.union(pin);
			});
		}

		if (groupContentEditPart instanceof GraphicalEditPart) {
			groupBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(groupContentEditPart);
			if (!groupBounds.contains(fbBounds)) {
				fbBounds.union(groupBounds);
				this.changeContainerBoundsCommand = ContainerContentLayoutPolicy
						.createChangeBoundsCommand(createFBElementInGroupCommand.getGroup(), groupBounds, fbBounds);
			}
		}
	}

}
