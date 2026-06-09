/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.nattable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.fordiac.ide.gef.nat.AttributeDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.AttributeDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.AttributeDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.grid.command.AutoResizeColumnCommandHandler;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Composite;

public class DynamicAttributeTableBuilder {

	private DynamicAttributeTableBuilder() {
		// utility class
	}

	public static BuiltNatTable<Attribute> create(final Composite parent, final CommandExecutor commandExecutor,
			final AttributeDeclaration attributeDeclaration) {
		final var tableColumnProvider = new AttributeDeclarationTableColumn.AttributeDeclarationTableColumnProvider(
				attributeDeclaration);
		final List<AttributeDeclarationTableColumn> columns = tableColumnProvider.getColumns();
		final Set<AttributeDeclarationTableColumn> editColumns = tableColumnProvider.getEditableColumns();

		final var accessor = new AttributeDeclarationColumnAccessor(commandExecutor, columns);
		final ChangeableListDataProvider<Attribute> provider = new ChangeableListDataProvider<>(accessor);
		final SorterModel<Attribute> sorterModel = new SorterModel<>(accessor);

		final DataLayer dataLayer = new DataLayer(provider);
		final var columnProvider = new NatTableColumnProvider<>(columns);
		dataLayer.setConfigLabelAccumulator(
				new AttributeDeclarationConfigLabelAccumulator(provider, () -> null, columns));

		final AtomicReference<NatTable> natTableRef = new AtomicReference<>();

		final NatTable natTable = NatTableWidgetFactory.createRowNatTable(parent, dataLayer, columnProvider,
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE, columns, editColumns),
				new TypeSelectionButton(() -> BulkEditorNatTable.typeLibraryForSelection(provider, natTableRef.get()),
						DataTypeSelectionContentProvider.INSTANCE, DataTypeSelectionTreeContentProvider.INSTANCE),
				null, sorterModel, false);
		natTableRef.set(natTable);

		dataLayer.setColumnPercentageSizing(false);
		dataLayer.registerCommandHandler(new AutoResizeColumnCommandHandler(dataLayer, dataLayer));

		return new BuiltNatTable<>(natTable, provider, sorterModel);
	}
}
