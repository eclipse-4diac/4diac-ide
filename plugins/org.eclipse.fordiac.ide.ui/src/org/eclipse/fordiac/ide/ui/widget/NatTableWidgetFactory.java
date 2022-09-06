/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditBindings;
import org.eclipse.nebula.widgets.nattable.edit.config.DefaultEditConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.swt.widgets.Composite;

public final class NatTableWidgetFactory {
	public static NatTable createNatTable(final Composite parent, final IDataProvider dataProvider,
			final IDataProvider headerDataProvider) {
		final NatTable table = new NatTable(parent, createLayer(dataProvider, headerDataProvider), false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);

		table.addConfiguration(new DefaultNatTableStyleConfiguration());
		table.configure();

		return table;
	}

	private static ILayer createLayer(final IDataProvider dataProvider, final IDataProvider headerDataProvider) {
		final DataLayer dataLayer = new DataLayer(dataProvider);

		dataLayer.setColumnPercentageSizing(true);
		dataLayer.setColumnWidthPercentageByPosition(0, 25);
		dataLayer.setColumnWidthPercentageByPosition(1, 25);
		dataLayer.setColumnWidthPercentageByPosition(2, 25);
		dataLayer.setColumnWidthPercentageByPosition(3, 25);

		final SelectionLayer selectionLayer = new SelectionLayer(dataLayer);
		final ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);

		final DataLayer columnHeaderDataLayer = new DataLayer(headerDataProvider);
		final ILayer columnHeaderLayer = new ColumnHeaderLayer(columnHeaderDataLayer, viewportLayer, selectionLayer);

		final CompositeLayer gridLayer = new CompositeLayer(1, 2);
		gridLayer.setChildLayer(GridRegion.COLUMN_HEADER, columnHeaderLayer, 0, 0);
		gridLayer.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		gridLayer.addConfiguration(new DefaultEditConfiguration());
		gridLayer.addConfiguration(new DefaultEditBindings());
		gridLayer.addConfiguration(new AbstractRegistryConfiguration() {
			@Override
			public void configureRegistry(final IConfigRegistry configRegistry) {
				configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE,
						IEditableRule.ALWAYS_EDITABLE);
			}
		});

		return gridLayer;
	}
}
