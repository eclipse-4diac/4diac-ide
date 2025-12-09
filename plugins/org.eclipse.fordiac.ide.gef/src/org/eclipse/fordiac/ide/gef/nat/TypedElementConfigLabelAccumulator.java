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
import org.eclipse.fordiac.ide.model.edit.helper.CommentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class TypedElementConfigLabelAccumulator<T extends ITypedElement>
		extends AbstractAnnotatedConfigLabelAccumulator<T> {
	private final List<TypedElementTableColumn> columns;

	public TypedElementConfigLabelAccumulator(final IRowDataProvider<T> dataProvider) {
		this(dataProvider, () -> null);
	}

	public TypedElementConfigLabelAccumulator(final IRowDataProvider<T> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier) {
		this(dataProvider, annotationModelSupplier, TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	public TypedElementConfigLabelAccumulator(final IRowDataProvider<T> dataProvider,
			final Supplier<GraphicalAnnotationModel> annotationModelSupplier,
			final List<TypedElementTableColumn> columns) {
		super(dataProvider, annotationModelSupplier);
		this.columns = columns;
	}

	public List<TypedElementTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final T rowItem = getDataProvider().getRowObject(rowPosition);
		switch (columns.get(columnPosition)) {
		case NAME:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetName);
			break;
		case TYPE:
			configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			accumulateAttributeConfigLabels(configLabels, rowItem, FordiacAnnotationUtil::showOnTargetType);
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
