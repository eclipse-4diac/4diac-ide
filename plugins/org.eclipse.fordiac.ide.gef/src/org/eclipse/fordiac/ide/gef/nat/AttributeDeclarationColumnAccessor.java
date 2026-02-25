/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.commands.Command;

public class AttributeDeclarationColumnAccessor
		extends AbstractCommandColumnAccessor<Attribute, AttributeDeclarationTableColumn> {
	public AttributeDeclarationColumnAccessor(final CommandExecutor commandExecutor,
			final List<AttributeDeclarationTableColumn> columns) {
		super(commandExecutor, columns);
	}

	@Override
	public Object getDataValue(final Attribute rowObject, final AttributeDeclarationTableColumn column) {
		if (column == AttributeDeclarationTableColumn.TYPE) {
			return PackageNameHelper.getFullTypeName(rowObject.getAttributeDeclaration());
		}
		if (column == AttributeDeclarationTableColumn.COMMENT) {
			return CommentHelper.getInstanceComment(rowObject);
		}
		if (column == AttributeDeclarationTableColumn.FILE_PATH) {
			return EcoreUtil.getURI(rowObject).toPlatformString(true);
		}
		if (column == AttributeDeclarationTableColumn.LOCATION) {
			return FordiacMarkerHelper.getLocation(rowObject);
		}
		if (column == AttributeDeclarationTableColumn.VALUE) {
			return InitialValueHelper.getInitialOrDefaultValue(rowObject);
		}

		if (rowObject.getAttributeDeclaration().getType() instanceof final StructuredType st) {
			final var columnName = column.getDisplayName();
			final StructValue value = (StructValue) ValueOperations.parseValue(rowObject.getValue(), st, null);
			final var columnVariable = value.get(columnName);
			return columnVariable != null ? columnVariable.toString(true) : null;
		}
		return null;
	}

	@Override
	public Command createCommand(final Attribute rowObject, final AttributeDeclarationTableColumn column,
			final Object newValue) {
		if (column == AttributeDeclarationTableColumn.COMMENT) {
			return new ChangeCommentCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		}
		if (column == AttributeDeclarationTableColumn.VALUE) {
			return new ChangeAttributeValueCommand(rowObject, Objects.toString(newValue, NULL_DEFAULT));
		}
		if (rowObject.getAttributeDeclaration().getType() instanceof final StructuredType structuredType) {
			final var columnName = column.getDisplayName();
			final StructValue value = new StructValue(
					(StructValue) ValueOperations.parseValue(rowObject.getValue(), structuredType, null));
			value.get(columnName).setValue((String) newValue);
			return new ChangeAttributeValueCommand(rowObject, value.toString(true));
		}
		return null;
	}
}
