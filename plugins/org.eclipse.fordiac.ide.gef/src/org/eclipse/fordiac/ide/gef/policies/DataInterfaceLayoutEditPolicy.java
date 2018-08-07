/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class DataInterfaceLayoutEditPolicy extends LayoutEditPolicy {
	@Override
	protected Command getMoveChildrenCommand(Request request) {
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if ((getHost() instanceof InterfaceEditPart) && (!(request.getNewObjectType() instanceof ResourceTypeEntry))){
			//TODO think of a better check that allows only appropriate request object types
			InterfaceEditPart host = (InterfaceEditPart) getHost();
			if ((host.getModel() instanceof VarDeclaration) && (!(host.getModel() instanceof AdapterDeclaration))) {
				VarDeclaration v = (VarDeclaration) host.getModel();
				return new ChangeValueCommand(v, request.getNewObject() != null ? request.getNewObject().toString():""); //$NON-NLS-1$
			}
		}
		return null;
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		return null;
	}
}