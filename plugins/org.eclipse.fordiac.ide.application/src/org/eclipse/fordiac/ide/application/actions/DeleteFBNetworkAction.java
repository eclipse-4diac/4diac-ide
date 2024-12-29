/*******************************************************************************
 * Copyright (c) 2012, 2024 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added checks that element in groups are not double deleted
 *                 if the parent group is deleted as well
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This special delete object will sort the commands that way that first the
 * connections are added and then the other objects.
 *
 */
public class DeleteFBNetworkAction extends DeleteAction {

	public DeleteFBNetworkAction(final IWorkbenchPart part) {
		super(part);
	}

	@Override
	public Command createDeleteCommand(final List<EditPart> selObjects) {
		if (selObjects.isEmpty()) {
			return null;
		}

		final HashSet<Group> groups = ((List<?>) selObjects).stream().filter(EditPart.class::isInstance)
				.map(EditPart.class::cast).map(EditPart::getModel).filter(Group.class::isInstance)
				.map(Group.class::cast).collect(Collectors.toCollection(HashSet::new));

		final List<EditPart> list = new ArrayList<>();

		// Resort list such that the connects are before any other edit parts
		for (final EditPart ep : selObjects) {
			if (ep instanceof ConnectionEditPart) {
				list.add(ep);
			}
		}

		for (final EditPart ep : selObjects) {
			if (!(ep instanceof ConnectionEditPart) && !isInGroupToBeDeleted(ep, groups)) {
				list.add(ep);
			}
		}

		return super.createDeleteCommand(list);
	}

	private static boolean isInGroupToBeDeleted(final Object object, final HashSet<Group> groups) {
		if (!(object instanceof final EditPart editPart
				&& editPart.getModel() instanceof final FBNetworkElement element)) {
			return false;
		}

		if (!element.isInGroup()) {
			return false;
		}

		return groups.contains(element.getGroup());
	}
}
