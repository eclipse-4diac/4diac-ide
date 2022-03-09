/*******************************************************************************
 * Copyright (c) 2012, 2022 fortiss GmbH, Primetals Technologies GmbH
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
import java.util.List;

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
	public Command createDeleteCommand(final List selObjects) {
		if (selObjects.isEmpty()) {
			return null;
		}
		if (!(selObjects.get(0) instanceof EditPart)) {
			return null;
		}

		final List<EditPart> list = new ArrayList<>();

		// Resort list such that the connects are before any other edit parts
		for (final Object object : selObjects) {
			if (object instanceof ConnectionEditPart) {
				list.add((EditPart) object);
			}
		}

		for (final Object object : selObjects) {
			if (!(object instanceof ConnectionEditPart) && !isInGroupToBeDeleted(object, selObjects)) {
				list.add((EditPart) object);
			}
		}

		return super.createDeleteCommand(list);
	}

	private static boolean isInGroupToBeDeleted(final Object object, final List<Object> selObjects) {
		if (object instanceof EditPart && ((EditPart) object).getModel() instanceof FBNetworkElement) {
			final FBNetworkElement el = (FBNetworkElement)((EditPart) object).getModel();
			if(el.isInGroup()) {
				final Group group = el.getGroup();
				return selObjects.stream().anyMatch(ob -> isGroup(ob, group));
			}
		}
		return false;
	}

	private static boolean isGroup(final Object obj, final Group group) {
		return (obj instanceof EditPart && ((EditPart) obj).getModel() instanceof Group
				&& ((EditPart) obj).getModel().equals(group));
	}

}
