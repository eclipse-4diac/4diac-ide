/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Virendra Ashiwal - method createMoveChildCommand and condition canReorder
 *                      for Events/Variables and Adapter Interface(Socket and Plug)
 *                      moved to base class AbstractInterfaceContainerLayoutEditPolicy
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class EventInputContainerLayoutEditPolicy extends AbstractInterfaceContainerLayoutEditPolicy {

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {

		return new AbstractInterfaceSelectionEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1)) {

			@Override
			protected List<? extends IInterfaceElement> getInterfaceElementList() {
				return getFBType().getInterfaceList().getEventInputs();
			}

			@Override
			protected Command getIECreateCommand(final DataType refElement, final int ref) {
				return new CreateInterfaceElementCommand(refElement, getFBType().getInterfaceList(), true, ref);
			}
		};
	}

	@Override
	protected boolean canReorder(final IInterfaceElement childEP, final IInterfaceElement afterEP) {
		return childEP instanceof Event && childEP.isIsInput()
				&& (null == afterEP || (afterEP instanceof Event && afterEP.isIsInput()));
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		final Object childClass = request.getNewObjectType();
		final FBType type = getFBType();
		int index = -1;
		final EditPart ref = getInsertionReference(request);
		if (null != ref) {
			index = type.getInterfaceList().getEventInputs().indexOf(ref.getModel());
		}
		if (childClass instanceof EventType && null != type) {
			return new CreateInterfaceElementCommand((DataType) childClass, type.getInterfaceList(), true, index);
		}
		return null;
	}
}
