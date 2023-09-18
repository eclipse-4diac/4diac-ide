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
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeleteVarInOutCommand extends DeleteInterfaceCommand {

	public DeleteVarInOutCommand(final VarDeclaration varInOut) {
		super(getInterfaceElement(varInOut));
	}

	@Override
	public void execute() {
		collectAdditionalOppositeDeleteCmds();
		super.execute();
	}

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}

	private void collectAdditionalOppositeDeleteCmds() {
		final VarDeclaration varInOut = getInterfaceElement();
		final VarDeclaration inOutVarOpposite = varInOut.getInOutVarOpposite();

		handleWiths(inOutVarOpposite);
		handleSubAppConnections(inOutVarOpposite);
	}

	private static IInterfaceElement getInterfaceElement(final VarDeclaration varInOut) {
		if (varInOut.isInOutVar() && !varInOut.isIsInput()) {
			return varInOut.getInOutVarOpposite();
		}
		return varInOut;
	}
}
