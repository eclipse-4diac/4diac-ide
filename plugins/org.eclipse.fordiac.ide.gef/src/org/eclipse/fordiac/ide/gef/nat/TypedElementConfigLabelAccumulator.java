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
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorTypeEntry;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

public class TypedElementConfigLabelAccumulator implements IConfigLabelAccumulator {
	private final IRowDataProvider<? extends ITypedElement> dataProvider;
	private final List<TypedElementTableColumn> columns;

	public TypedElementConfigLabelAccumulator(final IRowDataProvider<? extends ITypedElement> dataProvider) {
		this(dataProvider, TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	public TypedElementConfigLabelAccumulator(final IRowDataProvider<? extends ITypedElement> dataProvider,
			final List<TypedElementTableColumn> columns) {
		this.dataProvider = dataProvider;
		this.columns = columns;
	}

	public List<TypedElementTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		final ITypedElement rowItem = dataProvider.getRowObject(rowPosition);
		switch (columns.get(columnPosition)) {
		case NAME:
			configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
			break;
		case TYPE:
			configLabels.addLabel(NatTableWidgetFactory.PROPOSAL_CELL);
			if (rowItem.getType() instanceof ErrorMarkerDataType
					|| (rowItem instanceof final TypedConfigureableObject typedObject
							&& typedObject.getTypeEntry() instanceof ErrorTypeEntry)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
			}
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
