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
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.application.commands.ChangeSubAppInterfaceOrderCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.commands.ResizingSubappInterfaceCreationCommand;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.gef.nat.CopyDataImportCommandHandler;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.PasteDataImportFromClipboardCommandHandler;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
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
		outputProvider = new ChangeableListDataProvider<>(
				new VarDeclarationColumnAccessor(this, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE) {
					@Override
					public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
							final Object newValue) {
						return switch (column) {
						case NAME -> onNameChange(rowObject, Objects.toString(newValue, NULL_DEFAULT));
						default -> super.createCommand(rowObject, column, newValue);
						};
					}
				});
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(outputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(outputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE));
		final var columnProvider = new NatTableColumnProvider<>(VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE);
		outputTable = NatTableWidgetFactory
				.createRowNatTable(outputsGroup, inputDataLayer, columnProvider,
						new UntypedSubappInterfaceEditableRule(getSectionEditableRule(),
								VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE, outputProvider),
						null, this, true);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.configure();

		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(outputTable);
		selectionLayer.registerCommandHandler(new CopyDataImportCommandHandler(selectionLayer, columnProvider,
				Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType())));
		selectionLayer.registerCommandHandler(new PasteDataImportFromClipboardCommandHandler(selectionLayer,
				this::getCurrentCommandStack, columnProvider, List.of(VarDeclarationTableColumn.TYPE)));

	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG) {
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
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		final var columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer, columnProvider,
				new UntypedSubappInterfaceEditableRule(getSectionEditableRule(),
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG, inputProvider),
				null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.configure();

		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(inputTable);
		selectionLayer.registerCommandHandler(new CopyDataImportCommandHandler(selectionLayer, columnProvider,
				Map.of(VarDeclarationTableColumn.TYPE, eObject -> ((VarDeclaration) eObject).getType())));
		selectionLayer.registerCommandHandler(new PasteDataImportFromClipboardCommandHandler(selectionLayer,
				this::getCurrentCommandStack, columnProvider, List.of(VarDeclarationTableColumn.TYPE)));

	}

	@Override
	protected SubApp getInputType(final Object input) {
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(input);
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

	@Override
	public Command onNameChange(final IInterfaceElement ie, final String newValue) {
		final ChangeNameCommand nameChangeCmd = ChangeNameCommand.forName(ie, newValue);
		if (getType().isUnfolded()) {
			return new ResizeGroupOrSubappCommand(
					GetEditPartFromGraficalViewerHelper.findAbstractContainerContentEditFromInterfaceElement(ie),
					nameChangeCmd);
		}
		return nameChangeCmd;
	}

	private class UntypedSubappInterfaceEditableRule extends NatTableColumnEditableRule<VarDeclarationTableColumn> {

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