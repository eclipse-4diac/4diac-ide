/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.policies.FBAddToSubAppLayoutEditPolicy;
import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;

public class UnfoldedSubappContentEditPart extends AbstractContainerContentEditPart {

	static final int VER_BORDER_WIDTH = 10;
	static final int HOR_BORDER_WIDTH = 7 * VER_BORDER_WIDTH;
	static final Insets BORDER_INSET = new Insets(VER_BORDER_WIDTH, HOR_BORDER_WIDTH, VER_BORDER_WIDTH,
			HOR_BORDER_WIDTH);

	@Override
	public void setModel(final Object model) {
		super.setModel(model);
		p = FBNetworkHelper.getTopLeftCornerOfFBNetwork(getModel().getNetworkElements());
		p.x -= 40;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// Add policy to handle drag&drop of fbs
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBAddToSubAppLayoutEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		final IFigure figure = super.createFigure();
		figure.setBorder(new MarginBorder(BORDER_INSET));
		return figure;
	}

	@Override
	public void setLayoutConstraint(final EditPart child, final IFigure childFigure, final Object constraint) {
		if (constraint instanceof Rectangle) {
			final Rectangle rectConstraint = (Rectangle) constraint;
			if (child instanceof ValueEditPart) {
				rectConstraint.performTranslate(-getFigure().getBounds().x - HOR_BORDER_WIDTH,
						-getFigure().getBounds().y - VER_BORDER_WIDTH);
			} else {
				rectConstraint.performTranslate(-p.x, -p.y);
			}
		}
		super.setLayoutConstraint(child, childFigure, constraint);

	}

}
