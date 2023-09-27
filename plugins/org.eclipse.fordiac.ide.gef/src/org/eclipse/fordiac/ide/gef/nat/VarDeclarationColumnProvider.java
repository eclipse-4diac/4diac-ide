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

public class VarDeclarationColumnProvider implements IDataProvider {

	private final List<VarDeclarationTableColumn> columns;

	public VarDeclarationColumnProvider() {
		this(VarDeclarationTableColumn.DEFAULT_COLUMNS);
	}

	public VarDeclarationColumnProvider(final List<VarDeclarationTableColumn> columns) {
		this.columns = columns;
	}

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		return columns.get(columnIndex).getDisplayName();
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
