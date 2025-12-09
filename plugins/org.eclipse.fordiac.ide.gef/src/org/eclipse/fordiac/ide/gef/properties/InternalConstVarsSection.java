/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal  - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalConstVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalConstVariableCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class InternalConstVarsSection extends AbstractInternalVarsSection {

	@Override
	protected CreationCommand newCreateCommand(final Object refElement) {
		return new CreateInternalConstVariableCommand(getType(), getInsertionIndex(), getName(), getDataType());
	}

	@Override
	protected Command newDeleteCommand(final Object refElement) {
		return new DeleteInternalConstVariableCommand(getType(), (VarDeclaration) refElement);
	}

	@Override
	protected EList<VarDeclaration> getVarList() {
		return getType().getInternalConstVars();
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new DeleteInternalConstVariableCommand(getType(), varEntry));
		}
	}
}
