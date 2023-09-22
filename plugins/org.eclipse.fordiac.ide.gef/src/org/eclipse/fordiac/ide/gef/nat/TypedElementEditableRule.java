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
import java.util.Set;

import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;

public class TypedElementEditableRule extends EditableRule {
	private final IEditableRule parent;
	private final List<TypedElementTableColumn> columns;
	private final Set<TypedElementTableColumn> editableColumns;

	public TypedElementEditableRule(final IEditableRule parent, final List<TypedElementTableColumn> columns,
			final Set<TypedElementTableColumn> editableColumns) {
		this.parent = parent;
		this.columns = columns;
		this.editableColumns = editableColumns;
	}

	public List<TypedElementTableColumn> getColumns() {
		return columns;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		return parent.isEditable(columnIndex, rowIndex) && editableColumns.contains(columns.get(columnIndex));
	}
}
