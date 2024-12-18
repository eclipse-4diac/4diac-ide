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

import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferencePage;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeRetainAttributeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVarConfigurationCommand;
import org.eclipse.fordiac.ide.model.commands.change.HidePinCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;

public class VarDeclarationColumnAccessor extends AbstractColumnAccessor<VarDeclaration, VarDeclarationTableColumn> {

	public VarDeclarationColumnAccessor(final CommandExecutor commandExecutor) {
		this(commandExecutor, VarDeclarationTableColumn.DEFAULT_COLUMNS);
	}

	public VarDeclarationColumnAccessor(final CommandExecutor commandExecutor,
			final List<VarDeclarationTableColumn> columns) {
		super(commandExecutor, columns);
	}

	@Override
	public Object getDataValue(final VarDeclaration rowObject, final VarDeclarationTableColumn column) {
		return switch (column) {
		case NAME -> rowObject.getName();
		case TYPE -> rowObject.getFullTypeName();
		case COMMENT -> CommentHelper.getInstanceComment(rowObject);
		case INITIAL_VALUE -> getInitialValue(rowObject);
		case VAR_CONFIG -> Boolean.valueOf(rowObject.isVarConfig());
		case VISIBLE -> Boolean.valueOf(rowObject.isVisible());
		case RETAIN -> getAttributeValueAsString(rowObject);
		case VISIBLEIN, VISIBLEOUT -> Boolean.valueOf(handleInOutCheck(rowObject, column));

		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	private static boolean handleInOutCheck(final VarDeclaration rowObject, final VarDeclarationTableColumn column) {
		final IInterfaceElement interfaceElement = getCorrectVarInOutPin(rowObject, column);
		return interfaceElement.isVisible();

	}

	private static String getAttributeValueAsString(final VarDeclaration rowObject) {
		final Attribute attribute = rowObject.getAttribute(LibraryElementTags.RETAIN_ATTRIBUTE);
		if (attribute == null) {
			return NULL_DEFAULT;
		}
		return Objects.toString(RetainHelper.deriveTag(attribute.getValue()).getString(), NULL_DEFAULT);
	}

	@Override
	public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
			final Object newValue) {
		return switch (column) {
		case NAME -> ChangeNameCommand.forName(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case TYPE -> ChangeDataTypeCommand.forTypeDeclaration(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case COMMENT -> new ChangeCommentCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case INITIAL_VALUE -> new ChangeValueCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case VAR_CONFIG -> new ChangeVarConfigurationCommand(rowObject,
				Boolean.parseBoolean(Objects.toString(newValue, NULL_DEFAULT)));
		case VISIBLE -> new HidePinCommand(rowObject, Boolean.parseBoolean(Objects.toString(newValue, NULL_DEFAULT)));
		case VISIBLEIN, VISIBLEOUT -> handleInOut(rowObject, column, newValue);
		case RETAIN -> new ChangeRetainAttributeCommand(rowObject,
				RetainHelper.deriveTag(rowObject.getAttributeValue(LibraryElementTags.RETAIN_ATTRIBUTE)),
				RetainHelper.deriveTag(Objects.toString(newValue, NULL_DEFAULT)));
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	private static Command handleInOut(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
			final Object newValue) {
		final VarDeclaration varDeclaration = getCorrectVarInOutPin(rowObject, column);
		return new HidePinCommand(varDeclaration, Boolean.parseBoolean(Objects.toString(newValue, NULL_DEFAULT)));
	}

	private static VarDeclaration getCorrectVarInOutPin(final VarDeclaration rowObject,
			final VarDeclarationTableColumn column) {
		if (column.equals(VarDeclarationTableColumn.VISIBLEIN)) {
			return rowObject;
		}
		return rowObject.getInOutVarOpposite();
	}

	protected static String getInitialValue(final VarDeclaration rowObject) {
		final String value = InitialValueHelper.getInitialOrDefaultValue(rowObject);
		if (value.length() > DiagramPreferencePage.getMaxDefaultValueLength()) {
			return FordiacMessages.ValueTooLarge;
		}
		return value;
	}
}