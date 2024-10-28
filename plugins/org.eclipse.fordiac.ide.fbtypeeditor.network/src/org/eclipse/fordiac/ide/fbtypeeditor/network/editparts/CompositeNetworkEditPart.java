/*******************************************************************************
 * Copyright (c) 2008, 2024  Profactor GmbH, fortiss GmbH,
 *                           Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Moved code to common base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.EditorWithInterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * Edit Part for the visualization of Composite Networks.
 */
public class CompositeNetworkEditPart extends EditorWithInterfaceEditPart {

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelChildren() {
		final List<Object> modelChildren = (List<Object>) super.getModelChildren();
		if (getModel() != null) {
			modelChildren.addAll(getInterfaceList().getInOutVars());
		}
		return modelChildren;
	}

	/**
	 * Creates the EditPolicies used for this EditPart.
	 *
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// // handles constraint changes of model elements and creation of new
		// // model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CompositeFBNetworkLayoutEditPolicy());

	}

	private CompositeFBType getFbType() {
		return (CompositeFBType) getModel().eContainer();
	}

	@Override
	protected boolean isVarVisible(final EditPart childEditPart) {
		return !(childEditPart.getModel() instanceof AdapterDeclaration);
	}

	@Override
	protected InterfaceList getInterfaceList() {
		return getFbType().getInterfaceList();
	}
}
