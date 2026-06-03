/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommonConstants;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.AddToComparisonCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.MoveDownCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.MoveLeftCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.MoveRightCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.MoveUpCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.RemoveFromComparisonCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.NameStackedFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Resource;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * @brief EditPart for the Resource element in the replay navigator.
 *
 *        This class is responsible for creating the figure that represents a
 *        resource in the replay navigator, providing the content pane for its
 *        children, and handling the navigation actions (forward, backward, up,
 *        down) by interacting with the ReplayNavigator of the resource.
 */
public class ResourceEditPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		return new NameStackedFigure(getReplayNavigatorName());
	}

	private String getReplayNavigatorName() {
		return getModel().getName();
	}

	@Override
	public IFigure getContentPane() {
		return ((NameStackedFigure) getFigure()).getContentPane();
	}

	@Override
	protected List<?> getModelChildren() {
		return List.of(getModel().getRootTimelineModel());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy());
		installEditPolicy(CommonConstants.NAVIGATION_POLICY, new AbstractEditPolicy() {
			@Override
			public Command getCommand(final Request request) {
				if (!(request instanceof final NavigationRequest nav)) {
					return null;
				}
				return switch (nav.getDirection()) {
				case UP -> new MoveUpCommand(getModel().getReplayNavigator());
				case DOWN -> new MoveDownCommand(getModel().getReplayNavigator());
				case LEFT -> new MoveLeftCommand(getModel().getReplayNavigator());
				case RIGHT -> new MoveRightCommand(getModel().getReplayNavigator());
				default -> null;
				};
			}
		});

		installEditPolicy(CommonConstants.COMPARISON_POLICY, new AbstractEditPolicy() {
			@Override
			public Command getCommand(final Request request) {
				if (CommonConstants.ADD_TO_COMPARISON_REQUEST.equals(request.getType())) {
					return new AddToComparisonCommand(getModel().getReplayNavigator(),
							getModel().getReplayNavigator().getCurrentEventPosition(), CommentsHandler.getInstance()
									.getComment(getModel().getReplayNavigator().getCurrentEventPosition()));
				}
				if (CommonConstants.REMOVE_FROM_COMPARISON_REQUEST.equals(request.getType())) {
					return new RemoveFromComparisonCommand(getModel().getReplayNavigator().getCurrentEventPosition());
				}
				return null;
			}
		});

	}

	@Override
	public Resource getModel() {
		return (Resource) super.getModel();
	}
}
