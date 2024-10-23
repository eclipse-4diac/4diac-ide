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
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class VarDeclarationConfigLabelAccumulator extends AbstractAnnotatedConfigLabelAccumulator<VarDeclaration> {
	private final List<VarDeclarationTableColumn> columns;

	public VarDeclarationConfigLabelAccumulator(final IRowDataProvider<VarDeclaration> dataProvider) {
		this(dataProvider, () -> null);
	}

	public VarDeclarationConfigLabelAccumulator(final IRowDataProvider<VarDeclaration> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		this(dataProvider, annotationModelSupplier, VarDeclarationTableColumn.DEFAULT_COLUMNS);
	}

	public VarDeclarationConfigLabelAccumulator(final IRowDataProvider<VarDeclaration> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier,
			final List<VarDeclarationTableColumn> columns) {
		super(dataProvider, annotationModelSupplier);
		this.columns = columns;
	}

	public List<VarDeclarationTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final VarDeclaration rowItem = getDataProvider().getRowObject(rowPosition);
		switch (columns.get(columnPosition)) {
		case NAME:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTarget);
			break;
		case TYPE:
			configLabels.addLabel(TypeDeclarationEditorConfiguration.TYPE_DECLARATION_CELL);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetType);
			if (rowItem.isArray()) {
				accumulateAttributeConfigLabels(configLabels, rowItem.getArraySize());
			}
			break;
		case COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			if (!CommentHelper.hasComment(rowItem)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			break;
		case INITIAL_VALUE:
			if (rowItem.getType() instanceof StructuredType || rowItem.isArray()) {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_STRUCTURED_CELL);
			} else {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			}
			if (InitialValueHelper.hasInitalValue(rowItem)) {
				accumulateAttributeConfigLabels(configLabels, rowItem.getValue());
			} else {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			break;
		case VAR_CONFIG:
			configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			break;
		case VISIBLE, VISIBLEIN, VISIBLEOUT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.CHECKBOX_CELL);
			break;
		case RETAIN:
			configLabels.addLabelOnTop(NatTableWidgetFactory.RETAIN_CONFIG_CELL);
			break;
		default:
			break;
		}
	}

}
