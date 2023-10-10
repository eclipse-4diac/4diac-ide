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

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

public class AttributeEditableRule extends NatTableColumnEditableRule<AttributeTableColumn> {

	private final IRowDataProvider<Attribute> dataProvider;

	public AttributeEditableRule(final IEditableRule parent, final List<AttributeTableColumn> columns,
			final IRowDataProvider<Attribute> dataProvider) {
		super(parent, columns, AttributeTableColumn.ALL_EDITABLE);
		this.dataProvider = dataProvider;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		final Attribute rowItem = dataProvider.getRowObject(rowIndex);
		if (rowItem.getAttributeDeclaration() != null && getColumns().get(columnIndex) == AttributeTableColumn.TYPE) {
			return false;
		}
		return super.isEditable(columnIndex, rowIndex);
	}
}
