/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation#
 *   Sebastian Hollersbacher - Refactored to use builder
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumn;

public enum VarDeclarationTableColumn implements NatTableColumn {
	// @formatter:off
	NAME(FordiacMessages.Name),
	TYPE(FordiacMessages.Type),
	COMMENT(FordiacMessages.Comment),
	INITIAL_VALUE(FordiacMessages.InitialValue),
	VISIBLE(FordiacMessages.Visible),
	VAR_CONFIG(FordiacMessages.VarConfig),
	RETAIN(FordiacMessages.Retain),
	VISIBLEIN(FordiacMessages.Visible_IN),
	VISIBLEOUT(FordiacMessages.Visible_OUT),
	LOCATION(FordiacMessages.Location),
	FILE_PATH(FordiacMessages.File_Path);
	// @formatter:on

	// Columns-List
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS = List.of(NAME, TYPE, COMMENT, INITIAL_VALUE);
	public static final List<VarDeclarationTableColumn> DEFAULT_COLUMNS_VISIBLE_VARCONFIG = defaultColumnsWith(VISIBLE,
			VAR_CONFIG);

	public static List<VarDeclarationTableColumn> defaultColumnsWith(final VarDeclarationTableColumn... extra) {
		final List<VarDeclarationTableColumn> columns = new ArrayList<>(DEFAULT_COLUMNS);
		columns.addAll(List.of(extra));
		return List.copyOf(columns);
	}

	public static List<VarDeclarationTableColumn> defaultColumnsWithPrepended(
			final VarDeclarationTableColumn... extra) {
		final List<VarDeclarationTableColumn> columns = new ArrayList<>(List.of(extra));
		columns.addAll(DEFAULT_COLUMNS);
		return List.copyOf(columns);
	}

	// Editable-Set
	public static final Set<VarDeclarationTableColumn> ALL_EDITABLE = Set.of(VarDeclarationTableColumn.values());
	public static final Set<VarDeclarationTableColumn> DEFAULT_EDITABLE = Set.of(COMMENT, INITIAL_VALUE, VISIBLE,
			VAR_CONFIG, RETAIN, VISIBLEIN, VISIBLEOUT);

	public static Set<VarDeclarationTableColumn> defaultEditableWithout(final VarDeclarationTableColumn... excluded) {
		final EnumSet<VarDeclarationTableColumn> editable = EnumSet.copyOf(DEFAULT_EDITABLE);
		editable.removeAll(Set.of(excluded));
		return Collections.unmodifiableSet(editable);
	}

	private final String displayName;

	VarDeclarationTableColumn(final String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}