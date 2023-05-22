/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;

public class VarDeclarationWithVarConfigColumnProvider extends VarDeclarationColumnProvider {

	@Override
	public Object getDataValue(final int columnIndex, final int rowIndex) {
		if (columnIndex == I4diacNatTableUtil.VAR_CONFIG) {
			return FordiacMessages.VarConfig;
		}
		return super.getDataValue(columnIndex, rowIndex);
	}

	@Override
	public int getColumnCount() {
		return super.getColumnCount() + 1;
	}
}