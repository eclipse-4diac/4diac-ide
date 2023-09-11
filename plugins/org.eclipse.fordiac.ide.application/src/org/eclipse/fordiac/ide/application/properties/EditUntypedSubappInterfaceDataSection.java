/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 * 				 2020 - 2023 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method & cell editor classes
 *   Martin Melik Merkumians - added option for setting VAR CONFIG on inputs
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnProvider;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationEditableRule;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionButton;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.ColumnCachingDataLayer;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public class EditUntypedSubappInterfaceDataSection extends AbstractEditInterfaceDataSection {

	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement interfaceElement, final boolean isInput) {
		final DataType last = getLastUsedDataType(getType().getInterface(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(last,
				getCreationName(interfaceElement), getType().getInterface(), isInput, pos);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement interfaceElement, final boolean isInput,
			final int index) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(interfaceElement,
				isInput, getType().getInterface(), index);
		return ResizingSubappInterfaceCreationCommand.wrapCreateCommand(cmd, getType());
	}

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		outputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		final DataLayer outputDataLayer = new ColumnCachingDataLayer<>(outputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS, VarDeclarationTableColumn.INITIAL_VALUE);
		outputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(outputProvider));
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer,
				new VarDeclarationColumnProvider(),
				new UntypedSubappInterfaceEditableRule(getSectionEditableRule(),
						VarDeclarationTableColumn.DEFAULT_COLUMNS, outputProvider),
				new DataTypeSelectionButton(typeSelection), this, false);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.configure();
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(
				new VarDeclarationColumnAccessor(this, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VAR_CONFIG));
		final DataLayer inputDataLayer = new ColumnCachingDataLayer<>(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VAR_CONFIG, VarDeclarationTableColumn.INITIAL_VALUE);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VAR_CONFIG));
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer,
				new VarDeclarationColumnProvider(VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VAR_CONFIG),
				new UntypedSubappInterfaceEditableRule(getSectionEditableRule(),
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VAR_CONFIG, inputProvider),
				new DataTypeSelectionButton(typeSelection), this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return EditInterfaceAdapterSection.getSubAppFromInput(input);
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteSubAppInterfaceElementCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeSubAppInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

	private class UntypedSubappInterfaceEditableRule extends VarDeclarationEditableRule {

		private static final Set<VarDeclarationTableColumn> CONNECTED_EDITABLE_COLUMNS = Set.of(
				VarDeclarationTableColumn.NAME, VarDeclarationTableColumn.COMMENT,
				VarDeclarationTableColumn.VAR_CONFIG);

		private final IRowDataProvider<VarDeclaration> dataProvider;

		public UntypedSubappInterfaceEditableRule(final IEditableRule parent,
				final List<VarDeclarationTableColumn> columns, final IRowDataProvider<VarDeclaration> dataProvider) {
			super(parent, columns, ALL_EDITABLE);
			this.dataProvider = dataProvider;
		}

		@Override
		public boolean isEditable(final int columnIndex, final int rowIndex) {
			final VarDeclaration rowItem = dataProvider.getRowObject(rowIndex);
			if (isConnected(rowItem) && !CONNECTED_EDITABLE_COLUMNS.contains(getColumns().get(columnIndex))) {
				return false;
			}
			return super.isEditable(columnIndex, rowIndex);
		}

		private static boolean isConnected(final VarDeclaration varDeclaration) {
			return !varDeclaration.getInputConnections().isEmpty() || !varDeclaration.getOutputConnections().isEmpty();
		}
	}
}
