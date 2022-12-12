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

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeArraySizeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.edit.providers.DataLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;

public class VarDeclarationColumnAccessor implements IColumnAccessor<VarDeclaration> {

	private final AbstractSection section;

	public VarDeclarationColumnAccessor(final AbstractSection section) {
		this.section = section;
	}

	@Override
	public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
		switch (columnIndex) {
		case VarDeclarationColumnProvider.NAME:
			return rowObject.getName();
		case VarDeclarationColumnProvider.TYPE:
			return rowObject.getTypeName();
		case VarDeclarationColumnProvider.COMMENT:
			return rowObject.getComment();
		case VarDeclarationColumnProvider.INITIAL_VALUE:
			return InitialValueHelper.getInitalOrDefaultValue(rowObject);
		case VarDeclarationColumnProvider.ARRAY_SIZE:
			return DataLabelProvider.getArraySizeText(rowObject);
		default:
			return rowObject.getValue() == null ? "" : rowObject.getValue().getValue(); //$NON-NLS-1$
		}
	}

	@Override
	public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
		final String value = newValue instanceof String ? (String) newValue : null;
		Command cmd = null;
		switch (columnIndex) {
		case VarDeclarationColumnProvider.NAME:
			if (value == null) {
				return;
			}
			cmd = new ChangeNameCommand(rowObject, value);
			break;
		case VarDeclarationColumnProvider.TYPE:
			DataType dataType = section.getDataTypeLib().getDataTypesSorted().stream()
			.filter(type -> type.getName().equals(value)).findAny().orElse(null);
			if (dataType == null) {
				dataType = section.getDataTypeLib().getType(null);
			}
			cmd = new ChangeDataTypeCommand(rowObject, dataType);
			break;
		case VarDeclarationColumnProvider.COMMENT:
			cmd = new ChangeCommentCommand(rowObject, value);
			break;
		case VarDeclarationColumnProvider.INITIAL_VALUE:
			cmd = new ChangeValueCommand(rowObject, value);
			break;
		case VarDeclarationColumnProvider.ARRAY_SIZE:
			cmd = new ChangeArraySizeCommand(rowObject, value);
			break;

		default:
			return;
		}

		section.executeCommand(cmd);
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
}