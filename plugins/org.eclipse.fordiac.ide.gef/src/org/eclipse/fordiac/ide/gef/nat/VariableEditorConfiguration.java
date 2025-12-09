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

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;

public class VariableEditorConfiguration extends AbstractRegistryConfiguration {
	public static final String VARIABLE_VALUE_CELL = "VARIABLE_VALUE_CELL"; //$NON-NLS-1$

	private final IRowDataProvider<? extends Variable<?>> dataProvider;

	public VariableEditorConfiguration(final IRowDataProvider<? extends Variable<?>> dataProvider) {
		this.dataProvider = dataProvider;
	}

	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR,
				new VariableCellEditor(dataProvider, true), DisplayMode.EDIT, VARIABLE_VALUE_CELL);
		configRegistry.registerConfigAttribute(EditConfigAttributes.DATA_VALIDATOR,
				new VariableDataValidator(dataProvider), DisplayMode.EDIT, VARIABLE_VALUE_CELL);
	}
}
