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
 *    Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.subapptypeeditor.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.properties.EditTypeInterfaceSection;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public class EditTypedSubappInterfaceSection extends EditTypeInterfaceSection {

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		outputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		outputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(outputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, false);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		outputTable.configure();
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		inputTable.configure();
	}
}
