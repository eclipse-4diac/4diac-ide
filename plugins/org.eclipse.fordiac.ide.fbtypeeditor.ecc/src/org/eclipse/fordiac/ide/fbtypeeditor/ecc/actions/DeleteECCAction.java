/*******************************************************************************
 * Copyright (c) 2013 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECTransitionEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IWorkbenchPart;

public class DeleteECCAction extends DeleteAction {

	public DeleteECCAction(final IWorkbenchPart part) {
		super(part);
	}

	@Override
	public Command createDeleteCommand(final List objects) {
		if (objects.isEmpty()) {
			return null;
		}
		if (!(objects.get(0) instanceof EditPart)) {
			return null;
		}
		return super.createDeleteCommand(getDeleteList(objects));
	}

	@SuppressWarnings("rawtypes")
	private static List<EditPart> getDeleteList(final List objects) {
		final List<EditPart> list = new ArrayList<>();

		for (final Object object : objects) {
			if (object instanceof ECTransitionEditPart) {
				list.add(0, (EditPart) object); // add the transitions before anything else
			} else if (object instanceof ECActionAlgorithmEditPart) {
				if (!stateContainedInDeleteList(objects,
						((ECActionAlgorithmEditPart) object).getCastedModel().getAction().getECState())) {
					list.add((EditPart) object);
				}
			} else if (object instanceof ECActionOutputEventEditPart) {
				if (!stateContainedInDeleteList(objects,
						((ECActionOutputEventEditPart) object).getCastedModel().getAction().getECState())) {
					list.add((EditPart) object);
				}
			} else if (object instanceof EditPart) {
				list.add((EditPart) object);
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	private static boolean stateContainedInDeleteList(final List objects, final ECState eState) {
		for (final Object object : objects) {
			if (object instanceof EditPart && ((EditPart) object).getModel().equals(eState)) {
				return true;
			}
		}
		return false;
	}

}
