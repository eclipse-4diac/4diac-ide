/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
import java.util.Set;

import org.eclipse.fordiac.ide.model.helpers.InterfaceHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.widget.nattable.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableColumnEditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;

public class VarDeclarationVisibleEditableRule extends NatTableColumnEditableRule<VarDeclarationTableColumn> {

	private final IChangeableRowDataProvider<VarDeclaration> dataProvider;
	private final List<VarDeclarationTableColumn> columns;

	public VarDeclarationVisibleEditableRule(final IEditableRule parentRule,
			final IChangeableRowDataProvider<VarDeclaration> dataProvider,
			final List<VarDeclarationTableColumn> columns, final Set<VarDeclarationTableColumn> editableColumns) {
		super(parentRule, columns, editableColumns);
		this.dataProvider = dataProvider;
		this.columns = columns;
	}

	@Override
	public boolean isEditable(final int columnIndex, final int rowIndex) {
		if (!super.isEditable(columnIndex, rowIndex)) {
			return false;
		}

		final VarDeclarationTableColumn column = columns.get(columnIndex);
		final VarDeclaration varDecl = dataProvider.getRowObject(rowIndex);

		return switch (column) {
		case VarDeclarationTableColumn.VISIBLE, VarDeclarationTableColumn.VISIBLEIN -> isHiddenOrCanHide(varDecl);
		case VarDeclarationTableColumn.VISIBLEOUT ->
			varDecl.isInOutVar() && isHiddenOrCanHide(varDecl.getInOutVarOpposite());
		default -> true;
		};
	}

	@Override
	public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
		return isEditable(cell.getColumnIndex(), cell.getRowIndex());
	}

	private static boolean isHiddenOrCanHide(final VarDeclaration pin) {
		return !pin.isVisible() || InterfaceHelper.canHidePin(pin);
	}
}
