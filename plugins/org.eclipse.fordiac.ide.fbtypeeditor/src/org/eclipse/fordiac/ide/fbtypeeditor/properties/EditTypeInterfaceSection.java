/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 * 				 2023 Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Bianca Wiesmayr - create command now has enhanced guess
 *   Daniel Lindhuber - added insert command method
 *   Martin Melik Merkumians - add Var Config Column
 *   Sebastian Hollersbacher - added Visible Column and Output table
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditInterfaceDataSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInterfaceOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.DropdownSelectionWidget;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public class EditTypeInterfaceSection extends AbstractEditInterfaceDataSection {

	@Override
	protected CreateInterfaceElementCommand newCreateCommand(final IInterfaceElement interfaceElement,
			final boolean isInput) {
		final DataType last = getLastUsedDataType(getType().getInterfaceList(), isInput, interfaceElement);
		final int pos = getInsertingIndex(interfaceElement, isInput);
		return new CreateInterfaceElementCommand(last, getCreationName(interfaceElement), getType().getInterfaceList(),
				isInput, pos);
	}

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		outputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN));
		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN);
		outputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(outputProvider, this::getAnnotationModel,
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN));

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, false);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new DropdownSelectionWidget(RetainTag.getTagList()));
		outputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		outputTable.configure();

	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(inputProvider, this::getAnnotationModel,
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new DropdownSelectionWidget(RetainTag.getTagList()));
		inputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		inputTable.configure();
	}

	@Override
	protected CreateInterfaceElementCommand newInsertCommand(final IInterfaceElement toCopy, final boolean isInput,
			final int index) {
		return new CreateInterfaceElementCommand(toCopy, isInput, getType().getInterfaceList(), index);
	}

	@Override
	protected FBType getInputType(final Object input) {
		return FBTypePropertiesFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected DeleteInterfaceCommand newDeleteCommand(final IInterfaceElement selection) {
		return new DeleteInterfaceCommand(selection);
	}

	@Override
	protected ChangeInterfaceOrderCommand newOrderCommand(final IInterfaceElement selection, final boolean moveUp) {
		return new ChangeInterfaceOrderCommand(selection, moveUp);
	}

	@Override
	protected FBType getType() {
		return (FBType) type;
	}

	@Override
	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterfaceList() : null;
	}

}
