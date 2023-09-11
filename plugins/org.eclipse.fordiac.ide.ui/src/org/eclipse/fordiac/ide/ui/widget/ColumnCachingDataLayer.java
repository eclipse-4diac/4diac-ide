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

import java.util.List;
import java.util.Set;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class ColumnCachingDataLayer<T extends Enum<?>> extends AbstractCachingDataLayer {

	private final List<T> columns;
	private final Set<T> cachedColumns;

	public ColumnCachingDataLayer(final IDataProvider dataProvider, final List<T> columns, final Set<T> cachedColumns) {
		super(dataProvider);
		this.columns = columns;
		this.cachedColumns = cachedColumns;
	}

	@SafeVarargs
	public ColumnCachingDataLayer(final IDataProvider dataProvider, final List<T> columns, final T... cachedColumns) {
		super(dataProvider);
		this.columns = columns;
		this.cachedColumns = Set.of(cachedColumns);
	}

	public ColumnCachingDataLayer(final IDataProvider dataProvider, final int defaultColumnWidth,
			final int defaultRowHeight, final List<T> columns, final Set<T> cachedColumns) {
		super(dataProvider, defaultColumnWidth, defaultRowHeight);
		this.columns = columns;
		this.cachedColumns = cachedColumns;
	}

	@Override
	protected boolean isCachedValue(final int columnIndex, final int rowIndex) {
		return cachedColumns.contains(columns.get(columnIndex));
	}
}
