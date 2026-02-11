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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumn;

public class AttributeDeclarationTableColumn implements NatTableColumn {
	public static final AttributeDeclarationTableColumn TYPE = new AttributeDeclarationTableColumn(
			FordiacMessages.Type);
	public static final AttributeDeclarationTableColumn COMMENT = new AttributeDeclarationTableColumn(
			FordiacMessages.Comment);
	public static final AttributeDeclarationTableColumn LOCATION = new AttributeDeclarationTableColumn(
			FordiacMessages.Location);
	public static final AttributeDeclarationTableColumn FILE_PATH = new AttributeDeclarationTableColumn(
			FordiacMessages.File_Path);
	public static final AttributeDeclarationTableColumn VALUE = new AttributeDeclarationTableColumn(
			FordiacMessages.Value);

	private final String displayName;

	AttributeDeclarationTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public static class AttributeDeclarationTableColumnProvider {
		private List<AttributeDeclarationTableColumn> columns;
		private Set<AttributeDeclarationTableColumn> editableColumns;

		public AttributeDeclarationTableColumnProvider(final AttributeDeclaration attributeDeclaration) {
			final var type = attributeDeclaration.getType();
			if (type instanceof final StructuredType structuredType) {
				final var columnNames = structuredType.getMemberVariables().stream().map(VarDeclaration::getName)
						.toArray(String[]::new);
				final var instanceColumns = Arrays.stream(columnNames).map(AttributeDeclarationTableColumn::new)
						.toList();

				this.columns = new ArrayList<>();
				columns.add(AttributeDeclarationTableColumn.FILE_PATH);
				columns.add(AttributeDeclarationTableColumn.LOCATION);
				columns.add(AttributeDeclarationTableColumn.TYPE);
				columns.add(AttributeDeclarationTableColumn.COMMENT);
				columns.addAll(instanceColumns);

				this.editableColumns = new HashSet<>();
				editableColumns.add(AttributeDeclarationTableColumn.COMMENT);
				editableColumns.addAll(instanceColumns);
			} else if (type instanceof DirectlyDerivedType) {
				this.columns = new ArrayList<>();
				columns.add(AttributeDeclarationTableColumn.FILE_PATH);
				columns.add(AttributeDeclarationTableColumn.LOCATION);
				columns.add(AttributeDeclarationTableColumn.TYPE);
				columns.add(AttributeDeclarationTableColumn.COMMENT);
				columns.add(AttributeDeclarationTableColumn.VALUE);

				this.editableColumns = new HashSet<>();
				editableColumns.add(AttributeDeclarationTableColumn.COMMENT);
				editableColumns.add(AttributeDeclarationTableColumn.VALUE);
			} else {
				this.columns = Collections.emptyList();
				this.editableColumns = Collections.emptySet();
			}
		}

		public List<AttributeDeclarationTableColumn> getColumns() {
			return columns;
		}

		public Set<AttributeDeclarationTableColumn> getEditableColumns() {
			return editableColumns;
		}
	}
}
