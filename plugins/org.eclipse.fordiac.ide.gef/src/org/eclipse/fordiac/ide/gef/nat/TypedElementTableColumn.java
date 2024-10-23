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

public enum TypedElementTableColumn implements NatTableColumn {
	NAME(FordiacMessages.Name), TYPE(FordiacMessages.Type), COMMENT(FordiacMessages.Comment);

	public static final List<TypedElementTableColumn> DEFAULT_COLUMNS = List.of(NAME, TYPE, COMMENT);

	public static final Set<TypedElementTableColumn> DEFAULT_EDITABLE = Set.of(NAME, TYPE, COMMENT);

	private final String displayName;

	TypedElementTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
