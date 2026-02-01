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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumn;
import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;

public class SorterModel<T> implements ISortModel {
	private List<T> sortingList;

	AbstractColumnAccessor<T, ? extends NatTableColumn> accessor;
	private final Map<Integer, SortDirectionEnum> sortDirections = new LinkedHashMap<>();

	public SorterModel(final AbstractColumnAccessor<T, ? extends NatTableColumn> columnaccessor) {
		this.sortingList = Collections.emptyList();
		accessor = columnaccessor;
	}

	public void setSortingList(final List<T> list) {
		this.sortingList = list;
	}

	@Override
	public List<Integer> getSortedColumnIndexes() {
		return new ArrayList<>(sortDirections.keySet());
	}

	@Override
	public boolean isColumnIndexSorted(final int columnIndex) {
		return sortDirections.containsKey(Integer.valueOf(columnIndex));
	}

	@Override
	public SortDirectionEnum getSortDirection(final int columnIndex) {
		return sortDirections.getOrDefault(Integer.valueOf(columnIndex), SortDirectionEnum.NONE);
	}

	@Override
	public int getSortOrder(final int columnIndex) {
		return new ArrayList<>(sortDirections.keySet()).indexOf(Integer.valueOf(columnIndex));
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Comparator> getComparatorsForColumnIndex(final int columnIndex) {
		final var comp = getColumnComparator(columnIndex);
		return comp != null ? Collections.singletonList(comp) : Collections.emptyList();
	}

	@Override
	public Comparator<T> getColumnComparator(final int columnIndex) {
		return Comparator.comparing((final T att) -> (String) accessor.getDataValue(att, columnIndex),
				String.CASE_INSENSITIVE_ORDER);
	}

	@Override
	public void sort(final int columnIndex, final SortDirectionEnum sortDirection, final boolean accumulate) {
		Comparator<T> comparator = getColumnComparator(columnIndex);
		if (comparator == null) {
			return;
		}

		if (sortDirection == SortDirectionEnum.DESC) {
			comparator = Collections.reverseOrder(comparator);
		}

		if (!accumulate) {
			sortDirections.clear();
		}

		Comparator<T> combined = null;
		for (final int col : sortDirections.keySet()) {
			Comparator<T> colComp = getColumnComparator(columnIndex);
			if (sortDirections.get(Integer.valueOf(col)) == SortDirectionEnum.DESC) {
				colComp = Collections.reverseOrder(colComp);
			}

			combined = (combined == null) ? colComp : combined.thenComparing(colComp);
		}

		sortDirections.put(Integer.valueOf(columnIndex), sortDirection);
		sortingList.sort(comparator);
	}

	@Override
	public void clear() {
		sortDirections.clear();
	}
}
