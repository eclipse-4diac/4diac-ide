/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.policies.GroupXYLayoutPolicy;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

public class GroupContentEditPart extends AbstractContainerContentEditPart {

	@Override
	protected List<FBNetworkElement> getNetworkElements() {
		// our model is the group and the getNetworkElements all elements in the group we want to show as children
		return getModel().getNetworkElements();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GroupXYLayoutPolicy());
	}

	@Override
	public void setLayoutConstraint(final EditPart child, final IFigure childFigure, final Object constraint) {
		if ((constraint instanceof Rectangle)  && (child instanceof ValueEditPart)){
			final Rectangle rectConstraint = (Rectangle) constraint;
			final Point topLeft = getFigure().getClientArea().getTopLeft();
			rectConstraint.performTranslate(-topLeft.x, -topLeft.y);
		}
		super.setLayoutConstraint(child, childFigure, constraint);

	}

}
