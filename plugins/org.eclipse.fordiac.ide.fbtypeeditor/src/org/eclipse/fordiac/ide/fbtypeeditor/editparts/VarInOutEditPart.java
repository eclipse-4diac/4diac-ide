/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.fordiac.ide.gef.policies.INamedElementRenameEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

public class VarInOutEditPart extends InterfaceEditPart {

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		if (isDirectEditable() && !getModel().isIsInput()) {
			// add own direct edit policy for output side
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new INamedElementRenameEditPolicy() {
				@Override
				protected Command getDirectEditCommand(final DirectEditRequest request) {
					if (getHost() instanceof final VarInOutEditPart viewEditPart) {
						final VarDeclaration varDecl = viewEditPart.getModel();
						return new ChangeNameCommand(
								((InterfaceList) varDecl.eContainer()).getInOutVarOpposite(varDecl),
								(String) request.getCellEditor().getValue());
					}
					return null;
				}
			});
		}
	}

	@Override
	public VarDeclaration getModel() {
		return (VarDeclaration) super.getModel();
	}
}
