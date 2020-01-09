/*******************************************************************************
 * Copyright (c) 2012, 2013 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
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

	public DeleteFBNetworkAction(IWorkbenchPart part) {
		super(part);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Command createDeleteCommand(List objects) {
		if (objects.isEmpty()) {
			return null;
		}
		if (!(objects.get(0) instanceof EditPart)) {
			return null;
		}

		List<EditPart> list = new ArrayList<>();

		// Resort list such that the connects are before any other edit parts
		for (Object object : objects) {
			if (object instanceof ConnectionEditPart) {
				list.add((EditPart) object);
			}
		}

		for (Object object : objects) {
			if (!(object instanceof ConnectionEditPart)) {
				list.add((EditPart) object);
			}
		}

		return super.createDeleteCommand(list);
	}

}
