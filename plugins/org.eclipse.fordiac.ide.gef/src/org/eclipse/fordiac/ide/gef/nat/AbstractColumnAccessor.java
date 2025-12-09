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
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

public abstract class AbstractColumnAccessor<T, C extends NatTableColumn> implements IColumnPropertyAccessor<T> {
	private final CommandExecutor commandExecutor;
	private final List<C> columns;

	protected static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	protected AbstractColumnAccessor(final CommandExecutor commandExecutor, final List<C> columns) {
		this.commandExecutor = commandExecutor;
		this.columns = columns;
	}

	protected CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public List<C> getColumns() {
		return columns;
	}

	@Override
	public Object getDataValue(final T rowObject, final int columnIndex) {
		return getDataValue(rowObject, getColumns().get(columnIndex));
	}

	public abstract Object getDataValue(final T rowObject, final C column);

	@Override
	public void setDataValue(final T rowObject, final int columnIndex, final Object newValue) {
		final Command cmd = createCommand(rowObject, getColumns().get(columnIndex), newValue);
		if (cmd.canExecute()) {
			commandExecutor.executeCommand(cmd);
		}
	}

	public abstract Command createCommand(final T rowObject, final C column, final Object newValue);

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
