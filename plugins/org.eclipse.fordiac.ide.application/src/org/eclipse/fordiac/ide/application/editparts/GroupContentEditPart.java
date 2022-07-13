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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupCommand;
import org.eclipse.fordiac.ide.application.policies.GroupXYLayoutPolicy;
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
		// our model is the group and the getNetworkElements all elements in the group we want to show as children
		return getModel().getNetworkElements();
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GroupXYLayoutPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new AbstractCreateInstanceDirectEditPolicy() {
			@Override
			public Command getElementCreateCommand(final TypeEntry type, final Point refPoint) {
				return new ResizeGroupCommand(
						new CreateFBElementInGroupCommand(type, getModel().getGroup(), refPoint.x, refPoint.y),
						getViewer(), this.getHost());
			}
		});
	}


	@Override
	public Object getAdapter(final Class key) {
		if (key == Group.class) {
			return getModel().getGroup();
		}
		return super.getAdapter(key);
	}

	@Override
	public Group getContainerElement() {
		return getModel().getGroup();
	}


}
