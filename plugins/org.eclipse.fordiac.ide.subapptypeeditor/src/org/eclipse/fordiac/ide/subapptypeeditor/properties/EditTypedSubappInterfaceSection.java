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
import org.eclipse.fordiac.ide.ui.widget.nattable.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.widgets.Group;

public class EditTypedSubappInterfaceSection extends EditTypeInterfaceSection {

	@Override
	public void setupOutputTable(final Group outputsGroup) {
		final var columns = VarDeclarationTableColumn.defaultColumnsWith(VarDeclarationTableColumn.VISIBLE);
		outputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns));
		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputProvider, columns);
		outputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(outputProvider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		outputTable = NatTableWidgetFactory.createRowNatTable(outputsGroup, outputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, false);
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new TypeDeclarationEditorConfiguration(outputProvider));
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		outputTable.configure();
	}

	@Override
	public void setupInputTable(final Group inputsGroup) {
		final var columns = VarDeclarationTableColumn.DEFAULT_COLUMNS_VISIBLE_VARCONFIG;
		inputProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this, columns));
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputProvider, columns);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(inputProvider, this::getAnnotationModel, columns));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(columns);
		inputTable = NatTableWidgetFactory.createRowNatTable(inputsGroup, inputDataLayer, columnProvider,
				getSectionEditableRule(), null, this, true);
		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new TypeDeclarationEditorConfiguration(inputProvider));
		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		inputTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		inputTable.configure();
	}
}
