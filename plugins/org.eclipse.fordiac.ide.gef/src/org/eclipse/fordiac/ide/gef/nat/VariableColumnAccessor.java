/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.gef.commands.Command;

public class VariableColumnAccessor extends AbstractColumnAccessor<Variable<?>, VariableTableColumn> {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	public VariableColumnAccessor() {
		this(VariableTableColumn.DEFAULT_COLUMNS);
	}

	public VariableColumnAccessor(final List<VariableTableColumn> columns) {
		super(null, columns);
	}

	@Override
	public Object getDataValue(final Variable<?> rowObject, final VariableTableColumn column) {
		return switch (column) {
		case NAME -> rowObject.getName();
		case TYPE -> {
			if (rowObject.getType() instanceof final LibraryElement libraryElement) {
				yield PackageNameHelper.getFullTypeName(libraryElement);
			}
			yield rowObject.getType().getName();
		}
		case VALUE -> rowObject.toString();
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	@Override
	public void setDataValue(final Variable<?> rowObject, final int columnIndex, final Object newValue) {
		switch (getColumns().get(columnIndex)) {
		case VALUE -> rowObject.setValue(Objects.toString(newValue, NULL_DEFAULT));
		case NAME, TYPE -> throw new UnsupportedOperationException(
				"Cannot set value for column: " + getColumns().get(columnIndex).getDisplayName()); //$NON-NLS-1$
		default -> throw new IllegalArgumentException("Unexpected value: " + getColumns().get(columnIndex)); //$NON-NLS-1$
		}
	}

	@Override
	public Command createCommand(final Variable<?> rowObject, final VariableTableColumn column, final Object newValue) {
		throw new UnsupportedOperationException("Cannot create command for changing variable"); //$NON-NLS-1$
	}
}
