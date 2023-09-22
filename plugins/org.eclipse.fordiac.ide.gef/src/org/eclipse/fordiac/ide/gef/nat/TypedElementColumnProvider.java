/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *   Martin Jobst - refactor to generic implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class TypedElementColumnProvider implements IDataProvider {

	private final List<TypedElementTableColumn> columns;

	public TypedElementColumnProvider() {
		this(TypedElementTableColumn.DEFAULT_COLUMNS);
	}

	public TypedElementColumnProvider(final List<TypedElementTableColumn> columns) {
		this.columns = columns;
	}

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		return columns.get(columnIndex);
	}

	@Override
	public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
		throw new UnsupportedOperationException(); // Setting data values to the header is not supported
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public int getRowCount() {
		return 1;
	}
}
