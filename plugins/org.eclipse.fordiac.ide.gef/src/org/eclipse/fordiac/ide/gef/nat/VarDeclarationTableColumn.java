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

import org.eclipse.fordiac.ide.ui.FordiacMessages;

public enum VarDeclarationTableColumn {
	NAME(FordiacMessages.Name), TYPE(FordiacMessages.Type), COMMENT(FordiacMessages.Comment),
	INITIAL_VALUE(FordiacMessages.InitialValue), VAR_CONFIG(FordiacMessages.VarConfig),
	VISIBLE(FordiacMessages.Visible);

	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS = List.of(NAME, TYPE, COMMENT, INITIAL_VALUE);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VAR_CONFIG = List.of(NAME, TYPE, COMMENT,
			INITIAL_VALUE, VAR_CONFIG);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG = List.of(NAME,
			TYPE, COMMENT, INITIAL_VALUE, VISIBLE, VAR_CONFIG);

	private final String displayName;

	VarDeclarationTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
