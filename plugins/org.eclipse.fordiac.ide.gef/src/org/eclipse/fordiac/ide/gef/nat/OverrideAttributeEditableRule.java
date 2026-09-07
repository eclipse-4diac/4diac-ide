/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.OverrideAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;

public class OverrideAttributeEditableRule extends NatTableColumnEditableRule<AttributeTableColumn> {

	private final IRowDataProvider<Attribute> dataProvider;
	private final Supplier<TypedSubApp> subappSupplier;

	public OverrideAttributeEditableRule(final IEditableRule parent, final List<AttributeTableColumn> columns,
			final IRowDataProvider<Attribute> dataProvider, final Supplier<TypedSubApp> subappSupplier) {
		super(parent, columns, AttributeTableColumn.ALL_EDITABLE);
		this.dataProvider = dataProvider;
		this.subappSupplier = subappSupplier;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		if (!super.isEditable(columnIndex, rowIndex)) {
			return false;
		}

		final var column = getColumns().get(columnIndex);
		final var rowObject = dataProvider.getRowObject(rowIndex);

		if (rowObject instanceof final OverrideAttribute overrideAttribute
				&& overrideAttribute.eContainer() == subappSupplier.get()) {
			return switch (column) {
			case NAME, VALUE, COMMENT -> true;
			case TYPE -> overrideAttribute.getAttributeDeclaration() == null && isTypeEditable(overrideAttribute);
			default -> false;
			};
		}

		return column == AttributeTableColumn.VALUE || column == AttributeTableColumn.COMMENT;
	}

	private boolean isTypeEditable(final OverrideAttribute overrideAttribute) {
		return subappSupplier.get().findByQualifiedName(overrideAttribute.getLocation()).findFirst()
				.filter(ConfigurableObject.class::isInstance).map(ConfigurableObject.class::cast)
				.filter(confObject -> confObject.getAttribute(overrideAttribute.getName()) == null).isPresent();
	}
}
