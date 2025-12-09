/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, AIT, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed untyped subapp interface updates and according code cleanup
 *   Daniel Lindhuber - altered methods to work with the elk layouter
 *   Alois Zoitl - Moved code to common base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class UISubAppNetworkEditPart extends EditorWithInterfaceEditPart {

	public SubApp getSubApp() {
		return (SubApp) getModel().eContainer();
	}

	@Override
	protected List<?> getModelChildren() {
		@SuppressWarnings("unchecked")
		final List<Object> modelChildren = (List<Object>) super.getModelChildren();
		if (getModel() != null) {
			final InterfaceList ifList = getInterfaceList();
			modelChildren.addAll(ifList.getInOutVars());
			modelChildren.addAll(ifList.getOutMappedInOutVars());
		}
		return modelChildren;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FBNetworkXYLayoutEditPolicy());
	}

	@Override
	protected InterfaceList getInterfaceList() {
		return getSubApp().getInterface();
	}

}