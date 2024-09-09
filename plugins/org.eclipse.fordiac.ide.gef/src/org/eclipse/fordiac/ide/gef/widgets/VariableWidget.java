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
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.List;

import org.eclipse.fordiac.ide.gef.nat.VariableColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VariableTableColumn;
import org.eclipse.fordiac.ide.gef.nat.VariableTreeData;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class VariableWidget {

	private NatTable natTable;
	private VariableTreeData data;

	public Composite createWidget(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(composite);

		data = new VariableTreeData(new VariableColumnAccessor());
		final DataLayer dataLayer = new DataLayer(data);
		natTable = NatTableWidgetFactory.createTreeNatTable(composite, dataLayer, data,
				new NatTableColumnProvider<>(VariableTableColumn.DEFAULT_COLUMNS));

		return composite;
	}

	public void setInput(final List<Variable<?>> variables) {
		data.setInput(variables);
		natTable.refresh();
	}
}
