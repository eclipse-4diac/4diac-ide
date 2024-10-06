/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.IConfigLabelAccumulator;

public class VariableConfigLabelAccumulator implements IConfigLabelAccumulator {
	private final List<VariableTableColumn> columns;

	public VariableConfigLabelAccumulator() {
		this(VariableTableColumn.DEFAULT_COLUMNS);
	}

	public VariableConfigLabelAccumulator(final List<VariableTableColumn> columns) {
		this.columns = columns;
	}

	public List<VariableTableColumn> getColumns() {
		return columns;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		switch (columns.get(columnPosition)) {
		case NAME -> configLabels.addLabelOnTop(NatTableWidgetFactory.LEFT_ALIGNMENT);
		case VALUE -> configLabels.addLabel(VariableEditorConfiguration.VARIABLE_VALUE_CELL);
		default -> {
			// empty
		}
		}
	}

}
