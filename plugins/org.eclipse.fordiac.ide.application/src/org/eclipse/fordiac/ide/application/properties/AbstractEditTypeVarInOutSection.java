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
 *   Alois Zoitl    - Extracted from EditUntypedSubappVarInOutSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationVisibleEditableRule;
import org.eclipse.fordiac.ide.gef.properties.AbstractEditVarInOutSection;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractEditTypeVarInOutSection extends AbstractEditVarInOutSection {

	@Override
	protected void setTableInput() {
		inputProvider.setInput(getInterface().getInOutVars());
		if (isShowTableEditButtons()) {
			inputButtons.setEnabled(isEditable());
		}
	}

	@Override
	public void setupInputTable(final Composite parent) {
		final var columns = VarDeclarationTableColumn.defaultColumnsWith(VarDeclarationTableColumn.VISIBLEIN,
				VarDeclarationTableColumn.VISIBLEOUT);
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns) {
			@Override
			public Command createCommand(final VarDeclaration rowObject, final VarDeclarationTableColumn column,
					final Object newValue) {
				return switch (column) {
				default -> super.createCommand(rowObject, column, newValue);
				};
			}
		});
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider, columns);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(inputProvider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		inputTable = NatTableWidgetFactory.createRowNatTable(parent, inputDataLayer, columnProvider,
				new VarDeclarationVisibleEditableRule(getSectionEditableRule(), inputProvider, columns,
						VarDeclarationTableColumn.ALL_EDITABLE),
				null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
	}

	@Override
	protected BlockFBNetworkElement getType() {
		return (BlockFBNetworkElement) type;
	}

	protected InterfaceList getInterface() {
		return (getType() != null) ? getType().getInterface() : null;
	}

	@Override
	protected IEditableRule getSectionEditableRule() {
		return IEditableRule.ALWAYS_EDITABLE;
	}

}
