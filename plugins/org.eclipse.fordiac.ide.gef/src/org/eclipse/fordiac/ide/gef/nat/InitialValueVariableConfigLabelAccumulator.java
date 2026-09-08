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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.nat;

import java.util.function.Predicate;

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.ui.widget.nattable.NatTableWidgetFactory;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;

public class InitialValueVariableConfigLabelAccumulator extends VariableConfigLabelAccumulator {

	private final IRowDataProvider<Variable<?>> dataProvider;
	private final Predicate<Variable<?>> inheritedValuePredicate;

	public InitialValueVariableConfigLabelAccumulator(final IRowDataProvider<Variable<?>> dataProvider,
			final Predicate<Variable<?>> inheritedValuePredicate) {
		this.dataProvider = dataProvider;
		this.inheritedValuePredicate = inheritedValuePredicate;
	}

	@Override
	public void accumulateConfigLabels(final LabelStack configLabels, final int columnPosition, final int rowPosition) {
		super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
		if (getColumns().get(columnPosition) == VariableTableColumn.VALUE
				&& inheritedValuePredicate.test(dataProvider.getRowObject(rowPosition))) {
			configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
		}
	}
}
