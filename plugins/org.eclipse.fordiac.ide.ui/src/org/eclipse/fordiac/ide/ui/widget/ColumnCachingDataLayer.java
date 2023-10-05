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
package org.eclipse.fordiac.ide.ui.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class ColumnCachingDataLayer<T extends Enum<?>> extends AbstractCachingDataLayer {

	private final List<T> columns;
	private final Set<T> cachedColumns;
	private final List<Object> placeholderValues;

	public ColumnCachingDataLayer(final IDataProvider dataProvider, final List<T> columns, final Set<T> cachedColumns) {
		super(dataProvider);
		this.columns = columns;
		this.cachedColumns = cachedColumns;
		this.placeholderValues = new ArrayList<>(
				Collections.nCopies(columns.size(), FordiacMessages.ComputingPlaceholderValue));
	}

	@SafeVarargs
	public ColumnCachingDataLayer(final IDataProvider dataProvider, final List<T> columns, final T... cachedColumns) {
		super(dataProvider);
		this.columns = columns;
		this.cachedColumns = Set.of(cachedColumns);
		this.placeholderValues = new ArrayList<>(
				Collections.nCopies(columns.size(), FordiacMessages.ComputingPlaceholderValue));
	}

	public ColumnCachingDataLayer(final IDataProvider dataProvider, final int defaultColumnWidth,
			final int defaultRowHeight, final List<T> columns, final Set<T> cachedColumns) {
		super(dataProvider, defaultColumnWidth, defaultRowHeight);
		this.columns = columns;
		this.cachedColumns = cachedColumns;
		this.placeholderValues = new ArrayList<>(
				Collections.nCopies(columns.size(), FordiacMessages.ComputingPlaceholderValue));
	}

	@Override
	protected boolean isCachedValue(final int columnIndex, final int rowIndex) {
		return cachedColumns.contains(columns.get(columnIndex));
	}

	@Override
	protected Object getPlaceholderValue(final int columnIndex, final int rowIndex) {
		return placeholderValues.get(columnIndex);
	}

	public void setPlaceholderValue(final int columnIndex, final Object value) {
		placeholderValues.set(columnIndex, value);
	}

	public void setPlaceholderValues(final Object value) {
		Collections.fill(placeholderValues, value);
	}
}
