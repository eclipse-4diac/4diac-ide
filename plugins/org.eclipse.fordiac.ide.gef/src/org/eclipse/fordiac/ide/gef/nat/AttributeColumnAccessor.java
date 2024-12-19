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

import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;

public class AttributeColumnAccessor extends AbstractColumnAccessor<Attribute, AttributeTableColumn> {

	private static final String NULL_DEFAULT = ""; //$NON-NLS-1$

	public AttributeColumnAccessor(final CommandExecutor commandExecutor) {
		this(commandExecutor, AttributeTableColumn.DEFAULT_COLUMNS);
	}

	public AttributeColumnAccessor(final CommandExecutor commandExecutor, final List<AttributeTableColumn> columns) {
		super(commandExecutor, columns);
	}

	@Override
	public Object getDataValue(final Attribute rowObject, final AttributeTableColumn column) {
		return switch (column) {
		case NAME -> {
			if (rowObject.getAttributeDeclaration() != null) {
				yield ImportHelper.deresolveImport(rowObject.getAttributeDeclaration(), rowObject);
			}
			yield rowObject.getName();
		}
		case TYPE -> {
			if (rowObject.getAttributeDeclaration() != null) {
				yield PackageNameHelper.getFullTypeName(rowObject.getAttributeDeclaration());
			}
			yield ImportHelper.deresolveImport(rowObject.getType(), rowObject);
		}
		case VALUE -> InitialValueHelper.getInitialOrDefaultValue(rowObject);
		case COMMENT -> CommentHelper.getInstanceComment(rowObject);
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}

	@Override
	public Command createCommand(final Attribute rowObject, final AttributeTableColumn column, final Object newValue) {
		return switch (column) {
		case NAME -> ChangeNameCommand.forName(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case TYPE -> ChangeAttributeTypeCommand.forTypeName(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case VALUE -> new ChangeAttributeValueCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		case COMMENT -> new ChangeCommentCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		default -> throw new IllegalArgumentException("Unexpected value: " + column); //$NON-NLS-1$
		};
	}
}
