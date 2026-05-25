/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH, Johannes Kepler University Linz,
 *                    Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted from EditUntypedSubappInterfaceDataSection
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.properties;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public abstract class AbstractEditTypedInterfaceDataSection extends AbstractEditInterfaceDataSection {

	@Override
	protected BlockFBNetworkElement getType() {
		return (BlockFBNetworkElement) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		final var columns = VarDeclarationTableColumn.defaultColumnsWith(VarDeclarationTableColumn.VISIBLE);
		outputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns) {
			@Override
			public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
					final Object newValue) {
				return switch (column) {
				case NAME -> onNameChange(rowObject, Objects.toString(newValue, NULL_DEFAULT));
				default -> super.createCommand(rowObject, column, newValue);
				};
			}
		});
		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputProvider, columns);
		outputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(outputProvider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer, columnProvider,
				new UntypedSubappInterfaceEditableRule(getSectionEditableRule(), columns, outputProvider), null, this,
				false);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		outputTable.configure();
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(
				new VarDeclarationColumnAccessor(this, VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG) {
					@Override
					public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
							final Object newValue) {
						return switch (column) {
						case NAME -> onNameChange(rowObject, Objects.toString(newValue, NULL_DEFAULT));
						default -> super.createCommand(rowObject, column, newValue);
						};
					}
				});
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG);
		inputTable = NatTableWidgetFactory
				.createRowNatTable(inputsGroup, inputDataLayer, columnProvider,
						new UntypedSubappInterfaceEditableRule(getSectionEditableRule(),
								VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG, inputProvider),
						null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
	}

	private static class UntypedSubappInterfaceEditableRule
			extends NatTableColumnEditableRule<VarDeclarationTableColumn> {

		private static final Set<VarDeclarationTableColumn> CONNECTED_UNEDITABLE_COLUMNS = Set
				.of(VarDeclarationTableColumn.VISIBLE);

		private final IRowDataProvider<VarDeclaration> dataProvider;

		public UntypedSubappInterfaceEditableRule(final IEditableRule parent,
				final List<VarDeclarationTableColumn> columns, final IRowDataProvider<VarDeclaration> dataProvider) {
			super(parent, columns, VarDeclarationTableColumn.ALL_EDITABLE);
			this.dataProvider = dataProvider;
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final VarDeclaration rowItem = dataProvider.getRowObject(rowIndex);
			if (isConnected(rowItem) && CONNECTED_UNEDITABLE_COLUMNS.contains(getColumns().get(columnIndex))) {
				return false;
			}
			return super.isEditable(columnIndex, rowIndex);
		}

		private static boolean isConnected(final VarDeclaration varDeclaration) {
			return !varDeclaration.getInputConnections().isEmpty() || !varDeclaration.getOutputConnections().isEmpty();
		}
	}
}
