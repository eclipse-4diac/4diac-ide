/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
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

import java.util.Collections;

import org.eclipse.fordiac.ide.model.commands.create.CreateInternalConstVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalConstVariableCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class InternalConstVarsSection extends AbstractInternalVarsSection {

	@Override
	protected void setInputInit() {
		final BaseFBType currentType = getType();
		provider.setInput(currentType != null ? currentType.getInternalConstVars() : Collections.emptyList());
		table.refresh();
	}

	@Override
	protected CreationCommand newCreateCommand(final Object refElement) {
		return new CreateInternalConstVariableCommand(getType(), getInsertionIndex(), getName(), getDataType());
	}

	@Override
	protected Command newDeleteCommand(final Object refElement) {
		return new DeleteInternalConstVariableCommand(getType(), (VarDeclaration) refElement);
	}

	private int getInsertionIndex() {
		final VarDeclaration varConst = getLastSelectedVariable();
		if (null == varConst) {
			return getType().getInternalConstVars().size();
		}
		return getType().getInternalConstVars().indexOf(varConst) + 1;
	}

	@Override
	public Object getEntry(final int index) {
		return getType().getInternalConstVars().get(index);
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new InsertVariableCommand(getType(), getType().getInternalConstVars(), varEntry, index));
		}
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new DeleteInternalConstVariableCommand(getType(), varEntry));
		}
	}
}
