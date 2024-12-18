/*******************************************************************************
 * Copyright (c) 2011, 2023 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - copied from VariableInputContainerLayoutEditPolicy
 ********************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

public class VarInOutOutputContainerLayoutEditPolicy extends AbstractInterfaceContainerLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {

		return new AbstractInterfaceSelectionEditPolicy(GefPreferenceConstants.CORNER_DIM_HALF, new Insets(1)) {

			@Override
			protected List<? extends IInterfaceElement> getInterfaceElementList() {
				// we want the in out vars input side for reference generation
				return getFBType().getInterfaceList().getInOutVars();
			}

			@Override
			protected Command getIECreateCommand(final DataType refElement, final int ref) {
				return new CreateVarInOutCommand(refElement, getFBType().getInterfaceList(), ref);
			}

		};
	}

	@Override
	protected boolean canReorder(final IInterfaceElement childEP, final IInterfaceElement afterEP) {
		return isInOutVarOutput(childEP) && (null == afterEP || isInOutVarOutput(afterEP));
	}

	private static boolean isInOutVarOutput(final IInterfaceElement ie) {
		return ie instanceof final VarDeclaration childVar && childVar.isInOutVar() && !ie.isIsInput();
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		final Object childClass = request.getNewObjectType();
		final FBType type = getFBType();
		if (childClass instanceof final DataType dt && null != type && !(childClass instanceof EventType)
				&& !(childClass instanceof AdapterType)) {
			int index = -1;
			final EditPart ref = getInsertionReference(request);
			if (null != ref) {
				// we need the mapped index as the ref element is for the output side
				index = type.getInterfaceList().getOutMappedInOutVars().indexOf(ref.getModel());
			}
			return new CreateVarInOutCommand(dt, type.getInterfaceList(), index);
		}
		return null;
	}

	@Override
	protected Command createChangeInterfaceOrderCmd(final IInterfaceElement childEl, final int newIndex) {
		final InterfaceList il = (InterfaceList) childEl.eContainer();
		final IInterfaceElement inputSide = il.getInOutVars().get(il.getOutMappedInOutVars().indexOf(childEl));
		return new ChangeInterfaceOrderCommand(inputSide, newIndex);
	}

}
