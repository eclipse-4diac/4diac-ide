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
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class AttributeConfigLabelAccumulator extends AbstractAnnotatedConfigLabelAccumulator<Attribute> {
	private final List<AttributeTableColumn> columns;

	public AttributeConfigLabelAccumulator(final IRowDataProvider<Attribute> dataProvider) {
		this(dataProvider, () -> null);
	}

	public AttributeConfigLabelAccumulator(final IRowDataProvider<Attribute> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		this(dataProvider, annotationModelSupplier, AttributeTableColumn.DEFAULT_COLUMNS);
	}

	public AttributeConfigLabelAccumulator(final IRowDataProvider<Attribute> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier,
			final List<AttributeTableColumn> columns) {
		super(dataProvider, annotationModelSupplier);
		this.columns = columns;
	}

	public List<AttributeTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final Attribute rowItem = getDataProvider().getRowObject(rowPosition);
		switch (columns.get(columnPosition)) {
		case NAME:
			configLabels.addLabel(NatTableWidgetFactory.ATTRIBUTE_PROPOSAL_CELL);
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetName);
			break;
		case TYPE:
			configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetType);
			break;
		case VALUE:
			if (rowItem.getType() instanceof StructuredType) {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_STRUCTURED_CELL);
			} else {
				configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			}
			if (!InitialValueHelper.hasInitalValue(rowItem)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetValue);
			break;
		case COMMENT:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			if (!CommentHelper.hasComment(rowItem)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			break;
		default:
			break;
		}
	}

}
