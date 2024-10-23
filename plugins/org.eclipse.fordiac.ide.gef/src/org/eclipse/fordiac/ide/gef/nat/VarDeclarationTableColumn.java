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

public enum VarDeclarationTableColumn implements NatTableColumn {
	NAME(FordiacMessages.Name), TYPE(FordiacMessages.Type), COMMENT(FordiacMessages.Comment),
	INITIAL_VALUE(FordiacMessages.InitialValue), VAR_CONFIG(FordiacMessages.VarConfig),
	VISIBLE(FordiacMessages.Visible), RETAIN(FordiacMessages.Retain), VISIBLEIN(FordiacMessages.Visible_IN),
	VISIBLEOUT(FordiacMessages.Visible_OUT);

	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS = List.of(NAME, TYPE, COMMENT, INITIAL_VALUE);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VISIBLE = List.of(NAME, TYPE, COMMENT,
			INITIAL_VALUE, VISIBLE);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VISIBLE_FOR_INOUTS = List.of(NAME, TYPE,
			COMMENT, INITIAL_VALUE, VISIBLEIN, VISIBLEOUT);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VAR_CONFIG = List.of(NAME, TYPE, COMMENT,
			INITIAL_VALUE, VAR_CONFIG);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG = List.of(NAME,
			TYPE, COMMENT, INITIAL_VALUE, VISIBLE, VAR_CONFIG);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG_AND_RETAIN = List
			.of(NAME, TYPE, COMMENT, INITIAL_VALUE, VISIBLE, VAR_CONFIG, RETAIN);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_WITH_RETAIN = List.of(NAME, TYPE, COMMENT,
			INITIAL_VALUE, RETAIN);

	public static final Set<VarDeclarationTableColumn> DEFAULT_EDITABLE = Set.of(VarDeclarationTableColumn.COMMENT,
			VarDeclarationTableColumn.INITIAL_VALUE, VarDeclarationTableColumn.VISIBLE,
			VarDeclarationTableColumn.VAR_CONFIG, VarDeclarationTableColumn.RETAIN, VarDeclarationTableColumn.VISIBLEIN,
			VarDeclarationTableColumn.VISIBLEOUT);

	public static final Set<VarDeclarationTableColumn> DEFAULT_EDITABLE_NO_INITIAL_VALUE = Set.of(
			VarDeclarationTableColumn.COMMENT, VarDeclarationTableColumn.VISIBLE, VarDeclarationTableColumn.VAR_CONFIG,
			VarDeclarationTableColumn.VISIBLEIN, VarDeclarationTableColumn.VISIBLEOUT);

	public static final Set<VarDeclarationTableColumn> ALL_EDITABLE = Set.of(VarDeclarationTableColumn.values());

	private final String displayName;

	VarDeclarationTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}