/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class VarDeclarationColumnProvider implements IDataProvider {

	public static final int NAME = 0;
	public static final int TYPE = 1;
	public static final int COMMENT = 2;
	public static final int INITIAL_VALUE = 3;
	public static final int ARRAY_SIZE = 4;

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		switch (columnIndex) {
		case NAME:
			return FordiacMessages.Name;
		case TYPE:
			return FordiacMessages.Type;
		case COMMENT:
			return FordiacMessages.Comment;
		case INITIAL_VALUE:
			return FordiacMessages.InitialValue;
		case ARRAY_SIZE:
			return FordiacMessages.ArraySize;

		default:
			return FordiacMessages.EmptyField;
		}
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
		// Setting data values to the header is not supported
	}
}