/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class DataInterfaceLayoutEditPolicy extends LayoutEditPolicy {
	@Override
	protected Command getMoveChildrenCommand(final Request request) {
		return null;
	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		if ((getHost().getModel() instanceof VarDeclaration) && (!(getHost().getModel() instanceof AdapterDeclaration))
				&& (!(request.getNewObjectType() instanceof ResourceTypeEntry))) {
			return new ChangeValueCommand((VarDeclaration) getHost().getModel(),
					request.getNewObject() != null ? request.getNewObject().toString() : ""); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return null;
	}
}