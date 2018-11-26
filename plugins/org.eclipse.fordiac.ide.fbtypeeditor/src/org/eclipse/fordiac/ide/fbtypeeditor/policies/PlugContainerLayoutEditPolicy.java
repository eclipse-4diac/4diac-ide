/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.AdapterInterfaceEditPart;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.preference.IPreferenceStore;

public class PlugContainerLayoutEditPolicy extends AbstractInterfaceContainerLayoutEditPolicy {
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		IPreferenceStore pf = Activator.getDefault().getPreferenceStore();
		int cornerDim = pf.getInt(DiagramPreferences.CORNER_DIM);
		if (cornerDim > 1) {
			cornerDim = cornerDim / 2;
		}
		return new AbstractInterfaceSelectionEditPolicy(cornerDim, new Insets(1)){

			@Override
			protected List<? extends IInterfaceElement> getInterfaceElementList() {
				return getFBType().getInterfaceList().getPlugs();
			}

			@Override
			protected Command getIECreateCommand(DataType refElement, int ref) {				
				return new CreateInterfaceElementCommand(refElement, getFBType().getInterfaceList(), false, ref);
			}
		};
	}

	@Override
	protected Command createMoveChildCommand(final EditPart child,
			final EditPart after) {
		if (child instanceof AdapterInterfaceEditPart) {
			AdapterInterfaceEditPart childEP = (AdapterInterfaceEditPart) child;
			AdapterInterfaceEditPart afterEP = null;
			if (after != null) {
				afterEP = (AdapterInterfaceEditPart) after;
			}
			if (childEP.isAdapter() && !childEP.isInput()
					&& (afterEP == null || (afterEP.isAdapter() && !afterEP.isInput()))) {
				int newIndex = -1;
				if (after == null) {
					newIndex = getHost().getChildren().size();
				} else {
					newIndex = getHost().getChildren().indexOf(after);
				}
				return new ChangeInterfaceOrderCommand((IInterfaceElement) childEP.getModel(), ((IInterfaceElement)childEP.getModel()).isIsInput(), newIndex);
			}

		}
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		Object childClass = request.getNewObjectType();
		FBType type = getFBType();
		if (childClass instanceof AdapterType && type != null) {
			int index = -1;
			EditPart ref = getInsertionReference(request);		
			if (ref != null) {
				index = type.getInterfaceList().getPlugs().indexOf(ref.getModel());
			}
			return new CreateInterfaceElementCommand((DataType) childClass, type.getInterfaceList(), false, index);
		}
		return null;
	}
}
