/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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

import java.util.List;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;

public enum DirectlyDerivedTypeTableColumn implements NatTableColumn {
	BASE_TYPE(FordiacMessages.BaseType), INITIAL_VALUE(FordiacMessages.InitialValue), COMMENT(FordiacMessages.Comment);

	public static final List<DirectlyDerivedTypeTableColumn> DEFAULT_COLUMNS = List.of(BASE_TYPE, INITIAL_VALUE,
			COMMENT);

	private final String displayName;

	DirectlyDerivedTypeTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
