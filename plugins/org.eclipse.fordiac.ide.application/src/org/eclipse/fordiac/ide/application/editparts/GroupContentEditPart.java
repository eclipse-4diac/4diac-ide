/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
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
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.policies.AbstractContainerCreateInstanceDirectEditPolicy;
import org.eclipse.fordiac.ide.application.policies.GroupXYLayoutPolicy;
import org.eclipse.fordiac.ide.gef.figures.AbstractShadowBorder;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.create.CreateFBElementInGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;

public class GroupContentEditPart extends AbstractContainerContentEditPart {

	@Override
	public GroupContentNetwork getModel() {
		return (GroupContentNetwork) super.getModel();
	}

	@Override
	protected List<FBNetworkElement> getNetworkElements() {
		// our model is the group and the getNetworkElements all elements in the group
		// we want to show as children
		return getModel().getNetworkElements();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GroupXYLayoutPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractContainerCreateInstanceDirectEditPolicy() {
			@Override
			public Command getElementCreateCommand(final TypeEntry type, final Point refPoint) {
				return new ResizeGroupOrSubappCommand(this.getHost(),
						new CreateFBElementInGroupCommand(type, getModel().getGroup(), refPoint.x, refPoint.y));
			}
		});
	}

	@Override
	protected IFigure createFigure() {
		final IFigure newGroupContentFigure = super.createFigure();
		final int lrBorder = (int) CoordinateConverter.INSTANCE.getLineHeight()
				- AbstractShadowBorder.SHADOW_INSETS.left;
		newGroupContentFigure.setBorder(new MarginBorder(0, lrBorder, 5, lrBorder));
		return newGroupContentFigure;
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (key == Group.class) {
			return key.cast(getModel().getGroup());
		}
		return super.getAdapter(key);
	}

	@Override
	public Group getContainerElement() {
		return getModel().getGroup();
	}

}
