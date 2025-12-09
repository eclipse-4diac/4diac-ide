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

import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;

public class InterfaceElementColumnAccessor<T extends IInterfaceElement> extends TypedElementColumnAccessor<T> {

	public InterfaceElementColumnAccessor(final CommandExecutor commandExecutor) {
		super(commandExecutor, TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	public InterfaceElementColumnAccessor(final CommandExecutor commandExecutor,
			final List<TypedElementTableColumn> columns) {
		super(commandExecutor, columns);
	}

	@Override
	public Command createCommand(final T rowObject, final TypedElementTableColumn column, final Object newValue) {
		return switch (column) {
		case TYPE -> ChangeDataTypeCommand.forTypeName(rowObject, newValue.toString());
		default -> super.createCommand(rowObject, column, newValue);
		};
	}
}
