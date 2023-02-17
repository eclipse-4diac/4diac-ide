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
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;

public class VarDeclarationWithVarConfigColumnAccessor extends VarDeclarationColumnAccessor {

	public VarDeclarationWithVarConfigColumnAccessor(final CommandExecutor section) {
		super(section);
	}

	public VarDeclarationWithVarConfigColumnAccessor(final CommandExecutor section, final TypeLibrary library) {
		super(section, library);
	}

	@Override
	public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
		if (columnIndex == I4diacNatTableUtil.VAR_CONFIG) {
			return rowObject.isVarConfig();
		}
		return super.getDataValue(rowObject, columnIndex);
	}

	@Override
	public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
		if (columnIndex == I4diacNatTableUtil.VAR_CONFIG) {
			getSection().executeCommand(new ChangeVarConfigurationCommand(rowObject, ((Boolean) newValue).booleanValue()));
			return;
		}
		super.setDataValue(rowObject, columnIndex, newValue);
	}

	@Override
	public int getColumnCount() {
		return 6;
	}
}