/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditVarInOutSection;
import org.eclipse.fordiac.ide.model.commands.create.CreateVarInOutCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Composite;

public class EditUntypedSubappVarInOutSection extends AbstractEditVarInOutSection {

	@Override
	protected CreationCommand newCreateCommand(final IInterfaceElement ie) {
		return new CreateVarInOutCommand(getLastUsedDataType(getType().getInterface(), ie), getType().getInterface(),
				getInsertingIndex(ie));
	}

	@Override
	protected CreationCommand newInsertCommand(final IInterfaceElement ie, final int index) {
		return new CreateVarInOutCommand(ie, getType().getInterface(), index);
	}

	@Override
	protected void setTableInput() {
		inputProvider.setInput(getType().getInterface().getInOutVars());
		if (isShowTableEditButtons()) {
			inputButtons.setEnabled(isEditable());
		}
	}

	@Override
	public void setupInputTable(final Composite parent) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_FOR_INOUTS) {
			@Override
			public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
					final Object newValue) {
				return switch (column) {
				default -> super.createCommand(rowObject, column, newValue);
				};
			}
		});
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_FOR_INOUTS);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_FOR_INOUTS));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_FOR_INOUTS);
		inputTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		inputTable.configure();
	}

	@Override
	protected SubApp getType() {
		return (SubApp) type;
	}

	@Override
	protected SubApp getInputType(final Object input) {
		return SubappPropertySectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

}