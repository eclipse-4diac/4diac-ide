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
import java.util.Set;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumn;

public enum AttributeTableColumn implements NatTableColumn {
	NAME(FordiacMessages.Name), TYPE(FordiacMessages.Type), VALUE(FordiacMessages.Value),
	COMMENT(FordiacMessages.Comment);

	public static final List<AttributeTableColumn> DEFAULT_COLUMNS = List.of(NAME, TYPE, VALUE, COMMENT);

	public static final Set<AttributeTableColumn> ALL_EDITABLE = Set.of(AttributeTableColumn.values());

	private final String displayName;

	AttributeTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
