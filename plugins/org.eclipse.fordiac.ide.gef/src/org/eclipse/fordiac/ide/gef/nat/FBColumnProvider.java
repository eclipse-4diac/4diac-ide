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
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class FBColumnProvider implements IDataProvider {

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		switch (columnIndex) {
		case I4diacNatTableUtil.NAME:
			return FordiacMessages.Name;
		case I4diacNatTableUtil.TYPE:
			return FordiacMessages.Type;
		case I4diacNatTableUtil.COMMENT:
			return FordiacMessages.Comment;
		default:
			return FordiacMessages.EmptyField;
		}
	}

	@Override
	public int getColumnCount() {
		return I4diacNatTableUtil.COMMENT + 1;
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