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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.NameStackedFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Resource;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
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
	}

	@Override
	public Resource getModel() {
		return (Resource) super.getModel();
	}

	public void moveForward() {
		getModel().moveForward();
	}

	public void moveBackwards() {
		getModel().moveBackwards();
	}

	public void moveUp() {
		getModel().moveUp();
	}

	public void moveDown() {
		getModel().moveDown();
	}
}
