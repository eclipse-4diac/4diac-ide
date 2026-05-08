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

import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.SorterModel;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.swt.widgets.Composite;

public class VarDeclarationTableBuilder {

	private VarDeclarationTableBuilder() {
		// utility class
	}

	public static BuiltNatTable<VarDeclaration> create(final Composite parent, final CommandExecutor commandExecutor) {
		final var columns = VarDeclarationTableColumn.defaultColumnsWithPrepended(VarDeclarationTableColumn.FILE_PATH,
				VarDeclarationTableColumn.LOCATION);
		final var accessor = new VarDeclarationColumnAccessor(commandExecutor, columns);
		final ChangeableListDataProvider<VarDeclaration> provider = new ChangeableListDataProvider<>(accessor);
		final SorterModel<VarDeclaration> sorterModel = new SorterModel<>(accessor);

		final DataLayer inputDataLayer = new VarDeclarationDataLayer(provider, columns);

		final VarDeclarationConfigLabelAccumulator configLabelProvider = new VarDeclarationConfigLabelAccumulator(
				provider, () -> null, columns) {
			@Override
			public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition,
					final int rowPosition) {
				super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
				switch (getColumns().get(columnPosition)) {
				case NAME, TYPE -> configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
				default -> {
					// no extra labels
				}
				}
			}
		};
		inputDataLayer.setConfigLabelAccumulator(configLabelProvider);

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(columns);

		final NatTable natTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
				new NatTableColumnEditableRule<>(new LinkedElementsEditableRule(provider), columns,
						VarDeclarationTableColumn.defaultEditableWithout(VarDeclarationTableColumn.FILE_PATH,
								VarDeclarationTableColumn.LOCATION)),
				null, null, sorterModel, false);
		natTable.addConfiguration(new InitialValueEditorConfiguration(provider));
		natTable.addConfiguration(new TypeDeclarationEditorConfiguration(provider));
		natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, commandExecutor));

		return new BuiltNatTable<>(natTable, provider, sorterModel);
	}
}
