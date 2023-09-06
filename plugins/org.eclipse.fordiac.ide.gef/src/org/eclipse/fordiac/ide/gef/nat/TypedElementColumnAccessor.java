/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactor to generic implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

public class TypedElementColumnAccessor<T extends ITypedElement> implements IColumnPropertyAccessor<T> {

	private final CommandExecutor commandExecutor;
	private final List<TypedElementTableColumn> columns;

	protected TypedElementColumnAccessor(final CommandExecutor commandExecutor) {
		this(commandExecutor, TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	protected TypedElementColumnAccessor(final CommandExecutor commandExecutor,
			final List<TypedElementTableColumn> columns) {
		this.commandExecutor = commandExecutor;
		this.columns = columns;
	}

	protected CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public List<TypedElementTableColumn> getColumns() {
		return columns;
	}

	@Override
	public Object getDataValue(final T rowObject, final int columnIndex) {
		return getDataValue(rowObject, columns.get(columnIndex));
	}

	public Object getDataValue(final T rowObject, final TypedElementTableColumn column) {
		return switch (column) {
		case NAME -> rowObject.getName();
		case TYPE -> rowObject.getFullTypeName();
		case COMMENT -> CommentHelper.getInstanceComment(rowObject);
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	@Override
	public void setDataValue(final T rowObject, final int columnIndex, final Object newValue) {
		final Command cmd = createCommand(rowObject, columns.get(columnIndex), newValue);
		if (cmd.canExecute()) {
			commandExecutor.executeCommand(cmd);
		}
	}

	public Command createCommand(final T rowObject, final TypedElementTableColumn column, final Object newValue) {
		return switch (column) {
		case NAME -> new ChangeNameCommand(rowObject, newValue.toString());
		case TYPE -> throw new UnsupportedOperationException("Cannot change type"); //$NON-NLS-1$
		case COMMENT -> new ChangeCommentCommand(rowObject, newValue.toString());
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	@Override
	public String getColumnProperty(final int columnIndex) {
		return columns.get(columnIndex).toString();
	}

	@Override
	public int getColumnIndex(final String propertyName) {
		return IntStream.range(0, getColumnCount()).filter(i -> columns.get(i).toString().equals(propertyName))
				.findFirst().orElse(-1);
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}
}
