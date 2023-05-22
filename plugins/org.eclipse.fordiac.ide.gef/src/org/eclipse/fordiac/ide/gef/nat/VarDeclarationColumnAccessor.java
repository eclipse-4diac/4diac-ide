/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

public class VarDeclarationColumnAccessor implements IColumnAccessor<VarDeclaration> {

	private final CommandExecutor section;
	private DataTypeLibrary dataTypeLib;

	public VarDeclarationColumnAccessor(final CommandExecutor section) {
		this.section = section;
	}

	protected CommandExecutor getSection() {
		return section;
	}


	@Override
	public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			return rowObject.getName();
		case I4diacNatTableUtil.TYPE:
			return rowObject.getFullTypeName();
		case I4diacNatTableUtil.COMMENT:
			return rowObject.getComment();
		case I4diacNatTableUtil.INITIAL_VALUE:
			return InitialValueHelper.getInitialOrDefaultValue(rowObject);
		default:
			return rowObject.getValue() == null ? "" : rowObject.getValue().getValue(); //$NON-NLS-1$
		}
	}

	@Override
	public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
		final String value = newValue instanceof String ? (String) newValue : null;
		Command cmd = null;
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			if (value == null) {
				return;
			}
			cmd = new ChangeNameCommand(rowObject, value);
			break;
		case I4diacNatTableUtil.TYPE:
			cmd = ChangeDataTypeCommand.forTypeDeclaration(rowObject, value);
			break;
		case I4diacNatTableUtil.COMMENT:
			cmd = new ChangeCommentCommand(rowObject, value);
			break;
		case I4diacNatTableUtil.INITIAL_VALUE:
			cmd = new ChangeValueCommand(rowObject, value);
			break;
		default:
			return;
		}

		getSection().executeCommand(cmd);
	}

	@Override
	public int getColumnCount() {
		return I4diacNatTableUtil.INITIAL_VALUE + 1;
	}

	public void setTypeLib(final DataTypeLibrary dataTypeLib) {
		this.dataTypeLib = dataTypeLib;
	}
}