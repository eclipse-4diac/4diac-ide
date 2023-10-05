/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

public class VarDeclarationConfigLabelAccumulator implements IConfigLabelAccumulator {
	private final IRowDataProvider<VarDeclaration> dataProvider;
	private final List<VarDeclarationTableColumn> columns;

	public VarDeclarationConfigLabelAccumulator(final IRowDataProvider<VarDeclaration> dataProvider) {
		this(dataProvider, VarDeclarationTableColumn.DEFAULT_COLUMNS);
	}

	public VarDeclarationConfigLabelAccumulator(final IRowDataProvider<VarDeclaration> dataProvider,
			final List<VarDeclarationTableColumn> columns) {
		this.dataProvider = dataProvider;
		this.columns = columns;
	}

	public List<VarDeclarationTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final VarDeclaration rowItem = dataProvider.getRowObject(rowPosition);
		switch (columns.get(columnPosition)) {
		case NAME:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			break;
		case TYPE:
			configLabels.addLabel(TypeDeclarationEditorConfiguration.TYPE_DECLARATION_CELL);
			if (rowItem.getType() instanceof ErrorMarkerDataType
					|| (rowItem.isArray() && rowItem.getArraySize().hasError())) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
			break;
		case COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			if (!CommentHelper.hasComment(rowItem)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			break;
		case INITIAL_VALUE:
			configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			if (rowItem.getValue() != null && rowItem.getValue().hasError()) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			} else if (!InitialValueHelper.hasInitalValue(rowItem)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			break;
		case VAR_CONFIG:
			configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			break;
		case VISIBLE:
			configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			break;
		default:
			break;
		}
	}

}
