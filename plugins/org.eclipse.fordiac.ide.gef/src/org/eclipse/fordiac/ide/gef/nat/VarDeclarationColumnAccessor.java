/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
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
import java.util.Objects;
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.commands.change.HidePinCommand;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

public class VarDeclarationColumnAccessor implements IColumnPropertyAccessor<VarDeclaration> {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	private final CommandExecutor commandExecutor;
	private final List<VarDeclarationTableColumn> columns;

	public VarDeclarationColumnAccessor(final CommandExecutor commandExecutor) {
		this(commandExecutor, VarDeclarationTableColumn.DEFAULT_COLUMNS);
	}

	public VarDeclarationColumnAccessor(final CommandExecutor commandExecutor,
			final List<VarDeclarationTableColumn> columns) {
		this.commandExecutor = commandExecutor;
		this.columns = columns;
	}

	protected CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}

	public List<VarDeclarationTableColumn> getColumns() {
		return columns;
	}

	@Override
	public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
		return switch (columns.get(columnIndex)) {
		case NAME -> rowObject.getName();
		case TYPE -> rowObject.getFullTypeName();
		case COMMENT -> CommentHelper.getInstanceComment(rowObject);
		case INITIAL_VALUE -> getInitialValue(rowObject);
		case VAR_CONFIG -> Boolean.valueOf(rowObject.isVarConfig());
		case VISIBLE -> Boolean.valueOf(rowObject.isVisible());
		default -> throw new IllegalArgumentException("Unexpected value: " + columns.get(columnIndex)); //$NON-NLS-1$
		};
	}

	@Override
	public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
		final Command cmd = switch (columns.get(columnIndex)) {
		case NAME -> ChangeNameCommand.forName(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case TYPE -> ChangeDataTypeCommand.forTypeDeclaration(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case COMMENT -> new ChangeCommentCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case INITIAL_VALUE -> new ChangeValueCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case VAR_CONFIG -> new ChangeVarConfigurationCommand(rowObject,
				Boolean.parseBoolean(Objects.toString(newValue, NULL_DEFAULT)));
		case VISIBLE -> new HidePinCommand(rowObject, Boolean.parseBoolean(Objects.toString(newValue, NULL_DEFAULT)));
		default -> throw new IllegalArgumentException("Unexpected value: " + columns.get(columnIndex)); //$NON-NLS-1$
		};
		if (cmd.canExecute()) {
			commandExecutor.executeCommand(cmd);
		}
	}

	protected static String getInitialValue(final VarDeclaration rowObject) {
		final String value = InitialValueHelper.getInitialOrDefaultValue(rowObject);
		if (value.length() > DiagramPreferences.getMaxDefaultValueLength()) {
			return FordiacMessages.ValueTooLarge;
		}
		return value;
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
