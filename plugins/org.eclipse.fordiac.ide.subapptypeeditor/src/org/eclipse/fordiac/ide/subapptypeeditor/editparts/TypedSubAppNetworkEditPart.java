/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Kirill Dorofeev
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPart;
import org.eclipse.fordiac.ide.subapptypeeditor.policies.SubAppTypeFBNetworkLayoutEditPolicy;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

public class TypedSubAppNetworkEditPart extends CompositeNetworkEditPart {

	@Override
	protected List<?> getModelChildren() {
		@SuppressWarnings("unchecked")
		final List<Object> modelChildren = (List<Object>) super.getModelChildren();
		if (getModel() != null) {
			modelChildren.addAll(getInterfaceList().getOutMappedInOutVars());
		}
		return modelChildren;
	}

	@Override
	protected boolean isVarVisible(final EditPart childEditPart) {
		return true;
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes of model elements and creation of new
		// model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new SubAppTypeFBNetworkLayoutEditPolicy());
	}
}
