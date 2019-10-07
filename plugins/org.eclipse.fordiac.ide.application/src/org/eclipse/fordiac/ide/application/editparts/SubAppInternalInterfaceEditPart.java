/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH, Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.application.policies.DeleteSubAppInterfaceElementPolicy;
import org.eclipse.fordiac.ide.gef.draw2d.ConnectorBorder;
import org.eclipse.gef.EditPolicy;

public class SubAppInternalInterfaceEditPart extends UntypedSubAppInterfaceElementEditPart {
	
	@Override
	protected IFigure createFigure() {
		InterfaceFigure figure = new InterfaceFigure();
		figure.setBorder(new ConnectorBorder(getModel()){
			@Override
			public boolean isInput() {
				return !super.isInput();
			}
		});
		return figure;
	}
	
	@Override
	public boolean isInput() {
		return !super.isInput();
	}
	
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		// allow delete of a subapp's interface element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeleteSubAppInterfaceElementPolicy());
	}
	
}
