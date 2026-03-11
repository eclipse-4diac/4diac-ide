/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;
import java.util.function.Supplier;

import org.eclipse.fordiac.ide.gef.annotation.FordiacAnnotationUtil;
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.ui.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class AttributeDeclarationConfigLabelAccumulator extends AbstractAnnotatedConfigLabelAccumulator<Attribute> {
	private final List<AttributeDeclarationTableColumn> columns;

	public AttributeDeclarationConfigLabelAccumulator(final IRowDataProvider<Attribute> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier,
			final List<AttributeDeclarationTableColumn> columns) {
		super(dataProvider, annotationModelSupplier);
		this.columns = columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final var column = columns.get(columnPosition);
		if (column == AttributeDeclarationTableColumn.COMMENT) {
			configLabels.addLabelOnTop(NatTableWidgetFactory.NONE_NULL);
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			if (!CommentHelper.hasComment(getDataProvider().getRowObject(rowPosition))) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
		}
		if (column == AttributeDeclarationTableColumn.FILE_PATH || column == AttributeDeclarationTableColumn.LOCATION
				|| column == AttributeDeclarationTableColumn.TYPE) {
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_TRUNCATING);
		}
		if (column == AttributeDeclarationTableColumn.VALUE) {
			configLabels.addLabel(InitialValueEditorConfiguration.INITIAL_VALUE_CELL);
			if (!InitialValueHelper.hasInitalValue(getDataProvider().getRowObject(rowPosition))) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			accumulateAttributeConfigLabels(configLabels, getDataProvider().getRowObject(rowPosition),
					FordiacAnnotationUtil::showOnTargetValue);
		}
	}
}
